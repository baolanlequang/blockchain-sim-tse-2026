package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 * The @code{TraceEventLoggerContainer} interface represents a container
 * for @code{TraceEventLogger} instances.
 *
 * @author Yannik Sproll
 */
interface TraceEventLoggerContainer {
  /**
   * Creates a trace event logger for the specified log origin,
   * if it does not already exist.
   *
   * @param logOrigin the log origin to create a logger for
   */
  fun createTraceEventLogger(logOrigin: TraceEventLogOrigin)

  /**
   * Returns the @code{TraceEventLogger} instance for the specified
   * log origin.
   *
   * @param logOrigin the log origin of the requested trace event logger
   * @return the trace event logger
   */
  fun getLogger(logOrigin: TraceEventLogOrigin): TraceEventLogger?
}