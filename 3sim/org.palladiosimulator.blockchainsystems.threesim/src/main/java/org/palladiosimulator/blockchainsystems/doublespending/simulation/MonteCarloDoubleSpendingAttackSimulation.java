package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
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
	private final int _concurrency;

	public MonteCarloDoubleSpendingAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			SimulationRoundInterpretation simulationRoundInterpretation,
			MonteCarloSimulationProgressMonitor simulationProgressMonitor,
			long maximumBlockchainLength,
			int numberOfSimulationRounds) {
		this(blockchainSystemFactory, logOutputProvider, simulationRoundInterpretation,
				simulationProgressMonitor, maximumBlockchainLength, numberOfSimulationRounds,
				Runtime.getRuntime().availableProcessors());
	}

	public MonteCarloDoubleSpendingAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			SimulationRoundInterpretation simulationRoundInterpretation,
			MonteCarloSimulationProgressMonitor simulationProgressMonitor,
			long maximumBlockchainLength,
			int numberOfSimulationRounds,
			int concurrency) {
		_blockchainSystemFactory = blockchainSystemFactory;
		_logOutputProvider = logOutputProvider;
		_simulationRoundInterpretation = simulationRoundInterpretation;
		_simulationProgressMonitor = simulationProgressMonitor;
		_maximumBlockchainLength = maximumBlockchainLength;
		_numberOfSimulationRounds = numberOfSimulationRounds;
		_concurrency = Math.max(1, concurrency);
	}
	
	public MonteCarloDoubleSpendingAttackSimulationResult run() {
//		_simulationProgressMonitor.onSimulationStarted(_numberOfSimulationRounds);
		System.out.println("simulation started with " + _numberOfSimulationRounds + " round(s)");

		if (_blockchainSystemFactory.getBlockchainSystemSpecification().getNumberOfAttacker() <= 0) {
			System.out.println("simulation stopped because there is no attacker");
			return new MonteCarloDoubleSpendingAttackSimulationResult(
					0, 0, _numberOfSimulationRounds
			);
		}
		
		// Each round holds a full blockchain system; cap in-flight rounds to bound peak memory.
		// Default is available CPU cores; override via configuration key "monteCarloConcurrency".
		ExecutorService executor = Executors.newFixedThreadPool(_concurrency);

		List<Future<DoubleSpendingSimulationRoundResult>> futures =
			Stream.iterate(0, n -> n + 1)
				.limit(_numberOfSimulationRounds)
				.map(x -> executor.submit(this::performSimulationRun))
				.collect(Collectors.toList());

		executor.shutdown();

		List<InterpretedResult> results = futures.stream()
			.map(f -> {
				try { return f.get(); }
				catch (Exception e) { Thread.currentThread().interrupt(); return null; }
			})
			.filter(Objects::nonNull)
			.map(x -> _simulationRoundInterpretation.interpretRoundResult(x))
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

//		_simulationProgressMonitor.onSimulationFinished();
//		System.out.println("simulation finished");

		List<String> roundResultNames = results.stream()
				.map(Enum::name)
				.collect(Collectors.toList());

		return new MonteCarloDoubleSpendingAttackSimulationResult(
				results.stream().filter(x -> x == InterpretedResult.AttackerWon).count(),
				results.stream().filter(x -> x == InterpretedResult.SystemWon).count(),
				results.stream().filter(x -> x == InterpretedResult.Unambiguous).count(),
				roundResultNames);
	}
	
	private DoubleSpendingSimulationRoundResult performSimulationRun() {
		BlockchainSystem blockchainSystem = _blockchainSystemFactory.createBlockchainSystem();
		
		// Create simulation round
		DoubleSpendingAttackSimulationRound simulationRound = new DoubleSpendingAttackSimulationRound(
				blockchainSystem,
				_logOutputProvider.getLogOutputs(),
				_maximumBlockchainLength,
				_blockchainSystemFactory.getBlockchainSystemSpecification().getMeanBlockTime());
		
		//Run simulation
		DoubleSpendingSimulationRoundResult result = simulationRound.run();
		
//		_simulationProgressMonitor.onSimulationRoundFinished();
//		System.out.println("simulation finished");
		
		return result;
	}
}
