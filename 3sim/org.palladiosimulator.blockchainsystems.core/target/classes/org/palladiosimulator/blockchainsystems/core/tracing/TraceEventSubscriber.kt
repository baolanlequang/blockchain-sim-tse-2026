package org.palladiosimulator.blockchainsystems.core.tracing

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin

/**
 * Interface for subscribers that receive trace events.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface TraceEventSubscriber {
  fun onTraceEventOccurred(event: TraceEvent, logOrigin: TraceEventLogOrigin)
}