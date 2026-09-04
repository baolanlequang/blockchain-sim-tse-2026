# Simulator Handover for Revised Sections IV and V

## Purpose and current status

This note summarizes the simulator behavior, implementation refinements, validation checks, and remaining review items needed to support the revised Sections IV and V.

The main simulator refinement has already been placed on the GitHub review branch:

- repository: `https://github.com/baolanlequang/blockchain-sim-tse-2026`
- branch: `feature-sections-iv-v-simulator-refinement`
- base: `lanle_running_simulation-generator-fix`
- initial refinement commit: `a76e1ee00` — `Align simulator with revised Sections IV and V`
- reproducible-build/regression commit: `fc35473ea` — `Add reproducible refined build and regression smoke`

During the subsequent plausibility campaign, additional runtime/correctness fixes were identified in the clean worktree branch `plausibility-validation`. These post-handover fixes must be reviewed and incorporated into the production branch before the main experiments are launched.

The goal of Lan's review is to confirm that the reviewed GitHub source implements the behavior specified by the paper and that a clean checkout can reproduce the validated execution path.

---

## Frozen main-experiment settings

The following settings are fixed for the main experiments:

- network realizations per design--operational pair: `R_S = 2`
- event replications per network realization: `R_E = 2`
- warm-up blocks per validating node: `kappa_warm = 4`
- measured blocks per validating node: `kappa_measure = 4`
- maximum transaction follow-up: `T_drain = 14,400 s`
- master seed: `1024`
- internal simulator mode: one explicit execution per manifest row (`Single`, internal Monte Carlo rounds = 1)

These are tractability/stability choices from calibration and should not be interpreted as guaranteeing a predefined Monte Carlo precision for every response metric.

---

## 1. Hierarchical execution and random seeds

The revised method separates structural and event-level randomness.

### Network realization (`R_S`)

Each network realization receives its own `network_seed` and regenerates structural/resource characteristics, including:

- topology permutation;
- node-bandwidth allocation;
- link-bandwidth allocation;
- hashing-power allocation;
- adversarial-node assignment;
- fixed connection-latency realization where applicable.

### Event replication (`R_E`)

Event replications belonging to the same network realization retain the same structural realization and `network_seed`, but use distinct `event_seed` values for event-level stochasticity such as mining, transaction arrivals, tie resolution, and selfish-mining events.

For `R_S = 2` and `R_E = 2`, each design--operational pair therefore has four explicit executions. The engine must execute one simulation per manifest row rather than combine this hierarchy with a large internal Monte Carlo loop.

The manifest/runner should validate the hierarchy and preserve `network_instance`, `event_replication`, `network_realization_id`, `run_id`, `network_seed`, and `event_seed`.

---

## 2. Parameter semantics

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

The serialized result must retain the sampled values needed to reconstruct each design--operational pair.

---

## 3. Topology, resources, hashing power, and adversaries

The revised interpretation of `C` is that every validating node initiates exactly `C` connections and accepts exactly `C` connections; established links are bidirectional, so realized degree is `2C`. The generator must enforce `C >= 1`, `2C <= N_V - 1`, connectedness, and the absence of self-, duplicate-, and reciprocal-duplicate connections.

`H_node`, `H_link`, and `H_hash` are target/expected normalized concentration values. For an allocation among `m` recipients and target `H > 0`, the simulator uses the symmetric Dirichlet concentration

`alpha = (1 - H) / (m * H)`

and equal allocation for `H = 0`. Both configured and realized concentration values must be retained. Resource conservation checks must remain available in the audit.

Hashing-power shares must sum to one, and validator `i` must produce blocks according to its assigned share while preserving the aggregate expected interval `BCI`.

Adversarial validators are selected independently of resource allocations. Results must retain sampled `f_A`, attacker count, realized attacker fraction, and realized adversarial hashing-power share.

---

## 4. Transaction demand and execution phases

The primary experiment treats `lambda_tx` as an absolute Poisson transaction-arrival rate and must not silently rescale it by `BCI` or `MBS`.

