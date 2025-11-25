package org.palladiosimulator.blockchainsystems.core.simulation

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters

/**
 * Simulation parameters for Monte Carlo simulations in blockchain systems.
 *
 * @property simulationType The type of simulation to be performed.
 * @property maxAllowedBlockchainLength The maximum length of the blockchain.
 * @property numberOfMonteCarloRounds The number of Monte Carlo simulation rounds.
 * @property blockchainSystemModelFilePath The file path to the blockchain system model.
 *
 * @author Davis Riedel
 */
@Serializable
class MonteCarloSimulationParameters(
  override val maxAllowedBlockchainLength: Long,
  val numberOfMonteCarloRounds: Int,
  override val blockchainSystemModelFilePath: String
) : SimulationParameters {
  override val simulationType: SimulationType = SimulationType.MonteCarlo
}