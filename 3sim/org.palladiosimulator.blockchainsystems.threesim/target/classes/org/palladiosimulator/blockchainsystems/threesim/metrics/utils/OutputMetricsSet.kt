package org.palladiosimulator.blockchainsystems.threesim.metrics.utils

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import org.palladiosimulator.blockchainsystems.threesim.serialization.OutputMetricsSetSerializer
import java.util.TreeSet

/**
 * A set of output metrics. Each metric is uniquely identified by its name.
 *
 * @author Davis Riedel
 */
@Serializable(with = OutputMetricsSetSerializer::class)
class OutputMetricsSet : TreeSet<OutputMetric<*>>(compareBy { it.name }) {
  companion object {
    fun from(vararg metrics: OutputMetric<*>): OutputMetricsSet {
      return OutputMetricsSet().apply { addAll(metrics) }
    }
  }
}