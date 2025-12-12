package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.HerfindahlHirschmanIndex
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult
import kotlin.math.pow

/**
 * Calculates the Normalized Herfindahl-Hirschman-Index (HHI_norm)
 *
 * @property tokensHeldPerNode Denotes for each node i the amount of tokens it holds.
 *
 * @author Davis Riedel
 */
class HerfindahlHirschmanIndexCalculator(
  private val tokensHeldPerNode: Collection<Double>
) : OutputMetricCalculator<HerfindahlHirschmanIndex> {
  override fun calculate(): HerfindahlHirschmanIndex {
    val n = tokensHeldPerNode.size.toDouble() // number of validating nodes
    val total = tokensHeldPerNode.sum() // total amount of tokens held by all nodes

    if (n == 0.0 || total == 0.0) {
      return HerfindahlHirschmanIndex(0.0)
    }

    val hhi = tokensHeldPerNode.sumOf { (it / total).pow(2) } // calculate HHI
    val hhiNorm = (hhi - 1 / n) / (1 - (1 / n)) // normalize HHI

    return HerfindahlHirschmanIndex(hhiNorm)
  }

  companion object : AverageOutputMetricCalculator<HerfindahlHirschmanIndex>() {
    override fun getValue(metric: HerfindahlHirschmanIndex): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = HerfindahlHirschmanIndex.NAME,
        average = result.average,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}