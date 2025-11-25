package org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions

import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates the average of a list of output metrics.
 *
 * @author Davis Riedel
 */
abstract class AverageOutputMetricCalculator<M : OutputMetric<*>> {
  protected abstract fun getValue(metric: M): Double
  protected abstract fun createResult(result: AverageCalculatorResult): AverageOutputMetric

  open fun calculateAverage(measurements: List<M>): AverageOutputMetric {
    val values = measurements.map { getValue(it) }
    val result = AverageCalculator.calculate(values)
    return createResult(result)
  }
}