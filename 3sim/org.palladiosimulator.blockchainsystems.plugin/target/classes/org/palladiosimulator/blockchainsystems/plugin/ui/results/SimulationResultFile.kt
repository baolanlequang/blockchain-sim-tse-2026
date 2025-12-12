package org.palladiosimulator.blockchainsystems.plugin.ui.results

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * Represents a simulation result file and provides methods to extract values from it.
 * This class reads the content of the file and extracts relevant simulation results.
 *
 * @param file       the file containing the simulation results
 * @param repository the repository where this file is stored
 *
 * @author Yannik Sproll, Davis Riedel
 */
class SimulationResultFile(
  val file: File?,
  val repository: ResultsRepository?
) {
  val jsonContent: JsonElement? by lazy {
    val filePath = file?.toPath() ?: return@lazy null
    try {
      val content = Files.readString(filePath)
      return@lazy Json.decodeFromString(content)
    } catch (e: IOException) {
      e.printStackTrace()
      return@lazy null
    }
  }
}