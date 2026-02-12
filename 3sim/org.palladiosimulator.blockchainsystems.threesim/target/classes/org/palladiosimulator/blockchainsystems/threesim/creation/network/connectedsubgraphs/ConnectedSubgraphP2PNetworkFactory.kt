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
import org.palladiosimulator.blockchainsystems.core.network.BandwidthDistribution
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
    val nodeIdToIndexMapping = HashMap<String, Int>()

    val subgraphIdToSubgraphNodesMapping = HashMap<String, HashSet<P2PNode>>()
    val subGraphIdToProxyNodesMapping = HashMap<String, HashSet<P2PNode>>()
    val subGraphIdToLinkSpecificationMapping = HashMap<String, SubgraphSpecification>()

    // Fill mappings and create P2PNode instances
    topology.subgraphs.forEach { subgraphSpec ->
      subgraphIdToSubgraphNodesMapping.put(subgraphSpec.id, HashSet())
      subGraphIdToProxyNodesMapping.put(subgraphSpec.id, HashSet())
      subGraphIdToLinkSpecificationMapping.put(subgraphSpec.id, subgraphSpec)

      subgraphSpec.nodeTemplates.forEach { nodeTemplate ->
        (0 until nodeTemplate.numberOfNodeOccurences).forEach { idx ->
//          println("nodeindex: " + idx)
          val p2pNodeId = UUID.randomUUID().toString()
          val node = P2PNode(p2pNodeId)

          subgraphIdToSubgraphNodesMapping.get(subgraphSpec.id)?.add(node)

          if (nodeTemplate.isIsSubgraphProxy()) {
            subGraphIdToProxyNodesMapping.get(subgraphSpec.id)?.add(node)
          }

          nodeIdToNodeTemplateIdMapping.put(p2pNodeId, nodeTemplate.id)
          nodeIdToIndexMapping.put(p2pNodeId, idx)
        }
      }
    }

    val networkGraph = SimpleDirectedGraph<P2PNode, P2PLink>(P2PLink::class.java)

    // Create each subgraph and add it to the networkGraph
    subgraphIdToSubgraphNodesMapping.entries.forEach { (subgraphId, subgraphNodes) ->
      val initialDegrees = CounterMap<P2PNode>()
      val subgraphSpec = subGraphIdToLinkSpecificationMapping.get(subgraphId) ?: return@forEach

//      // Add vertices of subgraph
//      subgraphNodes.forEach { node ->
//        networkGraph.addVertex(node)
//        initialDegrees.put(node, subgraphSpec.connectivity)
//      }

      // Get link allocation for subgraph internal links
      val subgraphLinkSpecification = subgraphSpec.linkAllocation

      // Get connectivity specification for this subgraph
      val subgraphConnectivitySpecification = subgraphSpec.connectivitySpecification

      val inBoundLinkAllocationSpecification = subgraphConnectivitySpecification.inBoundLinkAllocationSpecification
      val outBoundLinkAllocationSpecification = subgraphConnectivitySpecification.outBoundLinkAllocationSpecification

      val numberOfInbound = subgraphConnectivitySpecification.numberOfInbound
      val numberOfOutBound = subgraphConnectivitySpecification.numberOfOutBound

//      val nodeBudget = subgraphConnectivitySpecification.nodeBudget
//      val nodeBudget = subgraphConnectivitySpecification.nodeBudget
//
//      var bandWidthLinkTarget = subgraphLinkSpecification.bandwidthSpecification.heterogeneityTarget
//      val nodeBudget = 0.0

      var bandWidthLinkTarget = subgraphLinkSpecification.bandwidthSpecification.heterogeneityLinkTarget
      var bandWidthNodeTarget = subgraphLinkSpecification.bandwidthSpecification.heterogeneityNodeTarget

      // Calirate node alpha
      val alphaNodes: Double = BandwidthDistribution.calibrateAlpha(bandWidthNodeTarget, subgraphNodes.size)
      // Distribute node
      val sharesBandwidthNodes: DoubleArray = BandwidthDistribution.generateDirichlet(alphaNodes, subgraphNodes.size)

      // Calirate inbound alpha
      val alphaInBound: Double = BandwidthDistribution.calibrateAlpha(bandWidthLinkTarget, numberOfInbound)

      // Distribute inbound
      val sharesInBound: DoubleArray = BandwidthDistribution.generateDirichlet(alphaInBound, numberOfInbound)

      // Calirate outbound alpha
      val alphaOutBound: Double = BandwidthDistribution.calibrateAlpha(bandWidthLinkTarget, numberOfOutBound)

      // Distribute outbound
      val sharesOutBound: DoubleArray = BandwidthDistribution.generateDirichlet(alphaOutBound, numberOfOutBound)

      val inBoundBandwidthMapping = HashMap<String, ArrayList<Double>>()
      val outBoundBandwidthMapping = HashMap<String, ArrayList<Double>>()

      for (i in 0..<sharesBandwidthNodes.size) {
        val nodeBudget = sharesBandwidthNodes[i] * subgraphLinkSpecification.bandwidthSpecification.bandwidth
        val currentNode = subgraphNodes.elementAt(i)
        println("currentNode: " + currentNode.endpointId)
        println("bandwidth: " + nodeBudget)

        val arrInBoundBandwidth: ArrayList<Double> = ArrayList()
        val arrOutBoundBandwidth: ArrayList<Double> = ArrayList()

        for (i in 0..<sharesInBound.size) {
          val bandwidth: Double = sharesInBound[i] * nodeBudget
          arrInBoundBandwidth.add(bandwidth)
        }

        for (i in 0..<sharesOutBound.size) {
          val bandwidth: Double = sharesOutBound[i] * nodeBudget
          arrOutBoundBandwidth.add(bandwidth)
        }

        inBoundBandwidthMapping.put(currentNode.endpointId, arrInBoundBandwidth)
        outBoundBandwidthMapping.put(currentNode.endpointId, arrOutBoundBandwidth)
      }


      subgraphNodes.forEach { node ->
        networkGraph.addVertex(node)
        initialDegrees.put(node, numberOfInbound + numberOfOutBound)
      }

      // outbound connection specification
      val latencyValueProvider = createLatencyValueProvider(outBoundLinkAllocationSpecification.latencySpecification)
      val throughputValueProvider = createThroughputValueProvider(outBoundLinkAllocationSpecification.throughputSpecification)
//      val bandwidthValueProvider = createBandwidthValueProvider(outBoundLinkAllocationSpecification.bandwidthSpecification)

      val currentNodeId = subgraphNodes.elementAt(0).endpointId
      val initialOutboundBandwidth = outBoundBandwidthMapping.get(currentNodeId)?.get(0) ?: 0.0
      var bandwidthValueProvider = createBandwidthValueProviderWithValue(initialOutboundBandwidth)

      // in bound connection specification
      val inBoundLatencyValueProvider = createLatencyValueProvider(inBoundLinkAllocationSpecification.latencySpecification)
      val inBoundThroughputValueProvider = createThroughputValueProvider(inBoundLinkAllocationSpecification.throughputSpecification)
      val inBoundBandwidthValueProvider = createBandwidthValueProvider(inBoundLinkAllocationSpecification.bandwidthSpecification)

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
        // create outbound connection
        while (initialDegrees.get(currentNode) > numberOfInbound) {
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

          val currentDegrees = initialDegrees.get(currentNode)
          val indexOfEdge = (numberOfOutBound + numberOfInbound) - currentDegrees - 1

          val outBoundBandwidth = outBoundBandwidthMapping.get(currentNode.endpointId)?.get(indexOfEdge) ?: 0.0
          bandwidthValueProvider = createBandwidthValueProviderWithValue(outBoundBandwidth)

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

        // create inbound connection
        val checkingArrayList: ArrayList<P2PNode> = ArrayList()

        while (initialDegrees.get(currentNode) > 0) {
          val potentialNodes = initialDegrees.keys
            .filterNot {
              it == currentNode
                      || initialDegrees.get(it) == 0 // the neigbors reached the capacity
                      || checkingArrayList.contains(it)
            }

          if (potentialNodes.isEmpty()) {
            // Sometimes each node except for the last one has reached the maximum degree
            // The strategy here is to neglect the range parameters for the last node
            initialDegrees.decrement(currentNode)
            continue
          }

          val selectedNode = potentialNodes.random()

          val currentDegrees = initialDegrees.get(currentNode)
          val indexOfEdge = numberOfInbound - currentDegrees

          val arrInBoundBandwidth = inBoundBandwidthMapping.get(currentNode.endpointId)
          val inBoundBandwidth = arrInBoundBandwidth?.get(indexOfEdge) ?: 0.0
          bandwidthValueProvider = createBandwidthValueProviderWithValue(inBoundBandwidth)

          if ((networkGraph.containsEdge(currentNode, selectedNode) || networkGraph.containsEdge(selectedNode, currentNode)
          )) {
            val currEdges = networkGraph.getAllEdges(selectedNode, currentNode)
            val firstEdge = currEdges.first()
            val currBandwidth = firstEdge.bandwidthValueProvider.getValue()

            checkingArrayList.add(selectedNode)

            if (currBandwidth >= inBoundBandwidth) {
              networkGraph.removeEdge(firstEdge)
              networkGraph.addBidirectionalEdge(
                currentNode,
                selectedNode,
                fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
                  inBoundLatencyValueProvider,
                  inBoundThroughputValueProvider,
                  bandwidthValueProvider,
                  fromVertex,
                  toVertex
                )
              )

              initialDegrees.decrement(currentNode)
              initialDegrees.decrement(selectedNode)
            } else {
              continue
            }
          } else {
            networkGraph.addBidirectionalEdge(
              currentNode,
              selectedNode,
              fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
                inBoundLatencyValueProvider,
                inBoundThroughputValueProvider,
                bandwidthValueProvider,
                fromVertex,
                toVertex
              )
            )

            initialDegrees.decrement(currentNode)
            initialDegrees.decrement(selectedNode)
          }
//          val currEdges2 = networkGraph.getAllEdges(currentNode, selectedNode)
//          println(currEdges2)



        }
      }

