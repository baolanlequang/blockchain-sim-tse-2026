/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subgraph Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getConnectedSubgraphs <em>Connected Subgraphs</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphLink()
 * @model
 * @generated
 */
public interface SubgraphLink extends Entity {
	/**
	 * Returns the value of the '<em><b>Allocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation</em>' containment reference.
	 * @see #setAllocation(LinkAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphLink_Allocation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LinkAllocation getAllocation();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink#getAllocation <em>Allocation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation</em>' containment reference.
	 * @see #getAllocation()
	 * @generated
	 */
	void setAllocation(LinkAllocation value);

	/**
	 * Returns the value of the '<em><b>Connected Subgraphs</b></em>' reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connected Subgraphs</em>' reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphLink_ConnectedSubgraphs()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<SubgraphSpecification> getConnectedSubgraphs();

} // SubgraphLink
