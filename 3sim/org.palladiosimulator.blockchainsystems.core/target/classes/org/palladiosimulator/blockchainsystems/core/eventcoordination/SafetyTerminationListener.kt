package org.palladiosimulator.blockchainsystems.core.eventcoordination

/** Receives structured safety and constant-space event-engine telemetry. */
interface SafetyTerminationListener {
  fun onSafetyTermination(reason: String)

  fun onEventCoordinatorTelemetry(
    processedEvents: Long,
    maxFutureEventsObserved: Long
  ) {
    // Optional constant-space diagnostic hook.
  }
}
