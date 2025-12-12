package org.palladiosimulator.blockchainsystems.core.tracing

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogger
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLoggerContainer

class TraceEventLoggerContainerImpl(
  private val traceEventConfiguration: TraceEventConfiguration = AllTraceEventsEnabledTraceEventConfiguration()
) : TraceEventLoggerContainer {
  private val loggers: HashMap<String, TraceEventLogger> = HashMap()

  private val eventSubscribers: HashSet<TraceEventSubscriber> = HashSet()

  fun addSubscriber(subscriber: TraceEventSubscriber) {
    eventSubscribers.add(subscriber)
  }

  fun removeSubscriber(subscriber: TraceEventSubscriber) {
    eventSubscribers.remove(subscriber)
  }

  private fun notifySubscribers(traceEvent: TraceEvent, logOrigin: TraceEventLogOrigin) {
    for (subscriber in eventSubscribers) {
      subscriber.onTraceEventOccurred(traceEvent, logOrigin)
    }
  }

  override fun createTraceEventLogger(logOrigin: TraceEventLogOrigin) {
    if (loggers.containsKey(logOrigin.id)) return

    val loggerImpl = TraceEventLoggerImpl(logOrigin, traceEventConfiguration)
    loggerImpl.setTraceEventCallback { traceEvent: TraceEvent, logOrigin: TraceEventLogOrigin ->
      this.notifySubscribers(
        traceEvent,
        logOrigin
      )
    }
    loggers.put(logOrigin.id, loggerImpl)
  }

  override fun getLogger(logOrigin: TraceEventLogOrigin): TraceEventLogger? {
    return loggers.get(logOrigin.id)
  }
}