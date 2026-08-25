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
            Map<String, String> configuration,
            int runId) {

        ThreesimBlockchainSystemFactory blockchainSystemFactory =
                createBlockchainSystemFactory(simulationParameters, configuration, runId);

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

            int concurrency = parseConcurrency(configuration);

            this.simulation =
                    new ThreesimMonteCarloSimulation(
                            progressMonitor,
                            blockchainSystemFactory,
                            logOutputProvider,
                            maxAllowedBlockchainLength,
                            (MonteCarloSimulationParameters) simulationParameters,
                            threesimSimulationParameters,
                            concurrency
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
     * Number of Monte-Carlo rounds to run in parallel at any one time. Remaining rounds
     * are queued and started as running ones finish, so total rounds are unchanged - this
     * only bounds peak memory (each round holds a full blockchain system). Configured via
     * "numberOfParallelTasks" in configuration.json; the configured value is authoritative
     * (not capped at CPU count) so it can be lowered below the core count to avoid
     * out-of-memory. Falls back to available CPU cores when unset or invalid.
     */
    private static int parseConcurrency(Map<String, String> configuration) {
        int cores = Runtime.getRuntime().availableProcessors();

        String raw = configuration.getOrDefault(
                "engineNumberOfParallelTasks",
                configuration.get("numberOfParallelTasks"));
        if (raw == null || raw.isBlank()) {
            return cores;
        }
        try {
            int requested = Integer.parseInt(raw.trim());
            return Math.max(1, requested);
        } catch (NumberFormatException e) {
            return cores;
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

        int warmupBlocksPerValidator = Integer.parseInt(configuration.getOrDefault(
                "warmup_blocks_per_validator",
                configuration.getOrDefault("warmupBlocksPerValidator", "0")));
        int measuredBlocksPerValidator = Integer.parseInt(configuration.getOrDefault(
                "measured_blocks_per_validator",
                configuration.getOrDefault("measuredBlocksPerValidator", "0")));
        double transactionDrainSeconds = Double.parseDouble(configuration.getOrDefault(
                "transaction_drain_seconds",
                configuration.getOrDefault("transactionDrainSeconds", "0")));
        long transactionDrainMillis = Math.round(transactionDrainSeconds * 1000.0);

        /*
         * Production results keep only sufficient transaction-follow-up
         * statistics. Full per-transaction observations are diagnostic-only and
         * must be explicitly requested because high-demand runs can contain
         * millions of measurement-window transactions.
         */
        boolean retainTransactionFollowUpObservations = Boolean.parseBoolean(
                configuration.getOrDefault("retainTransactionFollowUpObservations", "false"));

        return new ThreesimSimulationParameters(
                failureThroughputThreshold,
                shannonEntropyK,
                nakamotoCoefficientThreshold,
                reliabilityObservationTimespan,
                warmupBlocksPerValidator,
                measuredBlocksPerValidator,
                transactionDrainMillis,
                retainTransactionFollowUpObservations);
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
            long networkSeed = Long.parseLong(configuration.getOrDefault("network_seed", "0"));
            long eventSeed = Long.parseLong(configuration.getOrDefault("event_seed", "0"));
            double gamma = Double.parseDouble(configuration.getOrDefault("selfishMiningGamma", "0.5"));
            boolean hasAttackers = designBlockchainSystem.getSpecification().getNumberOfAttacker() > 0;

            return new ConnectedSubgraphNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ConnectedSubgraphsNetworkTopology) networkTopology,
                    hasAttackers,
                    runId,
                    gamma,
                    networkSeed,
                    eventSeed);
        }

        if (networkTopology instanceof ExplicitNetworkTopology) {
            // The revised TSE study itself uses ConnectedSubgraphsNetworkTopology,
            // so this branch is a legacy/non-study compatibility path. Keep it
            // seed- and attacker-consistent nevertheless so an explicit topology
            // cannot silently revert to the old unseeded/all-honest semantics.
            long networkSeed = Long.parseLong(configuration.getOrDefault("network_seed", "0"));
            long eventSeed = Long.parseLong(configuration.getOrDefault("event_seed", "0"));
            double gamma = Double.parseDouble(configuration.getOrDefault("selfishMiningGamma", "0.5"));
            boolean hasAttackers = designBlockchainSystem.getSpecification().getNumberOfAttacker() > 0;
            return new ExplicitNetworkBlockchainSystemFactory(
                    designBlockchainSystem,
                    (ExplicitNetworkTopology) networkTopology,
                    hasAttackers,
                    runId,
                    gamma,
                    networkSeed,
                    eventSeed);
        }

        throw new IllegalStateException(
                "Unsupported network topology: "
                        + networkTopology.getClass().getName());
    }
}
