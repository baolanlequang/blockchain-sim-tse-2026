package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Geographical Diversity
 *
 * @author Davis Riedel
 */
@Serializable
class GeographicalDiversity(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "GeographicalDiversity"
    const val UNIT = "%"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
