package org.palladiosimulator.blockchainsystems.core.tracing

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogger
import java.util.*

/**
 * The `TraceEventLoggerImpl` is an implementation of the `TraceEventLogger` interface.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class TraceEventLoggerImpl(
  override val logOrigin: TraceEventLogOrigin,
  private val traceEventConfiguration: TraceEventConfiguration
) : TraceEventLogger {
  private val events: TreeSet<TraceEvent> = TreeSet<TraceEvent> { a, b ->
    a.occurrenceTime.compareTo(b.occurrenceTime)
  }

  private var traceEventCallback: ((TraceEvent, TraceEventLogOrigin) -> Unit)? = null

  override fun logEvent(traceEvent: TraceEvent) {
    events.add(traceEvent)

    notifyTraceEventOccurred(traceEvent)
  }

  private fun notifyTraceEventOccurred(traceEvent: TraceEvent) {
    traceEventCallback?.invoke(traceEvent, this.logOrigin)
  }

  fun setTraceEventCallback(traceEventCallback: (TraceEvent, TraceEventLogOrigin) -> Unit) {
    this.traceEventCallback = traceEventCallback
  }

  override fun isEventTypeEnabled(eventType: String): Boolean {
    return traceEventConfiguration.isEventTypeEnabled(eventType)
  }
}