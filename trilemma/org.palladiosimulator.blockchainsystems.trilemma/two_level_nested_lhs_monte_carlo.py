#!/usr/bin/env python3
"""Generate design and operational samples for the revised 3SIM manuscript.

Manuscript-defined primary sample sizes
----------------------------------------
* Design: 128 configurations = 120 Latin-hypercube configurations + 8 corners.
* Operational: 96 Latin-hypercube conditions.
* Homogeneous operational reference: 1 additional condition, reported separately.
* Nested initial stage: 64 design configurations x 48 operational conditions.
* Primary experiment: lambda_tx is an operational parameter. The 96 sampled
  operational conditions contain common ABSOLUTE transaction-arrival rates;
  every design is evaluated under exactly the same 96 demand conditions.
* Separate overload analysis: 32 space-filling conditions over
  (N_V, H_node, H_link, H_hash, f_A), crossed with designs and assigned the
  design-dependent 105%-of-theoretical-capacity rate lambda_tx^sat.

Important manuscript details implemented here
----------------------------------------------
* C in [1, 8], BCI in [60, 1200] s, MBS in [0.25, 8] MB.
* N_V in [20, 1000], H_node in [0, 0.50], H_link in [0, 0.40],
  H_hash in [0, 0.50], f_A in [0, 0.25].
* BCI, MBS, N_V, and lambda_tx are sampled logarithmically; the H values and
  f_A are sampled linearly.
* The topology is feasible only when 2*C <= N_V - 1.
* H_node and H_hash are translated with allocation dimension m=N_V.
* H_link is translated with allocation dimension m=2*C (not N_V).
* The sampled attacker fraction f_A remains the operational parameter. The integer
  number of attackers is derived deterministically as the nearest admissible integer
  to f_A*N_V, and the realized attacker fraction is retained as a diagnostic.
* For H=0, equal shares are assigned. For 0<H<1,
      alpha(m,H) = (1-H)/(m*H)
  is used for a symmetric Dirichlet allocation.

What the manuscript does NOT specify exactly
---------------------------------------------
The revised manuscript says that (i) candidate maximin Latin hypercubes were
compared using recorded seeds and (ii) the nested 64/48 subsets were selected for
space-filling coverage, but it does not state the number of candidate hypercubes
or the exact nested-subset selection algorithm. This script therefore makes those
choices explicit and records them in sample_metadata.json:
* --design-candidates controls how many candidate 120-point design LHS samples
  are compared (default 128).
* nested subsets are chosen by a deterministic greedy maximin rule. By default
  the initial 64-design subset is selected purely by the space-filling rule;
  --force-corners-in-initial-64 can reproduce the earlier implementation choice.
* the 32-condition overload "subset" is interpreted literally as a subset of the
  final 96 operational conditions, selected for space-filling coverage in the
  five non-demand coordinates; its sampled primary lambda is retained only for
  audit, while Eq. (16) supplies the separate design-dependent overload rate.
* duplicate design configurations created by integer conversion are replaced
  deterministically with newly sampled points instead of rejecting the whole
  candidate, matching the manuscript wording.

The manuscript table records master seed s0=1024, so this script now uses 1024 as
the default. Mean transaction size is still required because the supplied method
text leaves its executed value as [insert].

Example
-------
python generate_revised_configurations.py \
    --seed 123456 \
    --mean-tx-size-bytes 500 \
    --out generated_samples

Dependencies: numpy, pandas, scipy.

User-facing CSV naming
----------------------
All simulation-input CSVs use descriptive parameter names close to the revised
manuscript: connection_count, block_creation_interval, maximum_block_size,
validating_node_count, node_bandwidth_heterogeneity,
link_bandwidth_heterogeneity, hashing_power_concentration,
fraction_of_attackers, and transaction_arrival_rate.
"""

from __future__ import annotations

import argparse
import hashlib
import itertools
import json
import math
from collections import Counter, deque
from dataclasses import asdict, dataclass
from pathlib import Path
from typing import Iterable, Sequence

import numpy as np
import pandas as pd
from scipy.spatial.distance import pdist
from scipy.stats import qmc


# -------------------------------
# Manuscript-defined ranges/sizes
# -------------------------------
C_MIN, C_MAX = 1, 8
BCI_MIN, BCI_MAX = 60, 1200  # seconds
MBS_MIN, MBS_MAX = 0.25, 8.0  # MB

NV_MIN, NV_MAX = 20, 1000
HNODE_MIN, HNODE_MAX = 0.0, 0.50
HLINK_MIN, HLINK_MAX = 0.0, 0.40
HHASH_MIN, HHASH_MAX = 0.0, 0.50
FA_MIN, FA_MAX = 0.0, 0.25

N_DESIGN_LHS = 120
N_DESIGN_FINAL = 128
N_DESIGN_INITIAL = 64
N_OPERATIONAL_FINAL = 96
N_OPERATIONAL_INITIAL = 48
N_SENSITIVITY = 32


@dataclass(frozen=True)
class ScriptChoices:
    """Implementation choices that are not fixed numerically by the manuscript."""

    design_candidate_count: int
    nested_subset_method: str = "greedy maximin in manuscript sampling coordinates"
    design_initial_forces_all_8_corners: bool = False
    sensitivity_interpretation: str = "greedy maximin 32-point subset of operational_96 using the five non-demand coordinates"
    mb_bytes: int = 1_000_000
    mc_z: float = 6.0
    mc_abs_tol: float = 0.01



# ----------------------------------------------------------
# User-facing CSV parameter names (close to the manuscript)
# ----------------------------------------------------------
#
# The implementation keeps short internal symbols such as C, NV, H_node, etc.
# because those names map directly to the manuscript equations. However, all
# CSV files intended for experiment setup use the descriptive names below.
#
# Sampled design parameters:
#   C       -> connection_count
#   BCI     -> block_creation_interval
#   MBS     -> maximum_block_size
#
# Sampled operational parameters:
#   N_V     -> validating_node_count
#   H_node  -> node_bandwidth_heterogeneity
#   H_link  -> link_bandwidth_heterogeneity
#   H_hash  -> hashing_power_concentration
#   f_A     -> fraction_of_attackers
#   lambda  -> transaction_arrival_rate
#
# Derived quantities (Dirichlet alphas, allocation dimensions, topology
# feasibility, etc.) are kept out of the main simulation-input CSV.

PARAMETER_CSV_NAMES = {
    "C": "connection_count",
    "BCI_s": "block_creation_interval",
    "MBS_MB": "maximum_block_size",
    "NV": "validating_node_count",
    "H_node": "node_bandwidth_heterogeneity",
    "H_link": "link_bandwidth_heterogeneity",
    "H_hash": "hashing_power_concentration",
    "f_A": "fraction_of_attackers",
    "lambda_tx_per_s": "transaction_arrival_rate",
}

DERIVED_CSV_NAMES = {
    "m_node": "node_bandwidth_allocation_dimension",
    "m_hash": "hashing_power_allocation_dimension",
    "m_link": "link_bandwidth_allocation_dimension",
    "alpha_node": "node_bandwidth_dirichlet_alpha",
    "alpha_hash": "hashing_power_dirichlet_alpha",
    "alpha_link": "link_bandwidth_dirichlet_alpha",
    "m_eff_node": "effective_node_bandwidth_recipient_count",
    "m_eff_hash": "effective_hashing_power_recipient_count",
    "m_eff_link": "effective_link_bandwidth_recipient_count",
    "aggregate_block_rate_per_s": "aggregate_block_production_rate",
    "theoretical_capacity_tx_per_s": "theoretical_transaction_capacity",
    "primary_lambda_tx_per_s_audit": "primary_transaction_arrival_rate_audit",
    "N_A": "number_of_attackers",
    "f_A_realized": "realized_fraction_of_attackers",
}

PARAMETER_UNITS = {
    "connection_count": "connections per validating node initiated and accepted",
    "block_creation_interval": "seconds",
    "maximum_block_size": "MB",
    "validating_node_count": "validating nodes",
    "node_bandwidth_heterogeneity": "normalized concentration",
    "link_bandwidth_heterogeneity": "normalized concentration",
    "hashing_power_concentration": "normalized concentration",
    "fraction_of_attackers": "fraction",
    "transaction_arrival_rate": "transactions per second",
    "number_of_attackers": "validating nodes (derived)",
    "realized_fraction_of_attackers": "fraction (derived diagnostic)",
}


