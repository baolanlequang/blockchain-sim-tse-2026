package org.palladiosimulator.blockchainsystems.core.propagation.block

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint

/**
 * Trace event that occurs when a block is sent to a peer in the network.
 *
 * @property occurrenceTime The time at which the event occurred.
 * @property sentBlock The block that was sent.
 * @property receivingNetworkEndpoint The network endpoint of the peer that received the block.
 *
 * @author Davis Riedel, Yannik Sproll
 */
@Serializable
data class BlockSentTraceEvent(
  override val occurrenceTime: Long,
  val sentBlock: Block?,
  val receivingNetworkEndpoint: P2PNetworkEndpoint?
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "BlockSentTraceEvent"
  }
}