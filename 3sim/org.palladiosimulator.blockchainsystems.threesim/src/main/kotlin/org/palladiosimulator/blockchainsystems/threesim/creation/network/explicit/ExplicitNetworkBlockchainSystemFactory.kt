package org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem as DesignBlockchainSystem

/**
 * Factory for creating a [BlockchainSystem] based on an [ExplicitNetworkTopology].
 *
 * @author Davis Riedel
 */
class ExplicitNetworkBlockchainSystemFactory(
  designBlockchainSystem: DesignBlockchainSystem,
  explicitTopology: ExplicitNetworkTopology
) : ThreesimBlockchainSystemFactory(designBlockchainSystem, explicitTopology) {

  override fun createP2PNetworkFactory(): P2PNetworkFactory {
    return ExplicitTopologyP2PNetworkFactory(networkTopology as ExplicitNetworkTopology)
  }

  override fun getNodeAllocationResolver(networkCreationResult: P2PNetworkCreationResult): NodeAllocationResolver {
    return ExplicitNetworkNodeAllocationResolver(networkTopology as ExplicitNetworkTopology)
  }

  override fun getResourcePowerCalculator(networkCreationResult: P2PNetworkCreationResult): ResourcePowerCalculator {
    return ExplicitNetworkResourcePowerCalculator(
      networkTopology as ExplicitNetworkTopology,
      designBlockchainSystem.specification.hashRateConcentration
    )
  }
}