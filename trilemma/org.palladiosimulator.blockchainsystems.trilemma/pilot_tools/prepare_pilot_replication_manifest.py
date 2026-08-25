#!/usr/bin/env python3
"""Create the explicit R_S x R_E pilot execution manifest for revised 3SIM.

The script keeps one network_seed fixed across all R_E event replications of an
R_S network instance and gives each event replication its own event_seed.
The input should be the authoritative pilot table containing pilot_id and the
sampled/derived parameters required by TrilemmaSimulator.
"""
from __future__ import annotations

import argparse
from pathlib import Path
import pandas as pd

MASK = (1 << 64) - 1


def _u64(x: int) -> int:
    return x & MASK


def _signed64(x: int) -> int:
    x &= MASK
    return x if x < (1 << 63) else x - (1 << 64)


def derive_seed(base_seed: int, label: str) -> int:
    # Mirrors RefinedExperimentRandomness.deriveSeed: FNV-1a-ish label mix,
    # followed by SplitMix64 finalization, with Java Long overflow semantics.
    h = _u64(0xCBF29CE484222325 ^ _u64(base_seed))
    for b in label.encode("utf-8"):
        h = _u64(h ^ b)
        h = _u64(h * 0x100000001B3)
    z = _u64(h + 0x9E3779B97F4A7C15)
    z = _u64((z ^ (z >> 30)) * 0xBF58476D1CE4E5B9)
    z = _u64((z ^ (z >> 27)) * 0x94D049BB133111EB)
    z = _u64(z ^ (z >> 31))
    return _signed64(z)


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--input", type=Path, required=True)
    ap.add_argument("--output", type=Path, required=True)
    ap.add_argument("--rs", type=int, default=2, help="Network instances per pilot pair")
    ap.add_argument("--re", type=int, default=2, help="Event replications per network instance")
    ap.add_argument("--master-seed", type=int, default=1024)
    ap.add_argument("--pilot-id", default=None, help="Optional single pilot id, e.g. P01")
    args = ap.parse_args()

    if args.rs < 1 or args.re < 1:
        ap.error("--rs and --re must be >= 1")

    df = pd.read_csv(args.input)
    required = {
        "pilot_id",
        "connection_count",
        "block_creation_interval",
        "maximum_block_size",
        "validating_node_count",
        "node_bandwidth_heterogeneity",
        "link_bandwidth_heterogeneity",
        "hashing_power_concentration",
        "fraction_of_attackers",
        "number_of_attackers",
        "realized_fraction_of_attackers",
        "transaction_arrival_rate",
    }
    missing = sorted(required - set(df.columns))
    if missing:
        raise SystemExit(f"Input is missing required column(s): {', '.join(missing)}")

    if args.pilot_id:
        df = df[df["pilot_id"].astype(str) == args.pilot_id].copy()
        if df.empty:
            raise SystemExit(f"No row found for pilot_id={args.pilot_id}")

    out_rows = []
    execution_index = 0
    for _, row in df.iterrows():
        pid = str(row["pilot_id"])
        for s in range(1, args.rs + 1):
            network_id = f"{pid}_S{s:02d}"
            network_seed = derive_seed(args.master_seed, f"pilot|{pid}|network|{s}")
            for e in range(1, args.re + 1):
                execution_index += 1
                event_seed = derive_seed(args.master_seed, f"pilot|{pid}|network|{s}|event|{e}")
                record = row.to_dict()
                record.update({
                    "execution_index": execution_index,
                    "manifest_pair_id": pid,
                    "network_instance": s,
                    "event_replication": e,
                    "network_realization_id": network_id,
                    "network_seed": network_seed,
                    "event_seed": event_seed,
                    "run_id": f"{network_id}_E{e:02d}",
                })
                out_rows.append(record)

    out = pd.DataFrame(out_rows)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    out.to_csv(args.output, index=False)
    print(f"Wrote {len(out)} execution rows to {args.output}")
    print(f"Distinct pilot pairs: {out['manifest_pair_id'].nunique()}")
    print(f"R_S={args.rs}, R_E={args.re}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
