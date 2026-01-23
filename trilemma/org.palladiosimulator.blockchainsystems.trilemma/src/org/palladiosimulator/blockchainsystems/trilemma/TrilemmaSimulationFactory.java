/**
 * RESPONSIBILITY:
 * - Constructs the appropriate Threesim simulation (Single or Monte-Carlo).
 * - CRITICAL CHANGE: forwards the full configuration map to
 *   BlockchainSystemModelLoader so that CSV parameters override the model.
 *
 * WHY THIS WAS NECESSARY:
 * - Without this, the batch CSV had no effect on the underlying blockchain model.
 */


package org.palladiosimulator.blockchainsystems.trilemma;

import java.util.Map;
// fix "BlockchainSystemLoader" cannot be resolved to a type
import org.palladiosimulator.blockchainsystems.trilemma.BlockchainSystemModelLoader;
// end fix
import org.eclipse.core.runtime.CoreException;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology;
// fix the error:The method getNetwork() is undefined for the type Object
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
// end fix
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
     * Constructor used by your batch runner.
     */
    public TrilemmaSimulationFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

        // --- CREATE BLOCKCHAIN SYSTEM FACTORY (THIS IS WHERE CSV OVERRIDES ARE APPLIED) ---
        var blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration);

        var maxAllowedBlockchainLength =
                simulationParameters.getMaxAllowedBlockchainLength();

        var threesimSimulationParameters =
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

        // --- SELECT SINGLE OR MONTE-CARLO SIMULATION ---
        if (simulationParameters instanceof MonteCarloSimulationParameters) {

            var progressMonitor =
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
     * Reads threshold-like simulation parameters from configuration.json / CSV.
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
     * by passing the configuration map to BlockchainSystemModelLoader.
     */
	 
	 /**
 * IMPORTANT: the configuration map must be passed to the model loader so that
 * CSV parameters are injected into the EMF model before simulation.
 */

    private ThreesimBlockchainSystemFactory createBlockchainSystemFactory(
            SimulationParameters simulationParameters,
            Map<String, String> configuration) {

        final var designModelLoader =
                new BlockchainSystemModelLoader();

        // === IMPORTANT FIX: pass configuration so model overrides are applied ===
       // final var designBlockchainSystem =
         //       designModelLoader.load(
         //               simulationParameters.getBlockchainSystemModelFilePath(),
          //              configuration);

		// Fix the error: The method getNetwork() is undefined for the type object
		final BlockchainSystem designBlockchainSystem =
        designModelLoader.load(
                simulationParameters.getBlockchainSystemModelFilePath(),
                configuration);
		// end fix

        //final var networkTopology =
        //        designBlockchainSystem.getNetwork().getTopology();
		//
        // if (networkTopology instanceof ConnectedSubgraphsNetworkTopology) {
        //    return new ConnectedSubgraphNetworkBlockchainSystemFactory(
        //           designBlockchainSystem,
        //            (ConnectedSubgraphsNetworkTopology) networkTopology);
        //}

        //if (networkTopology instanceof ExplicitNetworkTopology) {
        //    return new ExplicitNetworkBlockchainSystemFactory(
        //           designBlockchainSystem,
        //           (ExplicitNetworkTopology) networkTopology);
        //}

		// Fix ConnectedSubgraphNetworkBlockchainSystemFactory(...) is undefined and ExplicitNetworkBlockchainSystemFactory(...) is undefined
				if (networkTopology instanceof ConnectedSubgraphsNetworkTopology topology) {
		
		    ConnectedSubgraphNetworkBlockchainSystemFactory factory =
		            new ConnectedSubgraphNetworkBlockchainSystemFactory();
		
		    factory.setBlockchainSystem(designBlockchainSystem);
		    factory.setNetworkTopology(topology);
		
		    return factory;
		}
		
		if (networkTopology instanceof ExplicitNetworkTopology topology) {
		
		    ExplicitNetworkBlockchainSystemFactory factory =
		            new ExplicitNetworkBlockchainSystemFactory();
		
		    factory.setBlockchainSystem(designBlockchainSystem);
		    factory.setNetworkTopology(topology);
		
		    return factory;
		}

		
		// End fix

        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());
    }
}
