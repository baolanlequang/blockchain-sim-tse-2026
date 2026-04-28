package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;

import com.google.common.io.Files;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map; // ✅ FIX: Map was missing → caused runtime compilation error

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class BlockchainSystemModelLoader {

//    private final ResourceSet resourceSet = new ResourceSetImpl();

    public BlockchainSystem load(String uri) {

        String folderName = Paths.get(uri).getParent().getFileName().toString();
        String fileName = Paths.get(uri).getFileName().toString();
        String baseName = Files.getNameWithoutExtension(fileName);
        
        ResourceSet resourceSet = new ResourceSetImpl();

        try {
        	XMIResourceFactoryImpl xmiFactory = new XMIResourceFactoryImpl();
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("blockchainsystem", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("p2pnetwork", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("bscmrepository", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("nodeallocation", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("geographicalregions", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("linkallocation", xmiFactory);
        	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("transactions", xmiFactory);

        	resourceSet.getPackageRegistry().put(
        		    BlockchainsystemPackage.eNS_URI, 
        		    BlockchainsystemPackage.eINSTANCE
        		    );
        	resourceSet.getPackageRegistry().put(
        			P2pnetworkPackage.eNS_URI, 
        			P2pnetworkPackage.eINSTANCE
        		    );
        	resourceSet.getPackageRegistry().put(
        			NodeallocationPackage.eNS_URI, 
        			NodeallocationPackage.eINSTANCE
        		    );
        	resourceSet.getPackageRegistry().put(
        			BlockchainsystemComponentRepositoryPackage.eNS_URI, 
        			BlockchainsystemComponentRepositoryPackage.eINSTANCE
        		    );
        	
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, fileName), createFilePluginURI(folderName, fileName));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), createFilePluginURI(folderName, baseName + ".p2pnetwork"));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".nodeallocation"), createFilePluginURI(folderName, baseName + ".nodeallocation"));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".bscmrepository"), createFilePluginURI(folderName, baseName + ".bscmrepository"));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".geographicalregions"), createFilePluginURI(folderName, baseName + ".geographicalregions"));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".linkallocation"), createFilePluginURI(folderName, baseName + ".linkallocation"));
	        resourceSet.getURIConverter().getURIMap().put(createRelativePluginURI(folderName, baseName + ".transactions"), createFilePluginURI(folderName, baseName + ".transactions"));
	        
	        resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".nodeallocation"), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".bscmrepository"), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".geographicalregions"), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".linkallocation"), true);
	        resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".transactions"), true);
        } catch (Exception e) {
        	resourceSet = null;
        	resourceSet = new ResourceSetImpl();
        	BlockchainsystemPackage.eINSTANCE.eClass();
        	
        	resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".p2pnetwork"), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".nodeallocation"), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".bscmrepository"), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".geographicalregions"), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".linkallocation"), true);
            resourceSet.getResource(createRelativePluginURI(folderName, baseName + ".transactions"), true);
        }

        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (final Resource r : currentResources) {
            	EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());
        
        return (BlockchainSystem) currentResources
                .get(0)
                .getContents()
                .get(0);
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
    
    private URI createFilePluginURI(String folder, String relativePath) {
        String path =
                Paths.get(
                        "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                        relativePath)
                     .toString();
        return URI.createURI(path);
    }
}
