package org.palladiosimulator.blockchainsystems.trilemma.attack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.palladiosimulator.blockchainsystems.trilemma.Activator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Refined batch runner for the legacy 3SIM selfish-mining simulation path.
 *
 * <p>The runner deliberately uses the same sampled design/operational columns,
 * reusable base model, and network/event seed hierarchy as the normal refined
 * runner. One CSV row is one simulator round; the legacy internal Monte-Carlo
 * loop is disabled so structural and event randomness are not mixed.</p>
 *
 * <p>Important: this aligns the attack execution inputs and randomness with the
 * revised method, but it does not invent the paper's SPSM round semantics. The
 * legacy selfish-mining result remains a reference/validation output until
 * successful/unambiguous attack-round boundaries are defined explicitly.</p>
 */
public class SelfishminingSimulator {

    private static final Path DEFAULT_TESTMODELS_DIR = Paths.get("testmodels");
    private static final Path DEFAULT_BASE_CONFIG_JSON =
            DEFAULT_TESTMODELS_DIR.resolve("configuration_refined_pilot.json");
    private static final Path DEFAULT_CSV = Paths.get("selfish_replication_manifest.csv");

    public static void main(String[] args) {
        Path csvPath = (args.length >= 1) ? Paths.get(args[0]) : DEFAULT_CSV;
        Path testmodelsDir = (args.length >= 2) ? Paths.get(args[1]) : DEFAULT_TESTMODELS_DIR;
        Path baseConfigJson = (args.length >= 3) ? Paths.get(args[2]) : DEFAULT_BASE_CONFIG_JSON;

        BlockchainTrilemmaAttackStandalone simulator =
                new BlockchainTrilemmaAttackStandalone(
                        "org.palladiosimulator.blockchainsystems.trilemma",
                        Activator.class);

        if (!simulator.initAnalysis()) {
            System.err.println("Unable to initialize selfish-mining simulator");
            return;
        }

        try {
            Map<String, String> baseConfig = loadJsonConfig(baseConfigJson);
            List<Map<String, String>> rows = loadCsv(csvPath);
            Path modelPath = resolveBaseModel(baseConfig, testmodelsDir);
            long masterSeed = Long.parseLong(baseConfig.getOrDefault("masterSeed", "1024"));

            int runId = 1;
            int failedRuns = 0;
            for (Map<String, String> row : rows) {
                validateCsvColumns(row);
                Map<String, String> config = new LinkedHashMap<>(baseConfig);
                config.putAll(row);

                String executionId = chooseExecutionId(row, runId);
                config.put("config_id", executionId);
                config.put("id", executionId);
                Path rowModelPath = resolveModelForRow(row, testmodelsDir, modelPath);
                config.put("blockchainSystemModelFilePath", rowModelPath.toString());

                // Keep legacy experiment-level Monte-Carlo fields unchanged for
                // compatibility/audit.  In particular, preserve "simulationType"
                // from the selected experiment JSON. Execute this explicit row once internally.
                config.putIfAbsent("engineSimulationType", "Single");
                config.putIfAbsent("engineNumberOfMonteCarloRounds", "1");
                config.putIfAbsent("engineNumberOfParallelTasks", "1");

                config.putIfAbsent(
                        "network_seed",
                        Long.toString(deriveSeed(masterSeed, executionId + "|network")));
                config.putIfAbsent(
                        "event_seed",
                        Long.toString(deriveSeed(masterSeed, executionId + "|event")));

                System.out.println("\nSelfish-mining run " + runId + " | " + executionId
                        + " | network_seed=" + config.get("network_seed")
                        + " | event_seed=" + config.get("event_seed"));
                try {
                    simulator.runSimulation(config, runId);
                } catch (Exception runFailure) {
                    failedRuns++;
                    writeFailedExecution(executionId, runId, config, runFailure);
                    System.err.println(
                            "Selfish-mining run failed and was retained in the audit output: "
                                    + executionId);
                    runFailure.printStackTrace();
                }
                runId++;
            }

            System.out.println("\nRefined selfish-mining batch completed: " + rows.size()
                    + " attempted, " + failedRuns + " failed at runner level.");
        } catch (Exception e) {
            System.err.println("Refined selfish-mining batch execution failed");
            e.printStackTrace();
        }
    }

    private static Path resolveBaseModel(Map<String, String> baseConfig, Path testmodelsDir) {
        String configuredModel = baseConfig.getOrDefault(
                "blockchainSystemModelFilePath", "refined-base/Net.blockchainsystem");
        Path modelPath = Paths.get(configuredModel);
        if (!modelPath.isAbsolute() && !Files.exists(modelPath)) {
            modelPath = testmodelsDir.resolve(configuredModel);
        }
        if (!Files.exists(modelPath)) {
            modelPath = testmodelsDir.resolve("refined-base").resolve("Net.blockchainsystem");
        }
        if (!Files.exists(modelPath)) {
            throw new IllegalArgumentException(
                    "Refined base model not found: " + modelPath.toAbsolutePath());
        }
        return modelPath;
    }

