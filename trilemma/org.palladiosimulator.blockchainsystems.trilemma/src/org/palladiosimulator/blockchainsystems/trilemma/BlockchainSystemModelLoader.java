package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork;

public class BlockchainSystemModelLoader  {
	
	private ResourceSet resourceSet = new ResourceSetImpl();
	
	public BlockchainSystem load(String uri) {
		// TODO: need to be implement
		BlockchainsystemPackage.eINSTANCE.eClass();
		
//		Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
//	    Map<String, Object> map = registry.getExtensionToFactoryMap();
//	    map.put("blockchainsystem", new XMIResourceFactoryImpl());
//	    map.put("p2pnetwork", new XMIResourceFactoryImpl());
//	    map.put("nodeallocation", new XMIResourceFactoryImpl());
//	    map.put("blockchainsystemComponentRepository", new XMIResourceFactoryImpl());
//	    map.put("nodesystem", new XMIResourceFactoryImpl());
//	    map.put("nodeenvironment", new XMIResourceFactoryImpl());
//	    map.put("geographcalregions", new XMIResourceFactoryImpl());
//	    map.put("linkallocation", new XMIResourceFactoryImpl());
//	    map.put("transactions", new XMIResourceFactoryImpl());
		
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
//		        "blockchainsystem", new XMIResourceFactoryImpl()
//		);
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
//		        "p2pnetwork", new XMIResourceFactoryImpl()
//		);
	    

	    // Get the resource
		var blockchainsystem = resourceSet.getResource(createRelativePluginURI("My.blockchainsystem"), true);
	    var networkResource = resourceSet.getResource(createRelativePluginURI("My.p2pnetwork"), true);
	    resourceSet.getResource(createRelativePluginURI("My.nodeallocation"), true);
	    resourceSet.getResource(createRelativePluginURI("My.blockchainsystemComponentRepository"), true);
	    resourceSet.getResource(createRelativePluginURI("My.nodesystem"), true);
	    resourceSet.getResource(createRelativePluginURI("My.nodeenvironment"), true);
	    resourceSet.getResource(createRelativePluginURI("My.geographicalregions"), true);
	    resourceSet.getResource(createRelativePluginURI("My.linkallocation"), true);
	    var transactionResource = resourceSet.getResource(createRelativePluginURI("My.transactions"), true);
	    
	    
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
	
	private URI createRelativePluginURI(String relativePath) {
		String path = Paths.get("org.palladiosimulator.blockchainsystems.trilemma/testmodels", relativePath).toString();
		return URI.createPlatformPluginURI(path, false);
	}

}
