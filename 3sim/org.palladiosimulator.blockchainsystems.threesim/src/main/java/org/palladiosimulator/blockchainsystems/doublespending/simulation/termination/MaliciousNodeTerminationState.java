package org.palladiosimulator.blockchainsystems.doublespending.simulation.termination;

import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode;
import org.palladiosimulator.blockchainsystems.doublespending.behavior.DSAttackPhaseChangedTraceEvent;

public class MaliciousNodeTerminationState implements NodeTerminationState {

	private BlockchainSystemNode _node;
	
	private boolean _hasReachedFinalPhase;

	private final LongestChainExceededMaxLengthCondition _chainLengthExceededCondition;
	
	public MaliciousNodeTerminationState(BlockchainSystemNode node, LongestChainExceededMaxLengthCondition chainLengthExceededCondition) {
		_node = node;
		
		_hasReachedFinalPhase = false;
//		_chainLengthExceededCondition = new LongestChainExceededMaxLengthCondition(25);
		_chainLengthExceededCondition = chainLengthExceededCondition;
	}
	
	public BlockchainSystemNode getNode() {
		return _node;
	}
	
	public boolean hasReachedFinalPhase() {
		return _hasReachedFinalPhase;
	}

	@Override
	public void onTraceEventOccured(TraceEvent traceEvent) {
		
		if (traceEvent.getEventType() == BlockAppendedTraceEvent.EVENT_TYPE) {
			BlockAppendedTraceEvent blockAppendedTraceEvent = (BlockAppendedTraceEvent) traceEvent;
			
			_chainLengthExceededCondition.onBlockAppended(blockAppendedTraceEvent.getBlockPosition());
		}
		if (traceEvent.getEventType() == DSAttackPhaseChangedTraceEvent.EVENT_TYPE) {
			DSAttackPhaseChangedTraceEvent dSAttackPhaseChangedTraceEvent = (DSAttackPhaseChangedTraceEvent) traceEvent;
			
			if (dSAttackPhaseChangedTraceEvent.getNewPhase() == "WaitForBlockDistributionPhase") {
				_hasReachedFinalPhase = true;
			}
		}
		
	}

	@Override
	public SimulationWinnerVote getWinnerVote() {
		return SimulationWinnerVote.NotDetermined;
	}

}
