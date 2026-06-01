package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock;
import org.palladiosimulator.blockchainsystems.core.common.SimulationContextImpl;
import org.palladiosimulator.blockchainsystems.core.eventcoordination.EventCoordinatorImpl;
import org.palladiosimulator.blockchainsystems.core.simulation.termination.InActivityThresholdCondition;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLoggerContainerImpl;
import org.palladiosimulator.blockchainsystems.doublespending.behavior.AttackerUtils;
import org.palladiosimulator.blockchainsystems.doublespending.monitoring.SimulationMonitor;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.LongestChainExceededMaxLengthCondition;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.SimulationWinnerVoter;

public class DoubleSpendingAttackSimulationRound {

	private final SimulationClock _clock;
	private final SimulationMonitor _monitor;
	private final EventCoordinatorImpl _eventCoordinator;
	private final TraceEventLoggerContainerImpl _traceEventLoggerContainer;
	
	private final SimulationContextImpl _context;
	
	private final Set<TraceEventLogOutput> _logOutputs;
	
	private final BlockchainSystem _blockchainSystem;
	
	public DoubleSpendingAttackSimulationRound(
			BlockchainSystem blockchainSystem,
			Set<TraceEventLogOutput> logOutputs,
			long maxBlockchainLength,
			double meanBlockTime) {
		_clock = new SimulationClock();
		InActivityThresholdCondition inActivityThresholdCondition = new InActivityThresholdCondition(meanBlockTime);
		inActivityThresholdCondition.setSimulationClock(_clock);
		_monitor = new SimulationMonitor(new LongestChainExceededMaxLengthCondition(maxBlockchainLength), inActivityThresholdCondition);
		_eventCoordinator = new EventCoordinatorImpl(_clock, _monitor);
		_traceEventLoggerContainer = new TraceEventLoggerContainerImpl();
		
		_context = new SimulationContextImpl(
				_eventCoordinator,
				_clock,
				_traceEventLoggerContainer);

		AttackerUtils.simulationContext = _context;
		
		_logOutputs = logOutputs;
		_blockchainSystem = blockchainSystem;
		
		_traceEventLoggerContainer.addSubscriber(_monitor);
		_logOutputs.forEach(x -> _traceEventLoggerContainer.addSubscriber(x));
	}
	
	public DoubleSpendingSimulationRoundResult run() {
		//Initialize log outputs
		_logOutputs.forEach(x -> x.initialize());
		
		//Initialize simulation monitor
		_monitor.initializeNodes(_blockchainSystem.getNodes());

		// Initialize blockchain system
		_blockchainSystem.initialize(_context);

		// Start processing events - processing will stop if termination condition is met
		_eventCoordinator.processEvents();
		
		// Clean up the blockchain system
		_blockchainSystem.cleanup();

		// Clean up the log ouptputs
		_logOutputs.forEach(x -> x.cleanUp());

		Set<SimulationWinnerVoter> winnerVoters = _monitor.getWinnerVoters();
		return DoubleSpendingSimulationRoundResult.create(
				winnerVoters
					.stream()
					.map(x -> x.getWinnerVote())
					.collect(Collectors.toSet()),
				_monitor.getForkedBlocks().size());
	}


}
