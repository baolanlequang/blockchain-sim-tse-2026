package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;
import org.palladiosimulator.blockchainsystems.core.behavior.BehaviorUtils;

public class WaitingForFirstHonestBlockPhase implements DoubleSpendingAttackPhase {

	private final BlockchainMaliciousNodesIdProvider _maliciousNodesIdProvider;
	private final DSAttackBlockStorage _attackBlockStorage;
	private DoubleSpendingAttackPhase _nextPhase;
	
	public WaitingForFirstHonestBlockPhase(BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
		_maliciousNodesIdProvider = maliciousNodesIdProvider;
		_attackBlockStorage = new DSAttackBlockStorage();
		_nextPhase = this;
	}
	
	@Override
	public void onBlockReceived(Block block, BlockchainSystemNodeContext context) {
		if (AttackerUtils.isMaliciousBlock(block)) {
			_attackBlockStorage.addBlock(block);
		} else {
			context.getBlockValidator().validateBlock(block);	
		}
	}

	@Override
	public void onBlockMined(Block block, BlockchainSystemNodeContext context) {
		// Should not be called since in this phase there are no blocks mined
	}

	@Override
	public Block onCreatingBlock(Long blockMinedAt, String previousBlockHash, BlockchainSystemNodeContext context) {
		// Should not be called since in this phase there are no blocks mined
		return null;
	}

	@Override
	public void onBlockValidated(Block block, Boolean isValid, BlockchainSystemNodeContext context) {
		if (isValid != null && isValid.booleanValue()) {
			
			// Append. Since this is the first block it musst be appended as an included block
			boolean hasBlockchainNewLongestBranch = BehaviorUtils.INSTANCE.appendBlockToBlockchain(block, context);
			
			context.getBlockPropagationStrategy().distribute(block);
			
			if (hasBlockchainNewLongestBranch) {
				_nextPhase = new MineBlockToOverwritePhase(_attackBlockStorage, _maliciousNodesIdProvider);
			}
		}
	}

	@Override
	public String onPreviousBlockSelection(BlockchainSystemNodeContext context) {
		// Should not be called since in this phase there are no blocks mined
		return null;
	}

	@Override
	public DoubleSpendingAttackPhase getNextPhase() {
		return _nextPhase;
	}

	@Override
	public void initialize(BlockchainSystemNodeContext context) {
	}

	@Override
	public String getPhaseName() {
		return "WaitingForFirstHonestBlockPhase";
	}
}
