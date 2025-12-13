package org.palladiosimulator.blockchainsystems.trilemma;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters;
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
	
	public void runSimulation() {
		var simulationParameters = getSimulationParametersFromConfiguration();
		
//		var simulationTypeName = "";
//		switch(simulationParameters.getSimulationType()) {
//			case SimulationType.MonteCarlo:
//				simulationTypeName = "Monte-Carlo";
//				break;
//			default:
//				simulationTypeName = "Single";
//		}
//		
		var simulationFactory = new TrilemmaSimulationFactory(simulationParameters);
		System.out.println("simulationFactory.run");
		var result = simulationFactory.run();
		System.out.println("result: " + result);
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
	
	private SimulationParameters getSimulationParametersFromConfiguration() {
		System.out.println("getSimulationParametersFromConfiguration");
//		var simulationType = SimulationType.Single; //TODO default need to be changed
		
		var maxAllowedBlockchainLength = 10; //TODO default need to be changed
		var blockchainSystemModelFilePath = "/Users/lanle/Documents/Working/Uni_Ulm/PhD/blockchain-sim-tse-2026/trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels/My.blockchainsystem";  //TODO default need to be changed
		
		var singleSimulation = new SingleSimulationParameters(maxAllowedBlockchainLength, blockchainSystemModelFilePath);
		
		
		return singleSimulation;
		
	}
}
