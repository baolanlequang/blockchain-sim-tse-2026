package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import kotlinx.serialization.Serializable

/**
 * Herfindahl Hirschman Index (HHI)
 *
 * @author Davis Riedel
 */
@Serializable
class HerfindahlHirschmanIndex(
  override val value: Double,
) : OutputMetric<Double> {
  companion object {
    const val NAME = "HerfindahlHirschmanIndex"
  }

  override val name: String = NAME
  override val unit: String? = null
}
