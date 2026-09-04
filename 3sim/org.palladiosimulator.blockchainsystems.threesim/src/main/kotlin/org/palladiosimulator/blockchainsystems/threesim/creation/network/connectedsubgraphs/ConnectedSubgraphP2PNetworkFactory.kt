package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.jgrapht.graph.SimpleDirectedGraph
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.network.P2PLink
import org.palladiosimulator.blockchainsystems.core.network.P2PNetworkImpl
import org.palladiosimulator.blockchainsystems.core.network.P2PNode
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult
import org.palladiosimulator.blockchainsystems.threesim.creation.DirichletUtils
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness
import org.palladiosimulator.blockchainsystems.threesim.creation.StaticLatencyValueProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.network.AbstractThreesimP2PNetworkFactory
import org.palladiosimulator.blockchainsystems.threesim.utils.addBidirectionalEdge
import java.util.Collections
import java.security.MessageDigest

/**
 * Connected-network translator for the refined TSE experiment.
 *
 * The represented NSB uses one regular peer topology.  Given a random
 * permutation sigma of N_V validating nodes, the node at position r initiates
 * connections to positions r+1,...,r+C modulo N_V.  Established connections
 * are bidirectional, so every node has C initiated and C accepted connections
 * and realized degree 2C.  The construction is feasible when 2C <= N_V-1.
 *
 * Bandwidth is translated in two stages: the model's bandwidth value stores
 * the aggregate B_total for the current configuration; H_node distributes it
 * across nodes and H_link distributes each node budget across all 2C adjacent
 * connection endpoints.  The effective bandwidth of an undirected connection
 * is min(B_ij, B_ji) and is used in both directions.
 */
