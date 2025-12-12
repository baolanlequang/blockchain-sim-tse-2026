package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Stale block rate
 *
 * @author Davis Riedel
 */
@Serializable
class StaleBlockRate(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "StaleBlockRate"
    const val UNIT = "blocks/min"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
