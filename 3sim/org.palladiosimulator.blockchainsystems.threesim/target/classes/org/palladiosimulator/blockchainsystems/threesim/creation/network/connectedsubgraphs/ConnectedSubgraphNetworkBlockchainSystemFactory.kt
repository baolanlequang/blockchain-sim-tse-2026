package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import java.util.random.RandomGenerator
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem as DesignBlockchainSystem

/**
 * Factory for creating a [BlockchainSystem] based on an [ConnectedSubgraphsNetworkTopology].
 *
 * @author Davis Riedel
 */
class ConnectedSubgraphNetworkBlockchainSystemFactory(
  designBlockchainSystem: DesignBlockchainSystem,
  connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  attackSimulation: Boolean
) : ThreesimBlockchainSystemFactory(designBlockchainSystem, connectedSubgraphsTopology, attackSimulation) {
  override fun createP2PNetworkFactory(): P2PNetworkFactory {
    return ConnectedSubgraphP2PNetworkFactory(
      networkTopology as ConnectedSubgraphsNetworkTopology
    )
  }

  override fun getNodeAllocationResolver(networkCreationResult: P2PNetworkCreationResult): NodeAllocationResolver {
    networkCreationResult as ConnectedSubgraphNetworkCreationResult
    return ConnectedSubgraphNetworkNodeAllocationResolver(
      networkTopology as ConnectedSubgraphsNetworkTopology,
      networkCreationResult.nodeIdToNodeTemplateIdMapping
    )
  }

  override fun getResourcePowerCalculator(networkCreationResult: P2PNetworkCreationResult): ResourcePowerCalculator {
    networkCreationResult as ConnectedSubgraphNetworkCreationResult
    return ConnectedSubgraphNetworkResourcePowerCalculator(
      networkTopology as ConnectedSubgraphsNetworkTopology,
      networkCreationResult.nodeIdToNodeTemplateIdMapping,
      designBlockchainSystem.specification.hashRateConcentration,
      networkCreationResult.nodeIdToIndexMapping
    )
  }
}