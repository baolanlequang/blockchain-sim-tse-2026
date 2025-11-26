package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 * Represents a value that is valid for a specific duration.
 *
 * @property value The value that is valid for the specified duration.
 * @property duration The duration for which this value is applicable, in milliseconds.
 *
 * @author Davis Riedel
 */
interface TemporalValue<T> {
  val value: T
  val duration: Long
}