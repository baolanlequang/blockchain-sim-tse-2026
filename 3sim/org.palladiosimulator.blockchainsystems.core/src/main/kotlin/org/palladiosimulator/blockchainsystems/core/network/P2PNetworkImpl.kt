package org.palladiosimulator.blockchainsystems.core.network

import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.palladiosimulator.blockchainsystems.core.common.BlockchainSimulationObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.NodeP2PNetworkInterface
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint
import java.util.*
import java.util.function.Consumer

/**
 * Implementation of a P2P network that uses a graph structure to represent nodes and links.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class P2PNetworkImpl internal constructor(
  id: String,
  private val networkGraph: Graph<P2PNode, P2PLink>,
  name: String
) : BlockchainSimulationObject(id, name), P2PNetwork {
  override fun dispatchEvent(event: Event) {
  }

  override fun multicast(sendingNetworkInterface: NodeP2PNetworkInterface, content: Message) {
    networkGraph
      .outgoingEdgesOf(sendingNetworkInterface as P2PNode)
      .forEach { it.send(content) }
  }

  public override fun onInitialize() {
    networkGraph.vertexSet().forEach(Consumer { it.initialize(simulationContext) })
    networkGraph.edgeSet().forEach(Consumer { it.initialize(simulationContext) })
  }

  public override fun onCleanup() {
    networkGraph.edgeSet().forEach(Consumer { it.cleanup() })
    networkGraph.vertexSet().forEach(Consumer { it.cleanup() })
  }

  override val nodes: MutableSet<NodeP2PNetworkInterface> = Collections.unmodifiableSet(networkGraph.vertexSet())

  override fun send(
    sendingNetworkInterface: NodeP2PNetworkInterface,
    receivingNetworkInterface: NodeP2PNetworkInterface,
    content: Message
  ) {
    networkGraph
      .getEdge(sendingNetworkInterface as P2PNode, receivingNetworkInterface as P2PNode)
      .send(content)
  }

  override fun getNeighbors(networkInterface: NodeP2PNetworkInterface): MutableSet<P2PNetworkEndpoint> {
    return networkGraph
      .outgoingEdgesOf(networkInterface as P2PNode)
      .map { it.toNode as P2PNetworkEndpoint }
      .toMutableSet()
  }

  fun getNodeTotalOutgoingBandwidth(nodeId: String): Double {
    val node = networkGraph.vertexSet().find { it.endpointId == nodeId } ?: return 0.0
    return networkGraph.outgoingEdgesOf(node)
      .sumOf { it.bandwidthValueProvider.getValue() ?: 0.0 }
  }

  fun getNodeTotalIncomingBandwidth(nodeId: String): Double {
    val node = networkGraph.vertexSet().find { it.endpointId == nodeId } ?: return 0.0
    return networkGraph.incomingEdgesOf(node)
      .sumOf { it.bandwidthValueProvider.getValue() ?: 0.0 }
  }

  /**
   * Computes the total outgoing and incoming bandwidth for every node in a single
   * pass over the edge set. Returns a pair of (outgoing, incoming) maps keyed by
   * node id. This avoids the O(N²) cost of calling the per-node lookups (which each
   * scan the full vertex set) once for every node.
   */
  fun computeTotalBandwidths(): Pair<Map<String, Double>, Map<String, Double>> {
    val outgoing = HashMap<String, Double>()
    val incoming = HashMap<String, Double>()
    networkGraph.edgeSet().forEach { edge ->
      val bandwidth = edge.bandwidthValueProvider.getValue() ?: 0.0
      outgoing.merge(edge.fromNode.endpointId, bandwidth, Double::plus)
      incoming.merge(edge.toNode.endpointId, bandwidth, Double::plus)
    }
    return outgoing to incoming
  }

  companion object {
    fun create(networkGraph: Graph<P2PNode, P2PLink>): P2PNetworkImpl {
      val p2pNetworkId = UUID.randomUUID().toString()
      return P2PNetworkImpl(p2pNetworkId, networkGraph, "P2PNetwork_" + networkGraph.hashCode())
    }
  }
}