# Plausibility Validation Note

## Context

Following a discussion with Niclas about simulator assurance, we decided to add
a separate plausibility-validation stage before freezing the refined simulator
for the main experiments.

The purpose was to assess whether the simulator reproduces established
qualitative relationships for Nakamoto-style blockchain systems under
controlled configurations. This plausibility stage is separate from the main
experimental design and uses its own replication settings.

During the plausibility work, we identified implementation issues affecting
execution correctness under some configurations. These issues were corrected
before conducting the final plausibility suite.

## Plausibility-Validation Design

The final P-A--P-D suite used:

- 4 network realizations per factor level (`R_S = 4`)
- 4 event replications per network realization (`R_E = 4`)
- 4 factor levels per test
- 256 simulator executions in total

The four behavioral plausibility tests were:

- **P-A -- Block creation interval:** shorter intervals were expected to
  increase stale-block pressure.
- **P-B -- Maximum block size:** larger realized blocks were expected to
  increase propagation-related fork pressure.
- **P-C -- Transaction load:** increasing load toward and beyond nominal
  capacity was expected to produce throughput saturation, lower transaction
  confirmation ratio, higher restricted mean confirmation latency, and more
  right censoring.
- **P-D -- Hashing-power concentration:** greater concentration was expected
  to reduce block-proposal entropy and increase canonical-block concentration.

Selfish-mining behavior is treated separately as a reference-model comparison
against SM-SIM and is not part of the 256 P-A--P-D executions.

## Implementation Fixes Identified During Plausibility Testing

Two correctness issues were identified and addressed:

1. Measurement-window and transaction-drain completion handling was corrected
   so that completed executions satisfy the intended canonical-block and
   transaction-follow-up invariants.

2. Orphan-chain handling was corrected so that, when the arrival of an ancestor
   causes previously orphaned descendants to form a new longest chain, mining
   restarts on the resulting longest-chain tip.

The second issue was particularly important under configurations producing
larger blocks and greater out-of-order propagation.

## Final Execution Audit

The complete final P-A--P-D suite passed the strict execution audit:

- Manifest rows: 256
- Distinct expected run IDs: 256
- Matched result JSONs: 256
- Missing results: 0
- Unexpected results: 0
- Invariant failures: 0
- All measurement windows completed
- All transaction follow-up periods completed

Each plausibility group contributed 64 valid executions.

## Behavioral Plausibility Results

The prespecified directional assessment produced:

- **P-A -- Block creation interval: Strong Pass**
  - Mean refined stale-block ratio decreased monotonically from 0.1685 at
    60 s to 0.0107 at 1,200 s.
  - Expected endpoint direction occurred in 4/4 matched network realizations.

- **P-B -- Maximum block size: Fail**
  - Mean realized transaction payload increased from approximately
    0.218 MB/block to 1.665 MB/block.
  - However, mean stale-block ratio changed from 0.2873 to 0.2774 rather than
    increasing.
  - Only 1/4 matched network realizations showed the expected endpoint
    direction.
  - This result is retained rather than tuning the simulator to obtain the
    expected relationship.

- **P-C -- Transaction load: Strong Pass**
  - Throughput approached nominal capacity as load increased.
  - Transaction confirmation ratio decreased.
  - Restricted mean confirmation latency increased.
  - Right censoring increased.
  - The joint expected endpoint direction occurred in 4/4 matched network
    realizations.

- **P-D -- Hashing-power concentration: Strong Pass**
  - Realized hashing-power concentration increased with the target value.
  - Block-proposal entropy decreased monotonically.
  - Canonical-block concentration increased monotonically.
  - The expected endpoint direction occurred in 4/4 matched network
    realizations.

The aggregate audit used for this assessment does not contain the
per-validator proposal-share versus assigned-hash-share comparison, so that
specific manipulation check should not yet be reported as verified.

## Requested Review Before Main Experiments

Before freezing the simulator for the main experiments, please review the
post-refinement source changes, particularly:

- the measurement/drain completion logic; and
- the orphan-chain longest-branch/mining-restart behavior.

The more detailed implementation history and experiment settings are provided
in `SIMULATOR_HANDOVER.md`.
