package org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver

/**
 * This class resolves the node allocations for a blockchain system with an explicit network topology.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ExplicitNetworkNodeAllocationResolver(
  networkTopology: ExplicitNetworkTopology
) : NodeAllocationResolver {
  private val nodeIdToNodeMappings: Map<String, Node> = networkTopology.nodes.associateBy { it.id }

  override fun getNodeAllocation(nodeId: String): NodeAllocation? {
    return nodeIdToNodeMappings[nodeId]?.allocation
  }
}