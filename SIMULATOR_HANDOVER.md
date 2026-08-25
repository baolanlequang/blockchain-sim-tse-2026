# Simulator Handover for Revised Sections IV and V

## Purpose

This note summarizes the simulator changes and checks needed to support the revised methodological design in Sections IV and V.

The refined simulator used during the pilot currently exists on the local development machine and has **not yet been pushed to GitHub**. Before the subsequent experiments, the refined source should be pushed to a separate GitHub branch so that Lan can review the exact implementation, compare it with the previous simulator, make any necessary corrections, and use a reproducible version for the production runs.

This document describes the required **behaviour** of the simulator. Lan can decide the most appropriate implementation details.

---

## Recommended handover workflow

1. Push the current refined simulator source to a dedicated branch, for example:
   `method-revision-simulator`.
2. Do not overwrite the previous stable branch immediately.
3. Include this handover file in that branch.
4. Lan reviews the implementation against Sections IV and V and this checklist.
5. Any remaining corrections are committed to the same branch.
6. Build the standalone JAR from a clean checkout and run a small regression/smoke test.
7. Merge or tag the reviewed version before the main experiments.

Eclipse can continue to be used for development and debugging. For the actual experiments, the recommended execution path is the standalone JAR with explicit CSV manifests/configuration files, including batch or SLURM execution where appropriate. This keeps the production runs reproducible and independent of an Eclipse workspace configuration.

---

## Frozen execution settings from the calibration pilot

The following settings are now fixed for the subsequent experiments:

- Network instances per design--operational pair: `R_S = 2`
- Event replications per network instance: `R_E = 2`
- Warm-up blocks per validating node: `kappa_warm = 4`
- Measured blocks per validating node: `kappa_measure = 4`
- Maximum transaction follow-up: `T_drain = 14,400 s`
- Master seed: `1024`

The pilot was used as a practical calibration/stability exercise. These settings should not be described as having satisfied an exhaustive 24-case Monte Carlo precision study.

---

## 1. Hierarchical execution and random seeds

The revised method separates two sources of randomness:

### Network realization (`R_S`)

Each network instance must generate a new structural/resource realization, including:

- validator placement/topology permutation;
- node-bandwidth allocation;
- link-bandwidth allocation;
- hashing-power allocation;
- adversarial-node assignment.

Each network realization must have its own `network_seed`.

### Event replication (`R_E`)

Event replications belonging to the same network realization must:

- retain the same network/resource/adversarial realization;
- retain the same `network_seed`;
- use a different `event_seed`;
- generate new block-production and transaction-arrival timing.

For `R_S = 2` and `R_E = 2`, every design--operational pair therefore has four explicit executions:

```text
PAIR_S01_E01
PAIR_S01_E02
PAIR_S02_E01
PAIR_S02_E02
```

### Important change from the previous execution model

The simulator engine should execute **one simulation per explicit manifest row**.

Do not combine the new `R_S/R_E` hierarchy with the previous approach of running hundreds of internal Monte Carlo rounds for each row. In production, internal engine Monte Carlo rounds should remain one unless the revised method is deliberately changed again.

The manifest/runner should validate that:

- `network_seed` is constant across the `R_E` replications of the same network realization;
- `event_seed` is unique within a network realization;
- `network_instance`, `event_replication`, `network_realization_id`, and `run_id` are present and consistent.

---

## 2. Revised parameter semantics

The implementation should correspond to the current parameter ontology.

### Design parameters

- `C`: connection count
- `BCI`: block creation interval
- `MBS`: maximum block size

### Operational parameters

- `N_V`: validating-node count
- `H_node`: node-bandwidth heterogeneity
- `H_link`: link-bandwidth heterogeneity
- `H_hash`: hashing-power concentration
- `f_A`: fraction of attackers
- `lambda_tx`: transaction-arrival rate

The input configuration/manifest and serialized result should retain the sampled values needed to reconstruct each design--operational pair.

---

## 3. Peer topology

