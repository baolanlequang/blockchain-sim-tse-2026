package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.AvailabilitySecurity
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates availability regarding security
 *
 * @property meanTimeBetweenFailures average time between consecutive failures
 * @property meanTimeToRepair average time to recover from failure
 *
 * @author Davis Riedel
 */
class AvailabilitySecurityCalculator(
  private val meanTimeBetweenFailures: Double,
  private val meanTimeToRepair: Double,
) : OutputMetricCalculator<AvailabilitySecurity> {
  override fun calculate(): AvailabilitySecurity {
    if (meanTimeBetweenFailures == 0.0 || meanTimeToRepair == 0.0) {
      throw IllegalStateException("Mean time between failures and mean time to repair must not be zero when calculating AvailabilitySecurity.")
    }

    if (meanTimeBetweenFailures == -1.0 || meanTimeToRepair == -1.0) { // -1 if no failures occurred, which indicates high availability, e.g. A = 1
      return AvailabilitySecurity(1.0)
    }

    return AvailabilitySecurity(
      meanTimeBetweenFailures / (meanTimeBetweenFailures + meanTimeToRepair)
    )
  }

  companion object : AverageOutputMetricCalculator<AvailabilitySecurity>() {
    override fun getValue(metric: AvailabilitySecurity): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = AvailabilitySecurity.NAME,
        average = result.average,
        unit = AvailabilitySecurity.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}