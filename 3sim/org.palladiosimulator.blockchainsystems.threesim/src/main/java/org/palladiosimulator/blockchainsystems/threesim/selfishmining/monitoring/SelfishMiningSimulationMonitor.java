package org.palladiosimulator.blockchainsystems.threesim.selfishmining.monitoring;

import java.util.HashSet;
import java.util.Set;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType;
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockTypeChangedTraceEvent;
import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin;
import org.palladiosimulator.blockchainsystems.core.eventcoordination.TerminationCondition;
import org.palladiosimulator.blockchainsystems.core.simulation.termination.InActivityThresholdCondition;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventSubscriber;
import org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation.termination.LongestChainExceededMaxLengthCondition;

/**
 * Drives termination of a selfish-mining simulation round (max chain length or prolonged
 * inactivity) and tallies confirmed-block rewards per node for the revenue-share success
 * criterion. Unlike the double-spending attack this pipeline replaces, selfish mining has no
 * "final phase" to wait for — the attacker mines continuously for the whole round.
 */
public class SelfishMiningSimulationMonitor implements TraceEventSubscriber, TerminationCondition {

	private final LongestChainExceededMaxLengthCondition _maxBlockchainLengthCondition;
	private final InActivityThresholdCondition _inactivityThresholdCondition;
	private final BlockRewardMonitor _blockRewardMonitor = new BlockRewardMonitor();
	private final Set<String> _rewardedBlockHashes = new HashSet<>();

	public SelfishMiningSimulationMonitor(
			LongestChainExceededMaxLengthCondition maxBlockchainLengthCondition,
			InActivityThresholdCondition inactivityThresholdCondition) {
		_maxBlockchainLengthCondition = maxBlockchainLengthCondition;
		_inactivityThresholdCondition = inactivityThresholdCondition;
	}

	@Override
	public void onTraceEventOccurred(TraceEvent event, TraceEventLogOrigin logOrigin) {
		// Any simulation activity resets the inactivity timer, matching ThreesimSimulationMonitor.
		// Without this, the round terminates as soon as simulated time exceeds ~30 minutes
		// regardless of whether blocks are still being mined normally.
		_inactivityThresholdCondition.restartLoggedSimulationClock();

		if (BlockAppendedTraceEvent.EVENT_TYPE.equals(event.getEventType())) {
			BlockAppendedTraceEvent blockAppendedTraceEvent = (BlockAppendedTraceEvent) event;

			_maxBlockchainLengthCondition.onBlockAppended(blockAppendedTraceEvent.getBlockPosition());

			recordRewardIfConfirmed(blockAppendedTraceEvent.getAppendedBlockType(), blockAppendedTraceEvent.getAppendedBlock());

		} else if (BlockTypeChangedTraceEvent.EVENT_TYPE.equals(event.getEventType())) {
			// A block is first appended as IncludedBlock and only later, once enough
			// confirmations accumulate, transitions to ConfirmedBlock via this separate event
			// rather than a new BlockAppendedTraceEvent - so this is where rewards actually
			// get recorded in practice.
			BlockTypeChangedTraceEvent blockTypeChangedTraceEvent = (BlockTypeChangedTraceEvent) event;

			recordRewardIfConfirmed(blockTypeChangedTraceEvent.getNewBlockType(), blockTypeChangedTraceEvent.getBlock());
		}
	}

	private void recordRewardIfConfirmed(BlockType blockType, Block block) {
		if (blockType == BlockType.ConfirmedBlock && _rewardedBlockHashes.add(block.getHash())) {
			_blockRewardMonitor.recordBlockReward(block);
		}
	}

	@Override
	public boolean shouldTerminate() {
		if (_inactivityThresholdCondition.hasProlongedInactivityExceeded()) {
			return true;
		}

		return _maxBlockchainLengthCondition.hasLengthExceeded();
	}

	public void setSimulationClock(SimulationClock simulationClock) {
		_inactivityThresholdCondition.setSimulationClock(simulationClock);
	}

	public int getTotalRewards() {
		return _blockRewardMonitor.getTotalRewards();
	}

	public int getRewardsForNode(String nodeId) {
		return _blockRewardMonitor.getRewardsForNode(nodeId);
	}
}
