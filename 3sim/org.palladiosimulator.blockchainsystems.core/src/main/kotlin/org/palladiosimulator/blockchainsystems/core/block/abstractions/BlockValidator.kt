package org.palladiosimulator.blockchainsystems.core.block.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable

/**
 * The @code{BlockValidator} interface is an abstraction of the block validation component
 * of a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface BlockValidator : Traceable {
  /**
   * Submits a block for validation.
   *
   * @param block the block to be validated.
   */
  fun validateBlock(block: Block)

  /**
   * Sets a callback that is invoked if the validation of a block has finished.
   *
   * @param onBlockValidatedCallback callback that is invoked when a block validation is finished
   */
  fun setOnBlockValidatedCallback(onBlockValidatedCallback: (Block, Boolean) -> Unit)
}