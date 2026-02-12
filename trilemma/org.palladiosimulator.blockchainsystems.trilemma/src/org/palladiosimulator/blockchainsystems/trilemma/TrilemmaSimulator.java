package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;


public class TrilemmaSimulator {

	public static void main(String[] args) {
		
		BlockchainTrilemmaStandalone simulator = new BlockchainTrilemmaStandalone("org.palladiosimulator.blockchainsystems.trilemma", Activator.class);
		if (simulator.initAnalysis()) {
			System.out.println("Success initialize simulator");
			
//			Map<String, String> configuration = new HashMap<String, String>();
			try {
//				String jsonString = Files.readString(Paths.get("./configuration.json"));
				String configFilePath = "/Users/lanle/Documents/Working/Uni_Ulm/PhD/blockchain-sim-tse-2026/trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json";
				Gson gson = new Gson();
				Type type = new TypeToken<Map<String, String>>() {}.getType();

				Map<String, String> configuration = gson.fromJson(new FileReader(configFilePath), type);
				
//				simulator.runSimulation(configuration);
				simulator.runSimulation(configuration, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
////			configuration.put("simulationType", "Monte-Carlo");
			
			

		} else {
			System.out.println("Unable to initialize simulator");
		}

	}

}
