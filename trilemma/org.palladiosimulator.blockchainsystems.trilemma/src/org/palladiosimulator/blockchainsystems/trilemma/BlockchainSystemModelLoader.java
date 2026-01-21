/**
 * RESPONSIBILITY:
 * - Loads the EMF blockchain model from testmodels/.
 * - CRITICAL CHANGE: applies CSV configuration parameters directly to the EMF model
 *   via reflective traversal (eAllContents()) before simulation starts.
 *
 * WHY THIS WAS NECESSARY:
 * - Previously, CSV parameters had little or no effect on the actual model.
 * - Now, each CSV row genuinely changes the blockchain system (bandwidth,
 *   block interval, hashing power, validator count, workload, etc.).
 *
 * SIDE EFFECT:
 * - Prints "[ModelOverride] Applied X attribute updates from CSV." per run,
 *   enabling verification that parameters were actually injected.
 */

package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.*;

import com.google.common.io.Files;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class BlockchainSystemModelLoader {

    private final ResourceSet resourceSet = new ResourceSetImpl();

    public BlockchainSystem load(String uri) {
        return load(uri, Collections.emptyMap());
    }

/**
 * Loads the model AND overrides its attributes based on the provided CSV row.
 * This is the key integration point between batch parameters and the EMF model.
 */

    public BlockchainSystem load(String uri, Map<String, String> configuration) {

        BlockchainsystemPackage.eINSTANCE.eClass();

        String folderName = Paths.get(uri).getParent().getFileName().toString();
        String fileName = Paths.get(uri).getFileName().toString();
        String baseName = Files.getNameWithoutExtension(fileName);

        // Load all related model files (unchanged behavior)
        resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".nodeallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".blockchainsystemComponentRepository"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".geographicalregions"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".linkallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".transactions"), true);

        // Resolve everything
        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());

        BlockchainSystem system =
                (BlockchainSystem) currentResources.get(0).getContents().getFirst();

        // === APPLY CSV OVERRIDES HERE ===
        int updates = applyConfigurationOverrides(system, configuration);
        System.out.println("   [ModelOverride] Applied " + updates + " attribute updates from CSV.");
        
        
        //TODO: This is an example of print connectivity to check, it doesn't update
        System.out.println("netwo: " + ((ConnectedSubgraphsNetworkTopology)system.getNetwork().getTopology()).getSubgraphs());
        //End TODO

        return system;
    }

    private URI createRelativePluginURI(String folder, String relativePath) {
        String path =
                Paths.get("org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                          relativePath)
                     .toString();
        return URI.createPlatformPluginURI(path, false);
    }

    /**
     * Reflectively override EMF attributes whose names match CSV parameters.
     */
    private int applyConfigurationOverrides(EObject root, Map<String, String> cfg) {

        if (cfg == null || cfg.isEmpty()) return 0;

        // Canonical keys we expect AFTER normalization in TrilemmaSimulator
        Map<String, String> desired = new LinkedHashMap<>();
        putIfPresent(desired, "bandwidth", cfg);
        putIfPresent(desired, "block_creation_interval", cfg);
        putIfPresent(desired, "hashing_power", cfg);
        putIfPresent(desired, "max_block_size", cfg);
        putIfPresent(desired, "validator_count", cfg);
        putIfPresent(desired, "workload", cfg);
        putIfPresent(desired, "crashed_validators", cfg);
        putIfPresent(desired, "peer_connectivity", cfg);

        if (desired.isEmpty()) return 0;

        // Build name variants for matching EMF attributes
        Map<String, List<String>> nameVariants = new LinkedHashMap<>();
        for (String k : desired.keySet()) {
            nameVariants.put(k, buildNameVariants(k));
        }

        AtomicInteger updates = new AtomicInteger(0);

        TreeIterator<EObject> it = root.eAllContents();
        while (it.hasNext()) {
            EObject obj = it.next();
            EClass cls = obj.eClass();

            for (Map.Entry<String, String> e : desired.entrySet()) {
                String key = e.getKey();
                String rawVal = e.getValue();

                for (String fname : nameVariants.get(key)) {
                    EStructuralFeature f = cls.getEStructuralFeature(fname);

                    if (f instanceof EAttribute attr && attr.isChangeable()) {
                        Object parsed = parseToEType(attr.getEAttributeType(), rawVal);
                        if (parsed != null) {
                            obj.eSet(attr, parsed);
                            updates.incrementAndGet();
                        }
                        break;
                    }
                }
            }
        }
        return updates.get();
    }

    private void putIfPresent(Map<String, String> out, String key, Map<String, String> cfg) {
        String v = cfg.get(key);
        if (v != null && !v.isBlank()) out.put(key, v.trim());
    }

    private List<String> buildNameVariants(String snake) {
        String[] parts = snake.split("_");
        StringBuilder camel = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camel.append(parts[i].substring(0,1).toUpperCase())
                 .append(parts[i].substring(1));
        }
        String camelStr = camel.toString();
        String pascal = camelStr.substring(0,1).toUpperCase() + camelStr.substring(1);

        return Arrays.asList(snake, camelStr, pascal, snake.replace("_",""));
    }

    private Object parseToEType(EDataType type, String raw) {
        try {
            String name = type.getInstanceClassName();
            if (name == null) return raw;

            if (name.equals("int") || name.equals("java.lang.Integer")) {
                return Integer.parseInt(raw);
            }
            if (name.equals("double") || name.equals("java.lang.Double")) {
                return Double.parseDouble(raw);
            }
            if (name.equals("boolean") || name.equals("java.lang.Boolean")) {
                return Boolean.parseBoolean(raw);
            }
            return raw;
        } catch (Exception e) {
            return null;
        }
    }
}
