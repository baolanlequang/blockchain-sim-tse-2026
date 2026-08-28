package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem as DesignBlockchainSystem
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockchainFactoryImpl
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockPropagationStrategyFactoryImpl
import org.palladiosimulator.blockchainsystems.core.propagation.transaction.TransactionPropagationStrategyFactoryImpl
import org.palladiosimulator.blockchainsystems.core.block.BlockFactoryImpl
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegionsResolver
import org.palladiosimulator.blockchainsystems.core.orphanblockpool.OrphanBlockPoolFactoryImpl
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNodeFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode
import org.palladiosimulator.blockchainsystems.core.network.P2PNetworkImpl
import org.palladiosimulator.blockchainsystems.core.transaction.TrxMemPoolFactoryImpl
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimBlockchainSystemNodeBehaviorFactory
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimTransactionSelectionProcessFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.geography.ThreesimGeographicalRegionsResolver
import java.util.random.RandomGenerator
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimBlockchainSystemNodeTagProvider
import org.palladiosimulator.blockchainsystems.threesim.behavior.ThreesimTransactionSubmissionProcess
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import java.util.UUID
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider
import org.palladiosimulator.blockchainsystems.threesim.behavior.MaliciousNodesIdProviderImpl
import org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior.SelfishMiningBlockchainSystemNodeBehaviorFactory
import java.util.HashSet

/**
 * Factory for creating a generic [BlockchainSystem]
 *
 * @author Davis Riedel
 */
