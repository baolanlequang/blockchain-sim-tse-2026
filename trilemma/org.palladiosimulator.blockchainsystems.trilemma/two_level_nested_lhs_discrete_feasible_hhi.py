#!/usr/bin/env python3
"""Two-level nested optimized Latin hypercube design for NSB experiments.

The experimental design separates:

1. Outer-level design parameters:
   - block_creation_interval
   - max_block_size
   - inbound_connections
   - outbound_connections

2. Inner-level operational parameters, sampled independently for every design:
   - Hnode
   - Hlink
   - hashrate_concentration
   - attacker_fraction
   - validator_count

3. Monte Carlo replications:
   - each design--operational pair receives deterministic replication seeds

Design and operational parameters are sampled at separate LHS levels.
validator_count is operational and is sampled from the feasible range implied
by each fixed design's connection limits. number_of_attackers is then derived
from attacker_fraction and validator_count.

Normalized heterogeneity factors are sampled only from a finite, prevalidated
set of admissible HHI levels. The admissible set is checked against the relevant
validator-count and peer-count constraints before sampling. Each selected HHI
level is then mapped continuously to a matching resource-share profile.
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

# Number of decimals used only when constructing duplicate-detection keys.
# The underlying exact-HHI mapping is not rounded.
HETEROGENEITY_SIGNATURE_DECIMALS = 12


# ---------------------------------------------------------------------------
# 2. Parameter ranges
# ---------------------------------------------------------------------------

DESIGN_PARAM_RANGES: dict[str, tuple[float, float]] = {
    "block_creation_interval": (60, 1200),  # seconds
    "max_block_size": (0.25, 8.00),          # MB
    "inbound_connections": (1, 125),        # integer
    "outbound_connections": (1, 8),        # integer
}

OPERATIONAL_PARAM_RANGES: dict[str, tuple[float, float]] = {
    "Hnode": (0.05, 0.60),                  # operational parameter
    "Hlink": (0.05, 0.40),                  # operational parameter
    "hashrate_concentration": (0.05, 0.80),  # operational parameter
    "attacker_fraction": (0.0, 0.25),       # target fraction of validators
    "validator_count": (20, 1000),          # integer
}


# Finite HHI candidate grid used by the experimental design.
#
# The grid step is chosen so that, at the default 100 operational conditions
# per design, even the narrowest HHI interval (Hlink: 0.05--0.40) contains more
# than 100 admissible levels. This permits sampling without replacement within
# each inner design when all grid points are feasible.
HHI_GRID_STEP = 0.0025

# Numerical tolerance used only when validating that a selected HHI level can
# be reproduced by the configured mapping for the relevant allocation size.
HHI_FEASIBILITY_TOLERANCE = 1e-10

INTEGER_DESIGN_PARAMS = {
    "block_creation_interval",
    "inbound_connections",
    "outbound_connections",
}

INTEGER_OPERATIONAL_PARAMS = {
    "validator_count",
}




# ---------------------------------------------------------------------------
# 3. Continuous exact normalized-HHI mapping
# ---------------------------------------------------------------------------

HETEROGENEITY_SIGNATURE_DECIMALS = 12
HHI_ABSOLUTE_TOLERANCE = 1e-10
BISECTION_MAX_ITERATIONS = 200
MAX_SOFTMAX_SCALE = 1e12


def normalized_hhi(shares: np.ndarray) -> float:
    """Return normalized HHI for one vector of non-negative resource shares."""
    values = np.asarray(shares, dtype=float)
    if values.ndim != 1 or values.size < 1:
        raise ValueError("shares must be a non-empty one-dimensional vector.")
    if np.any(~np.isfinite(values)) or np.any(values < 0.0):
        raise ValueError("shares must contain finite, non-negative values.")

    total = float(values.sum())
    if not np.isclose(total, 1.0, atol=HHI_ABSOLUTE_TOLERANCE):
        raise ValueError(f"shares must sum to one; received {total!r}.")

    dimension = values.size
    if dimension == 1:
        return 0.0

    raw_hhi = float(np.square(values).sum())
    minimum_hhi = 1.0 / dimension
    return (raw_hhi - minimum_hhi) / (1.0 - minimum_hhi)


def stable_softmax(values: np.ndarray) -> np.ndarray:
    """Return a numerically stable softmax vector."""
    shifted = np.asarray(values, dtype=float)
    shifted = shifted - np.max(shifted)
    exponentials = np.exp(shifted)
    return exponentials / exponentials.sum()


def deterministic_base_scores(
    dimension: int,
    seed: int | np.random.SeedSequence,
) -> np.ndarray:
    """Generate a deterministic continuous base vector with a unique maximum."""
    if dimension < 2:
        raise ValueError("dimension must be at least two.")

    rng = np.random.default_rng(seed)
    scores = rng.standard_normal(dimension)

    # Continuous draws are unique almost surely. The deterministic perturbation
    # also guarantees uniqueness in the extremely unlikely event of a tie.
    scores = scores + np.arange(dimension, dtype=float) * np.finfo(float).eps
    scores = (scores - scores.mean()) / scores.std()

    if not np.all(np.isfinite(scores)) or np.isclose(scores.std(), 0.0):
        raise RuntimeError("Could not construct a non-degenerate base vector.")

    return scores


def exact_hhi_softmax_profile(
    dimension: int,
    target_h: float,
    seed: int | np.random.SeedSequence,
) -> tuple[np.ndarray, float]:
    """Map a normalized-HHI target continuously to a share profile.

    A deterministic base vector is transformed with a softmax. The softmax
    scale is found by bisection. For large dimensions and high concentration,
    floating-point arithmetic may make consecutive bisection points identical;
    therefore the best candidate is retained and accepted when its residual is
    within ``HHI_ABSOLUTE_TOLERANCE``.
    """
    if dimension < 1:
        raise ValueError("dimension must be positive.")
    if not 0.0 <= target_h < 1.0:
        raise ValueError("target_h must lie in [0, 1).")

    if dimension == 1:
        if not np.isclose(target_h, 0.0, atol=HHI_ABSOLUTE_TOLERANCE):
            raise ValueError(
                "A one-component allocation can only realize normalized HHI zero."
            )
        return np.array([1.0], dtype=float), 0.0

    scores = deterministic_base_scores(dimension, seed)

    if np.isclose(target_h, 0.0, atol=HHI_ABSOLUTE_TOLERANCE):
        return np.full(dimension, 1.0 / dimension, dtype=float), 0.0

    lower_beta = 0.0
    upper_beta = 1.0

    # Bracket the target.
    while True:
        upper_shares = stable_softmax(upper_beta * scores)
        upper_h = normalized_hhi(upper_shares)
        if upper_h >= target_h:
            break
        upper_beta *= 2.0
        if upper_beta > MAX_SOFTMAX_SCALE:
            raise RuntimeError(
                f"Could not bracket target HHI {target_h} for dimension {dimension}."
            )

    best_shares = upper_shares
    best_beta = upper_beta
    best_error = abs(upper_h - target_h)
    previous_beta = None

    for _ in range(BISECTION_MAX_ITERATIONS):
        beta = lower_beta + 0.5 * (upper_beta - lower_beta)

        # Floating-point plateau: no representable point remains between bounds.
        if beta == lower_beta or beta == upper_beta or beta == previous_beta:
            break
        previous_beta = beta

        shares = stable_softmax(beta * scores)
        realized_h = normalized_hhi(shares)
        signed_error = realized_h - target_h
        absolute_error = abs(signed_error)

        if absolute_error < best_error:
            best_error = absolute_error
            best_shares = shares
            best_beta = beta

        if absolute_error <= HHI_ABSOLUTE_TOLERANCE:
            return shares, beta

        if signed_error < 0.0:
            lower_beta = beta
        else:
            upper_beta = beta

    # Use the numerically best point found rather than failing merely because
    # the beta interval reached machine precision.
    realized_best = normalized_hhi(best_shares)
    if abs(realized_best - target_h) <= HHI_ABSOLUTE_TOLERANCE:
        return best_shares, best_beta

    raise RuntimeError(
        "HHI mapping did not reach the requested numerical tolerance: "
        f"dimension={dimension}, target_h={target_h}, "
        f"realized_h={realized_best}, absolute_error={best_error}, "
        f"tolerance={HHI_ABSOLUTE_TOLERANCE}."
    )


def heterogeneity_seed(
    pair_id: int,
    stream_id: int,
    master_seed: int = DEFAULT_SEED,
) -> int:
    """Return a deterministic seed for one pair and heterogeneity stream."""
    if pair_id < 1:
        raise ValueError("pair_id must be positive.")
    if stream_id < 1:
        raise ValueError("stream_id must be positive.")

    mask = (1 << 64) - 1
    mixed_stream = (stream_id * 0x9E3779B97F4A7C15) & mask
    mixed_value = (pair_id ^ master_seed ^ mixed_stream) & mask
    value = np.array([np.uint64(mixed_value)], dtype=np.uint64)
    return int(splitmix64(value)[0])


def continuous_hhi_profile_summary(
    dimension: int,
    target_h: float,
    seed: int,
    prefix: str,
) -> dict[str, float | int | str]:
    """Return metadata and a duplicate-detection signature."""
    shares, beta = exact_hhi_softmax_profile(
        dimension=dimension,
        target_h=target_h,
        seed=seed,
    )

    # Sorting makes the signature invariant to component labels. The seed is
    # retained separately so the simulator can reproduce the labeled profile.
    rounded = np.round(np.sort(shares), HETEROGENEITY_SIGNATURE_DECIMALS)
    signature = "|".join(
        f"{value:.{HETEROGENEITY_SIGNATURE_DECIMALS}f}" for value in rounded
    )

    return {
        f"{prefix}_dimension": int(dimension),
        f"{prefix}_target_h": float(target_h),
        f"{prefix}_realized_h": float(normalized_hhi(shares)),
        f"{prefix}_mapping_seed": int(seed),
        f"{prefix}_softmax_scale": float(beta),
        f"{prefix}_minimum_share": float(shares.min()),
        f"{prefix}_maximum_share": float(shares.max()),
        f"{prefix}_profile_signature": signature,
    }


def attach_continuous_hhi_mappings(
    pairs: pd.DataFrame,
    master_seed: int,
) -> pd.DataFrame:
    """Attach reproducible continuous exact-HHI mapping metadata.

    Node bandwidth and hash rate use ``validator_count`` as the allocation
    dimension. Link bandwidth must ultimately use each node's realized degree
    inside the simulator. Here, ``configured_peer_count`` is used only as a
    design-stage diagnostic dimension.

    The simulator can reconstruct any profile with
    ``exact_hhi_softmax_profile(dimension, target_h, mapping_seed)``.
    """
    required = {
        "pair_id",
        "Hnode",
        "Hlink",
        "hashrate_concentration",
        "validator_count",
        "inbound_connections",
        "outbound_connections",
    }
    missing = required.difference(pairs.columns)
    if missing:
        raise KeyError(
            f"Missing columns required for HHI mapping: {sorted(missing)}"
        )

    mapped = pairs.copy()
    mapped["configured_peer_count"] = (
        mapped["inbound_connections"].astype(int)
        + mapped["outbound_connections"].astype(int)
    )

    if (mapped["configured_peer_count"] < 2).any():
        raise ValueError(
            "Positive Hlink targets require at least two configured peers."
        )

    records: list[dict[str, object]] = []
    for row in mapped.itertuples(index=False):
        pair_id = int(row.pair_id)

        node = continuous_hhi_profile_summary(
            dimension=int(row.validator_count),
            target_h=float(row.Hnode),
            seed=heterogeneity_seed(pair_id, stream_id=1, master_seed=master_seed),
            prefix="node_bandwidth",
        )
        link = continuous_hhi_profile_summary(
            dimension=int(row.configured_peer_count),
            target_h=float(row.Hlink),
            seed=heterogeneity_seed(pair_id, stream_id=2, master_seed=master_seed),
            prefix="link_bandwidth",
        )
        hashing = continuous_hhi_profile_summary(
            dimension=int(row.validator_count),
            target_h=float(row.hashrate_concentration),
            seed=heterogeneity_seed(pair_id, stream_id=3, master_seed=master_seed),
            prefix="hashrate",
        )
        records.append({**node, **link, **hashing})

    metadata = pd.DataFrame.from_records(records, index=mapped.index)
    return pd.concat([mapped, metadata], axis=1)


def validate_unique_mapped_configurations(pairs: pd.DataFrame) -> None:
    """Fail if distinct LHS rows collapse to duplicate effective profiles."""
    key_columns = [
        "block_creation_interval",
        "max_block_size",
        "inbound_connections",
        "outbound_connections",
        "validator_count",
        "number_of_attackers",
        "node_bandwidth_profile_signature",
        "link_bandwidth_profile_signature",
        "hashrate_profile_signature",
    ]

    duplicate_mask = pairs.duplicated(subset=key_columns, keep=False)
    if duplicate_mask.any():
        examples = pairs.loc[
            duplicate_mask,
            ["pair_id", "design_id", "operational_id", *key_columns],
        ].head(20)
        raise ValueError(
            "Distinct sampled rows map to duplicate effective configurations.\n"
            + examples.to_string(index=False)
        )



# ---------------------------------------------------------------------------
# 3A. Finite admissible HHI sets
# ---------------------------------------------------------------------------

def candidate_hhi_levels(lower: float, upper: float) -> np.ndarray:
    """Return the finite experimental HHI grid over an inclusive interval."""
    if not 0.0 <= lower < upper < 1.0:
        raise ValueError("HHI bounds must satisfy 0 <= lower < upper < 1.")

    count = int(np.floor((upper - lower) / HHI_GRID_STEP + 1e-12))
    levels = lower + np.arange(count + 1, dtype=float) * HHI_GRID_STEP

    # Include the upper bound if floating-point arithmetic missed it.
    if levels[-1] < upper - 1e-12:
        levels = np.append(levels, upper)

    levels[-1] = min(levels[-1], upper)
    return np.round(levels, 12)


def hhi_level_is_feasible(
    dimension: int,
    target_h: float,
    seed: int = DEFAULT_SEED,
) -> bool:
    """Return whether the configured mapping can realize one HHI level.

    This function is deliberately the single feasibility gate used to build the
    admissible sets. If the executable simulator later imposes additional
    quantization or minimum-share constraints, add those checks here; the LHS
    sampling code does not need to change.
    """
    if dimension < 2:
        return bool(np.isclose(target_h, 0.0, atol=HHI_FEASIBILITY_TOLERANCE))

    try:
        shares, _ = exact_hhi_softmax_profile(
            dimension=dimension,
            target_h=float(target_h),
            seed=seed,
        )
    except (ValueError, RuntimeError, FloatingPointError):
        return False

    realized = normalized_hhi(shares)
    return bool(
        np.isclose(
            realized,
            target_h,
            atol=HHI_FEASIBILITY_TOLERANCE,
            rtol=0.0,
        )
    )


def common_feasible_hhi_levels(
    parameter: str,
    dimensions: list[int],
) -> np.ndarray:
    """Precalculate HHI levels feasible for every relevant dimension.

    The intersection is computed over the supplied dimensions. At minimum this
    includes the study extrema, as requested for validator count and peer count.
    The function accepts a larger set if every intermediate dimension should be
    proved explicitly as well.
    """
    lower, upper = OPERATIONAL_PARAM_RANGES[parameter]
    candidates = candidate_hhi_levels(float(lower), float(upper))

    feasible = []
    for target_h in candidates:
        if all(
            hhi_level_is_feasible(
                dimension=int(dimension),
                target_h=float(target_h),
                seed=DEFAULT_SEED + int(dimension),
            )
            for dimension in dimensions
        ):
            feasible.append(float(target_h))

    if not feasible:
        raise ValueError(
            f"No common feasible HHI levels found for {parameter} "
            f"over dimensions {dimensions}."
        )

    return np.asarray(feasible, dtype=float)


def study_hhi_feasible_sets() -> dict[str, np.ndarray]:
    """Derive finite HHI sets from the study's min/max dimensional constraints."""
    validator_min, validator_max = OPERATIONAL_PARAM_RANGES["validator_count"]

    inbound_min, inbound_max = DESIGN_PARAM_RANGES["inbound_connections"]
    outbound_min, outbound_max = DESIGN_PARAM_RANGES["outbound_connections"]

    # Configured peer count is inbound + outbound in the current generator.
    peer_min = int(inbound_min) + int(outbound_min)
    peer_max = int(inbound_max) + int(outbound_max)

    validator_dimensions = [int(validator_min), int(validator_max)]
    peer_dimensions = [peer_min, peer_max]

    return {
        "Hnode": common_feasible_hhi_levels("Hnode", validator_dimensions),
        "Hlink": common_feasible_hhi_levels("Hlink", peer_dimensions),
        "hashrate_concentration": common_feasible_hhi_levels(
            "hashrate_concentration",
            validator_dimensions,
        ),
    }


