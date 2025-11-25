package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import kotlin.collections.get

/**
 * This class calculates the global resource power of a connected subgraph network topology.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ConnectedSubgraphNetworkResourcePowerCalculator(
  private val connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  private val nodeIdToNodeTemplateIdMapping: HashMap<String, String>
) : ResourcePowerCalculator {
  private val resourcePowerPerNodeTemplate: Map<String, Double> by lazy {
    connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .associateBy { it.id }
      .mapValues { getResourcePowerOfAllocation(it.value.allocation) }
  }

  private val globalResourcePower: Double by lazy {
    connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences * getResourcePowerOfAllocation(it.allocation) }
  }

  private fun getResourcePowerOfAllocation(nodeAllocation: NodeAllocation): Double {
    return nodeAllocation
      .allocationContexts
      .filter { it.assemblyContext.encapsulatedComponent is MiningProcessComponent }
      .sumOf { it.resourceContainer.resourcePower }
  }

  override fun calculateGlobalResourcePower(): Double {
    return globalResourcePower // in MH/s
  }

  override fun getResourcePowerOfNode(nodeId: String): Double? {
    val nodeTemplateId = nodeIdToNodeTemplateIdMapping[nodeId]
    return resourcePowerPerNodeTemplate[nodeTemplateId] // in MH/s
  }
}