

import argparse
import hashlib
from pathlib import Path

import pandas as pd


RS_DEFAULT = 2
RE_DEFAULT = 2
MASTER_SEED_DEFAULT = 1024


# Columns required by the refined 3SIM execution manifest.
SIMULATOR_COLUMNS = [
    "design_id",
    "operational_id",
    "connection_count",
    "block_creation_interval",
    "maximum_block_size",
    "validating_node_count",
    "node_bandwidth_heterogeneity",
    "link_bandwidth_heterogeneity",
    "hashing_power_concentration",
    "number_of_attackers",
    # Single selfish-mining attacker: its share of total hashing power (alpha).
    # number_of_attackers is 1 if this share is > 0, else 0.
    "adversarial_hashing_power_share",
    # Absolute Poisson rate of transaction submissions (tx/s), written per row by
    # two_level_nested_lhs_monte_carlo.py (log-uniform 0.5-100 tx/s in the
    # primary/nested/reference batches; Eq. 7 in the overload batch). The loader
    # uses it directly; no relative load or lambda_ref is involved.
    "transaction_arrival_rate",
    # One simulated transaction stands for this many transactions (computational
    # feasibility; b = 1 for most rows). The simulator submits b-sized messages at
    # rate lambda_tx / b and multiplies TPS by b.
    "transaction_batch_size",
]

# Carried through when present (provenance only).
OPTIONAL_COLUMNS = [
    "capacity_load_factor",
    "source_operational_id",
]

TRANSACTION_SIZE_BYTES = 500


def signed_seed(master_seed: int, label: str) -> int:
    """
    Deterministically derive a signed 64-bit seed.

    The same pair/network-realization receives the same network seed
    whenever the manifest is regenerated with the same master seed.
    """
    payload = f"{master_seed}|{label}".encode("utf-8")
    digest = hashlib.blake2b(payload, digest_size=8).digest()

    return int.from_bytes(
        digest,
        byteorder="little",
        signed=True,
    )


def prepare_pair_table(df: pd.DataFrame) -> pd.DataFrame:
    """
    Normalize a pair-level CSV for manifest generation.

    Primary/nested files already contain operational_id.

    The overload file instead contains overload_condition_id;
    for execution purposes we expose it as operational_id while
    leaving the original pair-level source file unchanged.
    """

    df = df.copy()

    if "operational_id" not in df.columns:
        if "overload_condition_id" in df.columns:
            df["operational_id"] = df["overload_condition_id"]
        else:
            raise ValueError(
                "Input CSV contains neither 'operational_id' nor "
                "'overload_condition_id'."
            )

    missing = [
        c for c in SIMULATOR_COLUMNS
        if c not in df.columns
    ]

    if missing:
        raise ValueError(
            "Input pair CSV is missing required columns: "
            + ", ".join(missing)
        )

    alpha = df["adversarial_hashing_power_share"].astype(float)
    if not alpha.between(0.0, 0.25).all():
        raise AssertionError("adversarial_hashing_power_share must lie in [0, 0.25].")
    if ((alpha > 0).astype(int) != df["number_of_attackers"].astype(int)).any():
        raise AssertionError("number_of_attackers must be 1 when adversarial_hashing_power_share > 0, else 0.")
    b = df["transaction_batch_size"]
    if not ((b >= 1) & (b == b.round())).all():
        raise AssertionError("transaction_batch_size must be a positive integer.")

    return df


def check_capacity_relative_demand(df: pd.DataFrame, u_cap: float = 1.05) -> None:
    """
    Overload batch, Eq. (7): transaction_arrival_rate must equal
    u_cap * MBS / (BCI * S_tx) for every row (MBS in MB = 10^6 bytes, BCI in s,
    S_tx in bytes). Raises if the pair CSV was generated with another rule.
    """
    capacity_tx_per_s = (
        df["maximum_block_size"] * 1_000_000.0
        / (df["block_creation_interval"] * TRANSACTION_SIZE_BYTES)
    )
    ratio = df["transaction_arrival_rate"] / capacity_tx_per_s
    if not ((ratio - u_cap).abs() <= 1e-6 * u_cap).all():  # allows CSV rounding
        raise AssertionError(
            "Overload pair CSV: transaction_arrival_rate is not 1.05 x nominal capacity "
            f"(observed ratio range {ratio.min():.6g}-{ratio.max():.6g})."
        )


