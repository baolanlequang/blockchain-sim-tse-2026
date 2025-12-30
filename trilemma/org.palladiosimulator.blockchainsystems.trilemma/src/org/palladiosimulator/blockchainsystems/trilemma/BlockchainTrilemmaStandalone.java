package org.palladiosimulator.blockchainsystems.trilemma;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers;
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimMonteCarloSimulationResult;
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationResultSerializer;
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSingleSimulationResult;
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters;
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType;
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters;

public class BlockchainTrilemmaStandalone {
	private final Logger logger = Logger.getLogger(BlockchainTrilemmaStandalone.class);
	
	private String modelProjectName = "org.palladiosimulator.blockchainsystems.trilemma";
    private Class<? extends Plugin> modelProjectActivator;
	
	public BlockchainTrilemmaStandalone(String modelProjectName,
            Class<? extends Plugin> modelProjectActivator) {
		this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;
	}
	
	public boolean initAnalysis() {
		EcorePlugin.ExtensionProcessor.process(null);
		if (!initStandalone()) {
            return false;
		}
		
		return true;
	}
	
	public void runSimulation(Map<String, String> configuration) {
		var simulationParameters = getSimulationParametersFromConfiguration(configuration);
		
//		var simulationTypeName = "";
//		switch(simulationParameters.getSimulationType()) {
//			case SimulationType.MonteCarlo:
//				simulationTypeName = "Monte-Carlo";
//				break;
//			default:
//				simulationTypeName = "Single";
//		}
//		
		var simulationFactory = new TrilemmaSimulationFactory(simulationParameters, configuration);
		System.out.println("simulationFactory.run");
		var result = simulationFactory.run();
		var serialization = new ThreesimSimulationResultSerializer(ThreesimSerializers.INSTANCE.getJson());
		var jsonResult = serialization.serialize(result);
		
		System.out.println("result: " + result);
		System.out.println("jsonResult: " + jsonResult);
//		if (result instanceof ThreesimMonteCarloSimulationResult monteCarloSimulationResult) {
//			System.out.println("final result monte-carlo: " + monteCarloSimulationResult.getSimulationRoundResults());
//		} else if (result instanceof ThreesimSingleSimulationResult singleSimulationResult) {
//			System.out.println("final result single: " + singleSimulationResult.getSimulationRoundResult());
//		}
		 
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
            logger.error("Unable to initialize standalone environment.");
            e.printStackTrace();
            return false;
        }
    }
	
	private SimulationParameters getSimulationParametersFromConfiguration(Map<String, String> configuration) {
		System.out.println("getSimulationParametersFromConfiguration");
		
		SimulationType simulationType = SimulationType.Single;
		var simulationTypeName = configuration.getOrDefault("simulationType", "");
		if (simulationTypeName.equals("Monte-Carlo")) {
			simulationType = SimulationType.MonteCarlo;
		}
		
		var maxAllowedBlockchainLength = Integer. parseInt(configuration.getOrDefault("maxAllowedBlockchainLength", "30"));
		var numberOfMonteCarloRounds = Integer. parseInt(configuration.getOrDefault("numberOfMonteCarloRounds", "1"));
		var blockchainSystemModelFilePath = configuration.getOrDefault("blockchainSystemModelFilePath", "");
		
		
		SimulationParameters simulationParameters = null;
		switch (simulationType) {
		case MonteCarlo:
			simulationParameters = new MonteCarloSimulationParameters(maxAllowedBlockchainLength, numberOfMonteCarloRounds, blockchainSystemModelFilePath);
			break;
		default:
			simulationParameters = new SingleSimulationParameters(maxAllowedBlockchainLength, blockchainSystemModelFilePath);
		}
		
		return simulationParameters;
		
	}
}
