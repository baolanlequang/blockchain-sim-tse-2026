package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TemporalValue

/**
 * Represents the latency of a link in a blockchain network.
 *
 * @property value The latency of the link in milliseconds.
 * @property duration The duration for which this latency is applicable, in milliseconds.
 *
 * @author Davis Riedel
 */
data class LinkLatency(
  override val value: Long,
  override val duration: Long
) : TemporalValue<Long>