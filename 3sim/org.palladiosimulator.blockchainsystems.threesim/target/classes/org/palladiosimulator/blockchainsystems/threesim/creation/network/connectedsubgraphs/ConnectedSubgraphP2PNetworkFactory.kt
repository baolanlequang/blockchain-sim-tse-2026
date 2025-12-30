package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.jgrapht.graph.SimpleDirectedGraph
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification
import org.palladiosimulator.blockchainsystems.core.network.P2PLink
import org.palladiosimulator.blockchainsystems.core.network.P2PNetworkImpl
import org.palladiosimulator.blockchainsystems.core.network.P2PNode
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult
import java.util.UUID
import org.palladiosimulator.blockchainsystems.core.utils.CounterMap
import org.palladiosimulator.blockchainsystems.threesim.creation.network.AbstractThreesimP2PNetworkFactory
import org.palladiosimulator.blockchainsystems.threesim.utils.addBidirectionalEdge

/**
 * Factory for creating a P2P network based on a connected subgraph topology.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ConnectedSubgraphP2PNetworkFactory(
  private val topology: ConnectedSubgraphsNetworkTopology
) : AbstractThreesimP2PNetworkFactory() {
  override fun createP2PNetwork(): P2PNetworkCreationResult {
    val nodeIdToNodeTemplateIdMapping = HashMap<String, String>()

    val subgraphIdToSubgraphNodesMapping = HashMap<String, HashSet<P2PNode>>()
    val subGraphIdToProxyNodesMapping = HashMap<String, HashSet<P2PNode>>()
    val subGraphIdToLinkSpecificationMapping = HashMap<String, SubgraphSpecification>()

    // Fill mappings and create P2PNode instances
    topology.subgraphs.forEach { subgraphSpec ->
      subgraphIdToSubgraphNodesMapping.put(subgraphSpec.id, HashSet())
      subGraphIdToProxyNodesMapping.put(subgraphSpec.id, HashSet())
      subGraphIdToLinkSpecificationMapping.put(subgraphSpec.id, subgraphSpec)

      subgraphSpec.nodeTemplates.forEach { nodeTemplate ->
        (0 until nodeTemplate.numberOfNodeOccurences).forEach { _ ->
          val p2pNodeId = UUID.randomUUID().toString()
          val node = P2PNode(p2pNodeId)

          subgraphIdToSubgraphNodesMapping.get(subgraphSpec.id)?.add(node)

          if (nodeTemplate.isIsSubgraphProxy()) {
            subGraphIdToProxyNodesMapping.get(subgraphSpec.id)?.add(node)
          }

          nodeIdToNodeTemplateIdMapping.put(p2pNodeId, nodeTemplate.id)
        }
      }
    }

    val networkGraph = SimpleDirectedGraph<P2PNode, P2PLink>(P2PLink::class.java)

    // Create each subgraph and add it to the networkGraph
    subgraphIdToSubgraphNodesMapping.entries.forEach { (subgraphId, subgraphNodes) ->
      val initialDegrees = CounterMap<P2PNode>()
      val subgraphSpec = subGraphIdToLinkSpecificationMapping.get(subgraphId) ?: return@forEach

      // Add vertices of subgraph
      subgraphNodes.forEach { node ->
        networkGraph.addVertex(node)
        initialDegrees.put(node, subgraphSpec.connectivity)
      }

      // Get link allocation for subgraph internal links
      val subgraphLinkSpecification = subgraphSpec.linkAllocation

      val latencyValueProvider = createLatencyValueProvider(subgraphLinkSpecification.latencySpecification)
      val throughputValueProvider = createThroughputValueProvider(subgraphLinkSpecification.throughputSpecification)
      val bandwidthValueProvider = createBandwidthValueProvider(subgraphLinkSpecification.bandwidthSpecification)

      // Create a bidirectional spanning tree in subgraph
      subgraphNodes
        .windowed(2)
        .forEach { (firstNode, secondNode) ->
          networkGraph.addBidirectionalEdge(
            firstNode,
            secondNode,
            fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
              latencyValueProvider,
              throughputValueProvider,
              bandwidthValueProvider,
              fromVertex,
              toVertex
            )
          )

          initialDegrees.decrement(firstNode)
          initialDegrees.decrement(secondNode)
        }

      // Enhance with random bidirectional edges
      val nodesToEnhance = initialDegrees.keys.toTypedArray()

      nodesToEnhance.forEach { currentNode ->
        while (initialDegrees.get(currentNode) > 0) {
          val potentialNodes = initialDegrees.keys
            .filterNot {
              it == currentNode
                || networkGraph.containsEdge(it, currentNode)
                || networkGraph.containsEdge(currentNode, it)
            }

          if (potentialNodes.isEmpty()) {
            // Sometimes each node except for the last one has reached the maximum degree
            // The strategy here is to neglect the range parameters for the last node
            initialDegrees.decrement(currentNode)
            continue
          }

          val selectedNode = potentialNodes.random()

          networkGraph.addBidirectionalEdge(
            currentNode,
            selectedNode,
            fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
              latencyValueProvider,
              throughputValueProvider,
              bandwidthValueProvider,
              fromVertex,
              toVertex
            )
          )

          initialDegrees.decrement(currentNode)
          initialDegrees.decrement(selectedNode)
        }
      }
    }

    // Add connections between the proxies of the subgraph
    topology.subgraphLinks.forEach { subgraphLink ->
      val firstSubgraphSpec = subgraphLink.connectedSubgraphs[0]
      val secondSubgraphSpec = subgraphLink.connectedSubgraphs[1]

      val firstSubgraphProxies =
        subGraphIdToProxyNodesMapping.get(firstSubgraphSpec.id)?.toTypedArray() ?: return@forEach
      val secondSubgraphProxies =
        subGraphIdToProxyNodesMapping.get(secondSubgraphSpec.id)?.toTypedArray() ?: return@forEach

      val subgraphLinkSpecification = subgraphLink.allocation

      val latencyValueProvider = createLatencyValueProvider(subgraphLinkSpecification.latencySpecification)
      val throughputValueProvider = createThroughputValueProvider(subgraphLinkSpecification.throughputSpecification)
      val bandwidthValueProvider = createBandwidthValueProvider(subgraphLinkSpecification.bandwidthSpecification)

      firstSubgraphProxies.forEach { firstSubgraphProxy ->
        secondSubgraphProxies.forEach { secondSubgraphProxy ->
          networkGraph.addBidirectionalEdge(
            firstSubgraphProxy,
            secondSubgraphProxy,
            fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
              latencyValueProvider,
              throughputValueProvider,
              bandwidthValueProvider,
              fromVertex,
              toVertex
            )
          )
        }
      }
    }

    val networkImpl = P2PNetworkImpl.create(networkGraph)

    networkGraph.vertexSet().forEach { it.initNetwork(networkImpl) }

    return ConnectedSubgraphNetworkCreationResult(
      networkImpl,
      nodeIdToNodeTemplateIdMapping
    )
  }
}