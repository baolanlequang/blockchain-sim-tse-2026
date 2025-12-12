package org.palladiosimulator.blockchainsystems.core.blockchain

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Blockchain
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainFactory

/**
 * Implementation of the [BlockchainFactory] interface.
 * This factory is responsible for creating a new blockchain with a given genesis block.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainFactoryImpl(
  private val numberOfRequiredSecurityConfirmations: Int,
) : BlockchainFactory {
  override fun createBlockchain(genesisBlock: Block, nodeId: String?): Blockchain {
    val genesisBlockchainElement = BlockchainElement(
      genesisBlock,
      null,
      BlockchainElementType.Included,
      1
    )

    return BlockchainImpl(genesisBlockchainElement, numberOfRequiredSecurityConfirmations)
  }
}