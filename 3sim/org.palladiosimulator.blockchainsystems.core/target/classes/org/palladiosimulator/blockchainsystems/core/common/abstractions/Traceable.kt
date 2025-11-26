package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 * The [Traceable] interface specifies the initialization method
 * for a [TraceEventLogOrigin]. This [TraceEventLogOrigin]
 * instance is used as a log origin for trace events.
 *
 * @author Yannik Sproll
 */
interface Traceable : SimulationLifecycleAware {
  /**
   * Initializes with the specified log origin used for trace events.
   *
   * @param logOrigin the log origin used for trace events
   */
  fun initializeLogger(logOrigin: TraceEventLogOrigin)
}