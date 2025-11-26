package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 * The [TraceEventLogOrigin] interface represents the origin component
 * of trace events.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface TraceEventLogOrigin {
  /**
   * Unique identifier of the trace event log origin.
   */
  val id: String

  /**
   * Name of the trace event log origin.
   */
  val name: String
}