def map_lhs_to_discrete_levels_without_replacement(
    unit_values: np.ndarray,
    admissible_levels: np.ndarray,
    rng: np.random.Generator,
) -> np.ndarray:
    """Map LHS coordinates to a finite admissible set with balanced coverage.

    When the admissible set has at least as many values as samples, levels are
    selected without replacement. The sorted LHS coordinates are matched to
    approximately equally spaced ranks in the admissible set, then the rank
    assignment is randomly offset/jittered in a reproducible way. This preserves
    the ordering/stratification of the LHS coordinate while avoiding duplicates
    in the parameter within one inner design.

    If there are fewer admissible levels than samples, exact uniqueness for that
    individual parameter is impossible; balanced repeated use is then employed.
    Complete configurations are still checked separately for duplicates.
    """
    u = np.asarray(unit_values, dtype=float)
    levels = np.asarray(admissible_levels, dtype=float)

    if u.ndim != 1:
        raise ValueError("unit_values must be one-dimensional.")
    if len(levels) < 1:
        raise ValueError("admissible_levels must not be empty.")

    n = len(u)
    m = len(levels)

    order = np.argsort(u)
    assigned = np.empty(n, dtype=float)

    if m >= n:
        # Choose n distinct ranks spread across the full admissible set.
        # The small random phase avoids always selecting exactly the same ranks.
        phase = rng.uniform(0.0, 1.0)
        ranks = np.floor((np.arange(n) + phase) * m / n).astype(int)
        ranks = np.clip(ranks, 0, m - 1)

        # Defensive uniqueness correction (normally unnecessary for m >= n).
        if len(np.unique(ranks)) != n:
            ranks = np.linspace(0, m - 1, n).round().astype(int)
            if len(np.unique(ranks)) != n:
                raise RuntimeError(
                    "Could not assign unique admissible HHI levels."
                )

        assigned[order] = levels[ranks]
    else:
        # Individual duplicates are mathematically unavoidable.
        ranks = np.floor(u * m).astype(int)
        ranks = np.clip(ranks, 0, m - 1)
        assigned[:] = levels[ranks]

    return assigned


