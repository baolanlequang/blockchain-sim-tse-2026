package org.palladiosimulator.blockchainsystems.core.block.abstractions

/**
 * Interface for a factory that produces instances of `BlockValidator`.
 *
 * @author Yannik Sproll
 */
interface BlockValidatorFactory {
  /**
   * Returns an instance of [BlockValidator] for a specified blockchain system node.
   *
   * @param nodeId the id of the node the created `MiningProcess` belongs to
   *
   * @return instance of [BlockValidator]
   */
  fun createBlockValidator(nodeId: String): BlockValidator
}