package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.MonteCarloSimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimMonteCarloSimulationResultSerializer
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters

/**
 * Result of a Monte Carlo simulation of 3SIM.
 *
 * @author Davis Riedel
 */
@Serializable(with = ThreesimMonteCarloSimulationResultSerializer::class)
data class ThreesimMonteCarloSimulationResult(
  val simulationParameters: SimulationParameters,
  val threesimSimulationParameters: ThreesimSimulationParameters,
  val generalResults: ThreesimSimulationRoundResult,
  val simulationRoundResults: List<ThreesimSimulationRoundResult>
) : MonteCarloSimulationResult {
  val averageSimulationRoundResult =
    ThreesimAverageSimulationRoundResult.fromSimulationRoundResults(simulationRoundResults)
}
