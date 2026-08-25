package org.palladiosimulator.blockchainsystems.trilemma.attack;

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

    private final String modelProjectName;
    private final Class<?> modelProjectActivator;

    public BlockchainTrilemmaAttackStandalone(String modelProjectName,
                                        Class<?> modelProjectActivator) {
        this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;
        if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            BasicConfigurator.configure();
        }
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
    	
    	var startTime = System.nanoTime();
    	
    	Runtime runtime = Runtime.getRuntime();
    	runtime.gc();
    	long before = runtime.totalMemory() - runtime.freeMemory();

        var simulationParameters =
                getSimulationParametersFromConfiguration(configuration);

        var simulationFactory =
                new SelfishMiningSimulationFactory(simulationParameters, configuration, runId);

        String simulationJson = simulationFactory.run();
        
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
            throw new IllegalStateException("Selfish-mining simulation completed but its result JSON could not be written", e);
        }

    }

    private Path createOutputPath(String configId, int runId) {
        // Name results by config_id, which is globally unique across the whole CSV. runId
        // restarts at 1 in every jar invocation, so a runId-based name would collide when the
        // 500 configs are split across SLURM array tasks. Fall back to runId only if config_id
        // is somehow absent.
        String key = (configId == null || configId.isBlank()) ? ("run_" + runId) : ("config_" + configId);
        return Paths.get("result_selfishmining")
                .resolve("result_" + key + ".json");
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
