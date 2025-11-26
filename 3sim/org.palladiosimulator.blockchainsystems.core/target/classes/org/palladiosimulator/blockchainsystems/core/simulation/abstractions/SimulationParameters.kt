package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType

/**
 * Common parameters for simulations.
 *
 * @property simulationType The type of simulation to be performed.
 * @property maxAllowedBlockchainLength The maximum length of the blockchain.
 * @property blockchainSystemModelFilePath The file path to the blockchain system model.
 *
 * @author Davis Riedel
 */
interface SimulationParameters {
  val simulationType: SimulationType
  val maxAllowedBlockchainLength: Long
  val blockchainSystemModelFilePath: String
}