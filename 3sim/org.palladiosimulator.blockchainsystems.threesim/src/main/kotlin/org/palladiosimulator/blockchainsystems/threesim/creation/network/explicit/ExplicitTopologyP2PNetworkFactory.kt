package org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit

import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink
import org.palladiosimulator.blockchainsystems.core.network.P2PLink
import org.palladiosimulator.blockchainsystems.core.network.P2PNetworkImpl
import org.palladiosimulator.blockchainsystems.core.network.P2PNode
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult
import org.palladiosimulator.blockchainsystems.threesim.creation.network.AbstractThreesimP2PNetworkFactory

/**
 * Factory for creating a P2P network based on an explicit network topology from the metamodel.
 *
 * @param topology the explicit network topology obtained from the metamodel.
 *
 * @author Davis Riedel
 */
class ExplicitTopologyP2PNetworkFactory(
  private val topology: ExplicitNetworkTopology,
) : AbstractThreesimP2PNetworkFactory() {
  override fun createP2PNetwork(): P2PNetworkCreationResult {
    val networkGraph: Graph<P2PNode, P2PLink> = SimpleDirectedGraph(P2PLink::class.java)

    // Store attacker information
    val attackerNodeIds = ArrayList<String>()

    // Add nodes to the graph
    val p2pNodeMappings = topology.nodes.associate {
      val nodeImpl = P2PNode(it.id)
      networkGraph.addVertex(nodeImpl)
      it.id to nodeImpl
    }

    // Add links to the graph
    topology.links.forEach { designLink ->
      val linkSpecification = designLink.allocation
      val latencyValueProvider = createLatencyValueProvider(linkSpecification.latencySpecification)
      val throughputValueProvider = createThroughputValueProvider(linkSpecification.throughputSpecification)
      val bandwidthValueProvider = createBandwidthValueProvider(linkSpecification.bandwidthSpecification)

      when (designLink) {
        is UnidirectionalLink -> {
          listOf(Pair(designLink.fromNode.id, designLink.toNode.id))
        }

        is BidirectionalLink -> {
          require(designLink.connectedNodes.size == 2) {
            "Bidirectional link must connect exactly two nodes."
          } // This is ensured in the ecore metamodel

          listOf(
            Pair(designLink.connectedNodes[0].id, designLink.connectedNodes[1].id),
            Pair(designLink.connectedNodes[1].id, designLink.connectedNodes[0].id)
          )
        }

        else -> throw IllegalArgumentException("Unsupported link type: ${designLink::class.java.name}")
      }
        // Create P2PLink instances for each link
        .mapNotNull { (fromNodeId, toNodeId) ->
          val fromP2PNode = p2pNodeMappings[fromNodeId] ?: return@mapNotNull null
          val toP2PNode = p2pNodeMappings[toNodeId] ?: return@mapNotNull null

          P2PLink(
            latencyValueProvider,
            throughputValueProvider,
            bandwidthValueProvider,
            fromP2PNode,
            toP2PNode
          )
        }
        // Add the P2PLinks to the graph
        .forEach {
          networkGraph.addEdge(
            it.fromNode,
            it.toNode,
            it
          )
        }
    }

    val networkImpl = P2PNetworkImpl.create(networkGraph)

    // Initialize the nodes with a reference to the network
    p2pNodeMappings.values.forEach { it.initNetwork(networkImpl) }

    return ExplicitP2PNetworkCreationResult(networkImpl)
  }
}