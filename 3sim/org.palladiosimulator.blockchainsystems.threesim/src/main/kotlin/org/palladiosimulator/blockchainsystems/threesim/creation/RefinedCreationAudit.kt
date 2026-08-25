package org.palladiosimulator.blockchainsystems.threesim.creation

import kotlinx.serialization.Serializable

/** Structural/resource realization retained with each refined execution. */
@Serializable
data class RefinedCreationAudit(
  val networkSeed: Long,
  val eventSeed: Long,
  val validatingNodeCount: Int,
  val connectionCount: Int,
  val undirectedConnectionCount: Int,
  val topologyFeasible: Boolean,
  val topologyConnected: Boolean,
  val noSelfConnections: Boolean,
  val noDuplicateOrReciprocalInitiations: Boolean,
  val initiatedAcceptedEachEqualC: Boolean,
  val topologyFingerprint: String,
  val bandwidthAllocationFingerprint: String,
  val latencyAllocationFingerprint: String,
  val networkRealizationFingerprint: String,
  val systemBandwidthBudgetMbps: Double,
  val nodeBandwidthSumMbps: Double,
  /** Fixed eta_B used by the regularized bandwidth allocation. */
  val bandwidthUniformFloorFraction: Double,
  /** Guaranteed per-endpoint bandwidth floor implied by eta_B and B_endpoint. */
  val guaranteedMinimumEndpointBandwidthMbps: Double,
  /** Smallest effective connection bandwidth in this network realization. */
  val minimumRealizedEffectiveConnectionBandwidthMbps: Double,
  val targetNodeBandwidthH: Double,
  val realizedNodeBandwidthH: Double,
  val targetLinkBandwidthH: Double,
  val meanRealizedLinkBandwidthH: Double,
  val meanConnectionLatencyMs: Double,
  val minConnectionLatencyMs: Long,
  val maxConnectionLatencyMs: Long,
  val targetHashingPowerH: Double,
  val realizedHashingPowerH: Double,
  val hashingPowerShareSum: Double,
  val numberOfAttackers: Int,
  val realizedFractionOfAttackers: Double,
  val realizedAdversarialHashingPowerShare: Double,
  val attackerNodeIds: List<String>
)
