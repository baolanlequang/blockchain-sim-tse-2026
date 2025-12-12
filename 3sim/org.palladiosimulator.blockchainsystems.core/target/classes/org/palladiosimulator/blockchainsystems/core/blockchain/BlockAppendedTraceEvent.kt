package org.palladiosimulator.blockchainsystems.core.blockchain

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when a block is appended to the blockchain.
 *
 * @property occurrenceTime The time when the event occurred.
 * @property appendedBlock The block that was appended to the blockchain.
 * @property blockPosition The position of the appended block in the blockchain.
 * @property previousBlock The predecessor block of the appended block.
 * @property appendedBlockType The type of the appended block.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockAppendedTraceEvent(
  override val occurrenceTime: Long,
  val appendedBlock: Block,
  val blockPosition: Long,
  val previousBlock: Block,
  val appendedBlockType: BlockType
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "BlockAppendedTraceEvent"
  }
}