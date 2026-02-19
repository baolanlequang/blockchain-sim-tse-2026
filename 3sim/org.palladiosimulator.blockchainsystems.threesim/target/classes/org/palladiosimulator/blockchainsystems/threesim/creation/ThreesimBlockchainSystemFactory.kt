package org.palladiosimulator.blockchainsystems.threesim.creation

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
import java.util.HashSet

/**
 * Factory for creating a generic [BlockchainSystem]
 *
 * @author Davis Riedel
 */
abstract class ThreesimBlockchainSystemFactory(
  protected val designBlockchainSystem: DesignBlockchainSystem,
  protected val networkTopology: NetworkTopology,
  protected val attackSimulation: Boolean
) {
  protected abstract fun createP2PNetworkFactory(): P2PNetworkFactory

  protected abstract fun getNodeAllocationResolver(networkCreationResult: P2PNetworkCreationResult): NodeAllocationResolver
  protected abstract fun getResourcePowerCalculator(networkCreationResult: P2PNetworkCreationResult): ResourcePowerCalculator

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

    return createBlockchainSystemInstance(
      networkCreationResult.createdNetwork,
      blockFactory,
      nodeFactory,
      geographicalRegionsResolver,
      designBlockchainSystem.specification.blockReward
    )
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

    val numberOfAttacker = if (attackSimulation == true) designBlockchainSystem.specification.numberOfAttacker else 0
    val behaviorFactory = ThreesimBlockchainSystemNodeBehaviorFactory(resourcePowerCalculator, numberOfAttacker)
    val tagProvider = ThreesimBlockchainSystemNodeTagProvider(behaviorFactory.maliciousNodesIdProvider)

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
      tagProvider
    )
  }

  private fun createBlockFactory(): BlockFactoryImpl {
    return BlockFactoryImpl()
  }
}