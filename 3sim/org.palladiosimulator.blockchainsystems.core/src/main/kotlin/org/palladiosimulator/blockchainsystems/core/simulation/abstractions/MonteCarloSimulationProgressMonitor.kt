package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

interface MonteCarloSimulationProgressMonitor {
  fun onSimulationStarted(numberOfSimulationRounds: Int)

  fun onSimulationRoundFinished()

  fun onSimulationFinished()
}