class ConnectedSubgraphP2PNetworkFactory(
  private val topology: ConnectedSubgraphsNetworkTopology,
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : AbstractThreesimP2PNetworkFactory() {

  override fun createP2PNetwork(): P2PNetworkCreationResult {
    require(topology.subgraphs.size == 1) {
      "The refined NSB experiment requires exactly one connected subgraph; found ${topology.subgraphs.size}."
    }
    require(topology.subgraphLinks.isEmpty()) {
      "The refined NSB experiment does not use inter-subgraph proxy links."
    }

    val subgraph = topology.subgraphs.single()
    val connectivity = subgraph.connectivitySpecification
    val cInbound = connectivity.numberOfInbound
    val cOutbound = connectivity.numberOfOutBound
    require(cInbound == cOutbound) {
      "Refined connection count C requires identical initiated/accepted counts; inbound=$cInbound outbound=$cOutbound."
    }
    val c = cOutbound
    require(c >= 1) { "Connection count C must be >= 1." }

    // Preserve node-template allocation information while assigning deterministic
    // node identifiers.  Deterministic IDs are important because network/event
    // seed prefixes should regenerate exactly the same structural realization.
    val nodes = mutableListOf<P2PNode>()
    val nodeIdToNodeTemplateIdMapping = HashMap<String, String>()
    val nodeIdToIndexMapping = HashMap<String, Int>()
    var globalIndex = 0
    subgraph.nodeTemplates.forEach { template ->
      repeat(template.numberOfNodeOccurences) {
        val nodeId = "validator-${globalIndex.toString().padStart(5, '0')}"
        val node = P2PNode(nodeId)
        nodes.add(node)
        nodeIdToNodeTemplateIdMapping[nodeId] = template.id
        nodeIdToIndexMapping[nodeId] = globalIndex
        globalIndex++
      }
    }

    val nv = nodes.size
    require(nv >= 2) { "At least two validating nodes are required, got $nv." }
    require(2 * c <= nv - 1) {
      "Infeasible refined topology: 2C=${2 * c} > N_V-1=${nv - 1}."
    }

    // Draw sigma with a dedicated structural stream.
    val permutation = nodes.indices.toMutableList()
    Collections.shuffle(permutation, java.util.Random(
      RefinedExperimentRandomness.deriveSeed(randomness.networkSeed, "topology-permutation")
    ))

    // Create the undirected connection set implied by the circulant construction.
    // Normalized index pairs are used only for duplicate/reciprocal auditing; the
    // directed initiator relation is implicit in (r, offset).
    val undirectedPairs = LinkedHashSet<Pair<Int, Int>>()
    val initiatedCounts = IntArray(nv)
    val acceptedCounts = IntArray(nv)
    for (r in 0 until nv) {
      val u = permutation[r]
      for (offset in 1..c) {
        val v = permutation[(r + offset) % nv]
        require(u != v) { "Topology construction produced a self connection." }
        initiatedCounts[u]++
        acceptedCounts[v]++
        val pair = if (u < v) Pair(u, v) else Pair(v, u)
        require(undirectedPairs.add(pair)) {
          "Topology construction produced a duplicate or reciprocally initiated connection: $pair"
        }
      }
    }
    require(undirectedPairs.size == nv * c) {
      "Expected N_V*C=${nv * c} undirected connections, got ${undirectedPairs.size}."
    }
    val initiatedAcceptedEachEqualC =
      initiatedCounts.all { it == c } && acceptedCounts.all { it == c }
    require(initiatedAcceptedEachEqualC) {
      "Every validator must initiate and accept exactly C=$c connections."
    }

    val neighborIndices = Array(nv) { mutableListOf<Int>() }
    undirectedPairs.forEach { (u, v) ->
      neighborIndices[u].add(v)
      neighborIndices[v].add(u)
    }
    require(neighborIndices.all { it.size == 2 * c }) {
      "Every validator must have realized degree 2C=${2 * c}."
    }

    // Explicit connectivity audit in addition to the construction argument.
    val visited = BooleanArray(nv)
    val queue = java.util.ArrayDeque<Int>()
    visited[0] = true
    queue.add(0)
    while (queue.isNotEmpty()) {
      val u = queue.removeFirst()
      for (v in neighborIndices[u]) {
        if (!visited[v]) {
          visited[v] = true
          queue.add(v)
        }
      }
    }
    val topologyConnected = visited.all { it }
    require(topologyConnected) { "Refined topology is unexpectedly disconnected." }

    val bandwidthSpec = subgraph.linkAllocation.bandwidthSpecification
    val bTotal = bandwidthSpec.bandwidth
    require(bTotal > 0.0 && bTotal.isFinite()) {
      "Aggregate bandwidth budget B_total must be finite and > 0, got $bTotal Mbps."
    }
    val hNode = bandwidthSpec.heterogeneityNodeTarget
    val hLink = bandwidthSpec.heterogeneityLinkTarget

    /*
     * BANDWIDTH REGULARIZATION
     * ------------------------
     * The study budget is B_total = 2 * B_endpoint * N_V * C. Applying the
     * uniform fraction eta_B once at node level and once at link level gives
     * every directed endpoint a guaranteed minimum of eta_B^2 * B_endpoint.
     *
     * This is an analytical property of the allocation distribution, not a
     * post-sampling clamp. Therefore total bandwidth and requested E[H] remain
     * intact.
     */
    val bandwidthUniformFloorFraction = DirichletUtils.BANDWIDTH_UNIFORM_FLOOR_FRACTION
    val baselineEndpointBandwidthMbps = bTotal / (2.0 * nv.toDouble() * c.toDouble())
    val guaranteedMinimumEndpointBandwidthMbps =
      bandwidthUniformFloorFraction * bandwidthUniformFloorFraction * baselineEndpointBandwidthMbps

    val nodeShares = DirichletUtils.drawRegularizedBandwidthShares(
      nv,
      hNode,
      randomness.network("node-bandwidth-allocation")
    )
    val nodeBudgets = nodes.indices.associate { i -> nodes[i].endpointId to nodeShares[i] * bTotal }
    require(kotlin.math.abs(nodeBudgets.values.sum() - bTotal) <= 1e-9 * maxOf(1.0, bTotal)) {
      "Node bandwidth allocations do not sum to B_total."
    }

    // Per-node endpoint budgets B_ij.  H_link is applied over all 2C adjacent
    // validators, not separately over inbound/outbound halves.
    val directedEndpointBandwidth = HashMap<Pair<Int, Int>, Double>()
    val realizedLinkH = HashMap<String, Double>()
    for (i in 0 until nv) {
      val neighbors = neighborIndices[i].sorted()
      val linkShares = DirichletUtils.drawRegularizedBandwidthShares(
        2 * c,
        hLink,
        randomness.networkForNode("link-bandwidth-allocation", nodes[i].endpointId)
      )
      realizedLinkH[nodes[i].endpointId] = DirichletUtils.normalizedConcentration(linkShares)
      val nodeBudget = nodeShares[i] * bTotal
      var endpointSum = 0.0
      for (j in neighbors.indices) {
        val value = linkShares[j] * nodeBudget
        directedEndpointBandwidth[Pair(i, neighbors[j])] = value
        endpointSum += value
      }
      require(kotlin.math.abs(endpointSum - nodeBudget) <= 1e-9 * maxOf(1.0, nodeBudget)) {
        "Endpoint bandwidth allocations for ${nodes[i].endpointId} do not sum to its node budget."
      }
    }

    val networkGraph = SimpleDirectedGraph<P2PNode, P2PLink>(P2PLink::class.java)
    nodes.forEach(networkGraph::addVertex)

    // L_ij is a structural/network-instance value in the refined method.  Draw
    // one latency from the configured distribution per undirected connection
    // using the network seed, then keep it fixed in both directions and across
    // all R_E event replications that reuse this R_S network instance.  Legacy
    // throughput variation is disabled because the refined propagation equation
    // uses only B_ij^eff and L_ij.
    val connectionLatenciesMs = linkedMapOf<String, Long>()

    /*
     * DIAGNOSTIC ONLY:
     * These collections are populated only while building the network and are
     * discarded before simulation execution. They do not alter allocations,
     * random-number consumption, or network behavior.
     *
     * Enable printing with:
     *   -Dthreesim.networkDiagnostics=true
     */
    val effectiveConnectionBandwidthsMbps = ArrayList<Double>(undirectedPairs.size)

    undirectedPairs.forEach { (u, v) ->
      val buv = directedEndpointBandwidth[Pair(u, v)]
        ?: error("Missing endpoint bandwidth B_${u}${v}")
      val bvu = directedEndpointBandwidth[Pair(v, u)]
        ?: error("Missing endpoint bandwidth B_${v}${u}")
      val effective = minOf(buv, bvu)
      effectiveConnectionBandwidthsMbps.add(effective)

      /*
       * Fail loudly if a future code change breaks the analytical floor. Never
       * silently clamp here: clamping would alter both the bandwidth budget and
       * the target concentration after sampling.
       */
      val floorTolerance = maxOf(1e-12, guaranteedMinimumEndpointBandwidthMbps * 1e-12)
      require(effective + floorTolerance >= guaranteedMinimumEndpointBandwidthMbps) {
        "Effective connection bandwidth $effective Mbps is below the guaranteed " +
          "regularized floor $guaranteedMinimumEndpointBandwidthMbps Mbps."
      }

      val first = nodes[u]
      val second = nodes[v]
      val pairLabel = "${first.endpointId}|${second.endpointId}"
      val sampledLatencyMs = createNetworkFixedLatencyValueProvider(
        subgraph.linkAllocation.latencySpecification,
        randomness.network("connection-latency|$pairLabel")
      ).getValue() ?: error("Connection latency provider returned null for $pairLabel")
      connectionLatenciesMs[pairLabel] = sampledLatencyMs

      networkGraph.addBidirectionalEdge(
        first,
        second,
        fun(fromVertex: P2PNode, toVertex: P2PNode): P2PLink {
          return P2PLink(
            StaticLatencyValueProvider(sampledLatencyMs),
            createAlwaysAvailableThroughputValueProvider(),
            createBandwidthValueProviderWithValue(effective),
            fromVertex,
            toVertex
          )
        }
      )
    }

    if (java.lang.Boolean.getBoolean("threesim.networkDiagnostics")) {
      fun quantile(values: List<Double>, p: Double): Double {
        if (values.isEmpty()) return Double.NaN
        val sorted = values.sorted()
        val index = ((sorted.size - 1) * p).toInt().coerceIn(0, sorted.lastIndex)
        return sorted[index]
      }

      fun format(value: Double): String =
        if (value.isFinite()) "%.12g".format(java.util.Locale.ROOT, value) else value.toString()

      val nodeValues = nodeBudgets.values.toList()
      val linkValues = effectiveConnectionBandwidthsMbps.toList()

      /*
       * Report only derived summaries. No state is modified and no additional
       * random values are drawn. This is therefore safe for reproducibility.
       */
      System.err.println(
        "[3SIM-network-diagnostic] " +
          "nodes=$nv connections=${undirectedPairs.size} C=$c " +
          "eta_B=${format(bandwidthUniformFloorFraction)} " +
          "baselineEndpointMbps=${format(baselineEndpointBandwidthMbps)} " +
          "guaranteedEndpointFloorMbps=${format(guaranteedMinimumEndpointBandwidthMbps)} " +
          "nodeBandwidthMbps[min=${format(nodeValues.minOrNull() ?: Double.NaN)}," +
          "p01=${format(quantile(nodeValues, 0.01))}," +
          "p05=${format(quantile(nodeValues, 0.05))}," +
          "median=${format(quantile(nodeValues, 0.50))}," +
          "p95=${format(quantile(nodeValues, 0.95))}," +
          "max=${format(nodeValues.maxOrNull() ?: Double.NaN)}] " +
          "effectiveLinkBandwidthMbps[min=${format(linkValues.minOrNull() ?: Double.NaN)}," +
          "p01=${format(quantile(linkValues, 0.01))}," +
          "p05=${format(quantile(linkValues, 0.05))}," +
          "median=${format(quantile(linkValues, 0.50))}," +
          "p95=${format(quantile(linkValues, 0.95))}," +
          "max=${format(linkValues.maxOrNull() ?: Double.NaN)}]"
      )

      /*
       * Representative transmission delays under the current refined equation:
       *   delay = latency + size*8 / bandwidth.
       *
       * We report serialization only here because actual L_ij is already printed
       * separately by the model/result audit and is tiny compared with pathological
       * near-zero bandwidth if that is the source of the backlog.
       */
      val minBandwidth = linkValues.minOrNull() ?: Double.NaN
      fun serializationDelayMs(bytes: Long): Double =
        if (!minBandwidth.isFinite() || minBandwidth <= 0.0) Double.POSITIVE_INFINITY
        else (bytes.toDouble() * 8.0 * 1000.0) / (minBandwidth * 1_000_000.0)

      System.err.println(
        "[3SIM-network-diagnostic] slowestLinkSerializationMs " +
          "inv44B=${format(serializationDelayMs(44))} " +
          "tx500B=${format(serializationDelayMs(500))} " +
          "block1_414MB=${format(serializationDelayMs(1_414_000))}"
      )
    }

    // Strong structural audit: each directed graph must contain two directed
    // edges per undirected connection and exactly 2C neighbors per node.
    require(networkGraph.edgeSet().size == 2 * nv * c) {
      "Expected ${2 * nv * c} directed edges, got ${networkGraph.edgeSet().size}."
    }
    nodes.forEach { node ->
      val outDegree = networkGraph.outDegreeOf(node)
      val inDegree = networkGraph.inDegreeOf(node)
      require(outDegree == 2 * c && inDegree == 2 * c) {
        "Bidirectional transport graph degree mismatch for ${node.endpointId}: in=$inDegree out=$outDegree expected=${2 * c}."
      }
    }

    val topologyFingerprint = sha256Hex(
      undirectedPairs
        .map { (u, v) -> listOf(nodes[u].endpointId, nodes[v].endpointId).sorted().joinToString("--") }
        .sorted()
        .joinToString("|")
    )
    val bandwidthAllocationFingerprint = sha256Hex(
      buildList {
        nodeBudgets.toSortedMap().forEach { (nodeId, value) ->
          add("node:$nodeId=${java.lang.Double.toHexString(value)}")
        }
        directedEndpointBandwidth.entries
          .sortedWith(compareBy({ it.key.first }, { it.key.second }))
          .forEach { (edge, value) ->
            add("endpoint:${nodes[edge.first].endpointId}->${nodes[edge.second].endpointId}=${java.lang.Double.toHexString(value)}")
          }
      }.joinToString("|")
    )
    val latencyAllocationFingerprint = sha256Hex(
      connectionLatenciesMs.toSortedMap().entries.joinToString("|") { (edge, latency) -> "$edge=$latency" }
    )

    val networkImpl = P2PNetworkImpl.create(networkGraph, randomness.network("p2p-network-id"))
    networkGraph.vertexSet().forEach { it.initNetwork(networkImpl) }

    return ConnectedSubgraphNetworkCreationResult(
      createdNetwork = networkImpl,
      nodeIdToNodeTemplateIdMapping = nodeIdToNodeTemplateIdMapping,
      nodeIdToIndexMapping = nodeIdToIndexMapping,
      connectionCount = c,
      systemBandwidthBudgetMbps = bTotal,
      nodeBandwidthsMbps = nodeBudgets,
      bandwidthUniformFloorFraction = bandwidthUniformFloorFraction,
      guaranteedMinimumEndpointBandwidthMbps = guaranteedMinimumEndpointBandwidthMbps,
      minimumRealizedEffectiveConnectionBandwidthMbps =
        effectiveConnectionBandwidthsMbps.minOrNull() ?: Double.NaN,
      realizedNodeBandwidthH = DirichletUtils.normalizedConcentration(nodeShares),
      realizedLinkBandwidthHPerNode = realizedLinkH,
      connectionLatenciesMs = connectionLatenciesMs,
      topologyFingerprint = topologyFingerprint,
      bandwidthAllocationFingerprint = bandwidthAllocationFingerprint,
      latencyAllocationFingerprint = latencyAllocationFingerprint,
      undirectedConnectionCount = undirectedPairs.size,
      topologyConnected = topologyConnected,
      noSelfConnections = true,
      noDuplicateOrReciprocalInitiations = true,
      initiatedAcceptedEachEqualC = initiatedAcceptedEachEqualC
    )
  }

  private fun sha256Hex(value: String): String =
    MessageDigest.getInstance("SHA-256")
      .digest(value.toByteArray(Charsets.UTF_8))
      .joinToString("") { byte -> "%02x".format(byte.toInt() and 0xff) }

}
