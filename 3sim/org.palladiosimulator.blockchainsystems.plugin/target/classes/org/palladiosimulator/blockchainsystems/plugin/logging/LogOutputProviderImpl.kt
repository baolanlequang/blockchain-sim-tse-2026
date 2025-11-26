package org.palladiosimulator.blockchainsystems.plugin.logging

import org.eclipse.core.runtime.CoreException
import org.eclipse.debug.core.ILaunchConfiguration
import kotlinx.serialization.json.Json
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput
import org.palladiosimulator.blockchainsystems.loggers.TraceEventConsoleLogger
import org.palladiosimulator.blockchainsystems.loggers.TraceEventFileLogger
import org.palladiosimulator.blockchainsystems.loggers.TraceEventPostgresDbLogger
import org.palladiosimulator.blockchainsystems.plugin.Attributes


/**
 * Implementation of the [LogOutputProvider] interface.
 * Supports console, file, and database logging.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class LogOutputProviderImpl(
  private val jsonSerializer: Json,
  private val useConsoleLogging: Boolean,
  private val useFileLogging: Boolean,
  private val fileLoggingDirectoryPath: String,
  private val useDatabaseLogging: Boolean,
  private val dbServer: String,
  private val dbPort: String,
  private val dbName: String,
  private val dbUsername: String,
  private val dbPassword: String
) : LogOutputProvider {

  override val logOutputs: MutableSet<TraceEventLogOutput>
    get() {
      val logOutputs = HashSet<TraceEventLogOutput>()

      if (useConsoleLogging) {
        logOutputs.add(createConsoleLogger())
      }

      if (useFileLogging) {
        logOutputs.add(createFileLogger())
      }

      if (useDatabaseLogging) {
        logOutputs.add(createDatabaseLogger())
      }

      return logOutputs
    }

  private fun createConsoleLogger(): TraceEventLogOutput {
    return TraceEventConsoleLogger(jsonSerializer)
  }

  private fun createFileLogger(): TraceEventLogOutput {
    return TraceEventFileLogger(
      jsonSerializer,
      fileLoggingDirectoryPath
    )
  }

  private fun createDatabaseLogger(): TraceEventLogOutput {
    return TraceEventPostgresDbLogger(
      dbServer,
      dbPort,
      dbName,
      dbUsername,
      dbPassword
    )
  }

  companion object {
    @Throws(CoreException::class)
    fun fromLaunchConfiguration(
      jsonSerializer: Json,
      configuration: ILaunchConfiguration
    ): LogOutputProviderImpl {
      return LogOutputProviderImpl(
        jsonSerializer = jsonSerializer,
        useConsoleLogging = configuration.getAttribute(
          Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE,
          Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT
        ),
        useFileLogging = configuration.getAttribute(
          Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE,
          Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT
        ),
        fileLoggingDirectoryPath = configuration.getAttribute(
          Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE,
          Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE_DEFAULT
        ),
        useDatabaseLogging = configuration.getAttribute(
          Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE,
          Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT
        ),
        dbServer = configuration.getAttribute(
          Attributes.Logging.DATABASE_SERVER_ATTRIBUTE,
          Attributes.Logging.DATABASE_SERVER_ATTRIBUTE_DEFAULT
        ),
        dbPort = configuration.getAttribute(
          Attributes.Logging.DATABASE_PORT_ATTRIBUTE,
          Attributes.Logging.DATABASE_PORT_ATTRIBUTE_DEFAULT
        ),
        dbName = configuration.getAttribute(
          Attributes.Logging.DATABASE_NAME_ATTRIBUTE,
          Attributes.Logging.DATABASE_NAME_ATTRIBUTE_DEFAULT
        ),
        dbUsername = configuration.getAttribute(
          Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE,
          Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE_DEFAULT
        ),
        dbPassword = configuration.getAttribute(
          Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE,
          Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE_DEFAULT
        )
      )
    }
  }
}