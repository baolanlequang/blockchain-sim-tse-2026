package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import com.google.common.io.Files;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * RESPONSIBILITY:
 * - Loads a fully materialized EMF BlockchainSystem model from testmodels/.
 *
 * IMPORTANT DESIGN DECISION:
 * - This loader DOES NOT apply runtime configuration overrides.
 * - There is NO "base model" during simulation.
 * - All parameter variability is applied OFFLINE by the Python model generator.
 *
 * ASSUMPTIONS:
 * - Each configuration has its own complete model folder:
 *   testmodels/threesim-<config_id>/
 * - All related EMF files (.blockchainsystem, .p2pnetwork, etc.) exist there.
 *
 * WHY THIS IS SAFE:
 * - Avoids runtime mutation of EMF models.
 * - Ensures full reproducibility of simulation results.
 * - Eliminates CSV/JSON → EMF impedance mismatches at runtime.
 */
public class BlockchainSystemModelLoader {

    /**
     * Single ResourceSet reused for loading all related EMF resources
     * belonging to one blockchain system model.
     */
    private final ResourceSet resourceSet = new ResourceSetImpl();

    /**
     * Loads a complete BlockchainSystem model from the given URI.
     *
     * @param uri Absolute or plugin-relative path to Net.blockchainsystem
     * @return Fully resolved BlockchainSystem instance
     */
    public BlockchainSystem load(String uri) {

        // Ensure EMF package is registered
        BlockchainsystemPackage.eINSTANCE.eClass();

        // Extract folder and base filenames
        String folderName = Paths.get(uri).getParent().getFileName().toString();
        String fileName = Paths.get(uri).getFileName().toString();
        String baseName = Files.getNameWithoutExtension(fileName);

        /*
         * Load all EMF resources that together form one blockchain system.
         * These files are generated beforehand by the Python pipeline.
         */
        resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".nodeallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".blockchainsystemComponentRepository"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".geographicalregions"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".linkallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".transactions"), true);

        /*
         * Resolve all cross-resource EMF references.
         * Loop until no new resources are added during resolution.
         */
        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());

        // The first resource always contains the BlockchainSystem root
        return (BlockchainSystem) currentResources
                .get(0)
                .getContents()
                .getFirst();
    }

    /*
     * NOTE:
     * Runtime configuration overrides were intentionally REMOVED.
     * All parameter variation happens BEFORE simulation via Python-generated models.
     */

    /**
     * Creates a plugin-relative URI for EMF resource loading.
     *
     * NOTE:
     * - We intentionally use platform:/plugin URIs because the simulator
     *   is executed inside an Eclipse/PDE environment.
     */
    private URI createRelativePluginURI(String folder, String relativePath) {
        String path =
                Paths.get(
                        "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                        relativePath
                ).toString();
        return URI.createPlatformPluginURI(path, false);
    }
}
