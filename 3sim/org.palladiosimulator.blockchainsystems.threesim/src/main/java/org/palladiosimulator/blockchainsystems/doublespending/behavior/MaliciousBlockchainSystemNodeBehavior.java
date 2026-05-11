package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction;

public class MaliciousBlockchainSystemNodeBehavior extends BlockchainNodeObject implements BlockchainSystemNodeBehavior {

	private final BlockchainMaliciousNodesIdProvider _maliciousNodesIdProvider;
	private DoubleSpendingAttackPhase _currentAttackPhase;
	
	public MaliciousBlockchainSystemNodeBehavior(BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
		_maliciousNodesIdProvider = maliciousNodesIdProvider;
		_currentAttackPhase = new WaitingForFirstHonestBlockPhase(_maliciousNodesIdProvider);
	}
	
	@Override
	public void onBlockReceived(Block block, BlockchainSystemNodeContext context) {
		if (block != null) {
			_currentAttackPhase.onBlockReceived(block, context);
			goToNextPhase(context);
		}
	}

	@Override
	public void onBlockValidated(Block block, boolean isValid, BlockchainSystemNodeContext context) {
		if (block != null) {
			_currentAttackPhase.onBlockValidated(block, isValid, context);
			goToNextPhase(context);
		}
	}
	
	@Override
	public void onBlockMined(Block block, BlockchainSystemNodeContext context) {
		if (block != null) {
			_currentAttackPhase.onBlockMined(block, context);
			goToNextPhase(context);
		}
	}

	@Override
	public Block onCreatingBlock(long blockMinedAt, String previousBlockHash, BlockchainSystemNodeContext context) {
		Block block = _currentAttackPhase.onCreatingBlock(blockMinedAt, previousBlockHash, context);
		goToNextPhase(context);
		return block;
	}

	@Override
	public String onPreviousBlockSelection(BlockchainSystemNodeContext context) {
		String previousBlockHash = _currentAttackPhase.onPreviousBlockSelection(context);
		return previousBlockHash;
	}

	@Override
	public void onNodeInitialized(BlockchainSystemNodeContext context) {
		_currentAttackPhase.initialize(context);
	}

	private void goToNextPhase(BlockchainSystemNodeContext context) {
		DoubleSpendingAttackPhase nextPhase = _currentAttackPhase.getNextPhase();
		
		if (_currentAttackPhase != nextPhase) {
			String previousAttackPhaseName = _currentAttackPhase.getPhaseName();
			String newAttackPhaseName = nextPhase.getPhaseName();
			
			_currentAttackPhase = nextPhase;
			
			logAttackPhaseChanged(previousAttackPhaseName, newAttackPhaseName);
			
			_currentAttackPhase.initialize(context);
		}
	}
	
	private void logAttackPhaseChanged(String previousPhaseName, String newPhaseName) {
		DSAttackPhaseChangedTraceEvent event = new DSAttackPhaseChangedTraceEvent(
				getSimulationContext().getSystemClock().getCurrentTime(),
				previousPhaseName,
				newPhaseName);
		
		getTraceEventLogger()
			.logEvent(event);
	}

	@Override
	public void dispatchEvent(Event event) {
	}


	@Override
	public void onTransactionReceived(Transaction transaction, BlockchainSystemNodeContext context) {
		// TODO Auto-generated method stub
		
	}
}