# Precalculate once at import time. These are the only HHI values from which
# the inner LHS is allowed to select.
FEASIBLE_HHI_LEVELS = study_hhi_feasible_sets()


# ---------------------------------------------------------------------------
# 4. Generic optimized LHS helper
# ---------------------------------------------------------------------------

def optimized_lhs(
    param_ranges: dict[str, tuple[float, float]],
    n_samples: int,
    seed: int | np.random.SeedSequence,
) -> tuple[pd.DataFrame, np.ndarray]:
    """Generate an optimized LHS and return scaled and unit-cube coordinates."""
    if n_samples < 1:
        raise ValueError("n_samples must be positive.")

    param_names = list(param_ranges)
    sampler = qmc.LatinHypercube(
        d=len(param_names),
        seed=np.random.default_rng(seed),
        optimization=LHS_OPTIMIZATION,
    )
    lhs_unit = sampler.random(n=n_samples)

    lower = np.array([param_ranges[name][0] for name in param_names], dtype=float)
    upper = np.array([param_ranges[name][1] for name in param_names], dtype=float)
    lhs_scaled = qmc.scale(lhs_unit, lower, upper)

    return pd.DataFrame(lhs_scaled, columns=param_names), lhs_unit


def map_unit_interval_to_integers(
    unit_values: np.ndarray,
    lower: int,
    upper: int,
) -> np.ndarray:
    """Map LHS coordinates in [0, 1) to inclusive integer values.

    The interval [0, 1) is divided into ``upper - lower + 1`` equal-width
    bins, one for each admissible integer.  This is preferable to scaling
    followed by ``round()`` because rounding gives the two end values only
    half the probability mass of interior values.

    Repeated integer values are unavoidable when the number of LHS samples
    exceeds the number of admissible integers.  This mapping nevertheless
    keeps their marginal allocation as balanced as the LHS coordinates allow.
    """
    if upper < lower:
        raise ValueError("upper must be greater than or equal to lower.")

    values = np.asarray(unit_values, dtype=float)
    if np.any((values < 0.0) | (values > 1.0)):
        raise ValueError("unit_values must lie in [0, 1].")

    number_of_values = upper - lower + 1

    # np.nextafter protects against the rare case where a value is exactly 1.0.
    # SciPy's LatinHypercube normally returns values in [0, 1), but clipping
    # makes this helper safe for reused or externally supplied coordinates.
    values = np.minimum(values, np.nextafter(1.0, 0.0))
    mapped = lower + np.floor(values * number_of_values).astype(np.int64)

    return np.clip(mapped, lower, upper)


