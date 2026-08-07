#!/usr/bin/env python3
"""Two-level nested LHS using normalized-HHI heterogeneity factors.

Hnode, Hlink, and hashrate_concentration are experimental normalized-HHI
targets. They are not Dirichlet concentration parameters and are not interpreted
as CV-like quantities by this experimental-design script.

The heterogeneous factors are sampled from finite admissible grids. Feasibility
is established constructively: for every allocation dimension K >= 2 and every
target h in [0, 1), a non-negative share vector can be constructed that sums to
one and has normalized HHI exactly h. Thus the allocation preserves a fixed
resource budget while realizing the selected heterogeneity target.

The relevant minimum/maximum validator counts and peer counts are checked
explicitly. HHI levels are sampled without replacement within each inner LHS
when enough admissible values exist, and complete nine-parameter configurations
are checked for duplicates.
"""

from __future__ import annotations

import argparse
from pathlib import Path

import numpy as np
import pandas as pd
from scipy.stats import qmc


# ---------------------------------------------------------------------------
# 1. Experimental setup
# ---------------------------------------------------------------------------

DEFAULT_SEED = 42
DEFAULT_N_DESIGNS = 500
DEFAULT_N_OPERATIONAL_PER_DESIGN = 100
DEFAULT_N_REPLICATIONS = 500

LHS_OPTIMIZATION = "random-cd"

# Finite grid resolution. The narrowest heterogeneity interval (Hlink) then has
# 141 levels, which is enough to choose 100 values without replacement.
HETEROGENEITY_GRID_STEP = 0.0025

# 3SIM clips alpha to 0.001. We exclude targets that would trigger that clip,
# because clipping would make the target-to-alpha mapping non-injective.
THREESIM_ALPHA_FLOOR = 0.001


# ---------------------------------------------------------------------------
# 2. Parameter ranges
# ---------------------------------------------------------------------------

DESIGN_PARAM_RANGES: dict[str, tuple[float, float]] = {
    "block_creation_interval": (60, 1200),  # seconds
    "max_block_size": (0.25, 8.00),         # MB

    # Positive normalized-HHI link heterogeneity requires at least two links.
    # Therefore both inbound and outbound connectivity start at two.
    "inbound_connections": (2, 125),        # integer
    "outbound_connections": (2, 8),         # integer
}

OPERATIONAL_PARAM_RANGES: dict[str, tuple[float, float]] = {
    # Finite normalized-HHI target ranges.
    "Hnode": (0.05, 0.60),
    "Hlink": (0.05, 0.40),
    "hashrate_concentration": (0.05, 0.80),

    "attacker_fraction": (0.0, 0.25),
    "validator_count": (20, 1000),          # integer
}

INTEGER_DESIGN_PARAMS = {
    "block_creation_interval",
    "inbound_connections",
    "outbound_connections",
}

INTEGER_OPERATIONAL_PARAMS = {"validator_count"}

DISCRETE_HETEROGENEITY_PARAMS = {
    "Hnode",
    "Hlink",
    "hashrate_concentration",
}


# ---------------------------------------------------------------------------
# 3. Normalized-HHI feasibility and budget-preserving construction
# ---------------------------------------------------------------------------

def normalized_hhi(shares: np.ndarray) -> float:
    """Return normalized HHI for a non-negative share vector summing to one."""
    values = np.asarray(shares, dtype=float)

    if values.ndim != 1 or values.size < 1:
        raise ValueError("shares must be a non-empty one-dimensional vector.")
    if np.any(~np.isfinite(values)) or np.any(values < 0.0):
        raise ValueError("shares must be finite and non-negative.")

    if not np.isclose(values.sum(), 1.0, atol=1e-12, rtol=0.0):
        raise ValueError("shares must sum to one.")

    k = values.size
    if k == 1:
        return 0.0

    raw_hhi = float(np.square(values).sum())
    return (raw_hhi - 1.0 / k) / (1.0 - 1.0 / k)


