/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link#getAllocation <em>Allocation</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getLink()
 * @model abstract="true"
 * @generated
 */
public interface Link extends Entity {
	/**
	 * Returns the value of the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation</em>' reference.
	 * @see #setAllocation(LinkAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getLink_Allocation()
	 * @model required="true"
	 * @generated
	 */
	LinkAllocation getAllocation();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link#getAllocation <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation</em>' reference.
	 * @see #getAllocation()
	 * @generated
	 */
	void setAllocation(LinkAllocation value);

} // Link