def _descriptive_csv_names(df: pd.DataFrame, *, include_derived: bool = False) -> pd.DataFrame:
    """Return a copy with manuscript-like, human-readable CSV column names."""
    mapping = dict(PARAMETER_CSV_NAMES)
    if include_derived:
        mapping.update(DERIVED_CSV_NAMES)
    return df.rename(columns=mapping).copy()


def _design_csv(design: pd.DataFrame) -> pd.DataFrame:
    """Final design sample with clearly named sampled design parameters."""
    cols = ["design_id", "C", "BCI_s", "MBS_MB", "source", "is_boundary"]
    return _descriptive_csv_names(design[cols])


def _operational_csv(operational: pd.DataFrame) -> pd.DataFrame:
    """Final operational sample with clearly named sampled operational parameters."""
    cols = [
        "operational_id",
        "NV", "H_node", "H_link", "H_hash", "f_A", "lambda_tx_per_s",
    ]
    return _descriptive_csv_names(operational[cols])


def _primary_simulation_csv(primary_pairs: pd.DataFrame) -> pd.DataFrame:
    """Main simulation input: sampled parameters plus derived attacker count.

    ``f_A`` remains the sampled operational parameter. ``N_A`` is an integer
    simulator input derived from ``f_A`` and ``N_V``; ``f_A_realized`` is kept
    so that rounding effects are explicit and auditable.
    """
    cols = [
        "design_id", "operational_id",
        "C", "BCI_s", "MBS_MB",
        "NV", "H_node", "H_link", "H_hash", "f_A",
        "N_A", "f_A_realized", "lambda_tx_per_s",
    ]
    return _descriptive_csv_names(primary_pairs[cols], include_derived=True)


def _overload_simulation_csv(overload_pairs: pd.DataFrame) -> pd.DataFrame:
    """Separate 105%-load simulation input with derived attacker count."""
    cols = [
        "design_id", "sensitivity_id", "source_operational_id",
        "C", "BCI_s", "MBS_MB",
        "NV", "H_node", "H_link", "H_hash", "f_A",
        "N_A", "f_A_realized", "lambda_tx_per_s",
    ]
    out = _descriptive_csv_names(overload_pairs[cols], include_derived=True)
    return out.rename(columns={"sensitivity_id": "overload_condition_id"})


def _reference_simulation_csv(reference_pairs: pd.DataFrame) -> pd.DataFrame:
    """Homogeneous reference crossed with all designs, including derived attackers."""
    cols = [
        "design_id", "operational_id",
        "C", "BCI_s", "MBS_MB",
        "NV", "H_node", "H_link", "H_hash", "f_A",
        "N_A", "f_A_realized", "lambda_tx_per_s",
    ]
    return _descriptive_csv_names(reference_pairs[cols], include_derived=True)


# -------------------------------
# Utility functions
# -------------------------------

def _log_interp(u: np.ndarray, lo: float, hi: float) -> np.ndarray:
    return np.exp(np.log(lo) + u * (np.log(hi) - np.log(lo)))


def _scale_linear(x: np.ndarray, lo: float, hi: float) -> np.ndarray:
    return (x - lo) / (hi - lo)


def _scale_log(x: np.ndarray, lo: float, hi: float) -> np.ndarray:
    return (np.log(x) - np.log(lo)) / (np.log(hi) - np.log(lo))


def _derived_seed(master_seed: int, label: str, index: int = 0) -> int:
    """Stable 64-bit seed derived from a master seed and text label."""
    payload = f"{master_seed}|{label}|{index}".encode("utf-8")
    digest = hashlib.blake2b(payload, digest_size=8).digest()
    return int.from_bytes(digest, "little", signed=False)


def _lhs(n: int, d: int, seed: int) -> np.ndarray:
    # Plain scrambled LHS. We do not use scipy's discrepancy optimization because
    # the manuscript specifically describes candidate selection for the design LHS.
    return qmc.LatinHypercube(d=d, scramble=True, seed=seed).random(n=n)


def _min_distance(coords: np.ndarray) -> float:
    if len(coords) < 2:
        return math.inf
    return float(np.min(pdist(coords, metric="euclidean")))


def _unique_rows(df: pd.DataFrame, cols: Sequence[str]) -> bool:
    return not df.duplicated(subset=list(cols)).any()


def _round_half_away_nonnegative(x: np.ndarray | float) -> np.ndarray | int:
    """round(x) for nonnegative values with .5 rounded upward.

    Python/NumPy use bankers' rounding. The manuscript only says 'rounding'; this
    helper makes the rule explicit and stable for positive counts/times.
    """
    arr = np.asarray(x)
    out = np.floor(arr + 0.5).astype(int)
    if np.ndim(x) == 0:
        return int(out)
    return out


def normalized_concentration(shares: np.ndarray) -> float:
    """Equation (3): normalized concentration H(p)."""
    p = np.asarray(shares, dtype=float)
    m = p.size
    if m < 2:
        raise ValueError("Normalized concentration requires allocation dimension m >= 2.")
    if np.any(p < -1e-15):
        raise ValueError("Shares must be nonnegative.")
    s = p.sum()
    if not np.isclose(s, 1.0, rtol=1e-10, atol=1e-12):
        raise ValueError(f"Shares must sum to one; got {s}.")
    return float((m * np.sum(p * p) - 1.0) / (m - 1.0))


def alpha_from_h(m: int, h: float) -> float | None:
    """Equation (4). Returns None for H=0, which is handled by equal shares."""
    if m < 2:
        raise ValueError(f"Allocation dimension must be >=2; got m={m}.")
    if not (0.0 <= h < 1.0):
        raise ValueError(f"H must satisfy 0 <= H < 1; got H={h}.")
    if h == 0.0:
        return None
    return (1.0 - h) / (m * h)


def effective_recipient_count(m: int, h: float) -> float:
    """Equation (14): effective number of equally resourced recipients."""
    if m < 1:
        raise ValueError("m must be positive.")
    if not (0.0 <= h <= 1.0):
        raise ValueError("H must lie in [0,1].")
    return m / (1.0 + (m - 1.0) * h)


def draw_shares(m: int, h: float, rng: np.random.Generator, size: int | None = None) -> np.ndarray:
    """Translate a target H into equal or symmetric-Dirichlet shares."""
    alpha = alpha_from_h(m, h)
    if alpha is None:
        if size is None:
            return np.full(m, 1.0 / m)
        return np.full((size, m), 1.0 / m)
    return rng.dirichlet(np.full(m, alpha, dtype=float), size=size)


# -------------------------------
# Sampling transforms
# -------------------------------

def transform_design_lhs(u: np.ndarray) -> pd.DataFrame:
    if u.shape[1] != 3:
        raise ValueError("Design LHS must have 3 dimensions.")
    c = np.floor(u[:, 0] * (C_MAX - C_MIN + 1)).astype(int) + C_MIN
    c = np.clip(c, C_MIN, C_MAX)
    bci = _round_half_away_nonnegative(_log_interp(u[:, 1], BCI_MIN, BCI_MAX))
    bci = np.clip(bci, BCI_MIN, BCI_MAX)
    mbs = _log_interp(u[:, 2], MBS_MIN, MBS_MAX)
    return pd.DataFrame({"C": c, "BCI_s": bci, "MBS_MB": mbs})


def design_sampling_coords(df: pd.DataFrame) -> np.ndarray:
    return np.column_stack(
        [
            _scale_linear(df["C"].to_numpy(float), C_MIN, C_MAX),
            _scale_log(df["BCI_s"].to_numpy(float), BCI_MIN, BCI_MAX),
            _scale_log(df["MBS_MB"].to_numpy(float), MBS_MIN, MBS_MAX),
        ]
    )


def design_corners() -> pd.DataFrame:
    rows = [
        {"C": c, "BCI_s": bci, "MBS_MB": mbs, "source": "boundary"}
        for c, bci, mbs in itertools.product(
            (C_MIN, C_MAX), (BCI_MIN, BCI_MAX), (MBS_MIN, MBS_MAX)
        )
    ]
    return pd.DataFrame(rows)


