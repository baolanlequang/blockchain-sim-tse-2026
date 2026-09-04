/**
 * RESPONSIBILITY:
 * - Initializes the standalone Palladio/Threesim environment.
 * - Executes a single simulation run and serializes the result to JSON.
 *
 * KEY CHANGE:
 * - Each experiment now produces its own result file containing:
 *   (1) config_id,
 *   (2) full input configuration,
 *   (3) raw simulation results.
 *
 * WHY THIS WAS NECESSARY:
 * - Guarantees traceability and reproducibility of every experiment.
 */


package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers;
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationResultSerializer;
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;

public class BlockchainTrilemmaStandalone {

    private final Logger logger =
            Logger.getLogger(BlockchainTrilemmaStandalone.class);

    private final String modelProjectName;
    private final Class<?> modelProjectActivator;

    public BlockchainTrilemmaStandalone(String modelProjectName,
                                        Class<?> modelProjectActivator) {
        this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;

        // The standalone/fat-JAR runner is intentionally independent of an
        // Eclipse launch configuration. Configure a minimal console appender
        // only when no Log4j configuration has been installed already. This
        // removes the misleading "No appenders" warning without overriding a
        // user-supplied logging configuration.
        if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            BasicConfigurator.configure();
        }
    }

    public boolean initAnalysis() {
        EcorePlugin.ExtensionProcessor.process(null);
        return initStandalone();
    }

/**
 * Runs one simulation and writes a dedicated JSON file for this configuration.
 * Output filename includes config_id to avoid collisions between runs.
 */

    public void runSimulation(Map<String, String> configuration, int runId) {
    	
    	var startTime = System.nanoTime();
    	
    	Runtime runtime = Runtime.getRuntime();
    	runtime.gc();
    	long before = runtime.totalMemory() - runtime.freeMemory();

        var simulationParameters =
                getSimulationParametersFromConfiguration(configuration);

        var simulationFactory =
                new TrilemmaSimulationFactory(simulationParameters, configuration, runId);

        var result = simulationFactory.run();

        var serializer =
                new ThreesimSimulationResultSerializer(
                        ThreesimSerializers.INSTANCE.getJson());

        String simulationJson = serializer.serialize(result);
        
        var stopTime = System.nanoTime();
        
        long after = runtime.totalMemory() - runtime.freeMemory();
        var memoryUsed = (after - before) / (1024 * 1024);

        Map<String, Object> finalResult = new LinkedHashMap<>();
        finalResult.put("runId", runId);
        finalResult.put("config_id", configuration.get("config_id"));
        finalResult.put("inputParameters", configuration);
        finalResult.put("simulationResult",
                com.google.gson.JsonParser.parseString(simulationJson));
        finalResult.put("startSimulationTime", startTime);
        finalResult.put("stopSimulationTime", stopTime);
        finalResult.put("simulationTime", stopTime - startTime);
        finalResult.put("memoryUsed", memoryUsed); // in MB

        String jsonResult = new com.google.gson.GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(finalResult);

        try {
            Path outputFile = createOutputPath(configuration.get("config_id"), runId);
            Files.createDirectories(outputFile.getParent());

            try (BufferedWriter writer =
                         Files.newBufferedWriter(outputFile)) {
                writer.write(jsonResult);
            }

            System.out.println("✔ Result saved: "
                    + outputFile.toAbsolutePath());

        } catch (IOException e) {
            logger.error("Failed to write simulation result", e);
            // A simulation whose result cannot be persisted is not a successful
            // scientific execution. Propagate the failure so the batch runner
            // can retain a FAILED audit record and return a non-zero exit code.
            throw new IllegalStateException("Simulation completed but its result JSON could not be written", e);
        }

    }

/**
 * Builds a unique, human-readable result filename per configuration.
 * Keeps results colocated with the corresponding model folder.
 */

    private Path createOutputPath(String configId, int runId) {
        // Name results by config_id, which is globally unique across the whole CSV. runId
        // restarts at 1 in every jar invocation, so a runId-based name would collide when the
        // 500 configs are split across SLURM array tasks. Fall back to runId only if config_id
        // is somehow absent.
        String key = (configId == null || configId.isBlank()) ? ("run_" + runId) : ("config_" + configId);
        return Paths.get("result_trilemma")
                .resolve("result_" + key + ".json");
    }

    private boolean initStandalone() {
        try {
            StandaloneInitializerBuilder.builder()
                    .registerProjectURI(
                            this.modelProjectActivator,
                            this.modelProjectName)
                    .registerProjectURI(org.glassfish.hk2.osgiresourcelocator.Activator.class,
                            "org.glassfish.hk2.osgi-resource-locator")
                    .build()
                    .init();

            logger.info("Successfully initialized standalone environment.");
            return true;

        } catch (StandaloneInitializationException e) {
        	e.printStackTrace();
            logger.error("Unable to initialize standalone environment.", e);
            return false;
        }
    }

    private SimulationParameters getSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        // For externally sampled batches, preserve the legacy experiment-level
        // simulationType in the audit record while allowing the batch runner to
        // request one engine execution per explicit CSV/manifest row.
        String effectiveSimulationType = configuration.getOrDefault(
                "engineSimulationType",
                configuration.getOrDefault("simulationType", "Single"));
        SimulationType simulationType = SimulationType.Single;
        if ("Monte-Carlo".equals(effectiveSimulationType)) {
            simulationType = SimulationType.MonteCarlo;
        }

        int maxAllowedBlockchainLength =
                Integer.parseInt(
                        configuration.getOrDefault(
                                "maxAllowedBlockchainLength", "30"));

        int numberOfMonteCarloRounds =
                Integer.parseInt(
                        configuration.getOrDefault(
                                "engineNumberOfMonteCarloRounds",
                                configuration.getOrDefault("numberOfMonteCarloRounds", "1")));

        String blockchainSystemModelFilePath =
                configuration.getOrDefault(
                        "blockchainSystemModelFilePath", "");

        /*
         * RESULT-AUDIT CONSISTENCY:
         * Refined manifests already contain the deterministic derived attacker
         * count as `number_of_attackers`. The previous standalone runner
         * hard-coded zero into SimulationParameters even though the model loader
         * correctly created adversarial nodes. This produced contradictory JSON:
         * legacy simulationParameters.numberOfAttacker=0 while
         * refinedExecutionAudit.creation.numberOfAttackers was non-zero.
         *
         * Use the manifest value here so the legacy metadata agrees with the
         * actual refined execution. The model loader remains authoritative for
         * constructing attacker behavior; this change fixes metadata only.
         */
        String attackerCountRaw = configuration.getOrDefault(
                "number_of_attackers",
                configuration.getOrDefault("numberOfAttacker", "0"));
        int numberOfAttacker = Integer.parseInt(attackerCountRaw.trim());
        if (numberOfAttacker < 0) {
            throw new IllegalArgumentException(
                    "number_of_attackers must be >= 0, got " + numberOfAttacker);
        }

        return (simulationType == SimulationType.MonteCarlo)
                ? new MonteCarloSimulationParameters(
                maxAllowedBlockchainLength,
                numberOfMonteCarloRounds,
                blockchainSystemModelFilePath,
                numberOfAttacker)
                : new SingleSimulationParameters(
                maxAllowedBlockchainLength,
                blockchainSystemModelFilePath,
                numberOfAttacker);
    }
}