def canonical_hhi_shares(dimension: int, target_h: float) -> np.ndarray:
    """Construct shares with exactly the requested normalized HHI.

    For K >= 2,

        p_1 = [1 + (K - 1) sqrt(h)] / K
        p_i = [1 - sqrt(h)] / K,  i = 2,...,K.

    These shares are non-negative, sum to one, and realize normalized HHI h.
    Hence every h in [0,1) is constructively feasible for every K >= 2.
    """
    if dimension < 1:
        raise ValueError("dimension must be positive.")
    if not 0.0 <= target_h < 1.0:
        raise ValueError("target_h must lie in [0, 1).")

    if dimension == 1:
        if not np.isclose(target_h, 0.0, atol=1e-12, rtol=0.0):
            raise ValueError(
                "Positive heterogeneity cannot be realized with one component."
            )
        return np.array([1.0], dtype=float)

    root_h = float(np.sqrt(target_h))
    dominant = (1.0 + (dimension - 1) * root_h) / dimension
    remainder = (1.0 - root_h) / dimension

    shares = np.full(dimension, remainder, dtype=float)
    shares[0] = dominant

    if not np.isclose(shares.sum(), 1.0, atol=1e-12, rtol=0.0):
        raise AssertionError("Resource budget is not preserved.")

    realized = normalized_hhi(shares)
    if not np.isclose(realized, target_h, atol=1e-12, rtol=0.0):
        raise AssertionError(
            f"HHI construction failed: target={target_h}, realized={realized}."
        )

    return shares


def finite_grid(lower: float, upper: float) -> np.ndarray:
    """Construct the inclusive finite normalized-HHI grid."""
    if not 0.0 <= lower < upper < 1.0:
        raise ValueError("HHI bounds must satisfy 0 <= lower < upper < 1.")

    count = int(np.floor((upper - lower) / HETEROGENEITY_GRID_STEP + 1e-12))
    values = lower + np.arange(count + 1, dtype=float) * HETEROGENEITY_GRID_STEP

    if values[-1] < upper - 1e-12:
        values = np.append(values, upper)

    values[-1] = upper
    return np.round(values, 12)


def target_is_admissible(target_h: float, dimensions: list[int]) -> bool:
    """Check constructive feasibility for the supplied study dimensions."""
    for dimension in dimensions:
        if dimension < 2:
            return False
        try:
            shares = canonical_hhi_shares(dimension, target_h)
        except (ValueError, AssertionError):
            return False

        if not np.isclose(
            normalized_hhi(shares),
            target_h,
            atol=1e-12,
            rtol=0.0,
        ):
            return False

    return True


def build_admissible_target_sets() -> dict[str, np.ndarray]:
    """Build finite normalized-HHI sets from the study's dimensional bounds.

    The constructive formula proves feasibility for every K >= 2. We explicitly
    verify the minimum and maximum dimensions used in the study, while the
    analytic construction covers all intermediate dimensions.
    """
    node_min, node_max = map(int, OPERATIONAL_PARAM_RANGES["validator_count"])
    in_min, in_max = map(int, DESIGN_PARAM_RANGES["inbound_connections"])
    out_min, out_max = map(int, DESIGN_PARAM_RANGES["outbound_connections"])

    validator_extrema = [node_min, node_max]
    link_extrema = sorted({in_min, in_max, out_min, out_max})

    result: dict[str, np.ndarray] = {}

    for parameter in ("Hnode", "Hlink", "hashrate_concentration"):
        lower, upper = OPERATIONAL_PARAM_RANGES[parameter]
        candidates = finite_grid(float(lower), float(upper))
        dimensions = link_extrema if parameter == "Hlink" else validator_extrema

        feasible = [
            float(h)
            for h in candidates
            if target_is_admissible(float(h), dimensions)
        ]

        if not feasible:
            raise RuntimeError(
                f"No admissible normalized-HHI levels remain for {parameter}."
            )

        result[parameter] = np.asarray(feasible, dtype=float)

    return result


ADMISSIBLE_TARGETS = build_admissible_target_sets()


# ---------------------------------------------------------------------------
# 4. Generic LHS helpers
# ---------------------------------------------------------------------------

