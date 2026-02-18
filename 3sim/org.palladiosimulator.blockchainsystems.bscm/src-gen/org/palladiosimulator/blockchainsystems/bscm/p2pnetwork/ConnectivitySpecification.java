/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connectivity Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getInBoundLinkAllocationSpecification <em>In Bound Link Allocation Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getOutBoundLinkAllocationSpecification <em>Out Bound Link Allocation Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getNumberOfInbound <em>Number Of Inbound</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getNumberOfOutBound <em>Number Of Out Bound</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectivitySpecification()
 * @model
 * @generated
 */
public interface ConnectivitySpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>In Bound Link Allocation Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Bound Link Allocation Specification</em>' reference.
	 * @see #setInBoundLinkAllocationSpecification(LinkAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectivitySpecification_InBoundLinkAllocationSpecification()
	 * @model required="true"
	 * @generated
	 */
	LinkAllocation getInBoundLinkAllocationSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getInBoundLinkAllocationSpecification <em>In Bound Link Allocation Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Bound Link Allocation Specification</em>' reference.
	 * @see #getInBoundLinkAllocationSpecification()
	 * @generated
	 */
	void setInBoundLinkAllocationSpecification(LinkAllocation value);

	/**
	 * Returns the value of the '<em><b>Out Bound Link Allocation Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Bound Link Allocation Specification</em>' reference.
	 * @see #setOutBoundLinkAllocationSpecification(LinkAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectivitySpecification_OutBoundLinkAllocationSpecification()
	 * @model required="true"
	 * @generated
	 */
	LinkAllocation getOutBoundLinkAllocationSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getOutBoundLinkAllocationSpecification <em>Out Bound Link Allocation Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out Bound Link Allocation Specification</em>' reference.
	 * @see #getOutBoundLinkAllocationSpecification()
	 * @generated
	 */
	void setOutBoundLinkAllocationSpecification(LinkAllocation value);

	/**
	 * Returns the value of the '<em><b>Number Of Inbound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Inbound</em>' attribute.
	 * @see #setNumberOfInbound(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectivitySpecification_NumberOfInbound()
	 * @model required="true"
	 * @generated
	 */
	int getNumberOfInbound();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getNumberOfInbound <em>Number Of Inbound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Inbound</em>' attribute.
	 * @see #getNumberOfInbound()
	 * @generated
	 */
	void setNumberOfInbound(int value);

	/**
	 * Returns the value of the '<em><b>Number Of Out Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Out Bound</em>' attribute.
	 * @see #setNumberOfOutBound(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getConnectivitySpecification_NumberOfOutBound()
	 * @model required="true"
	 * @generated
	 */
	int getNumberOfOutBound();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification#getNumberOfOutBound <em>Number Of Out Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Out Bound</em>' attribute.
	 * @see #getNumberOfOutBound()
	 * @generated
	 */
	void setNumberOfOutBound(int value);

} // ConnectivitySpecification
