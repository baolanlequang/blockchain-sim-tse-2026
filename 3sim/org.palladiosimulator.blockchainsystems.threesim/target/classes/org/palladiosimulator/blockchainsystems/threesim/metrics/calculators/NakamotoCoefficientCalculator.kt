package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.NakamotoCoefficient
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult
import kotlin.math.roundToInt

/**
 * Calculates the Nakamoto coefficient
 *
 * @property hashingPowerPerNode the hashing power of each validating node in the system
 * @property threshold the threshold to compute the Nakamoto coefficient for, 0.0 ... 100.0 %
 *
 * @author Davis Riedel
 */
class NakamotoCoefficientCalculator(
  private val hashingPowerPerNode: Collection<Double>,
  private val threshold: Double
) : OutputMetricCalculator<NakamotoCoefficient> {
  init {
    require(threshold in 0.0..100.0) { "Threshold must be in the range 0.0 to 100.0" }
  }

  override fun calculate(): NakamotoCoefficient {
    val totalHashingPower = hashingPowerPerNode.sum()
    if (totalHashingPower == 0.0) return NakamotoCoefficient(0)

    val sortedHashingPowers = hashingPowerPerNode.sortedDescending()

    val coefficient = sortedHashingPowers
      .runningReduce { acc, d -> acc + d } // Calculate cumulative hashing power for each entry
      .indexOfFirst { it / totalHashingPower >= (threshold / 100.0) } // Find first index where cumulative hashing power >= threshold
      .let { if (it == -1) sortedHashingPowers.size else it + 1 } // +1 because index is 0-based. If not found, return size of array.

    return NakamotoCoefficient(coefficient)
  }

  companion object : AverageOutputMetricCalculator<NakamotoCoefficient>() {
    override fun getValue(metric: NakamotoCoefficient): Double {
      return metric.value.toDouble()
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = NakamotoCoefficient.NAME,
        average = result.average.roundToInt().toDouble(),
        unit = NakamotoCoefficient.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}