Each execution has three phases:

1. warm-up: `kappa_warm * N_V` canonical blocks;
2. measurement: `kappa_measure * N_V` additional canonical blocks;
3. transaction follow-up: each transaction submitted during measurement is followed until confirmation or `T_drain` after its own submission time.

Transactions unresolved at the individual follow-up horizon remain unconfirmed and are right-censored for confirmation-latency analysis.

Failed or incomplete executions must remain visible in the audit and must not be silently replaced.

---

## 5. Refined metrics and authoritative result fields

Production analysis should use the refined execution-audit fields where definitions differ from legacy outputs.

Required metrics include:

- proposal entropy (`H_prop`);
- canonical-chain normalized HHI (`HHI_canon`);
- transaction throughput (`TPS`);
- transaction confirmation ratio (`TCR`);
- stale-block ratio (`SBR`);
- restricted mean confirmation latency (`RMCL`, with Kaplan--Meier/right-censor handling);
- selfish-mining success probability (`SPSM`) when defined.

Use consistently:

`SBR = N_stale / (N_canonical + N_stale)`

For `SPSM`, pool successful and unambiguous attack-round counts across the replications of a design--operational pair and leave the metric undefined when no adversarial validator or no unambiguous attack round is observed.

---

## 6. Post-pilot correctness and scalability fixes that require review

Three important issues were identified during the plausibility campaign after the original handover was prepared.

### A. Measurement boundary and drain termination — resolved

The earlier handover asked Lan to investigate an occasional one-block discrepancy at the measurement boundary. This is no longer an open question. The monitor was corrected so that measurement-local canonical progress is used consistently and the drain phase terminates explicitly at the transaction follow-up deadline.

Regression cases that previously reported `79/81` measurement blocks now complete at the exact target and report `transactionFollowUpCompleted = true` with `DRAIN_DEADLINE_REACHED`.

Patch retained for review: `plausibility-runtime-fix.patch`.

### B. Memory scalability improvements — applied

High-load/larger-block plausibility runs exposed avoidable memory pressure. The following behavior-preserving changes were applied:

- removed a redundant transaction-announcement ID set;
- changed mempool selection to prefix traversal up to the block-size limit;
- avoided copying the complete mempool for transaction selection.

A regression comparison differed only by floating-point roundoff in proposal entropy (approximately `2e-16`).

Patch retained for review: `plausibility-memory-scalability-fix.patch`.

### C. Orphan-chain mining-restart correctness — critical fix

A correctness bug was found in `BehaviorUtils.appendBlockToBlockchain()`. When a newly received ancestor unlocked previously orphaned descendants and one of those descendants created the new longest chain, the method returned only whether the initially received ancestor was an included block. Consequently, mining did not always restart on the newly longest descendant tip, which could sustain chain divergence under out-of-order block arrivals.

The fix returns `hasNewLongestBranch`, thereby propagating the recursively detected longest-chain change to the caller and restarting mining on the correct tip.

Patch retained for review: `plausibility-orphan-chain-restart-fix-v2.patch`.

A clean Maven/Tycho rebuild succeeded with zero Checkstyle failures. The validated standalone plausibility JAR was:

`trilemma-plausibility-orphanfix-diagnostic.jar`

SHA-256:

`E461309811DB5BDA4FE1FD216EC6013456DAF647A951F4E7708BBA5391C23099`

Lan should review and commit/integrate these post-pilot fixes into the production branch before the main experiment rather than relying on a local-only JAR.

---

## 7. Final plausibility-suite status

A final A--D plausibility suite was executed using the post-fix simulator with `R_S = 4`, `R_E = 4` for each factor level. This plausibility replication design is separate from the main-experiment `R_S = R_E = 2` settings.

Strict audit result:

- manifest rows: `256`
- matched result JSONs: `256`
- missing results: `0`
- unexpected results: `0`
- invariant failures: `0`
- P-A: `64` executions
- P-B: `64` executions
- P-C: `64` executions
- P-D: `64` executions