def _replace_duplicate_design_rows(
    df: pd.DataFrame,
    master_seed: int,
    candidate_id: int,
    forbidden: set[tuple[int, int, float]] | None = None,
) -> tuple[pd.DataFrame, int]:
    """Deterministically replace duplicate/transformation-colliding design rows.

    Replacement points are generated from independent one-point scrambled LHS draws
    using derived seeds. This implements the manuscript statement that duplicates
    created by integer conversion are replaced, while keeping the procedure fully
    reproducible and auditable.
    """
    out = df.copy().reset_index(drop=True)
    forbidden = set() if forbidden is None else set(forbidden)
    seen: set[tuple[int, int, float]] = set()
    replacements = 0
    for i in range(len(out)):
        key = (int(out.at[i, "C"]), int(out.at[i, "BCI_s"]), float(out.at[i, "MBS_MB"]))
        attempt = 0
        while key in seen or key in forbidden:
            seed = _derived_seed(master_seed, f"design_duplicate_replacement_{candidate_id}_{i}", attempt)
            repl = transform_design_lhs(_lhs(1, 3, seed)).iloc[0]
            key = (int(repl["C"]), int(repl["BCI_s"]), float(repl["MBS_MB"]))
            attempt += 1
            if attempt > 10000:
                raise RuntimeError("Could not generate a unique replacement design point.")
        if attempt:
            out.loc[i, ["C", "BCI_s", "MBS_MB"]] = [key[0], key[1], key[2]]
            replacements += 1
        seen.add(key)
    out["C"] = out["C"].astype(int)
    out["BCI_s"] = out["BCI_s"].astype(int)
    return out, replacements


def generate_design_sample(master_seed: int, n_candidates: int) -> tuple[pd.DataFrame, pd.DataFrame, dict]:
    """Generate 120 candidate-LHS points, select maximin candidate, add 8 corners."""
    if n_candidates < 1:
        raise ValueError("design candidate count must be >= 1")

    all_candidates: list[pd.DataFrame] = []
    scores: list[dict] = []
    best_df: pd.DataFrame | None = None
    best_score = -math.inf
    best_seed = None
    best_id = None

    for candidate_id in range(n_candidates):
        seed = _derived_seed(master_seed, "design_lhs_candidate", candidate_id)
        raw = _lhs(N_DESIGN_LHS, 3, seed)
        df = transform_design_lhs(raw)
        corner_keys = set(
            (int(r.C), int(r.BCI_s), float(r.MBS_MB))
            for r in design_corners().itertuples(index=False)
        )
        df, replacement_count = _replace_duplicate_design_rows(
            df, master_seed, candidate_id, forbidden=corner_keys
        )
        unique = _unique_rows(df, ["C", "BCI_s", "MBS_MB"])
        score = _min_distance(design_sampling_coords(df))

        candidate_copy = df.copy()
        candidate_copy.insert(0, "candidate_id", candidate_id)
        candidate_copy.insert(1, "candidate_seed", seed)
        candidate_copy.insert(2, "candidate_min_distance", score)
        candidate_copy.insert(3, "candidate_unique", unique)
        candidate_copy.insert(4, "duplicate_replacements", replacement_count)
        candidate_copy.insert(5, "candidate_row", np.arange(1, len(candidate_copy) + 1))
        all_candidates.append(candidate_copy)
        scores.append(
            {
                "candidate_id": candidate_id,
                "candidate_seed": seed,
                "candidate_min_distance": score,
                "candidate_unique": bool(unique),
                "duplicate_replacements": int(replacement_count),
            }
        )

        if score > best_score:
            best_score = score
            best_seed = seed
            best_id = candidate_id
            best_df = df.copy()

    if best_df is None:
        raise RuntimeError("No valid 120-point design LHS candidate was generated.")

    best_df["source"] = "lhs"
    final = pd.concat([best_df, design_corners()], ignore_index=True)
    if len(final) != N_DESIGN_FINAL:
        raise AssertionError("Design sample size mismatch after adding corners.")
    if not _unique_rows(final, ["C", "BCI_s", "MBS_MB"]):
        raise AssertionError("Duplicate-replacement logic failed to keep design points distinct from boundaries.")

    final.insert(0, "design_id", [f"D{i:03d}" for i in range(1, len(final) + 1)])
    final["is_boundary"] = final["source"].eq("boundary")

    candidates = pd.concat(all_candidates, ignore_index=True)
    score_df = pd.DataFrame(scores).sort_values("candidate_min_distance", ascending=False)
    metadata = {
        "selected_candidate_id": int(best_id),
        "selected_candidate_seed": int(best_seed),
        "selected_candidate_min_distance": float(best_score),
        "duplicate_handling": "deterministic replacement after integer conversion; boundary collisions also replaced",
    }
    return final, candidates, {**metadata, "candidate_scores": score_df.to_dict(orient="records")}


def transaction_rate_bounds(mean_tx_size_mb: float) -> tuple[float, float]:
    """Equation (15)."""
    if mean_tx_size_mb <= 0:
        raise ValueError("mean transaction size must be > 0")
    lambda_min = 0.5 * MBS_MIN / (BCI_MAX * mean_tx_size_mb)
    lambda_max = 1.5 * MBS_MAX / (BCI_MIN * mean_tx_size_mb)
    return lambda_min, lambda_max


def transform_operational_lhs(u: np.ndarray, lambda_min: float, lambda_max: float) -> pd.DataFrame:
    if u.shape[1] != 6:
        raise ValueError("Operational LHS must have 6 dimensions.")
    nv = _round_half_away_nonnegative(_log_interp(u[:, 0], NV_MIN, NV_MAX))
    nv = np.clip(nv, NV_MIN, NV_MAX)
    return pd.DataFrame(
        {
            "NV": nv,
            "H_node": HNODE_MIN + u[:, 1] * (HNODE_MAX - HNODE_MIN),
            "H_link": HLINK_MIN + u[:, 2] * (HLINK_MAX - HLINK_MIN),
            "H_hash": HHASH_MIN + u[:, 3] * (HHASH_MAX - HHASH_MIN),
            "f_A": FA_MIN + u[:, 4] * (FA_MAX - FA_MIN),
            "lambda_tx_per_s": _log_interp(u[:, 5], lambda_min, lambda_max),
        }
    )


def operational_sampling_coords(df: pd.DataFrame, lambda_min: float, lambda_max: float) -> np.ndarray:
    return np.column_stack(
        [
            _scale_log(df["NV"].to_numpy(float), NV_MIN, NV_MAX),
            _scale_linear(df["H_node"].to_numpy(float), HNODE_MIN, HNODE_MAX),
            _scale_linear(df["H_link"].to_numpy(float), HLINK_MIN, HLINK_MAX),
            _scale_linear(df["H_hash"].to_numpy(float), HHASH_MIN, HHASH_MAX),
            _scale_linear(df["f_A"].to_numpy(float), FA_MIN, FA_MAX),
            _scale_log(df["lambda_tx_per_s"].to_numpy(float), lambda_min, lambda_max),
        ]
    )


def generate_operational_sample(master_seed: int, lambda_min: float, lambda_max: float) -> tuple[pd.DataFrame, dict]:
    seed = _derived_seed(master_seed, "operational_lhs", 0)
    u = _lhs(N_OPERATIONAL_FINAL, 6, seed)
    df = transform_operational_lhs(u, lambda_min, lambda_max)
    if not _unique_rows(df, ["NV", "H_node", "H_link", "H_hash", "f_A", "lambda_tx_per_s"]):
        raise RuntimeError("Operational LHS contains a full duplicate after integer conversion.")
    df.insert(0, "operational_id", [f"O{i:03d}" for i in range(1, len(df) + 1)])
    return df, {"operational_lhs_seed": int(seed)}


def homogeneous_reference(lambda_min: float, lambda_max: float) -> pd.DataFrame:
    nv_mid = _round_half_away_nonnegative(math.sqrt(NV_MIN * NV_MAX))
    lam_mid = math.sqrt(lambda_min * lambda_max)
    return pd.DataFrame(
        [
            {
                "operational_id": "OREF",
                "NV": nv_mid,
                "H_node": 0.0,
                "H_link": 0.0,
                "H_hash": 0.0,
                "f_A": 0.0,
                "lambda_tx_per_s": lam_mid,
                "reference": "homogeneous_geometric_midpoint",
            }
        ]
    )


