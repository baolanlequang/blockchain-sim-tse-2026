package org.palladiosimulator.blockchainsystems.threesim.metrics.utils

import kotlinx.serialization.Serializable

/**
 * Represents the result of an average calculation, including the average value,
 * standard deviation, and the coefficient of variation, if the average is not zero.
 *
 * @author Davis Riedel
 */
@Serializable
data class AverageCalculatorResult(
  val average: Double,
  val standardDeviation: Double,
  val coefficientOfVariation: Double? = null
)