//      println("====")
////      println(initialDegrees.getAll().size)
////      println(initialDegrees.getAll())
//      val edgeSet = networkGraph.edgeSet()
//      val emptyMutableMap = mutableMapOf<P2PNode, P2PNode>()
//      for (edge in edgeSet) {
////        println("edge3: " + edge.bandwidthValueProvider.getValue())
//        val sourceVertex = networkGraph.getEdgeSource(edge)
//        val targetVertex = networkGraph.getEdgeTarget(edge)
//        if (emptyMutableMap[sourceVertex] == targetVertex) {
//          println("==== edge")
//          println("sourceVertex: " + sourceVertex)
//          println("targetVertex: " + targetVertex)
//          println("edge bandwidth: " + edge.bandwidthValueProvider.getValue())
//        } else {
//          print("hihih")
//          emptyMutableMap[sourceVertex] = targetVertex
//        }
//      }
//      subgraphNodes
//        .windowed(2)
//        .forEach { (firstNode, secondNode) ->
//          println("====")
//          println("firstNode: " + firstNode)
//          println("secondNode: " + secondNode)
//          var allEdges = networkGraph.getAllEdges(firstNode, secondNode)
//          var allEdges2 = networkGraph.getAllEdges(secondNode, firstNode)
//          val edgeSet = networkGraph.edgeSet()
//          println(allEdges)
//          println(allEdges2)
//          println(edgeSet)
//          for (edge in allEdges) {
////            edge.modifyBandwidthValueProvider(inBoundBandwidthValueProvider)
//            println("edge1: " + edge.bandwidthValueProvider.getValue())
//          }
//          for (edge in allEdges2) {
////            edge.modifyBandwidthValueProvider(inBoundBandwidthValueProvider)
//            println("edge2: " + edge.bandwidthValueProvider.getValue())
//          }
//        }
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
      nodeIdToNodeTemplateIdMapping,
      nodeIdToIndexMapping
    )
  }
}