package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.Simulation;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult;
import org.palladiosimulator.blockchainsystems.plugin.logging.LogOutputProviderImpl;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSingleSimulation;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters;

public class TrilemmaSimulationFactory implements Simulation {
	
	private Simulation simulation;
	
	public TrilemmaSimulationFactory() {
		
	}
	
	public TrilemmaSimulationFactory(SimulationParameters simulationParameters) {
		var blockchainSystemFactory = createBlockchainSystemFactory(simulationParameters);
		LogOutputProviderImpl logOutputProvider = null;
		var maxAllowedBlockchainLength = simulationParameters.getMaxAllowedBlockchainLength();
		var threesimSimulationParameters = getThreesimSimulationParametersFromConfiguration();
		
		simulation = new ThreesimSingleSimulation(
				blockchainSystemFactory, logOutputProvider, 
				maxAllowedBlockchainLength, (SingleSimulationParameters) simulationParameters, 
				threesimSimulationParameters);
		
		
	}

	@Override
	public SimulationResult run() {
		// TODO need to implement monte-carlo
		
		return simulation.run();
	}
	
	private ThreesimSimulationParameters getThreesimSimulationParametersFromConfiguration() {
		final var failureThroughputThreshold = 1.0; // TODO need to be implement
		final var shannonEntropyK = 1.0;
		final var nakamotoCoefficientThreshold = 50.0;
		final var reliabilityObservationTimespan = 0.000277778; // 1 second
		var threesimSimulationParameters = new ThreesimSimulationParameters(
				failureThroughputThreshold, shannonEntropyK,
				nakamotoCoefficientThreshold, reliabilityObservationTimespan);
		return threesimSimulationParameters;
	}
	
	private ThreesimBlockchainSystemFactory createBlockchainSystemFactory(SimulationParameters simulationParameters) {
		final var designModelLoader = new BlockchainSystemModelLoader();
		final var designBlockchainSystem = designModelLoader.load(simulationParameters.getBlockchainSystemModelFilePath());
		final var networkTopology = designBlockchainSystem.getNetwork().getTopology();
		
		if (networkTopology instanceof ConnectedSubgraphsNetworkTopology) { 
			var blockchainFactory = new ConnectedSubgraphNetworkBlockchainSystemFactory(designBlockchainSystem, (ConnectedSubgraphsNetworkTopology) networkTopology);
			return blockchainFactory;
		}
		return null;
	}

}
