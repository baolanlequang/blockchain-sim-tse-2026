package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * Interface for a factory that produces instances of `BlockchainSystemNodeBehavior`.
 *
 * @author Yannik Sproll
 */
interface BlockchainSystemNodeBehaviorFactory {
  /**
   * Creates an instance of `BlockchainSystemNodeBehavior` for a specified blockchain system node.
   *
   * @param nodeId the id of the node the created `BlockchainSystemNodeBehavior` belongs to
   * @return a `BlockchainSystemNodeBehavior` instance
   */
  fun create(nodeId: String): BlockchainSystemNodeBehavior
}