# ---------------------------------------------------------------------------
# 4. Outer-level design configurations
# ---------------------------------------------------------------------------

def generate_outer_designs(
    n_designs: int,
    seed: int | np.random.SeedSequence,
) -> pd.DataFrame:
    """Generate feasible outer-level architectural design configurations."""
    designs, lhs_unit = optimized_lhs(
        DESIGN_PARAM_RANGES,
        n_samples=n_designs,
        seed=seed,
    )

    # Convert integer-valued design parameters directly from their unit-cube
    # LHS coordinates.  Equal-width discrete bins avoid the endpoint bias that
    # would arise from scaling first and then applying round().
    for index, parameter in enumerate(DESIGN_PARAM_RANGES):
        if parameter not in INTEGER_DESIGN_PARAMS:
            continue
        lower, upper = DESIGN_PARAM_RANGES[parameter]
        designs[parameter] = map_unit_interval_to_integers(
            lhs_unit[:, index],
            int(lower),
            int(upper),
        )

    designs.insert(0, "design_id", np.arange(1, n_designs + 1, dtype=np.int64))

    # Retain unit-cube coordinates for reproducibility and design diagnostics.
    for index, parameter in enumerate(DESIGN_PARAM_RANGES):
        designs[f"lhs_u_{parameter}"] = lhs_unit[:, index]

    return designs


