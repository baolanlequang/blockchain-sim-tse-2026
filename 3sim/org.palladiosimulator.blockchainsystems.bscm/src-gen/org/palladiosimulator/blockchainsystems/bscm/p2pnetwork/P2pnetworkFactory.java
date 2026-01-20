/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage
 * @generated
 */
public interface P2pnetworkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	P2pnetworkFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2pnetworkFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>P2P Network</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>P2P Network</em>'.
	 * @generated
	 */
	P2PNetwork createP2PNetwork();

	/**
	 * Returns a new object of class '<em>Explicit Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Explicit Network Topology</em>'.
	 * @generated
	 */
	ExplicitNetworkTopology createExplicitNetworkTopology();

	/**
	 * Returns a new object of class '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node</em>'.
	 * @generated
	 */
	Node createNode();

	/**
	 * Returns a new object of class '<em>Connected Subgraphs Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connected Subgraphs Network Topology</em>'.
	 * @generated
	 */
	ConnectedSubgraphsNetworkTopology createConnectedSubgraphsNetworkTopology();

	/**
	 * Returns a new object of class '<em>Subgraph Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subgraph Specification</em>'.
	 * @generated
	 */
	SubgraphSpecification createSubgraphSpecification();

	/**
	 * Returns a new object of class '<em>Connectivity Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connectivity Specification</em>'.
	 * @generated
	 */
	ConnectivitySpecification createConnectivitySpecification();

	/**
	 * Returns a new object of class '<em>Subgraph Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subgraph Link</em>'.
	 * @generated
	 */
	SubgraphLink createSubgraphLink();

	/**
	 * Returns a new object of class '<em>Subgraph Node Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subgraph Node Template</em>'.
	 * @generated
	 */
	SubgraphNodeTemplate createSubgraphNodeTemplate();

	/**
	 * Returns a new object of class '<em>Bidirectional Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bidirectional Link</em>'.
	 * @generated
	 */
	BidirectionalLink createBidirectionalLink();

	/**
	 * Returns a new object of class '<em>Unidirectional Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unidirectional Link</em>'.
	 * @generated
	 */
	UnidirectionalLink createUnidirectionalLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	P2pnetworkPackage getP2pnetworkPackage();

} //P2pnetworkFactory