def optimized_lhs(
    param_ranges: dict[str, tuple[float, float]],
    n_samples: int,
    seed: int | np.random.SeedSequence,
) -> tuple[pd.DataFrame, np.ndarray]:
    if n_samples < 1:
        raise ValueError("n_samples must be positive.")

    names = list(param_ranges)
    sampler = qmc.LatinHypercube(
        d=len(names),
        seed=np.random.default_rng(seed),
        optimization=LHS_OPTIMIZATION,
    )
    unit = sampler.random(n=n_samples)

    lower = np.array([param_ranges[n][0] for n in names], dtype=float)
    upper = np.array([param_ranges[n][1] for n in names], dtype=float)
    scaled = qmc.scale(unit, lower, upper)
    return pd.DataFrame(scaled, columns=names), unit


def map_unit_interval_to_integers(
    unit_values: np.ndarray,
    lower: int,
    upper: int,
) -> np.ndarray:
    if upper < lower:
        raise ValueError("upper must be >= lower.")

    values = np.asarray(unit_values, dtype=float)
    values = np.minimum(values, np.nextafter(1.0, 0.0))
    n_values = upper - lower + 1
    mapped = lower + np.floor(values * n_values).astype(np.int64)
    return np.clip(mapped, lower, upper)


def map_lhs_to_admissible_levels(
    unit_values: np.ndarray,
    levels: np.ndarray,
    seed: int | np.random.SeedSequence,
) -> np.ndarray:
    """Map one LHS marginal to a finite admissible set.

    If enough levels exist, use distinct levels without replacement and spread
    them across the full ordered set. This avoids duplicates of the same
    heterogeneity factor within one inner design while preserving balanced
    marginal coverage.
    """
    u = np.asarray(unit_values, dtype=float)
    levels = np.asarray(levels, dtype=float)
    n = len(u)
    m = len(levels)

    if m < n:
        raise ValueError(
            f"Need at least {n} admissible levels for duplicate-free inner LHS; "
            f"only {m} are available."
        )

    order = np.argsort(u)
    result = np.empty(n, dtype=float)

    rng = np.random.default_rng(seed)
    phase = rng.uniform(0.0, 1.0)
    ranks = np.floor((np.arange(n) + phase) * m / n).astype(int)
    ranks = np.clip(ranks, 0, m - 1)

    if len(np.unique(ranks)) != n:
        ranks = np.linspace(0, m - 1, n).round().astype(int)

    if len(np.unique(ranks)) != n:
        raise RuntimeError("Could not construct unique admissible ranks.")

    result[order] = levels[ranks]
    return result


# ---------------------------------------------------------------------------
# 5. Outer-level design configurations
# ---------------------------------------------------------------------------

def generate_outer_designs(
    n_designs: int,
    seed: int | np.random.SeedSequence,
) -> pd.DataFrame:
    designs, lhs_unit = optimized_lhs(DESIGN_PARAM_RANGES, n_designs, seed)

    for idx, parameter in enumerate(DESIGN_PARAM_RANGES):
        if parameter in INTEGER_DESIGN_PARAMS:
            lo, hi = DESIGN_PARAM_RANGES[parameter]
            designs[parameter] = map_unit_interval_to_integers(
                lhs_unit[:, idx], int(lo), int(hi)
            )

    designs.insert(0, "design_id", np.arange(1, n_designs + 1, dtype=np.int64))
    return designs


# ---------------------------------------------------------------------------
# 6. Inner-level operational conditions
# ---------------------------------------------------------------------------

