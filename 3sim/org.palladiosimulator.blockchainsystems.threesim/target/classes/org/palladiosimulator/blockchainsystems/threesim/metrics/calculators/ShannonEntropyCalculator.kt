package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.ShannonEntropy
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult
import kotlin.math.log

/**
 * Calculates Shannon Entropy
 *
 * @property k the constant factor k of the Shannon Entropy
 * @property blocksProposedPerNode stores for each node i the amount of blocks proposed by i
 *
 * @author Davis Riedel
 */
class ShannonEntropyCalculator(
  private val k: Double,
  private val blocksProposedPerNode: Collection<Int>
) : OutputMetricCalculator<ShannonEntropy> {
  override fun calculate(): ShannonEntropy {
    val totalNumOfBlocksProposed = blocksProposedPerNode.sum()
    if (totalNumOfBlocksProposed == 0) {
      return ShannonEntropy(0.0) // Avoid division by zero
    }

    val sum = blocksProposedPerNode.sumOf {
      if (it == 0) return@sumOf 0.0 // Avoid log(0)
      val b = it.toDouble() / totalNumOfBlocksProposed // Probability of block proposed by node i
      b * log(b, 2.0)
    }

    val result = -1 * k * sum

    return ShannonEntropy(result)
  }

  companion object : AverageOutputMetricCalculator<ShannonEntropy>() {
    override fun getValue(metric: ShannonEntropy): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = ShannonEntropy.NAME,
        average = result.average,
        unit = ShannonEntropy.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}