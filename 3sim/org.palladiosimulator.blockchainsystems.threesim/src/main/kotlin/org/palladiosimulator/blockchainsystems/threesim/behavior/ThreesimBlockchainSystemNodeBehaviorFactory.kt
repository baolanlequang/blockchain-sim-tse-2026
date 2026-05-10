package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.behavior.HonestBlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory
import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousNodesIdProvider
import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousBlockchainSystemNodeBehavior
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousNodesIdProviderImpl

/**
 * Factory for creating a [BlockchainSystemNodeBehavior] for the Threesim blockchain system.
 *
 * This factory creates an instance of [HonestBlockchainSystemNodeBehavior], which represents a node that behaves honestly
 * in the blockchain system.
 *
 * @author Davis Riedel
 */
class ThreesimBlockchainSystemNodeBehaviorFactory(
  private val resourcePowerCalculator: ResourcePowerCalculator,
  private val numberOfAttacker: Int
) : BlockchainSystemNodeBehaviorFactory {
//  val maliciousNodesIdProvider: MaliciousNodesIdProvider = MaliciousNodesIdProviderImpl(mutableSetOf(), numberOfAttacker)

  override fun create(nodeId: String, maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider): BlockchainSystemNodeBehavior {
    if (resourcePowerCalculator != null) {
//      val resourcePower = resourcePowerCalculator.getResourcePowerOfNode(nodeId) ?: 0.0
//      val globalResourcePower = resourcePowerCalculator.calculateGlobalResourcePower()
//      if (resourcePower / globalResourcePower <= 0.25 && maliciousNodesIdProvider.getMaliciousNodeIds().size < numberOfAttacker) {
//        maliciousNodesIdProvider.addMaliciousNodeId(nodeId)
//      }
      if (maliciousNodesIdProvider.getMaliciousNodeIds().size < numberOfAttacker) {
        maliciousNodesIdProvider.addMaliciousNodeId(nodeId)
      }

    }

    if (maliciousNodesIdProvider.getMaliciousNodeIds().contains(nodeId)) {
      return MaliciousBlockchainSystemNodeBehavior(maliciousNodesIdProvider);
    }
    return HonestBlockchainSystemNodeBehavior()
  }
}