def generate_inner_operational_conditions(
    design: pd.Series,
    n_operational: int,
    seed: int | np.random.SeedSequence,
) -> pd.DataFrame:
    if n_operational < 1:
        raise ValueError("n_operational must be positive.")

    names = list(OPERATIONAL_PARAM_RANGES)
    sampler = qmc.LatinHypercube(
        d=len(names),
        seed=np.random.default_rng(seed),
        optimization=LHS_OPTIMIZATION,
    )
    unit = sampler.random(n=n_operational)
    operational = pd.DataFrame(index=np.arange(n_operational))

    seed_sequence = (
        seed if isinstance(seed, np.random.SeedSequence)
        else np.random.SeedSequence(seed)
    )
    map_seeds = seed_sequence.spawn(3)
    map_seed_index = 0

    for idx, parameter in enumerate(names):
        if parameter == "validator_count":
            continue

        if parameter in DISCRETE_HETEROGENEITY_PARAMS:
            operational[parameter] = map_lhs_to_admissible_levels(
                unit[:, idx],
                ADMISSIBLE_TARGETS[parameter],
                map_seeds[map_seed_index],
            )
            map_seed_index += 1
        else:
            lo, hi = OPERATIONAL_PARAM_RANGES[parameter]
            operational[parameter] = lo + unit[:, idx] * (hi - lo)

    configured_lower, configured_upper = OPERATIONAL_PARAM_RANGES["validator_count"]
    feasible_lower = max(
        int(configured_lower),
        int(design["inbound_connections"]) + 1,
        int(design["outbound_connections"]) + 1,
    )

    validator_idx = names.index("validator_count")
    operational["validator_count"] = map_unit_interval_to_integers(
        unit[:, validator_idx],
        feasible_lower,
        int(configured_upper),
    )

    operational["number_of_attackers"] = np.rint(
        operational["attacker_fraction"] * operational["validator_count"]
    ).astype(int)

    maximum_attackers = np.floor(
        0.25 * operational["validator_count"]
    ).astype(int)

    operational["number_of_attackers"] = np.minimum(
        operational["number_of_attackers"],
        maximum_attackers,
    ).astype(int)

    operational["attacker_fraction_realized"] = (
        operational["number_of_attackers"]
        / operational["validator_count"]
    )

    operational.insert(
        0, "operational_id",
        np.arange(1, n_operational + 1, dtype=np.int64)
    )
    operational.insert(0, "design_id", int(design["design_id"]))
    return operational


# ---------------------------------------------------------------------------
# 7. Nested design and duplicate checks
# ---------------------------------------------------------------------------

EXPERIMENTAL_COLUMNS = [
    "block_creation_interval",
    "max_block_size",
    "inbound_connections",
    "outbound_connections",
    "Hnode",
    "Hlink",
    "hashrate_concentration",
    "attacker_fraction",
    "validator_count",
]


def build_nested_lhs(
    n_designs: int,
    n_operational_per_design: int,
    seed: int,
) -> tuple[pd.DataFrame, pd.DataFrame]:
    master = np.random.SeedSequence(seed)
    children = master.spawn(1 + n_designs)

    designs = generate_outer_designs(n_designs, children[0])

    frames = []
    for row_index, design in designs.iterrows():
        operational = generate_inner_operational_conditions(
            design,
            n_operational_per_design,
            children[row_index + 1],
        )

        for parameter in DESIGN_PARAM_RANGES:
            operational[parameter] = design[parameter]

        frames.append(operational)

    pairs = pd.concat(frames, ignore_index=True)
    pairs.insert(0, "pair_id", np.arange(1, len(pairs) + 1, dtype=np.int64))

    duplicate_mask = pairs.duplicated(subset=EXPERIMENTAL_COLUMNS, keep=False)
    if duplicate_mask.any():
        examples = pairs.loc[
            duplicate_mask,
            ["pair_id", "design_id", "operational_id", *EXPERIMENTAL_COLUMNS],
        ].head(20)
        raise ValueError(
            "Duplicate complete experimental configurations detected:\n"
            + examples.to_string(index=False)
        )

    return designs, pairs


# ---------------------------------------------------------------------------
# 8. Monte Carlo seeds
# ---------------------------------------------------------------------------

def splitmix64(values: np.ndarray) -> np.ndarray:
    x = np.asarray(values, dtype=np.uint64)
    x = x + np.uint64(0x9E3779B97F4A7C15)
    z = x.copy()
    z = (z ^ (z >> np.uint64(30))) * np.uint64(0xBF58476D1CE4E5B9)
    z = (z ^ (z >> np.uint64(27))) * np.uint64(0x94D049BB133111EB)
    return z ^ (z >> np.uint64(31))


