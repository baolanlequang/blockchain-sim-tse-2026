package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Confirmation Latency
 *
 * CL_emp = BConfTime - BPropTime
 */
@Serializable
class ConfirmationLatency(
  override val value: Double
) : OutputMetric<Double> {

  companion object {
    const val NAME = "ConfirmationLatency"
    const val UNIT = "ms"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
