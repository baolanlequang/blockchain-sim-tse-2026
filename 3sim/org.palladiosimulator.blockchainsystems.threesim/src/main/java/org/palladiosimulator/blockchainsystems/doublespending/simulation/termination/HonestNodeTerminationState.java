package org.palladiosimulator.blockchainsystems.doublespending.simulation.termination;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType;
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockTypeChangedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode;
import org.palladiosimulator.blockchainsystems.doublespending.behavior.AttackerUtils;

public class HonestNodeTerminationState implements NodeTerminationState {

	private BlockchainSystemNode _node;
	
	private final HashMap<String, BTOState> _btoStates;
	
	public HonestNodeTerminationState(BlockchainSystemNode node) {
		_node = node;
		_btoStates = new HashMap<String, BTOState>();
	}
	
	@Override
	public void onTraceEventOccured(TraceEvent traceEvent) {
		if (traceEvent.getEventType() == BlockAppendedTraceEvent.EVENT_TYPE) {
			BlockAppendedTraceEvent blockAppendedTraceEvent = (BlockAppendedTraceEvent) traceEvent;
			
			addBlock(
					blockAppendedTraceEvent.getAppendedBlock(),
					blockAppendedTraceEvent.getAppendedBlockType(),
					blockAppendedTraceEvent.getBlockPosition());
			
		} else if (traceEvent.getEventType() == BlockTypeChangedTraceEvent.EVENT_TYPE) {
			BlockTypeChangedTraceEvent blockTypeChangedTraceEvent = (BlockTypeChangedTraceEvent) traceEvent;
			
			onBlockTypeChanged(
					blockTypeChangedTraceEvent.getBlock(),
					blockTypeChangedTraceEvent.getNewBlockType());
		}
		
	}
	
	private void addHonestBlock(BlockState blockState) {
		// Try add block ass successor of BTO
		BTOState btoState = _btoStates
			.values()
			.stream()
			.filter(x -> x.isBlockSuccessorOfBTO(blockState.getBlock()))
			.findFirst()
			.orElse(null);
		
		// Block is successor of BTO
		if (btoState != null) {
			btoState.addBTOSuccessorBlock(blockState);
			// Check if BTO was accepted
		}
	}
	
	public void onBlockTypeChanged(Block block, BlockType newBlockType) {
		if (AttackerUtils.isBlockABlockToOverride(block)) {
			
			if (!_btoStates.containsKey(block.getHash())) {
				return;
			}
			
			_btoStates.get(block.getHash()).onBTOBlockTypeChanged(newBlockType);
		}
		
	}
	
	public void addBlock(Block block, BlockType blockType, long blockPosition) {
		BlockState blockState = new BlockState(
				block,
				blockPosition,
				blockType);
		
		if (AttackerUtils.isMaliciousBlock(block)
				&& AttackerUtils.isBlockABlockToOverride(block)) {
			
			if (_btoStates.containsKey(blockState.getBlock().getHash())) {
				return;
			}
			// TODO: parametrize
			BTOState btoState = new BTOState(blockState, 3);
			_btoStates.put(blockState.getBlock().getHash(), btoState);
		
		} else {
			addHonestBlock(blockState);
		}
	}

	@Override
	public SimulationWinnerVote getWinnerVote() {
		BTOState selectedBTOState = getFirstBlockToOverride();
		
		if (selectedBTOState == null || !selectedBTOState.wasBTOAccepted()) {
			return SimulationWinnerVote.BTONotIncluded;
		}
		
		String attackOriginBlockHash = selectedBTOState.getBTO().getBlock().getPreviousHash();
		
		Set<Block> attackOriginBlockSuccessors = _node.getBlockchain()
				.getImmediateSuccessorBlocks(attackOriginBlockHash);

		Set<Block> honestSuccessorChains = getHonestBlocks(attackOriginBlockSuccessors);
		Set<Block> malicoiusSuccessorChains = getForkedBlocks(attackOriginBlockSuccessors);
		
		long btoForkLength = selectedBTOState.getNumberOfLongestChainSuccessors() + 1;
		long longestHonestSuccessorChainLength = getLongestSuccessorChainLength(honestSuccessorChains) + 1;
		long longestMaliciousSuccessorChainLength = getLongestSuccessorChainLength(malicoiusSuccessorChains) + 1;;
		
		if (longestMaliciousSuccessorChainLength > btoForkLength
				&& longestMaliciousSuccessorChainLength > longestHonestSuccessorChainLength) {
			return SimulationWinnerVote.AttackerWon;
		}
		
		return SimulationWinnerVote.SystemWon;
	}

	public boolean hasReceivedAllForkedBlocks(Set<Block> forkedBlocks) {
		return forkedBlocks
				.stream()
				.allMatch(x -> _node.getBlockchain().hasBlockWithHash(x.getHash()));
	}
	
	public boolean hasLongestChainWithDistance(int distance) {
		BTOState selectedBTOState = getFirstBlockToOverride();
		if (selectedBTOState == null) {
			// If no block to override is present the simulation can not be terminated yet.
			return false;
		}
		
		String attackOriginBlockHash = selectedBTOState.getBTO().getBlock().getPreviousHash();
		
		Set<Block> attackOriginBlockSuccessors = _node.getBlockchain()
				.getImmediateSuccessorBlocks(attackOriginBlockHash);

		Set<Block> honestSuccessorChains = getHonestBlocks(attackOriginBlockSuccessors);
		Set<Block> malicoiusSuccessorChains = getForkedBlocks(attackOriginBlockSuccessors);
		
		long btoForkLength = selectedBTOState.getNumberOfLongestChainSuccessors() + 1;
		long longestHonestSuccessorChainLength = getLongestSuccessorChainLength(honestSuccessorChains) + 1;
		long longestMaliciousSuccessorChainLength = getLongestSuccessorChainLength(malicoiusSuccessorChains) + 1;
		
		List<Long> longestLengths = List.of(btoForkLength, longestHonestSuccessorChainLength, longestMaliciousSuccessorChainLength)
			.stream()
			.sorted()
			.limit(2)
			.collect(Collectors.toList());
		
		return Math.abs(longestLengths.get(0) - longestLengths.get(1)) >= distance;
	}
	
	private Set<Block> getHonestBlocks(Set<Block> blocks) {
		return blocks
				.stream()
				.filter(x -> !AttackerUtils.isMaliciousBlock(x))
				.collect(Collectors.toSet());
	}
	
	private Set<Block> getForkedBlocks(Set<Block> blocks) {
		return blocks
				.stream()
				.filter(x -> AttackerUtils.isBlockABlockForkedBlock(x))
				.collect(Collectors.toSet());
	}
	
	private long getLongestSuccessorChainLength(Set<Block> blocks) {
		return blocks.stream()
				.mapToLong(x -> _node.getBlockchain().getLongestSuccessorChainLength(x.getHash()))
				.max()
				.orElse(0);
	}
	
	private BTOState getFirstBlockToOverride() {
		return _btoStates
				.values()
				.stream()
				.min((x1, x2) -> Long.compare(x1.getBTO().getBlock().getBlockMinedTimestamp(), x2.getBTO().getBlock().getBlockMinedTimestamp()))
				.orElse(null);
	}
}
