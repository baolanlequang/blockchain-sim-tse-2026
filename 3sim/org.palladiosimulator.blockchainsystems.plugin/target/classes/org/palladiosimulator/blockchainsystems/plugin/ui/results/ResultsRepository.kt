package org.palladiosimulator.blockchainsystems.plugin.ui.results

import java.io.File
import java.io.FileFilter
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Represents a repository for simulation results.
 * Provides methods to retrieve simulation result files from a specified directory.
 *
 * @param directory The directory where simulation results are stored.
 * @param container The container that holds the results repository.
 * @author Yannik Sproll, Davis Riedel
 */
data class ResultsRepository(
  val directory: File?,
  val container: ResultsRepositoryContainer?
) {
  private val textFileFilter = FileFilter { file -> file.isFile() && file.getName().endsWith(".dssimresult") }

  fun getSimulationResultFiles(): MutableList<SimulationResultFile> {
    return directory?.listFiles(textFileFilter)
      ?.map { SimulationResultFile(it, this) }
      ?.toMutableList() ?: mutableListOf()
  }
}