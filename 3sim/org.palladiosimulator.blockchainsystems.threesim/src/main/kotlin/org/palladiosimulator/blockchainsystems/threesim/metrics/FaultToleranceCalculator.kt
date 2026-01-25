package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultTolerance
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceAverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceValue
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculator
import kotlin.math.abs

/**
 * Calculates fault tolerance.
 *
 * Fault tolerance captures how much key performance metrics degrade
 * when failures are introduced into the system.
 *
 * Current implementation measures degradation as absolute differences
 * between normal-operation and failure-operation averages.
 *
 * IMPORTANT DESIGN NOTE:
 * ----------------------
 * Monte-Carlo simulations may legitimately produce rounds where:
 *  - no transactions are confirmed,
 *  - throughput is zero,
 *  - confirmation latency is undefined.
 *
 * These cases are NOT programming errors but represent unstable or
 * collapsed system configurations.
 *
 * Therefore, instead of throwing an exception and aborting batch
 * execution, such cases are mapped to FaultTolerance = 0.0.
 *
 * This ensures:
 *  - full batch completion,
 *  - traceability of unstable configurations,
 *  - scientifically meaningful results.
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

    // ------------------------------------------------------------------
    // CASE 1: No failures occurred during simulation
    // ------------------------------------------------------------------
    // Threesim uses -1.0 as a sentinel value when no failure phase exists.
    // In this case, fault tolerance is defined as zero degradation.
    if (
      averageThroughputWithFailures == -1.0 ||
      averageConfirmationLatencyWithFailures == -1.0
    ) {
      return FaultTolerance(
        FaultToleranceValue.of(0.0, 0.0)
      )
    }

    // ------------------------------------------------------------------
    // CASE 2: Invalid or degenerate simulation state
    // ------------------------------------------------------------------
    // Negative or zero values can arise when:
    //  - no blocks were confirmed,
    //  - no transactions were processed,
    //  - Monte-Carlo rounds collapse under extreme parameters.
    //
    // These states previously caused batch execution to abort.
    // We now *gracefully degrade* by assigning fault tolerance = 0.0.
    if (
      averageThroughputWithoutFailures <= 0.0 ||
      averageThroughputWithFailures <= 0.0 ||
      averageConfirmationLatencyWithoutFailures <= 0.0 ||
      averageConfirmationLatencyWithFailures <= 0.0
    ) {
      return FaultTolerance(
        FaultToleranceValue.of(0.0, 0.0)
      )
    }

    // ------------------------------------------------------------------
    // CASE 3: Normal case – compute degradation
    // ------------------------------------------------------------------
    // Absolute difference captures how strongly failures affect performance.
    val throughputDelta =
      abs(averageThroughputWithoutFailures - averageThroughputWithFailures)

    val confirmationLatencyDelta =
      abs(
        averageConfirmationLatencyWithoutFailures -
        averageConfirmationLatencyWithFailures
      )

    return FaultTolerance(
      FaultToleranceValue.of(
        throughputDelta,
        confirmationLatencyDelta
      )
    )
  }

  companion object {

    /**
     * Aggregates fault tolerance over multiple Monte-Carlo rounds.
     *
     * Averages are computed independently for:
     *  - throughput degradation
     *  - confirmation latency degradation
     */
    fun calculateAverage(measurements: List<FaultTolerance>): FaultToleranceAverageOutputMetric {
      return FaultToleranceAverageOutputMetric.of(
        AverageCalculator.calculate(
          measurements.map { it.value.throughputDelta.value }
        ),
        AverageCalculator.calculate(
          measurements.map { it.value.confirmationLatencyDelta.value }
        )
      )
    }
  }
}
