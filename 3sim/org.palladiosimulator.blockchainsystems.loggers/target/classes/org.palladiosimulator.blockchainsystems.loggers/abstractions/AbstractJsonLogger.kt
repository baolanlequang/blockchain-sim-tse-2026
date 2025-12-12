package org.palladiosimulator.blockchainsystems.loggers.abstractions

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput

/**
 * An abstract class for trace event loggers that write plain text logs.
 *
 * @author Davis Riedel
 */
abstract class AbstractJsonLogger(
  private val jsonSerializer: Json,
) : TraceEventLogOutput {

  // Only id and name are relevant for serialization.
  @Serializable
  internal data class SerializableTraceEventLogOrigin(
    override val id: String,
    override val name: String
  ) : TraceEventLogOrigin

  @Serializable
  internal data class LogEntry(
    val traceEvent: TraceEvent,
    val logOrigin: SerializableTraceEventLogOrigin
  )

  protected fun getEventFormat(traceEvent: TraceEvent, logOrigin: TraceEventLogOrigin): String {
    val logEntry = LogEntry(
      traceEvent,
      SerializableTraceEventLogOrigin(logOrigin.id, logOrigin.name)
    )
    return jsonSerializer.encodeToString(logEntry)
  }
}