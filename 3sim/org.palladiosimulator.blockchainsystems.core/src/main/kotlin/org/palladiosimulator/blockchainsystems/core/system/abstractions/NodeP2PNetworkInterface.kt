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
   * Registers a listener for messages received from a neighbor.
   *
   * A node hosts several propagation strategies (blocks and transactions) on the
   * same interface, so the interface must deliver every message to every
   * registered listener. Each strategy ignores content types it does not own.
   * (A single overwritable callback silently disabled transaction gossip.)
   */
  fun addMessageReceivedListener(listener: (Message, P2PNetworkEndpoint) -> Unit)

  /** Removes a listener previously registered with [addMessageReceivedListener]. */
  fun removeMessageReceivedListener(listener: (Message, P2PNetworkEndpoint) -> Unit)

  /** Registers a listener for messages that could not be delivered to a neighbor. */
  fun addMessageDroppedListener(listener: (Message, P2PNetworkEndpoint) -> Unit)

  /** Removes a listener previously registered with [addMessageDroppedListener]. */
  fun removeMessageDroppedListener(listener: (Message, P2PNetworkEndpoint) -> Unit)
}
