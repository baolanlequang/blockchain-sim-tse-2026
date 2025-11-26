package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * The [NodeP2PNetworkInterface] interface represents the interface
 * between a blockchain system node and the underlying P2P network.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface NodeP2PNetworkInterface : P2PNetworkEndpoint {
  /**
   * Sends (multicasts) a given message to all neighbors of a blockchain system node.
   *
   * @param message the message to be multicasted
   */
  fun multicast(message: Message)

  /**
   * Sends the specified message to the specified recipient.
   * This recipient must be a neighbor of the current blockchain system node.
   *
   * @param message   the message to send
   * @param recipient the recipient neighbor of the message
   */
  fun send(message: Message, recipient: P2PNetworkEndpoint)

  /**
   * Returns a set of network endpoints, one for each neighbor node.
   *
   * @return set of neighbor network endpoints
   */
  fun getNeighbors(): MutableSet<P2PNetworkEndpoint>

  /**
   * Sets the callback that is invoked when a message is received from
   * one of the current blockchain system neighbors.
   *
   * @param onMessageReceivedCallback the callback to be set
   */
  fun setOnMessageReceivedCallback(onMessageReceivedCallback: ((Message, P2PNetworkEndpoint) -> Unit)?)

  /**
   * Sets the callback that is invoked when the recipient node
   * dropped the message that was sent to it.
   *
   * @param onMessageDroppedCallback the callback to be set
   */
  fun setOnMessageDroppedCallback(onMessageDroppedCallback: ((Message, P2PNetworkEndpoint) -> Unit)?)
}