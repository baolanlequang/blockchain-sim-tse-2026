package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

import java.util.List;

public record MonteCarloSelfishMiningAttackSimulationResult(
		int numberOfSimulationRounds,
		long roundsSucceeded,
		double averageAttackerRevenueShareFraction,
		double averageAttackerHashPowerFraction,
		List<SelfishMiningSimulationRoundResult> roundResults) {

	public double getAttackSuccessProbability() {
		return numberOfSimulationRounds == 0 ? 0.0 : ((double) roundsSucceeded) / ((double) numberOfSimulationRounds);
	}
}
