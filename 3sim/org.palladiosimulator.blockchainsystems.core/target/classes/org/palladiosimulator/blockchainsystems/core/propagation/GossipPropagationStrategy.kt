package org.palladiosimulator.blockchainsystems.core.propagation

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*

/**
 * Implementation of a [PropagationStrategy] that realizes a simple gossip protocol, using multicast.
 *
 * @author Davis Riedel
 */
abstract class GossipPropagationStrategy<E : Propagatable> : BlockchainNodeObject(), PropagationStrategy<E> {
  protected var networkInterface: NodeP2PNetworkInterface? = null
    private set

  protected var onReceivedCallback: ((E) -> Unit)? = null
    private set


  protected abstract val INV_MESSAGE_KEY: String
  protected abstract val GET_DATA_MESSAGE_KEY: String
  protected abstract val ELEMENT_MESSAGE_KEY: String

  protected abstract fun createInvMessage(element: E): Message
  protected abstract fun handleInvMessageReceived(message: Message, senderNetworkEndpoint: P2PNetworkEndpoint)

  protected abstract fun createGetDataMessage(elementId: String): Message
  protected abstract fun handleGetDataMessageReceived(message: Message, senderNetworkEndpoint: P2PNetworkEndpoint)

  protected abstract fun createElementMessage(element: E): Message
  protected abstract fun handleElementMessageReceived(message: Message, senderNetworkEndpoint: P2PNetworkEndpoint)

  protected abstract fun handleMessageDropped(
    message: Message,
    recipientNetworkEndpoint: P2PNetworkEndpoint
  )

  protected var context: BlockchainSystemNodeContext? = null

  override fun setBlockchainSystemNodeContext(context: BlockchainSystemNodeContext) {
    this.context = context
  }


  override fun distribute(element: E) {
    networkInterface?.multicast(createInvMessage(element))
  }

  override fun distribute(element: E, neighborEndpoints: MutableSet<P2PNetworkEndpoint>) {
    neighborEndpoints.forEach {
      networkInterface?.send(createInvMessage(element), it)
    }
  }

  private fun onMessageReceivedFromNetworkInterface(
    message: Message,
    senderNetworkEndpoint: P2PNetworkEndpoint
  ) {
    when (message.contentType) {
      INV_MESSAGE_KEY -> handleInvMessageReceived(message, senderNetworkEndpoint)
      GET_DATA_MESSAGE_KEY -> handleGetDataMessageReceived(message, senderNetworkEndpoint)
      ELEMENT_MESSAGE_KEY -> handleElementMessageReceived(message, senderNetworkEndpoint)
    }
  }

  private fun failedToSendMessageToNetworkInterface(
    message: Message,
    recipientNetworkEndpoint: P2PNetworkEndpoint
  ) {
    handleMessageDropped(message, recipientNetworkEndpoint)
  }

  override fun setNetworkInterface(networkInterface: NodeP2PNetworkInterface) {
    if (this.networkInterface != null) removeCurrentNetworkInterface()
    setNewNetworkInterface(networkInterface)
  }

  private fun removeCurrentNetworkInterface() {
    networkInterface?.setOnMessageReceivedCallback(null)
    networkInterface?.setOnMessageDroppedCallback(null)
    networkInterface = null
  }

  private fun setNewNetworkInterface(networkInterface: NodeP2PNetworkInterface) {
    this.networkInterface = networkInterface
    this.networkInterface?.setOnMessageReceivedCallback { message, senderNetworkEndpoint ->
      this.onMessageReceivedFromNetworkInterface(
        message, senderNetworkEndpoint
      )
    }
    this.networkInterface?.setOnMessageDroppedCallback { message, recipientNetworkEndpoint ->
      this.failedToSendMessageToNetworkInterface(
        message, recipientNetworkEndpoint
      )
    }
  }

  override fun setOnReceivedCallback(onReceivedCallback: ((E) -> Unit)) {
    this.onReceivedCallback = onReceivedCallback
  }

  override fun dispatchEvent(event: Event) {
  }
}