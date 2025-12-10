package org.palladiosimulator.blockchainsystems.trilemma;

public class TrilemmaSimulator {

	public static void main(String[] args) {
		// TODO Implementation
		BlockchainTrilemmaStandalone simulator = new BlockchainTrilemmaStandalone("", Activator.class);
		if (simulator.initAnalysis()) {
			System.out.println("Success initialize simulator");


		} else {
			System.out.println("Unable to initialize simulator");
		}

	}

}
