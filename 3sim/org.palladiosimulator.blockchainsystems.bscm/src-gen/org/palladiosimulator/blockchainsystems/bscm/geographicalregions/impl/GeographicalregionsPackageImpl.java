/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl;

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

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsFactory;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage;

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
public class GeographicalregionsPackageImpl extends EPackageImpl implements GeographicalregionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geographicalRegionsSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geographicalRegionEClass = null;

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
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GeographicalregionsPackageImpl() {
		super(eNS_URI, GeographicalregionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GeographicalregionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GeographicalregionsPackage init() {
		if (isInited)
			return (GeographicalregionsPackage) EPackage.Registry.INSTANCE
					.getEPackage(GeographicalregionsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGeographicalregionsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GeographicalregionsPackageImpl theGeographicalregionsPackage = registeredGeographicalregionsPackage instanceof GeographicalregionsPackageImpl
				? (GeographicalregionsPackageImpl) registeredGeographicalregionsPackage
				: new GeographicalregionsPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LinkallocationPackage.eNS_URI);
		LinkallocationPackageImpl theLinkallocationPackage = (LinkallocationPackageImpl) (registeredPackage instanceof LinkallocationPackageImpl
				? registeredPackage
				: LinkallocationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TransactionsPackage.eNS_URI);
		TransactionsPackageImpl theTransactionsPackage = (TransactionsPackageImpl) (registeredPackage instanceof TransactionsPackageImpl
				? registeredPackage
				: TransactionsPackage.eINSTANCE);

		// Create package meta-data objects
		theGeographicalregionsPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theP2pnetworkPackage.createPackageContents();
		theBlockchainsystemPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theLinkallocationPackage.createPackageContents();
		theTransactionsPackage.createPackageContents();

		// Initialize created meta-data
		theGeographicalregionsPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theP2pnetworkPackage.initializePackageContents();
		theBlockchainsystemPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theLinkallocationPackage.initializePackageContents();
		theTransactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGeographicalregionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GeographicalregionsPackage.eNS_URI, theGeographicalregionsPackage);
		return theGeographicalregionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeographicalRegionsSpecification() {
		return geographicalRegionsSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeographicalRegionsSpecification_Regions() {
		return (EReference) geographicalRegionsSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeographicalRegion() {
		return geographicalRegionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeographicalRegion_RegionName() {
		return (EAttribute) geographicalRegionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalregionsFactory getGeographicalregionsFactory() {
		return (GeographicalregionsFactory) getEFactoryInstance();
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
		geographicalRegionsSpecificationEClass = createEClass(GEOGRAPHICAL_REGIONS_SPECIFICATION);
		createEReference(geographicalRegionsSpecificationEClass, GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS);

		geographicalRegionEClass = createEClass(GEOGRAPHICAL_REGION);
		createEAttribute(geographicalRegionEClass, GEOGRAPHICAL_REGION__REGION_NAME);
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
		geographicalRegionsSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		geographicalRegionEClass.getESuperTypes().add(theEntityPackage.getEntity());

		// Initialize classes and features; add operations and parameters
		initEClass(geographicalRegionsSpecificationEClass, GeographicalRegionsSpecification.class,
				"GeographicalRegionsSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGeographicalRegionsSpecification_Regions(), this.getGeographicalRegion(), null, "Regions",
				null, 1, -1, GeographicalRegionsSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(geographicalRegionEClass, GeographicalRegion.class, "GeographicalRegion", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGeographicalRegion_RegionName(), theEcorePackage.getEString(), "RegionName", null, 1, 1,
				GeographicalRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //GeographicalregionsPackageImpl
