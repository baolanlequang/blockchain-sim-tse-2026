package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.Simulation;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult;
import org.palladiosimulator.blockchainsystems.plugin.logging.LogOutputProviderImpl;
import org.palladiosimulator.blockchainsystems.plugin.simulation.MonteCarloSimulationProgressMonitorAdapter;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSingleSimulation;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit.ExplicitNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimMonteCarloSimulation;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters;

public class TrilemmaSimulationFactory implements Simulation {
	
	private Simulation simulation;
	
	public TrilemmaSimulationFactory() {
		
	}
	
	public TrilemmaSimulationFactory(SimulationParameters simulationParameters, Map<String, String> configuration) {
		var blockchainSystemFactory = createBlockchainSystemFactory(simulationParameters);
		var maxAllowedBlockchainLength = simulationParameters.getMaxAllowedBlockchainLength();
		var threesimSimulationParameters = getThreesimSimulationParametersFromConfiguration(configuration);
		
		System.out.println("simulationParameters " + simulationParameters);
		LogOutputProviderImpl logOutputProvider;
		try {
			logOutputProvider = LogOutputProviderImpl.Companion.fromLaunchConfiguration(ThreesimSerializers.INSTANCE.getJson(), null);
		} catch (CoreException e) {
			logOutputProvider = null;
			e.printStackTrace();
		}
		
		if (simulationParameters instanceof MonteCarloSimulationParameters) {
			var progressMonitor = new MonteCarloSimulationProgressMonitorAdapter(null);
			simulation = new ThreesimMonteCarloSimulation(progressMonitor, blockchainSystemFactory, logOutputProvider, 
					maxAllowedBlockchainLength, (MonteCarloSimulationParameters) simulationParameters, 
					threesimSimulationParameters);
			
		} else {
			simulation = new ThreesimSingleSimulation(
					blockchainSystemFactory, logOutputProvider, 
					maxAllowedBlockchainLength, (SingleSimulationParameters) simulationParameters, 
					threesimSimulationParameters);
		}
		
		
	}

	@Override
	public SimulationResult run() {
		return simulation.run();
	}
	
	private ThreesimSimulationParameters getThreesimSimulationParametersFromConfiguration(Map<String, String> configuration) {
		var failureThroughputThreshold = Double.parseDouble(configuration.getOrDefault("failureThroughputThreshold", "1.0"));
		var shannonEntropyK = Double.parseDouble(configuration.getOrDefault("shannonEntropyK", "1.0"));
		var nakamotoCoefficientThreshold = Double.parseDouble(configuration.getOrDefault("nakamotoCoefficientThreshold", "50.0"));
		var reliabilityObservationTimespan = Double.parseDouble(configuration.getOrDefault("reliabilityObservationTimespan", "24.0"));
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
		} else if (networkTopology instanceof ExplicitNetworkTopology) { 
			var blockchainFactory = new ExplicitNetworkBlockchainSystemFactory(designBlockchainSystem, (ExplicitNetworkTopology) networkTopology);
			return blockchainFactory;
		}
		return null;
	}

}
