package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;

public class SingleDoubleSpendingAttackSimulation {

	private final ThreesimBlockchainSystemFactory _blockchainSystemFactory;
	private final LogOutputProvider _logOutputProvider;
	private final SimulationRoundInterpretation _simulationRoundInterpretation;
	private final long _maximumBlockchainLength;
	
	public SingleDoubleSpendingAttackSimulation(
			ThreesimBlockchainSystemFactory blockchainSystemFactory,
			LogOutputProvider logOutputProvider,
			SimulationRoundInterpretation simulationRoundInterpretation,
			long maximumBlockchainLength) {
		_blockchainSystemFactory = blockchainSystemFactory;
		_logOutputProvider = logOutputProvider;
		_simulationRoundInterpretation = simulationRoundInterpretation;
		_maximumBlockchainLength = maximumBlockchainLength;
	}
	
	public InterpretedResult run() {
		// Create blockchain system
		BlockchainSystem blockchainSystem = _blockchainSystemFactory.createBlockchainSystem();
		
		// Create simulation round
		DoubleSpendingAttackSimulationRound simulationRound = new DoubleSpendingAttackSimulationRound(
				blockchainSystem,
				_logOutputProvider.getLogOutputs(),
				_maximumBlockchainLength,
				_blockchainSystemFactory.getBlockchainSystemSpecification().getMeanBlockTime());
		
		//Run simulation
		SimulationRoundResult roundResult = simulationRound.run();
		return _simulationRoundInterpretation.interpretRoundResult(roundResult);
	}
}
