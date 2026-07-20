#!/usr/bin/env python3
"""
Convert node-init result JSON files (e.g. results_log/init_run_*.json) to Parquet.

Each input JSON has the shape:
    {
        "systemName": str,
        "totalNodes": int,
        "nodes": [
            {"nodeId": str, "resourcePower": float,
             "totalOutboundBandwidth": float, "totalInboundBandwidth": float},
            ...
        ]
    }

The "nodes" array is flattened into one row per node; "systemName" and
"totalNodes" are repeated on every row along with the source file name.

Parses JSON and builds Arrow tables in a process pool (JSON parsing is CPU
bound, so this scales across cores), and in --combine mode streams tables
into a single Parquet file as they finish instead of holding everything
in memory / concatenating at the end.
"""

import argparse
import json
import os
import sys
import time
from concurrent.futures import ProcessPoolExecutor, as_completed
from pathlib import Path

import pyarrow as pa
import pyarrow.parquet as pq


def build_table(path: Path) -> pa.Table:
    with path.open("r") as f:
        data = json.load(f)

    nodes = data.get("nodes", [])
    n = len(nodes)

    columns = {
        "sourceFile": [path.name] * n,
        "systemName": [data.get("systemName")] * n,
        "totalNodes": [data.get("totalNodes")] * n,
    }
    for key in (nodes[0].keys() if nodes else ()):
        columns[key] = [node[key] for node in nodes]

    return pa.table(columns)


def convert_one(args: tuple) -> tuple:
    path, output_dir = args
    table = build_table(path)
    out_path = output_dir / (path.stem + ".parquet")
    pq.write_table(table, out_path)
    return path.name, table.num_rows


def convert_dir(input_dir: Path, output_dir: Path, pattern: str, combine: bool, workers: int) -> None:
    files = sorted(input_dir.glob(pattern))
    if not files:
        print(f"No files matching '{pattern}' found in {input_dir}", file=sys.stderr)
        return

    output_dir.mkdir(parents=True, exist_ok=True)
    total = len(files)
    start = time.monotonic()
    report_every = max(1, total // 100)

    if combine:
        out_path = output_dir / "combined.parquet"
        writer = None
        total_rows = 0
        try:
            with ProcessPoolExecutor(max_workers=workers) as pool:
                for i, table in enumerate(pool.map(build_table, files, chunksize=16), 1):
                    if writer is None:
                        writer = pq.ParquetWriter(out_path, table.schema)
                    writer.write_table(table)
                    total_rows += table.num_rows
                    if i % report_every == 0 or i == total:
                        elapsed = time.monotonic() - start
                        print(f"[{i}/{total}] {elapsed:.1f}s elapsed, {total_rows} rows so far")
        finally:
            if writer is not None:
                writer.close()
        print(f"Wrote {total} JSON files -> {out_path} ({total_rows} rows)")
        return

    with ProcessPoolExecutor(max_workers=workers) as pool:
        futures = [pool.submit(convert_one, (path, output_dir)) for path in files]
        for i, future in enumerate(as_completed(futures), 1):
            name, rows = future.result()
            if i % report_every == 0 or i == total:
                elapsed = time.monotonic() - start
                print(f"[{i}/{total}] {elapsed:.1f}s elapsed (last: {name}, {rows} rows)")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("input_dir", type=Path, help="Folder containing JSON files")
    parser.add_argument("output_dir", type=Path, help="Folder to write Parquet files to")
    parser.add_argument(
        "--pattern", default="*.json", help="Glob pattern for input files (default: *.json)"
    )
    parser.add_argument(
        "--combine",
        action="store_true",
        help="Write a single combined.parquet instead of one file per input JSON",
    )
    parser.add_argument(
        "--workers",
        type=int,
        default=os.cpu_count(),
        help="Number of worker processes (default: number of CPUs)",
    )
    args = parser.parse_args()

    convert_dir(args.input_dir, args.output_dir, args.pattern, args.combine, args.workers)


if __name__ == "__main__":
    main()
