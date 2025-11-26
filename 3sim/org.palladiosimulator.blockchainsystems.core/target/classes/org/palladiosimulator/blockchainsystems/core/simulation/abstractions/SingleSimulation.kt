package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

interface SingleSimulation : Simulation {
  /**
   * Runs a simulation with a single round.
   *
   * @return The result of the simulation.
   */
  override fun run(): SingleSimulationResult
}