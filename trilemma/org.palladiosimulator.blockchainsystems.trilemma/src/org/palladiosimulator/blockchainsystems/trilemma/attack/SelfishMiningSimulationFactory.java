package org.palladiosimulator.blockchainsystems.trilemma.attack;

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

public class SelfishMiningSimulationFactory {

    private MonteCarloDoubleSpendingAttackSimulation montecarloSimulation = null;
    private SingleDoubleSpendingAttackSimulation singleSimulation =  null;

    public SelfishMiningSimulationFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

    	ThreesimBlockchainSystemFactory blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration);

        LogOutputAttackProviderImpl logOutputProvider = new LogOutputAttackProviderImpl(false, false, "", false, "", 0, "", "","");

        if (simulationParameters instanceof MonteCarloSimulationParameters parameter) {

            MonteCarloSimulationProgressMonitorAdapter progressMonitor =
                    new MonteCarloSimulationProgressMonitorAdapter(null);

            montecarloSimulation = new MonteCarloDoubleSpendingAttackSimulation(
            		blockchainSystemFactory,
            		logOutputProvider,
            		new SimulationRoundInterpretationImpl(),
            		null,
            		parameter.getMaxAllowedBlockchainLength(),
            		parameter.getNumberOfMonteCarloRounds()
            		);

        } else {
        	singleSimulation = new SingleDoubleSpendingAttackSimulation(
        			blockchainSystemFactory,
            		logOutputProvider,
            		new SimulationRoundInterpretationImpl(),
            		simulationParameters.getMaxAllowedBlockchainLength());

        }
    }

    public void run() {
    	if (singleSimulation != null) {
    		var result = singleSimulation.run();
    		System.out.println("Selfish mining attack Single simulation result: " + result);
    	} else if (montecarloSimulation != null) {
    		MonteCarloDoubleSpendingAttackSimulationResult result = montecarloSimulation.run();
    		System.out.println("Selfish mining attack Montecarlo Simulation result: " + result);
    	}
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
            Map<String, String> configuration) {

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
                    true);
        }

        if (networkTopology instanceof ExplicitNetworkTopology) {
            return new ExplicitNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ExplicitNetworkTopology) networkTopology,
                    true);
        }

        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());
    }
}
