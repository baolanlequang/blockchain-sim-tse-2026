package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

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
   * Rounds run with bounded parallelism (capped at available CPU cores) to
   * prevent OOM while still exploiting all available cores.
   *
   * @return The result of the simulation.
   */
  override fun run(): MonteCarloSimulationResult {
    progressMonitor.onSimulationStarted(numberOfRounds)

    val concurrency = Runtime.getRuntime().availableProcessors()
    val semaphore = Semaphore(concurrency)

    val results = runBlocking {
      coroutineScope {
        (0 until numberOfRounds).map {
          async {
            semaphore.withPermit {
              val result = performSimulationRound()
              progressMonitor.onSimulationRoundFinished()
              result
            }
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
