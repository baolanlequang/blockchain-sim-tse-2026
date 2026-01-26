package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 * Standalone entry point for running blockchain trilemma simulations.
 *
 * RESPONSIBILITY:
 * - Initializes the Palladio / EMF standalone environment
 * - Parses simulation parameters from a configuration map
 * - Executes either Single or Monte-Carlo simulations
 * - Serializes results to JSON next to the model directory
 *
 * NOTE:
 * - This class does NOT validate model semantics
 * - Non-progressing systems are handled downstream (e.g., NaN metrics)
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

    /**
     * Initializes the EMF / Palladio standalone environment.
     */
    public boolean initAnalysis() {
        EcorePlugin.ExtensionProcessor.process(null);
        return initStandalone();
    }

    /**
     * Executes a single simulation run based on the provided configuration.
     *
     * @param configuration key-value map describing the simulation setup
     */
    public void runSimulation(Map<String, String> configuration) {

        // Parse simulation parameters from configuration
        SimulationParameters simulationParameters =
            getSimulationParametersFromConfiguration(configuration);

        // Create and execute simulation
        TrilemmaSimulationFactory simulationFactory =
            new TrilemmaSimulationFactory(simulationParameters, configuration);

        var result = simulationFactory.run();

        // Serialize simulation result to JSON
        var serializer = new ThreesimSimulationResultSerializer(
            ThreesimSerializers.INSTANCE.getJson());

        String jsonResult = serializer.serialize(result);

        // Write result next to the model directory
        try {
            String modelPath =
                configuration.get("blockchainSystemModelFilePath");

            String modelFolder =
                Paths.get(modelPath).getParent().toString();

            try (BufferedWriter writer =
                     new BufferedWriter(
                         new FileWriter(modelFolder + "_result.json"))) {

                writer.write(jsonResult);
            }

            System.out.println("Simulation finished");

        } catch (IOException e) {
            logger.error("Failed to write simulation result", e);
        }
    }

    /**
     * Initializes the standalone environment for EMF-based models.
     */
    private boolean initStandalone() {
        try {
            StandaloneInitializerBuilder.builder()
                .registerProjectURI(
                    this.modelProjectActivator,
                    this.modelProjectName)
                .build()
                .init();

            logger.info(
                "Successfully initialized standalone environment.");

            return true;

        } catch (StandaloneInitializationException e) {
            logger.error(
                "Unable to initialize standalone environment.", e);
            return false;
        }
    }

    /**
     * Creates simulation parameters based on the configuration map.
     *
     * Expected configuration keys:
     * - simulationType (Single | Monte-Carlo)
     * - maxAllowedBlockchainLength
     * - numberOfMonteCarloRounds
     * - blockchainSystemModelFilePath
     */
    private SimulationParameters
        getSimulationParametersFromConfiguration(
            Map<String, String> configuration) {

        SimulationType simulationType =
            "Monte-Carlo".equals(
                configuration.get("simulationType"))
                ? SimulationType.MonteCarlo
                : SimulationType.Single;

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

        return simulationType == SimulationType.MonteCarlo
            ? new MonteCarloSimulationParameters(
                maxAllowedBlockchainLength,
                numberOfMonteCarloRounds,
                blockchainSystemModelFilePath)
            : new SingleSimulationParameters(
                maxAllowedBlockchainLength,
                blockchainSystemModelFilePath);
    }
}
