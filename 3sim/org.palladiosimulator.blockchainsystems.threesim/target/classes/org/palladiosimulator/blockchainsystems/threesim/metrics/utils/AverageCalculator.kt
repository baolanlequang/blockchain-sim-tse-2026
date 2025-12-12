package org.palladiosimulator.blockchainsystems.threesim.metrics.utils

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Helper to calculate the average, standard deviation, and coefficient of variation
 * for a list of double values.
 *
 * @author Davis Riedel
 */
object AverageCalculator {
  fun calculate(values: List<Double>): AverageCalculatorResult {
    val mean = values.average()
    val standardDeviation = sqrt(values.sumOf { (it - mean).pow(2) } / values.size)
    val coefficientOfVariation = if (mean != 0.0) {
      standardDeviation / mean
    } else {
      null
    }

    return AverageCalculatorResult(
      average = mean,
      standardDeviation = standardDeviation,
      coefficientOfVariation = coefficientOfVariation
    )
  }
}