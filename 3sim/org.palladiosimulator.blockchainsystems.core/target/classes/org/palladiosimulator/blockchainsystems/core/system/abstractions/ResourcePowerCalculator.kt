package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * Interface for calculating the resource power of a blockchain system.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface ResourcePowerCalculator {
  /**
   * Calculates the global resource power (MH/s) of the blockchain system.
   *
   * @return The total resource power of all nodes in the system.
   */
  fun calculateGlobalResourcePower(): Double

  /**
   * Retrieves the resource power (MH/s) of a specific node in the blockchain system.
   *
   * @param nodeId The ID of the node for which to retrieve the resource power.
   *
   * @return The resource power of the specified node, or null if no node with the given id exists.
   */
  fun getResourcePowerOfNode(nodeId: String): Double?
}