package org.palladiosimulator.blockchainsystems.core.block

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Implementation of the [Block] interface.
 *
 * @property hash The unique identifier of the block.
 * @property previousHash The hash of the previous block, or null for the genesis block.
 * @property originId The ID of the node that mined the block, or null for the genesis block.
 * @property blockMinedTimestamp The timestamp when the block was mined.
 * @property size The size of the block in bytes.
 * @property transactions The set of transactions included in the block.
 * @property tags The set of tags associated with the block.
 *
 * @author Yannik Sproll, Davis Riedel
 */
@Serializable
class BlockImpl(
  override val hash: String,
  override val previousHash: String?,
  override val originId: String?,
  override val blockMinedTimestamp: Long,
  override val size: Int,
  override val transactions: Set<Transaction>,
  val tags: Set<String>
) : Block {
  override fun hasTag(tag: String): Boolean {
    return tags.contains(tag)
  }
}