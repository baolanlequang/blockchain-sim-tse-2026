package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput;
//import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemFactory;
//import org.palladiosimulator.blockchainsystems.doublespending.simulation.logoutputs.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;

public class MonteCarloDoubleSpendingAttackSimulation {

	private final ThreesimBlockchainSystemFactory _blockchainSystemFactory;
	private final LogOutputProvider _logOutputProvider;
	private final SimulationRoundInterpretation _simulationRoundInterpretation;
	private final MonteCarloSimulationProgressMonitor _simulationProgressMonitor;
	private final long _maximumBlockchainLength;
	private final int _numberOfSimulationRounds;
	
	public MonteCarloDoubleSpendingAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			SimulationRoundInterpretation simulationRoundInterpretation,
			MonteCarloSimulationProgressMonitor simulationProgressMonitor,
			long maximumBlockchainLength,
			int numberOfSimulationRounds) {
		_blockchainSystemFactory = blockchainSystemFactory;
		_logOutputProvider = logOutputProvider;
		_simulationRoundInterpretation = simulationRoundInterpretation;
		_simulationProgressMonitor = simulationProgressMonitor;
		_maximumBlockchainLength = maximumBlockchainLength;
		_numberOfSimulationRounds = numberOfSimulationRounds;
	}
	
	public MonteCarloDoubleSpendingAttackSimulationResult run() {
//		_simulationProgressMonitor.onSimulationStarted(_numberOfSimulationRounds);
		System.out.println("simulation started with " + _numberOfSimulationRounds + " round(s)");
		
		List<InterpretedResult> results = Stream.iterate(0, n -> n + 1)
			.parallel()
			.limit(_numberOfSimulationRounds)
			.map(x -> performSimulationRun())
			.map(x -> _simulationRoundInterpretation.interpretRoundResult(x))
			.collect(Collectors.toList());
		
//		_simulationProgressMonitor.onSimulationFinished();
//		System.out.println("simulation finished");
		
		return new MonteCarloDoubleSpendingAttackSimulationResult(
				results.stream().filter(x -> x == InterpretedResult.AttackerWon).count(),
				results.stream().filter(x -> x == InterpretedResult.SystemWon).count(),
				results.stream().filter(x -> x == InterpretedResult.Unambiguous).count());
	}
	
	private SimulationRoundResult performSimulationRun() {
		BlockchainSystem blockchainSystem = _blockchainSystemFactory.createBlockchainSystem();
		
		// Create simulation round
		DoubleSpendingAttackSimulationRound simulationRound = new DoubleSpendingAttackSimulationRound(
				blockchainSystem,
				_logOutputProvider.getLogOutputs(),
				_maximumBlockchainLength,
				_blockchainSystemFactory.getBlockchainSystemSpecification().getMeanBlockTime());
		
		//Run simulation
		SimulationRoundResult result = simulationRound.run();
		
//		_simulationProgressMonitor.onSimulationRoundFinished();
//		System.out.println("simulation finished");
		
		return result;
	}
}
