package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Transaction Throughput
 *
 * @property value the throughput value in transactions per minute
 *
 * @author Davis Riedel
 */
@Serializable
class TransactionThroughput(
  override val value: Double,
) : OutputMetric<Double> {
  companion object {
    const val NAME = "TransactionThroughput"
    const val UNIT = "transactions/min"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