def check_demand_columns(df: pd.DataFrame, name: str) -> None:
    if "relative_transaction_load" in df.columns:
        raise AssertionError(
            f"{name}: contains relative_transaction_load. The revised design uses the "
            "absolute transaction_arrival_rate only; regenerate the samples with the "
            "revised two_level_nested_lhs_monte_carlo.py."
        )
    rate = df["transaction_arrival_rate"].astype(float)
    if not (rate > 0).all() or rate.isna().any():
        raise AssertionError(f"{name}: transaction_arrival_rate must be finite and > 0.")


def build_manifest(
    pairs: pd.DataFrame,
    rs: int,
    re: int,
    master_seed: int,
    seed_scope: str = "pair",
) -> pd.DataFrame:
    """
    seed_scope="pair" (default, as in the manuscript): every design-condition pair
    receives its own network and event seeds. seed_scope="operational" shares seeds
    across designs within a condition (common random numbers).
    """

    if seed_scope not in ("operational", "pair"):
        raise ValueError("seed_scope must be 'operational' or 'pair'")

    pairs = prepare_pair_table(pairs)
    passthrough = [c for c in OPTIONAL_COLUMNS if c in pairs.columns]
    out_columns = list(SIMULATOR_COLUMNS) + passthrough

    rows = []
    execution_index = 0

    for pair in pairs.itertuples(index=False):

        pair_dict = pair._asdict()

        design_id = str(pair_dict["design_id"])
        operational_id = str(pair_dict["operational_id"])

        manifest_pair_id = (
            f"{design_id}_{operational_id}"
        )

        for network_instance in range(1, rs + 1):

            network_realization_id = (
                f"{manifest_pair_id}"
                f"_S{network_instance:02d}"
            )

            # IMPORTANT:
            # The network seed depends on pair ID + network instance,
            # but NOT on the event replication.
            #
            # Therefore E01 and E02 for the same S realization share
            # exactly the same network structure/resource realization.
            seed_key = (
                operational_id if seed_scope == "operational"
                else manifest_pair_id
            )

            network_seed = signed_seed(
                master_seed,
                (
                    f"{seed_key}"
                    f"|network|{network_instance}"
                ),
            )

            for event_replication in range(1, re + 1):

                execution_index += 1

                event_seed = signed_seed(
                    master_seed,
                    (
                        f"{seed_key}"
                        f"|network|{network_instance}"
                        f"|event|{event_replication}"
                    ),
                )

                run_id = (
                    f"{network_realization_id}"
                    f"_E{event_replication:02d}"
                )

                row = {
                    c: pair_dict[c]
                    for c in out_columns
                }

                row.update(
                    {
                        "execution_index": execution_index,
                        "manifest_pair_id": manifest_pair_id,
                        "network_instance": network_instance,
                        "event_replication": event_replication,
                        "network_realization_id":
                            network_realization_id,
                        "network_seed": network_seed,
                        "event_seed": event_seed,
                        "run_id": run_id,
                    }
                )

                rows.append(row)

    manifest = pd.DataFrame(rows)

    # ---------------------------------------------------------
    # Verification
    # ---------------------------------------------------------

    expected_rows = len(pairs) * rs * re

    if len(manifest) != expected_rows:
        raise AssertionError(
            f"Expected {expected_rows} manifest rows, "
            f"found {len(manifest)}."
        )

    if not manifest["run_id"].is_unique:
        raise AssertionError(
            "Manifest contains duplicate run_id values."
        )

    replication_counts = (
        manifest
        .groupby("manifest_pair_id")
        .size()
    )

    if not (replication_counts == rs * re).all():
        raise AssertionError(
            "Not every design-condition pair has "
            "R_S * R_E executions."
        )

    # Network seed must be identical for E01/E02 belonging
    # to the same network realization.
    network_seed_counts = (
        manifest
        .groupby("network_realization_id")
        ["network_seed"]
        .nunique()
    )

    if not (network_seed_counts == 1).all():
        raise AssertionError(
            "A network realization received multiple "
            "network seeds."
        )

    # Event seeds must differ between the executions of one pair. With
    # seed_scope="operational" they are intentionally shared across designs.
    if seed_scope == "pair" and not manifest["event_seed"].is_unique:
        raise AssertionError("Event seeds are not globally unique.")
    if manifest.groupby("manifest_pair_id")["event_seed"].nunique().min() != rs * re:
        raise AssertionError(
            "Event seeds are not unique within a design-condition pair."
        )

    return manifest


