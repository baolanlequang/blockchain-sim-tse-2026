package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

/**
 * Represents a simulation that can be executed.
 *
 * @author Davis Riedel
 */
interface Simulation {
  fun run(): SimulationResult
}