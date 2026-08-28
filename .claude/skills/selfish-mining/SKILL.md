---
name: Selfish mining and stubborn mining — paper model vs Trilemma implementation
description: Explains the Eyal & Sirer 2014 selfish-mining model and the Nayak et al. 2016 stubborn-mining generalizations, and maps them precisely onto Trilemma's attacker behavior classes, including known bugs already fixed.
---
# Instructions

## Source papers
- **Selfish mining**: Eyal & Sirer, "Majority is not Enough: Bitcoin Mining is Vulnerable" (2014), https://arxiv.org/abs/1311.0243
- **Stubborn mining**: Nayak, Kumar, Miller, Shi, "Stubborn Mining: Generalizing Selfish Mining and Combining with an Eclipse Attack" (2016), https://users.cs.duke.edu/~kartik/papers/5_stubborn_eclipse.pdf

## The Eyal & Sirer selfish-mining model

Two parties: a selfish pool with hash power α, and honest miners with (1-α). γ is the fraction of
honest miners that mine on the pool's block during a tie (network propagation advantage).

**State** = the pool's current *hidden lead* over the public chain: how many unpublished blocks the
pool is ahead by. State 0 = no lead (single shared chain). State 0' = a *tie*: two public branches of
equal length exist because the pool just published to fight back from a 1-block lead. States 1, 2,
3, ... = pool secretly ahead by that many blocks.

**Algorithm 1 (paper's Selfish-Mine), by event:**
- **Pool mines a block:** always extend the pool's own private chain (increment lead). Exception: from
  state 0' (a tie, no more hidden material), a pool-mined block wins the tie outright — reveal
  everything now to win both blocks.
- **Others (honest) mine a block, at lead 0:** pool adopts the public chain (it had nothing to defend).
- **at lead 1:** publish the pool's single hidden block immediately, creating a tie (state 0').
- **at lead 2:** publish the entire private branch (2 blocks) — the pool's branch is now strictly
  longer, so it wins outright.
- **at lead > 2:** publish only the first unpublished block from the private chain (keeps the rest
  hidden, preserves the remaining lead). Revenue-neutral either way since the pool's branch stays
  strictly longest regardless.

**Revenue formula (Eq. 8, for 0 ≤ α ≤ 1/2):**
```
R_pool = [α(1-α)²(4α + γ(1-2α)) - α³] / [1 - α(1 + (2-α)α)]
```
**Profitability / success criterion (Observation 1):** the pool earns more than its fair share iff
```
(1-γ)/(3-2γ) < α < 1/2
```
i.e. **R_pool > α**. This inequality — realized revenue share exceeding hash-power share — is the
success criterion, not the closed-form formula itself (the paper derives the closed form but
validates it against Monte Carlo simulation, Section 4.3 — simulating and measuring is the paper's
own validation method, not an approximation of it).

## Nayak et al. stubborn-mining generalizations

All three keep the paper's revenue-share success criterion (R_pool > α) and differ only in specific
state transitions from vanilla selfish mining:

- **Lead-stubborn (L-stubborn):** for **any** hidden lead k ≥ 1 (not just k=1), when honest miners
  catch up, reveal **exactly one** block (not all, not none), landing in a tied-fork state with
  k-1 blocks still hidden behind it. This uniformly replaces selfish mining's special-cased
  lead=1/lead=2/lead>2 behavior with one "always reveal one" rule for the ahead-side.
- **Equal-fork-stubborn (F-stubborn):** identical to vanilla selfish mining in every transition
  **except** state 0' (a tie) + pool mines the next block. Instead of revealing to win outright
  (selfish mining's behavior), F-stubborn **conceals** the new block and keeps mining privately —
  transitioning to a fresh, plain lead of 1 instead of ending the contest.
  ("Equal-fork" = deliberately choosing to stay forked instead of resolving it.)
- **Trail-stubborn (Tj-stubborn):** ahead-side behavior is unchanged vanilla selfish mining. The
  deviation is only on the behind side: instead of adopting immediately when honest miners catch up
  to parity (Δ=0) and pull ahead, the pool keeps mining privately on its old tip while trailing,
  tracking a deficit. Only once Δ ≤ -(j+1) (fallen more than j blocks behind) does it give up and
  resync with the public chain. Mining a block while trailing reduces the deficit by one (catching
  up); reaching deficit 0 again is a fresh plain lead-1 state. Default j=1.

## ATOSIM's implementation


**Success/revenue calculation** —
[ThreesimSimulationRoundResultFactory.java](../../../atosim/org.palladiosimulator.blockchainsystems.threesim/src/main/java/org/palladiosimulator/blockchainsystems/threesim/simulation/results/ThreesimSimulationRoundResultFactory.java):
```java
attackerRevenueShareFraction = attackerRewards / totalRewards;
success = attackerRevenueShareFraction > attackerHashPower;   // R_pool > alpha
```

Rewards are counted only from `BlockType.ConfirmedBlock` events (main-chain, not orphaned/stale) via
`BlockRewardMonitor`, keyed by `block.getOriginId()`. The attacker-node-ID chain (attack model's
`linkedNodeSystem` → design-model `Entity.getId()` → remapped runtime P2PNode ID in
`ThreesimBlockchainSystemFactory.java:56-64` → `Block.getOriginId()`) was audited and confirmed
consistent end-to-end — no ID-space mismatch bug found there.


