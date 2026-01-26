package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultTolerance
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceAverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceValue
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculator
import kotlin.math.abs

/**
 * Calculates fault tolerance
 *
 * @author Davis Riedel
 */
class FaultToleranceCalculator(
  private val averageThroughputWithoutFailures: Double,
  private val averageThroughputWithFailures: Double,
  private val averageConfirmationLatencyWithoutFailures: Double,
  private val averageConfirmationLatencyWithFailures: Double,
) : OutputMetricCalculator<FaultTolerance> {
  override fun calculate(): FaultTolerance {
    if (averageThroughputWithFailures == -1.0 || averageConfirmationLatencyWithFailures == -1.0) { // no failures occurred
      return FaultTolerance(
        FaultToleranceValue.of(0.0, 0.0)
      )
    }

    var currAverageThroughputWithoutFailures = averageThroughputWithoutFailures
    var currAverageConfirmationLatencyWithoutFailures = averageConfirmationLatencyWithoutFailures
    if (currAverageThroughputWithoutFailures == -1.0) {
      currAverageThroughputWithoutFailures = 0.0
    }
    if (currAverageConfirmationLatencyWithoutFailures == -1.0) {
      currAverageConfirmationLatencyWithoutFailures = 0.0
    }
    
    if (
      currAverageThroughputWithoutFailures < 0.0
      || averageThroughputWithFailures < 0.0
      || currAverageConfirmationLatencyWithoutFailures < 0.0
      || averageConfirmationLatencyWithFailures < 0.0
    ) {
      println(averageThroughputWithoutFailures)
      println(averageThroughputWithFailures)
      println(averageConfirmationLatencyWithoutFailures)
      println(averageConfirmationLatencyWithFailures)
      throw IllegalStateException("Average throughput and average confirmation latency must not be negative when calculating fault tolerance.")
    }

    val throughputDelta = abs(currAverageThroughputWithoutFailures - averageThroughputWithFailures)
    val confirmationLatencyDelta =
      abs(currAverageConfirmationLatencyWithoutFailures - averageConfirmationLatencyWithFailures)

    return FaultTolerance(
      FaultToleranceValue.of(throughputDelta, confirmationLatencyDelta)
    )
  }

  companion object {
    fun calculateAverage(measurements: List<FaultTolerance>): FaultToleranceAverageOutputMetric {
      return FaultToleranceAverageOutputMetric.of(
        AverageCalculator.calculate(measurements.map { it.value.throughputDelta.value }),
        AverageCalculator.calculate(measurements.map { it.value.confirmationLatencyDelta.value })
      )
    }
  }
}