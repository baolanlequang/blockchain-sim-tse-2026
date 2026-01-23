/**
 * RESPONSIBILITY:
 * - Constructs the appropriate Threesim simulation (Single or Monte-Carlo).
 * - Forwards the full configuration map to BlockchainSystemModelLoader
 *   so that CSV parameters override the EMF model before simulation.
 *
 * WHY THIS IS CORRECT:
 * - CSV parameters now genuinely affect the blockchain system.
 * - Factory construction matches the Palladio API used in this project.
 */

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

    /**
     * Constructor used by the batch runner.
     */
    public TrilemmaSimulationFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

        // ----------------------------------------------------
        // Create the blockchain system factory
        // CSV overrides are applied inside the model loader
        // ----------------------------------------------------
        ThreesimBlockchainSystemFactory blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration);

        int maxAllowedBlockchainLength =
                simulationParameters.getMaxAllowedBlockchainLength();

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

        // ----------------------------------------------------
        // Select Single vs Monte-Carlo simulation
        // ----------------------------------------------------
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

    /**
     * Reads threshold-like simulation parameters
     * (these are NOT part of the EMF model).
     */
    private ThreesimSimulationParameters getThreesimSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        double failureThroughputThreshold =
                Double.parseDouble(
                        configuration.getOrDefault(
                                "failureThroughputThreshold", "1.0"));

        double shannonEntropyK =
                Double.parseDouble(
                        configuration.getOrDefault(
                                "shannonEntropyK", "1.0"));

        double nakamotoCoefficientThreshold =
                Double.parseDouble(
                        configuration.getOrDefault(
                                "nakamotoCoefficientThreshold", "50.0"));

        double reliabilityObservationTimespan =
                Double.parseDouble(
                        configuration.getOrDefault(
                                "reliabilityObservationTimespan", "24.0"));

        return new ThreesimSimulationParameters(
                failureThroughputThreshold,
                shannonEntropyK,
                nakamotoCoefficientThreshold,
                reliabilityObservationTimespan);
    }

    /**
     * CRUCIAL METHOD:
     * Loads the blockchain system model AND applies CSV overrides
     * before creating the Threesim blockchain system factory.
     */
    private ThreesimBlockchainSystemFactory createBlockchainSystemFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

        // Loader is correct and must stay
        BlockchainSystemModelLoader designModelLoader =
                new BlockchainSystemModelLoader();

        // IMPORTANT FIX:
        // - Explicit type (not var) to avoid Object inference
        // - Pass configuration so CSV overrides are applied
        BlockchainSystem designBlockchainSystem =
                designModelLoader.load(
                        simulationParameters.getBlockchainSystemModelFilePath(),
                        configuration);

        // This must exist: topology determines which factory is valid
        var networkTopology =
                designBlockchainSystem.getNetwork().getTopology();

        // ----------------------------------------------------
        // CORRECT FACTORY CONSTRUCTION (constructor-based)
        // ----------------------------------------------------

        if (networkTopology instanceof ConnectedSubgraphsNetworkTopology) {

            return new ConnectedSubgraphNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ConnectedSubgraphsNetworkTopology) networkTopology);
        }

        if (networkTopology instanceof ExplicitNetworkTopology) {

            return new ExplicitNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ExplicitNetworkTopology) networkTopology);
        }

        // ----------------------------------------------------
        // No other topology types are supported by Threesim
        // ----------------------------------------------------
        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());

        /*
         * REMOVED (and why):
         *
         * ❌ No-arg constructors + setters such as:
         *
         *   new ConnectedSubgraphNetworkBlockchainSystemFactory();
         *   factory.setBlockchainSystem(...)
         *   factory.setNetworkTopology(...)
         *
         * These APIs DO NOT EXIST in this Palladio version.
         * Keeping them causes compilation errors.
         */
    }
}
