package org.palladiosimulator.blockchainsystems.core.network

import kotlinx.serialization.Serializable

import org.palladiosimulator.blockchainsystems.core.common.P2PNetworkObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.NodeP2PNetworkInterface
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint

@Serializable
class P2PNode(
  override val endpointId: String
) : P2PNetworkObject(), NodeP2PNetworkInterface, P2PNetworkEndpoint {
  private lateinit var network: P2PNetwork
  // One listener per propagation strategy (blocks and transactions). Kept as
  // ordered lists so dispatch order is deterministic across runs.
  @kotlinx.serialization.Transient
  private val messageReceivedListeners = ArrayList<(Message, P2PNetworkEndpoint) -> Unit>(2)
  @kotlinx.serialization.Transient
  private val messageDroppedListeners = ArrayList<(Message, P2PNetworkEndpoint) -> Unit>(2)

  fun initNetwork(network: P2PNetwork) {
    this.network = network
  }

  fun onReceive(messageContent: Message, sender: P2PNetworkEndpoint) {
    for (i in messageReceivedListeners.indices) messageReceivedListeners[i](messageContent, sender)
  }

  fun onMessageDropped(messageContent: Message, recipient: P2PNetworkEndpoint) {
    for (i in messageDroppedListeners.indices) messageDroppedListeners[i](messageContent, recipient)
  }

  override fun dispatchEvent(event: Event) {
  }

  override fun multicast(message: Message) {
    checkNotNull(network) { "P2PNode is missing an instance of a p2p network." }
      .multicast(this, message)
  }

  override fun addMessageReceivedListener(listener: (Message, P2PNetworkEndpoint) -> Unit) {
    if (messageReceivedListeners.none { it === listener }) messageReceivedListeners.add(listener)
  }

  override fun removeMessageReceivedListener(listener: (Message, P2PNetworkEndpoint) -> Unit) {
    messageReceivedListeners.removeAll { it === listener }
  }

  override fun addMessageDroppedListener(listener: (Message, P2PNetworkEndpoint) -> Unit) {
    if (messageDroppedListeners.none { it === listener }) messageDroppedListeners.add(listener)
  }

  override fun removeMessageDroppedListener(listener: (Message, P2PNetworkEndpoint) -> Unit) {
    messageDroppedListeners.removeAll { it === listener }
  }

  override fun send(message: Message, recipient: P2PNetworkEndpoint) {
    network.send(this, recipient as P2PNode, message)
  }

  override fun getNeighbors(): MutableSet<P2PNetworkEndpoint> {
    return network.getNeighbors(this)
  }
}