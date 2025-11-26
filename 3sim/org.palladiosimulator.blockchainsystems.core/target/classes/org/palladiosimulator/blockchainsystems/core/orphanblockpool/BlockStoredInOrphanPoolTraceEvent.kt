package org.palladiosimulator.blockchainsystems.core.orphanblockpool

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when a block is stored in the orphan pool.
 *
 * @property occurrenceTime The time at which the event occurred.
 * @property storedBlock The block that has been stored in the orphan pool.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockStoredInOrphanPoolTraceEvent(
  override val occurrenceTime: Long,
  val storedBlock: Block
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "BlockStoredInOrphanPoolTraceEvent"
  }
}