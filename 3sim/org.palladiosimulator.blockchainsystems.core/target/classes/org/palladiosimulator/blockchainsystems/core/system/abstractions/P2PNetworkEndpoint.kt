package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * The @code{NetworkEndpoint} interface represents a neighbor of a
 * blockchain system node.
 *
 * @author Yannik Sproll
 */
interface P2PNetworkEndpoint {
  /**
   * Returns an identifier of the network endpoint.
   *
   * @return the identifier of the network endpoint
   */
  val endpointId: String
}