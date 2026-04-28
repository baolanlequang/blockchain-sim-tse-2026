package org.palladiosimulator.blockchainsystems.doublespending.monitoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin;
import org.palladiosimulator.blockchainsystems.core.eventcoordination.TerminationCondition;
import org.palladiosimulator.blockchainsystems.core.mining.BlockMinedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.simulation.termination.InActivityThresholdCondition;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventSubscriber;
import org.palladiosimulator.blockchainsystems.doublespending.behavior.AttackerUtils;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.HonestNodeTerminationState;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.LongestChainExceededMaxLengthCondition;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.MaliciousNodeTerminationState;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.NodeTerminationState;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.SimulationWinnerVoter;
import org.palladiosimulator.blockchainsystems.doublespending.util.TagUtils;

public class SimulationMonitor implements TraceEventSubscriber, TerminationCondition {

	private final HashMap<String, HonestNodeTerminationState> _honestNodeTerminationStates;
	private final HashMap<String, MaliciousNodeTerminationState> _maliciousNodeTerminationStates;
	private final HashMap<String, NodeTerminationState> _allNodeTerminationStates;
	
	private final HashSet<Block> _forkedBlocks;
	
	private final LongestChainExceededMaxLengthCondition _maxBlockchainLengthCondition;
	private final InActivityThresholdCondition _inactivityThresholdCondition;
	
	public SimulationMonitor(LongestChainExceededMaxLengthCondition maxBlockchainLengthCondition, InActivityThresholdCondition inactivityThresholdCondition) {
		_maxBlockchainLengthCondition = maxBlockchainLengthCondition;
		_inactivityThresholdCondition = inactivityThresholdCondition;
		
		_honestNodeTerminationStates = new HashMap<String, HonestNodeTerminationState>();
		_maliciousNodeTerminationStates = new HashMap<String, MaliciousNodeTerminationState>();
		_allNodeTerminationStates = new HashMap<String, NodeTerminationState>();
	
		_forkedBlocks = new HashSet<Block>();
	}
	
	public void initializeNodes(Set<BlockchainSystemNode> nodes) {
		nodes.stream().forEach(node -> {
			if (TagUtils.isMalicioiusNode(node)) {
				MaliciousNodeTerminationState terminationState = new MaliciousNodeTerminationState(node, _maxBlockchainLengthCondition);
				_maliciousNodeTerminationStates.put(node.getId(), terminationState);
				_allNodeTerminationStates.put(node.getId(), terminationState);
			} else {
				HonestNodeTerminationState terminationState = new HonestNodeTerminationState(node);
				_honestNodeTerminationStates.put(node.getId(), terminationState);
				_allNodeTerminationStates.put(node.getId(), terminationState);
			}
		});
	}
	
	@Override
	public void onTraceEventOccurred(TraceEvent event, TraceEventLogOrigin logOrigin) {
		if (event.getEventType() == BlockMinedTraceEvent.EVENT_TYPE) {
			BlockMinedTraceEvent blockMinedTraceEvent = (BlockMinedTraceEvent)event;
			
			if (AttackerUtils.isBlockABlockForkedBlock(blockMinedTraceEvent.getBlock())) {
				_forkedBlocks.add(blockMinedTraceEvent.getBlock());
			}
			
		} else if (event.getEventType() == BlockAppendedTraceEvent.EVENT_TYPE) {
			BlockAppendedTraceEvent blockAppendedTraceEvent = (BlockAppendedTraceEvent)event;
			
			_maxBlockchainLengthCondition.onBlockAppended(blockAppendedTraceEvent.getBlockPosition());
		}
		
		NodeTerminationState terminationState = _allNodeTerminationStates.get(logOrigin.getId());
		if (terminationState != null) {
			terminationState.onTraceEventOccured(event);
		}
	}

	@Override
	public boolean shouldTerminate() {
		// Check if attackers have published all of their blocks
		if (didAllMaliciousNodesReachFinalPhase()) {
			// Require honest nodes to receive all forked blocks
			if (didAllHonestNodesReceiveAllForkedBlocks()) {
				// Check if all honest nodes have a single longest chain with distance x to the
				// next longest chain
				if (doAllHonestNodesHaveALongestChainWithDistance(2)) {
					return true;
				}
			}
		}

		return _maxBlockchainLengthCondition.hasLengthExceeded() || _inactivityThresholdCondition.hasProlongedInactivityExceeded();
	}

	public void setSimulationClock(SimulationClock simulationClock) {
		_inactivityThresholdCondition.setSimulationClock(simulationClock);
	}
	
	public Set<SimulationWinnerVoter> getWinnerVoters() {
		return _allNodeTerminationStates
				.values()
				.stream()
				.map(x -> (SimulationWinnerVoter)x)
				.collect(Collectors.toUnmodifiableSet());
	}

	private boolean didAllMaliciousNodesReachFinalPhase() {
		return _maliciousNodeTerminationStates
				.values()
				.stream()
				.anyMatch(x -> x.hasReachedFinalPhase());
	}
	
	private boolean didAllHonestNodesReceiveAllForkedBlocks() {
		return _honestNodeTerminationStates
				.values()
				.stream()
				.allMatch(x -> x.hasReceivedAllForkedBlocks(_forkedBlocks));
	}
	
	private boolean doAllHonestNodesHaveALongestChainWithDistance(int distance) {
		return _honestNodeTerminationStates
				.values()
				.stream()
				.allMatch(x -> x.hasLongestChainWithDistance(distance));
	}

}
