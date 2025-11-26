package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TemporalValue

/**
 * Represents the throughput of a link in a blockchain network.
 *
 * @property value The throughput of the link in bits per second (bps)
 * @property duration The duration for which this throughput is applicable, in milliseconds.
 *
 * @author Davis Riedel
 */
data class LinkThroughput(
  override val value: Long,
  override val duration: Long
) : TemporalValue<Long>