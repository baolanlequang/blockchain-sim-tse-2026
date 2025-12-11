package org.palladiosimulator.blockchainsystems.trilemma;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class BlockchainSystemModelLoader {
	public BlockchainSystem load(String uri) {
		// TODO: need to be implement
		BlockchainsystemPackage.eINSTANCE.eClass();
		
		Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> map = registry.getExtensionToFactoryMap();
	    map.put("blockchainsystem", new XMIResourceFactoryImpl());
	    map.put("p2pnetwork", new XMIResourceFactoryImpl());
	    map.put("nodeallocation", new XMIResourceFactoryImpl());
	    map.put("blockchainsystemComponentRepository", new XMIResourceFactoryImpl());
	    map.put("nodesystem", new XMIResourceFactoryImpl());
	    map.put("nodeenvironment", new XMIResourceFactoryImpl());
	    map.put("geographcalregions", new XMIResourceFactoryImpl());
	    map.put("linkallocation", new XMIResourceFactoryImpl());
	    map.put("transactions", new XMIResourceFactoryImpl());
		
		
		// Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    var resource = resSet.getResource(URI.createURI(uri), true);

	    EcoreUtil.resolveAll(resSet);

	    return (BlockchainSystem) resource.getContents().getFirst();
	}

}
