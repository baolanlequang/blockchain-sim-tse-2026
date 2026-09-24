

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
    "relative_transaction_load",
]


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

    return df


def build_manifest(
    pairs: pd.DataFrame,
    rs: int,
    re: int,
    master_seed: int,
) -> pd.DataFrame:

    pairs = prepare_pair_table(pairs)

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
            network_seed = signed_seed(
                master_seed,
                (
                    f"{manifest_pair_id}"
                    f"|network|{network_instance}"
                ),
            )

            for event_replication in range(1, re + 1):

                execution_index += 1

                event_seed = signed_seed(
                    master_seed,
                    (
                        f"{manifest_pair_id}"
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
                    for c in SIMULATOR_COLUMNS
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

    if not manifest["event_seed"].is_unique:
        raise AssertionError(
            "Event seeds are not unique."
        )

    return manifest


def generate_one(
    input_file: Path,
    output_file: Path,
    expected_pairs: int,
    rs: int,
    re: int,
    master_seed: int,
):

    pairs = pd.read_csv(input_file)

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

    args = parser.parse_args()

    d = args.samples_dir.expanduser().resolve()

    if not d.is_dir():
        raise FileNotFoundError(
            f"Samples directory does not exist: {d}\n"
            "Place this script beside the generated_samples folder, "
            "or pass --samples-dir with the correct path."
        )

    # ---------------------------------------------------------
    # 64 x 48 preliminary / nested experiment
    # ---------------------------------------------------------

    generate_one(
        input_file=
            d / "nested_design_operational_pairs_64x48.csv",

        output_file=
            d / (
                f"nested_design_operational_pairs_64x48_"
                f"rs{args.rs}re{args.re}_"
                f"master{args.master_seed}.csv"
            ),

        expected_pairs=64 * 48,
        rs=args.rs,
        re=args.re,
        master_seed=args.master_seed,
    )

    # ---------------------------------------------------------
    # 128 x 96 primary experiment
    # ---------------------------------------------------------

    generate_one(
        input_file=
            d / "nested_design_operational_pairs_128x96.csv",

        output_file=
            d / (
                f"nested_design_operational_pairs_128x96_"
                f"rs{args.rs}re{args.re}_"
                f"master{args.master_seed}.csv"
            ),

        expected_pairs=128 * 96,
        rs=args.rs,
        re=args.re,
        master_seed=args.master_seed,
    )

    # ---------------------------------------------------------
    # 128 x 32 design-relative overload sensitivity
    # ---------------------------------------------------------

    generate_one(
        input_file=
            d / "overload_105pct_128x32.csv",

        output_file=
            d / (
                f"overload_105pct_128x32_"
                f"rs{args.rs}re{args.re}_"
                f"master{args.master_seed}.csv"
            ),

        expected_pairs=128 * 32,
        rs=args.rs,
        re=args.re,
        master_seed=args.master_seed,
    )


if __name__ == "__main__":
    main()