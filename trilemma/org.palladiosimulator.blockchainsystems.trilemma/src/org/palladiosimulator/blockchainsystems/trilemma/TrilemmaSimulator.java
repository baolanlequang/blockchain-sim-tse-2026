package org.palladiosimulator.blockchainsystems.trilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Batch runner for the refined TSE design--operational CSVs and pilot manifest.
 *
 * Sampling remains external to 3SIM.  This runner consumes each sampled row,
 * applies it in-memory to one reusable base model, and executes exactly one
 * simulator replication per row.  Hierarchical R_S/R_E execution is achieved
 * by supplying the same network_seed for event replications belonging to one
 * network instance and a different event_seed for each event replication.
 */
public class TrilemmaSimulator {

    private static final Path DEFAULT_TESTMODELS_DIR = Paths.get("testmodels");
    private static final Path DEFAULT_BASE_CONFIG_JSON =
            DEFAULT_TESTMODELS_DIR.resolve("configuration_refined_pilot.json");
    private static final Path DEFAULT_CSV = Paths.get("pilot_replication_manifest.csv");

    public static void main(String[] args) {
        Path csvPath = (args.length >= 1) ? Paths.get(args[0]) : DEFAULT_CSV;
        Path testmodelsDir = (args.length >= 2) ? Paths.get(args[1]) : DEFAULT_TESTMODELS_DIR;
        Path baseConfigJson = (args.length >= 3) ? Paths.get(args[2]) : DEFAULT_BASE_CONFIG_JSON;

        BlockchainTrilemmaStandalone simulator = new BlockchainTrilemmaStandalone(
                "org.palladiosimulator.blockchainsystems.trilemma", Activator.class);
        if (!simulator.initAnalysis()) {
            System.err.println("Unable to initialize simulator");
            // Do not let shell/SLURM treat a failed initialization as success.
            System.exit(2);
            return;
        }

        try {
            Map<String, String> baseConfig = loadJsonConfig(baseConfigJson);
            List<Map<String, String>> rows = loadCsv(csvPath);

            // Production experiments must use an explicit R_S x R_E execution
            // manifest. This prevents accidental execution of the raw pair CSV,
            // which would not preserve one structural realization across R_E.
            boolean requireHierarchicalManifest = Boolean.parseBoolean(
                    baseConfig.getOrDefault("requireHierarchicalManifest", "false"));
            if (requireHierarchicalManifest) {
                validateHierarchicalManifest(rows);

                // The scientific pilot also needs the R_S x R_E hierarchy, but its
                // execution settings are intentionally not frozen yet.  Require the
                // freeze flag only for production experiments.
                String experimentPhase = baseConfig.getOrDefault("experimentPhase", "pilot");
                boolean production = "production".equalsIgnoreCase(experimentPhase);
                if (production
                        && !Boolean.parseBoolean(baseConfig.getOrDefault("productionSettingsFrozen", "false"))) {
                    throw new IllegalArgumentException(
                            "Production execution settings are not frozen. Complete the pilot and freeze "
                                    + "R_S, R_E, kappa_warm, kappa_measure, and T_drain before production.");
                }
            }

            // The refined configuration declares the number of distinct sampled
            // pairs with `declaredSamplePairs`. Older configuration files overloaded
            // numberOfMonteCarloRounds for the same purpose; that legacy field remains
            // accepted only as a fallback. Each manifest row is still one engine run.
            validateDeclaredSamplePairCount(baseConfig, rows, requireHierarchicalManifest);

            // Resolve the reusable base model. For pilot rows, the runner will
            // prefer testmodels/threesim-Pxx when that materialized model exists;
            // production rows continue to use refined-base. In every case the
            // sampled CSV values are re-applied in memory by the loader.
            String configuredModel = baseConfig.getOrDefault(
                    "blockchainSystemModelFilePath", "refined-base/Net.blockchainsystem");
            Path modelPath = Paths.get(configuredModel);
            if (!modelPath.isAbsolute() && !Files.exists(modelPath)) {
                modelPath = testmodelsDir.resolve(configuredModel);
            }
            if (!Files.exists(modelPath)) {
                // configuration_refined_pilot.json normally uses a path already
                // rooted at testmodels/; try the canonical refined-base fallback.
                modelPath = testmodelsDir.resolve("refined-base").resolve("Net.blockchainsystem");
            }
            if (!Files.exists(modelPath)) {
                throw new IllegalArgumentException("Refined base model not found: " + modelPath.toAbsolutePath());
            }

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

                // IMPORTANT EXECUTION SEMANTICS:
                // One CSV/manifest row equals exactly one 3SIM execution. The explicit
                // engine-level keys prevent a second, hidden Monte-Carlo loop inside a
                // row and therefore preserve the R_S/R_E network/event seed hierarchy.
                // `declaredSamplePairs` is metadata/validation only; it never multiplies
                // the number of engine executions.
                config.putIfAbsent("engineSimulationType", "Single");
                config.putIfAbsent("engineNumberOfMonteCarloRounds", "1");
                config.putIfAbsent("engineNumberOfParallelTasks", "1");

                if (requireHierarchicalManifest) {
                    // In a production R_S/R_E run, seeds are scientific inputs from
                    // the manifest and must never be silently regenerated here.
                    requireNonBlank(row, "network_seed");
                    requireNonBlank(row, "event_seed");
                    requireNonBlank(row, "network_realization_id");
                } else {
                    // Convenience fallback for one-off smoke tests only.
                    config.putIfAbsent("network_seed", Long.toString(
                            deriveSeed(masterSeed, executionId + "|network")));
                    config.putIfAbsent("event_seed", Long.toString(
                            deriveSeed(masterSeed, executionId + "|event")));
                }

                System.out.println("\nRun " + runId + " | " + executionId
                        + " | network_seed=" + config.get("network_seed")
                        + " | event_seed=" + config.get("event_seed"));
                try {
                    simulator.runSimulation(config, runId);

                    // A refined execution can terminate cleanly at the engine level
                    // while still being scientifically incomplete (for example the
                    // canonical-progress watchdog can stop an unbounded run before the
                    // requested measurement/drain windows finish). Preserve the rich
                    // result JSON, but propagate that incompleteness to the batch status.
                    String incompleteReason = refinedIncompleteReason(executionId);
                    if (incompleteReason != null) {
                        failedRuns++;
                        System.err.println("Run incomplete and retained in the audit output: "
                                + executionId + " | " + incompleteReason);
                    }
                } catch (Exception runFailure) {
                    failedRuns++;
                    writeFailedExecution(executionId, runId, config, runFailure);
                    System.err.println("Run failed and was retained in the audit output: " + executionId);
                    runFailure.printStackTrace();
                }
                runId++;
            }
            System.out.println("\nRefined batch completed: " + rows.size()
                    + " attempted, " + failedRuns + " failed/incomplete at runner level.");
            if (failedRuns > 0) {
                // Failed executions have already been retained in result_trilemma
                // as required by the method. A non-zero process code makes the
                // incomplete batch visible to local scripts and SLURM without
                // replacing/retrying those failed scientific replications.
                System.exit(3);
            }
        } catch (Exception e) {
            System.err.println("Refined batch execution failed");
            e.printStackTrace();
            System.exit(2);
        }
    }

    /**
     * Return a concise reason when a serialized refined execution is incomplete,
     * otherwise null. Engine-level termination is not sufficient for scientific
     * completeness: refined runs require both the requested measurement window
     * and transaction follow-up window to complete.
     */
    @SuppressWarnings("unchecked")
    private static String refinedIncompleteReason(String executionId) throws IOException {
        Path output = Paths.get("result_trilemma")
                .resolve("result_config_" + sanitize(executionId) + ".json");
        if (!Files.exists(output)) {
            throw new IOException("Expected simulation result JSON was not written: " + output.toAbsolutePath());
        }

        Map<String, Object> root;
        try (Reader reader = Files.newBufferedReader(output)) {
            Type mapType = new TypeToken<Map<String, Object>>() { }.getType();
            root = new Gson().fromJson(reader, mapType);
        }
        if (root == null) {
            throw new IOException("Simulation result JSON is empty: " + output.toAbsolutePath());
        }

        Object simulationResultRaw = root.get("simulationResult");
        if (!(simulationResultRaw instanceof Map<?, ?>)) {
            return null;
        }
        Map<String, Object> simulationResult = (Map<String, Object>) simulationResultRaw;
        Object auditRaw = simulationResult.get("refinedExecutionAudit");
        if (!(auditRaw instanceof Map<?, ?>)) {
            return null;
        }
        Map<String, Object> audit = (Map<String, Object>) auditRaw;

        if (!Boolean.TRUE.equals(audit.get("refinedWindowEnabled"))) {
            return null;
        }

        boolean measurementComplete = Boolean.TRUE.equals(audit.get("measurementWindowCompleted"));
        boolean followUpComplete = Boolean.TRUE.equals(audit.get("transactionFollowUpCompleted"));
        if (measurementComplete && followUpComplete) {
            return null;
        }

        String terminationReason = String.valueOf(
                audit.getOrDefault("terminationReason", "UNKNOWN_TERMINATION"));
        String phase = String.valueOf(
                audit.getOrDefault("executionPhaseAtTermination", "UNKNOWN_PHASE"));
        return "terminationReason=" + terminationReason
                + ", phase=" + phase
                + ", measurementWindowCompleted=" + measurementComplete
                + ", transactionFollowUpCompleted=" + followUpComplete;
    }

    /**
     * Preserve runner-level failures instead of silently retrying or terminating
     * the entire batch.  This implements the study rule that failed/incomplete
     * executions remain auditable and are not replaced by successful reruns.
     */
    private static void writeFailedExecution(
            String executionId, int runId, Map<String, String> config, Exception failure) {
        try {
            Path outputDir = Paths.get("result_trilemma");
            Files.createDirectories(outputDir);
            Path output = outputDir.resolve("result_config_" + sanitize(executionId) + "_FAILED.json");

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
            System.err.println("Could not write failure audit for " + executionId);
            auditFailure.printStackTrace();
        }
    }

    /**
     * Enforce the hierarchical execution contract used by the revised Method.
     *
     * Each sampled pair is expanded into R_S network realizations. All R_E rows
     * of one realization must have identical sampled parameters and network_seed,
     * but distinct event_seed/event_replication values. Distinct R_S realizations
     * of the same pair must have distinct network seeds.
     *
     * This validation is deliberately performed before the first simulator run.
     * A malformed manifest therefore fails fast instead of producing scientifically
     * invalid output that only becomes visible during later analysis.
     */
    private static void validateHierarchicalManifest(List<Map<String, String>> rows) {
        List<String> hierarchyColumns = List.of(
                "manifest_pair_id",
                "network_instance",
                "event_replication",
                "network_realization_id",
                "network_seed",
                "event_seed",
                "run_id");

        // Core sampled columns that are always present. Attacker participation is
        // handled separately because refined manifests may represent it either by
        // fraction_of_attackers (preferred in some pilot files) or by
        // number_of_attackers (used by the formal 64x48 catalogue).
        List<String> sampledColumns = List.of(
                "connection_count",
                "block_creation_interval",
                "maximum_block_size",
                "validating_node_count",
                "node_bandwidth_heterogeneity",
                "link_bandwidth_heterogeneity",
                "hashing_power_concentration",
                "transaction_arrival_rate");

        Map<String, String> realizationNetworkSeed = new HashMap<>();
        Map<String, String> realizationPair = new HashMap<>();
        Map<String, String> realizationSampleSignature = new HashMap<>();
        Map<String, Set<String>> realizationEventSeeds = new HashMap<>();
        Map<String, Set<String>> realizationEventReplications = new HashMap<>();
        Map<String, String> pairInstanceSeed = new HashMap<>();
        Map<String, String> pairSeedOwnerInstance = new HashMap<>();
        Set<String> runIds = new HashSet<>();

        for (Map<String, String> row : rows) {
            validateCsvColumns(row);
            for (String column : hierarchyColumns) {
                requireNonBlank(row, column);
            }

            String pairId = row.get("manifest_pair_id");
            String networkInstance = row.get("network_instance");
            String eventReplication = row.get("event_replication");
            String realizationId = row.get("network_realization_id");
            String networkSeed = row.get("network_seed");
            String eventSeed = row.get("event_seed");
            String runId = row.get("run_id");

            if (!runIds.add(runId)) {
                throw new IllegalArgumentException(
                        "Hierarchical manifest contains duplicate run_id: " + runId);
            }

            String previousSeed = realizationNetworkSeed.putIfAbsent(realizationId, networkSeed);
            if (previousSeed != null && !previousSeed.equals(networkSeed)) {
                throw new IllegalArgumentException(
                        "network_seed changes across R_E for network_realization_id="
                                + realizationId + ": " + previousSeed + " vs " + networkSeed);
            }

            String previousPair = realizationPair.putIfAbsent(realizationId, pairId);
            if (previousPair != null && !previousPair.equals(pairId)) {
                throw new IllegalArgumentException(
                        "network_realization_id=" + realizationId
                                + " is reused for different sampled pairs.");
            }

            String signature = sampledSignature(row, sampledColumns)
                    + attackerParticipationSignature(row);
            String previousSignature = realizationSampleSignature.putIfAbsent(realizationId, signature);
            if (previousSignature != null && !previousSignature.equals(signature)) {
                throw new IllegalArgumentException(
                        "Sampled design/operational parameters change across R_E for "
                                + "network_realization_id=" + realizationId);
            }

            Set<String> eventSeeds = realizationEventSeeds.computeIfAbsent(
                    realizationId, key -> new HashSet<>());
            if (!eventSeeds.add(eventSeed)) {
                throw new IllegalArgumentException(
                        "Duplicate event_seed within network_realization_id="
                                + realizationId + ": " + eventSeed);
            }

            Set<String> replications = realizationEventReplications.computeIfAbsent(
                    realizationId, key -> new HashSet<>());
            if (!replications.add(eventReplication)) {
                throw new IllegalArgumentException(
                        "Duplicate event_replication within network_realization_id="
                                + realizationId + ": " + eventReplication);
            }

            String pairInstanceKey = pairId + "|" + networkInstance;
            String priorPairInstanceSeed = pairInstanceSeed.putIfAbsent(pairInstanceKey, networkSeed);
            if (priorPairInstanceSeed != null && !priorPairInstanceSeed.equals(networkSeed)) {
                throw new IllegalArgumentException(
                        "One pair/network_instance received multiple network seeds: "
                                + pairInstanceKey);
            }

            String pairSeedKey = pairId + "|" + networkSeed;
            String priorOwnerInstance = pairSeedOwnerInstance.putIfAbsent(pairSeedKey, networkInstance);
            if (priorOwnerInstance != null && !priorOwnerInstance.equals(networkInstance)) {
                throw new IllegalArgumentException(
                        "Distinct R_S instances of pair " + pairId
                                + " reuse network_seed=" + networkSeed);
            }
        }

        System.out.println("Hierarchical manifest validated: "
                + rows.size() + " execution rows, "
                + realizationNetworkSeed.size() + " distinct network realizations.");
    }

    /**
     * Validate the agreed experiment-level numberOfMonteCarloRounds declaration.
     *
     * In the original 3SIM Monte-Carlo engine this field means repeated rounds of
     * one model.  In the revised external-sampling workflow it is deliberately kept
     * as the declared number of distinct sampled pairs: 24, 3072, 12288, or 4096.
     * The actual engine still executes each manifest row once via engineSimulationType.
     */
    private static void validateDeclaredSamplePairCount(
            Map<String, String> baseConfig,
            List<Map<String, String>> rows,
            boolean hierarchical) {

        // Prefer the explicit refined-workflow declaration. The legacy
        // numberOfMonteCarloRounds field is retained only as a backwards-compatible
        // fallback for older configurations.
        String raw = baseConfig.get("declaredSamplePairs");
        if (raw == null || raw.isBlank()) {
            raw = baseConfig.get("numberOfMonteCarloRounds");
        }
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException(
                    "Base configuration must declare declaredSamplePairs "
                            + "(or legacy numberOfMonteCarloRounds).");
        }

        final int declared;
        try {
            declared = Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "declaredSamplePairs/numberOfMonteCarloRounds must be an integer sample-pair count: " + raw, e);
        }

        Set<String> pairIds = new HashSet<>();
        if (hierarchical) {
            for (Map<String, String> row : rows) {
                requireNonBlank(row, "manifest_pair_id");
                pairIds.add(row.get("manifest_pair_id"));
            }
        } else {
            for (int i = 0; i < rows.size(); i++) {
                Map<String, String> row = rows.get(i);
                String id = row.get("pilot_id");
                if (id == null || id.isBlank()) id = row.get("pilot_case_id");
                if (id == null || id.isBlank()) id = row.get("config_id");
                if (id == null || id.isBlank()) id = "row_" + (i + 1);
                pairIds.add(id);
            }
        }

        // A local/HPC launcher may intentionally hand a validated slice of a full
        // manifest to one JVM.  In that case full-batch validation has already been
        // performed before slicing and this per-process count check is skipped.
        boolean partialBatch = Boolean.parseBoolean(
                baseConfig.getOrDefault("allowValidatedPartialBatch", "false"));
        if (!partialBatch && pairIds.size() != declared) {
            throw new IllegalArgumentException(
                    "Declared sample-pair count=" + declared
                            + " but input contains " + pairIds.size()
                            + " distinct sampled pairs. Use the configuration JSON that matches "
                            + "this experiment, or regenerate the execution manifest.");
        }

        System.out.println("Experiment-level Monte-Carlo declaration: " + declared
                + " sampled pairs; engine executes one row per explicit realization/replication."
                + (partialBatch ? " [validated partial batch]" : ""));
    }

    private static String sampledSignature(
            Map<String, String> row, List<String> sampledColumns) {
        StringBuilder b = new StringBuilder();
        for (String column : sampledColumns) {
            requireNonBlank(row, column);
            b.append(column).append('=').append(row.get(column)).append('|');
        }
        return b.toString();
    }

    private static String attackerParticipationSignature(Map<String, String> row) {
        StringBuilder b = new StringBuilder();
        for (String key : List.of(
                "fraction_of_attackers",
                "number_of_attackers",
                "realized_fraction_of_attackers")) {
            String value = row.get(key);
            if (value != null && !value.isBlank()) {
                b.append(key).append('=').append(value).append('|');
            }
        }
        // validateCsvColumns(row) has already guaranteed that at least one of
        // fraction_of_attackers or number_of_attackers is available.
        return b.toString();
    }

    private static void requireNonBlank(Map<String, String> row, String key) {
        String value = row.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing or empty required execution-manifest column: " + key
                            + " in row: " + row);
        }
    }

    /**
     * For pilot rows, prefer the materialized threesim-Pxx test model when it is
     * present. Production rows continue to use the reusable refined-base model.
     * The CSV values are still applied in-memory by BlockchainSystemModelLoader,
     * so the sampled parameters remain authoritative.
     */
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

    private static String chooseExecutionId(Map<String, String> row, int runId) {
        for (String key : List.of("run_id", "config_id", "pilot_case_id", "pilot_id")) {
            String value = row.get(key);
            if (value != null && !value.isBlank()) {
                if (("pilot_case_id".equals(key) || "pilot_id".equals(key))
                        && row.containsKey("network_instance")
                        && row.containsKey("event_replication")) {
                    return sanitize(value + "_S" + row.get("network_instance") + "_E" + row.get("event_replication"));
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
            throw new IllegalArgumentException("Base configuration JSON not found: " + jsonPath.toAbsolutePath());
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        try (Reader r = Files.newBufferedReader(jsonPath)) {
            Map<String, String> m = gson.fromJson(r, type);
            if (m == null || m.isEmpty()) {
                throw new IllegalArgumentException("Base configuration JSON is empty: " + jsonPath.toAbsolutePath());
            }
            return m;
        }
    }

    private static List<Map<String, String>> loadCsv(Path csvPath) throws IOException {
        if (!Files.exists(csvPath)) throw new IllegalArgumentException("CSV not found: " + csvPath.toAbsolutePath());
        List<Map<String, String>> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String headerLine = br.readLine();
            if (headerLine == null) throw new IllegalArgumentException("CSV is empty: " + csvPath.toAbsolutePath());
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
        if (rows.isEmpty()) throw new IllegalArgumentException("No rows found in CSV: " + csvPath.toAbsolutePath());
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
                throw new IllegalArgumentException("Missing or empty refined CSV column: " + key + " in row: " + row);
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
                    "Refined CSV requires fraction_of_attackers (preferred) or number_of_attackers for pilot compatibility: " + row);
        }
        if (hasFraction) {
            final double fA;
            final int nv;
            try {
                fA = Double.parseDouble(row.get("fraction_of_attackers"));
                nv = (int) Math.rint(Double.parseDouble(row.get("validating_node_count")));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid f_A or N_V in refined CSV row: " + row, e);
            }
            if (!Double.isFinite(fA) || fA < 0.0 || fA > 0.25) {
                throw new IllegalArgumentException("fraction_of_attackers outside [0,0.25]: " + fA);
            }
            int derived = deriveAttackerCount(nv, fA);
            if (hasCount) {
                final int supplied;
                try {
                    supplied = (int) Math.rint(Double.parseDouble(row.get("number_of_attackers")));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number_of_attackers in refined CSV row: " + row, e);
                }
                if (supplied != derived) {
                    throw new IllegalArgumentException(
                            "number_of_attackers=" + supplied
                                    + " disagrees with sampled fraction_of_attackers=" + fA
                                    + " and validating_node_count=" + nv
                                    + "; expected " + derived);
                }
                String realizedRaw = row.get("realized_fraction_of_attackers");
                if (realizedRaw != null && !realizedRaw.isBlank()) {
                    double realized = Double.parseDouble(realizedRaw);
                    double expectedRealized = nv == 0 ? 0.0 : ((double) supplied) / ((double) nv);
                    if (!Double.isFinite(realized) || Math.abs(realized - expectedRealized) > 1e-12) {
                        throw new IllegalArgumentException(
                                "realized_fraction_of_attackers=" + realized
                                        + " disagrees with number_of_attackers/N_V=" + expectedRealized);
                    }
                }
            }
        }
    }

    private static int deriveAttackerCount(int nv, double fA) {
        if (fA == 0.0) return 0;
        return Math.max(1, (int) Math.floor(fA * nv + 0.5));
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