def sensitivity_sampling_coords(df: pd.DataFrame) -> np.ndarray:
    return np.column_stack(
        [
            _scale_log(df["NV"].to_numpy(float), NV_MIN, NV_MAX),
            _scale_linear(df["H_node"].to_numpy(float), HNODE_MIN, HNODE_MAX),
            _scale_linear(df["H_link"].to_numpy(float), HLINK_MIN, HLINK_MAX),
            _scale_linear(df["H_hash"].to_numpy(float), HHASH_MIN, HHASH_MAX),
            _scale_linear(df["f_A"].to_numpy(float), FA_MIN, FA_MAX),
        ]
    )


# -------------------------------
# Nested space-filling subsets
# -------------------------------

def greedy_maximin_subset(
    coords: np.ndarray,
    k: int,
    forced_indices: Iterable[int] = (),
) -> list[int]:
    """Choose a deterministic nested space-filling subset by greedy maximin.

    This is an explicit implementation choice because the manuscript does not give
    the exact algorithm used to pick its nested 64/48 subsets.
    """
    n = len(coords)
    if not (1 <= k <= n):
        raise ValueError("k must satisfy 1 <= k <= n")

    selected: list[int] = []
    forced = sorted(set(int(i) for i in forced_indices))
    if any(i < 0 or i >= n for i in forced):
        raise IndexError("forced index out of range")
    if len(forced) > k:
        raise ValueError("more forced points than requested subset size")
    selected.extend(forced)

    if not selected:
        # Start with the pair farthest apart; ties resolved by lexicographic index.
        # For k=1, choose the point farthest from the geometric center.
        center = np.full(coords.shape[1], 0.5)
        if k == 1:
            d = np.linalg.norm(coords - center, axis=1)
            return [int(np.argmax(d))]
        best_pair = (0, 1)
        best_d = -1.0
        for i in range(n - 1):
            diff = coords[i + 1 :] - coords[i]
            ds = np.einsum("ij,ij->i", diff, diff)
            jrel = int(np.argmax(ds))
            d2 = float(ds[jrel])
            pair = (i, i + 1 + jrel)
            if d2 > best_d or (d2 == best_d and pair < best_pair):
                best_d = d2
                best_pair = pair
        selected.extend(best_pair)

    selected_set = set(selected)
    while len(selected) < k:
        remaining = np.array([i for i in range(n) if i not in selected_set], dtype=int)
        sel = coords[np.array(selected, dtype=int)]
        rem = coords[remaining]
        # Distance from each remaining point to its nearest selected point.
        diff = rem[:, None, :] - sel[None, :, :]
        d2 = np.sum(diff * diff, axis=2)
        nearest = np.min(d2, axis=1)
        # Deterministic tie break: first remaining index with maximal nearest distance.
        pick = int(remaining[int(np.argmax(nearest))])
        selected.append(pick)
        selected_set.add(pick)

    return selected


def nested_design_64(design: pd.DataFrame, *, force_corners: bool = False) -> pd.DataFrame:
    coords = design_sampling_coords(design)
    forced = np.flatnonzero(design["is_boundary"].to_numpy(bool)).tolist() if force_corners else []
    idx = greedy_maximin_subset(coords, N_DESIGN_INITIAL, forced_indices=forced)
    subset = design.iloc[idx].copy()
    subset.insert(1, "initial_stage_rank", np.arange(1, len(subset) + 1))
    return subset


def nested_operational_48(operational: pd.DataFrame, lambda_min: float, lambda_max: float) -> pd.DataFrame:
    coords = operational_sampling_coords(operational, lambda_min, lambda_max)
    idx = greedy_maximin_subset(coords, N_OPERATIONAL_INITIAL)
    subset = operational.iloc[idx].copy()
    subset.insert(1, "initial_stage_rank", np.arange(1, len(subset) + 1))
    return subset


def sensitivity_32_from_operational(operational: pd.DataFrame) -> pd.DataFrame:
    """Select the manuscript's 32-condition space-filling sensitivity subset.

    The manuscript calls this a 32-condition "subset" over the five non-demand
    operational coordinates but does not state the exact selector. We interpret
    "subset" literally as a subset of operational_96 and choose it with the same
    deterministic greedy-maximin rule used for the other nested subsets. The
    original primary-sample lambda is retained only as an audit column; Eq. (16)
    replaces it when the subset is crossed with each design.
    """
    coords = sensitivity_sampling_coords(operational)
    idx = greedy_maximin_subset(coords, N_SENSITIVITY)
    chosen = operational.iloc[idx].copy()
    out = pd.DataFrame(
        {
            "sensitivity_id": [f"S{i:03d}" for i in range(1, len(chosen) + 1)],
            "source_operational_id": chosen["operational_id"].to_numpy(),
            "NV": chosen["NV"].to_numpy(),
            "H_node": chosen["H_node"].to_numpy(),
            "H_link": chosen["H_link"].to_numpy(),
            "H_hash": chosen["H_hash"].to_numpy(),
            "f_A": chosen["f_A"].to_numpy(),
            "primary_lambda_tx_per_s_audit": chosen["lambda_tx_per_s"].to_numpy(),
        }
    )
    return out


# -------------------------------
# Topology construction/verification
# -------------------------------

def construct_topology(nv: int, c: int, seed: int) -> list[tuple[int, int]]:
    """Manuscript Section IV-C1 topology: random permutation + C forward initiations."""
    if c < 1:
        raise ValueError("C must be >=1")
    if 2 * c > nv - 1:
        raise ValueError(f"Infeasible topology: 2C={2*c} > NV-1={nv-1}")
    rng = np.random.default_rng(seed)
    sigma = rng.permutation(nv)
    edges: list[tuple[int, int]] = []
    for r in range(nv):
        u = int(sigma[r])
        for offset in range(1, c + 1):
            v = int(sigma[(r + offset) % nv])
            edges.append((u, v))
    return edges


def verify_topology(nv: int, c: int, edges: Sequence[tuple[int, int]]) -> dict:
    expected_edges = nv * c
    if len(edges) != expected_edges:
        return {"pass": False, "reason": f"edge_count={len(edges)} expected={expected_edges}"}

    self_edges = [(u, v) for u, v in edges if u == v]
    directed_unique = len(set(edges)) == len(edges)
    undirected = [tuple(sorted((u, v))) for u, v in edges]
    undirected_unique = len(set(undirected)) == len(undirected)

    out = Counter(u for u, _ in edges)
    inc = Counter(v for _, v in edges)
    degree_ok = all(out[i] == c and inc[i] == c for i in range(nv))

    adj = [[] for _ in range(nv)]
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)
    seen = {0}
    q = deque([0])
    while q:
        u = q.popleft()
        for v in adj[u]:
            if v not in seen:
                seen.add(v)
                q.append(v)
    connected = len(seen) == nv

    passed = (
        not self_edges
        and directed_unique
        and undirected_unique
        and degree_ok
        and connected
    )
    return {
        "pass": bool(passed),
        "self_connection_count": len(self_edges),
        "directed_unique": bool(directed_unique),
        "no_second_or_reciprocal_connection": bool(undirected_unique),
        "inbound_outbound_each_equal_C": bool(degree_ok),
        "connected": bool(connected),
        "directed_edge_count": len(edges),
    }


# -------------------------------
# Crossed configuration translation
# -------------------------------

def derive_attacker_count(nv: int, f_a: float) -> tuple[int, float]:
    """Derive integer attacker count from sampled attacker fraction.

    The sampled ``f_A`` remains the operational parameter. Because the simulator
    requires an integer number of adversarial validating nodes, we map ``f_A*N_V``
    to the nearest non-negative integer using the manuscript's explicit half-up
    rounding rule, then restrict the result to the admissible range
    ``0 <= N_A <= floor(FA_MAX*N_V)``. The realized fraction is reported as
    ``N_A/N_V`` and is diagnostic rather than a replacement for ``f_A``.
    """
    nv = int(nv)
    f_a = float(f_a)
    if nv < 1:
        raise ValueError(f"N_V must be positive; got {nv}.")
    if not (FA_MIN <= f_a <= FA_MAX):
        raise ValueError(f"f_A must lie in [{FA_MIN}, {FA_MAX}]; got {f_a}.")

    target = f_a * nv
    n_a = int(_round_half_away_nonnegative(target))
    max_attackers = int(math.floor(FA_MAX * nv + 1e-12))
    n_a = min(max(n_a, 0), max_attackers)
    realized = n_a / nv
    return n_a, realized

