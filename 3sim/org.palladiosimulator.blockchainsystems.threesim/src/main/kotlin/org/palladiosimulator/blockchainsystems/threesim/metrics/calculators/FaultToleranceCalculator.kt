package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultTolerance
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceAverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceValue
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculator
import kotlin.math.abs

/**
 * Calculates fault tolerance based on super-CDF distance:
 *
 *   FT = | S_N(b_N) − S_F(b_F) |
 *
 * where S(x) is the empirical super-cumulative distribution function.
 *
 * This implementation follows the revised metric definition used in the paper.
 */
class FaultToleranceCalculator(
  private val normalMeasurements: List<Double>,
  private val faultyMeasurements: List<Double>
) : OutputMetricCalculator<FaultTolerance> {

  override fun calculate(): FaultTolerance {

    // If no faults occurred, fault tolerance is zero by definition
    if (faultyMeasurements.isEmpty()) {
      return FaultTolerance(FaultToleranceValue.of(0.0, 0.0))
    }

    // Filter invalid values defensively
    val normal = normalMeasurements.filter { it >= 0.0 }
    val faulty = faultyMeasurements.filter { it >= 0.0 }

    if (normal.isEmpty() || faulty.isEmpty()) {
      return FaultTolerance(FaultToleranceValue.of(0.0, 0.0))
    }

    val sN = superCdf(normal)
    val sF = superCdf(faulty)

    val bN = normal.maxOrNull()!!
    val bF = faulty.maxOrNull()!!

    val ftValue = abs(sN(bN) - sF(bF))

    // Second value kept for compatibility; set to same FT or 0.0
    return FaultTolerance(
      FaultToleranceValue.of(ftValue, 0.0)
    )
  }

  /**
   * Builds the empirical super-CDF function S(x).
   */
  private fun superCdf(samples: List<Double>): (Double) -> Double {

    val sorted = samples.sorted()
    val n = sorted.size.toDouble()

    // Empirical CDF
    fun cdf(x: Double): Double =
      sorted.count { it <= x } / n

    // Super-CDF: sum of CDF values up to x
    return { x ->
      sorted
        .filter { it <= x }
        .sumOf { t -> cdf(t) }
    }
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