def generate_one(
    input_file: Path,
    output_file: Path,
    expected_pairs: int,
    rs: int,
    re: int,
    master_seed: int,
    seed_scope: str = "pair",
    capacity_relative_demand: bool = False,
):

    pairs = pd.read_csv(input_file)
    check_demand_columns(prepare_pair_table(pairs), input_file.name)
    if capacity_relative_demand:
        check_capacity_relative_demand(pairs)

    if len(pairs) != expected_pairs:
        raise AssertionError(
            f"{input_file.name}: expected "
            f"{expected_pairs} pairs, found {len(pairs)}."
        )

    manifest = build_manifest(
        pairs,
        rs=rs,
        re=re,
        master_seed=master_seed,
        seed_scope=seed_scope,
    )

    output_file.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    manifest.to_csv(
        output_file,
        index=False,
    )

    print(
        f"{input_file.name}\n"
        f"  pairs      = {len(pairs):,}\n"
        f"  executions = {len(manifest):,}\n"
        f"  output     = {output_file}\n"
    )


def main():

    parser = argparse.ArgumentParser()

    # By default, look for "generated_samples" beside this script.
    # This makes the script independent of the current PowerShell directory.
    script_dir = Path(__file__).resolve().parent

    parser.add_argument(
        "--samples-dir",
        type=Path,
        default=script_dir / "generated_samples",
        help=(
            "Directory containing the pair-level CSV files. "
            "Defaults to a generated_samples folder beside this script."
        ),
    )

    parser.add_argument(
        "--rs",
        type=int,
        default=RS_DEFAULT,
    )

    parser.add_argument(
        "--re",
        type=int,
        default=RE_DEFAULT,
    )

    parser.add_argument(
        "--master-seed",
        type=int,
        default=MASTER_SEED_DEFAULT,
    )

    parser.add_argument(
        "--seed-scope",
        choices=["pair", "operational"],
        default="pair",
        help="Share network/event seeds across designs (operational) or not (pair).",
    )

    args = parser.parse_args()

    d = args.samples_dir.expanduser().resolve()

    if not d.is_dir():
        raise FileNotFoundError(
            f"Samples directory does not exist: {d}\n"
            "Place this script beside the generated_samples folder, "
            "or pass --samples-dir with the correct path."
        )

    # Pair CSVs written by the revised two_level_nested_lhs_monte_carlo.py.
    # Each batch accepts the generator's file name or the older/renamed one.
    batches = [
        # (accepted input names, output stem, expected pairs, Eq. 7 check, required)
        (["simulation_nested_64x48.csv", "nested_design_operational_pairs_64x48.csv"],
         "nested_64x48", 64 * 48, False, True),
        (["simulation_primary_128x96.csv", "nested_design_operational_pairs_128x96.csv"],
         "primary_128x96", 128 * 96, False, True),
        (["simulation_overload_105pct_128x32.csv", "overload_105pct_128x32.csv"],
         "overload_105pct_128x32", 128 * 32, True, True),
        (["homogeneous_reference_128x1.csv"],
         "reference_homogeneous_128x1", 128, False, False),
    ]
    for names, stem, expected, eq7, required in batches:
        found = [d / n for n in names if (d / n).is_file()]
        if not found:
            msg = f"{stem}: none of {', '.join(names)} found in {d}"
            if required:
                raise FileNotFoundError(msg)
            print(f"SKIPPED {msg}\n")
            continue
        if len(found) > 1:
            print(f"NOTE {stem}: several inputs present, using {found[0].name}")
        generate_one(
            input_file=found[0],
            output_file=d / "manifests" / (
                f"{stem}_rs{args.rs}re{args.re}_master{args.master_seed}.csv"
            ),
            expected_pairs=expected,
            rs=args.rs,
            re=args.re,
            master_seed=args.master_seed,
            seed_scope=args.seed_scope,
            capacity_relative_demand=eq7,
        )

if __name__ == "__main__":
    main()