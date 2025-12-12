package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * The `P2PNetworkCreationResult` class is a wrapper for `P2PNetwork` instances
 * created by a `P2PNetworkFactory`.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface P2PNetworkCreationResult {
  /**
   * Returns the created instance of the `P2PNetwork`.
   *
   * @return instance of `P2PNetwork`
   */
  val createdNetwork: P2PNetwork
}