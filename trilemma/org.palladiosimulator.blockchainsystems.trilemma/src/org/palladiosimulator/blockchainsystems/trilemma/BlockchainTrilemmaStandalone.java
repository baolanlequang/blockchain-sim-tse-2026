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

        var simulationParameters =
                getSimulationParametersFromConfiguration(configuration);

        var simulationFactory =
                new TrilemmaSimulationFactory(simulationParameters, configuration);

        var result = simulationFactory.run();

        var serializer =
                new ThreesimSimulationResultSerializer(
                        ThreesimSerializers.INSTANCE.getJson());

        String simulationJson = serializer.serialize(result);

        Map<String, Object> finalResult = new LinkedHashMap<>();
        finalResult.put("runId", runId);
        finalResult.put("config_id", configuration.get("config_id"));
        finalResult.put("inputParameters", configuration);
        finalResult.put("simulationResult",
                com.google.gson.JsonParser.parseString(simulationJson));

        String jsonResult = new com.google.gson.GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(finalResult);

        try {
            Path outputFile = createOutputPath(runId);
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

    private Path createOutputPath(int runId) {
        return Paths.get("indiv_json")
                .resolve("result_run_" + runId + ".json");
    }

    private boolean initStandalone() {
        try {
            StandaloneInitializerBuilder.builder()
                    .registerProjectURI(
                            this.modelProjectActivator,
                            this.modelProjectName)
                    .build()
                    .init();

            logger.info("Successfully initialized standalone environment.");
            return true;

        } catch (StandaloneInitializationException e) {
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
                blockchainSystemModelFilePath)
                : new SingleSimulationParameters(
                maxAllowedBlockchainLength,
                blockchainSystemModelFilePath);
    }
}
