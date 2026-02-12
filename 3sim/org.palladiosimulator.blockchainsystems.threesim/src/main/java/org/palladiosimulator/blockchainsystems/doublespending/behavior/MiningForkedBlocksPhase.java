package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.behavior.BehaviorUtils;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;

public class MiningForkedBlocksPhase implements DoubleSpendingAttackPhase {

	private final MaliciousNodesIdProvider _maliciousNodesIdProvider;
	private final DSAttackBlockStorage _attackBlockStorage;
	private DoubleSpendingAttackPhase _nextPhase;
	
	private String _currentMiningPreviousBlockHash;
	
	private int _numberOfForkedBlocksMined;
	private static int _maxNumberOfForkedBlocksMined = 15;

	public MiningForkedBlocksPhase(DSAttackBlockStorage attackBlockStorage, MaliciousNodesIdProvider maliciousNodesIdProvider) {
		_attackBlockStorage = attackBlockStorage;
		_maliciousNodesIdProvider = maliciousNodesIdProvider;
		_nextPhase = this;
		
		_currentMiningPreviousBlockHash = null;
		_numberOfForkedBlocksMined = 0;
	}

	@Override
	public void onBlockReceived(Block block, BlockchainSystemNodeContext context) {
		if (AttackerUtils.isMaliciousBlock(block)) {
			
			BehaviorUtils.INSTANCE.appendBlockToBlockchain(block, context);
			
			_attackBlockStorage.addBlock(block);
			
			String nextMiningPreviousBlockHash = _attackBlockStorage.getHashOfLatestForkedBlock();
			if (_currentMiningPreviousBlockHash != nextMiningPreviousBlockHash && nextMiningPreviousBlockHash != null) {
				_currentMiningPreviousBlockHash = nextMiningPreviousBlockHash;
				context.getMiningProcess().restartMining();
			}
			
			_numberOfForkedBlocksMined++;
			
			boolean canReleaseMaliciousBlocks = checkCanReleaseBlocks(context);
			if (canReleaseMaliciousBlocks) {
				_nextPhase = new WaitForBlockDistributionPhase(_attackBlockStorage, _maliciousNodesIdProvider);
			}
		} else {
			context.getBlockValidator().validateBlock(block);
		}
	}
	
