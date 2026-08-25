package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.behavior.HonestBlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness

/**
 * Factory for creating a [BlockchainSystemNodeBehavior] for the Threesim blockchain system.
 *
 * This factory creates an instance of [HonestBlockchainSystemNodeBehavior], which represents a node that behaves honestly
 * in the blockchain system. It is used for the plain (non-attack) trilemma pipeline, where there
 * are no attacker nodes; attack runs use [org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior.SelfishMiningBlockchainSystemNodeBehaviorFactory] instead.
 *
 * @author Davis Riedel
 */
class ThreesimBlockchainSystemNodeBehaviorFactory(
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : BlockchainSystemNodeBehaviorFactory {
  override fun create(nodeId: String, maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider): BlockchainSystemNodeBehavior {
    return HonestBlockchainSystemNodeBehavior(randomness.eventForNode("honest-behavior", nodeId))
  }
}