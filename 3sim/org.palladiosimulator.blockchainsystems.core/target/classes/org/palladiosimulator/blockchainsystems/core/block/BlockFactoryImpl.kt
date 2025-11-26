package org.palladiosimulator.blockchainsystems.core.block

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import java.util.UUID

/**
 * The @code{BlockFactoryImpl} class implements the @code{BlockFactory} interface
 * to create instances of the @code{Block} class.
 * It provides methods to create blocks with or without tags, and a genesis block.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockFactoryImpl : BlockFactory {
  override fun createBlock(
    hash: String,
    previousHash: String,
    originId: String,
    blockMinedTimestamp: Long,
    blockSize: Int,
    transactions: Set<Transaction>
  ): Block {
    return BlockImpl(
      hash,
      previousHash,
      originId,
      blockMinedTimestamp,
      blockSize,
      transactions,
      HashSet()
    )
  }

  override fun createGenesisBlock(): Block {
    return BlockImpl(
      UUID.randomUUID().toString(),
      null,
      null,
      0,
      0,
      HashSet(),
      HashSet()
    )
  }

  public override fun createBlock(
    hash: String,
    previousHash: String,
    originId: String,
    blockMinedTimestamp: Long,
    blockSize: Int,
    transactions: Set<Transaction>,
    tags: Set<String>
  ): Block {
    return BlockImpl(
      hash,
      previousHash,
      originId,
      blockMinedTimestamp,
      blockSize,
      transactions,
      tags
    )
  }
}