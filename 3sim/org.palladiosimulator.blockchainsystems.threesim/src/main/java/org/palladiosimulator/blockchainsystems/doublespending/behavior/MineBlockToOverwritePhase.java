package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.Set;
import java.util.UUID;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;

public class MineBlockToOverwritePhase implements DoubleSpendingAttackPhase {

	private final MaliciousNodesIdProvider _maliciousNodesIdProvider;
	private final DSAttackBlockStorage _attackBlockStorage;
	private DoubleSpendingAttackPhase _nextPhase;
	
	public MineBlockToOverwritePhase(DSAttackBlockStorage attackBlockStorage, MaliciousNodesIdProvider maliciousNodesIdProvider) {
		_maliciousNodesIdProvider = maliciousNodesIdProvider;
		_attackBlockStorage = attackBlockStorage;
		_nextPhase = this;
	}
	
	@Override
	public void onBlockReceived(Block block, BlockchainSystemNodeContext context) {
		if (AttackerUtils.isMaliciousBlock(block)) {
			
			if (AttackerUtils.isBlockABlockToOverride(block)) {
				context.getMiningProcess().stopMining();
				
				context.getBlockchain().appendBlock(block);
				
				_attackBlockStorage.addBlock(block);
				
				_nextPhase = new MiningForkedBlocksPhase(_attackBlockStorage, _maliciousNodesIdProvider);
			} else {
				_attackBlockStorage.addBlock(block);
			}
			
		} else {
			context.getBlockValidator().validateBlock(block);
		}
	}
	
	@Override
	public void onBlockMined(Block block, BlockchainSystemNodeContext context) {
		context.getMiningProcess().stopMining();
		
		context.getBlockchain().appendBlock(block);
		_attackBlockStorage.addBlock(block);
		
		// Send to all nodes
		context.getBlockPropagationStrategy().distribute(block);
		
		_nextPhase = new MiningForkedBlocksPhase(_attackBlockStorage, _maliciousNodesIdProvider);
		
	}

	@Override
	public Block onCreatingBlock(Long blockMinedAt, String previousBlockHash, BlockchainSystemNodeContext context) {
		// Select transactions to include in the block
		var selectedTrxsResult = context.getTransactionSelectionProcess().selectTransactionsForBlock(context);

	    // Remove the selected transactions from the mempool
		context.getTrxMemPool().removeTransactions(selectedTrxsResult.getTransactions());
		
		return context.getBlockFactory().createBlock(
				UUID.randomUUID().toString(), 
				previousBlockHash, 
				context.getId(), 
				blockMinedAt,
				selectedTrxsResult.getTotalSize(),
				selectedTrxsResult.getTransactions(),
				AttackerUtils.getBlocksToOverrideAttributes());
	}

	@Override
	public void onBlockValidated(Block block, Boolean isValid, BlockchainSystemNodeContext context) {
		// Only invoked for blocks mined by honest nodes
		
		// Append them if valid but only distribute them to other malicious nodes
		if (isValid != null && isValid.booleanValue()) {
			context.getBlockchain().appendBlock(block);
			
			AttackerUtils.distributeBlockToMaliciousNodes(block, context, _maliciousNodesIdProvider);
		}
	}

	@Override
	public String onPreviousBlockSelection(BlockchainSystemNodeContext context) {
		// Get only blocks at position 2 | One block after the genesis block
		Set<Block> potentialPreviousBlocks = context.getBlockchain().getBlocksAtPosition(2);
		
		Block chosenBlock = potentialPreviousBlocks
				.stream()
				.min((x1, x2) -> Long.compare(x1.getBlockMinedTimestamp(), x2.getBlockMinedTimestamp()))
				.get();
		
		return chosenBlock.getHash();
	}

	@Override
	public DoubleSpendingAttackPhase getNextPhase() {
		return _nextPhase;
	}

	@Override
	public void initialize(BlockchainSystemNodeContext context) {
		// Append already received malicious blocks if possible
		context.getMiningProcess().startMining();
	}

	@Override
	public String getPhaseName() {
		return "MineBlockToOverwritePhase";
	}
}
