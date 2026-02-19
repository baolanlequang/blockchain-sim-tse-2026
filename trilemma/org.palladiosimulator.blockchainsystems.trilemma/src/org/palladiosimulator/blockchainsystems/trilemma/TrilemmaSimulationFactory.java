package org.palladiosimulator.blockchainsystems.trilemma;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology;

import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.Simulation;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult;

import org.palladiosimulator.blockchainsystems.plugin.logging.LogOutputProviderImpl;
import org.palladiosimulator.blockchainsystems.plugin.simulation.MonteCarloSimulationProgressMonitorAdapter;

import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit.ExplicitNetworkBlockchainSystemFactory;
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimMonteCarloSimulation;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSingleSimulation;
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters;

public class TrilemmaSimulationFactory implements Simulation {

    private final Simulation simulation;

    public TrilemmaSimulationFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

        ThreesimBlockchainSystemFactory blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration);
        blockchainSystemFactory.createBlockchainSystem();
        

        // ✅ FIX: Threesim expects int, SimulationParameters returns long
        int maxAllowedBlockchainLength =
                Math.toIntExact(simulationParameters.getMaxAllowedBlockchainLength());

        ThreesimSimulationParameters threesimSimulationParameters =
                getThreesimSimulationParametersFromConfiguration(configuration);

        LogOutputProviderImpl logOutputProvider;
        try {
            logOutputProvider =
                    LogOutputProviderImpl.Companion.fromLaunchConfiguration(
                            ThreesimSerializers.INSTANCE.getJson(), null);
        } catch (CoreException e) {
            logOutputProvider = null;
            e.printStackTrace();
        }

        if (simulationParameters instanceof MonteCarloSimulationParameters) {

            MonteCarloSimulationProgressMonitorAdapter progressMonitor =
                    new MonteCarloSimulationProgressMonitorAdapter(null);

            this.simulation =
                    new ThreesimMonteCarloSimulation(
                            progressMonitor,
                            blockchainSystemFactory,
                            logOutputProvider,
                            maxAllowedBlockchainLength,
                            (MonteCarloSimulationParameters) simulationParameters,
                            threesimSimulationParameters
                    );

        } else {

            this.simulation =
                    new ThreesimSingleSimulation(
                            blockchainSystemFactory,
                            logOutputProvider,
                            maxAllowedBlockchainLength,
                            (SingleSimulationParameters) simulationParameters,
                            threesimSimulationParameters
                    );
        }
    }

    @Override
    public SimulationResult run() {
        return simulation.run();
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
                    false);
        }

        if (networkTopology instanceof ExplicitNetworkTopology) {
            return new ExplicitNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ExplicitNetworkTopology) networkTopology,
                    false);
        }

        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());
    }
}
