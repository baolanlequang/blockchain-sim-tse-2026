package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.List;

public record MonteCarloDoubleSpendingAttackSimulationResult(
		long attackerWonRounds,
		long systemWonRounds,
		long unambiguousRounds,
		List<String> roundResults) {

	public MonteCarloDoubleSpendingAttackSimulationResult(long attackerWonRounds, long systemWonRounds, long unambiguousRounds) {
		this(attackerWonRounds, systemWonRounds, unambiguousRounds, List.of());
	}

	public double getAttackSuccessProbability() {
		return ((double) attackerWonRounds) / ((double) (systemWonRounds + attackerWonRounds));
	}
}
