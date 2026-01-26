package org.palladiosimulator.blockchainsystems.trilemma;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Batch runner for pre-generated Threesim blockchain system models.
 *
 * IMPORTANT:
 * - Each folder (threesim-X) contains ONE fully parameterized blockchain system.
 * - All parameters are already baked into the EMF models.
 * - This class only iterates over model folders and runs simulations.
 *
 * This is the ONLY file that needs modification for batch execution.
 */
public class TrilemmaSimulator {

    public static void main(String[] args) {

        // ------------------------------------------------------------
        // Root directory containing threesim-1 ... threesim-N folders
        // ------------------------------------------------------------
        Path testmodelsDir = Paths.get(
            "C:/Users/hk1012/Desktop/MetaModeling/blockchain-sim-tse-2026/"
          + "trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels"
        );

        // ------------------------------------------------------------
        // Initialize simulator ONCE (required for PDE / Palladio)
        // ------------------------------------------------------------
        BlockchainTrilemmaStandalone simulator =
            new BlockchainTrilemmaStandalone(
                "org.palladiosimulator.blockchainsystems.trilemma",
                Activator.class
            );

        if (!simulator.initAnalysis()) {
            System.err.println("❌ Simulator initialization failed");
            return;
        }

        System.out.println("✔ Simulator initialized");
        System.out.println("Models root: " + testmodelsDir.toAbsolutePath());

        // ------------------------------------------------------------
        // One simulation per threesim-X model
        // ------------------------------------------------------------
        int runId = 1;

        try (var paths = Files.list(testmodelsDir)) {

            for (Path modelDir : (Iterable<Path>) paths::iterator) {

                // Only consider directories (threesim-1, threesim-2, ...)
                if (!Files.isDirectory(modelDir)) {
                    continue;
                }

                // Entry point of a Threesim model
                Path modelFile = modelDir.resolve("Net.blockchainsystem");
                if (!Files.exists(modelFile)) {
                    continue; // skip folders without a model
                }

                System.out.println("\n▶ Run " + runId);
                System.out.println("   Model: " + modelFile.toAbsolutePath());

                // ----------------------------------------------------
                // Minimal configuration map
                // ----------------------------------------------------
                // The model already contains ALL system parameters.
                Map<String, String> config = new HashMap<>();
                config.put(
                    "blockchainSystemModelFilePath",
                    modelFile.toAbsolutePath().toString()
                );

                // Optional identifier (useful for logging/results)
                config.put("config_id", String.valueOf(runId));

                // ----------------------------------------------------
                // Execute ONE simulation for this model
                // ----------------------------------------------------
                //simulator.runSimulation(config, runId);
				// Execute the simulation for this model.
				// Note: BlockchainTrilemmaStandalone only accepts a single Map<String, String>
				// argument. Run identification and result separation are handled via the
				// configuration entries (e.g., config_id, outputDirectory), not via a method parameter.
				simulator.runSimulation(config);


                runId++;
            }

        } catch (Exception e) {
            System.err.println("❌ Batch execution failed");
            e.printStackTrace();
        }

        System.out.println("\n✔ All simulations finished");
    }
}
