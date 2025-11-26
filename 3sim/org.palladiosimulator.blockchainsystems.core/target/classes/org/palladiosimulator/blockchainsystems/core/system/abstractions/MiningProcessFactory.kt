package org.palladiosimulator.blockchainsystems.core.system.abstractions

/**
 * Interface for a factory that produces instances of `MiningProcess`.
 *
 * @author Yannik Sproll
 */
interface MiningProcessFactory {
  /**
   * Creates an instance of @code{MiningProcess} for a specified blockchain system node.
   *
   * @param nodeId the id of the node the created `MiningProcess` belongs to
   * @return a @code{MiningProcess} instance
   */
  fun createMiningProcess(nodeId: String): MiningProcess
}