package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.TransactionThroughput
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Calculates transaction throughput
 *
 * @property numberOfConfirmedTransactions number of successfully processed transactions
 * @property observationTime observation time in milliseconds
 *
 * @author Davis Riedel
 */
class TransactionThroughputCalculator(
  private val numberOfConfirmedTransactions: Int,
  private val observationTime: Long,
) : OutputMetricCalculator<TransactionThroughput> {
  override fun calculate(): TransactionThroughput {
    if (observationTime <= 0 || numberOfConfirmedTransactions <= 0) {
      return TransactionThroughput(0.0)
    }

    return TransactionThroughput(
      (numberOfConfirmedTransactions.toDouble() / observationTime.toDouble()) * 60 * 1000 // Convert ms to min
    )
  }

  companion object : AverageOutputMetricCalculator<TransactionThroughput>() {
    override fun getValue(metric: TransactionThroughput): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = TransactionThroughput.NAME,
        average = result.average,
        unit = TransactionThroughput.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}