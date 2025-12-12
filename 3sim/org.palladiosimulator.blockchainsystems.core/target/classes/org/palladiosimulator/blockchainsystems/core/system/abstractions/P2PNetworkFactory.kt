package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * Interface for a factory that produces instances of `P2PNetwork`.
 *
 * @author Yannik Sproll
 */
interface P2PNetworkFactory {
  /**
   * Creates an instance of @code{P2PNetwork} and returns and instance of the `P2PNetworkCreationResult`
   * that contains the created `P2PNetwork` instance.
   *
   * @return a @code{P2PNetworkCreationResult} instance, containing the created network
   */
  fun createP2PNetwork(): P2PNetworkCreationResult
}