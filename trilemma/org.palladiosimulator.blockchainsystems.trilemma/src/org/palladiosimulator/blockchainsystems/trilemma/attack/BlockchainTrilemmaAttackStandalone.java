package org.palladiosimulator.blockchainsystems.trilemma.attack;

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
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters; 
import org.palladiosimulator.blockchainsystems.trilemma.BlockchainTrilemmaStandalone;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

public class BlockchainTrilemmaAttackStandalone {
	private final Logger logger =
            Logger.getLogger(BlockchainTrilemmaStandalone.class);

    private String modelProjectName;
    private Class<? extends Plugin> modelProjectActivator;

    public BlockchainTrilemmaAttackStandalone(String modelProjectName,
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
    
    public void runSimulation(Map<String, String> configuration, int runId) {

        var simulationParameters =
                getSimulationParametersFromConfiguration(configuration);

        var simulationFactory =
                new SelfishMiningSimulationFactory(simulationParameters, configuration);

        String simulationJson = simulationFactory.run();

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
    
    private Path createOutputPath(int runId) {
        return Paths.get("result_selfishmining")
                .resolve("result_run_" + runId + ".json");
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
                1)
                : new SingleSimulationParameters(
                maxAllowedBlockchainLength,
                blockchainSystemModelFilePath,
                1);
    }

}
