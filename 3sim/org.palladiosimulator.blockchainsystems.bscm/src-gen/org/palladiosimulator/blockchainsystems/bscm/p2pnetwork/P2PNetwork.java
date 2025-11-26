/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>P2P Network</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork#getTopology <em>Topology</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getP2PNetwork()
 * @model
 * @generated
 */
public interface P2PNetwork extends Entity {
	/**
	 * Returns the value of the '<em><b>Topology</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topology</em>' containment reference.
	 * @see #setTopology(NetworkTopology)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getP2PNetwork_Topology()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NetworkTopology getTopology();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork#getTopology <em>Topology</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Topology</em>' containment reference.
	 * @see #getTopology()
	 * @generated
	 */
	void setTopology(NetworkTopology value);

} // P2PNetwork
