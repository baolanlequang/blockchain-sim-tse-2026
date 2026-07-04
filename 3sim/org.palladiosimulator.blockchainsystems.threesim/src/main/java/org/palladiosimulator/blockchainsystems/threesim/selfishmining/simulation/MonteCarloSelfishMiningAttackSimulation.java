package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;

public class MonteCarloSelfishMiningAttackSimulation {

	private final ThreesimBlockchainSystemFactory _blockchainSystemFactory;
	private final LogOutputProvider _logOutputProvider;
	private final long _maximumBlockchainLength;
	private final int _numberOfSimulationRounds;
	private final int _concurrency;

	// Counts finished rounds across the parallel workers so progress can be logged per round.
	private final AtomicInteger _completedRounds = new AtomicInteger(0);

	public MonteCarloSelfishMiningAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			long maximumBlockchainLength,
			int numberOfSimulationRounds,
			int concurrency) {
		_blockchainSystemFactory = blockchainSystemFactory;
		_logOutputProvider = logOutputProvider;
		_maximumBlockchainLength = maximumBlockchainLength;
		_numberOfSimulationRounds = numberOfSimulationRounds;
		_concurrency = Math.max(1, concurrency);
	}

	public MonteCarloSelfishMiningAttackSimulationResult run() {
		System.out.println("selfish mining simulation started with " + _numberOfSimulationRounds + " round(s)");

		if (_blockchainSystemFactory.getBlockchainSystemSpecification().getNumberOfAttacker() <= 0) {
			System.out.println("simulation stopped because there is no attacker");
			return new MonteCarloSelfishMiningAttackSimulationResult(0, 0, 0.0, 0.0, List.of());
		}

		// Each round holds a full blockchain system; cap in-flight rounds to bound peak memory.
		ExecutorService executor = Executors.newFixedThreadPool(_concurrency);

		List<Future<SelfishMiningSimulationRoundResult>> futures =
			Stream.iterate(0, n -> n + 1)
				.limit(_numberOfSimulationRounds)
				.map(x -> executor.submit(this::performSimulationRun))
				.collect(Collectors.toList());

		executor.shutdown();

		List<SelfishMiningSimulationRoundResult> results = futures.stream()
			.map(f -> {
				try { return f.get(); }
				catch (Exception e) { Thread.currentThread().interrupt(); return null; }
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		long roundsSucceeded = results.stream().filter(SelfishMiningSimulationRoundResult::hasSucceeded).count();

		double averageRevenueShare = results.stream()
				.mapToDouble(SelfishMiningSimulationRoundResult::getAttackerRevenueShareFraction)
				.average()
				.orElse(0.0);

		double averageHashPower = results.stream()
				.mapToDouble(SelfishMiningSimulationRoundResult::attackerHashPowerFraction)
				.average()
				.orElse(0.0);

		return new MonteCarloSelfishMiningAttackSimulationResult(
				results.size(),
				roundsSucceeded,
				averageRevenueShare,
				averageHashPower,
				results);
	}

	private SelfishMiningSimulationRoundResult performSimulationRun() {
		BlockchainSystem blockchainSystem = _blockchainSystemFactory.createBlockchainSystem();

		SelfishMiningAttackSimulationRound simulationRound = new SelfishMiningAttackSimulationRound(
				blockchainSystem,
				_logOutputProvider.getLogOutputs(),
				_maximumBlockchainLength,
				_blockchainSystemFactory.getBlockchainSystemSpecification().getMeanBlockTime());

		SelfishMiningSimulationRoundResult result = simulationRound.run();

		int completed = _completedRounds.incrementAndGet();
		System.out.printf(
				"✔ Round %d/%d finished — attackerRewards=%d, totalRewards=%d, revenueShare=%.4f, hashPower=%.4f, succeeded=%b%n",
				completed,
				_numberOfSimulationRounds,
				result.attackerRewards(),
				result.totalRewards(),
				result.getAttackerRevenueShareFraction(),
				result.attackerHashPowerFraction(),
				result.hasSucceeded());

		return result;
	}
}
