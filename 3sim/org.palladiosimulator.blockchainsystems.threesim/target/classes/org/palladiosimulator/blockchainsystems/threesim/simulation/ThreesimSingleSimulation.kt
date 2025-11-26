package org.palladiosimulator.blockchainsystems.threesim.simulation

import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SingleSimulation
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SingleSimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSingleSimulationResult

/**
 * Single simulation of 3SIM.
 *
 * @author Davis Riedel
 */
class ThreesimSingleSimulation(
  private val blockchainSystemFactory: ThreesimBlockchainSystemFactory,
  private val logOutputProvider: LogOutputProvider,
  private val maxAllowedBlockchainLength: Long,
  private val simulationParameters: SingleSimulationParameters,
  private val threesimSimulationParameters: ThreesimSimulationParameters
) : SingleSimulation {
  override fun run(): SingleSimulationResult {
    val result = ThreesimSimulationRound(
      blockchainSystemFactory,
      logOutputProvider.logOutputs,
      maxAllowedBlockchainLength,
      threesimSimulationParameters
    ).run();

    return ThreesimSingleSimulationResult(
      simulationParameters,
      threesimSimulationParameters,
      result
    );
  }
}