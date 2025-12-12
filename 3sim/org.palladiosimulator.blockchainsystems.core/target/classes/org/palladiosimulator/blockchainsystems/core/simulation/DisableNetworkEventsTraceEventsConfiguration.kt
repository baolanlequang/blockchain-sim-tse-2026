package org.palladiosimulator.blockchainsystems.core.simulation

import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockReceivedTraceEvent
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockSentTraceEvent
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventConfiguration

/**
 * A configuration class that disables network-related trace events.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class DisableNetworkEventsTraceEventsConfiguration : TraceEventConfiguration {
  private val networkEventTypes: HashSet<String> = hashSetOf(
    BlockReceivedTraceEvent.EVENT_TYPE,
    BlockSentTraceEvent.EVENT_TYPE
  )

  override fun isEventTypeEnabled(eventType: String): Boolean {
    return !networkEventTypes.contains(eventType)
  }
}