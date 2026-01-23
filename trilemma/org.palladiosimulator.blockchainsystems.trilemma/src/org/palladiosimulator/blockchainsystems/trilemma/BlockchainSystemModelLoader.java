package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import com.google.common.io.Files;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map; // ✅ FIX: Map was missing → caused runtime compilation error

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class BlockchainSystemModelLoader {

    private final ResourceSet resourceSet = new ResourceSetImpl();

    public BlockchainSystem load(String uri) {

        BlockchainsystemPackage.eINSTANCE.eClass();

        String folderName = Paths.get(uri).getParent().getFileName().toString();
        String fileName = Paths.get(uri).getFileName().toString();
        String baseName = Files.getNameWithoutExtension(fileName);

        resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".nodeallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".blockchainsystemComponentRepository"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".geographicalregions"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".linkallocation"), true);
        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".transactions"), true);

        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());

        return (BlockchainSystem) currentResources
                .get(0)
                .getContents()
                .getFirst();
    }

    /**
     * Overload used by TrilemmaSimulationFactory.
     * Configuration is forwarded for future CSV-based model overrides.
     */
    public BlockchainSystem load(String uri, Map<String, String> configuration) {
        return load(uri);
    }

    private URI createRelativePluginURI(String folder, String relativePath) {
        String path =
                Paths.get(
                        "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                        relativePath)
                     .toString();
        return URI.createPlatformPluginURI(path, false);
    }
}
