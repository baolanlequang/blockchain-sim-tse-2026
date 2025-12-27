package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.behavior.HonestBlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory

/**
 * Factory for creating a [BlockchainSystemNodeBehavior] for the Threesim blockchain system.
 *
 * This factory creates an instance of [HonestBlockchainSystemNodeBehavior], which represents a node that behaves honestly
 * in the blockchain system.
 *
 * @author Davis Riedel
 */
class ThreesimBlockchainSystemNodeBehaviorFactory : BlockchainSystemNodeBehaviorFactory {
  override fun create(nodeId: String): BlockchainSystemNodeBehavior {
    return HonestBlockchainSystemNodeBehavior()
  }
}