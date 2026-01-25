/**
 * RESPONSIBILITY:
 * - Batch runner for multiple simulations based on a CSV file.
 * - Merges: (1) base testmodels/configuration.json (simulation thresholds)
 *   with (2) one CSV row per experiment (system parameters).
 *
 * KEY IMPROVEMENTS:
 * - Keeps the CSV structure exactly as provided by the user.
 * - Validates that all required columns are present before each run.
 * - Selects the correct model from testmodels/ using config_id → threesim-config_id.
 * - Ensures consistent and reproducible execution across all configurations.
 */

package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TrilemmaSimulator {

    // ----------------------------------------------------
    // DEFAULT LOCATIONS (can be overridden via CLI args)
    // ----------------------------------------------------

    private static final Path SIMULATION_SKIPPED_LOG =
            Paths.get("skipped_during_simulation.csv");

    // === NEW ===
    // Base directory where all simulation results will be stored.
    // Each configuration gets its own subfolder:
    // simulation-results/config-<config_id>/
    private static final Path RESULTS_BASE_DIR =
            Paths.get("simulation-results");

    private static final Path DEFAULT_TESTMODELS_DIR =
            Paths.get("testmodels");

    private static final Path DEFAULT_BASE_CONFIG_JSON =
            DEFAULT_TESTMODELS_DIR.resolve("configuration.json");

    private static final Path DEFAULT_CSV =
            Paths.get("optimized_deterministic_lhs_configurations.csv");

    public static void main(String[] args) {

        Path csvPath = (args.length >= 1) ? Paths.get(args[0]) : DEFAULT_CSV;
        Path testmodelsDir = (args.length >= 2) ? Paths.get(args[1]) : DEFAULT_TESTMODELS_DIR;
        Path baseConfigJson = (args.length >= 3) ? Paths.get(args[2]) : DEFAULT_BASE_CONFIG_JSON;

        BlockchainTrilemmaStandalone simulator =
                new BlockchainTrilemmaStandalone(
                        "org.palladiosimulator.blockchainsystems.trilemma",
                        Activator.class);

        // Initialize simulation-skipped log
        try {
            if (!Files.exists(SIMULATION_SKIPPED_LOG)) {
                Files.writeString(
                        SIMULATION_SKIPPED_LOG,
                        "config_id,reason\n",
                        StandardOpenOption.CREATE
                );
            }

            // === NEW ===
            // Ensure base results directory exists
            Files.createDirectories(RESULTS_BASE_DIR);

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logging infrastructure", e);
        }

        if (!simulator.initAnalysis()) {
            System.err.println("❌ Unable to initialize simulator");
            return;
        }

        System.out.println("✔ Simulator initialized");
        System.out.println("CSV: " + csvPath.toAbsolutePath());
        System.out.println("testmodels/: " + testmodelsDir.toAbsolutePath());
        System.out.println("Base config: " + baseConfigJson.toAbsolutePath());

        try {
            Map<String, String> baseConfig = loadJsonConfig(baseConfigJson);
            List<Map<String, String>> rows = loadCsv(csvPath);

            int runId = 1;

            for (Map<String, String> row : rows) {

                // Validate CSV row BEFORE running anything
                validateCsvColumns(row);

                // Start from base configuration.json
                Map<String, String> config = new LinkedHashMap<>(baseConfig);

                // Preserve config_id from CSV if present, else use run counter
                String configId = row.getOrDefault("config_id", String.valueOf(runId));
                config.put("config_id", configId);
                config.put("id", configId);

                // Copy CSV parameters as-is
                config.putAll(row);

                // ----------------------------------------------------
                // Deterministic model selection:
                // config_id = N → testmodels/threesim-N/Net.blockchainsystem
                // ----------------------------------------------------
                Path modelPath = pickModelPath(testmodelsDir, configId);
                config.put("blockchainSystemModelFilePath", modelPath.toString());

                // ----------------------------------------------------
                // === NEW ===
                // Create a dedicated results directory for this configuration
                // All outputs of this run will be written here.
                // ----------------------------------------------------
                Path runResultsDir =
                        RESULTS_BASE_DIR.resolve("config-" + configId);
                Files.createDirectories(runResultsDir);

                // Pass result directory to Threesim via system property
                // This is the supported way to control output location
                System.setProperty(
                        "threesim.results.dir",
                        runResultsDir.toAbsolutePath().toString()
                );

                System.out.println("\n▶ Run " + runId + " | config_id=" + configId);
                System.out.println("   Using model: " + modelPath.toAbsolutePath());
                System.out.println("   Results dir: " + runResultsDir.toAbsolutePath());
                System.out.println("   Monte-Carlo rounds = "
                        + config.getOrDefault("numberOfMonteCarloRounds", "?"));

                // ADDRESS NONCOMPATIBLE / UNSTABLE CONFIGURATIONS
                try {
                    simulator.runSimulation(config, runId);

                } catch (IllegalStateException e) {

                    // Threesim throws this when metrics become invalid
                    System.err.println(
                            "[SKIP][SIMULATION] config_id=" + configId +
                            " | reason=" + e.getMessage()
                    );

                    // Persist skip for later analysis
                    Files.writeString(
                            SIMULATION_SKIPPED_LOG,
                            configId + ",\"" + e.getMessage().replace("\"", "'") + "\"\n",
                            StandardOpenOption.APPEND
                    );

                    // IMPORTANT:
                    // Skip this configuration and continue batch execution
                }

                runId++;
            }

            System.out.println("\n✔ All runs completed");

        } catch (Exception e) {
            System.err.println("❌ Batch execution failed");
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    // Load base configuration.json
    // ----------------------------------------------------
    private static Map<String, String> loadJsonConfig(Path jsonPath) throws IOException {
        if (!Files.exists(jsonPath)) {
            throw new IllegalArgumentException(
                    "Base configuration.json not found: " + jsonPath.toAbsolutePath());
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();

        try (Reader r = Files.newBufferedReader(jsonPath)) {
            Map<String, String> m = gson.fromJson(r, type);
            if (m == null || m.isEmpty()) {
                throw new IllegalArgumentException(
                        "Base configuration.json is empty/unreadable: "
                                + jsonPath.toAbsolutePath());
            }
            return m;
        }
    }

    // ----------------------------------------------------
    // Load CSV configurations
    // ----------------------------------------------------
    private static List<Map<String, String>> loadCsv(Path csvPath) throws IOException {
        if (!Files.exists(csvPath)) {
            throw new IllegalArgumentException(
                    "CSV not found: " + csvPath.toAbsolutePath());
        }

        List<Map<String, String>> rows = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvPath)) {

            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException(
                        "CSV is empty: " + csvPath.toAbsolutePath());
            }

            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",", -1);
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    row.put(headers[i].trim(), values[i].trim());
                }
                rows.add(row);
            }
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException(
                    "No rows found in CSV: " + csvPath.toAbsolutePath());
        }

        return rows;
    }

    // ----------------------------------------------------
    // CSV validation
    // ----------------------------------------------------
    private static void validateCsvColumns(Map<String, String> row) {

        List<String> required = List.of(
                "config_id",
                "bandwidth",
                "block_creation_interval",
                "hashing_power",
                "max_block_size",
                "inbound_connectivity",
                "outbound_connectivity",
                "crashed_validators",
                "validator_count",
                "workload"
        );

        for (String key : required) {
            if (!row.containsKey(key) || row.get(key).isBlank()) {
                throw new IllegalArgumentException(
                        "❌ Missing or empty required CSV column: " + key
                                + " in row: " + row);
            }
        }
    }

    // ----------------------------------------------------
    // Deterministic model resolver for 50 configurations
    // ----------------------------------------------------
    private static Path pickModelPath(Path testmodelsDir, String configId) {

        Path modelPath =
                testmodelsDir
                        .resolve("threesim-" + configId)
                        .resolve("Net.blockchainsystem");

        // Fail fast if the model is missing or misconfigured
        if (!Files.exists(modelPath)) {
            throw new IllegalArgumentException(
                    "❌ Model not found for config_id=" + configId +
                    " at " + modelPath.toAbsolutePath());
        }

        return modelPath;
    }
}
