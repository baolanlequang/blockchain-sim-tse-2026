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


# ---------------------------------------------------------------------------
# 2. Parameter ranges
# ---------------------------------------------------------------------------

DESIGN_PARAM_RANGES: dict[str, tuple[float, float]] = {
    "block_creation_interval": (60, 1200),  # seconds
    "max_block_size": (0.25, 8.0),          # MB
    "inbound_connections": (1, 250),        # integer
    "outbound_connections": (1, 16),        # integer
}

OPERATIONAL_PARAM_RANGES: dict[str, tuple[float, float]] = {
    "Hnode": (0.0, 1.5),                    # operational parameter
    "Hlink": (0.3, 10.0),                   # operational parameter
    "hashrate_concentration": (0.10, 1.5),  # operational parameter
    "attacker_fraction": (0.0, 0.25),       # target fraction of validators
    "validator_count": (50, 5000),          # integer
}

INTEGER_DESIGN_PARAMS = {
    "block_creation_interval",
    "inbound_connections",
    "outbound_connections",
}

INTEGER_OPERATIONAL_PARAMS = {
    "validator_count",
}


# ---------------------------------------------------------------------------
# 3. Generic optimized LHS helper
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

    for parameter in INTEGER_DESIGN_PARAMS:
        designs[parameter] = designs[parameter].round().astype(int)

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

    for index, parameter in enumerate(parameter_names):
        if parameter == "validator_count":
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
    operational["validator_count"] = (
        feasible_lower
        + lhs_unit[:, validator_index] * (configured_upper - feasible_lower)
    ).round().astype(int)

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

    for parameter, (lower, upper) in OPERATIONAL_PARAM_RANGES.items():
        assert pairs[parameter].between(lower, upper, inclusive="both").all()


# ---------------------------------------------------------------------------
# 9. CLI and output
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
    pairs.to_csv(
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
    print(f"Output directory: {output_dir.resolve()}")


if __name__ == "__main__":
    main()