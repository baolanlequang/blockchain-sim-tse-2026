package org.palladiosimulator.blockchainsystems.core.mining

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when block mining is restarted.
 *
 * @param occurrenceTime The time at which the trace event occurred.
 * @param previousBlockHash The hash of the previous block, if available.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockMiningRestartedTraceEvent(
  override val occurrenceTime: Long,
  val previousBlockHash: String?
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE: String = "BlockMiningRestartedTraceEvent"
  }
}