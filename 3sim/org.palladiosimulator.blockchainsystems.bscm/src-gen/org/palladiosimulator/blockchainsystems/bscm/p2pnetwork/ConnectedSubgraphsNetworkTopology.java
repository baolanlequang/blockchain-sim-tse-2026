/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connected Subgraphs Network Topology</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphs <em>Subgraphs</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology#getSubgraphLinks <em>Subgraph Links</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectedSubgraphsNetworkTopology()
 * @model
 * @generated
 */
public interface ConnectedSubgraphsNetworkTopology extends ConstraintNetworkTopology {
	/**
	 * Returns the value of the '<em><b>Subgraphs</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subgraphs</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectedSubgraphsNetworkTopology_Subgraphs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<SubgraphSpecification> getSubgraphs();

	/**
	 * Returns the value of the '<em><b>Subgraph Links</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subgraph Links</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectedSubgraphsNetworkTopology_SubgraphLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<SubgraphLink> getSubgraphLinks();

} // ConnectedSubgraphsNetworkTopology
