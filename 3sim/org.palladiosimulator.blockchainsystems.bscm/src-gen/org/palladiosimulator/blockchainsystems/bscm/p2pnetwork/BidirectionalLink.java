/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bidirectional Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink#getConnectedNodes <em>Connected Nodes</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getBidirectionalLink()
 * @model
 * @generated
 */
public interface BidirectionalLink extends Link {
	/**
	 * Returns the value of the '<em><b>Connected Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connected Nodes</em>' reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getBidirectionalLink_ConnectedNodes()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<Node> getConnectedNodes();

} // BidirectionalLink
