package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.Consistency
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates consistency
 *
 * @property blockProposalTimeAndConfirmationTimePerConfirmedBlock Denotes for each confirmed block i its proposal time and its confirmation time.
 *
 * @author Davis Riedel
 */
class ConsistencyCalculator(
  private val blockProposalTimeAndConfirmationTimePerConfirmedBlock: Collection<Pair<Long, Long>>,
) : OutputMetricCalculator<Consistency> {
  override fun calculate(): Consistency {
    val n = blockProposalTimeAndConfirmationTimePerConfirmedBlock.size

    if (n == 0) {
      return Consistency(0.0)
    }

    val result = (1.0 / n) *
      blockProposalTimeAndConfirmationTimePerConfirmedBlock
        .sumOf { (blockProposalTime, blockConfirmationTime) ->
          blockConfirmationTime - blockProposalTime
        }

    return Consistency(result)
  }

  companion object : AverageOutputMetricCalculator<Consistency>() {
    override fun getValue(metric: Consistency): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = Consistency.NAME,
        average = result.average,
        unit = Consistency.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }

  }
}