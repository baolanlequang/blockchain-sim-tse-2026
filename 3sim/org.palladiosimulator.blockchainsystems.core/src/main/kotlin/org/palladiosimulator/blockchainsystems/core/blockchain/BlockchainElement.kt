package org.palladiosimulator.blockchainsystems.core.blockchain

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import java.util.Collections

/**
 * An element in the simulator's blockchain abstraction
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainElement(
  val block: Block,
  val previousBlockchainElement: BlockchainElement?,
  var type: BlockchainElementType,
  val position: Long
) {
  private val mutableNextBlockchainElements: HashSet<BlockchainElement> = hashSetOf()

  val nextBlockchainElements: Set<BlockchainElement>
    get() = Collections.unmodifiableSet(mutableNextBlockchainElements)

  init {
    previousBlockchainElement?.mutableNextBlockchainElements?.add(this)
  }
}