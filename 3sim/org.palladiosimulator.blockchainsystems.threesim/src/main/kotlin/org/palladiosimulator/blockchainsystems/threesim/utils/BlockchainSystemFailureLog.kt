package org.palladiosimulator.blockchainsystems.threesim.utils

/**
 * Constant-space failure statistics.
 *
 * The monitor consumes only the number of failures, whether one is currently
 * active, and the mean duration of completed failures. Retaining one object per
 * historical failure therefore changed memory use without changing any output.
 */
class BlockchainSystemFailureLog {
  private var ongoingFailureStartedAt: Long? = null
  private var numberOfFailures: Int = 0
  private var completedFailureCount: Int = 0
  private var completedFailureDurationSum: Long = 0L

  fun failureStarted(occurrenceTime: Long) {
    if (isFailureOngoing()) return
    ongoingFailureStartedAt = occurrenceTime
    numberOfFailures++
  }

  fun failureEnded(occurrenceTime: Long) {
    val startedAt = ongoingFailureStartedAt ?: return
    completedFailureDurationSum += occurrenceTime - startedAt
    completedFailureCount++
    ongoingFailureStartedAt = null
  }

  fun isFailureOngoing(): Boolean = ongoingFailureStartedAt != null

  fun calculateMeanFailureDuration(): Double {
    if (completedFailureCount == 0) return -1.0
    return completedFailureDurationSum.toDouble() / completedFailureCount.toDouble()
  }

  fun getNumberOfFailures(): Int = numberOfFailures
}
