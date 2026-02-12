package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeTagProvider
import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousNodesIdProvider

class ThreesimBlockchainSystemNodeTagProvider(
  private val maliciousNodesIdProvider: MaliciousNodesIdProvider
) : BlockchainSystemNodeTagProvider {

  override fun getTags(nodeId: String): Set<String> {
    if (maliciousNodesIdProvider.getMaliciousNodeIds().contains(nodeId)) {
      return hashSetOf("MaliciousNode")
    }
    return emptySet()
  }
}