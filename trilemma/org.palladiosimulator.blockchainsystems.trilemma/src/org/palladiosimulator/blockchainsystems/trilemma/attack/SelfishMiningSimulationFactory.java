package org.palladiosimulator.blockchainsystems.trilemma.attack;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology;
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.MonteCarloDoubleSpendingAttackSimulation;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.MonteCarloDoubleSpendingAttackSimulationResult;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.SingleDoubleSpendingAttackSimulation;
import org.palladiosimulator.blockchainsystems.plugin.simulation.MonteCarloSimulationProgressMonitorAdapter;
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit.ExplicitNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters;
import org.palladiosimulator.blockchainsystems.trilemma.BlockchainSystemModelLoader;

import com.google.gson.Gson;

public class SelfishMiningSimulationFactory {

    private MonteCarloDoubleSpendingAttackSimulation montecarloSimulation = null;
    private SingleDoubleSpendingAttackSimulation singleSimulation =  null;

    public SelfishMiningSimulationFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration,
            int runId) {

    	ThreesimBlockchainSystemFactory blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration, runId);

        LogOutputAttackProviderImpl logOutputProvider = new LogOutputAttackProviderImpl(true, false, "", false, "", 0, "", "","", runId);

        if (simulationParameters instanceof MonteCarloSimulationParameters parameter) {

            MonteCarloSimulationProgressMonitorAdapter progressMonitor =
                    new MonteCarloSimulationProgressMonitorAdapter(null);

            montecarloSimulation = new MonteCarloDoubleSpendingAttackSimulation(
            		blockchainSystemFactory,
            		logOutputProvider,
            		new SimulationRoundInterpretationImpl(runId),
            		null,
            		parameter.getMaxAllowedBlockchainLength(),
            		parameter.getNumberOfMonteCarloRounds()
            		);

        } else {
        	singleSimulation = new SingleDoubleSpendingAttackSimulation(
        			blockchainSystemFactory,
            		logOutputProvider,
            		new SimulationRoundInterpretationImpl(runId),
            		simulationParameters.getMaxAllowedBlockchainLength());

        }
    }

    public String run() {
    	Gson gson = new Gson();
    	if (singleSimulation != null) {
    		var result = singleSimulation.run();
    		System.out.println("Selfish mining attack Single simulation result: " + result);
    		String jsonStr = gson.toJson(result);
    		return jsonStr;
    	} else if (montecarloSimulation != null) {
    		MonteCarloDoubleSpendingAttackSimulationResult result = montecarloSimulation.run();
    		
    		String jsonStr = gson.toJson(result);    		
    		System.out.println("Selfish mining attack Montecarlo Simulation result: " + jsonStr);
    		return jsonStr;
    	}
    	return null;
    }
    

    private ThreesimSimulationParameters getThreesimSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        double failureThroughputThreshold =
                Double.parseDouble(configuration.getOrDefault(
                        "failureThroughputThreshold", "1.0"));

        double shannonEntropyK =
                Double.parseDouble(configuration.getOrDefault(
                        "shannonEntropyK", "1.0"));

        double nakamotoCoefficientThreshold =
                Double.parseDouble(configuration.getOrDefault(
                        "nakamotoCoefficientThreshold", "50.0"));

        double reliabilityObservationTimespan =
                Double.parseDouble(configuration.getOrDefault(
                        "reliabilityObservationTimespan", "24.0"));

        return new ThreesimSimulationParameters(
                failureThroughputThreshold,
                shannonEntropyK,
                nakamotoCoefficientThreshold,
                reliabilityObservationTimespan);
    }

    private ThreesimBlockchainSystemFactory createBlockchainSystemFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration,
            int runId) {

        BlockchainSystemModelLoader loader =
                new BlockchainSystemModelLoader();

        BlockchainSystem designBlockchainSystem =
                loader.load(
                        simulationParameters.getBlockchainSystemModelFilePath(),
                        configuration);

        var networkTopology =
                designBlockchainSystem.getNetwork().getTopology();

        if (networkTopology instanceof ConnectedSubgraphsNetworkTopology) {
            return new ConnectedSubgraphNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ConnectedSubgraphsNetworkTopology) networkTopology,
                    true,
                    runId);
        }

        if (networkTopology instanceof ExplicitNetworkTopology) {
            return new ExplicitNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ExplicitNetworkTopology) networkTopology,
                    true,
                    runId);
        }

        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());
    }
}
