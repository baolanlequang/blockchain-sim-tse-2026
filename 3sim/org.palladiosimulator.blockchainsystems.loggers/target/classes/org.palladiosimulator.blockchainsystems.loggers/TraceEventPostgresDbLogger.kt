package org.palladiosimulator.blockchainsystems.loggers

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

/**
 * A logger that writes trace events to a PostgreSQL database.
 *
 * @param server The PostgreSQL server address.
 * @param port The PostgreSQL server port.
 * @param database The name of the database to connect to.
 * @param databaseUser The username for the database connection.
 * @param databasePassword The password for the database connection.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class TraceEventPostgresDbLogger(
  private val server: String,
  private val port: String,
  private val database: String,
  private val databaseUser: String,
  private val databasePassword: String
) : TraceEventLogOutput {
  private lateinit var tempLogFileWriter: BufferedWriter
  private lateinit var logFilePath: Path

  override fun initialize() {
    logFilePath = getTempLogFullPath(UUID.randomUUID())

    try {
      val fileWriter = FileWriter(logFilePath.toString())
      tempLogFileWriter = BufferedWriter(fileWriter)
    } catch (e: IOException) {
      e.printStackTrace()
    }

    try {
      tempLogFileWriter.append(TEMP_LOG_FILE_HEADER)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  override fun cleanUp() {
    try {
      tempLogFileWriter.flush()
      tempLogFileWriter.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    copyToDatabase()

    try {
      Files.deleteIfExists(logFilePath)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  private fun copyToDatabase() {
    var conn: Connection? = null

    try {
      conn = DriverManager.getConnection(
        "jdbc:postgresql://$server:$port/$database",
        databaseUser,
        databasePassword
      )
    } catch (e: SQLException) {
      e.printStackTrace()
      return
    }

    var copyManager: CopyManager? = null

    try {
      copyManager = CopyManager(conn as BaseConnection?)
    } catch (e: SQLException) {
      e.printStackTrace()
    }

    var reader: BufferedReader? = null

    try {
      reader = BufferedReader(FileReader(logFilePath.toString()))
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    }

    try {
      copyManager?.copyIn(
        "COPY public.\"TraceEvents\" FROM STDIN DELIMITER ',' CSV HEADER;",
        reader
      )
    } catch (e: SQLException) {
      e.printStackTrace()
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    try {
      reader?.close()
      conn.close()
    } catch (e: SQLException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  override fun onTraceEventOccurred(event: TraceEvent, logOrigin: TraceEventLogOrigin) {
    try {
      with(tempLogFileWriter) {
        append(UUID.randomUUID().toString())
        append(",")
        append(logOrigin.name)
        append(",")
        append(event.occurrenceTime.toString())
        append(",")
        append(event.eventType)
        append(System.lineSeparator())
      }
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  companion object {
    private val TEMP_LOG_FILE_HEADER = "Id,Node,Timestamp,EventType" + System.lineSeparator()

    private fun getTempLogFullPath(simulationId: UUID): Path {
      val currentFileDirectory = FileSystems.getDefault().getPath("").toAbsolutePath()
      val p = Paths.get(currentFileDirectory.toString(), "temp-simulation-log-$simulationId.csv")
      return p
    }
  }
}