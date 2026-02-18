package org.palladiosimulator.blockchainsystems.trilemma.attack;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.palladiosimulator.blockchainsystems.trilemma.Activator;
import org.palladiosimulator.blockchainsystems.trilemma.BlockchainTrilemmaStandalone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SelfishminingSimulator {
	public static void main(String[] args) {
		
		BlockchainTrilemmaAttackStandalone simulator = new BlockchainTrilemmaAttackStandalone("org.palladiosimulator.blockchainsystems.trilemma", Activator.class);
		if (simulator.initAnalysis()) {
			System.out.println("Success initialize simulator");
			
			try {
				String configFilePath = "/Users/lanle/Documents/Working/Uni_Ulm/PhD/blockchain-sim-tse-2026/trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json";
				Gson gson = new Gson();
				Type type = new TypeToken<Map<String, String>>() {}.getType();

				Map<String, String> configuration = gson.fromJson(new FileReader(configFilePath), type);
				simulator.runSimulation(configuration, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} else {
			System.out.println("Unable to initialize simulator");
		}

	}
}
