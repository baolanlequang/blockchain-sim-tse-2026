/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

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

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConstraintNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkFactory;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink;

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
public class P2pnetworkPackageImpl extends EPackageImpl implements P2pnetworkPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass p2PNetworkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass networkTopologyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass explicitNetworkTopologyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintNetworkTopologyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectedSubgraphsNetworkTopologyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subgraphSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectivitySpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subgraphLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subgraphNodeTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bidirectionalLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unidirectionalLinkEClass = null;

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
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private P2pnetworkPackageImpl() {
		super(eNS_URI, P2pnetworkFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link P2pnetworkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static P2pnetworkPackage init() {
		if (isInited)
			return (P2pnetworkPackage) EPackage.Registry.INSTANCE.getEPackage(P2pnetworkPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredP2pnetworkPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		P2pnetworkPackageImpl theP2pnetworkPackage = registeredP2pnetworkPackage instanceof P2pnetworkPackageImpl
				? (P2pnetworkPackageImpl) registeredP2pnetworkPackage
				: new P2pnetworkPackageImpl();

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
		theP2pnetworkPackage.createPackageContents();
		theNodeallocationPackage.createPackageContents();
		theNodesystemPackage.createPackageContents();
		theBlockchainsystemComponentRepositoryPackage.createPackageContents();
		theBlockchainsystemPackage.createPackageContents();
		theNodeenvironmentPackage.createPackageContents();
		theGeographicalregionsPackage.createPackageContents();
		theLinkallocationPackage.createPackageContents();
		theTransactionsPackage.createPackageContents();

		// Initialize created meta-data
		theP2pnetworkPackage.initializePackageContents();
		theNodeallocationPackage.initializePackageContents();
		theNodesystemPackage.initializePackageContents();
		theBlockchainsystemComponentRepositoryPackage.initializePackageContents();
		theBlockchainsystemPackage.initializePackageContents();
		theNodeenvironmentPackage.initializePackageContents();
		theGeographicalregionsPackage.initializePackageContents();
		theLinkallocationPackage.initializePackageContents();
		theTransactionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theP2pnetworkPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(P2pnetworkPackage.eNS_URI, theP2pnetworkPackage);
		return theP2pnetworkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getP2PNetwork() {
		return p2PNetworkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getP2PNetwork_Topology() {
		return (EReference) p2PNetworkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNetworkTopology() {
		return networkTopologyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExplicitNetworkTopology() {
		return explicitNetworkTopologyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExplicitNetworkTopology_Nodes() {
		return (EReference) explicitNetworkTopologyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExplicitNetworkTopology_Links() {
		return (EReference) explicitNetworkTopologyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConstraintNetworkTopology() {
		return constraintNetworkTopologyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNode_Allocation() {
		return (EReference) nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Allocation() {
		return (EReference) linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConnectedSubgraphsNetworkTopology() {
		return connectedSubgraphsNetworkTopologyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnectedSubgraphsNetworkTopology_Subgraphs() {
		return (EReference) connectedSubgraphsNetworkTopologyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnectedSubgraphsNetworkTopology_SubgraphLinks() {
		return (EReference) connectedSubgraphsNetworkTopologyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubgraphSpecification() {
		return subgraphSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphSpecification_NodeTemplates() {
		return (EReference) subgraphSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubgraphSpecification_Connectivity() {
		return (EAttribute) subgraphSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphSpecification_LinkAllocation() {
		return (EReference) subgraphSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphSpecification_ConnectivitySpecification() {
		return (EReference) subgraphSpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConnectivitySpecification() {
		return connectivitySpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnectivitySpecification_InBoundLinkAllocationSpecification() {
		return (EReference) connectivitySpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnectivitySpecification_OutBoundLinkAllocationSpecification() {
		return (EReference) connectivitySpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectivitySpecification_NumberOfInbound() {
		return (EAttribute) connectivitySpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConnectivitySpecification_NumberOfOutBound() {
		return (EAttribute) connectivitySpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubgraphLink() {
		return subgraphLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphLink_Allocation() {
		return (EReference) subgraphLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphLink_ConnectedSubgraphs() {
		return (EReference) subgraphLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubgraphNodeTemplate() {
		return subgraphNodeTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubgraphNodeTemplate_NumberOfNodeOccurences() {
		return (EAttribute) subgraphNodeTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubgraphNodeTemplate_Allocation() {
		return (EReference) subgraphNodeTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubgraphNodeTemplate_IsSubgraphProxy() {
		return (EAttribute) subgraphNodeTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBidirectionalLink() {
		return bidirectionalLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBidirectionalLink_ConnectedNodes() {
		return (EReference) bidirectionalLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUnidirectionalLink() {
		return unidirectionalLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUnidirectionalLink_FromNode() {
		return (EReference) unidirectionalLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUnidirectionalLink_ToNode() {
		return (EReference) unidirectionalLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public P2pnetworkFactory getP2pnetworkFactory() {
		return (P2pnetworkFactory) getEFactoryInstance();
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
		p2PNetworkEClass = createEClass(P2P_NETWORK);
		createEReference(p2PNetworkEClass, P2P_NETWORK__TOPOLOGY);

		networkTopologyEClass = createEClass(NETWORK_TOPOLOGY);

		explicitNetworkTopologyEClass = createEClass(EXPLICIT_NETWORK_TOPOLOGY);
		createEReference(explicitNetworkTopologyEClass, EXPLICIT_NETWORK_TOPOLOGY__NODES);
		createEReference(explicitNetworkTopologyEClass, EXPLICIT_NETWORK_TOPOLOGY__LINKS);

		constraintNetworkTopologyEClass = createEClass(CONSTRAINT_NETWORK_TOPOLOGY);

		nodeEClass = createEClass(NODE);
		createEReference(nodeEClass, NODE__ALLOCATION);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__ALLOCATION);

		connectedSubgraphsNetworkTopologyEClass = createEClass(CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY);
		createEReference(connectedSubgraphsNetworkTopologyEClass, CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS);
		createEReference(connectedSubgraphsNetworkTopologyEClass, CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS);

		subgraphSpecificationEClass = createEClass(SUBGRAPH_SPECIFICATION);
		createEReference(subgraphSpecificationEClass, SUBGRAPH_SPECIFICATION__NODE_TEMPLATES);
		createEAttribute(subgraphSpecificationEClass, SUBGRAPH_SPECIFICATION__CONNECTIVITY);
		createEReference(subgraphSpecificationEClass, SUBGRAPH_SPECIFICATION__LINK_ALLOCATION);
		createEReference(subgraphSpecificationEClass, SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION);

		connectivitySpecificationEClass = createEClass(CONNECTIVITY_SPECIFICATION);
		createEReference(connectivitySpecificationEClass,
				CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION);
		createEReference(connectivitySpecificationEClass,
				CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION);
		createEAttribute(connectivitySpecificationEClass, CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND);
		createEAttribute(connectivitySpecificationEClass, CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND);

		subgraphLinkEClass = createEClass(SUBGRAPH_LINK);
		createEReference(subgraphLinkEClass, SUBGRAPH_LINK__ALLOCATION);
		createEReference(subgraphLinkEClass, SUBGRAPH_LINK__CONNECTED_SUBGRAPHS);

		subgraphNodeTemplateEClass = createEClass(SUBGRAPH_NODE_TEMPLATE);
		createEAttribute(subgraphNodeTemplateEClass, SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES);
		createEReference(subgraphNodeTemplateEClass, SUBGRAPH_NODE_TEMPLATE__ALLOCATION);
		createEAttribute(subgraphNodeTemplateEClass, SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY);

		bidirectionalLinkEClass = createEClass(BIDIRECTIONAL_LINK);
		createEReference(bidirectionalLinkEClass, BIDIRECTIONAL_LINK__CONNECTED_NODES);

		unidirectionalLinkEClass = createEClass(UNIDIRECTIONAL_LINK);
		createEReference(unidirectionalLinkEClass, UNIDIRECTIONAL_LINK__FROM_NODE);
		createEReference(unidirectionalLinkEClass, UNIDIRECTIONAL_LINK__TO_NODE);
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
		NodeallocationPackage theNodeallocationPackage = (NodeallocationPackage) EPackage.Registry.INSTANCE
				.getEPackage(NodeallocationPackage.eNS_URI);
		LinkallocationPackage theLinkallocationPackage = (LinkallocationPackage) EPackage.Registry.INSTANCE
				.getEPackage(LinkallocationPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		p2PNetworkEClass.getESuperTypes().add(theEntityPackage.getEntity());
		networkTopologyEClass.getESuperTypes().add(theEntityPackage.getEntity());
		explicitNetworkTopologyEClass.getESuperTypes().add(this.getNetworkTopology());
		constraintNetworkTopologyEClass.getESuperTypes().add(this.getNetworkTopology());
		nodeEClass.getESuperTypes().add(theEntityPackage.getEntity());
		linkEClass.getESuperTypes().add(theEntityPackage.getEntity());
		connectedSubgraphsNetworkTopologyEClass.getESuperTypes().add(this.getConstraintNetworkTopology());
		subgraphSpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		connectivitySpecificationEClass.getESuperTypes().add(theEntityPackage.getEntity());
		subgraphLinkEClass.getESuperTypes().add(theEntityPackage.getEntity());
		subgraphNodeTemplateEClass.getESuperTypes().add(theEntityPackage.getEntity());
		bidirectionalLinkEClass.getESuperTypes().add(this.getLink());
		unidirectionalLinkEClass.getESuperTypes().add(this.getLink());

		// Initialize classes and features; add operations and parameters
		initEClass(p2PNetworkEClass, P2PNetwork.class, "P2PNetwork", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getP2PNetwork_Topology(), this.getNetworkTopology(), null, "Topology", null, 1, 1,
				P2PNetwork.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(networkTopologyEClass, NetworkTopology.class, "NetworkTopology", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(explicitNetworkTopologyEClass, ExplicitNetworkTopology.class, "ExplicitNetworkTopology",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExplicitNetworkTopology_Nodes(), this.getNode(), null, "Nodes", null, 0, -1,
				ExplicitNetworkTopology.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExplicitNetworkTopology_Links(), this.getLink(), null, "Links", null, 0, -1,
				ExplicitNetworkTopology.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constraintNetworkTopologyEClass, ConstraintNetworkTopology.class, "ConstraintNetworkTopology",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNode_Allocation(), theNodeallocationPackage.getNodeAllocation(), null, "Allocation", null, 1,
				1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkEClass, Link.class, "Link", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Allocation(), theLinkallocationPackage.getLinkAllocation(), null, "Allocation", null, 1,
				1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectedSubgraphsNetworkTopologyEClass, ConnectedSubgraphsNetworkTopology.class,
				"ConnectedSubgraphsNetworkTopology", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectedSubgraphsNetworkTopology_Subgraphs(), this.getSubgraphSpecification(), null,
				"Subgraphs", null, 1, -1, ConnectedSubgraphsNetworkTopology.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectedSubgraphsNetworkTopology_SubgraphLinks(), this.getSubgraphLink(), null,
				"SubgraphLinks", null, 0, -1, ConnectedSubgraphsNetworkTopology.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subgraphSpecificationEClass, SubgraphSpecification.class, "SubgraphSpecification", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubgraphSpecification_NodeTemplates(), this.getSubgraphNodeTemplate(), null, "NodeTemplates",
				null, 1, -1, SubgraphSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubgraphSpecification_Connectivity(), theEcorePackage.getEInt(), "Connectivity", null, 1, 1,
				SubgraphSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubgraphSpecification_LinkAllocation(), theLinkallocationPackage.getLinkAllocation(), null,
				"LinkAllocation", null, 1, 1, SubgraphSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubgraphSpecification_ConnectivitySpecification(), this.getConnectivitySpecification(), null,
				"ConnectivitySpecification", null, 1, 1, SubgraphSpecification.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectivitySpecificationEClass, ConnectivitySpecification.class, "ConnectivitySpecification",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectivitySpecification_InBoundLinkAllocationSpecification(),
				theLinkallocationPackage.getLinkAllocation(), null, "InBoundLinkAllocationSpecification", null, 1, 1,
				ConnectivitySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectivitySpecification_OutBoundLinkAllocationSpecification(),
				theLinkallocationPackage.getLinkAllocation(), null, "OutBoundLinkAllocationSpecification", null, 1, 1,
				ConnectivitySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectivitySpecification_NumberOfInbound(), theEcorePackage.getEInt(), "NumberOfInbound",
				null, 1, 1, ConnectivitySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectivitySpecification_NumberOfOutBound(), theEcorePackage.getEInt(), "NumberOfOutBound",
				null, 1, 1, ConnectivitySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subgraphLinkEClass, SubgraphLink.class, "SubgraphLink", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubgraphLink_Allocation(), theLinkallocationPackage.getLinkAllocation(), null, "Allocation",
				null, 1, 1, SubgraphLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubgraphLink_ConnectedSubgraphs(), this.getSubgraphSpecification(), null,
				"ConnectedSubgraphs", null, 2, 2, SubgraphLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subgraphNodeTemplateEClass, SubgraphNodeTemplate.class, "SubgraphNodeTemplate", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubgraphNodeTemplate_NumberOfNodeOccurences(), theEcorePackage.getEInt(),
				"NumberOfNodeOccurences", null, 1, 1, SubgraphNodeTemplate.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubgraphNodeTemplate_Allocation(), theNodeallocationPackage.getNodeAllocation(), null,
				"Allocation", null, 1, 1, SubgraphNodeTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubgraphNodeTemplate_IsSubgraphProxy(), theEcorePackage.getEBoolean(), "IsSubgraphProxy",
				null, 1, 1, SubgraphNodeTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bidirectionalLinkEClass, BidirectionalLink.class, "BidirectionalLink", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBidirectionalLink_ConnectedNodes(), this.getNode(), null, "ConnectedNodes", null, 2, 2,
				BidirectionalLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unidirectionalLinkEClass, UnidirectionalLink.class, "UnidirectionalLink", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnidirectionalLink_FromNode(), this.getNode(), null, "FromNode", null, 1, 1,
				UnidirectionalLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnidirectionalLink_ToNode(), this.getNode(), null, "ToNode", null, 1, 1,
				UnidirectionalLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //P2pnetworkPackageImpl
