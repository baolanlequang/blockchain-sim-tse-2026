package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

import java.util.List;

/**
 * Result of a single selfish-mining simulation round: the attacker's realized share of confirmed
 * block rewards versus its share of hash power, evaluated against Eyal &amp; Sirer's Observation 1
 * success criterion (the attacker earns more than its fair share iff its revenue share exceeds
 * its hash-power share). Includes a per-node breakdown of every attacker node.
 */
public record SelfishMiningSimulationRoundResult(
		int attackerRewards,
		int totalRewards,
		double attackerHashPowerFraction,
		List<AttackerNodeResult> attackerNodes) {

	public double getAttackerRevenueShareFraction() {
		return totalRewards == 0 ? 0.0 : ((double) attackerRewards) / ((double) totalRewards);
	}

	public boolean hasSucceeded() {
		return getAttackerRevenueShareFraction() > attackerHashPowerFraction;
	}
}