def add_translation_columns(pairs: pd.DataFrame) -> pd.DataFrame:
    out = pairs.copy()

    # f_A is sampled; N_A is derived for the simulator. Keep the realized
    # fraction explicit so rounding/clipping is transparent in later analyses.
    attacker_values = [
        derive_attacker_count(int(nv), float(f_a))
        for nv, f_a in zip(out["NV"], out["f_A"])
    ]
    out["N_A"] = [value[0] for value in attacker_values]
    out["f_A_realized"] = [value[1] for value in attacker_values]

    out["m_node"] = out["NV"].astype(int)
    out["m_hash"] = out["NV"].astype(int)
    out["m_link"] = 2 * out["C"].astype(int)

    def alpha_series(mcol: str, hcol: str) -> list[float]:
        vals = []
        for m, h in zip(out[mcol], out[hcol]):
            a = alpha_from_h(int(m), float(h))
            vals.append(np.nan if a is None else a)
        return vals

    out["alpha_node"] = alpha_series("m_node", "H_node")
    out["alpha_hash"] = alpha_series("m_hash", "H_hash")
    out["alpha_link"] = alpha_series("m_link", "H_link")

    out["m_eff_node"] = [effective_recipient_count(int(m), float(h)) for m, h in zip(out["m_node"], out["H_node"])]
    out["m_eff_hash"] = [effective_recipient_count(int(m), float(h)) for m, h in zip(out["m_hash"], out["H_hash"])]
    out["m_eff_link"] = [effective_recipient_count(int(m), float(h)) for m, h in zip(out["m_link"], out["H_link"])]

    out["topology_feasible"] = (2 * out["C"] <= out["NV"] - 1)
    out["aggregate_block_rate_per_s"] = 1.0 / out["BCI_s"]
    return out


def cross_design_operational(design: pd.DataFrame, operational: pd.DataFrame) -> pd.DataFrame:
    """Primary experiment: cross every design with the SAME absolute-demand conditions.

    lambda_tx_per_s comes only from the operational sample and is never recomputed
    from a design's MBS/BCI. This is what makes design comparisons valid under a
    common operating condition.
    """
    d = design.assign(_k=1)
    o = operational.assign(_k=1)
    pairs = d.merge(o, on="_k", how="inner").drop(columns="_k")
    pairs["experiment_type"] = "primary_common_absolute_demand"
    pairs["lambda_definition"] = "sampled operational parameter; independent of design MBS/BCI"
    return add_translation_columns(pairs)


def cross_sensitivity(
    design: pd.DataFrame,
    sensitivity: pd.DataFrame,
    mean_tx_size_mb: float,
) -> pd.DataFrame:
    d = design.assign(_k=1)
    s = sensitivity.assign(_k=1)
    pairs = d.merge(s, on="_k", how="inner").drop(columns="_k")
    pairs["lambda_tx_per_s"] = 1.05 * pairs["MBS_MB"] / (pairs["BCI_s"] * mean_tx_size_mb)
    pairs["experiment_type"] = "separate_105pct_overload_analysis"
    pairs["lambda_definition"] = "1.05 * MBS / (BCI * mean_tx_size); design-dependent"
    pairs["theoretical_capacity_tx_per_s"] = pairs["MBS_MB"] / (pairs["BCI_s"] * mean_tx_size_mb)
    pairs["nominal_load_fraction"] = pairs["lambda_tx_per_s"] / pairs["theoretical_capacity_tx_per_s"]
    return add_translation_columns(pairs)


# -------------------------------
# Verification
# -------------------------------

def _mc_check_h(m: int, h: float, draws: int, seed: int, z: float, abs_tol: float) -> dict:
    if m < 2:
        return {"pass": False, "m": m, "H_target": h, "reason": "m<2"}
    if h == 0.0:
        shares = np.full(m, 1.0 / m)
        realized = normalized_concentration(shares)
        return {
            "pass": bool(np.isclose(realized, 0.0, atol=1e-14)),
            "m": m,
            "H_target": h,
            "H_mean": realized,
            "H_se": 0.0,
            "tolerance": 1e-14,
            "draws": 1,
        }

    alpha = alpha_from_h(m, h)
    assert alpha is not None
    analytic_h = 1.0 / (m * alpha + 1.0)
    analytic_ok = math.isclose(analytic_h, h, rel_tol=1e-12, abs_tol=1e-12)

    rng = np.random.default_rng(seed)
    x = rng.dirichlet(np.full(m, alpha), size=draws)
    hvals = (m * np.sum(x * x, axis=1) - 1.0) / (m - 1.0)
    mean = float(np.mean(hvals))
    sd = float(np.std(hvals, ddof=1)) if draws > 1 else 0.0
    se = sd / math.sqrt(draws) if draws > 1 else 0.0
    tolerance = max(abs_tol, z * se)
    mc_ok = abs(mean - h) <= tolerance
    return {
        "pass": bool(analytic_ok and mc_ok),
        "m": int(m),
        "H_target": float(h),
        "alpha": float(alpha),
        "H_analytic": float(analytic_h),
        "analytic_pass": bool(analytic_ok),
        "H_mean": mean,
        "H_se": se,
        "tolerance": tolerance,
        "draws": int(draws),
        "monte_carlo_pass": bool(mc_ok),
    }


def verify_resource_translation(
    operational: pd.DataFrame,
    design: pd.DataFrame,
    master_seed: int,
    b_endpoint: float,
) -> dict:
    """One structural allocation smoke test for each operational condition and C value."""
    if b_endpoint <= 0:
        raise ValueError("b_endpoint must be >0")

    c_values = sorted(set(int(x) for x in design["C"]))
    failures: list[dict] = []
    checked = 0

    for op_row in operational.itertuples(index=False):
        nv = int(op_row.NV)
        hnode = float(op_row.H_node)
        hlink = float(op_row.H_link)
        hhash = float(op_row.H_hash)
        op_id = str(op_row.operational_id)

        # Node bandwidth and hashing power depend on N_V, not C.
        rng_node = np.random.default_rng(_derived_seed(master_seed, f"verify_node_{op_id}"))
        node_shares = draw_shares(nv, hnode, rng_node)
        rng_hash = np.random.default_rng(_derived_seed(master_seed, f"verify_hash_{op_id}"))
        hash_shares = draw_shares(nv, hhash, rng_hash)
        if not np.isclose(node_shares.sum(), 1.0, atol=1e-12):
            failures.append({"operational_id": op_id, "kind": "node_shares_sum"})
        if not np.isclose(hash_shares.sum(), 1.0, atol=1e-12):
            failures.append({"operational_id": op_id, "kind": "hash_shares_sum"})

        for c in c_values:
            checked += 1
            if 2 * c > nv - 1:
                failures.append({"operational_id": op_id, "C": c, "kind": "topology_infeasible"})
                continue
            b_total = 2.0 * b_endpoint * nv * c
            node_bw = node_shares * b_total
            if not np.isclose(node_bw.sum(), b_total, rtol=1e-12, atol=1e-10):
                failures.append({"operational_id": op_id, "C": c, "kind": "bandwidth_budget_sum"})

            # Every node has the same H_link target and dimension 2C. Verify every
            # node's endpoint allocation sums back to that node's bandwidth.
            rng_link = np.random.default_rng(_derived_seed(master_seed, f"verify_link_{op_id}_C{c}"))
            m = 2 * c
            alpha = alpha_from_h(m, hlink)
            if alpha is None:
                link_shares = np.full((nv, m), 1.0 / m)
            else:
                link_shares = rng_link.dirichlet(np.full(m, alpha), size=nv)
            endpoint_bw = link_shares * node_bw[:, None]
            row_sums = endpoint_bw.sum(axis=1)
            if not np.allclose(row_sums, node_bw, rtol=1e-11, atol=1e-9):
                failures.append({"operational_id": op_id, "C": c, "kind": "link_bandwidth_row_sum"})

    return {"pass": not failures, "checked_operational_C_combinations": checked, "failures": failures}


