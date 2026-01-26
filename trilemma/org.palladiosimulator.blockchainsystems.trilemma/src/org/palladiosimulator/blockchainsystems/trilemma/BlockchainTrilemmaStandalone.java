package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType;

import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers;
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationResultSerializer;

/**
 * Standalone executor for Trilemma / Threesim simulations.
 *
 * FIXES:
 * - Ensures Monte-Carlo actually runs when numberOfMonteCarloRounds > 1.
 * - Writes results into ONE shared folder (flat file naming).
 *
 * NOTE:
 * - We do NOT modify model parameters (models are already parameterized).
 * - configuration.json is only used for simulation settings + thresholds.
 */
public class BlockchainTrilemmaStandalone {

    private final Logger logger =
            Logger.getLogger(BlockchainTrilemmaStandalone.class);

    private final String modelProjectName;
    private final Class<? extends Plugin> modelProjectActivator;

    public BlockchainTrilemmaStandalone(
            String modelProjectName,
            Class<? extends Plugin> modelProjectActivator) {

        this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;
    }

    public boolean initAnalysis() {
        EcorePlugin.ExtensionProcessor.process(null);
        return initStandalone();
    }

    private boolean initStandalone() {
        try {
            StandaloneInitializerBuilder.builder()
                .registerProjectURI(this.modelProjectActivator, this.modelProjectName)
                .build()
                .init();

            logger.info("Successfully initialized standalone environment.");
            return true;

        } catch (StandaloneInitializationException e) {
            logger.error("Unable to initialize standalone environment.", e);
            return false;
        }
    }

    public void runSimulation(Map<String, String> configuration) {

        SimulationParameters simulationParameters =
                getSimulationParametersFromConfiguration(configuration);

        TrilemmaSimulationFactory simulationFactory =
                new TrilemmaSimulationFactory(simulationParameters, configuration);

        var result = simulationFactory.run();

        // Serialize full simulation result (Monte-Carlo result object includes averages;
        // depending on library, it may also include per-round results)
        var serializer =
                new ThreesimSimulationResultSerializer(
                        ThreesimSerializers.INSTANCE.getJson());

        String jsonResult = serializer.serialize(result);

        writeResult(configuration, jsonResult);
    }

    /**
     * Builds SimulationParameters from the configuration map.
     *
     * IMPORTANT FIX:
     * - If numberOfMonteCarloRounds > 1, we force Monte-Carlo execution
     *   even if simulationType was not explicitly passed.
     */
    private SimulationParameters getSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        int numberOfMonteCarloRounds =
                Integer.parseInt(configuration.getOrDefault("numberOfMonteCarloRounds", "1"));

        // ✅ Force Monte-Carlo if rounds > 1
        SimulationType simulationType;
        if (numberOfMonteCarloRounds > 1) {
            simulationType = SimulationType.MonteCarlo;
        } else {
            simulationType = SimulationType.Single;
        }

        // If simulationType is explicitly given, keep it consistent:
        // (but still safe if user forgets to pass it)
        String simulationTypeName = configuration.getOrDefault("simulationType", "");
        if ("Monte-Carlo".equalsIgnoreCase(simulationTypeName)) {
            simulationType = SimulationType.MonteCarlo;
        }

        int maxAllowedBlockchainLength =
                Integer.parseInt(configuration.getOrDefault("maxAllowedBlockchainLength", "30"));

        String modelPath = configuration.get("blockchainSystemModelFilePath");
        if (modelPath == null || modelPath.isBlank()) {
            throw new IllegalArgumentException("Missing blockchainSystemModelFilePath");
        }

        if (simulationType == SimulationType.MonteCarlo) {
            return new MonteCarloSimulationParameters(
                    maxAllowedBlockchainLength,
                    numberOfMonteCarloRounds,
                    modelPath);
        }

        return new SingleSimulationParameters(maxAllowedBlockchainLength, modelPath);
    }

    /**
     * Writes results into ONE shared directory as flat files:
     *   result_run_1.json
     *   result_run_2.json
     *   ...
     */
    private void writeResult(Map<String, String> configuration, String jsonResult) {

        try {
            String outputDir = configuration.get("outputDirectory");
            String runIndex = configuration.getOrDefault("run_index", "0");

            if (outputDir == null || outputDir.isBlank()) {
                throw new IllegalStateException("outputDirectory must be provided");
            }

            Path outDir = Paths.get(outputDir);
            Files.createDirectories(outDir);

            Path outputFile = outDir.resolve("result_run_" + runIndex + ".json");

            try (BufferedWriter writer =
                    new BufferedWriter(new FileWriter(outputFile.toFile()))) {
                writer.write(jsonResult);
            }

            System.out.println("Simulation finished → " + outputFile.toAbsolutePath());

        } catch (IOException e) {
            logger.error("Failed to write simulation result", e);
        }
    }
}
