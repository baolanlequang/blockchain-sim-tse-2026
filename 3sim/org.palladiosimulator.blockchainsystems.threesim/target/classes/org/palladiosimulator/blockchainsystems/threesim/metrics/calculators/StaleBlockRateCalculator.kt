package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.StaleBlockRate
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates stale block rate
 *
 * @property numberOfStaleBlocks number of stale blocks
 * @property numberOfConfirmedBlocks number of confirmed blocks
 *
 * @author Davis Riedel
 */
class StaleBlockRateCalculator(
  private val numberOfStaleBlocks: Int,
  private val numberOfConfirmedBlocks: Int
) : OutputMetricCalculator<StaleBlockRate> {
  override fun calculate(): StaleBlockRate {
    if (numberOfConfirmedBlocks <= 0) {
      return StaleBlockRate(0.0)
    }

    return StaleBlockRate(
      numberOfStaleBlocks.toDouble() / numberOfConfirmedBlocks.toDouble()
    )
  }

  companion object : AverageOutputMetricCalculator<StaleBlockRate>() {
    override fun getValue(metric: StaleBlockRate): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = StaleBlockRate.NAME,
        average = result.average,
        unit = StaleBlockRate.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}