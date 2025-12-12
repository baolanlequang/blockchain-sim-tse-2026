package org.palladiosimulator.blockchainsystems.core.blockchain

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when the type of a block changes.
 *
 * @property occurrenceTime The time at which the event occurred.
 * @property block The block whose type has changed.
 * @property oldBlockType The previous type of the block.
 * @property newBlockType The new type of the block.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockTypeChangedTraceEvent(
  override val occurrenceTime: Long,
  val block: Block,
  val oldBlockType: BlockType,
  val newBlockType: BlockType
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "BlockTypeChangedTraceEvent"
  }
}