abstract class ThreesimBlockchainSystemFactory @JvmOverloads constructor(
  protected val designBlockchainSystem: DesignBlockchainSystem,
  protected val networkTopology: NetworkTopology,
  protected val attackSimulation: Boolean,
  protected val runId: Int = 0,
  protected val gamma: Double = 0.5,
  // Explicit, caller-overridable seed for attacker-node selection (see selectAttackerNodeIds).
  // Defaults to null, meaning "derive from runId" -- there is no pre-existing simulation-wide
  // seed to reuse (mining/transaction/latency/bandwidth all use unseeded RandomGenerator.of
  // ("Random") deliberately, for genuine run-to-run Monte Carlo variation), so this is a new,
  // dedicated parameter rather than a repurposed one. Deriving the default from runId keeps
  // each Monte Carlo round's attacker draw distinct while remaining fully reproducible for a
  // fixed (config, runId) pair; pass an explicit value to control it independently of runId.
  protected val attackerSelectionSeed: Long? = null
) {
  protected abstract fun createP2PNetworkFactory(): P2PNetworkFactory

  protected abstract fun getNodeAllocationResolver(networkCreationResult: P2PNetworkCreationResult): NodeAllocationResolver
  protected abstract fun getResourcePowerCalculator(networkCreationResult: P2PNetworkCreationResult): ResourcePowerCalculator

  // Set by createBlockchainSystemNodeFactory (during createBlockchainSystem), read by
  // logNodeInitializationInfo -- the actual seed used for attacker selection on this run, so it
  // ends up in the run's node-init JSON output and results are traceable/reproducible after the
  // fact.
  private var lastAttackerSelectionSeed: Long? = null

  /**
   * Selects exactly `numberOfAttacker` node IDs uniformly at random from `nodeIds`, using an
   * explicit seeded RNG. Replaces the previous mechanism, which assigned the first
   * `numberOfAttacker` nodes encountered while iterating a `HashSet<P2PNode>` (JVM
   * object-identity hash-bucket order, since `P2PNode` does not override `equals`/`hashCode`) --
   * not a documented or reproducible random draw. `nodeIds` must be a stably-ordered `List`
   * (the caller sorts it) so that "same seed -> same selection" is well-defined independent of
   * any `Set`'s iteration order.
   */
  private fun selectAttackerNodeIds(nodeIds: List<String>, numberOfAttacker: Int, seed: Long): Set<String> {
    if (numberOfAttacker <= 0 || nodeIds.isEmpty()) return emptySet()
    return nodeIds.shuffled(kotlin.random.Random(seed)).take(numberOfAttacker).toSet()
  }

  fun createBlockchainSystem(): BlockchainSystem {
    val networkFactory = createP2PNetworkFactory()

    val networkCreationResult = networkFactory.createP2PNetwork()

    // Create information provider based on the generated network
    val nodeAllocationResolver = getNodeAllocationResolver(networkCreationResult)
    val resourcePowerCalculator = getResourcePowerCalculator(networkCreationResult)

    val geographicalRegionsResolver = ThreesimGeographicalRegionsResolver(
      designBlockchainSystem.geographicalRegionsSpecification,
      nodeAllocationResolver
    );

    // Create factories based on information providers and metamodel
    val blockFactory: BlockFactory = createBlockFactory()

    val nodeFactory = createBlockchainSystemNodeFactory(
      nodeAllocationResolver,
      resourcePowerCalculator,
      blockFactory,
      geographicalRegionsResolver,
      networkCreationResult
    )

    val blockchainSystem = createBlockchainSystemInstance(
      networkCreationResult.createdNetwork,
      blockFactory,
      nodeFactory,
      geographicalRegionsResolver,
      designBlockchainSystem.specification.blockReward
    )

    logNodeInitializationInfo(blockchainSystem, networkCreationResult.createdNetwork)

    return blockchainSystem
  }

  private fun logNodeInitializationInfo(blockchainSystem: BlockchainSystem, network: P2PNetwork) {
    val networkImpl = network as? P2PNetworkImpl
    val systemName = "run_$runId"

    // Compute all node bandwidths in a single pass over the edges (O(N)) instead of
    // calling the per-node lookups (each an O(N) vertex scan) for every node (O(N²)).
    val (outgoingBandwidths, incomingBandwidths) =
      networkImpl?.computeTotalBandwidths() ?: (emptyMap<String, Double>() to emptyMap())

    val nodesJson = blockchainSystem.nodes.sortedBy { it.id }.joinToString(separator = ",\n    ") { node ->
      val outbound = if (networkImpl != null) outgoingBandwidths[node.id] ?: 0.0 else Double.NaN
      val inbound = if (networkImpl != null) incomingBandwidths[node.id] ?: 0.0 else Double.NaN
      """{"nodeId": "${node.id}", "resourcePower": ${node.resourcePower}, "totalOutboundBandwidth": $outbound, "totalInboundBandwidth": $inbound}"""
    }

    val json = """{
  "systemName": "$systemName",
  "totalNodes": ${blockchainSystem.nodes.size},
  "attackerSelectionSeed": ${lastAttackerSelectionSeed?.toString() ?: "null"},
  "nodes": [
    $nodesJson
  ]
}"""

    try {
      // Keep selfish-mining and trilemma node-init logs in separate folders. attackSimulation
      // is true only for the selfish-mining attack runs and false for the trilemma runs.
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
    val blockchainSystemId = UUID.randomUUID().toString()
    val blockchainSystemName = "BlockchainSystem_" + blockchainSystemId.substring(0, 8)

    val genesisBlock = blockFactory.createGenesisBlock()

    val blockchainSystemNodes = network.nodes
      .map { nodeFactory.createBlockchainSystemNode(it, genesisBlock) }
      .toHashSet()


    val trxPropSpec = designBlockchainSystem.transactionsSpecification.transactionPropertiesSpecification
    val meanTrxCreationInterval = designBlockchainSystem.transactionsSpecification.meanTransactionCreationInterval

    val transactionSubmissionProcess = ThreesimTransactionSubmissionProcess(
      blockchainSystemId,
      blockchainSystemName,
      meanTrxCreationInterval,
      TransactionPropertiesValueProviderAdapter.create(
        trxPropSpec,
        RandomGenerator.of("Random")
      )
    )

    val geographicalRegions = geographicalRegionsResolver.resolveGeographicalRegions()

    return BlockchainSystem(
      blockchainSystemId,
      blockchainSystemName,
      network,
      geographicalRegions,
      blockchainSystemNodes,
      transactionSubmissionProcess,
      blockReward
    )
  }

  private fun createBlockchainSystemNodeFactory(
    nodeAllocationResolver: NodeAllocationResolver,
    resourcePowerCalculator: ResourcePowerCalculator,
    blockFactory: BlockFactory,
    geographicalRegionsResolver: ThreesimGeographicalRegionsResolver,
    networkCreationResult: P2PNetworkCreationResult
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
      resourcePowerCalculator
    )
    val transactionSelectionProcessFactory = ThreesimTransactionSelectionProcessFactory(
      maxBlockSize = designBlockchainSystem.specification.maxBlockSize // in byte
    )
    val blockValidatorFactory = ThreesimBlockValidatorFactory(nodeAllocationResolver)

    val numberOfAttacker = if (attackSimulation) designBlockchainSystem.specification.numberOfAttacker else 0

    // Pre-select the full attacker set upfront, via an explicit seeded uniform sample, before
    // any node's behavior is created -- see selectAttackerNodeIds. Sorting the node IDs first
    // gives a stable, Set-iteration-order-independent input to shuffle.
    val effectiveAttackerSelectionSeed = attackerSelectionSeed ?: runId.toLong()
    lastAttackerSelectionSeed = if (attackSimulation) effectiveAttackerSelectionSeed else null
    val allNodeIds = networkCreationResult.createdNetwork.nodes.map { it.endpointId }.sorted()
    val selectedAttackerIds = if (attackSimulation) {
      selectAttackerNodeIds(allNodeIds, numberOfAttacker, effectiveAttackerSelectionSeed)
    } else {
      emptySet()
    }

    val maliciousNodesIdProvider: BlockchainMaliciousNodesIdProvider =
      MaliciousNodesIdProviderImpl(selectedAttackerIds.toMutableSet(), numberOfAttacker)
    val behaviorFactory = if (attackSimulation) {
      SelfishMiningBlockchainSystemNodeBehaviorFactory(numberOfAttacker, gamma)
    } else {
      ThreesimBlockchainSystemNodeBehaviorFactory()
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

  private fun createBlockFactory(): BlockFactoryImpl {
    return BlockFactoryImpl()
  }

  fun getBlockchainSystemSpecification(): BlockchainSystemSpecification {
    return designBlockchainSystem.specification
  }
}