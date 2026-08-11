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

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Plugin;
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

    private String modelProjectName;
    private Class<? extends Plugin> modelProjectActivator;

    public BlockchainTrilemmaStandalone(String modelProjectName,
                                        Class<? extends Plugin> modelProjectActivator) {
        this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;
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

        String idKey = configuration.containsKey("pair_id") ? "pair_id" : "config_id";

        Map<String, Object> finalResult = new LinkedHashMap<>();
        finalResult.put("runId", runId);
        finalResult.put(idKey, configuration.get(idKey));
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
            Path outputFile = createOutputPath(idKey, configuration.get(idKey), runId);
            Files.createDirectories(outputFile.getParent());

            try (BufferedWriter writer =
                         Files.newBufferedWriter(outputFile)) {
                writer.write(jsonResult);
            }

            System.out.println("✔ Result saved: "
                    + outputFile.toAbsolutePath());

        } catch (IOException e) {
            logger.error("Failed to write simulation result", e);
        }

    }

/**
 * Builds a unique, human-readable result filename per configuration.
 * Keeps results colocated with the corresponding model folder.
 */

    private Path createOutputPath(String idKey, String idValue, int runId) {
        // Name results by pair_id/config_id, which is globally unique across the whole CSV.
        // runId restarts at 1 in every jar invocation, so a runId-based name would collide when
        // the configs/pairs are split across SLURM array tasks. Fall back to runId only if the
        // identifier is somehow absent. Prefix distinguishes pair_id- from config_id-keyed
        // results so nested and legacy runs never collide.
        String prefix = "pair_id".equals(idKey) ? "pair_" : "config_";
        String key = (idValue == null || idValue.isBlank()) ? ("run_" + runId) : (prefix + idValue);
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
        	System.out.println("test");
            logger.error("Unable to initialize standalone environment.", e);
            return false;
        }
    }

    private SimulationParameters getSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        SimulationType simulationType = SimulationType.Single;
        if ("Monte-Carlo".equals(configuration.getOrDefault("simulationType", ""))) {
            simulationType = SimulationType.MonteCarlo;
        }

        int maxAllowedBlockchainLength =
                Integer.parseInt(
                        configuration.getOrDefault(
                                "maxAllowedBlockchainLength", "30"));

        int numberOfMonteCarloRounds =
                Integer.parseInt(
                        configuration.getOrDefault(
                                "numberOfMonteCarloRounds", "1"));

        String blockchainSystemModelFilePath =
                configuration.getOrDefault(
                        "blockchainSystemModelFilePath", "");

        return (simulationType == SimulationType.MonteCarlo)
                ? new MonteCarloSimulationParameters(
                maxAllowedBlockchainLength,
                numberOfMonteCarloRounds,
                blockchainSystemModelFilePath,
                0)
                : new SingleSimulationParameters(
                maxAllowedBlockchainLength,
                blockchainSystemModelFilePath,
                0);
    }
}
