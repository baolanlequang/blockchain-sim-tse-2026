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

    // Each round allocates a full blockchain system. Run at most CONCURRENCY rounds
    // simultaneously to cap peak memory while still parallelising across CPU cores.
    // Raise this value if you have ample heap; lower it if you still see OOM.
    val concurrency = 10
    val semaphore = Semaphore(concurrency)

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
