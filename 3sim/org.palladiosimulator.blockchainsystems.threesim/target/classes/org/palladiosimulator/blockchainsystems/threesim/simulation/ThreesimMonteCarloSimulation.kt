package org.palladiosimulator.blockchainsystems.threesim.simulation

import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.MonteCarloSimulation
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.MonteCarloSimulationProgressMonitor
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.metrics.GeographicalDiversity
import org.palladiosimulator.blockchainsystems.threesim.metrics.NakamotoCoefficient
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimMonteCarloSimulationResult
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationRoundResult

/**
 * Monte Carlo simulation of 3SIM.
 *
 * @author Davis Riedel
 */
class ThreesimMonteCarloSimulation(
  progressMonitor: MonteCarloSimulationProgressMonitor,
  private val blockchainSystemFactory: ThreesimBlockchainSystemFactory,
  private val logOutputProvider: LogOutputProvider,
  private val maxAllowedBlockchainLength: Long,
  private val simulationParameters: MonteCarloSimulationParameters,
  private val threesimSimulationParameters: ThreesimSimulationParameters
) : MonteCarloSimulation<ThreesimSimulationRoundResult>(
  simulationParameters.numberOfMonteCarloRounds,
  progressMonitor
) {

  override fun performSimulationRound(): ThreesimSimulationRoundResult {
    return ThreesimSimulationRound(
      blockchainSystemFactory,
      logOutputProvider.logOutputs,
      maxAllowedBlockchainLength,
      threesimSimulationParameters
    ).run();
  }

  override fun createSimulationResultFromRoundResults(results: List<ThreesimSimulationRoundResult>): ThreesimMonteCarloSimulationResult {
    // Filter results that are not calculated probabilistically, but are the same for all rounds,
    // because we do not need to calculate averages for them.
    val generalResults = ThreesimSimulationRoundResult(results.first().outputMetrics.filter {
      it is NakamotoCoefficient || it is GeographicalDiversity
    }.toCollection(OutputMetricsSet()))
    val roundResults = results.map {
      ThreesimSimulationRoundResult(it.outputMetrics.filterNot {
        it is NakamotoCoefficient || it is GeographicalDiversity
      }.toCollection(OutputMetricsSet()))
    }

    return ThreesimMonteCarloSimulationResult(
      simulationParameters,
      threesimSimulationParameters,
      generalResults,
      roundResults
    )
  }

}
