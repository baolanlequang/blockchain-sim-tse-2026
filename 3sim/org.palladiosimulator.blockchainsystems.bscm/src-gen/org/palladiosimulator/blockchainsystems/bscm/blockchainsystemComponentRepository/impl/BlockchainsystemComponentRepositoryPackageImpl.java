/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl;

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

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryFactory;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent;

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
public class BlockchainsystemComponentRepositoryPackageImpl extends EPackageImpl
		implements BlockchainsystemComponentRepositoryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockchainSystemNodeComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockchainSystemNodeComponentRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockValidatorComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass miningProcessComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockValiationDurationSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockValidationDurationValueEClass = null;

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
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BlockchainsystemComponentRepositoryPackageImpl() {
		super(eNS_URI, BlockchainsystemComponentRepositoryFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BlockchainsystemComponentRepositoryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BlockchainsystemComponentRepositoryPackage init() {
		if (isInited)
			return (BlockchainsystemComponentRepositoryPackage) EPackage.Registry.INSTANCE
					.getEPackage(BlockchainsystemComponentRepositoryPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBlockchainsystemComponentRepositoryPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BlockchainsystemComponentRepositoryPackageImpl theBlockchainsystemComponentRepositoryPackage = registeredBlockchainsystemComponentRepositoryPackage instanceof BlockchainsystemComponentRepositoryPackageImpl
				? (BlockchainsystemComponentRepositoryPackageImpl) registeredBlockchainsystemComponentRepositoryPackage
				: new BlockchainsystemComponentRepositoryPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(P2pnetworkPackage.eNS_URI);
		P2pnetworkPackageImpl theP2pnetworkPackage = (P2pnetworkPackageImpl) (registeredPackage instanceof P2pnetworkPackageImpl
				? registeredPackage
				: P2pnetworkPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BlockchainsystemPackage.eNS_URI);
		BlockchainsystemPackageImpl theBlockchainsystemPackage = (BlockchainsystemPackageImpl) (registeredPackage instanceof BlockchainsystemPackageImpl
				? registeredPackage
				: BlockchainsystemPackage.eINSTANCE);
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
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theP2pnetworkPackage.createPackageContents();
		theBlockchainsystemPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theGeographicalregionsPackage.createPackageContents();
		theLinkallocationPackage.createPackageContents();
		theTransactionsPackage.createPackageContents();

		// Initialize created meta-data
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theP2pnetworkPackage.initializePackageContents();
		theBlockchainsystemPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theGeographicalregionsPackage.initializePackageContents();
		theLinkallocationPackage.initializePackageContents();
		theTransactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBlockchainsystemComponentRepositoryPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BlockchainsystemComponentRepositoryPackage.eNS_URI,
				theBlockchainsystemComponentRepositoryPackage);
		return theBlockchainsystemComponentRepositoryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockchainSystemNodeComponent() {
		return blockchainSystemNodeComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockchainSystemNodeComponentRepository() {
		return blockchainSystemNodeComponentRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockchainSystemNodeComponentRepository_Components() {
		return (EReference) blockchainSystemNodeComponentRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockValidatorComponent() {
		return blockValidatorComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockValidatorComponent_ValidationDuration() {
		return (EReference) blockValidatorComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockValidatorComponent_Crashed() {
		return (EAttribute) blockValidatorComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMiningProcessComponent() {
		return miningProcessComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMiningProcessComponent_IsMiningProcessEnabled() {
		return (EAttribute) miningProcessComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockValiationDurationSpecification() {
		return blockValiationDurationSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockValiationDurationSpecification_Values() {
		return (EReference) blockValiationDurationSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockValidationDurationValue() {
		return blockValidationDurationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockValidationDurationValue_Duration() {
		return (EAttribute) blockValidationDurationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockValidationDurationValue_Probability() {
		return (EAttribute) blockValidationDurationValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainsystemComponentRepositoryFactory getBlockchainsystemComponentRepositoryFactory() {
		return (BlockchainsystemComponentRepositoryFactory) getEFactoryInstance();
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
		blockchainSystemNodeComponentEClass = createEClass(BLOCKCHAIN_SYSTEM_NODE_COMPONENT);

		blockchainSystemNodeComponentRepositoryEClass = createEClass(BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY);
		createEReference(blockchainSystemNodeComponentRepositoryEClass,
				BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS);

		blockValidatorComponentEClass = createEClass(BLOCK_VALIDATOR_COMPONENT);
		createEReference(blockValidatorComponentEClass, BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION);
		createEAttribute(blockValidatorComponentEClass, BLOCK_VALIDATOR_COMPONENT__CRASHED);

		miningProcessComponentEClass = createEClass(MINING_PROCESS_COMPONENT);
		createEAttribute(miningProcessComponentEClass, MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED);

		blockValiationDurationSpecificationEClass = createEClass(BLOCK_VALIATION_DURATION_SPECIFICATION);
		createEReference(blockValiationDurationSpecificationEClass, BLOCK_VALIATION_DURATION_SPECIFICATION__VALUES);

		blockValidationDurationValueEClass = createEClass(BLOCK_VALIDATION_DURATION_VALUE);
		createEAttribute(blockValidationDurationValueEClass, BLOCK_VALIDATION_DURATION_VALUE__DURATION);
		createEAttribute(blockValidationDurationValueEClass, BLOCK_VALIDATION_DURATION_VALUE__PROBABILITY);
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
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		blockchainSystemNodeComponentEClass.getESuperTypes().add(theEntityPackage.getEntity());
		blockchainSystemNodeComponentRepositoryEClass.getESuperTypes().add(theEntityPackage.getEntity());
		blockValidatorComponentEClass.getESuperTypes().add(this.getBlockchainSystemNodeComponent());
		miningProcessComponentEClass.getESuperTypes().add(this.getBlockchainSystemNodeComponent());
		blockValiationDurationSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		blockValidationDurationValueEClass.getESuperTypes().add(theEntityPackage.getEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(blockchainSystemNodeComponentEClass, BlockchainSystemNodeComponent.class,
				"BlockchainSystemNodeComponent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(blockchainSystemNodeComponentRepositoryEClass, BlockchainSystemNodeComponentRepository.class,
				"BlockchainSystemNodeComponentRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockchainSystemNodeComponentRepository_Components(), this.getBlockchainSystemNodeComponent(),
				null, "Components", null, 0, -1, BlockchainSystemNodeComponentRepository.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(blockValidatorComponentEClass, BlockValidatorComponent.class, "BlockValidatorComponent",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockValidatorComponent_ValidationDuration(), this.getBlockValiationDurationSpecification(),
				null, "ValidationDuration", null, 1, 1, BlockValidatorComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockValidatorComponent_Crashed(), theEcorePackage.getEBoolean(), "crashed", null, 0, 1,
				BlockValidatorComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(miningProcessComponentEClass, MiningProcessComponent.class, "MiningProcessComponent", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMiningProcessComponent_IsMiningProcessEnabled(), theEcorePackage.getEBoolean(),
				"IsMiningProcessEnabled", null, 1, 1, MiningProcessComponent.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockValiationDurationSpecificationEClass, BlockValiationDurationSpecification.class,
				"BlockValiationDurationSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockValiationDurationSpecification_Values(), this.getBlockValidationDurationValue(), null,
				"Values", null, 1, -1, BlockValiationDurationSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockValidationDurationValueEClass, BlockValidationDurationValue.class,
				"BlockValidationDurationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBlockValidationDurationValue_Duration(), theEcorePackage.getELong(), "Duration", null, 1, 1,
				BlockValidationDurationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockValidationDurationValue_Probability(), theEcorePackage.getEDouble(), "Probability", null,
				1, 1, BlockValidationDurationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //BlockchainsystemComponentRepositoryPackageImpl
