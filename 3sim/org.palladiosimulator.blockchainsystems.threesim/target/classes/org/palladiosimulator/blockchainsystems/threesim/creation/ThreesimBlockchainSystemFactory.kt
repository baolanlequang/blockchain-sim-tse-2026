package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem as DesignBlockchainSystem
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology
import org.palladiosimulator.blockchainsystems.core.block.BlockFactoryImpl
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockchainFactoryImpl
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegionsResolver
import org.palladiosimulator.blockchainsystems.core.network.P2PNetworkImpl
import org.palladiosimulator.blockchainsystems.core.orphanblockpool.OrphanBlockPoolFactoryImpl
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockPropagationStrategyFactoryImpl
import org.palladiosimulator.blockchainsystems.core.propagation.transaction.TransactionPropagationStrategyFactoryImpl
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNodeFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.core.transaction.TrxMemPoolFactoryImpl
import org.palladiosimulator.blockchainsystems.threesim.behavior.MaliciousNodesIdProviderImpl
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimBlockchainSystemNodeBehaviorFactory
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimBlockchainSystemNodeTagProvider
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimTransactionSelectionProcessFactory
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimTransactionSubmissionProcess
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import org.palladiosimulator.blockchainsystems.threesim.creation.geography.ThreesimGeographicalRegionsResolver
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkCreationResult
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkResourcePowerCalculator
import org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior.SelfishMiningBlockchainSystemNodeBehaviorFactory
import java.util.UUID
import java.security.MessageDigest

