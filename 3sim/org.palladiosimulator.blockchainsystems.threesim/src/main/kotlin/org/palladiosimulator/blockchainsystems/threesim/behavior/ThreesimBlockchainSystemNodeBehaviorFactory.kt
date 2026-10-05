package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.behavior.GammaAwareHonestBlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness

/**
 * Factory for creating a [BlockchainSystemNodeBehavior] for the Threesim blockchain system.
 *
 * This factory creates honest nodes for executions without adversarial nodes. It uses the same
 * behavior class as honest nodes in attack executions so that the honest rules do not depend on f_A. It is used for the plain (non-attack) trilemma pipeline, where there
 * are no attacker nodes; attack runs use [org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior.SelfishMiningBlockchainSystemNodeBehaviorFactory] instead.
 *
 * @author Davis Riedel
 */
class ThreesimBlockchainSystemNodeBehaviorFactory(
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : BlockchainSystemNodeBehaviorFactory {
  override fun create(nodeId: String, maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider): BlockchainSystemNodeBehavior {
    // Same honest rules as in executions with adversarial nodes; with an empty
    // attacker set the gamma rule never applies.
    return GammaAwareHonestBlockchainSystemNodeBehavior(
      emptySet(),
      0.0,
      randomness.eventForNode("honest-behavior", nodeId)
    )
  }
}