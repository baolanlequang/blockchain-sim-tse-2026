package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult

/**
 * Result of the connected-subgraph network translation used by the refined study.
 * Audit values are retained so the experiment runner can verify that executable
 * allocations match the sampled configuration.
 */
data class ConnectedSubgraphNetworkCreationResult(
  override val createdNetwork: P2PNetwork,
  val nodeIdToNodeTemplateIdMapping: HashMap<String, String>,
  val nodeIdToIndexMapping: HashMap<String, Int>,
  val connectionCount: Int = 0,
  val systemBandwidthBudgetMbps: Double = Double.NaN,
  val nodeBandwidthsMbps: Map<String, Double> = emptyMap(),
  /** Fixed uniform fraction mixed into each node/link bandwidth allocation. */
  val bandwidthUniformFloorFraction: Double = Double.NaN,
  /** Analytical floor after the two hierarchical bandwidth-allocation stages. */
  val guaranteedMinimumEndpointBandwidthMbps: Double = Double.NaN,
  /** Smallest effective undirected connection bandwidth actually realized. */
  val minimumRealizedEffectiveConnectionBandwidthMbps: Double = Double.NaN,
  val realizedNodeBandwidthH: Double = Double.NaN,
  val realizedLinkBandwidthHPerNode: Map<String, Double> = emptyMap(),
  /** One fixed L_ij value per undirected connection for this network instance. */
  val connectionLatenciesMs: Map<String, Long> = emptyMap(),
  val topologyFingerprint: String = "",
  val bandwidthAllocationFingerprint: String = "",
  val latencyAllocationFingerprint: String = "",
  val undirectedConnectionCount: Int = 0,
  val topologyConnected: Boolean = false,
  val noSelfConnections: Boolean = false,
  val noDuplicateOrReciprocalInitiations: Boolean = false,
  val initiatedAcceptedEachEqualC: Boolean = false
) : P2PNetworkCreationResult
