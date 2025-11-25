package org.palladiosimulator.blockchainsystems.plugin.simulation

import org.eclipse.core.runtime.CoreException
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResultSerializer
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Utility class for saving simulation result summaries.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class SimulationResultsWriter(
  private val simulationResultSerializer: SimulationResultSerializer
) {
  @Throws(CoreException::class)
  fun saveResultFile(
    result: SimulationResult,
    outDir: String
  ) {
    val timestamp = getCurrentTimeFormatted()
    val fileName = "$timestamp.tsr.json"
    val fullFilePath = Path.of(outDir, fileName).toString()

    val serializedResult = simulationResultSerializer.serialize(result)

    try {
      BufferedWriter(FileWriter(fullFilePath)).use { writer ->
        writer.write(serializedResult)
      }
    } catch (e: IOException) {
      System.err.println("An error occurred while writing to the file $fullFilePath.")
      e.printStackTrace()
    }
  }

  private fun getCurrentTimeFormatted(): String {
    val now = Instant.now()

    // Convert Instant to ZonedDateTime in the system default time zone
    val zonedDateTime = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())

    // Define the desired format
    val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")

    // Format the ZonedDateTime
    return zonedDateTime.format(formatter)
  }
}