The revised interpretation of `C` is:

- every validating node initiates exactly `C` connections;
- every validating node accepts exactly `C` connections;
- established links carry messages in both directions;
- realized degree is therefore `2C`.

For the sampled parameter space, topology generation should enforce or verify:

- `C >= 1`;
- `2C <= N_V - 1`;
- connected topology;
- no self-connections;
- no duplicate connection;
- no reciprocal duplicate initiation.

The random network permutation should change which independently generated resource/adversarial assignments become adjacent without changing the regular degree structure.

---

## 4. Resource heterogeneity

`H_node`, `H_link`, and `H_hash` are **target/expected normalized concentration values**, not deterministic realized HHI values.

For an allocation among `m` recipients and target normalized concentration `H`, the current method uses the symmetric Dirichlet concentration

```text
alpha = (1 - H) / (m * H)
```

for `H > 0`, with equal allocation when `H = 0`.

The simulator should record both:

- the configured/target `H`;
- the realized concentration produced by the random allocation.

A single execution should not be rejected merely because its realized concentration differs from the target expectation.

The implementation should also verify/record conservation constraints such as:

- node-bandwidth shares/budgets sum correctly;
- hashing-power shares sum to one.

---

## 5. Bandwidth and links

The revised simulator allocates bandwidth consistently with the regular topology and the fixed baseline bandwidth-per-endpoint assumption.

The implementation should preserve:

- the configured system bandwidth budget;
- node-level allocation;
- link-level allocation;
- the bandwidth uniform-floor rule;
- effective connection bandwidth;
- connection latency.

The refined audit should retain enough information to verify these properties after execution.

---

## 6. Hashing power and block production

Hashing power is allocated across validators according to `H_hash`.

Validator `i` should produce blocks according to its allocated hashing-power share so that the aggregate expected block-production interval remains `BCI`.

The implementation should retain both target and realized hashing-power concentration.

---

## 7. Adversarial assignment

The sampled operational variable is the attacker fraction `f_A`.

The implementation should convert this to an integer number of adversarial validators using the current rule, while recording:

- sampled `f_A`;
- integer attacker count;
- realized attacker-node fraction;
- realized adversarial hashing-power share.

Adversarial validators must be selected independently of the hashing-power/resource allocation. The attacker count must therefore not determine the adversarial hashing-power share.

---

## 8. Transaction demand

The primary experiment treats `lambda_tx` as an **absolute transaction-arrival rate**.

Transactions should arrive as a Poisson process with exponentially distributed interarrival times.

For the primary crossed experiment, `lambda_tx` should not be silently rescaled by `BCI` or `MBS`. The same sampled operational demand condition is intended to be applied to every design configuration.

The separate load-sensitivity experiment is different: its arrival rate is deliberately derived from each design's nominal capacity and must remain identifiable as a separate experiment.

---

## 9. Warm-up, measurement, and drain phases

Each execution must distinguish:

1. warm-up;
2. measurement;
3. transaction follow-up/drain.

### Warm-up

Warm-up target:

```text
kappa_warm * N_V
```

canonical-block progress.

Warm-up observations must not contribute to the reported measurement metrics.

### Measurement

After warm-up, measurement continues for the configured additional canonical-block progress based on:

```text
kappa_measure * N_V
```

The production settings use:

```text
kappa_warm = 4
kappa_measure = 4
```

### Transaction follow-up

Transactions submitted during measurement are followed until either:

- the confirmation criterion is met; or
- `T_drain = 14,400 s` after submission is reached.

Transactions unresolved at this horizon remain unconfirmed and are treated as right-censored observations for confirmation latency.

Failed or incomplete executions should remain visible in the audit rather than being silently replaced.

---

## 10. Refined metrics and result source

The subsequent analysis should use the **refined execution audit/results**, rather than legacy result fields where the definitions differ.

Required metrics include:

