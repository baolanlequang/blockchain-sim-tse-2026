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
    // Unique, global per-node index (0 until total node count). This MUST be a running node
    // index, not the per-template occurrence index: the resource-power Dirichlet generates one
    // weight per node and is looked up by this value (see ConnectedSubgraphNetworkResourcePower-
    // Calculator). Using the occurrence index (0..numberOfNodeOccurences-1) collapsed the whole
    // hashrate distribution onto only a handful of distinct weights.
    val nodeIdToIndexMapping = HashMap<String, Int>()
    var globalNodeIndex = 0

    // Store attacker information
    val attackerNodeIds = ArrayList<String>()

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
          nodeIdToIndexMapping.put(p2pNodeId, globalNodeIndex)
          globalNodeIndex++
        }
      }
    }

    val networkGraph = SimpleDirectedGraph<P2PNode, P2PLink>(P2PLink::class.java)

    // Create each subgraph and add it to the networkGraph
    subgraphIdToSubgraphNodesMapping.entries.forEach { (subgraphId, subgraphNodes) ->
      val subgraphSpec = subGraphIdToLinkSpecificationMapping.get(subgraphId) ?: return@forEach

      // Get link allocation for subgraph internal links
      val subgraphLinkSpecification = subgraphSpec.linkAllocation

      // Get connectivity specification for this subgraph
      val subgraphConnectivitySpecification = subgraphSpec.connectivitySpecification

      val inBoundLinkAllocationSpecification = subgraphConnectivitySpecification.inBoundLinkAllocationSpecification
      val outBoundLinkAllocationSpecification = subgraphConnectivitySpecification.outBoundLinkAllocationSpecification

      val numberOfInbound = subgraphConnectivitySpecification.numberOfInbound
      val numberOfOutBound = subgraphConnectivitySpecification.numberOfOutBound

      var bandWidthLinkTarget = subgraphLinkSpecification.bandwidthSpecification.heterogeneityLinkTarget
      var bandWidthNodeTarget = subgraphLinkSpecification.bandwidthSpecification.heterogeneityNodeTarget

      // Calirate node alpha
      val alphaNodes: Double = BandwidthDistribution.calibrateAlpha(bandWidthNodeTarget, subgraphNodes.size)
      // Distribute node
      val sharesBandwidthNodes: DoubleArray = BandwidthDistribution.generateDirichlet(alphaNodes, subgraphNodes.size)

      // Calirate inbound alpha
      val alphaInBound: Double = BandwidthDistribution.calibrateAlpha(bandWidthLinkTarget, numberOfInbound)

      // Calirate outbound alpha
      val alphaOutBound: Double = BandwidthDistribution.calibrateAlpha(bandWidthLinkTarget, numberOfOutBound)

      val inBoundBandwidthMapping = HashMap<String, ArrayList<Double>>()
      val outBoundBandwidthMapping = HashMap<String, ArrayList<Double>>()

      for (i in 0..<sharesBandwidthNodes.size) {
        val nodeBudget = sharesBandwidthNodes[i] * subgraphLinkSpecification.bandwidthSpecification.bandwidth
        val currentNode = subgraphNodes.elementAt(i)

        // Each validating node independently samples its own link-split allocation (paper Eq.:
        // (l_i1,...,l_i,d_i) ~ Dir(alpha_link,...,alpha_link) per node i, sec:operationalization-
        // heterogeneous-resource) -- alphaInBound/alphaOutBound are subgraph-level constants (the
        // calibration target is the same for every node), but the SAMPLING must be drawn fresh
        // per node here, inside this loop. Previously sharesInBound/sharesOutBound were drawn
        // ONCE for the whole subgraph (outside this loop) and the same DoubleArray reference was
        // read by every node, so all nodes in a subgraph shared identical relative link-split
        // proportions -- only the absolute values differed, via each node's own nodeBudget
        // scaling. Sampling here gives every node its own independent draw.
        val sharesInBound: DoubleArray = BandwidthDistribution.generateDirichlet(alphaInBound, numberOfInbound)
        val sharesOutBound: DoubleArray = BandwidthDistribution.generateDirichlet(alphaOutBound, numberOfOutBound)

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

      // Effective bandwidth of a link (i,j) is B_ij^eff = min(B_ij, B_ji) (paper
      // sec:operationalization-heterogeneous-resource): each endpoint independently allocates
      // its own bandwidth budget across its own links, and the slower endpoint governs.
      //
      // Every touch of a node's own outbound/inbound array -- whether the node is initiating the
      // edge (spanning tree, its own enhance-phase turn) or is the *other* endpoint of an edge
      // someone else is creating (the "reverse" contribution) -- draws from ONE shared, strictly
      // sequential per-node-per-direction counter. This is the single source of truth for "which
      // slot is next" for that node/direction: there is no second, independently-computed index
      // formula anywhere else that could race or misalign with it. A node's array has exactly
      // numberOfOutBound/numberOfInbound entries; since every edge a node ends up with consumes
      // exactly one slot in the corresponding direction, the counter lands on each index exactly
      // once over the lifetime of the subgraph (module the pre-existing, out-of-scope case where
      // a node ends up with fewer edges than its target degree because candidates ran out -- see
      // "neglect the range parameters for the last node" below). The modulo is only a defensive
      // guard against that shortfall case, not the normal path.
      val nextOutboundSlotIndex = HashMap<String, Int>()
      val nextInboundSlotIndex = HashMap<String, Int>()

      // Read-only: what the next slot for this node/direction WOULD be, without consuming it.
      // Needed where a candidate edge may still be rejected (inbound-enhance's accept/reject
      // branch) -- the decision needs the value, but a rejected candidate must not advance the
      // counter, otherwise a slot is burned for an edge that never gets created.
      fun peekOutboundBandwidth(nodeId: String): Double {
        val arr = outBoundBandwidthMapping.get(nodeId)
        if (arr == null || arr.isEmpty()) return 0.0
        return arr[(nextOutboundSlotIndex.get(nodeId) ?: 0) % arr.size]
      }

      fun peekInboundBandwidth(nodeId: String): Double {
        val arr = inBoundBandwidthMapping.get(nodeId)
        if (arr == null || arr.isEmpty()) return 0.0
        return arr[(nextInboundSlotIndex.get(nodeId) ?: 0) % arr.size]
      }

      // Commit: advance the counter past the slot that was just (or is about to be) used for a
      // real edge. Call only once the edge is actually being created.
      fun advanceOutboundSlot(nodeId: String) {
        val arr = outBoundBandwidthMapping.get(nodeId)
        if (arr == null || arr.isEmpty()) return
        nextOutboundSlotIndex.put(nodeId, ((nextOutboundSlotIndex.get(nodeId) ?: 0) % arr.size) + 1)
      }

      fun advanceInboundSlot(nodeId: String) {
        val arr = inBoundBandwidthMapping.get(nodeId)
        if (arr == null || arr.isEmpty()) return
        nextInboundSlotIndex.put(nodeId, ((nextInboundSlotIndex.get(nodeId) ?: 0) % arr.size) + 1)
      }

      // Peek-and-commit in one step, for call sites where the edge is unconditionally created
      // (spanning tree, outbound-enhance -- neither has a reject path).
      fun nextOutboundBandwidth(nodeId: String): Double {
        val value = peekOutboundBandwidth(nodeId)
        advanceOutboundSlot(nodeId)
        return value
      }

      fun nextInboundBandwidth(nodeId: String): Double {
        val value = peekInboundBandwidth(nodeId)
        advanceInboundSlot(nodeId)
        return value
      }

      // ============================================================================
      // KNOWN, DELIBERATE DEVIATION -- numberOfOutBound == 1 only. Do not remove or widen
      // this without re-running the verification harness described in the review history.
      //
      // The spanning tree below (windowed(2)) unconditionally gives every internal node two
      // outbound-type touches, regardless of that node's own numberOfOutBound. For
      // numberOfOutBound >= 2 this always fits inside the node's own outBoundBandwidthMapping
      // array (2 <= numberOfOutBound), so nextOutboundBandwidth() above already gives each
      // touch its own distinct, independently-drawn slot -- verified exact (no collision, no
      // skip) for every numberOfOutBound >= 2 configuration tested. When numberOfOutBound == 1,
      // a node's array has exactly one slot: there is no second, independent value to assign to
      // its second spanning-tree touch, full stop -- this is a structural property of the
      // unconditional spanning tree, not of reverse-target selection (which is already
      // correctly gated per-direction elsewhere in this file). Rather than relying on
      // nextOutboundBandwidth()'s generic modulo wraparound to *coincidentally* produce the
      // same value on the second touch, this function makes the reuse explicit: it tracks,
      // per node, whether the spanning tree has already touched it once, and on the second
      // touch of a numberOfOutBound == 1 node, re-peeks (does NOT re-advance) that node's one
      // and only slot instead of drawing from it as if it were a fresh index.
      // ============================================================================
      val spanningTreeTouchedNodes = HashSet<String>()

      fun spanningTreeOutboundBandwidth(nodeId: String): Double {
        val isSecondSpanningTreeTouch = !spanningTreeTouchedNodes.add(nodeId)
        return if (numberOfOutBound == 1 && isSecondSpanningTreeTouch) {
          // AUDIT: numberOfOutBound == 1 fallback -- reuse this node's only outbound slot for
          // its second spanning-tree edge instead of a nonexistent second independent value.
          peekOutboundBandwidth(nodeId)
        } else {
          nextOutboundBandwidth(nodeId)
        }
      }

      // Outbound and inbound are separate, independently-configured budgets (distinct CSV
      // columns / metamodel attributes), not a combined pool to be freely split. Each node's
      // remaining capacity is tracked separately per direction, and candidate selection for
      // "who can be a reverse target" is gated on the *specific* direction being consumed --
      // this is what guarantees a node's outbound-array consumption never exceeds
      // numberOfOutBound and its inbound-array consumption never exceeds numberOfInbound,
      // independently, regardless of how many times it gets picked by other nodes.
      val remainingOutbound = CounterMap<P2PNode>()
      val remainingInbound = CounterMap<P2PNode>()

      subgraphNodes.forEach { node ->
        networkGraph.addVertex(node)
        remainingOutbound.put(node, numberOfOutBound)
        remainingInbound.put(node, numberOfInbound)
      }

      // outbound connection specification
      val latencyValueProvider = createLatencyValueProvider(outBoundLinkAllocationSpecification.latencySpecification)
      val throughputValueProvider = createThroughputValueProvider(outBoundLinkAllocationSpecification.throughputSpecification)

      var bandwidthValueProvider = createBandwidthValueProviderWithValue(0.0)

      // in bound connection specification
      val inBoundLatencyValueProvider = createLatencyValueProvider(inBoundLinkAllocationSpecification.latencySpecification)
      val inBoundThroughputValueProvider = createThroughputValueProvider(inBoundLinkAllocationSpecification.throughputSpecification)
      val inBoundBandwidthValueProvider = createBandwidthValueProvider(inBoundLinkAllocationSpecification.bandwidthSpecification)

      // Create a bidirectional spanning tree in subgraph
      subgraphNodes
        .windowed(2)
        .forEach { (firstNode, secondNode) ->
          val forwardBandwidth = spanningTreeOutboundBandwidth(firstNode.endpointId)
          val reverseBandwidth = spanningTreeOutboundBandwidth(secondNode.endpointId)
          val edgeBandwidthValueProvider = createBandwidthValueProviderWithValue(minOf(forwardBandwidth, reverseBandwidth))

          networkGraph.addBidirectionalEdge(
            firstNode,
            secondNode,
            fun(fromVertex: P2PNode, toVertex: P2PNode) = P2PLink(
              latencyValueProvider,
              throughputValueProvider,
              edgeBandwidthValueProvider,
              fromVertex,
              toVertex
            )
          )

          // Spanning-tree edges are outbound-type for both endpoints (they draw from
          // nextOutboundBandwidth above), so they consume outbound capacity for both.
          remainingOutbound.decrement(firstNode)
          remainingOutbound.decrement(secondNode)
        }

      // Enhance with random bidirectional edges.
      //
      // Performance: the original code rebuilt a filtered candidate list over *all* still-
      // unsaturated nodes on every single edge addition (O(N) per edge), giving O(N^2 * degree)
      // per network. With N up to ~2000 and inbound degree up to ~250, this construction
      // dominated the whole simulation runtime. We instead build each node's candidate list
      // once and maintain it incrementally with O(1) swap-removes, giving O(N) per node.
      // Selection remains a uniform draw over the exact same candidate set at each step, so the
      // generated network distribution is unchanged (the generator is unseeded, so only the
      // distribution -- not a fixed sequence -- is observable). CounterMap.decrement removes a
      // node once its remaining degree reaches 0, so within a node's loop only that node and the
      // nodes it connects to ever change degree, which is what makes incremental upkeep exact.
      // Every node gets a turn; a turn whose direction is already fully consumed (e.g. by the
      // spanning tree alone) just runs zero iterations of that direction's while-loop below.
      val nodesToEnhance = subgraphNodes.toTypedArray()

      nodesToEnhance.forEach { currentNode ->
        // Outbound candidates: nodes that still have their OWN remaining outbound capacity
        // (this is what bounds nextOutboundBandwidth(selectedNode) below to at most
        // numberOfOutBound touches for that node, independent of its inbound capacity), with no
        // existing edge to/from currentNode. A node only leaves this set when currentNode
        // connects to it, so swap-removing the selected node keeps the list exactly in sync with
        // the original per-iteration filter.
        val outboundCandidates = subgraphNodes.filterNot {
          it == currentNode
            || remainingOutbound.get(it) <= 0
            || networkGraph.containsEdge(it, currentNode)
            || networkGraph.containsEdge(currentNode, it)
        }.toMutableList()

        while (remainingOutbound.get(currentNode) > 0) {
          if (outboundCandidates.isEmpty()) {
            // Sometimes each node except for the last one has reached the maximum degree
            // The strategy here is to neglect the range parameters for the last node
            remainingOutbound.decrement(currentNode)
            continue
          }

          val selectedIndex = outboundCandidates.indices.random()
          val selectedNode = outboundCandidates[selectedIndex]
          // selectedNode now shares an edge with currentNode -> drop it from the candidate pool
          outboundCandidates[selectedIndex] = outboundCandidates[outboundCandidates.size - 1]
          outboundCandidates.removeAt(outboundCandidates.size - 1)

          // This edge is created unconditionally (no accept/reject here), so both endpoints'
          // slots can be consumed straight away via the single shared counter per node.
          val outBoundBandwidth = nextOutboundBandwidth(currentNode.endpointId)
          val reverseOutBoundBandwidth = nextOutboundBandwidth(selectedNode.endpointId)
          bandwidthValueProvider = createBandwidthValueProviderWithValue(minOf(outBoundBandwidth, reverseOutBoundBandwidth))

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

          remainingOutbound.decrement(currentNode)
          remainingOutbound.decrement(selectedNode)
        }

        // Inbound candidates: nodes that still have their OWN remaining inbound capacity
        // (excluding self) that have not yet been "checked". Unlike the outbound case a node may
        // be re-selected after a plain new edge (the re-selection then enters the
        // bandwidth-comparison branch), so a node only leaves the pool when it is checked or its
        // remaining inbound capacity reaches 0. This mirrors the original filter
        // `it != self && remainingInbound(it) != 0 && it !in checkingArrayList`.
        val inboundCandidates = subgraphNodes.filterNot { it == currentNode || remainingInbound.get(it) <= 0 }.toMutableList()

        while (remainingInbound.get(currentNode) > 0) {
          if (inboundCandidates.isEmpty()) {
            // Sometimes each node except for the last one has reached the maximum degree
            // The strategy here is to neglect the range parameters for the last node
            remainingInbound.decrement(currentNode)
            continue
          }

          val selectedIndex = inboundCandidates.indices.random()
          val selectedNode = inboundCandidates[selectedIndex]

          // Read-only: needed for the accept/reject decision below. Does NOT consume the slot --
          // a rejected candidate (the `continue` path) must leave currentNode's counter
          // untouched, since no edge ends up being created for this attempt.
          val inBoundBandwidth = peekInboundBandwidth(currentNode.endpointId)

          if ((networkGraph.containsEdge(currentNode, selectedNode) || networkGraph.containsEdge(selectedNode, currentNode)
          )) {
            val currEdges = networkGraph.getAllEdges(selectedNode, currentNode)
            val firstEdge = currEdges.first()
            val currBandwidth = firstEdge.bandwidthValueProvider.getValue()

            // selectedNode is now "checked" -> exclude it from future draws for currentNode
            inboundCandidates[selectedIndex] = inboundCandidates[inboundCandidates.size - 1]
            inboundCandidates.removeAt(inboundCandidates.size - 1)

            if (currBandwidth >= inBoundBandwidth) {
              // Accepted: the edge is really being (re)created now, so commit currentNode's
              // peeked slot and draw+consume selectedNode's own reverse contribution.
              advanceInboundSlot(currentNode.endpointId)
              val reverseInBoundBandwidth = nextInboundBandwidth(selectedNode.endpointId)
              bandwidthValueProvider = createBandwidthValueProviderWithValue(minOf(inBoundBandwidth, reverseInBoundBandwidth))

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

              remainingInbound.decrement(currentNode)
              remainingInbound.decrement(selectedNode)
            } else {
              // Rejected: no edge created, no slot consumed for either endpoint.
              continue
            }
          } else {
            // No existing edge -> always accepted, so consume both endpoints' slots now.
            advanceInboundSlot(currentNode.endpointId)
            val reverseInBoundBandwidth = nextInboundBandwidth(selectedNode.endpointId)
            bandwidthValueProvider = createBandwidthValueProviderWithValue(minOf(inBoundBandwidth, reverseInBoundBandwidth))

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

            remainingInbound.decrement(currentNode)
            remainingInbound.decrement(selectedNode)

            // selectedNode keeps an edge now but stays selectable unless it just saturated
            if (remainingInbound.get(selectedNode) == 0) {
              inboundCandidates[selectedIndex] = inboundCandidates[inboundCandidates.size - 1]
              inboundCandidates.removeAt(inboundCandidates.size - 1)
            }
          }

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
      nodeIdToNodeTemplateIdMapping,
      nodeIdToIndexMapping
    )
  }

}