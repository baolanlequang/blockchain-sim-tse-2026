package org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions

import kotlinx.serialization.Serializable

/**
 * Represents an average of an output metric in 3SIM
 *
 * @property name the name of the output metric
 * @property average the average value of the output metric
 * @property unit the unit of the output metric, or null if not applicable
 * @property standardDeviation the standard deviation of the output metric values
 * @property coefficientOfVariation the coefficient of variation of the output metric values, or null if the average is zero
 *
 * @author Davis Riedel
 */
@Serializable
data class AverageOutputMetricImpl(
  val name: String,
  val average: Double,
  val unit: String? = null,
  val standardDeviation: Double,
  val coefficientOfVariation: Double?
) : AverageOutputMetric