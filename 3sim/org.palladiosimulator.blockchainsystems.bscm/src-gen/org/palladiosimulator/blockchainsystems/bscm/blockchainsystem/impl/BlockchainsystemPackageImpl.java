/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl;

import de.uka.ipd.sdq.identifier.IdentifierPackage;

import de.uka.ipd.sdq.probfunction.ProbfunctionPackage;

import de.uka.ipd.sdq.stoex.StoexPackage;

import de.uka.ipd.sdq.units.UnitsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemFactory;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;

import org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl;

import org.palladiosimulator.pcm.PcmPackage;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BlockchainsystemPackageImpl extends EPackageImpl implements BlockchainsystemPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockchainSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockchainSystemSpecificationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BlockchainsystemPackageImpl() {
		super(eNS_URI, BlockchainsystemFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link BlockchainsystemPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BlockchainsystemPackage init() {
		if (isInited)
			return (BlockchainsystemPackage) EPackage.Registry.INSTANCE.getEPackage(BlockchainsystemPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBlockchainsystemPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BlockchainsystemPackageImpl theBlockchainsystemPackage = registeredBlockchainsystemPackage instanceof BlockchainsystemPackageImpl
				? (BlockchainsystemPackageImpl) registeredBlockchainsystemPackage
				: new BlockchainsystemPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		PcmPackage.eINSTANCE.eClass();
		IdentifierPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		StoexPackage.eINSTANCE.eClass();
		UnitsPackage.eINSTANCE.eClass();
		ProbfunctionPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NodeallocationPackage.eNS_URI);
		NodeallocationPackageImpl theNodeallocationPackage = (NodeallocationPackageImpl) (registeredPackage instanceof NodeallocationPackageImpl
				? registeredPackage
				: NodeallocationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NodesystemPackage.eNS_URI);
		NodesystemPackageImpl theNodesystemPackage = (NodesystemPackageImpl) (registeredPackage instanceof NodesystemPackageImpl
				? registeredPackage
				: NodesystemPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BlockchainsystemComponentRepositoryPackage.eNS_URI);
		BlockchainsystemComponentRepositoryPackageImpl theBlockchainsystemComponentRepositoryPackage = (BlockchainsystemComponentRepositoryPackageImpl) (registeredPackage instanceof BlockchainsystemComponentRepositoryPackageImpl
				? registeredPackage
				: BlockchainsystemComponentRepositoryPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(P2pnetworkPackage.eNS_URI);
		P2pnetworkPackageImpl theP2pnetworkPackage = (P2pnetworkPackageImpl) (registeredPackage instanceof P2pnetworkPackageImpl
				? registeredPackage
				: P2pnetworkPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(NodeenvironmentPackage.eNS_URI);
		NodeenvironmentPackageImpl theNodeenvironmentPackage = (NodeenvironmentPackageImpl) (registeredPackage instanceof NodeenvironmentPackageImpl
				? registeredPackage
				: NodeenvironmentPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(GeographicalregionsPackage.eNS_URI);
		GeographicalregionsPackageImpl theGeographicalregionsPackage = (GeographicalregionsPackageImpl) (registeredPackage instanceof GeographicalregionsPackageImpl
				? registeredPackage
				: GeographicalregionsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LinkallocationPackage.eNS_URI);
		LinkallocationPackageImpl theLinkallocationPackage = (LinkallocationPackageImpl) (registeredPackage instanceof LinkallocationPackageImpl
				? registeredPackage
				: LinkallocationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TransactionsPackage.eNS_URI);
		TransactionsPackageImpl theTransactionsPackage = (TransactionsPackageImpl) (registeredPackage instanceof TransactionsPackageImpl
				? registeredPackage
				: TransactionsPackage.eINSTANCE);

		// Create package meta-data objects
		theBlockchainsystemPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theP2pnetworkPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theGeographicalregionsPackage.createPackageContents();
		theLinkallocationPackage.createPackageContents();
		theTransactionsPackage.createPackageContents();

		// Initialize created meta-data
		theBlockchainsystemPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theP2pnetworkPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theGeographicalregionsPackage.initializePackageContents();
		theLinkallocationPackage.initializePackageContents();
		theTransactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBlockchainsystemPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BlockchainsystemPackage.eNS_URI, theBlockchainsystemPackage);
		return theBlockchainsystemPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockchainSystem() {
		return blockchainSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockchainSystem_Network() {
		return (EReference) blockchainSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockchainSystem_Specification() {
		return (EReference) blockchainSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockchainSystem_TransactionsSpecification() {
		return (EReference) blockchainSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockchainSystem_GeographicalRegionsSpecification() {
		return (EReference) blockchainSystemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockchainSystemSpecification() {
		return blockchainSystemSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_MeanBlockTime() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_NumOfRequiredSecurityConfirmations() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_MaxBlockSize() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_BlockReward() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_HashRateConcentration() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockchainSystemSpecification_NumberOfAttacker() {
		return (EAttribute) blockchainSystemSpecificationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainsystemFactory getBlockchainsystemFactory() {
		return (BlockchainsystemFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		blockchainSystemEClass = createEClass(BLOCKCHAIN_SYSTEM);
		createEReference(blockchainSystemEClass, BLOCKCHAIN_SYSTEM__NETWORK);
		createEReference(blockchainSystemEClass, BLOCKCHAIN_SYSTEM__SPECIFICATION);
		createEReference(blockchainSystemEClass, BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION);
		createEReference(blockchainSystemEClass, BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION);

		blockchainSystemSpecificationEClass = createEClass(BLOCKCHAIN_SYSTEM_SPECIFICATION);
		createEAttribute(blockchainSystemSpecificationEClass, BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME);
		createEAttribute(blockchainSystemSpecificationEClass,
				BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS);
		createEAttribute(blockchainSystemSpecificationEClass, BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE);
		createEAttribute(blockchainSystemSpecificationEClass, BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD);
		createEAttribute(blockchainSystemSpecificationEClass, BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION);
		createEAttribute(blockchainSystemSpecificationEClass, BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EntityPackage theEntityPackage = (EntityPackage) EPackage.Registry.INSTANCE.getEPackage(EntityPackage.eNS_URI);
		P2pnetworkPackage theP2pnetworkPackage = (P2pnetworkPackage) EPackage.Registry.INSTANCE
				.getEPackage(P2pnetworkPackage.eNS_URI);
		TransactionsPackage theTransactionsPackage = (TransactionsPackage) EPackage.Registry.INSTANCE
				.getEPackage(TransactionsPackage.eNS_URI);
		GeographicalregionsPackage theGeographicalregionsPackage = (GeographicalregionsPackage) EPackage.Registry.INSTANCE
				.getEPackage(GeographicalregionsPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		blockchainSystemEClass.getESuperTypes().add(theEntityPackage.getEntity());
		blockchainSystemSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(blockchainSystemEClass, BlockchainSystem.class, "BlockchainSystem", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockchainSystem_Network(), theP2pnetworkPackage.getP2PNetwork(), null, "Network", null, 1, 1,
				BlockchainSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockchainSystem_Specification(), this.getBlockchainSystemSpecification(), null,
				"Specification", null, 1, 1, BlockchainSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockchainSystem_TransactionsSpecification(),
				theTransactionsPackage.getTransactionsSpecification(), null, "TransactionsSpecification", null, 1, 1,
				BlockchainSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockchainSystem_GeographicalRegionsSpecification(),
				theGeographicalregionsPackage.getGeographicalRegionsSpecification(), null,
				"GeographicalRegionsSpecification", null, 1, 1, BlockchainSystem.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockchainSystemSpecificationEClass, BlockchainSystemSpecification.class,
				"BlockchainSystemSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBlockchainSystemSpecification_MeanBlockTime(), theEcorePackage.getEDouble(), "MeanBlockTime",
				null, 1, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockchainSystemSpecification_NumOfRequiredSecurityConfirmations(), theEcorePackage.getEInt(),
				"NumOfRequiredSecurityConfirmations", null, 1, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockchainSystemSpecification_MaxBlockSize(), theEcorePackage.getEInt(), "MaxBlockSize", null,
				1, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockchainSystemSpecification_BlockReward(), theEcorePackage.getEDouble(), "BlockReward",
				null, 0, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockchainSystemSpecification_HashRateConcentration(), theEcorePackage.getEDouble(),
				"HashRateConcentration", null, 1, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockchainSystemSpecification_NumberOfAttacker(), theEcorePackage.getEInt(),
				"NumberOfAttacker", "0", 1, 1, BlockchainSystemSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //BlockchainsystemPackageImpl
