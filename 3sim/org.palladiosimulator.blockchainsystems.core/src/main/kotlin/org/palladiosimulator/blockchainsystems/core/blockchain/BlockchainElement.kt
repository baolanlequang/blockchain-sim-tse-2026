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
  // Most blockchain elements have exactly one child. Avoid allocating a HashSet
  // (and its backing HashMap/table) for every element; promote to a set only when
  // a real fork creates a second distinct successor.
  private var singleNextBlockchainElement: BlockchainElement? = null
  private var multipleNextBlockchainElements: HashSet<BlockchainElement>? = null

  val nextBlockchainElements: Set<BlockchainElement>
    get() {
      multipleNextBlockchainElements?.let { return Collections.unmodifiableSet(it) }
      return singleNextBlockchainElement?.let { Collections.singleton(it) } ?: emptySet()
    }

  private fun addNextBlockchainElement(element: BlockchainElement) {
    val multiple = multipleNextBlockchainElements
    if (multiple != null) {
      multiple.add(element)
      return
    }

    val single = singleNextBlockchainElement
    if (single == null) {
      singleNextBlockchainElement = element
      return
    }

    if (single === element) return

    multipleNextBlockchainElements = hashSetOf(single, element)
    singleNextBlockchainElement = null
  }

  init {
    previousBlockchainElement?.addNextBlockchainElement(this)
  }
}
