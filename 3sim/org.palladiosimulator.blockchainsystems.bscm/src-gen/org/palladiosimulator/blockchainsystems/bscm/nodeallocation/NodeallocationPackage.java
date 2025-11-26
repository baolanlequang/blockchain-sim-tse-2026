/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationFactory
 * @model kind="package"
 * @generated
 */
public interface NodeallocationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nodeallocation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/NodeAllocation/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nodeallocation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodeallocationPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl <em>Node Allocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocation()
	 * @generated
	 */
	int NODE_ALLOCATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation Contexts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__ALLOCATION_CONTEXTS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node Allocation Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Node System</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__NODE_SYSTEM = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Geographical Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION__GEOGRAPHICAL_REGION = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Node Allocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationRepositoryImpl <em>Node Allocation Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationRepositoryImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocationRepository()
	 * @generated
	 */
	int NODE_ALLOCATION_REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_REPOSITORY__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_REPOSITORY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Node Allocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Allocation Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_REPOSITORY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationContextImpl <em>Node Allocation Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationContextImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocationContext()
	 * @generated
	 */
	int NODE_ALLOCATION_CONTEXT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_CONTEXT__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_CONTEXT__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Assembly Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Container</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_CONTEXT__RESOURCE_CONTAINER = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Node Allocation Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ALLOCATION_CONTEXT_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation <em>Node Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
	 * @generated
	 */
	EClass getNodeAllocation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getAllocationContexts <em>Allocation Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Allocation Contexts</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getAllocationContexts()
	 * @see #getNodeAllocation()
	 * @generated
	 */
	EReference getNodeAllocation_AllocationContexts();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeAllocationEnvironment <em>Node Allocation Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Node Allocation Environment</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeAllocationEnvironment()
	 * @see #getNodeAllocation()
	 * @generated
	 */
	EReference getNodeAllocation_NodeAllocationEnvironment();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeSystem <em>Node System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Node System</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeSystem()
	 * @see #getNodeAllocation()
	 * @generated
	 */
	EReference getNodeAllocation_NodeSystem();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getGeographicalRegion <em>Geographical Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Geographical Region</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getGeographicalRegion()
	 * @see #getNodeAllocation()
	 * @generated
	 */
	EReference getNodeAllocation_GeographicalRegion();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository <em>Node Allocation Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Allocation Repository</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository
	 * @generated
	 */
	EClass getNodeAllocationRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository#getNodeAllocations <em>Node Allocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node Allocations</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository#getNodeAllocations()
	 * @see #getNodeAllocationRepository()
	 * @generated
	 */
	EReference getNodeAllocationRepository_NodeAllocations();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext <em>Node Allocation Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Allocation Context</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext
	 * @generated
	 */
	EClass getNodeAllocationContext();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getAssemblyContext <em>Assembly Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Assembly Context</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getAssemblyContext()
	 * @see #getNodeAllocationContext()
	 * @generated
	 */
	EReference getNodeAllocationContext_AssemblyContext();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getResourceContainer <em>Resource Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource Container</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getResourceContainer()
	 * @see #getNodeAllocationContext()
	 * @generated
	 */
	EReference getNodeAllocationContext_ResourceContainer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NodeallocationFactory getNodeallocationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl <em>Node Allocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocation()
		 * @generated
		 */
		EClass NODE_ALLOCATION = eINSTANCE.getNodeAllocation();

		/**
		 * The meta object literal for the '<em><b>Allocation Contexts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION__ALLOCATION_CONTEXTS = eINSTANCE.getNodeAllocation_AllocationContexts();

		/**
		 * The meta object literal for the '<em><b>Node Allocation Environment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT = eINSTANCE
				.getNodeAllocation_NodeAllocationEnvironment();

		/**
		 * The meta object literal for the '<em><b>Node System</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION__NODE_SYSTEM = eINSTANCE.getNodeAllocation_NodeSystem();

		/**
		 * The meta object literal for the '<em><b>Geographical Region</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION__GEOGRAPHICAL_REGION = eINSTANCE.getNodeAllocation_GeographicalRegion();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationRepositoryImpl <em>Node Allocation Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationRepositoryImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocationRepository()
		 * @generated
		 */
		EClass NODE_ALLOCATION_REPOSITORY = eINSTANCE.getNodeAllocationRepository();

		/**
		 * The meta object literal for the '<em><b>Node Allocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS = eINSTANCE
				.getNodeAllocationRepository_NodeAllocations();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationContextImpl <em>Node Allocation Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationContextImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl#getNodeAllocationContext()
		 * @generated
		 */
		EClass NODE_ALLOCATION_CONTEXT = eINSTANCE.getNodeAllocationContext();

		/**
		 * The meta object literal for the '<em><b>Assembly Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT = eINSTANCE.getNodeAllocationContext_AssemblyContext();

		/**
		 * The meta object literal for the '<em><b>Resource Container</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ALLOCATION_CONTEXT__RESOURCE_CONTAINER = eINSTANCE.getNodeAllocationContext_ResourceContainer();

	}

} //NodeallocationPackage