# ---------------------------------------------------------------------------
# 5. Inner-level operational conditions
# ---------------------------------------------------------------------------

def generate_inner_operational_conditions(
    design: pd.Series,
    n_operational: int,
    seed: int | np.random.SeedSequence,
) -> pd.DataFrame:
    """Sample operational parameters for one fixed design configuration.

    validator_count is operational. Its lower bound is conditioned on the fixed
    design connection limits so that executable pairs are produced without
    modifying the design configuration.
    """
    if n_operational < 1:
        raise ValueError("n_operational must be positive.")

    parameter_names = list(OPERATIONAL_PARAM_RANGES)
    sampler = qmc.LatinHypercube(
        d=len(parameter_names),
        seed=np.random.default_rng(seed),
        optimization=LHS_OPTIMIZATION,
    )
    lhs_unit = sampler.random(n=n_operational)

    operational = pd.DataFrame(index=np.arange(n_operational))

    discrete_hhi_parameters = {
        "Hnode",
        "Hlink",
        "hashrate_concentration",
    }

    mapping_rng = np.random.default_rng(seed)

    for index, parameter in enumerate(parameter_names):
        if parameter == "validator_count":
            continue

        if parameter in discrete_hhi_parameters:
            operational[parameter] = map_lhs_to_discrete_levels_without_replacement(
                lhs_unit[:, index],
                FEASIBLE_HHI_LEVELS[parameter],
                rng=mapping_rng,
            )
            continue

        lower, upper = OPERATIONAL_PARAM_RANGES[parameter]
        operational[parameter] = lower + lhs_unit[:, index] * (upper - lower)

    configured_lower, configured_upper = OPERATIONAL_PARAM_RANGES["validator_count"]
    feasible_lower = max(
        int(configured_lower),
        int(design["inbound_connections"]) + 1,
        int(design["outbound_connections"]) + 1,
    )
    if feasible_lower > int(configured_upper):
        raise ValueError(
            "The design connection limits require validator_count "
            f">= {feasible_lower}, exceeding the configured upper bound "
            f"{int(configured_upper)}."
        )

    validator_index = parameter_names.index("validator_count")
    # validator_count is discrete and has a design-dependent feasible lower
    # bound.  Map its unit-cube coordinate into equal-width bins over the
    # inclusive feasible integer range instead of rounding a continuous value.
    operational["validator_count"] = map_unit_interval_to_integers(
        lhs_unit[:, validator_index],
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
        0,
        "operational_id",
        np.arange(1, n_operational + 1, dtype=np.int64),
    )
    operational.insert(0, "design_id", int(design["design_id"]))

    for index, parameter in enumerate(parameter_names):
        operational[f"lhs_u_{parameter}"] = lhs_unit[:, index]

    return operational


