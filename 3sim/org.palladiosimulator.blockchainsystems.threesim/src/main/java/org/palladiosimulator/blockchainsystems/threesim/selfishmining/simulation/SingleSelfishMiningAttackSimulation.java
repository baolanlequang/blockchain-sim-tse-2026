package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;

public class SingleSelfishMiningAttackSimulation {

	private final ThreesimBlockchainSystemFactory _blockchainSystemFactory;
	private final LogOutputProvider _logOutputProvider;
	private final long _maximumBlockchainLength;

	public SingleSelfishMiningAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			long maximumBlockchainLength) {
		_blockchainSystemFactory = blockchainSystemFactory;
		_logOutputProvider = logOutputProvider;
		_maximumBlockchainLength = maximumBlockchainLength;
	}

	public SelfishMiningSimulationRoundResult run() {
		BlockchainSystem blockchainSystem = _blockchainSystemFactory.createBlockchainSystem();

		SelfishMiningAttackSimulationRound simulationRound = new SelfishMiningAttackSimulationRound(
				blockchainSystem,
				_logOutputProvider.getLogOutputs(),
				_maximumBlockchainLength,
				_blockchainSystemFactory.getBlockchainSystemSpecification().getMeanBlockTime());

		return simulationRound.run();
	}
}
