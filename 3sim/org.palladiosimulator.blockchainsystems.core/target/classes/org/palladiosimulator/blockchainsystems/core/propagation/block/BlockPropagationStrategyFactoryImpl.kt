package org.palladiosimulator.blockchainsystems.core.propagation.block

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategyFactory
import org.palladiosimulator.blockchainsystems.core.scalability.ScalabilityStateTracker

/**
 * Factory implementation for creating instances of BlockPropagationStrategy.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockPropagationStrategyFactoryImpl(
  private val scalabilityStateTracker: ScalabilityStateTracker? = null
) : PropagationStrategyFactory<Block> {
  override fun createPropagationStrategy(): PropagationStrategy<Block> {
    return BlockPropagationStrategy(scalabilityStateTracker)
  }
}