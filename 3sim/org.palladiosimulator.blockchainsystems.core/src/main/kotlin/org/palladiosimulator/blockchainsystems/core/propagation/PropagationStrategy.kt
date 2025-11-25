package org.palladiosimulator.blockchainsystems.core.propagation

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Blockchain
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext
import org.palladiosimulator.blockchainsystems.core.system.abstractions.NodeP2PNetworkInterface
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint

/**
 * The [PropagationStrategy] interface is an abstraction
 * of the strategy used to exchange blocks and transactions between nodes.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface PropagationStrategy<E : Propagatable> : Traceable {
  /**
   * Distributes the specified element to all neighbors.
   *
   * @param element the element to distribute
   */
  fun distribute(element: E)

  /**
   * Distributes the specified element to the specified neighbors.
   *
   * @param element           the element to distribute
   * @param neighborEndpoints the neighbors to receive the element
   */
  fun distribute(element: E, neighborEndpoints: MutableSet<P2PNetworkEndpoint>)

  /**
   * Sets the network interface used to send the elements to the neighbors.
   *
   * @param networkInterface used to send elements
   */
  fun setNetworkInterface(networkInterface: NodeP2PNetworkInterface)

  /**
   * Sets the blockchain system node context for this propagation strategy.
   *
   * @param context the blockchain system node context to set
   */
  fun setBlockchainSystemNodeContext(context: BlockchainSystemNodeContext)

  /**
   * Sets a callback that is invoked if an element is received from the assigned network interface.
   *
   * @param onReceivedCallback callback that is invoked when a new element is received
   */
  fun setOnReceivedCallback(onReceivedCallback: (E) -> Unit)
}