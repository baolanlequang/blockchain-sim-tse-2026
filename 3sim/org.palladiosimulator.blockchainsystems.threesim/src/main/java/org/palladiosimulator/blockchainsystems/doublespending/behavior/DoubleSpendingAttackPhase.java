package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;

public interface DoubleSpendingAttackPhase {

	void initialize(BlockchainSystemNodeContext context);
	
	void onBlockReceived(Block block, BlockchainSystemNodeContext context);
	
	void onBlockMined(Block block, BlockchainSystemNodeContext context);
	
	Block onCreatingBlock(Long blockMinedAt, String previousBlockHash, BlockchainSystemNodeContext context);
	
	void onBlockValidated(Block block, Boolean isValid, BlockchainSystemNodeContext context);
	
	String onPreviousBlockSelection(BlockchainSystemNodeContext context);
	
	DoubleSpendingAttackPhase getNextPhase();
	
	String getPhaseName();
}