def verify_all(
    design: pd.DataFrame,
    design64: pd.DataFrame,
    operational: pd.DataFrame,
    operational48: pd.DataFrame,
    reference: pd.DataFrame,
    sensitivity: pd.DataFrame,
    primary_pairs: pd.DataFrame,
    sensitivity_pairs: pd.DataFrame,
    master_seed: int,
    mc_draws: int,
    choices: ScriptChoices,
    b_endpoint: float,
) -> dict:
    report: dict = {"checks": {}}
    checks = report["checks"]

    # Counts, uniqueness, ranges, nesting.
    checks["counts"] = {
        "pass": all(
            [
                len(design) == N_DESIGN_FINAL,
                len(design64) == N_DESIGN_INITIAL,
                len(operational) == N_OPERATIONAL_FINAL,
                len(operational48) == N_OPERATIONAL_INITIAL,
                len(reference) == 1,
                len(sensitivity) == N_SENSITIVITY,
                len(primary_pairs) == N_DESIGN_FINAL * N_OPERATIONAL_FINAL,
                len(sensitivity_pairs) == N_DESIGN_FINAL * N_SENSITIVITY,
            ]
        ),
        "design_final": len(design),
        "design_initial": len(design64),
        "operational_final": len(operational),
        "operational_initial": len(operational48),
        "reference": len(reference),
        "sensitivity": len(sensitivity),
        "primary_pairs": len(primary_pairs),
        "sensitivity_pairs": len(sensitivity_pairs),
    }

    checks["uniqueness"] = {
        "pass": all(
            [
                design["design_id"].is_unique,
                operational["operational_id"].is_unique,
                sensitivity["sensitivity_id"].is_unique,
                sensitivity["source_operational_id"].is_unique,
                _unique_rows(design, ["C", "BCI_s", "MBS_MB"]),
                _unique_rows(operational, ["NV", "H_node", "H_link", "H_hash", "f_A", "lambda_tx_per_s"]),
            ]
        )
    }

    design_ids = set(design["design_id"])
    op_ids = set(operational["operational_id"])
    checks["nested_membership"] = {
        "pass": set(design64["design_id"]).issubset(design_ids)
        and set(operational48["operational_id"]).issubset(op_ids)
        and set(sensitivity["source_operational_id"]).issubset(op_ids),
        "all_8_design_boundaries_in_initial_64": bool(design64["is_boundary"].sum() == 8),
        "corners_forced_by_script_choice": bool(choices.design_initial_forces_all_8_corners),
        "sensitivity_32_is_subset_of_operational_96": bool(set(sensitivity["source_operational_id"]).issubset(op_ids)),
    }
    if choices.design_initial_forces_all_8_corners:
        checks["nested_membership"]["pass"] = bool(
            checks["nested_membership"]["pass"]
            and checks["nested_membership"]["all_8_design_boundaries_in_initial_64"]
        )

    corners = set(
        (int(c), int(bci), float(mbs))
        for c, bci, mbs in itertools.product(
            (C_MIN, C_MAX), (BCI_MIN, BCI_MAX), (MBS_MIN, MBS_MAX)
        )
    )
    actual_boundaries = set(
        (int(r.C), int(r.BCI_s), float(r.MBS_MB))
        for r in design.loc[design["is_boundary"]].itertuples(index=False)
    )
    checks["design_boundaries"] = {"pass": actual_boundaries == corners, "count": len(actual_boundaries)}

    range_ok = (
        design["C"].between(C_MIN, C_MAX).all()
        and design["BCI_s"].between(BCI_MIN, BCI_MAX).all()
        and design["MBS_MB"].between(MBS_MIN, MBS_MAX).all()
        and operational["NV"].between(NV_MIN, NV_MAX).all()
        and operational["H_node"].between(HNODE_MIN, HNODE_MAX).all()
        and operational["H_link"].between(HLINK_MIN, HLINK_MAX).all()
        and operational["H_hash"].between(HHASH_MIN, HHASH_MAX).all()
        and operational["f_A"].between(FA_MIN, FA_MAX).all()
        and sensitivity["NV"].between(NV_MIN, NV_MAX).all()
        and sensitivity["H_node"].between(HNODE_MIN, HNODE_MAX).all()
        and sensitivity["H_link"].between(HLINK_MIN, HLINK_MAX).all()
        and sensitivity["H_hash"].between(HHASH_MIN, HHASH_MAX).all()
        and sensitivity["f_A"].between(FA_MIN, FA_MAX).all()
    )
    checks["parameter_ranges"] = {"pass": bool(range_ok)}

    # Attacker count is derived from sampled f_A and N_V, never sampled independently.
    attacker_failures = []
    for table_name, table in [("primary", primary_pairs), ("sensitivity", sensitivity_pairs)]:
        for row in table.itertuples(index=False):
            expected_n, expected_realized = derive_attacker_count(int(row.NV), float(row.f_A))
            if int(row.N_A) != expected_n or not math.isclose(
                float(row.f_A_realized), expected_realized, rel_tol=0.0, abs_tol=1e-15
            ):
                if len(attacker_failures) < 20:
                    attacker_failures.append({
                        "table": table_name,
                        "NV": int(row.NV),
                        "f_A": float(row.f_A),
                        "N_A": int(row.N_A),
                        "expected_N_A": expected_n,
                        "f_A_realized": float(row.f_A_realized),
                        "expected_f_A_realized": expected_realized,
                    })
    checks["attacker_count_derivation"] = {
        "pass": not attacker_failures,
        "rule": "N_A = nearest admissible integer to f_A*N_V; f_A_realized=N_A/N_V; f_A remains sampled",
        "failures": attacker_failures,
    }

    # Every crossed primary pair must be topology-feasible and H-translatable.
    topology_cross_ok = bool(primary_pairs["topology_feasible"].all())
    sens_topology_cross_ok = bool(sensitivity_pairs["topology_feasible"].all())
    checks["all_crossed_pairs_topology_feasible"] = {
        "pass": topology_cross_ok and sens_topology_cross_ok,
        "primary_infeasible": int((~primary_pairs["topology_feasible"]).sum()),
        "sensitivity_infeasible": int((~sensitivity_pairs["topology_feasible"]).sum()),
        "global_sufficient_condition": f"2*C_max={2*C_MAX} <= NV_min-1={NV_MIN-1}",
    }

    # Explicit topology construction tests for every unique (C,NV) pair used in
    # primary + sensitivity samples. This directly checks the manuscript invariants.
    unique_nv = sorted(set(operational["NV"].astype(int)) | set(sensitivity["NV"].astype(int)) | {int(reference.iloc[0]["NV"])})
    unique_c = sorted(set(design["C"].astype(int)))
    topo_failures = []
    topo_checked = 0
    for nv in unique_nv:
        for c in unique_c:
            topo_checked += 1
            seed = _derived_seed(master_seed, f"topology_nv{nv}_c{c}")
            try:
                edges = construct_topology(nv, c, seed)
                result = verify_topology(nv, c, edges)
                if not result["pass"]:
                    topo_failures.append({"NV": nv, "C": c, **result})
            except Exception as exc:  # verification report should retain failure details
                topo_failures.append({"NV": nv, "C": c, "pass": False, "reason": repr(exc)})
    checks["topology_construction"] = {
        "pass": not topo_failures,
        "checked_unique_C_NV_combinations": topo_checked,
        "failures": topo_failures,
    }

    # Algebraic translation for every crossed pair.
    alpha_cols = ["alpha_node", "alpha_hash", "alpha_link"]
    finite_or_equal = True
    bad_translation_rows = []
    for table_name, table in [("primary", primary_pairs), ("sensitivity", sensitivity_pairs)]:
        for row in table.itertuples(index=False):
            vals = {
                "node": (int(row.m_node), float(row.H_node), row.alpha_node),
                "hash": (int(row.m_hash), float(row.H_hash), row.alpha_hash),
                "link": (int(row.m_link), float(row.H_link), row.alpha_link),
            }
            for kind, (m, h, a) in vals.items():
                ok = m >= 2 and 0 <= h < 1 and ((h == 0 and pd.isna(a)) or (h > 0 and np.isfinite(a) and a > 0))
                if not ok:
                    finite_or_equal = False
                    if len(bad_translation_rows) < 20:
                        bad_translation_rows.append({"table": table_name, "kind": kind, "m": m, "H": h, "alpha": None if pd.isna(a) else float(a)})
    checks["heterogeneity_translation_all_pairs"] = {
        "pass": bool(finite_or_equal),
        "bad_examples": bad_translation_rows,
        "rule": "m=N_V for H_node/H_hash; m=2C for H_link; H=0 equal shares; 0<H<1 uses alpha=(1-H)/(mH)",
    }

    # Resource sums. Include the homogeneous reference in this smoke test.
    op_with_ref = pd.concat([operational, reference[operational.columns]], ignore_index=True)
    checks["resource_allocation_sums"] = verify_resource_translation(
        operational=op_with_ref,
        design=design,
        master_seed=master_seed,
        b_endpoint=b_endpoint,
    )

    # Aggregate block rate check is algebraic once hashing shares sum to one.
    expected = 1.0 / primary_pairs["BCI_s"].to_numpy(float)
    checks["aggregate_block_rate"] = {
        "pass": bool(np.allclose(primary_pairs["aggregate_block_rate_per_s"], expected, rtol=0, atol=0)),
        "rule": "sum_i(pi_i/BCI) = 1/BCI whenever sum_i(pi_i)=1",
    }
    checks["transaction_rate_configuration"] = {
        "pass": bool((primary_pairs["lambda_tx_per_s"] > 0).all() and (sensitivity_pairs["lambda_tx_per_s"] > 0).all()),
        "note": "This generator verifies configured positive rates. Event-time Monte Carlo belongs in the simulator/event-engine test suite.",
    }

    # Supervisor-requested separation of the two demand treatments. In the primary
    # experiment, each operational_id must carry exactly one absolute lambda across
    # all 128 designs. In the separate overload analysis, lambda is recomputed from
    # each design's MBS/BCI and must equal 105% of nominal theoretical capacity.
    primary_lambda_counts = primary_pairs.groupby("operational_id")["lambda_tx_per_s"].nunique(dropna=False)
    primary_common_ok = bool((primary_lambda_counts == 1).all())
    overload_ratio_ok = bool(np.allclose(
        sensitivity_pairs["nominal_load_fraction"].to_numpy(float),
        1.05,
        rtol=1e-12,
        atol=1e-12,
    ))
    checks["demand_experiment_separation"] = {
        "pass": bool(primary_common_ok and overload_ratio_ok),
        "primary_common_absolute_demand_across_designs": primary_common_ok,
        "primary_operational_conditions_checked": int(len(primary_lambda_counts)),
        "overload_exactly_105pct_of_nominal_capacity": overload_ratio_ok,
        "interpretation": "Primary lambda_tx is an operational parameter; 105% load is a separate design-dependent overload analysis.",
    }

    # Repeated Dirichlet allocation verification. Test all unique translation
    # (dimension,target-H) pairs appearing in primary/reference/sensitivity data.
    translation_keys: set[tuple[int, float, str]] = set()
    combined_ops = pd.concat(
        [
            operational[["NV", "H_node", "H_link", "H_hash"]],
            reference[["NV", "H_node", "H_link", "H_hash"]],
            sensitivity[["NV", "H_node", "H_link", "H_hash"]],
        ],
        ignore_index=True,
    )
    for r in combined_ops.itertuples(index=False):
        translation_keys.add((int(r.NV), float(r.H_node), "node"))
        translation_keys.add((int(r.NV), float(r.H_hash), "hash"))
        for c in unique_c:
            translation_keys.add((2 * int(c), float(r.H_link), "link"))

    mc_results = []
    for idx, (m, h, kind) in enumerate(sorted(translation_keys, key=lambda x: (x[2], x[0], x[1]))):
        seed = _derived_seed(master_seed, f"mc_h_{kind}_{m}_{h:.17g}", idx)
        res = _mc_check_h(m, h, mc_draws, seed, choices.mc_z, choices.mc_abs_tol)
        res["kind"] = kind
        mc_results.append(res)
    mc_failures = [r for r in mc_results if not r["pass"]]
    checks["dirichlet_target_concentration"] = {
        "pass": not mc_failures,
        "unique_translation_cases": len(mc_results),
        "mc_draws_per_nonzero_case": mc_draws,
        "acceptance_rule": f"analytic identity must hold and |mean(H_realized)-H_target| <= max({choices.mc_abs_tol}, {choices.mc_z}*SE)",
        "failures": mc_failures[:50],
    }

    report["simulator_only_checks_not_implemented_here"] = [
        "Monte Carlo agreement of realized transaction-arrival/event timing with configured rates in the actual event engine",
        "fork-resolution behavior against the SM-SIM reference",
        "selfish-mining behavior against the SM-SIM reference",
        "held-out surrogate prediction error and learning-curve/sample-adequacy checks, which require simulation results",
    ]
    report["configuration_verification_pass"] = all(bool(v.get("pass", False)) for v in checks.values())
    report["pass"] = report["configuration_verification_pass"]  # backward-compatible alias
    report["method_coverage"] = {
        "implemented_here": [
            "parameter ranges and sampling transforms",
            "design/operational sample generation and crossing",
            "homogeneous reference and 105% overload configuration",
            "topology structural checks",
            "resource-share sum and Dirichlet-concentration checks",
        ],
        "requires_simulator_or_results": report["simulator_only_checks_not_implemented_here"] + [
            "R_S network-instance and R_E event-replication execution",
            "pilot selection of R_S/R_E and metric-specific Monte Carlo tolerances",
            "warm-up and measured canonical-chain block horizons",
            "transaction drain, confirmation tracking, and right-censoring",
            "adversarial hashing-power share per network instance",
            "fixed transaction/latency/validation/confirmation/selfish-mining settings used by the simulator",
        ],
    }
    return report