/** Factory for creating a generic 3SIM blockchain system. */
abstract class ThreesimBlockchainSystemFactory @JvmOverloads constructor(
  protected val designBlockchainSystem: DesignBlockchainSystem,
  protected val networkTopology: NetworkTopology,
  protected val attackSimulation: Boolean,
  protected val runId: Int = 0,
  protected val gamma: Double = 0.5,
  networkSeed: Long = 0L,
  eventSeed: Long = 0L
) {
  protected val randomness = RefinedExperimentRandomness(networkSeed, eventSeed)

  @Volatile
  private var lastCreationAudit: RefinedCreationAudit? = null

  protected abstract fun createP2PNetworkFactory(): P2PNetworkFactory
  protected abstract fun getNodeAllocationResolver(networkCreationResult: P2PNetworkCreationResult): NodeAllocationResolver
  protected abstract fun getResourcePowerCalculator(networkCreationResult: P2PNetworkCreationResult): ResourcePowerCalculator

  fun createBlockchainSystem(): BlockchainSystem {
    val networkCreationResult = createP2PNetworkFactory().createP2PNetwork()
    val nodeAllocationResolver = getNodeAllocationResolver(networkCreationResult)
    val resourcePowerCalculator = getResourcePowerCalculator(networkCreationResult)

    val geographicalRegionsResolver = ThreesimGeographicalRegionsResolver(
      designBlockchainSystem.geographicalRegionsSpecification,
      nodeAllocationResolver
    )
    val blockFactory: BlockFactory = createBlockFactory()

    val requestedAttackers = if (attackSimulation) designBlockchainSystem.specification.numberOfAttacker else 0
    val attackerIds = sampleAdversarialNodeIds(
      networkCreationResult.createdNetwork.nodes.map { it.endpointId },
      requestedAttackers
    )
    val maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider =
      MaliciousNodesIdProviderImpl(HashSet(attackerIds), requestedAttackers)

    val nodeFactory = createBlockchainSystemNodeFactory(
      nodeAllocationResolver,
      resourcePowerCalculator,
      blockFactory,
      geographicalRegionsResolver,
      maliciousNodesIdProvider
    )

    val blockchainSystem = createBlockchainSystemInstance(
      networkCreationResult.createdNetwork,
      blockFactory,
      nodeFactory,
      geographicalRegionsResolver,
      designBlockchainSystem.specification.blockReward
    )

    lastCreationAudit = buildCreationAudit(
      networkCreationResult,
      resourcePowerCalculator,
      attackerIds
    )
    logNodeInitializationInfo(blockchainSystem, networkCreationResult.createdNetwork, lastCreationAudit)
    return blockchainSystem
  }

  /** Structural realization associated with the most recently created single run. */
  fun getLastCreationAudit(): RefinedCreationAudit? = lastCreationAudit

  private fun sampleAdversarialNodeIds(nodeIds: Collection<String>, requested: Int): Set<String> {
    require(requested >= 0) { "Number of attackers must be >= 0." }
    require(requested <= nodeIds.size) {
      "Number of attackers $requested exceeds validating-node count ${nodeIds.size}."
    }
    if (requested == 0) return emptySet()

    val shuffled = nodeIds.sorted().toMutableList()
    val rng = randomness.network("adversarial-identity-assignment")
    for (i in 0 until requested) {
      val j = i + rng.nextInt(shuffled.size - i)
      val tmp = shuffled[i]
      shuffled[i] = shuffled[j]
      shuffled[j] = tmp
    }
    return shuffled.take(requested).toSet()
  }

  private fun buildCreationAudit(
    networkCreationResult: P2PNetworkCreationResult,
    resourcePowerCalculator: ResourcePowerCalculator,
    attackerIds: Set<String>
  ): RefinedCreationAudit? {
    val connected = networkCreationResult as? ConnectedSubgraphNetworkCreationResult ?: return null
    val topology = networkTopology as? ConnectedSubgraphsNetworkTopology ?: return null
    val subgraph = topology.subgraphs.singleOrNull() ?: return null

    val nv = connected.createdNetwork.nodes.size
    val c = connected.connectionCount
    val globalPower = resourcePowerCalculator.calculateGlobalResourcePower()
    val shares = connected.createdNetwork.nodes
      .mapNotNull { n -> resourcePowerCalculator.getResourcePowerOfNode(n.endpointId)?.div(globalPower) }
    val qA = connected.createdNetwork.nodes
      .filter { it.endpointId in attackerIds }
      .sumOf { n -> resourcePowerCalculator.getResourcePowerOfNode(n.endpointId)?.div(globalPower) ?: 0.0 }

    val realizedHashH = (resourcePowerCalculator as? ConnectedSubgraphNetworkResourcePowerCalculator)
      ?.realizedHashingPowerH ?: Double.NaN
    val bandwidthSpec = subgraph.linkAllocation.bandwidthSpecification
    val hashFingerprint = sha256Hex(
      connected.createdNetwork.nodes.sortedBy { it.endpointId }.joinToString("|") { node ->
        val share = resourcePowerCalculator.getResourcePowerOfNode(node.endpointId)?.div(globalPower) ?: 0.0
        "${node.endpointId}=${java.lang.Double.toHexString(share)}"
      }
    )
    val networkRealizationFingerprint = sha256Hex(
      listOf(
        connected.topologyFingerprint,
        connected.bandwidthAllocationFingerprint,
        connected.latencyAllocationFingerprint,
        hashFingerprint,
        attackerIds.sorted().joinToString(",")
      ).joinToString("|")
    )

    return RefinedCreationAudit(
      networkSeed = randomness.networkSeed,
      eventSeed = randomness.eventSeed,
      validatingNodeCount = nv,
      connectionCount = c,
      undirectedConnectionCount = connected.undirectedConnectionCount,
      topologyFeasible = 2 * c <= nv - 1,
      topologyConnected = connected.topologyConnected,
      noSelfConnections = connected.noSelfConnections,
      noDuplicateOrReciprocalInitiations = connected.noDuplicateOrReciprocalInitiations,
      initiatedAcceptedEachEqualC = connected.initiatedAcceptedEachEqualC,
      topologyFingerprint = connected.topologyFingerprint,
      bandwidthAllocationFingerprint = connected.bandwidthAllocationFingerprint,
      latencyAllocationFingerprint = connected.latencyAllocationFingerprint,
      networkRealizationFingerprint = networkRealizationFingerprint,
      systemBandwidthBudgetMbps = connected.systemBandwidthBudgetMbps,
      nodeBandwidthSumMbps = connected.nodeBandwidthsMbps.values.sum(),
      bandwidthUniformFloorFraction = connected.bandwidthUniformFloorFraction,
      guaranteedMinimumEndpointBandwidthMbps = connected.guaranteedMinimumEndpointBandwidthMbps,
      minimumRealizedEffectiveConnectionBandwidthMbps =
        connected.minimumRealizedEffectiveConnectionBandwidthMbps,
      targetNodeBandwidthH = bandwidthSpec.heterogeneityNodeTarget,
      realizedNodeBandwidthH = connected.realizedNodeBandwidthH,
      targetLinkBandwidthH = bandwidthSpec.heterogeneityLinkTarget,
      meanRealizedLinkBandwidthH = connected.realizedLinkBandwidthHPerNode.values
        .takeIf { it.isNotEmpty() }?.average() ?: Double.NaN,
      meanConnectionLatencyMs = connected.connectionLatenciesMs.values
        .takeIf { it.isNotEmpty() }?.average() ?: Double.NaN,
      minConnectionLatencyMs = connected.connectionLatenciesMs.values.minOrNull() ?: -1L,
      maxConnectionLatencyMs = connected.connectionLatenciesMs.values.maxOrNull() ?: -1L,
      targetHashingPowerH = designBlockchainSystem.specification.hashRateConcentration,
      realizedHashingPowerH = realizedHashH,
      hashingPowerShareSum = shares.sum(),
      numberOfAttackers = attackerIds.size,
      realizedFractionOfAttackers = if (nv == 0) 0.0 else attackerIds.size.toDouble() / nv.toDouble(),
      realizedAdversarialHashingPowerShare = qA,
      attackerNodeIds = attackerIds.sorted()
    )
  }

  private fun logNodeInitializationInfo(
    blockchainSystem: BlockchainSystem,
    network: P2PNetwork,
    audit: RefinedCreationAudit?
  ) {
    val networkImpl = network as? P2PNetworkImpl
    val systemName = "run_$runId"
    val (outgoingBandwidths, incomingBandwidths) =
      networkImpl?.computeTotalBandwidths() ?: (emptyMap<String, Double>() to emptyMap())

    val nodesJson = blockchainSystem.nodes.sortedBy { it.id }.joinToString(separator = ",\n    ") { node ->
      val outbound = if (networkImpl != null) outgoingBandwidths[node.id] ?: 0.0 else Double.NaN
      val inbound = if (networkImpl != null) incomingBandwidths[node.id] ?: 0.0 else Double.NaN
      """{"nodeId": "${node.id}", "resourcePower": ${node.resourcePower}, "totalOutboundBandwidth": $outbound, "totalInboundBandwidth": $inbound}"""
    }

    val auditJson = audit?.let {
      """"networkSeed": ${it.networkSeed}, "eventSeed": ${it.eventSeed}, "connectionCount": ${it.connectionCount}, "bandwidthUniformFloorFraction": ${it.bandwidthUniformFloorFraction}, "guaranteedMinimumEndpointBandwidthMbps": ${it.guaranteedMinimumEndpointBandwidthMbps}, "minimumRealizedEffectiveConnectionBandwidthMbps": ${it.minimumRealizedEffectiveConnectionBandwidthMbps}, "realizedNodeBandwidthH": ${it.realizedNodeBandwidthH}, "meanRealizedLinkBandwidthH": ${it.meanRealizedLinkBandwidthH}, "realizedHashingPowerH": ${it.realizedHashingPowerH}, "numberOfAttackers": ${it.numberOfAttackers}, "realizedAdversarialHashingPowerShare": ${it.realizedAdversarialHashingPowerShare},"""
    } ?: ""

    val json = """{
  "systemName": "$systemName",
  $auditJson
  "totalNodes": ${blockchainSystem.nodes.size},
  "nodes": [
    $nodesJson
  ]
}"""

    try {
      val nodeInitDir = if (attackSimulation) "node_init_selfishmining" else "node_init_trilemma"
      val outputDir = java.nio.file.Paths.get(nodeInitDir)
      java.nio.file.Files.createDirectories(outputDir)
      val outputFile = outputDir.resolve("init_${systemName}_${blockchainSystem.id.substring(0, 8)}.json")
      java.nio.file.Files.writeString(outputFile, json)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun createBlockchainSystemInstance(
    network: P2PNetwork,
    blockFactory: BlockFactory,
    nodeFactory: BlockchainSystemNodeFactory,
    geographicalRegionsResolver: GeographicalRegionsResolver,
    blockReward: Double
  ): BlockchainSystem {
    val systemIdRng = randomness.event("blockchain-system-id")
    val blockchainSystemId = UUID(systemIdRng.nextLong(), systemIdRng.nextLong()).toString()
    val blockchainSystemName = "BlockchainSystem_" + blockchainSystemId.substring(0, 8)
    val genesisBlock = blockFactory.createGenesisBlock()

    val blockchainSystemNodes = network.nodes
      .map { nodeFactory.createBlockchainSystemNode(it, genesisBlock) }
      .toHashSet()

    val trxPropSpec = designBlockchainSystem.transactionsSpecification.transactionPropertiesSpecification
    val meanTrxCreationInterval = designBlockchainSystem.transactionsSpecification.meanTransactionCreationInterval
    require(meanTrxCreationInterval > 0.0) { "Mean transaction creation interval must be > 0 ms." }

    val transactionSubmissionProcess = ThreesimTransactionSubmissionProcess(
      blockchainSystemId,
      blockchainSystemName,
      meanTrxCreationInterval,
      TransactionPropertiesValueProviderAdapter.create(
        trxPropSpec,
        randomness.event("transaction-properties")
      ),
      arrivalRandomGenerator = randomness.event("transaction-arrival"),
      identityRandomGenerator = randomness.event("transaction-identities")
    )

    return BlockchainSystem(
      blockchainSystemId,
      blockchainSystemName,
      network,
      geographicalRegionsResolver.resolveGeographicalRegions(),
      blockchainSystemNodes,
      transactionSubmissionProcess,
      blockReward,
      randomness.event("transaction-recipient")
    )
  }

  private fun createBlockchainSystemNodeFactory(
    nodeAllocationResolver: NodeAllocationResolver,
    resourcePowerCalculator: ResourcePowerCalculator,
    blockFactory: BlockFactory,
    geographicalRegionsResolver: ThreesimGeographicalRegionsResolver,
    maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider
  ): BlockchainSystemNodeFactory {
    val blockchainFactory = BlockchainFactoryImpl(
      designBlockchainSystem.specification.numOfRequiredSecurityConfirmations
    )
    val blockPropagationStrategyFactory = BlockPropagationStrategyFactoryImpl()
    val transactionPropagationStrategyFactory = TransactionPropagationStrategyFactoryImpl()
    val orphanBlockPoolFactory = OrphanBlockPoolFactoryImpl()
    val trxMemPoolFactory = TrxMemPoolFactoryImpl()
    val miningProcessFactory = ThreesimMiningProcessFactory(
      designBlockchainSystem.specification.meanBlockTime,
      resourcePowerCalculator,
      randomness
    )
    val transactionSelectionProcessFactory = ThreesimTransactionSelectionProcessFactory(
      maxBlockSize = designBlockchainSystem.specification.maxBlockSize
    )
    val blockValidatorFactory = ThreesimBlockValidatorFactory(nodeAllocationResolver, randomness)

    val numberOfAttacker = maliciousNodesIdProvider.getNumberOfAttacker()
    val behaviorFactory = if (attackSimulation && numberOfAttacker > 0) {
      SelfishMiningBlockchainSystemNodeBehaviorFactory(numberOfAttacker, gamma, randomness.eventSeed)
    } else {
      ThreesimBlockchainSystemNodeBehaviorFactory(randomness)
    }
    val tagProvider = ThreesimBlockchainSystemNodeTagProvider(maliciousNodesIdProvider)

    return BlockchainSystemNodeFactory(
      blockFactory,
      blockchainFactory,
      miningProcessFactory,
      transactionSelectionProcessFactory,
      blockValidatorFactory,
      blockPropagationStrategyFactory,
      transactionPropagationStrategyFactory,
      trxMemPoolFactory,
      orphanBlockPoolFactory,
      behaviorFactory,
      geographicalRegionsResolver,
      resourcePowerCalculator,
      tagProvider,
      maliciousNodesIdProvider
    )
  }

  private fun createBlockFactory(): BlockFactoryImpl = BlockFactoryImpl(randomness.event("genesis-block-id"))

  private fun sha256Hex(value: String): String =
    MessageDigest.getInstance("SHA-256")
      .digest(value.toByteArray(Charsets.UTF_8))
      .joinToString("") { byte -> "%02x".format(byte.toInt() and 0xff) }

  fun getBlockchainSystemSpecification(): BlockchainSystemSpecification = designBlockchainSystem.specification
}