# ---------------------------------------------------------------------------
# 6. Two-level nested design
# ---------------------------------------------------------------------------

def build_nested_lhs(
    n_designs: int,
    n_operational_per_design: int,
    seed: int,
) -> tuple[pd.DataFrame, pd.DataFrame]:
    """Build independent outer and inner LHS levels, then form nested pairs."""
    if n_designs < 1:
        raise ValueError("n_designs must be positive.")
    if n_operational_per_design < 1:
        raise ValueError("n_operational_per_design must be positive.")

    master_sequence = np.random.SeedSequence(seed)
    child_sequences = master_sequence.spawn(1 + n_designs)

    outer_seed = child_sequences[0]
    inner_seeds = child_sequences[1:]

    designs = generate_outer_designs(
        n_designs=n_designs,
        seed=outer_seed,
    )

    pair_frames: list[pd.DataFrame] = []

    for row_index, design in designs.iterrows():
        operational = generate_inner_operational_conditions(
            design=design,
            n_operational=n_operational_per_design,
            seed=inner_seeds[row_index],
        )

        # Copy the fixed outer-level design parameters into each nested pair.
        for parameter in DESIGN_PARAM_RANGES:
            operational[parameter] = design[parameter]

        pair_frames.append(operational)

    pairs = pd.concat(pair_frames, ignore_index=True)
    pairs.insert(0, "pair_id", np.arange(1, len(pairs) + 1, dtype=np.int64))

    # Map normalized heterogeneity targets analytically. No nearest-grid
    # approximation is used, so distinct targets are not collapsed merely
    # because of calibration-table rounding.
    pairs = attach_continuous_hhi_mappings(pairs, master_seed=seed)
    validate_unique_mapped_configurations(pairs)
    validate_no_duplicates_across_all_parameters(pairs)

    return designs, pairs


# ---------------------------------------------------------------------------
# 7. Deterministic Monte Carlo replication seeds
# ---------------------------------------------------------------------------

def splitmix64(values: np.ndarray) -> np.ndarray:
    """Return well-scrambled deterministic uint64 values."""
    x = np.asarray(values, dtype=np.uint64)
    x = x + np.uint64(0x9E3779B97F4A7C15)
    z = x.copy()
    z = (z ^ (z >> np.uint64(30))) * np.uint64(0xBF58476D1CE4E5B9)
    z = (z ^ (z >> np.uint64(27))) * np.uint64(0x94D049BB133111EB)
    return z ^ (z >> np.uint64(31))


def monte_carlo_seed(
    pair_id: int,
    replication_id: int,
    n_replications: int,
    master_seed: int = DEFAULT_SEED,
) -> int:
    """Return a deterministic seed for one pair-level Monte Carlo replication."""
    if pair_id < 1:
        raise ValueError("pair_id must be one-based and positive.")
    if not 1 <= replication_id <= n_replications:
        raise ValueError("replication_id must be within 1..n_replications.")

    run_id = (pair_id - 1) * n_replications + replication_id
    value = np.array(
        [np.uint64(run_id) ^ np.uint64(master_seed)],
        dtype=np.uint64,
    )
    return int(splitmix64(value)[0])


def write_replication_plan_streaming(
    pairs: pd.DataFrame,
    n_replications: int,
    output_path: Path,
    master_seed: int,
    pair_chunk_size: int = 1_000,
) -> None:
    """Write one row per Monte Carlo run without materializing the full plan."""
    if n_replications < 1:
        raise ValueError("n_replications must be positive.")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    wrote_header = False

    for start in range(0, len(pairs), pair_chunk_size):
        chunk = pairs.iloc[start : start + pair_chunk_size].copy()
        repeated = chunk.loc[chunk.index.repeat(n_replications)].reset_index(drop=True)

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
# 8. Validation
# ---------------------------------------------------------------------------

