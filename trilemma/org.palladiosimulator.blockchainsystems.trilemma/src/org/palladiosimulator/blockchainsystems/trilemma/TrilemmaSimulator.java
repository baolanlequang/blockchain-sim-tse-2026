/**
 * RESPONSIBILITY:
 * - Batch runner for multiple simulations based on a CSV file.
 * - Merges: (1) base testmodels/configuration.json (simulation thresholds)
 *   with (2) one CSV row per experiment (system parameters).
 *
 * KEY IMPROVEMENTS:
 * - Keeps the CSV structure exactly as provided by the user.
 * - Validates that all required columns are present before each run.
 * - Selects the correct model from testmodels/ using the row's identifier column.
 * - Ensures consistent and reproducible execution across all configurations.
 *
 * IDENTIFIER COLUMN:
 * - Nested design/operational-pairs CSV (current default): identifier is "pair_id"
 *   -> testmodels/threesim-<pair_id>. This matches the actual output folder naming of
 *   generate_models.py --id-column pair_id (verified against
 *   blockchain-sim-tse-2026-artifact/sampling/generated_models_nested/ on disk).
 * - Legacy LHS CSV: identifier is "config_id" -> testmodels/threesim-<config_id>.
 *   Both are supported (chosen per-row based on which column the CSV provides), but the
 *   two id spaces are NOT disambiguated by folder name -- both use "threesim-<N>". Since
 *   config_id runs 1-500, pair_id values <= 500 collide with pre-existing legacy model
 *   folders. Do not copy nested pair models for pair_id <= 500 into testmodels/ without
 *   first resolving that collision (e.g. retiring/relocating the legacy 1-500 folders).
 */

package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TrilemmaSimulator {

    // ----------------------------------------------------
    // DEFAULT LOCATIONS (can be overridden via CLI args)
    // ----------------------------------------------------
    private static final Path DEFAULT_TESTMODELS_DIR =
            Paths.get("testmodels");

    private static final Path DEFAULT_BASE_CONFIG_JSON =
            DEFAULT_TESTMODELS_DIR.resolve("configuration.json");

    private static final Path DEFAULT_CSV =
            Paths.get("simulation_pilot_24.csv");

    public static void main(String[] args) {

        Path csvPath = (args.length >= 1) ? Paths.get(args[0]) : DEFAULT_CSV;
        Path testmodelsDir = (args.length >= 2) ? Paths.get(args[1]) : DEFAULT_TESTMODELS_DIR;
        Path baseConfigJson = (args.length >= 3) ? Paths.get(args[2]) : DEFAULT_BASE_CONFIG_JSON;

        BlockchainTrilemmaStandalone simulator =
                new BlockchainTrilemmaStandalone(
                        "org.palladiosimulator.blockchainsystems.trilemma",
                        Activator.class);

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

                String idColumn;
                String idValue;
                if (row.containsKey("design_id") && row.containsKey("operational_id")) {
                    // PR-9 schema: design_id + operational_id together are the identifier.
                    // idValue matches generate_test_models.py's PR-9-schema output folder
                    // naming exactly (threesim-<design_id>_<operational_id>), so pickModelPath
                    // below needs no changes at all -- it already does "threesim-" + idValue.
                    idColumn = "design_id_operational_id";
                    idValue = row.get("design_id") + "_" + row.get("operational_id");
                } else {
                    // Nested design/operational-pairs CSV uses "pair_id"; the legacy LHS CSV uses
                    // "config_id". Pick whichever the row actually provides, else fall back to the
                    // run counter.
                    idColumn = row.containsKey("pair_id") ? "pair_id" : "config_id";
                    idValue = row.getOrDefault(idColumn, String.valueOf(runId));
                }
                config.put(idColumn, idValue);
                config.put("id", idValue);

                // Copy CSV parameters as-is
                config.putAll(row);

                // ----------------------------------------------------
                // Deterministic model selection: idColumn = N → testmodels/threesim-N/Net.blockchainsystem
                // (same folder naming for pair_id and config_id -- see pickModelPath for the
                // resulting collision for pair_id <= 500, which callers must resolve on disk).
                // ----------------------------------------------------
                Path modelPath = pickModelPath(testmodelsDir, idColumn, idValue);
                config.put("blockchainSystemModelFilePath", modelPath.toString());
                // ----------------------------------------------------

                System.out.println("\n▶ Run " + runId + " | " + idColumn + "=" + idValue);
                System.out.println("   Using model: " + modelPath.toAbsolutePath());
                System.out.println("   Monte-Carlo rounds = "
                        + config.getOrDefault("numberOfMonteCarloRounds", "?"));

                simulator.runSimulation(config, runId);
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

        if (row.containsKey("design_id") && row.containsKey("operational_id")) {
            // PR-9 schema (design_id/operational_id-keyed): required columns are entirely
            // different from the pair_id/config_id schema below, so this is validated and
            // returned separately rather than folded into the shared "required" list.
            List<String> requiredPr9 = List.of(
                    "design_id",
                    "operational_id",
                    "connection_count",
                    "block_creation_interval",
                    "maximum_block_size",
                    "validating_node_count",
                    "node_bandwidth_heterogeneity",
                    "link_bandwidth_heterogeneity",
                    "hashing_power_concentration",
                    "number_of_attackers",
                    "transaction_arrival_rate"
            );

            for (String key : requiredPr9) {
                if (!row.containsKey(key) || row.get(key).isBlank()) {
                    throw new IllegalArgumentException(
                            "❌ Missing or empty required CSV column: " + key
                                    + " in row: " + row);
                }
            }
            return;
        }

        if (!row.containsKey("pair_id") && !row.containsKey("config_id")) {
            throw new IllegalArgumentException(
                    "❌ CSV row missing identifier column (expected 'pair_id' or 'config_id'): "
                            + row);
        }

        List<String> required = List.of(
        		"Hnode",
        		"Hlink",
        		"block_creation_interval",
        		"hashrate_concentration",
        		"max_block_size",
        		"inbound_connections",
        		"outbound_connections",
        		"number_of_attackers",
        		"validator_count"
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
    // Revised to take into account 50 blockchain system models: deterministic model resolver
    // ----------------------------------------------------
    private static Path pickModelPath(Path testmodelsDir, String idColumn, String idValue) {

        // Each configuration has exactly one model folder: testmodels/threesim-<id>/
        // Verified against the actual output of generate_models.py --id-column pair_id
        // (blockchain-sim-tse-2026-artifact/sampling/generated_models_nested/): the folder
        // naming is identical for both id spaces (threesim-<pair_id> and threesim-<config_id>),
        // it is only the *root* output directory that differs (generated_models_nested/ vs
        // generated_models/) on the artifact-repo side. WARNING: because pair_id and config_id
        // both start at 1 and the legacy config_id space runs 1-500, pair_ids <= 500 resolve to
        // the SAME testmodels/threesim-<N>/ path as the pre-existing legacy config_id folder.
        // Do not copy nested pair models for pair_id <= 500 into testmodels/ until that
        // collision is resolved (see project notes) -- this method does not detect it.
        String folderName = "threesim-" + idValue;

        Path modelPath =
                testmodelsDir
                        .resolve(folderName)
                        .resolve("Net.blockchainsystem");

        // Fail fast if the model is missing or misconfigured
        if (!Files.exists(modelPath)) {
            throw new IllegalArgumentException(
                    "❌ Model not found for " + idColumn + "=" + idValue +
                    " at " + modelPath.toAbsolutePath());
        }

        return modelPath;
    }
}

