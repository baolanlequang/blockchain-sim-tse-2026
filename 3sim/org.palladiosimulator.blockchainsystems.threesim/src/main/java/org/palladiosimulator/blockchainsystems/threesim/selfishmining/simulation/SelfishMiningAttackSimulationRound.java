package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock;
import org.palladiosimulator.blockchainsystems.core.common.SimulationContextImpl;
import org.palladiosimulator.blockchainsystems.core.eventcoordination.EventCoordinatorImpl;
import org.palladiosimulator.blockchainsystems.core.simulation.termination.InActivityThresholdCondition;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLoggerContainerImpl;
import org.palladiosimulator.blockchainsystems.threesim.selfishmining.monitoring.SelfishMiningSimulationMonitor;
import org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation.termination.LongestChainExceededMaxLengthCondition;

public class SelfishMiningAttackSimulationRound {

	private final SimulationClock _clock;
	private final SelfishMiningSimulationMonitor _monitor;
	private final EventCoordinatorImpl _eventCoordinator;
	private final TraceEventLoggerContainerImpl _traceEventLoggerContainer;

	private final SimulationContextImpl _context;

	private final Set<TraceEventLogOutput> _logOutputs;

	private final BlockchainSystem _blockchainSystem;

	public SelfishMiningAttackSimulationRound(
			BlockchainSystem blockchainSystem,
			Set<TraceEventLogOutput> logOutputs,
			long maxBlockchainLength,
			double meanBlockTime) {
		_clock = new SimulationClock();

		InActivityThresholdCondition inActivityThresholdCondition = new InActivityThresholdCondition(meanBlockTime);
		inActivityThresholdCondition.setSimulationClock(_clock);

		_monitor = new SelfishMiningSimulationMonitor(
				new LongestChainExceededMaxLengthCondition(maxBlockchainLength),
				inActivityThresholdCondition);

		_eventCoordinator = new EventCoordinatorImpl(_clock, _monitor);
		_traceEventLoggerContainer = new TraceEventLoggerContainerImpl();

		_context = new SimulationContextImpl(_eventCoordinator, _clock, _traceEventLoggerContainer);

		_logOutputs = logOutputs;
		_blockchainSystem = blockchainSystem;

		_traceEventLoggerContainer.addSubscriber(_monitor);
		_logOutputs.forEach(x -> _traceEventLoggerContainer.addSubscriber(x));
	}

	public SelfishMiningSimulationRoundResult run() {
		_logOutputs.forEach(TraceEventLogOutput::initialize);

		_blockchainSystem.initialize(_context);
		_eventCoordinator.processEvents();
		_blockchainSystem.cleanup();

		_logOutputs.forEach(TraceEventLogOutput::cleanUp);

		List<AttackerNodeResult> attackerNodes = _blockchainSystem.getNodes().stream()
				.filter(node -> node.getBehavior() instanceof SelfishMiningNodeBehavior)
				.map(node -> new AttackerNodeResult(
						node.getId(),
						node.getResourcePower(),
						_monitor.getRewardsForNode(node.getId())))
				.sorted((a, b) -> a.nodeId().compareTo(b.nodeId()))
				.collect(Collectors.toList());

		int attackerRewards = attackerNodes.stream().mapToInt(AttackerNodeResult::rewards).sum();

		double totalHashPower = _blockchainSystem.getNodes().stream()
				.mapToDouble(BlockchainSystemNode::getResourcePower)
				.sum();

		double attackerHashPower = attackerNodes.stream().mapToDouble(AttackerNodeResult::hashPower).sum();

		double attackerHashPowerFraction = totalHashPower == 0.0 ? 0.0 : attackerHashPower / totalHashPower;

		return new SelfishMiningSimulationRoundResult(
				attackerRewards,
				_monitor.getTotalRewards(),
				attackerHashPowerFraction,
				attackerNodes);
	}
}
