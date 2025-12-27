package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeTagProvider

class ThreesimBlockchainSystemNodeTagProvider : BlockchainSystemNodeTagProvider {
  override fun getTags(nodeId: String): Set<String> {
    return emptySet() // NOTE: tags not used in 3SIM (unlike SM-SIM)
  }
}