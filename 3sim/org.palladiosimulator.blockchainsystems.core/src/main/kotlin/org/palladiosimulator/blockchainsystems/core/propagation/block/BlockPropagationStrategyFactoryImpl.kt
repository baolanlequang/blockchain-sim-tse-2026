package org.palladiosimulator.blockchainsystems.core.propagation.block

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategyFactory

/**
 * Factory implementation for creating instances of BlockPropagationStrategy.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockPropagationStrategyFactoryImpl : PropagationStrategyFactory<Block> {
  override fun createPropagationStrategy(): PropagationStrategy<Block> {
    return BlockPropagationStrategy()
  }
}