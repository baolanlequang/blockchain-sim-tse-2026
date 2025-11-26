package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Gini coefficient
 *
 * @author Davis Riedel
 */
@Serializable
class GiniCoefficient(
  override val value: Double,
) : OutputMetric<Double> {
  companion object {
    const val NAME = "GiniCoefficient"
  }

  override val name: String = NAME
  override val unit: String? = null
}
