package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Represents a Monte-Carlo simulation with several rounds.
 * This class serves as a base for more specific simulations.
 *
 * @property numberOfRounds the number of rounds in this Monte-Carlo simulation.
 *
 * @author Davis Riedel
 */
abstract class MonteCarloSimulation<R : SimulationRoundResult>(
  protected val numberOfRounds: Int,
  protected val progressMonitor: MonteCarloSimulationProgressMonitor,
) : Simulation {

  /**
   * Runs the Monte-Carlo simulation and returns the result.
   *
   * @return The result of the simulation.
   */
  override fun run(): MonteCarloSimulationResult {
    progressMonitor.onSimulationStarted(numberOfRounds)

    // Run the simulation rounds and collect results
    val results = runBlocking {
      coroutineScope {
        (0 until numberOfRounds).map {
          async {
            val result = performSimulationRound()
            progressMonitor.onSimulationRoundFinished()
            result
          }
        }.awaitAll()
      }
    }

    progressMonitor.onSimulationFinished()

    return createSimulationResultFromRoundResults(results)
  }

  abstract fun performSimulationRound(): R
  abstract fun createSimulationResultFromRoundResults(results: List<R>): MonteCarloSimulationResult
}
