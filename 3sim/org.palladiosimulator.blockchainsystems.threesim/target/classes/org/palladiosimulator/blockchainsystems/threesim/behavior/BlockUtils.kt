package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block

object BlockUtils {
  const val FORKED_BLOCK_NAME = "ForkedBlock"

  fun isBlockForked(
    block: Block
  ): Boolean {
    return block.hasTag(FORKED_BLOCK_NAME)
  }
}