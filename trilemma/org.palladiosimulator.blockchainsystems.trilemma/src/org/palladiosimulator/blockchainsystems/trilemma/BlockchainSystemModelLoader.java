package org.palladiosimulator.blockchainsystems.trilemma;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.io.Files;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

/**
 * Loads a complete blockchain system model including all dependent resources.
 *
 * IMPORTANT:
 * ----------
 * EMF model loading relies on side effects.
 * Resources are explicitly loaded to ensure that cross-references
 * across files (.p2pnetwork, .nodeallocation, etc.) can be resolved.
 *
 * Unused-variable warnings are intentionally avoided by not assigning
 * loaded resources to local variables.
 */
public class BlockchainSystemModelLoader {

    private final ResourceSet resourceSet = new ResourceSetImpl();

    public BlockchainSystem load(String uri) {

        // Ensure the Ecore package is registered
        BlockchainsystemPackage.eINSTANCE.eClass();

        String folderName = Paths.get(uri).getParent().getFileName().toString();
        String blockchainSystemFileName = Paths.get(uri).getFileName().toString();
        String nameWithoutExtension = Files.getNameWithoutExtension(blockchainSystemFileName);

        // Load main blockchain system model
        resourceSet.getResource(
            createRelativePluginURI(folderName, blockchainSystemFileName), true);

        // Load dependent resources (side-effect based EMF loading)
        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".p2pnetwork"), true);

        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".nodeallocation"), true);

        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".blockchainsystemComponentRepository"), true);

        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".geographicalregions"), true);

        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".linkallocation"), true);

        resourceSet.getResource(
            createRelativePluginURI(folderName, nameWithoutExtension + ".transactions"), true);

        // Resolve all cross-references until no new resources are added
        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());

        // The first resource contains the BlockchainSystem root element
        return (BlockchainSystem)
            resourceSet.getResources().get(0).getContents().get(0);
    }

    /**
     * Creates a platform plugin URI relative to the testmodels folder.
     */
    private URI createRelativePluginURI(String folder, String relativePath) {
        String path = Paths.get(
            "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
            relativePath
        ).toString();

        return URI.createPlatformPluginURI(path, false);
    }
}
