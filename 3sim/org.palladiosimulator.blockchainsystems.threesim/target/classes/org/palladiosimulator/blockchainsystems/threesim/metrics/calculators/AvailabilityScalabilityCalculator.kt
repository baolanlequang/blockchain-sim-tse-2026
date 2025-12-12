package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.AvailabilityScalability
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates availability regarding scalability
 *
 * @property numberOfConfirmedTransactions the number of confirmed transactions within the observation time
 * @property numberOfTransactions the total number of transactions within the observation time
 *
 * @author Davis Riedel
 */
class AvailabilityScalabilityCalculator(
  private val numberOfConfirmedTransactions: Int,
  private val numberOfTransactions: Int
) : OutputMetricCalculator<AvailabilityScalability> {
  override fun calculate(): AvailabilityScalability {
    if (numberOfTransactions <= 0) {
      return AvailabilityScalability(0.0)
    }

    return AvailabilityScalability(
      numberOfConfirmedTransactions.toDouble() / numberOfTransactions.toDouble()
    )
  }

  companion object : AverageOutputMetricCalculator<AvailabilityScalability>() {
    override fun getValue(metric: AvailabilityScalability): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = AvailabilityScalability.NAME,
        average = result.average,
        unit = AvailabilityScalability.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}