	@Override
	public void onBlockMined(Block block, BlockchainSystemNodeContext context) {
		BehaviorUtils.INSTANCE.appendBlockToBlockchain(block, context);
		
		_attackBlockStorage.addBlock(block);
		
		String nextMiningPreviousBlockHash = _attackBlockStorage.getHashOfLatestForkedBlock();
		if (_currentMiningPreviousBlockHash != nextMiningPreviousBlockHash && nextMiningPreviousBlockHash != null) {
			_currentMiningPreviousBlockHash = nextMiningPreviousBlockHash;
		}
		
		_numberOfForkedBlocksMined++;

		AttackerUtils.distributeBlockToMaliciousNodes(block, context, _maliciousNodesIdProvider);
		
		boolean canReleaseMaliciousBlocks = checkCanReleaseBlocks(context);
		if (canReleaseMaliciousBlocks) {
			_nextPhase = new WaitForBlockDistributionPhase(_attackBlockStorage, _maliciousNodesIdProvider);
		}
		
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
				AttackerUtils.getForkedBlockAttributes());
	}

	@Override
	public void onBlockValidated(Block block, Boolean isValid, BlockchainSystemNodeContext context) {
		// Only invoked for blocks mined by honest nodes

		// Append them if valid but only distribute them to other malicious nodes
		if (isValid != null && isValid.booleanValue()) {
			BehaviorUtils.INSTANCE.appendBlockToBlockchain(block, context);

			AttackerUtils.distributeBlockToMaliciousNodes(block, context, _maliciousNodesIdProvider);
			
			boolean canReleaseMaliciousBlocks = checkCanReleaseBlocks(context);
			if (canReleaseMaliciousBlocks) {
				_nextPhase = new WaitForBlockDistributionPhase(_attackBlockStorage, _maliciousNodesIdProvider);
			}
		}
	}
	
	
	
	private static final int MINIMUM_NUMBER_OF_BLOCKS_TO_OVERRIDE = 2;
	
	private boolean checkCanReleaseBlocks(BlockchainSystemNodeContext context) {
		
		// Maximum number of forked blocks is reached
		if (_maxNumberOfForkedBlocksMined <= _numberOfForkedBlocksMined) {
			return true;
		}
		
		String attackOriginBlockHash = _attackBlockStorage.getHashOfFirstMinedAttackOriginBlock();
		Block attackOriginBlock = context.getBlockchain().getBlock(attackOriginBlockHash);
		long attackOriginBlockPosition = context.getBlockchain().getPositionOfBlock(attackOriginBlock);
		 
		 if (context.getBlockchain().getLength() < attackOriginBlockPosition + MINIMUM_NUMBER_OF_BLOCKS_TO_OVERRIDE + 1) {
			 // Block to override can not have enough confirmation blocks because the chain is not long enough
			 return false;
		 }

		 Set<Block> blocksToOverride = getImmediateSuccessorBlocksToOverride(
				 attackOriginBlock.getHash(), 
				 attackOriginBlockPosition,
				 context);
		 	
		 long blockToOverrideSuccessorCounts = getLongestSuccessorChainLength(
				 blocksToOverride.stream().map(x -> x.getHash()).collect(Collectors.toSet()),
				 attackOriginBlockPosition + 1,
				 context);
		 
		 Set<Block> forkedBlocks = getImmediateSuccessorForkedBlocks(
				 attackOriginBlock.getHash(),
				 attackOriginBlockPosition,
				 context);
		 
		 long maliciousForkBlockCounts = getLongestSuccessorChainLength(
				 forkedBlocks.stream().map(x -> x.getHash()).collect(Collectors.toSet()),
				 attackOriginBlockPosition + 1,
				 context);
		 
		 if (blockToOverrideSuccessorCounts < MINIMUM_NUMBER_OF_BLOCKS_TO_OVERRIDE) {
			 return false;
		 }
		 
		 return maliciousForkBlockCounts > blockToOverrideSuccessorCounts + 1;
	}
	

	private Set<Block> getImmediateSuccessorBlocksToOverride(String blockHash, long blockPosition, BlockchainSystemNodeContext context) {
		return context.getBlockchain().getBlocksAtPosition(blockPosition + 1)
				.stream()
			 	.filter(x -> x.getPreviousHash() == blockHash)
			 	.filter(x -> AttackerUtils.isBlockABlockToOverride(x))
			 	.collect(Collectors.toSet());
	}
	
	private Set<Block> getImmediateSuccessorForkedBlocks(String blockHash, long blockPosition, BlockchainSystemNodeContext context) {
		return context.getBlockchain().getBlocksAtPosition(blockPosition + 1)
				.stream()
			 	.filter(x -> x.getPreviousHash() == blockHash)
			 	.filter(x -> AttackerUtils.isBlockABlockForkedBlock(x))
			 	.collect(Collectors.toSet());
	}
	
	private long getLongestSuccessorChainLength(Set<String> startingBlockHashes, long blocksPosition, BlockchainSystemNodeContext context) {
		long counter = blocksPosition + 1;
		
		 Set<String> iterationBlockHashes = startingBlockHashes;
		 
		 while (counter <= context.getBlockchain().getLength() && !iterationBlockHashes.isEmpty()) {
			 HashSet<String> nextiterationBlockHashes = new HashSet<String>();
			 
			 Set<Block> blocksAtPosition = context.getBlockchain().getBlocksAtPosition(counter);
			 for (Block b : blocksAtPosition) {
				 if (iterationBlockHashes.contains(b.getPreviousHash())) {
					 nextiterationBlockHashes.add(b.getHash());
				 }
			 }
			 
			 counter++;
			 iterationBlockHashes = nextiterationBlockHashes;
		 }
		 
		 return counter - blocksPosition - 1;
	}
	
	@Override
	public String onPreviousBlockSelection(BlockchainSystemNodeContext context) {
		return _currentMiningPreviousBlockHash;
	}

	@Override
	public DoubleSpendingAttackPhase getNextPhase() {
		return _nextPhase;
	}

	@Override
	public void initialize(BlockchainSystemNodeContext context) {
		String hashOfLastForkedBlock = _attackBlockStorage.getHashOfLatestForkedBlock();
		if (hashOfLastForkedBlock != null) {
			_currentMiningPreviousBlockHash = hashOfLastForkedBlock;
			context.getMiningProcess().startMining();
		}
	}

	@Override
	public String getPhaseName() {
		return "MiningForkedBlocksPhase";
	}
}
