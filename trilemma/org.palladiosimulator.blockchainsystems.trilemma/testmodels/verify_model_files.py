#!/usr/bin/env python3
"""Generate and verify a manifest of model files under sampling/.

Covers the three generated model directories, each containing many
threesim-N subfolders with the same set of model files:
  - ./

Usage:
    python verify_model_files.py generate [--root sampling] [--manifest manifest.json]
    python verify_model_files.py check <copy_root> [--manifest manifest.json]
"""

import argparse
import hashlib
import json
import sys
from pathlib import Path

MODEL_DIRS = [
    "."
]


def sha256sum(path: Path) -> str:
    digest = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def build_manifest(root: Path) -> dict:
    manifest = {}
    for model_dir in MODEL_DIRS:
        base = root / model_dir
        if not base.is_dir():
            continue
        for path in sorted(base.rglob("*")):
            if path.is_file():
                rel = path.relative_to(root)
                manifest[str(rel)] = {
                    "size": path.stat().st_size,
                    "sha256": sha256sum(path),
                }
    return manifest


def cmd_generate(args) -> int:
    root = Path(args.root)
    manifest = build_manifest(root)
    with open(args.manifest, "w") as f:
        json.dump(manifest, f, indent=2, sort_keys=True)
    print(f"Wrote manifest with {len(manifest)} files to {args.manifest}")
    return 0


def cmd_check(args) -> int:
    with open(args.manifest) as f:
        manifest = json.load(f)

    copy_root = Path(args.copy_root)
    missing = []
    size_mismatch = []
    checksum_mismatch = []
    checked = 0

    for rel, expected in manifest.items():
        target = copy_root / rel
        if not target.is_file():
            missing.append(rel)
            continue
        actual_size = target.stat().st_size
        if actual_size != expected["size"]:
            size_mismatch.append((rel, expected["size"], actual_size))
            continue
        actual_sha256 = sha256sum(target)
        if actual_sha256 != expected["sha256"]:
            checksum_mismatch.append((rel, expected["sha256"], actual_sha256))
        checked += 1

    print(f"Checked {checked}/{len(manifest)} files against {copy_root}")

    if missing:
        print(f"\nMissing ({len(missing)}):")
        for rel in missing:
            print(f"  {rel}")

    if size_mismatch:
        print(f"\nSize mismatch ({len(size_mismatch)}):")
        for rel, expected_size, actual_size in size_mismatch:
            print(f"  {rel}: expected {expected_size} bytes, found {actual_size} bytes")

    if checksum_mismatch:
        print(f"\nChecksum mismatch ({len(checksum_mismatch)}):")
        for rel, expected_sha256, actual_sha256 in checksum_mismatch:
            print(f"  {rel}: expected {expected_sha256}, found {actual_sha256}")

    if not missing and not size_mismatch and not checksum_mismatch:
        print("\nAll files present with matching size and checksum.")
        return 0
    return 1


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="command", required=True)

    gen = sub.add_parser("generate", help="Build a manifest from the current sampling/ directory")
    gen.add_argument("--root", default=".", help="Path to the sampling/ directory (default: current dir)")
    gen.add_argument("--manifest", default="model_files_manifest.json", help="Output manifest path")
    gen.set_defaults(func=cmd_generate)

    chk = sub.add_parser("check", help="Check a copy of the model dirs against a manifest")
    chk.add_argument("copy_root", help="Path to the copy of sampling/ to verify")
    chk.add_argument("--manifest", default="model_files_manifest.json", help="Manifest path")
    chk.set_defaults(func=cmd_check)

    args = parser.parse_args()
    sys.exit(args.func(args))


if __name__ == "__main__":
    main()
