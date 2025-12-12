package org.palladiosimulator.blockchainsystems.threesim.metrics

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric

/**
 * Nakamoto Coefficient
 *
 * @property value the nakamoto coefficient for the threshold
 *
 * @author Davis Riedel
 */
@Serializable
class NakamotoCoefficient(
  override val value: Int
) : OutputMetric<Int> {
  companion object {
    const val NAME = "NakamotoCoefficient"
    const val UNIT = "validating nodes"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
