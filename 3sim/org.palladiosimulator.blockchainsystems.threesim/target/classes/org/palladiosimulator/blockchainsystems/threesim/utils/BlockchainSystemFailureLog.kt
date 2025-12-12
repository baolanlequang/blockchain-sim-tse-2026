package org.palladiosimulator.blockchainsystems.threesim.utils

/**
 * Logs the start and end of blockchain system failures.
 *
 * @author Davis Riedel
 */
class BlockchainSystemFailureLog {
  private data class BlockchainSystemFailureLogEntry(
    val occurrenceTime: Long,
    var duration: Long?,
  )

  private val log: MutableList<BlockchainSystemFailureLogEntry> = mutableListOf()

  fun failureStarted(occurrenceTime: Long) {
    if (isFailureOngoing()) return
    log.add(BlockchainSystemFailureLogEntry(occurrenceTime, null))
  }

  fun failureEnded(occurrenceTime: Long) {
    if (!isFailureOngoing()) return
    val lastEntry = log.last()
    lastEntry.duration = occurrenceTime - lastEntry.occurrenceTime
  }

  fun isFailureOngoing(): Boolean {
    return log.isNotEmpty() && log.last().duration == null
  }

  fun calculateMeanFailureDuration(): Double {
    val durations = log.mapNotNull { it.duration }
    if (durations.isEmpty()) return -1.0 // return -1 if no failures occurred
    return durations.average()
  }

  fun getNumberOfFailures(): Int {
    return log.size
  }
}