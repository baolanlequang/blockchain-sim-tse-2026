package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Consistency
 *
 * @author Davis Riedel
 */
@Serializable
class Consistency(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "Consistency"
    const val UNIT = "ms"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
