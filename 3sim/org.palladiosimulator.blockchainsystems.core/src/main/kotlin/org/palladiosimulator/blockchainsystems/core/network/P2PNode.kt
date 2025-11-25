package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.P2PNetworkObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.NodeP2PNetworkInterface
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint

class P2PNode(
  override val endpointId: String
) : P2PNetworkObject(), NodeP2PNetworkInterface, P2PNetworkEndpoint {
  private lateinit var network: P2PNetwork
  private var onMessageReceivedCallback: ((Message, P2PNetworkEndpoint) -> Unit)? = null
  private var onMessageDroppedCallback: ((Message, P2PNetworkEndpoint) -> Unit)? = null

  fun initNetwork(network: P2PNetwork) {
    this.network = network
  }

  fun onReceive(messageContent: Message, sender: P2PNetworkEndpoint) {
    onMessageReceivedCallback?.invoke(messageContent, sender)
  }

  fun onMessageDropped(messageContent: Message, recipient: P2PNetworkEndpoint) {
    onMessageDroppedCallback?.invoke(messageContent, recipient)
  }

  override fun dispatchEvent(event: Event) {
  }

  override fun multicast(message: Message) {
    checkNotNull(network) { "P2PNode is missing an instance of a p2p network." }
      .multicast(this, message)
  }

  override fun setOnMessageReceivedCallback(onMessageReceivedCallback: ((Message, P2PNetworkEndpoint) -> Unit)?) {
    this.onMessageReceivedCallback = onMessageReceivedCallback
  }

  override fun setOnMessageDroppedCallback(onMessageDroppedCallback: ((Message, P2PNetworkEndpoint) -> Unit)?) {
    this.onMessageDroppedCallback = onMessageDroppedCallback
  }

  override fun send(message: Message, recipient: P2PNetworkEndpoint) {
    network.send(this, recipient as P2PNode, message)
  }

  override fun getNeighbors(): MutableSet<P2PNetworkEndpoint> {
    return network.getNeighbors(this)
  }
}