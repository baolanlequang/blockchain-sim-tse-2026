package org.palladiosimulator.blockchainsystems.core.system.abstractions

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable

/**
 * The @code{MiningProcess} interface represents the mining process running
 * on a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface MiningProcess : Traceable {

  /**
   * Sets the callback that is invoked when the mining process mined a new block.
   *
   * @param onBlockMinedCallback the mining finished callback
   */
  fun setOnBlockMinedCallback(onBlockMinedCallback: ((Block) -> Unit)?)

  /**
   * Sets the callback that is invoked when a block object must be created for the mining process.
   *
   * @param onCreatingBlockCallback the block creation callback
   */
  fun setOnCreatingBlockCallback(onCreatingBlockCallback: ((Long, String) -> Block)?)

  /**
   * Sets the callback that requests a previous block for the next block to be mined.
   *
   * @param previousBlockSelectionCallback previous block selection callback
   */
  fun setPreviousBlockSelectionCallback(previousBlockSelectionCallback: (() -> String)?)

  /**
   * Starts the mining of new blocks.
   */
  fun startMining()

  /**
   * Interrupts the mining of the current process and starts the mining of a new block.
   */
  fun restartMining()

  /**
   * Stops the mining of new blocks.
   */
  fun stopMining()
}