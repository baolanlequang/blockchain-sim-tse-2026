package org.palladiosimulator.blockchainsystems.core.system.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAware

/**
 * The @code{P2PNetwork} interface is an abstraction of a
 * blockchain system's underlying P2P network.
 *
 * @author Yannik Sproll
 */
interface P2PNetwork : SimulationLifecycleAware {
  /**
   * Returns all network interfaces of the network.
   *
   * @return set of network interfaces
   */
  val nodes: MutableSet<NodeP2PNetworkInterface>

  /**
   * Returns the neighbors of the specified network interface
   *
   * @param networkInterface the network interface
   * @return a set of neighbor network endpoints
   */
  fun getNeighbors(networkInterface: NodeP2PNetworkInterface): MutableSet<P2PNetworkEndpoint>

  /**
   * Multicasts the specified message from the sending network interface
   * to all of its neighbors.
   *
   * @param sendingNetworkInterface the network interface from which the message is sent
   * @param content the message that is multicasted
   */
  fun multicast(sendingNetworkInterface: NodeP2PNetworkInterface, content: Message)

  /**
   * Sends a message from the sending network interface to the receiving network interface.
   *
   * @param sendingNetworkInterface the network interface that sends the specified message
   * @param receivingNetworkInterface the network interface that receives the specified message
   * @param content the message that is sent.
   */
  fun send(
    sendingNetworkInterface: NodeP2PNetworkInterface,
    receivingNetworkInterface: NodeP2PNetworkInterface,
    content: Message
  )
}