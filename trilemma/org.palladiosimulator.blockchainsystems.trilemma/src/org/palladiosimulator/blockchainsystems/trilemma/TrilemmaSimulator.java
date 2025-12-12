package org.palladiosimulator.blockchainsystems.trilemma;

public class TrilemmaSimulator {

	public static void main(String[] args) {
		// TODO Implementation
		BlockchainTrilemmaStandalone simulator = new BlockchainTrilemmaStandalone("org.palladiosimulator.blockchainsystems.trilemma", Activator.class);
		if (simulator.initAnalysis()) {
			System.out.println("Success initialize simulator");
			simulator.runSimulation();

		} else {
			System.out.println("Unable to initialize simulator");
		}

	}

}
