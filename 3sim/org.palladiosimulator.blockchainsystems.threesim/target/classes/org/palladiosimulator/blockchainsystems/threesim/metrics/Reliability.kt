package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Reliability
 *
 * @author Davis Riedel
 */
@Serializable
class Reliability(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "Reliability"
    const val UNIT = "%"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
