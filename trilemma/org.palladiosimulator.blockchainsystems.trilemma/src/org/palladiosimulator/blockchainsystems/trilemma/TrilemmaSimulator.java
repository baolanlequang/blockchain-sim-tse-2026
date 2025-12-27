package org.palladiosimulator.blockchainsystems.trilemma;

import java.util.HashMap;
import java.util.Map;


public class TrilemmaSimulator {

	public static void main(String[] args) {
		// TODO Implementation
		BlockchainTrilemmaStandalone simulator = new BlockchainTrilemmaStandalone("org.palladiosimulator.blockchainsystems.trilemma", Activator.class);
		if (simulator.initAnalysis()) {
			System.out.println("Success initialize simulator");
			
			Map<String, String> configuration = new HashMap<String, String>();
			configuration.put("simulationType", "Single");
//			configuration.put("simulationType", "Monte-Carlo");
			configuration.put("maxAllowedBlockchainLength", "10");
			configuration.put("numberOfMonteCarloRounds", "2");
			configuration.put("blockchainSystemModelFilePath", "/Users/lanle/Documents/Working/Uni_Ulm/PhD/blockchain-sim-tse-2026/trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels/My.blockchainsystem");
			
			configuration.put("failureThroughputThreshold", "1.0");
			configuration.put("shannonEntropyK", "1.0");
			configuration.put("nakamotoCoefficientThreshold", "50.0");
			configuration.put("reliabilityObservationTimespan", "24.0");
			
			simulator.runSimulation(configuration);

		} else {
			System.out.println("Unable to initialize simulator");
		}

	}

}
