package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Batch runner for pre-generated Threesim blockchain system models.
 *
 * FIXES:
 * 1) Monte-Carlo settings are actually applied:
 *    - Load testmodels/configuration.json ONCE
 *    - Then merge its keys into each run configuration map
 *
 * 2) Results go into ONE folder (flat output):
 *    - outputDirectory = resultsRoot (same for all models)
 *    - files = result_run_1.json, result_run_2.json, ...
 *
 * IMPORTANT:
 * - Do NOT parse CSV parameters.
 * - Do NOT modify models.
 * - Parameters embedded in threesim-X models remain the single source of truth.
 */
public class TrilemmaSimulator {

    private static final Pattern THREESIM_PATTERN =
            Pattern.compile("^threesim-(\\d+)$");

    private static int extractId(Path dir) {
        Matcher m = THREESIM_PATTERN.matcher(dir.getFileName().toString());
        return m.matches() ? Integer.parseInt(m.group(1)) : Integer.MAX_VALUE;
    }

    private static Map<String, String> loadJsonConfig(Path jsonPath) throws Exception {
        if (!Files.exists(jsonPath)) {
            throw new IllegalArgumentException(
                    "configuration.json not found: " + jsonPath.toAbsolutePath());
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();

        try (Reader r = Files.newBufferedReader(jsonPath)) {
            Map<String, String> m = gson.fromJson(r, type);
            if (m == null) m = new HashMap<>();
            return m;
        }
    }

    public static void main(String[] args) {

        // Root directory containing threesim-1 ... threesim-N folders
        Path testmodelsDir = Paths.get(
            "C:/Users/hk1012/Desktop/MetaModeling/blockchain-sim-tse-2026/"
          + "trilemma/org.palladiosimulator.blockchainsystems.trilemma/testmodels"
        );

        // ✅ ONE shared results folder (flat output)
        Path resultsRoot = Paths.get(
            "C:/Users/hk1012/Desktop/MetaModeling/blockchain-sim-tse-2026-results"
        );

        // Base simulation configuration (Monte-Carlo settings + thresholds)
        Path baseConfigJson = testmodelsDir.resolve("configuration.json");

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
        System.out.println("Models root : " + testmodelsDir.toAbsolutePath());
        System.out.println("Config JSON : " + baseConfigJson.toAbsolutePath());
        System.out.println("Results dir : " + resultsRoot.toAbsolutePath());

        try {
            Files.createDirectories(resultsRoot);

            // ✅ Load config once (Monte-Carlo rounds etc.)
            Map<String, String> baseConfig = loadJsonConfig(baseConfigJson);

            int runIndex = 1;

            try (var paths = Files.list(testmodelsDir)) {

                for (Path modelDir : (Iterable<Path>) paths
                        .filter(Files::isDirectory)
                        .sorted(Comparator.comparingInt(TrilemmaSimulator::extractId))
                        ::iterator) {

                    Path modelFile = modelDir.resolve("Net.blockchainsystem");
                    if (!Files.exists(modelFile)) {
                        continue;
                    }

                    System.out.println("\n▶ Run " + runIndex);
                    System.out.println("   Model: " + modelFile.toAbsolutePath());

                    // ✅ Merge base config (Monte-Carlo settings) + per-model settings
                    Map<String, String> config = new HashMap<>(baseConfig);

                    config.put("blockchainSystemModelFilePath", modelFile.toAbsolutePath().toString());

                    // ✅ One shared folder for all results (NO per-model folders)
                    config.put("outputDirectory", resultsRoot.toAbsolutePath().toString());

                    // ✅ Used by standalone to name output file result_run_X.json
                    config.put("run_index", String.valueOf(runIndex));

                    // Optional: keep traceability
                    config.put("config_id", modelDir.getFileName().toString());

                    simulator.runSimulation(config);
                    runIndex++;
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Batch execution failed");
            e.printStackTrace();
        }

        System.out.println("\n✔ All simulations finished");
    }
}

