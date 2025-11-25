package org.palladiosimulator.blockchainsystems.core.mining

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Trace event that is triggered when block mining is started.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockMiningStartedTraceEvent(
  override val occurrenceTime: Long,
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE: String = "BlockMiningStartedTraceEvent"
  }
}