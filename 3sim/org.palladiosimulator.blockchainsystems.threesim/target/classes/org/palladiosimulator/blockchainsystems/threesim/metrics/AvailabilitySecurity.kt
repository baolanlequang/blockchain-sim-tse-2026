package org.palladiosimulator.blockchainsystems.threesim.metrics

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric

/**
 * Availability regarding security
 *
 * @author Davis Riedel
 */
@Serializable
class AvailabilitySecurity(
  override val value: Double
) : OutputMetric<Double> {
  companion object {
    const val NAME = "AvailabilitySecurity"
    const val UNIT = "%"
  }

  override val name: String = NAME
  override val unit: String? = UNIT
}