    /** Prefer a materialized threesim-Pxx model for pilot rows when available. */
    private static Path resolveModelForRow(
            Map<String, String> row, Path testmodelsDir, Path defaultModelPath) {
        String pilotId = row.get("pilot_id");
        if (pilotId == null || pilotId.isBlank()) {
            pilotId = row.get("pilot_case_id");
        }
        if (pilotId != null && !pilotId.isBlank()) {
            String cleanPilotId = sanitize(pilotId);
            Path pilotModel = testmodelsDir
                    .resolve("threesim-" + cleanPilotId)
                    .resolve("Net.blockchainsystem");
            if (Files.exists(pilotModel)) {
                return pilotModel;
            }
        }
        return defaultModelPath;
    }

    private static void writeFailedExecution(
            String executionId, int runId, Map<String, String> config, Exception failure) {
        try {
            Path outputDir = Paths.get("result_selfishmining");
            Files.createDirectories(outputDir);
            Path output = outputDir.resolve(
                    "result_config_" + sanitize(executionId) + "_FAILED.json");

            StringWriter stack = new StringWriter();
            failure.printStackTrace(new PrintWriter(stack));
            Map<String, Object> record = new LinkedHashMap<>();
            record.put("runId", runId);
            record.put("config_id", executionId);
            record.put("inputParameters", config);
            record.put("executionStatus", "FAILED");
            record.put("failureClass", failure.getClass().getName());
            record.put("failureMessage", failure.getMessage());
            record.put("stackTrace", stack.toString());
            Files.writeString(output, new Gson().toJson(record));
        } catch (Exception auditFailure) {
            System.err.println("Could not write selfish-mining failure audit for " + executionId);
            auditFailure.printStackTrace();
        }
    }

    private static String chooseExecutionId(Map<String, String> row, int runId) {
        for (String key : List.of("run_id", "config_id", "pilot_case_id", "pilot_id")) {
            String value = row.get(key);
            if (value != null && !value.isBlank()) {
                if (("pilot_case_id".equals(key) || "pilot_id".equals(key))
                        && row.containsKey("network_instance")
                        && row.containsKey("event_replication")) {
                    return sanitize(value + "_S" + row.get("network_instance")
                            + "_E" + row.get("event_replication"));
                }
                return sanitize(value);
            }
        }
        String design = row.get("design_id");
        String operational = row.get("operational_id");
        if (design != null && operational != null) {
            return sanitize(design + "_" + operational
                    + optionalSuffix(row, "network_instance", "_S")
                    + optionalSuffix(row, "event_replication", "_E"));
        }
        return "run_" + runId;
    }

    private static String optionalSuffix(Map<String, String> row, String key, String prefix) {
        String value = row.get(key);
        return (value == null || value.isBlank()) ? "" : prefix + value;
    }

    private static String sanitize(String raw) {
        return raw.replaceAll("[^A-Za-z0-9_.-]", "_");
    }

    private static Map<String, String> loadJsonConfig(Path jsonPath) throws IOException {
        if (!Files.exists(jsonPath)) {
            throw new IllegalArgumentException(
                    "Base configuration JSON not found: " + jsonPath.toAbsolutePath());
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        try (Reader r = Files.newBufferedReader(jsonPath)) {
            Map<String, String> config = gson.fromJson(r, type);
            if (config == null || config.isEmpty()) {
                throw new IllegalArgumentException(
                        "Base configuration JSON is empty: " + jsonPath.toAbsolutePath());
            }
            return config;
        }
    }

    private static List<Map<String, String>> loadCsv(Path csvPath) throws IOException {
        if (!Files.exists(csvPath)) {
            throw new IllegalArgumentException("CSV not found: " + csvPath.toAbsolutePath());
        }
        List<Map<String, String>> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("CSV is empty: " + csvPath.toAbsolutePath());
            }
            String[] headers = headerLine.split(",", -1);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] values = line.split(",", -1);
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i].trim(), i < values.length ? values[i].trim() : "");
                }
                rows.add(row);
            }
        }
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("No rows found in CSV: " + csvPath.toAbsolutePath());
        }
        return rows;
    }

    private static void validateCsvColumns(Map<String, String> row) {
        List<String> required = List.of(
                "connection_count",
                "block_creation_interval",
                "maximum_block_size",
                "validating_node_count",
                "node_bandwidth_heterogeneity",
                "link_bandwidth_heterogeneity",
                "hashing_power_concentration",
                "transaction_arrival_rate");
        for (String key : required) {
            if (!row.containsKey(key) || row.get(key).isBlank()) {
                throw new IllegalArgumentException(
                        "Missing or empty refined CSV column: " + key + " in row: " + row);
            }
        }
        boolean hasFraction = row.containsKey("fraction_of_attackers")
                && row.get("fraction_of_attackers") != null
                && !row.get("fraction_of_attackers").isBlank();
        boolean hasCount = row.containsKey("number_of_attackers")
                && row.get("number_of_attackers") != null
                && !row.get("number_of_attackers").isBlank();
        if (!hasFraction && !hasCount) {
            throw new IllegalArgumentException(
                    "Refined CSV requires fraction_of_attackers (preferred) or number_of_attackers for pilot compatibility: "
                            + row);
        }
    }

    private static long deriveSeed(long baseSeed, String label) {
        long h = 0xcbf29ce484222325L ^ baseSeed;
        byte[] bytes = label.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        for (byte b : bytes) {
            h ^= (b & 0xffL);
            h *= 0x100000001b3L;
        }
        long z = h + 0x9E3779B97F4A7C15L;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }
}
