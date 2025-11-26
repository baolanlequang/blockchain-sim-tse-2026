package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SingleSimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSingleSimulationResultSerializer
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters

/**
 * Result of a single simulation of 3SIM.
 *
 * @author Davis Riedel
 */
@Serializable(with = ThreesimSingleSimulationResultSerializer::class)
data class ThreesimSingleSimulationResult(
  val simulationParameters: SimulationParameters,
  val threesimSimulationParameters: ThreesimSimulationParameters,
  val simulationRoundResult: ThreesimSimulationRoundResult
) : SingleSimulationResult
