package org.palladiosimulator.blockchainsystems.threesim.metrics

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric

/**
 * Availability regarding Scalability
 *
 * @author Davis Riedel
 */
@Serializable
data class AvailabilityScalability(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "AvailabilityScalability"
    const val UNIT = "%"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
