package org.palladiosimulator.blockchainsystems.doublespending.simulation;

public interface MonteCarloSimulationProgressMonitor {

	void onSimulationStarted(long numberOfSimulationRounds);
	void onSimulationRoundFinished();
	void onSimulationFinished();
}
