package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

import kotlinx.coroutines.Dispatchers
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
  private val concurrency: Int = DEFAULT_CONCURRENCY,
) : Simulation {

  companion object {
    const val DEFAULT_CONCURRENCY: Int = 96
  }

  /**
   * Runs the Monte-Carlo simulation and returns the result.
   * Rounds run with bounded parallelism (capped at [concurrency]) to
   * prevent OOM. Default is available CPU cores.
   *
   * @return The result of the simulation.
   */
  override fun run(): MonteCarloSimulationResult {
    progressMonitor.onSimulationStarted(numberOfRounds)

    val semaphore = Semaphore(concurrency.coerceAtLeast(1))
    
    val results = runBlocking {
      coroutineScope {
        (0 until numberOfRounds).map {
          // Dispatchers.Default puts work on the shared thread pool so rounds
          // actually run in parallel (without it, runBlocking uses a single thread).
          async(Dispatchers.Default) {
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
