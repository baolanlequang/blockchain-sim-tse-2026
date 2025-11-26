package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver

/**
 * This class resolves the node allocations for a connected subgraph network topology.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ConnectedSubgraphNetworkNodeAllocationResolver(
  connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  nodeIdToNodeTemplateIdMapping: HashMap<String, String>
) : NodeAllocationResolver {
  private val nodeIdToNodeTemplatesMappings: Map<String, SubgraphNodeTemplate>

  init {
    val nodeTemplatesByIds = connectedSubgraphsTopology
      .subgraphs
      .flatMap { x -> x.nodeTemplates }
      .associateBy { it.id }

    nodeIdToNodeTemplatesMappings =
      nodeIdToNodeTemplateIdMapping.mapNotNull { (nodeId, templateId) ->
        nodeTemplatesByIds[templateId]?.let { nodeId to it }
      }.toMap()
  }

  override fun getNodeAllocation(nodeId: String): NodeAllocation? {
    return nodeIdToNodeTemplatesMappings[nodeId]?.allocation
  }
}