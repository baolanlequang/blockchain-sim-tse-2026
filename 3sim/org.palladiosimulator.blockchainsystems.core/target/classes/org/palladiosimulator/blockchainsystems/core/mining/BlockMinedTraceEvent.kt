package org.palladiosimulator.blockchainsystems.core.mining

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when a block is mined.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockMinedTraceEvent(
  override val occurrenceTime: Long,
  val block: Block
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE: String = "BlockMinedTraceEvent"
  }
}