All 256 executions completed their measurement windows and transaction follow-up and met the canonical-block target invariant.

### Behavioral outcomes

- **P-A (block creation interval): Strong Pass.** Mean SBR decreased monotonically from `0.168469` at 60 s to `0.010692` at 1,200 s; expected endpoint in `4/4` matched network realizations.
- **P-B (maximum block size): Fail under the prespecified criterion.** Realized canonical-block transaction payload increased from `0.217725 MB` to `1.665255 MB`, but mean SBR changed from `0.287276` to `0.277413`; only `1/4` matched network realizations showed the expected endpoint direction. This result should be retained and not tuned away.
- **P-C (transaction load): Strong Pass.** TPS approached nominal capacity while TCR decreased and RMCL/censoring increased; joint expected direction in `4/4` matched network realizations.
- **P-D (hashing-power concentration): Strong Pass for the behavioral endpoints.** Realized concentration increased, proposal entropy decreased, and canonical HHI increased monotonically; expected endpoint in `4/4` matched network realizations.

The aggregate audit used for P-D did not expose the per-validator assigned-hash-share versus proposal-share comparison, so that specific manipulation check should not be claimed until separately extracted/verified.

Selfish-mining/SM-SIM reference validation (P-E) remains a separate like-for-like reference check and should not be conflated with the A--D aggregate plausibility suite.

---

## 8. Result audit/provenance requirements

Each production result should preserve enough information to audit:

- design and operational inputs;
- master/network/event seeds and hierarchy identifiers;
- topology and allocation checks/fingerprints where available;
- target and realized heterogeneity;
- attacker count/fraction/hash share;
- warm-up and measurement targets;
- measurement-window completion;
- transaction-follow-up completion;
- measurement submitted/confirmed/right-censored transaction counts;
- refined metric values;
- explicit failure/incomplete status.

Preflight and strict result-audit scripts should be retained with the replication package.

---

## 9. Items for Lan to verify before the main experiment

1. **Integrate the post-pilot fixes.** Review the runtime, memory-scalability, and orphan-chain restart patches and commit the accepted implementation to the production/review branch.
2. **Clean-source reproducibility.** Build the standalone JAR from a clean checkout and confirm that the critical classes and refined monitor behavior are present.
3. **Hierarchical manifest semantics.** Confirm `declaredSamplePairs`, CSV slicing, and SLURM execution preserve the experiment-level manifest semantics and one-row-per-engine-execution design.
4. **Legacy metric protection.** Ensure production analysis cannot accidentally use legacy metric definitions, especially stale-block ratio.
5. **Cluster benchmarking.** Re-benchmark heap size and rows per task under the one-row-per-execution model. The plausibility suite used `-Xms2g -Xmx16g`; this is a validated plausibility setting, not automatically the optimal cluster setting.
6. **P-D diagnostic availability.** If the manuscript will report that proposal shares track assigned hashing-power shares, expose or extract the required per-validator data and verify the relationship explicitly.
7. **Tag/freeze the reviewed production version.** Main experiments should be launched only from the reviewed commit/tag, with the JAR checksum and manifest generator version recorded.

---

## 10. Recommended smoke test before production

From a clean checkout of the reviewed production commit:

1. build the standalone JAR;
2. execute one small hierarchical pair with `R_S = 2`, `R_E = 2`;
3. verify four result files/run IDs;
4. confirm two distinct network realizations, common network seeds within each realization, distinct event seeds, topology/allocation checks, exact measurement canonical targets, completed transaction follow-up, and a passing strict result audit;
5. preserve the run and audit output as a regression example in the replication package.

---

## Handover objective

Lan's review should focus on the exact source that will be used for the main experiments. The production simulator should incorporate the reviewed post-pilot correctness fixes, build reproducibly from a clean checkout, preserve the hierarchical seed/execution semantics, and emit the refined audit fields used by the paper's analysis.
