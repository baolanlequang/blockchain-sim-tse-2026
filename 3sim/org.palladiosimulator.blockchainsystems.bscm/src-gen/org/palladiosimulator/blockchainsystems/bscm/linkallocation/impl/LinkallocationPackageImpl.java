/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

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

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationFactory;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification;

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
public class LinkallocationPackageImpl extends EPackageImpl implements LinkallocationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkAllocationRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkAllocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkLatencySpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bandwidthSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicLinkLatencySpecificationValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkThroughputSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicLinkThroughputSpecificationValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicLinkLatencySpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicLinkThroughputSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass staticLinkLatencySpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass staticLinkThroughputSpecificationEClass = null;

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
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LinkallocationPackageImpl() {
		super(eNS_URI, LinkallocationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LinkallocationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LinkallocationPackage init() {
		if (isInited)
			return (LinkallocationPackage) EPackage.Registry.INSTANCE.getEPackage(LinkallocationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLinkallocationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LinkallocationPackageImpl theLinkallocationPackage = registeredLinkallocationPackage instanceof LinkallocationPackageImpl
				? (LinkallocationPackageImpl) registeredLinkallocationPackage
				: new LinkallocationPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TransactionsPackage.eNS_URI);
		TransactionsPackageImpl theTransactionsPackage = (TransactionsPackageImpl) (registeredPackage instanceof TransactionsPackageImpl
				? registeredPackage
				: TransactionsPackage.eINSTANCE);

		// Create package meta-data objects
		theLinkallocationPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theP2pnetworkPackage.createPackageContents();
		theBlockchainsystemPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theGeographicalregionsPackage.createPackageContents();
		theTransactionsPackage.createPackageContents();

		// Initialize created meta-data
		theLinkallocationPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theP2pnetworkPackage.initializePackageContents();
		theBlockchainsystemPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theGeographicalregionsPackage.initializePackageContents();
		theTransactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLinkallocationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LinkallocationPackage.eNS_URI, theLinkallocationPackage);
		return theLinkallocationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLinkAllocationRepository() {
		return linkAllocationRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLinkAllocationRepository_LinkAllocations() {
		return (EReference) linkAllocationRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLinkAllocation() {
		return linkAllocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLinkAllocation_LatencySpecification() {
		return (EReference) linkAllocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLinkAllocation_ThroughputSpecification() {
		return (EReference) linkAllocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLinkAllocation_BandwidthSpecification() {
		return (EReference) linkAllocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLinkLatencySpecification() {
		return linkLatencySpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBandwidthSpecification() {
		return bandwidthSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBandwidthSpecification_Bandwidth() {
		return (EAttribute) bandwidthSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBandwidthSpecification_HeterogeneityLinkTarget() {
		return (EAttribute) bandwidthSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBandwidthSpecification_HeterogeneityNodeTarget() {
		return (EAttribute) bandwidthSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicLinkLatencySpecificationValue() {
		return dynamicLinkLatencySpecificationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkLatencySpecificationValue_Latency() {
		return (EAttribute) dynamicLinkLatencySpecificationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkLatencySpecificationValue_Probability() {
		return (EAttribute) dynamicLinkLatencySpecificationValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkLatencySpecificationValue_Duration() {
		return (EAttribute) dynamicLinkLatencySpecificationValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLinkThroughputSpecification() {
		return linkThroughputSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicLinkThroughputSpecificationValue() {
		return dynamicLinkThroughputSpecificationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkThroughputSpecificationValue_Throughput() {
		return (EAttribute) dynamicLinkThroughputSpecificationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkThroughputSpecificationValue_Probability() {
		return (EAttribute) dynamicLinkThroughputSpecificationValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDynamicLinkThroughputSpecificationValue_Duration() {
		return (EAttribute) dynamicLinkThroughputSpecificationValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicLinkLatencySpecification() {
		return dynamicLinkLatencySpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDynamicLinkLatencySpecification_Values() {
		return (EReference) dynamicLinkLatencySpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicLinkThroughputSpecification() {
		return dynamicLinkThroughputSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDynamicLinkThroughputSpecification_Values() {
		return (EReference) dynamicLinkThroughputSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStaticLinkLatencySpecification() {
		return staticLinkLatencySpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStaticLinkLatencySpecification_Latency() {
		return (EAttribute) staticLinkLatencySpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStaticLinkThroughputSpecification() {
		return staticLinkThroughputSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStaticLinkThroughputSpecification_Throughput() {
		return (EAttribute) staticLinkThroughputSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkallocationFactory getLinkallocationFactory() {
		return (LinkallocationFactory) getEFactoryInstance();
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
		linkAllocationRepositoryEClass = createEClass(LINK_ALLOCATION_REPOSITORY);
		createEReference(linkAllocationRepositoryEClass, LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS);

		linkAllocationEClass = createEClass(LINK_ALLOCATION);
		createEReference(linkAllocationEClass, LINK_ALLOCATION__LATENCY_SPECIFICATION);
		createEReference(linkAllocationEClass, LINK_ALLOCATION__THROUGHPUT_SPECIFICATION);
		createEReference(linkAllocationEClass, LINK_ALLOCATION__BANDWIDTH_SPECIFICATION);

		linkLatencySpecificationEClass = createEClass(LINK_LATENCY_SPECIFICATION);

		bandwidthSpecificationEClass = createEClass(BANDWIDTH_SPECIFICATION);
		createEAttribute(bandwidthSpecificationEClass, BANDWIDTH_SPECIFICATION__BANDWIDTH);
		createEAttribute(bandwidthSpecificationEClass, BANDWIDTH_SPECIFICATION__HETEROGENEITY_LINK_TARGET);
		createEAttribute(bandwidthSpecificationEClass, BANDWIDTH_SPECIFICATION__HETEROGENEITY_NODE_TARGET);

		dynamicLinkLatencySpecificationValueEClass = createEClass(DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE);
		createEAttribute(dynamicLinkLatencySpecificationValueEClass, DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY);
		createEAttribute(dynamicLinkLatencySpecificationValueEClass,
				DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY);
		createEAttribute(dynamicLinkLatencySpecificationValueEClass,
				DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION);

		linkThroughputSpecificationEClass = createEClass(LINK_THROUGHPUT_SPECIFICATION);

		dynamicLinkThroughputSpecificationValueEClass = createEClass(DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE);
		createEAttribute(dynamicLinkThroughputSpecificationValueEClass,
				DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__THROUGHPUT);
		createEAttribute(dynamicLinkThroughputSpecificationValueEClass,
				DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__PROBABILITY);
		createEAttribute(dynamicLinkThroughputSpecificationValueEClass,
				DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__DURATION);

		dynamicLinkLatencySpecificationEClass = createEClass(DYNAMIC_LINK_LATENCY_SPECIFICATION);
		createEReference(dynamicLinkLatencySpecificationEClass, DYNAMIC_LINK_LATENCY_SPECIFICATION__VALUES);

		dynamicLinkThroughputSpecificationEClass = createEClass(DYNAMIC_LINK_THROUGHPUT_SPECIFICATION);
		createEReference(dynamicLinkThroughputSpecificationEClass, DYNAMIC_LINK_THROUGHPUT_SPECIFICATION__VALUES);

		staticLinkLatencySpecificationEClass = createEClass(STATIC_LINK_LATENCY_SPECIFICATION);
		createEAttribute(staticLinkLatencySpecificationEClass, STATIC_LINK_LATENCY_SPECIFICATION__LATENCY);

		staticLinkThroughputSpecificationEClass = createEClass(STATIC_LINK_THROUGHPUT_SPECIFICATION);
		createEAttribute(staticLinkThroughputSpecificationEClass, STATIC_LINK_THROUGHPUT_SPECIFICATION__THROUGHPUT);
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
		linkAllocationRepositoryEClass.getESuperTypes().add(theEntityPackage.getEntity());
		linkAllocationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		linkLatencySpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		bandwidthSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		dynamicLinkLatencySpecificationValueEClass.getESuperTypes().add(theEntityPackage.getEntity());
		linkThroughputSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		dynamicLinkThroughputSpecificationValueEClass.getESuperTypes().add(theEntityPackage.getEntity());
		dynamicLinkLatencySpecificationEClass.getESuperTypes().add(this.getLinkLatencySpecification());
		dynamicLinkThroughputSpecificationEClass.getESuperTypes().add(this.getLinkThroughputSpecification());
		staticLinkLatencySpecificationEClass.getESuperTypes().add(this.getLinkLatencySpecification());
		staticLinkThroughputSpecificationEClass.getESuperTypes().add(this.getLinkThroughputSpecification());

		// Initialize classes and features; add operations and parameters
		initEClass(linkAllocationRepositoryEClass, LinkAllocationRepository.class, "LinkAllocationRepository",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLinkAllocationRepository_LinkAllocations(), this.getLinkAllocation(), null, "LinkAllocations",
				null, 0, -1, LinkAllocationRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkAllocationEClass, LinkAllocation.class, "LinkAllocation", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLinkAllocation_LatencySpecification(), this.getLinkLatencySpecification(), null,
				"latencySpecification", null, 1, 1, LinkAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLinkAllocation_ThroughputSpecification(), this.getLinkThroughputSpecification(), null,
				"throughputSpecification", null, 1, 1, LinkAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLinkAllocation_BandwidthSpecification(), this.getBandwidthSpecification(), null,
				"bandwidthSpecification", null, 1, 1, LinkAllocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkLatencySpecificationEClass, LinkLatencySpecification.class, "LinkLatencySpecification",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bandwidthSpecificationEClass, BandwidthSpecification.class, "BandwidthSpecification", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBandwidthSpecification_Bandwidth(), theEcorePackage.getEDouble(), "Bandwidth", null, 0, 1,
				BandwidthSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBandwidthSpecification_HeterogeneityLinkTarget(), theEcorePackage.getEDouble(),
				"HeterogeneityLinkTarget", "0.0", 1, 1, BandwidthSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBandwidthSpecification_HeterogeneityNodeTarget(), theEcorePackage.getEDouble(),
				"HeterogeneityNodeTarget", null, 1, 1, BandwidthSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicLinkLatencySpecificationValueEClass, DynamicLinkLatencySpecificationValue.class,
				"DynamicLinkLatencySpecificationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDynamicLinkLatencySpecificationValue_Latency(), theEcorePackage.getELong(), "Latency", null,
				1, 1, DynamicLinkLatencySpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicLinkLatencySpecificationValue_Probability(), theEcorePackage.getEDouble(),
				"Probability", null, 1, 1, DynamicLinkLatencySpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicLinkLatencySpecificationValue_Duration(), theEcorePackage.getELong(), "Duration", null,
				1, 1, DynamicLinkLatencySpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkThroughputSpecificationEClass, LinkThroughputSpecification.class, "LinkThroughputSpecification",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dynamicLinkThroughputSpecificationValueEClass, DynamicLinkThroughputSpecificationValue.class,
				"DynamicLinkThroughputSpecificationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDynamicLinkThroughputSpecificationValue_Throughput(), theEcorePackage.getELong(),
				"Throughput", null, 1, 1, DynamicLinkThroughputSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicLinkThroughputSpecificationValue_Probability(), theEcorePackage.getEDouble(),
				"Probability", null, 1, 1, DynamicLinkThroughputSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicLinkThroughputSpecificationValue_Duration(), theEcorePackage.getELong(), "Duration",
				null, 1, 1, DynamicLinkThroughputSpecificationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicLinkLatencySpecificationEClass, DynamicLinkLatencySpecification.class,
				"DynamicLinkLatencySpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicLinkLatencySpecification_Values(), this.getDynamicLinkLatencySpecificationValue(),
				null, "Values", null, 0, -1, DynamicLinkLatencySpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicLinkThroughputSpecificationEClass, DynamicLinkThroughputSpecification.class,
				"DynamicLinkThroughputSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicLinkThroughputSpecification_Values(),
				this.getDynamicLinkThroughputSpecificationValue(), null, "Values", null, 1, -1,
				DynamicLinkThroughputSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(staticLinkLatencySpecificationEClass, StaticLinkLatencySpecification.class,
				"StaticLinkLatencySpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStaticLinkLatencySpecification_Latency(), theEcorePackage.getELong(), "Latency", null, 1, 1,
				StaticLinkLatencySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(staticLinkThroughputSpecificationEClass, StaticLinkThroughputSpecification.class,
				"StaticLinkThroughputSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStaticLinkThroughputSpecification_Throughput(), theEcorePackage.getELong(), "Throughput",
				null, 1, 1, StaticLinkThroughputSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //LinkallocationPackageImpl
