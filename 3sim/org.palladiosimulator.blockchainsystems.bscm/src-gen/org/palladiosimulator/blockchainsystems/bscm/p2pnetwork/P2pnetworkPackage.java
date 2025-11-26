/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkFactory
 * @model kind="package"
 * @generated
 */
public interface P2pnetworkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "p2pnetwork";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/P2PNetwork/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "P2PNetwork";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	P2pnetworkPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2PNetworkImpl <em>P2P Network</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2PNetworkImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getP2PNetwork()
	 * @generated
	 */
	int P2P_NETWORK = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int P2P_NETWORK__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int P2P_NETWORK__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Topology</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int P2P_NETWORK__TOPOLOGY = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>P2P Network</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int P2P_NETWORK_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NetworkTopologyImpl <em>Network Topology</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NetworkTopologyImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getNetworkTopology()
	 * @generated
	 */
	int NETWORK_TOPOLOGY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_TOPOLOGY__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_TOPOLOGY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The number of structural features of the '<em>Network Topology</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_TOPOLOGY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ExplicitNetworkTopologyImpl <em>Explicit Network Topology</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ExplicitNetworkTopologyImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getExplicitNetworkTopology()
	 * @generated
	 */
	int EXPLICIT_NETWORK_TOPOLOGY = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_NETWORK_TOPOLOGY__ID = NETWORK_TOPOLOGY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_NETWORK_TOPOLOGY__ENTITY_NAME = NETWORK_TOPOLOGY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_NETWORK_TOPOLOGY__NODES = NETWORK_TOPOLOGY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_NETWORK_TOPOLOGY__LINKS = NETWORK_TOPOLOGY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Explicit Network Topology</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_NETWORK_TOPOLOGY_FEATURE_COUNT = NETWORK_TOPOLOGY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConstraintNetworkTopologyImpl <em>Constraint Network Topology</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConstraintNetworkTopologyImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getConstraintNetworkTopology()
	 * @generated
	 */
	int CONSTRAINT_NETWORK_TOPOLOGY = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_NETWORK_TOPOLOGY__ID = NETWORK_TOPOLOGY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_NETWORK_TOPOLOGY__ENTITY_NAME = NETWORK_TOPOLOGY__ENTITY_NAME;

	/**
	 * The number of structural features of the '<em>Constraint Network Topology</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_NETWORK_TOPOLOGY_FEATURE_COUNT = NETWORK_TOPOLOGY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NodeImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__ALLOCATION = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.LinkImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ALLOCATION = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl <em>Connected Subgraphs Network Topology</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getConnectedSubgraphsNetworkTopology()
	 * @generated
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__ID = CONSTRAINT_NETWORK_TOPOLOGY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__ENTITY_NAME = CONSTRAINT_NETWORK_TOPOLOGY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Subgraphs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS = CONSTRAINT_NETWORK_TOPOLOGY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subgraph Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS = CONSTRAINT_NETWORK_TOPOLOGY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Connected Subgraphs Network Topology</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY_FEATURE_COUNT = CONSTRAINT_NETWORK_TOPOLOGY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl <em>Subgraph Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphSpecification()
	 * @generated
	 */
	int SUBGRAPH_SPECIFICATION = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Node Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION__NODE_TEMPLATES = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Connectivity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION__CONNECTIVITY = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Link Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION__LINK_ALLOCATION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Subgraph Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl <em>Subgraph Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphLink()
	 * @generated
	 */
	int SUBGRAPH_LINK = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_LINK__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_LINK__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_LINK__ALLOCATION = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Connected Subgraphs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_LINK__CONNECTED_SUBGRAPHS = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Subgraph Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_LINK_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl <em>Subgraph Node Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphNodeTemplate()
	 * @generated
	 */
	int SUBGRAPH_NODE_TEMPLATE = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Number Of Node Occurences</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE__ALLOCATION = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Subgraph Proxy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Subgraph Node Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBGRAPH_NODE_TEMPLATE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.BidirectionalLinkImpl <em>Bidirectional Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.BidirectionalLinkImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getBidirectionalLink()
	 * @generated
	 */
	int BIDIRECTIONAL_LINK = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIDIRECTIONAL_LINK__ID = LINK__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIDIRECTIONAL_LINK__ENTITY_NAME = LINK__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIDIRECTIONAL_LINK__ALLOCATION = LINK__ALLOCATION;

	/**
	 * The feature id for the '<em><b>Connected Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIDIRECTIONAL_LINK__CONNECTED_NODES = LINK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bidirectional Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIDIRECTIONAL_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl <em>Unidirectional Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getUnidirectionalLink()
	 * @generated
	 */
	int UNIDIRECTIONAL_LINK = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK__ID = LINK__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK__ENTITY_NAME = LINK__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK__ALLOCATION = LINK__ALLOCATION;

	/**
	 * The feature id for the '<em><b>From Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK__FROM_NODE = LINK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK__TO_NODE = LINK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unidirectional Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIDIRECTIONAL_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork <em>P2P Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>P2P Network</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork
	 * @generated
	 */
	EClass getP2PNetwork();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork#getTopology <em>Topology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Topology</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork#getTopology()
	 * @see #getP2PNetwork()
	 * @generated
	 */
	EReference getP2PNetwork_Topology();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology <em>Network Topology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Network Topology</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology
	 * @generated
	 */
	EClass getNetworkTopology();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology <em>Explicit Network Topology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Explicit Network Topology</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
	 * @generated
	 */
	EClass getExplicitNetworkTopology();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology#getNodes()
	 * @see #getExplicitNetworkTopology()
	 * @generated
	 */
	EReference getExplicitNetworkTopology_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology#getLinks()
	 * @see #getExplicitNetworkTopology()
	 * @generated
	 */
	EReference getExplicitNetworkTopology_Links();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConstraintNetworkTopology <em>Constraint Network Topology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Network Topology</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConstraintNetworkTopology
	 * @generated
	 */
	EClass getConstraintNetworkTopology();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node#getAllocation <em>Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node#getAllocation()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_Allocation();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link#getAllocation <em>Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link#getAllocation()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Allocation();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology <em>Connected Subgraphs Network Topology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connected Subgraphs Network Topology</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
	 * @generated
	 */
	EClass getConnectedSubgraphsNetworkTopology();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphs <em>Subgraphs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subgraphs</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphs()
	 * @see #getConnectedSubgraphsNetworkTopology()
	 * @generated
	 */
	EReference getConnectedSubgraphsNetworkTopology_Subgraphs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphLinks <em>Subgraph Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subgraph Links</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphLinks()
	 * @see #getConnectedSubgraphsNetworkTopology()
	 * @generated
	 */
	EReference getConnectedSubgraphsNetworkTopology_SubgraphLinks();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification <em>Subgraph Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subgraph Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification
	 * @generated
	 */
	EClass getSubgraphSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getNodeTemplates <em>Node Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node Templates</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getNodeTemplates()
	 * @see #getSubgraphSpecification()
	 * @generated
	 */
	EReference getSubgraphSpecification_NodeTemplates();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivity <em>Connectivity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connectivity</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivity()
	 * @see #getSubgraphSpecification()
	 * @generated
	 */
	EAttribute getSubgraphSpecification_Connectivity();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getLinkAllocation <em>Link Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Link Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getLinkAllocation()
	 * @see #getSubgraphSpecification()
	 * @generated
	 */
	EReference getSubgraphSpecification_LinkAllocation();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink <em>Subgraph Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subgraph Link</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink
	 * @generated
	 */
	EClass getSubgraphLink();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getAllocation <em>Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getAllocation()
	 * @see #getSubgraphLink()
	 * @generated
	 */
	EReference getSubgraphLink_Allocation();

	/**
	 * Returns the meta object for the reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getConnectedSubgraphs <em>Connected Subgraphs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connected Subgraphs</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getConnectedSubgraphs()
	 * @see #getSubgraphLink()
	 * @generated
	 */
	EReference getSubgraphLink_ConnectedSubgraphs();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate <em>Subgraph Node Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subgraph Node Template</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate
	 * @generated
	 */
	EClass getSubgraphNodeTemplate();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getNumberOfNodeOccurences <em>Number Of Node Occurences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Node Occurences</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getNumberOfNodeOccurences()
	 * @see #getSubgraphNodeTemplate()
	 * @generated
	 */
	EAttribute getSubgraphNodeTemplate_NumberOfNodeOccurences();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getAllocation <em>Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getAllocation()
	 * @see #getSubgraphNodeTemplate()
	 * @generated
	 */
	EReference getSubgraphNodeTemplate_Allocation();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#isIsSubgraphProxy <em>Is Subgraph Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Subgraph Proxy</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#isIsSubgraphProxy()
	 * @see #getSubgraphNodeTemplate()
	 * @generated
	 */
	EAttribute getSubgraphNodeTemplate_IsSubgraphProxy();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink <em>Bidirectional Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bidirectional Link</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink
	 * @generated
	 */
	EClass getBidirectionalLink();

	/**
	 * Returns the meta object for the reference list '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink#getConnectedNodes <em>Connected Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connected Nodes</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink#getConnectedNodes()
	 * @see #getBidirectionalLink()
	 * @generated
	 */
	EReference getBidirectionalLink_ConnectedNodes();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink <em>Unidirectional Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unidirectional Link</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink
	 * @generated
	 */
	EClass getUnidirectionalLink();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getFromNode <em>From Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From Node</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getFromNode()
	 * @see #getUnidirectionalLink()
	 * @generated
	 */
	EReference getUnidirectionalLink_FromNode();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getToNode <em>To Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To Node</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getToNode()
	 * @see #getUnidirectionalLink()
	 * @generated
	 */
	EReference getUnidirectionalLink_ToNode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	P2pnetworkFactory getP2pnetworkFactory();

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
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2PNetworkImpl <em>P2P Network</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2PNetworkImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getP2PNetwork()
		 * @generated
		 */
		EClass P2P_NETWORK = eINSTANCE.getP2PNetwork();

		/**
		 * The meta object literal for the '<em><b>Topology</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference P2P_NETWORK__TOPOLOGY = eINSTANCE.getP2PNetwork_Topology();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NetworkTopologyImpl <em>Network Topology</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NetworkTopologyImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getNetworkTopology()
		 * @generated
		 */
		EClass NETWORK_TOPOLOGY = eINSTANCE.getNetworkTopology();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ExplicitNetworkTopologyImpl <em>Explicit Network Topology</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ExplicitNetworkTopologyImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getExplicitNetworkTopology()
		 * @generated
		 */
		EClass EXPLICIT_NETWORK_TOPOLOGY = eINSTANCE.getExplicitNetworkTopology();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPLICIT_NETWORK_TOPOLOGY__NODES = eINSTANCE.getExplicitNetworkTopology_Nodes();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPLICIT_NETWORK_TOPOLOGY__LINKS = eINSTANCE.getExplicitNetworkTopology_Links();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConstraintNetworkTopologyImpl <em>Constraint Network Topology</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConstraintNetworkTopologyImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getConstraintNetworkTopology()
		 * @generated
		 */
		EClass CONSTRAINT_NETWORK_TOPOLOGY = eINSTANCE.getConstraintNetworkTopology();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.NodeImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Allocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__ALLOCATION = eINSTANCE.getNode_Allocation();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.LinkImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Allocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__ALLOCATION = eINSTANCE.getLink_Allocation();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl <em>Connected Subgraphs Network Topology</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getConnectedSubgraphsNetworkTopology()
		 * @generated
		 */
		EClass CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY = eINSTANCE.getConnectedSubgraphsNetworkTopology();

		/**
		 * The meta object literal for the '<em><b>Subgraphs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS = eINSTANCE
				.getConnectedSubgraphsNetworkTopology_Subgraphs();

		/**
		 * The meta object literal for the '<em><b>Subgraph Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS = eINSTANCE
				.getConnectedSubgraphsNetworkTopology_SubgraphLinks();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl <em>Subgraph Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphSpecification()
		 * @generated
		 */
		EClass SUBGRAPH_SPECIFICATION = eINSTANCE.getSubgraphSpecification();

		/**
		 * The meta object literal for the '<em><b>Node Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBGRAPH_SPECIFICATION__NODE_TEMPLATES = eINSTANCE.getSubgraphSpecification_NodeTemplates();

		/**
		 * The meta object literal for the '<em><b>Connectivity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBGRAPH_SPECIFICATION__CONNECTIVITY = eINSTANCE.getSubgraphSpecification_Connectivity();

		/**
		 * The meta object literal for the '<em><b>Link Allocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBGRAPH_SPECIFICATION__LINK_ALLOCATION = eINSTANCE.getSubgraphSpecification_LinkAllocation();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl <em>Subgraph Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphLink()
		 * @generated
		 */
		EClass SUBGRAPH_LINK = eINSTANCE.getSubgraphLink();

		/**
		 * The meta object literal for the '<em><b>Allocation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBGRAPH_LINK__ALLOCATION = eINSTANCE.getSubgraphLink_Allocation();

		/**
		 * The meta object literal for the '<em><b>Connected Subgraphs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBGRAPH_LINK__CONNECTED_SUBGRAPHS = eINSTANCE.getSubgraphLink_ConnectedSubgraphs();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl <em>Subgraph Node Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getSubgraphNodeTemplate()
		 * @generated
		 */
		EClass SUBGRAPH_NODE_TEMPLATE = eINSTANCE.getSubgraphNodeTemplate();

		/**
		 * The meta object literal for the '<em><b>Number Of Node Occurences</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES = eINSTANCE
				.getSubgraphNodeTemplate_NumberOfNodeOccurences();

		/**
		 * The meta object literal for the '<em><b>Allocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBGRAPH_NODE_TEMPLATE__ALLOCATION = eINSTANCE.getSubgraphNodeTemplate_Allocation();

		/**
		 * The meta object literal for the '<em><b>Is Subgraph Proxy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY = eINSTANCE.getSubgraphNodeTemplate_IsSubgraphProxy();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.BidirectionalLinkImpl <em>Bidirectional Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.BidirectionalLinkImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getBidirectionalLink()
		 * @generated
		 */
		EClass BIDIRECTIONAL_LINK = eINSTANCE.getBidirectionalLink();

		/**
		 * The meta object literal for the '<em><b>Connected Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BIDIRECTIONAL_LINK__CONNECTED_NODES = eINSTANCE.getBidirectionalLink_ConnectedNodes();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl <em>Unidirectional Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkPackageImpl#getUnidirectionalLink()
		 * @generated
		 */
		EClass UNIDIRECTIONAL_LINK = eINSTANCE.getUnidirectionalLink();

		/**
		 * The meta object literal for the '<em><b>From Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNIDIRECTIONAL_LINK__FROM_NODE = eINSTANCE.getUnidirectionalLink_FromNode();

		/**
		 * The meta object literal for the '<em><b>To Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNIDIRECTIONAL_LINK__TO_NODE = eINSTANCE.getUnidirectionalLink_ToNode();

	}

} //P2pnetworkPackage