def validate_nested_design(
    designs: pd.DataFrame,
    pairs: pd.DataFrame,
    n_designs: int,
    n_operational_per_design: int,
) -> None:
    """Validate dimensions, uniqueness, and semantic constraints."""
    expected_pairs = n_designs * n_operational_per_design

    assert len(designs) == n_designs
    assert len(pairs) == expected_pairs
    assert designs["design_id"].is_unique
    assert pairs["pair_id"].is_unique

    assert (
        pairs.groupby("design_id")["operational_id"]
        .nunique()
        .eq(n_operational_per_design)
        .all()
    )

    assert (pairs["number_of_attackers"] >= 0).all()
    assert (
        pairs["number_of_attackers"]
        <= np.floor(0.25 * pairs["validator_count"])
    ).all()
    assert pairs["attacker_fraction"].between(0.0, 0.25, inclusive="both").all()
    assert pairs["attacker_fraction_realized"].between(
        0.0, 0.25, inclusive="both"
    ).all()

    assert (pairs["inbound_connections"] >= 1).all()
    assert (pairs["outbound_connections"] >= 1).all()
    assert (
        pairs["inbound_connections"] <= pairs["validator_count"] - 1
    ).all()
    assert (
        pairs["outbound_connections"] <= pairs["validator_count"] - 1
    ).all()

    assert pd.api.types.is_integer_dtype(pairs["validator_count"])

    assert np.allclose(pairs["node_bandwidth_target_h"], pairs["node_bandwidth_realized_h"], atol=1e-12)
    assert np.allclose(pairs["link_bandwidth_target_h"], pairs["link_bandwidth_realized_h"], atol=1e-12)
    assert np.allclose(pairs["hashrate_target_h"], pairs["hashrate_realized_h"], atol=1e-12)

    # Integer design parameters must remain within their inclusive configured
    # ranges after discrete bin mapping.
    for parameter in INTEGER_DESIGN_PARAMS:
        lower, upper = DESIGN_PARAM_RANGES[parameter]
        assert designs[parameter].between(
            int(lower), int(upper), inclusive="both"
        ).all()
        assert pd.api.types.is_integer_dtype(designs[parameter])

    for parameter, (lower, upper) in OPERATIONAL_PARAM_RANGES.items():
        assert pairs[parameter].between(lower, upper, inclusive="both").all()


    for parameter in ("Hnode", "Hlink", "hashrate_concentration"):
        admissible = set(np.round(FEASIBLE_HHI_LEVELS[parameter], 12))
        observed = set(np.round(pairs[parameter].to_numpy(dtype=float), 12))
        assert observed.issubset(admissible)

        # When enough admissible levels exist, each inner LHS uses each selected
        # HHI level at most once for this parameter.
        if len(FEASIBLE_HHI_LEVELS[parameter]) >= n_operational_per_design:
            per_design_max = (
                pairs.groupby(["design_id", parameter])
                .size()
                .groupby(level=0)
                .max()
            )
            assert per_design_max.le(1).all()



# ---------------------------------------------------------------------------
# 9. Output views and duplicate audits
# ---------------------------------------------------------------------------

PRIMARY_PAIR_COLUMNS = [
    "pair_id",
    "design_id",
    "operational_id",
    "block_creation_interval",
    "max_block_size",
    "inbound_connections",
    "outbound_connections",
    "Hnode",
    "Hlink",
    "hashrate_concentration",
    "attacker_fraction",
    "validator_count",
    "number_of_attackers",
    "attacker_fraction_realized",
]

MAPPING_AUDIT_COLUMNS = [
    "pair_id",
    "configured_peer_count",
    "node_bandwidth_dimension",
    "node_bandwidth_target_h",
    "node_bandwidth_realized_h",
    "node_bandwidth_mapping_seed",
    "node_bandwidth_softmax_scale",
    "node_bandwidth_minimum_share",
    "node_bandwidth_maximum_share",
    "node_bandwidth_profile_signature",
    "link_bandwidth_dimension",
    "link_bandwidth_target_h",
    "link_bandwidth_realized_h",
    "link_bandwidth_mapping_seed",
    "link_bandwidth_softmax_scale",
    "link_bandwidth_minimum_share",
    "link_bandwidth_maximum_share",
    "link_bandwidth_profile_signature",
    "hashrate_dimension",
    "hashrate_target_h",
    "hashrate_realized_h",
    "hashrate_mapping_seed",
    "hashrate_softmax_scale",
    "hashrate_minimum_share",
    "hashrate_maximum_share",
    "hashrate_profile_signature",
]


