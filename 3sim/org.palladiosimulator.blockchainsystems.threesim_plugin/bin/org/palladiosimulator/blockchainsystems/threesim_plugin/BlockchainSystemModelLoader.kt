package org.palladiosimulator.blockchainsystems.threesim_plugin

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl

/**
 * Load [BlockchainSystem] from the ecore metamodel.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainSystemModelLoader {
  fun load(uri: String): BlockchainSystem {
    BlockchainsystemPackage.eINSTANCE.eClass()

    with(Resource.Factory.Registry.INSTANCE.extensionToFactoryMap) {
      put("blockchainsystem", XMIResourceFactoryImpl())
      put("p2pnetwork", XMIResourceFactoryImpl())
      put("nodeallocation", XMIResourceFactoryImpl())
      put("blockchainsystemComponentRepository", XMIResourceFactoryImpl())
      put("nodesystem", XMIResourceFactoryImpl())
      put("nodeenvironment", XMIResourceFactoryImpl())
      put("geographcalregions", XMIResourceFactoryImpl())
      put("linkallocation", XMIResourceFactoryImpl())
      put("transactions", XMIResourceFactoryImpl())
    }

    // Obtain a new resource set
    val resSet: ResourceSet = ResourceSetImpl()

    // Get the resource
    val resource = resSet.getResource(URI.createURI(uri), true)

    EcoreUtil.resolveAll(resSet)

    return resource.contents.first() as BlockchainSystemImpl
  }
}