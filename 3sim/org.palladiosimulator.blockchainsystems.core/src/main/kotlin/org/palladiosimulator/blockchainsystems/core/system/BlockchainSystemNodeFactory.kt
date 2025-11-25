package org.palladiosimulator.blockchainsystems.core.system

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidatorFactory
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegionsResolver
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategyFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPoolFactory
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcessFactory


/**
 * Factory for creating instances of [BlockchainSystemNode].
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainSystemNodeFactory(
  private val blockFactory: BlockFactory,
  private val blockchainFactory: BlockchainFactory,
  private val miningProcessFactory: MiningProcessFactory,
  private val transactionSelectionProcessFactory: TransactionSelectionProcessFactory,
  private val blockValidatorFactory: BlockValidatorFactory,
  private val blockPropagationStrategyFactory: PropagationStrategyFactory<Block>,
  private val transactionPropagationStrategyFactory: PropagationStrategyFactory<Transaction>,
  private val trxMemPoolFactory: TrxMemPoolFactory,
  private val orphanBlockPoolFactory: OrphanBlockPoolFactory,
  private val behaviorFactory: BlockchainSystemNodeBehaviorFactory,
  private val geographicalRegionsResolver: GeographicalRegionsResolver,
  private val resourcePowerCalculator: ResourcePowerCalculator,
  private val tagProvider: BlockchainSystemNodeTagProvider
) {
  fun createBlockchainSystemNode(
    networkInterface: NodeP2PNetworkInterface,
    genesisBlock: Block
  ): BlockchainSystemNode {
    val nodeId = networkInterface.endpointId

    return BlockchainSystemNode(
      nodeId,
      "BlockchainSystemNode_$nodeId",
      blockPropagationStrategyFactory.createPropagationStrategy(),
      transactionPropagationStrategyFactory.createPropagationStrategy(),
      networkInterface,
      resourcePowerCalculator.getResourcePowerOfNode(nodeId)!!,
      miningProcessFactory.createMiningProcess(nodeId),
      transactionSelectionProcessFactory.createTransactionSelectionProcess(nodeId),
      blockchainFactory.createBlockchain(genesisBlock, nodeId),
      blockValidatorFactory.createBlockValidator(nodeId),
      trxMemPoolFactory.createEmptyTransactionMemPool(nodeId),
      orphanBlockPoolFactory.createOrphanBlockPool(nodeId),
      blockFactory,
      behaviorFactory.create(nodeId),
      geographicalRegionsResolver.getGeographicalRegionOfNode(nodeId),
      tagProvider.getTags(nodeId)
    )
  }
}