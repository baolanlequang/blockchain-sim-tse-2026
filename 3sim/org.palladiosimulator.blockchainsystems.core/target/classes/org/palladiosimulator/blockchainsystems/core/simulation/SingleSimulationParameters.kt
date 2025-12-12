package org.palladiosimulator.blockchainsystems.core.simulation

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters

/**
 * Parameters for a single simulation run.
 *
 * @property maxAllowedBlockchainLength The maximum length of the blockchain.
 * @property blockchainSystemModelFilePath The file path to the blockchain system model.
 *
 * @author Davis Riedel
 */
@Serializable
class SingleSimulationParameters(
  override val maxAllowedBlockchainLength: Long,
  override val blockchainSystemModelFilePath: String
) : SimulationParameters {
  override val simulationType: SimulationType = SimulationType.Single
}