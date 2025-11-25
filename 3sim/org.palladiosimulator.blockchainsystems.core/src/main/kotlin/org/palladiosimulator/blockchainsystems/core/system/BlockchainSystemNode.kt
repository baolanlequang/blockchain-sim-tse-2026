package org.palladiosimulator.blockchainsystems.core.system

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.common.BlockchainSimulationObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Taggable
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegion
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcess
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSubmittedCallbackSubscriber

/**
 * The [BlockchainSystemNode] class represents a blockchain system node.
 * It uses abstractions for the relevant components of blockchain system nodes,
 * such as a blockchain data structure.
 * The [BlockchainSystemNode] class sets up the correct initialization and linking
 * for these abstractions.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainSystemNode(
  id: String,
  name: String,
  private val blockPropagationStrategy: PropagationStrategy<Block>,
  private val transactionPropagationStrategy: PropagationStrategy<Transaction>,
  private val networkInterface: NodeP2PNetworkInterface,
  val resourcePower: Double,
  private val miningProcess: MiningProcess,
  private val transactionSelectionProcess: TransactionSelectionProcess,
  private val blockchain: Blockchain,
  private val blockValidator: BlockValidator,
  private val trxMemPool: TrxMemPool,
  private val orphanBlockPool: OrphanBlockPool,
  private val blockFactory: BlockFactory,
  private val behavior: BlockchainSystemNodeBehavior,
  val geographicalRegion: GeographicalRegion,
  private val tags: MutableSet<String> = mutableSetOf()
) : BlockchainSimulationObject(id, name), Taggable, TransactionSubmittedCallbackSubscriber {

  private val context: BlockchainSystemNodeContext = BlockchainSystemNodeContextImpl(
    id,
    blockPropagationStrategy,
    transactionPropagationStrategy,
    networkInterface,
    resourcePower,
    miningProcess,
    transactionSelectionProcess,
    blockchain,
    blockValidator,
    trxMemPool,
    orphanBlockPool,
    blockFactory,
    geographicalRegion
  )

  override fun onInitialize() {
    blockchain.initialize(simulationContext)
    blockchain.initializeLogger(this)

    transactionPropagationStrategy.setNetworkInterface(networkInterface)
    transactionPropagationStrategy.setBlockchainSystemNodeContext(context)
    transactionPropagationStrategy.setOnReceivedCallback { behavior.onTransactionReceived(it, context) }
    transactionPropagationStrategy.initialize(simulationContext)
    transactionPropagationStrategy.initializeLogger(this)

    blockPropagationStrategy.setNetworkInterface(networkInterface)
    blockPropagationStrategy.setBlockchainSystemNodeContext(context)
    blockPropagationStrategy.setOnReceivedCallback { behavior.onBlockReceived(it, context) }
    blockPropagationStrategy.initialize(simulationContext)
    blockPropagationStrategy.initializeLogger(this)

    blockValidator.setOnBlockValidatedCallback { block, isValid ->
      behavior.onBlockValidated(
        block,
        isValid,
        context
      )
    }
    blockValidator.initialize(simulationContext)
    blockValidator.initializeLogger(this)

    miningProcess.setOnBlockMinedCallback { behavior.onBlockMined(it, context) }
    miningProcess.setPreviousBlockSelectionCallback { behavior.onPreviousBlockSelection(context) }
    miningProcess.setOnCreatingBlockCallback { blockMinedAt, previousBlockHash ->
      behavior.onCreatingBlock(
        blockMinedAt,
        previousBlockHash,
        context
      )
    }
    miningProcess.initialize(simulationContext)
    miningProcess.initializeLogger(this)

    transactionSelectionProcess.initialize(simulationContext)
    transactionSelectionProcess.initializeLogger(this)

    trxMemPool.initialize(simulationContext)
    trxMemPool.initializeLogger(this)

    orphanBlockPool.initialize(simulationContext)
    orphanBlockPool.initializeLogger(this)

    behavior.initialize(simulationContext)
    behavior.initializeLogger(this)
    behavior.onNodeInitialized(context)
  }

  override fun onCleanup() {
    trxMemPool.cleanup()
    orphanBlockPool.cleanup()
    miningProcess.cleanup()
    blockValidator.cleanup()
    blockPropagationStrategy.cleanup()
    blockchain.cleanup()
  }

  override fun dispatchEvent(event: Event) {
  }

  override fun hasTag(tag: String): Boolean {
    return tags.contains(tag)
  }

  override fun onTransactionSubmitted(transaction: Transaction) {
    if (transaction.recipientId !== this.id) return // Only handle transactions send to this node
    behavior.onTransactionReceived(transaction, context)
  }
}