- proposal entropy (`H_prop`);
- canonical-chain normalized HHI (`HHI_canon`);
- transaction throughput (`TPS`);
- transaction confirmation rate (`TCR`);
- stale-block rate (`SBR`);
- confirmation latency (`CL`, using restricted mean/Kaplan--Meier handling of censoring);
- selfish-mining success probability (`SPSM`) when defined.

### SBR definition

Use consistently:

```text
SBR = N_stale / (N_canonical + N_stale)
```

The manuscript and result extractor should use this same normalization.

### SPSM

For a design--operational pair:

- pool selfish-mining attack rounds across its network instances and event replications;
- divide successful rounds by unambiguous rounds;
- leave SPSM undefined when there is no adversarial validator or no unambiguous attack round;
- do not replace undefined SPSM with zero.

---

## 11. Result audit/provenance

Each execution result should preserve enough information to audit the experiment, including at least:

- design and operational inputs;
- master seed;
- `network_seed`;
- `event_seed`;
- network/event hierarchy identifiers;
- topology checks/fingerprint;
- allocation checks/fingerprints where available;
- target and realized heterogeneity;
- attacker count/fraction/hash share;
- warm-up and measurement targets;
- measurement completion status;
- drain/follow-up completion status;
- measurement submitted/confirmed/censored transaction counts;
- refined metric values;
- explicit failure/incomplete status where applicable.

Preflight and result-audit scripts should be retained for production execution.

---

## 12. Items for Lan to verify before the main experiment

### A. Clean-source reproducibility

Please verify that every change used in the pilot exists in the actual source projects committed to GitHub, and not only in generated or staged build directories such as `standalone-build/fatjar-stage`.

Build a fresh standalone JAR from a clean checkout and confirm that a known pilot/smoke case reproduces the expected execution structure and passes the audit.

### B. Measurement boundary accounting

During the pilot, one result reached the intended cumulative measurement-window target but reported one fewer `measurementCanonicalBlocks` than the nominal `kappa_measure * N_V` count.

Please check whether this is an intentional interval-boundary convention or an implementation/accounting issue. The controller appears to reach its configured cumulative canonical target, but the audit field should be semantically clear before production.

### C. Production `declaredSamplePairs`

Pilot configurations used `declaredSamplePairs = 1` because P01 contained one sampled pair.

Please verify how this field should behave for the full experiment and for CSV/SLURM slices. It should not accidentally remain fixed at one or make a sliced batch inconsistent with the experiment-level manifest.

### D. Production manifest generator

The pilot generator already implements the required seed hierarchy. It would be preferable to generalize/rename it for production use (for example `prepare_replication_manifest.py`) while preserving the same deterministic seed logic and validation.

### E. Legacy metric fields

Please ensure that downstream users cannot accidentally extract legacy metrics whose definitions differ from the refined definitions, especially stale-block rate. Either align the legacy value, clearly deprecate it, or make the production analysis use the refined audit explicitly.

### F. Cluster execution

The previous SLURM configuration was designed around many internal Monte Carlo rounds. Re-benchmark resources under the new one-row-per-execution design before fixing:

- JVM heap size;
- CPUs per task;
- rows per SLURM task;
- job-array size.

The existing standalone JAR/CSV/SLURM approach can still be retained.

---

## 13. Suggested regression/smoke test before production

Before launching the full experiment:

1. build the JAR from a clean checkout;
2. execute one small hierarchical pair with `R_S = 2`, `R_E = 2`;
3. verify four result files/run IDs;
4. confirm:
   - two distinct network realizations;
   - same network seed within each pair of event replications;
   - distinct event seeds;
   - topology/allocation checks pass;
   - warm-up and measurement complete;
   - transaction follow-up completes;
   - result audit passes;
5. preserve this small run as a regression example in the replication package.

---

## Handover objective

The goal is not to reproduce the exact PowerShell commands used during pilot development. The goal is to ensure that the reviewed GitHub version implements the behaviour specified by Sections IV and V and that the same version can be built and executed reproducibly by Lan for the subsequent experiments.
