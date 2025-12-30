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

public class BlockchainSystemModelLoader  {
	
	private ResourceSet resourceSet = new ResourceSetImpl();
	
	public BlockchainSystem load(String uri) {
		// TODO: need to be implement
		BlockchainsystemPackage.eINSTANCE.eClass();
		
		String folderName = Paths.get(uri).getParent().getFileName().toString();
		String blockChainSystemFileName = Paths.get(uri).getFileName().toString();
		String nameWithoutExtension = Files.getNameWithoutExtension(blockChainSystemFileName);
		
//		Files.getNameWithoutExtension("blockChainSystemFileName.blockchainsystem");

	    // Get the resource
		var blockchainsystem = resourceSet.getResource(createRelativePluginURI(folderName, blockChainSystemFileName), true);
	    var networkResource = resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".p2pnetwork"), true);
	    resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".nodeallocation"), true);
	    resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".blockchainsystemComponentRepository"), true);
//	    resourceSet.getResource(createRelativePluginURI("Net.nodesystem"), true);
//	    resourceSet.getResource(createRelativePluginURI("Net.nodeenvironment"), true);
	    resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".geographicalregions"), true);
	    resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".linkallocation"), true);
	    var transactionResource = resourceSet.getResource(createRelativePluginURI(folderName, nameWithoutExtension + ".transactions"), true);
	    
	    
	    ArrayList<Resource> currentResources = null;

        do {
            currentResources = new ArrayList<Resource>(resourceSet.getResources());
            for (final Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources()
            .size());
        
//        System.out.println("currentResources " + (currentResources.size()));
//        System.out.println("currentResources " + ((P2PNetwork)currentResources.get(1).getContents().getFirst()).getTopology());
//        System.out.println("currentResources " + ((BlockchainSystem) currentResources.get(0).getContents().get(0)).getNetwork().getTopology());
        
        
//        initEMFProfiles();
        
        

	    return (BlockchainSystem) currentResources.get(0).getContents().getFirst();
	}
	
	private URI createRelativePluginURI(String folder, String relativePath) {
		String path = Paths.get("org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder, relativePath).toString();
		return URI.createPlatformPluginURI(path, false);
	}

}
