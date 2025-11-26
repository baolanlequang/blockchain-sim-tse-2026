/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.impl;

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

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsFactory;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification;

import org.palladiosimulator.pcm.PcmPackage;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransactionsPackageImpl extends EPackageImpl implements TransactionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transactionsSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transactionPropertiesSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transactionPropertiesSpecificationValueEClass = null;

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
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransactionsPackageImpl() {
		super(eNS_URI, TransactionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TransactionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransactionsPackage init() {
		if (isInited)
			return (TransactionsPackage) EPackage.Registry.INSTANCE.getEPackage(TransactionsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTransactionsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TransactionsPackageImpl theTransactionsPackage = registeredTransactionsPackage instanceof TransactionsPackageImpl
				? (TransactionsPackageImpl) registeredTransactionsPackage
				: new TransactionsPackageImpl();

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

		// Create package meta-data objects
		theTransactionsPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theP2pnetworkPackage.createPackageContents();
		theBlockchainsystemPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theGeographicalregionsPackage.createPackageContents();
		theLinkallocationPackage.createPackageContents();

		// Initialize created meta-data
		theTransactionsPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theP2pnetworkPackage.initializePackageContents();
		theBlockchainsystemPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theGeographicalregionsPackage.initializePackageContents();
		theLinkallocationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransactionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransactionsPackage.eNS_URI, theTransactionsPackage);
		return theTransactionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransactionsSpecification() {
		return transactionsSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransactionsSpecification_MeanTransactionCreationInterval() {
		return (EAttribute) transactionsSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransactionsSpecification_TransactionPropertiesSpecification() {
		return (EReference) transactionsSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransactionPropertiesSpecification() {
		return transactionPropertiesSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransactionPropertiesSpecification_Values() {
		return (EReference) transactionPropertiesSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransactionPropertiesSpecificationValue() {
		return transactionPropertiesSpecificationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransactionPropertiesSpecificationValue_Size() {
		return (EAttribute) transactionPropertiesSpecificationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransactionPropertiesSpecificationValue_Fee() {
		return (EAttribute) transactionPropertiesSpecificationValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransactionPropertiesSpecificationValue_Probability() {
		return (EAttribute) transactionPropertiesSpecificationValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransactionPropertiesSpecificationValue_Amount() {
		return (EAttribute) transactionPropertiesSpecificationValueEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionsFactory getTransactionsFactory() {
		return (TransactionsFactory) getEFactoryInstance();
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
		transactionsSpecificationEClass = createEClass(TRANSACTIONS_SPECIFICATION);
		createEAttribute(transactionsSpecificationEClass,
				TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL);
		createEReference(transactionsSpecificationEClass,
				TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION);

		transactionPropertiesSpecificationEClass = createEClass(TRANSACTION_PROPERTIES_SPECIFICATION);
		createEReference(transactionPropertiesSpecificationEClass, TRANSACTION_PROPERTIES_SPECIFICATION__VALUES);

		transactionPropertiesSpecificationValueEClass = createEClass(TRANSACTION_PROPERTIES_SPECIFICATION_VALUE);
		createEAttribute(transactionPropertiesSpecificationValueEClass,
				TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE);
		createEAttribute(transactionPropertiesSpecificationValueEClass,
				TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE);
		createEAttribute(transactionPropertiesSpecificationValueEClass,
				TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY);
		createEAttribute(transactionPropertiesSpecificationValueEClass,
				TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT);
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
		transactionsSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		transactionPropertiesSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		transactionPropertiesSpecificationValueEClass.getESuperTypes().add(theEntityPackage.getEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(transactionsSpecificationEClass, TransactionsSpecification.class, "TransactionsSpecification",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransactionsSpecification_MeanTransactionCreationInterval(), theEcorePackage.getEDouble(),
				"MeanTransactionCreationInterval", null, 1, 1, TransactionsSpecification.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransactionsSpecification_TransactionPropertiesSpecification(),
				this.getTransactionPropertiesSpecification(), null, "TransactionPropertiesSpecification", null, 1, 1,
				TransactionsSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transactionPropertiesSpecificationEClass, TransactionPropertiesSpecification.class,
				"TransactionPropertiesSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransactionPropertiesSpecification_Values(),
				this.getTransactionPropertiesSpecificationValue(), null, "Values", null, 1, -1,
				TransactionPropertiesSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transactionPropertiesSpecificationValueEClass, TransactionPropertiesSpecificationValue.class,
				"TransactionPropertiesSpecificationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransactionPropertiesSpecificationValue_Size(), theEcorePackage.getEInt(), "Size", null, 1, 1,
				TransactionPropertiesSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransactionPropertiesSpecificationValue_Fee(), theEcorePackage.getEDouble(), "Fee", null, 1,
				1, TransactionPropertiesSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransactionPropertiesSpecificationValue_Probability(), theEcorePackage.getEDouble(),
				"Probability", null, 1, 1, TransactionPropertiesSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransactionPropertiesSpecificationValue_Amount(), theEcorePackage.getEDouble(), "Amount",
				null, 0, 1, TransactionPropertiesSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TransactionsPackageImpl