# -------------------------------
# Output
# -------------------------------

def _json_default(obj):
    if isinstance(obj, (np.integer,)):
        return int(obj)
    if isinstance(obj, (np.floating,)):
        return float(obj)
    if isinstance(obj, (np.bool_,)):
        return bool(obj)
    raise TypeError(type(obj).__name__)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--seed", type=int, default=1024, help="Master random seed s0 (default: 1024, matching the manuscript table).")
    parser.add_argument(
        "--mean-tx-size-bytes",
        type=float,
        default=None,
        help="Mean transaction size used by Eq. (15) and Eq. (16). If omitted, you will be prompted.",
    )
    parser.add_argument(
        "--mb-bytes",
        type=int,
        default=1_000_000,
        help="Bytes per MB used to put mean transaction size in the same units as MBS (default: 1,000,000).",
    )
    parser.add_argument("--out", type=Path, default=Path("generated_samples"), help="Output directory.")
    parser.add_argument(
        "--design-candidates",
        type=int,
        default=128,
        help="Number of candidate 120-point design LHS samples compared by minimum distance. Manuscript does not specify this count.",
    )
    parser.add_argument(
        "--force-corners-in-initial-64",
        action="store_true",
        help="Force all 8 design boundaries into the nested 64-design subset. This is an implementation choice, not required by the supplied method text.",
    )
    parser.add_argument(
        "--mc-draws",
        type=int,
        default=256,
        help="Dirichlet draws per nonzero (m,H) verification case (default: 256).",
    )
    parser.add_argument(
        "--b-endpoint",
        type=float,
        default=1.0,
        help="Baseline endpoint bandwidth used only to verify Eq. (5)-style budget sums. Scaling does not affect the invariant.",
    )
    args = parser.parse_args()

    # Mean transaction size remains interactive because the supplied table leaves it as [insert].
    if args.mean_tx_size_bytes is None:
        while True:
            raw = input("Mean transaction size in bytes (e.g. 500): ").strip()
            try:
                args.mean_tx_size_bytes = float(raw)
                if args.mean_tx_size_bytes > 0:
                    break
            except ValueError:
                pass
            print("Please enter a positive number of bytes.")

    if args.mean_tx_size_bytes <= 0:
        parser.error("--mean-tx-size-bytes must be > 0")
    if args.mb_bytes <= 0:
        parser.error("--mb-bytes must be > 0")
    if args.mc_draws < 20:
        parser.error("--mc-draws should be at least 20")

    out_dir: Path = args.out
    out_dir.mkdir(parents=True, exist_ok=True)

    choices = ScriptChoices(
        design_candidate_count=args.design_candidates,
        design_initial_forces_all_8_corners=bool(args.force_corners_in_initial_64),
        mb_bytes=args.mb_bytes,
    )
    mean_tx_size_mb = args.mean_tx_size_bytes / args.mb_bytes
    lambda_min, lambda_max = transaction_rate_bounds(mean_tx_size_mb)

    design, design_candidates, design_meta = generate_design_sample(args.seed, args.design_candidates)
    operational, operational_meta = generate_operational_sample(args.seed, lambda_min, lambda_max)
    reference = homogeneous_reference(lambda_min, lambda_max)
    design64 = nested_design_64(design, force_corners=args.force_corners_in_initial_64)
    operational48 = nested_operational_48(operational, lambda_min, lambda_max)
    sensitivity = sensitivity_32_from_operational(operational)

    primary_pairs = cross_design_operational(design, operational)
    reference_pairs = cross_design_operational(design, reference[operational.columns])
    sensitivity_pairs = cross_sensitivity(design, sensitivity, mean_tx_size_mb)

    # ------------------------------------------------------------------
    # MAIN USER-FACING CSV FILES
    # ------------------------------------------------------------------
    # These files use descriptive names close to Tables II and III of the
    # revised manuscript.
    #
    # Primary simulation experiment:
    #     simulation_primary_128x96.csv
    # Separate 105%-of-nominal-capacity overload analysis:
    #     simulation_overload_105pct_128x32.csv

    _design_csv(design).to_csv(
        out_dir / "design_configurations_128.csv", index=False
    )
    _operational_csv(operational).to_csv(
        out_dir / "operational_conditions_96.csv", index=False
    )
    _primary_simulation_csv(primary_pairs).to_csv(
        out_dir / "simulation_primary_128x96.csv", index=False
    )
    _overload_simulation_csv(sensitivity_pairs).to_csv(
        out_dir / "simulation_overload_105pct_128x32.csv", index=False
    )
    _reference_simulation_csv(reference_pairs).to_csv(
        out_dir / "homogeneous_reference_128x1.csv", index=False
    )

    # ------------------------------------------------------------------
    # AUDIT / REPLICATION FILES
    # ------------------------------------------------------------------
    # These contain nested subsets, LHS candidates, internal translations,
    # and verification details. They are separated from simulation inputs.
    audit_dir = out_dir / "audit"
    audit_dir.mkdir(parents=True, exist_ok=True)

    _descriptive_csv_names(design64).to_csv(
        audit_dir / "design_64_nested.csv", index=False
    )
    _descriptive_csv_names(operational48).to_csv(
        audit_dir / "operational_48_nested.csv", index=False
    )
    _descriptive_csv_names(reference).to_csv(
        audit_dir / "operational_reference_condition.csv", index=False
    )
    _descriptive_csv_names(sensitivity).to_csv(
        audit_dir / "overload_32_base_conditions.csv", index=False
    )

    _descriptive_csv_names(primary_pairs, include_derived=True).to_csv(
        audit_dir / "primary_128x96_detailed.csv", index=False
    )
    _descriptive_csv_names(reference_pairs, include_derived=True).to_csv(
        audit_dir / "homogeneous_reference_128x1_detailed.csv", index=False
    )
    _descriptive_csv_names(sensitivity_pairs, include_derived=True).to_csv(
        audit_dir / "overload_105pct_128x32_detailed.csv", index=False
    )

    _descriptive_csv_names(design_candidates).to_csv(
        audit_dir / "design_lhs_candidates.csv", index=False
    )
    pd.DataFrame(design_meta.pop("candidate_scores")).to_csv(
        audit_dir / "design_candidate_scores.csv", index=False
    )

    verification = verify_all(
        design=design,
        design64=design64,
        operational=operational,
        operational48=operational48,
        reference=reference,
        sensitivity=sensitivity,
        primary_pairs=primary_pairs,
        sensitivity_pairs=sensitivity_pairs,
        master_seed=args.seed,
        mc_draws=args.mc_draws,
        choices=choices,
        b_endpoint=args.b_endpoint,
    )

    metadata = {
        "master_seed": int(args.seed),
        "mean_tx_size_bytes": float(args.mean_tx_size_bytes),
        "mb_bytes": int(args.mb_bytes),
        "mean_tx_size_MB": float(mean_tx_size_mb),
        "lambda_min_per_s": float(lambda_min),
        "lambda_max_per_s": float(lambda_max),
        "csv_parameter_names": PARAMETER_CSV_NAMES,
        "csv_parameter_units": PARAMETER_UNITS,
        "design": design_meta,
        "operational": operational_meta,
        "primary_demand_treatment": {
            "lambda_tx": "sampled as a common absolute operational parameter, independent of each design",
            "comparison_rule": "every design is crossed with the same 96 operational conditions"
        },
        "sensitivity": {
            "source": "32-point subset of operational_96 selected in five non-demand coordinates",
            "demand_treatment": "separate 105%-of-theoretical-capacity overload analysis; lambda_tx depends on each design's MBS/BCI"
        },
        "script_choices_not_numerically_fixed_by_manuscript": asdict(choices),
        "counts": {
            "design_final": N_DESIGN_FINAL,
            "design_lhs": N_DESIGN_LHS,
            "design_boundaries": 8,
            "design_initial_nested": N_DESIGN_INITIAL,
            "operational_final": N_OPERATIONAL_FINAL,
            "operational_reference_separate": 1,
            "operational_initial_nested": N_OPERATIONAL_INITIAL,
            "sensitivity_conditions": N_SENSITIVITY,
        },
        "manuscript_feasibility_summary": {
            "topology": "2*C <= NV-1",
            "node_bandwidth_dimension": "m=NV",
            "hashing_power_dimension": "m=NV",
            "link_bandwidth_dimension": "m=2*C",
            "H_zero": "equal shares",
            "H_positive": "0<H<1 and alpha=(1-H)/(mH)",
            "attacker_count": "sample f_A; derive integer N_A from f_A*N_V; retain f_A_realized=N_A/N_V",
        },
    }

    with (audit_dir / "sample_metadata.json").open("w", encoding="utf-8") as f:
        json.dump(metadata, f, indent=2, default=_json_default)
    with (audit_dir / "verification_report.json").open("w", encoding="utf-8") as f:
        json.dump(verification, f, indent=2, default=_json_default)
    with (audit_dir / "method_coverage.json").open("w", encoding="utf-8") as f:
        json.dump(verification["method_coverage"], f, indent=2, default=_json_default)

    print(f"\nWrote samples to: {out_dir.resolve()}")
    print("\nMAIN FILE FOR THE PRIMARY SIMULATION EXPERIMENT:")
    print(f"  {out_dir / 'simulation_primary_128x96.csv'}")
    print("  -> 12,288 rows; each row contains one complete design + operational condition.")
    print("\nSEPARATE 105% OVERLOAD ANALYSIS:")
    print(f"  {out_dir / 'simulation_overload_105pct_128x32.csv'}")
    print("  -> 4,096 rows; use only for the separate overload analysis.")
    print("\nREFERENCE TABLES:")
    print(f"  {out_dir / 'design_configurations_128.csv'}")
    print(f"  {out_dir / 'operational_conditions_96.csv'}")
    print(f"  {out_dir / 'homogeneous_reference_128x1.csv'}")
    print("\nAUDIT / VERIFICATION MATERIAL:")
    print(f"  {audit_dir}")
    print("\nPARAMETER COLUMNS IN THE PRIMARY CSV:")
    print("  connection_count")
    print("  block_creation_interval")
    print("  maximum_block_size")
    print("  validating_node_count")
    print("  node_bandwidth_heterogeneity")
    print("  link_bandwidth_heterogeneity")
    print("  hashing_power_concentration")
    print("  fraction_of_attackers  [sampled]")
    print("  number_of_attackers  [derived from fraction_of_attackers x validating_node_count]")
    print("  realized_fraction_of_attackers  [derived diagnostic]")
    print("  transaction_arrival_rate")
    print("\nSUMMARY:")
    print(f"  Design: 120 LHS + 8 boundaries = {len(design)}")
    print(f"  Operational: {len(operational)} sampled + 1 separate homogeneous reference")
    print(f"  Nested initial stage: {len(design64)} x {len(operational48)}")
    print(f"  Primary crossed pairs: {len(primary_pairs)}")
    print(f"  Separate 105% overload pairs: {len(sensitivity_pairs)}")
    print(f"  Configuration-generation verification: {'PASS' if verification['configuration_verification_pass'] else 'FAIL'}")
    print("  Full simulation-method verification: NOT PERFORMED by this configuration generator")
    if not verification["configuration_verification_pass"]:
        print(f"See {audit_dir / 'verification_report.json'} for failed checks.")
        return 2
    return 0


if __name__ == "__main__":
    raise SystemExit(main())