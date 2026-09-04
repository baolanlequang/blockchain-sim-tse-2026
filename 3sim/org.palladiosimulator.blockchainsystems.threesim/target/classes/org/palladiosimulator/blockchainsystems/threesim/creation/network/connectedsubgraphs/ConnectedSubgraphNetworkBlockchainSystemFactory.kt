package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem as DesignBlockchainSystem

/** Factory for the refined connected NSB topology. */
class ConnectedSubgraphNetworkBlockchainSystemFactory @JvmOverloads constructor(
  designBlockchainSystem: DesignBlockchainSystem,
  connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  attackSimulation: Boolean,
  runId: Int = 0,
  gamma: Double = 0.5,
  networkSeed: Long = 0L,
  eventSeed: Long = 0L
) : ThreesimBlockchainSystemFactory(
  designBlockchainSystem,
  connectedSubgraphsTopology,
  attackSimulation,
  runId,
  gamma,
  networkSeed,
  eventSeed
) {
  override fun createP2PNetworkFactory(): P2PNetworkFactory =
    ConnectedSubgraphP2PNetworkFactory(
      networkTopology as ConnectedSubgraphsNetworkTopology,
      randomness
    )

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
      networkCreationResult.nodeIdToIndexMapping,
      randomness
    )
  }
}
