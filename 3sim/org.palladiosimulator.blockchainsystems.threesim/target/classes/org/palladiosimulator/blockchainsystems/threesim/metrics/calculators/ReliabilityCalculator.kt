package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.Reliability
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult
import kotlin.math.exp

/**
 * Calculates reliability of a blockchain system within the given quantifying timespan.
 *
 * @property timespan the timespan over which the reliability is calculated, in hours
 * @property meanTimeBetweenFailures average time until a failure occurs, in milliseconds
 *
 * @author Davis Riedel
 */
class ReliabilityCalculator(
  private val timespan: Double,
  private val meanTimeBetweenFailures: Double,
) : OutputMetricCalculator<Reliability> {
  override fun calculate(): Reliability {
    if (meanTimeBetweenFailures == -1.0) { // meanTimeBetweenFailures = -1 if no failures occurred, which indicates very high reliability, e.g. R = 0
      return Reliability(0.0)
    }

    if (meanTimeBetweenFailures == 0.0) {
      throw IllegalStateException("Mean time between failures must not be zero when calculating reliability.")
    }

    val timespanInMs = timespan * 3_600_000.0 // convert hours to milliseconds

    return Reliability(
      exp(-1.0 * timespanInMs / meanTimeBetweenFailures)
    )
  }

  companion object : AverageOutputMetricCalculator<Reliability>() {
    override fun getValue(metric: Reliability): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = Reliability.NAME,
        average = result.average,
        unit = Reliability.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}