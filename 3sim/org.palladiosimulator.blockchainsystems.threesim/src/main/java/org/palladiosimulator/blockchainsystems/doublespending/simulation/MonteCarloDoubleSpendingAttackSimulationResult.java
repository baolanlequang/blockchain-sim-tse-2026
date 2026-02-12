package org.palladiosimulator.blockchainsystems.doublespending.simulation;

public record MonteCarloDoubleSpendingAttackSimulationResult(
		long attackerWonRounds,
		long systemWonRounds,
		long unambiguousRounds) {
	
	public double getAttackSuccessProbability() {
		return ((double) attackerWonRounds) / ((double) (systemWonRounds +attackerWonRounds ));
	}
}