def write_replication_plan_streaming(
    pairs: pd.DataFrame,
    n_replications: int,
    output_path: Path,
    master_seed: int,
    pair_chunk_size: int = 1_000,
) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    wrote_header = False

    for start in range(0, len(pairs), pair_chunk_size):
        chunk = pairs.iloc[start:start + pair_chunk_size].copy()
        repeated = chunk.loc[
            chunk.index.repeat(n_replications)
        ].reset_index(drop=True)

        repeated.insert(
            3,
            "replication_id",
            np.tile(
                np.arange(1, n_replications + 1, dtype=np.int32),
                len(chunk),
            ),
        )

        run_ids = (
            (repeated["pair_id"].to_numpy(dtype=np.uint64) - np.uint64(1))
            * np.uint64(n_replications)
            + repeated["replication_id"].to_numpy(dtype=np.uint64)
        )
        repeated.insert(
            4,
            "mc_seed",
            splitmix64(run_ids ^ np.uint64(master_seed)),
        )

        repeated.to_csv(
            output_path,
            index=False,
            mode="w" if not wrote_header else "a",
            header=not wrote_header,
        )
        wrote_header = True


# ---------------------------------------------------------------------------
# 9. Validation
# ---------------------------------------------------------------------------

def validate_nested_design(
    designs: pd.DataFrame,
    pairs: pd.DataFrame,
    n_designs: int,
    n_operational: int,
) -> None:
    assert len(designs) == n_designs
    assert len(pairs) == n_designs * n_operational
    assert designs["design_id"].is_unique
    assert pairs["pair_id"].is_unique

    assert (pairs["inbound_connections"] >= 2).all()
    assert (pairs["outbound_connections"] >= 2).all()
    assert (
        pairs["inbound_connections"] <= pairs["validator_count"] - 1
    ).all()
    assert (
        pairs["outbound_connections"] <= pairs["validator_count"] - 1
    ).all()

    for parameter in DISCRETE_HETEROGENEITY_PARAMS:
        admissible = set(np.round(ADMISSIBLE_TARGETS[parameter], 12))
        observed = set(np.round(pairs[parameter].to_numpy(float), 12))
        assert observed.issubset(admissible)

        # 100 distinct values per inner design with current admissible sets.
        max_frequency = (
            pairs.groupby(["design_id", parameter])
            .size()
            .groupby(level=0)
            .max()
        )
        assert max_frequency.le(1).all()

    assert not pairs.duplicated(subset=EXPERIMENTAL_COLUMNS).any()


# ---------------------------------------------------------------------------
# 10. CLI
# ---------------------------------------------------------------------------

def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--seed", type=int, default=DEFAULT_SEED)
    parser.add_argument("--n-designs", type=int, default=DEFAULT_N_DESIGNS)
    parser.add_argument(
        "--n-operational-per-design",
        type=int,
        default=DEFAULT_N_OPERATIONAL_PER_DESIGN,
    )
    parser.add_argument(
        "--n-replications",
        type=int,
        default=DEFAULT_N_REPLICATIONS,
    )
    parser.add_argument("--output-dir", default="two_level_lhs_output")
    parser.add_argument("--write-replication-plan", action="store_true")
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    designs, pairs = build_nested_lhs(
        args.n_designs,
        args.n_operational_per_design,
        args.seed,
    )
    validate_nested_design(
        designs,
        pairs,
        args.n_designs,
        args.n_operational_per_design,
    )

    designs.to_csv(
        output_dir / "outer_design_configurations.csv",
        index=False,
    )
    pairs.to_csv(
        output_dir / "nested_design_operational_pairs.csv",
        index=False,
    )

    if args.write_replication_plan:
        write_replication_plan_streaming(
            pairs,
            args.n_replications,
            output_dir / "monte_carlo_replication_plan.csv",
            args.seed,
        )

    print("Normalized-HHI nested LHS generated.")
    print(f"Outer designs: {len(designs):,}")
    print(f"Design--operational pairs: {len(pairs):,}")
    print(f"Duplicate complete configurations: 0")
    for parameter in ("Hnode", "Hlink", "hashrate_concentration"):
        levels = ADMISSIBLE_TARGETS[parameter]
        print(
            f"{parameter}: {len(levels)} admissible levels "
            f"[{levels.min():.4f}, {levels.max():.4f}]"
        )
    print(f"Output directory: {output_dir.resolve()}")


if __name__ == "__main__":
    main()