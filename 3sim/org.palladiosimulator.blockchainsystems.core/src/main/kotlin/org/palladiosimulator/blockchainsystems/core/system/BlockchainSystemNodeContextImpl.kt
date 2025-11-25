package org.palladiosimulator.blockchainsystems.core.system

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockFactory
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegion
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.system.abstractions.*
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSelectionProcess

/**
 * The [BlockchainSystemNodeContextImpl] class is an implementation of the [BlockchainSystemNodeContext] interface.
 * It is a container that provides access to all components of a blockchain system node.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainSystemNodeContextImpl(
  override val id: String,
  override val blockPropagationStrategy: PropagationStrategy<Block>,
  override val transactionPropagationStrategy: PropagationStrategy<Transaction>,
  override val networkInterface: NodeP2PNetworkInterface,
  override val resourcePower: Double,
  override val miningProcess: MiningProcess,
  override val transactionSelectionProcess: TransactionSelectionProcess,
  override val blockchain: Blockchain,
  override val blockValidator: BlockValidator,
  override val trxMemPool: TrxMemPool,
  override val orphanBlockPool: OrphanBlockPool,
  override val blockFactory: BlockFactory,
  override val geographicalRegion: GeographicalRegion,
) : BlockchainSystemNodeContext