package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.HashMap;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;
import org.palladiosimulator.blockchainsystems.core.behavior.BehaviorUtils;

public class WaitForBlockDistributionPhase implements DoubleSpendingAttackPhase {

	private final BlockchainMaliciousNodesIdProvider _maliciousNodesIdProvider;
	private final DSAttackBlockStorage _attackBlockStorage;
	
	public WaitForBlockDistributionPhase(DSAttackBlockStorage attackBlockStorage, BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
		_attackBlockStorage = attackBlockStorage;
		_maliciousNodesIdProvider = maliciousNodesIdProvider;
	}
	
	@Override
	public void initialize(BlockchainSystemNodeContext context) {
		context.getMiningProcess().stopMining();
		
		HashMap<Block, Long> maliciousBlocks = new HashMap<Block, Long>();
		
		String latestForkedBlockHash = _attackBlockStorage.getHashOfLatestForkedBlock();
		Block latestForkedBlock = context.getBlockchain().getBlock(latestForkedBlockHash);
		long latestForkedBlockPosition = context.getBlockchain().getPositionOfBlock(latestForkedBlock);
		
		
		String firstMinedAttackOriginBlockHash = _attackBlockStorage.getHashOfFirstMinedAttackOriginBlock();
		Block firstMinedAttackOriginBlock = context.getBlockchain().getBlock(firstMinedAttackOriginBlockHash);
		long firstMinedAttackOriginBlockPosition = context.getBlockchain().getPositionOfBlock(firstMinedAttackOriginBlock);
		
		long counter = latestForkedBlockPosition;
		Block currentBlock = latestForkedBlock;
		
		do {
			maliciousBlocks.put(currentBlock, counter);
			
			currentBlock = context.getBlockchain().getBlock(currentBlock.getPreviousHash());
			counter--;
			
		} while (currentBlock.getHash() != firstMinedAttackOriginBlockHash && counter >= firstMinedAttackOriginBlockPosition);
		
		
		maliciousBlocks.entrySet()
			.stream()
			.sorted((e1, e2) -> Long.compare(e1.getValue(), e2.getValue()))
			.map(entry -> entry.getKey())
			.forEach(block -> AttackerUtils.distributeBlockToHonestNodes(block, context, _maliciousNodesIdProvider));
	}

	@Override
	public void onBlockReceived(Block block, BlockchainSystemNodeContext context) {
		if (AttackerUtils.isMaliciousBlock(block)) {
			BehaviorUtils.INSTANCE.appendBlockToBlockchain(block, context);
			_attackBlockStorage.addBlock(block);
			
			AttackerUtils.distributeBlockToHonestNodes(block, context, _maliciousNodesIdProvider);
		}
	}
	
	@Override
	public void onBlockMined(Block block, BlockchainSystemNodeContext context) {
		// Should not be called since mining is stopped in this phase
	}

	@Override
	public Block onCreatingBlock(Long blockMinedAt, String previousBlockHash, BlockchainSystemNodeContext context) {
		// Should not be called since mining is stopped in this phase
		return null;
	}

	@Override
	public void onBlockValidated(Block block, Boolean isValid, BlockchainSystemNodeContext context) {
		// Should not be called since honest blocks are ignored in this phase
	}

	@Override
	public String onPreviousBlockSelection(BlockchainSystemNodeContext context) {
		// Should not be called since mining is stopped in this phase
		return null;
	}

	@Override
	public DoubleSpendingAttackPhase getNextPhase() {
		return this;
	}

	@Override
	public String getPhaseName() {
		return "WaitForBlockDistributionPhase";
	}
}