def primary_pair_view(pairs: pd.DataFrame) -> pd.DataFrame:
    """Return the compact configuration-pair table used by collaborators."""
    missing = [column for column in PRIMARY_PAIR_COLUMNS if column not in pairs]
    if missing:
        raise KeyError(f"Missing primary output columns: {missing}")
    return pairs.loc[:, PRIMARY_PAIR_COLUMNS].copy()


def mapping_audit_view(pairs: pd.DataFrame) -> pd.DataFrame:
    """Return mapping diagnostics separately from the primary pair table."""
    missing = [column for column in MAPPING_AUDIT_COLUMNS if column not in pairs]
    if missing:
        raise KeyError(f"Missing mapping audit columns: {missing}")
    return pairs.loc[:, MAPPING_AUDIT_COLUMNS].copy()


def duplicate_audit(pairs: pd.DataFrame) -> pd.DataFrame:
    """Audit duplicates in sampled parameters and effective configurations.

    ``sampled_parameter_duplicate`` checks the nine experimental factors.
    ``effective_configuration_duplicate`` checks what the simulator effectively
    receives after integer attacker conversion and heterogeneity mapping.
    """
    sampled_columns = [
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

    effective_columns = [
        "block_creation_interval",
        "max_block_size",
        "inbound_connections",
        "outbound_connections",
        "validator_count",
        "number_of_attackers",
        "node_bandwidth_profile_signature",
        "link_bandwidth_profile_signature",
        "hashrate_profile_signature",
    ]

    audit = pairs.loc[:, ["pair_id", "design_id", "operational_id"]].copy()
    audit["sampled_parameter_duplicate"] = pairs.duplicated(
        subset=sampled_columns,
        keep=False,
    )
    audit["effective_configuration_duplicate"] = pairs.duplicated(
        subset=effective_columns,
        keep=False,
    )
    return audit


def validate_no_duplicates_across_all_parameters(pairs: pd.DataFrame) -> None:
    """Fail if any complete sampled or effective configuration is duplicated."""
    audit = duplicate_audit(pairs)

    sampled_count = int(audit["sampled_parameter_duplicate"].sum())
    effective_count = int(audit["effective_configuration_duplicate"].sum())

    if sampled_count or effective_count:
        examples = audit[
            audit["sampled_parameter_duplicate"]
            | audit["effective_configuration_duplicate"]
        ].head(20)
        raise ValueError(
            "Duplicate complete configurations detected. "
            f"sampled_rows={sampled_count}, effective_rows={effective_count}\n"
            + examples.to_string(index=False)
        )


# ---------------------------------------------------------------------------
# 10. CLI and output
# ---------------------------------------------------------------------------

def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description=(
            "Generate a two-level nested optimized Latin hypercube design "
            "for Nakamoto-style blockchain simulation experiments."
        )
    )
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
    parser.add_argument(
        "--write-replication-plan",
        action="store_true",
        help=(
            "Write one row per Monte Carlo run. With the default paper settings, "
            "this creates 25,000,000 rows."
        ),
    )
    return parser.parse_args()


def main() -> None:
    args = parse_args()

    if args.n_replications < 1:
        raise ValueError("--n-replications must be positive.")

    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    designs, pairs = build_nested_lhs(
        n_designs=args.n_designs,
        n_operational_per_design=args.n_operational_per_design,
        seed=args.seed,
    )

    validate_nested_design(
        designs=designs,
        pairs=pairs,
        n_designs=args.n_designs,
        n_operational_per_design=args.n_operational_per_design,
    )

    designs.to_csv(
        output_dir / "outer_design_configurations.csv",
        index=False,
    )
    # Keep the collaborator-facing pair file compact. Detailed HHI mapping
    # diagnostics are written to a separate audit file.
    compact_pairs = primary_pair_view(pairs)
    compact_pairs.to_csv(
        output_dir / "nested_design_operational_pairs.csv",
        index=False,
    )

    if args.write_replication_plan:
        write_replication_plan_streaming(
            pairs=pairs,
            n_replications=args.n_replications,
            output_path=output_dir / "monte_carlo_replication_plan.csv",
            master_seed=args.seed,
        )

    total_pairs = len(pairs)
    total_runs = total_pairs * args.n_replications

    print("Two-level nested LHS generated.")
    print(f"Outer design configurations: {len(designs):,}")
    print(f"Operational conditions per design: {args.n_operational_per_design:,}")
    print(f"Design--operational pairs: {total_pairs:,}")
    print(f"Monte Carlo replications per pair: {args.n_replications:,}")
    print(f"Total planned simulation runs: {total_runs:,}")
    for parameter in ("Hnode", "Hlink", "hashrate_concentration"):
        levels = FEASIBLE_HHI_LEVELS[parameter]
        print(
            f"{parameter} admissible HHI levels: {len(levels):,} "
            f"[{levels.min():.4f}, {levels.max():.4f}]"
        )
    print(f"Output directory: {output_dir.resolve()}")


if __name__ == "__main__":
    main()