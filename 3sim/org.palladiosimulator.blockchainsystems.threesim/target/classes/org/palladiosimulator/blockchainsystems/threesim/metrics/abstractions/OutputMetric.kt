package org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions

/**
 * Represents an output metric in 3SIM
 *
 * @param T the type of the value of the output metric
 *
 * @author Davis Riedel
 */
interface OutputMetric<T> {
  /**
   * The value of the output metric.
   */
  val value: T

  /**
   * The name of the output metric, used in results
   */
  val name: String

  /**
   * The unit of the output metric, used in results
   */
  val unit: String?
}