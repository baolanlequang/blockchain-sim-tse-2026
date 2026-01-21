/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connectivity Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectivitySpecificationImpl#getInBoundLinkAllocationSpecification <em>In Bound Link Allocation Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectivitySpecificationImpl#getOutBoundLinkAllocationSpecification <em>Out Bound Link Allocation Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectivitySpecificationImpl#getNumberOfInbound <em>Number Of Inbound</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectivitySpecificationImpl#getNumberOfOutBound <em>Number Of Out Bound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectivitySpecificationImpl extends EntityImpl implements ConnectivitySpecification {
	/**
	 * The cached value of the '{@link #getInBoundLinkAllocationSpecification() <em>In Bound Link Allocation Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInBoundLinkAllocationSpecification()
	 * @generated
	 * @ordered
	 */
	protected LinkAllocation inBoundLinkAllocationSpecification;

	/**
	 * The cached value of the '{@link #getOutBoundLinkAllocationSpecification() <em>Out Bound Link Allocation Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutBoundLinkAllocationSpecification()
	 * @generated
	 * @ordered
	 */
	protected LinkAllocation outBoundLinkAllocationSpecification;

	/**
	 * The default value of the '{@link #getNumberOfInbound() <em>Number Of Inbound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfInbound()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_INBOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfInbound() <em>Number Of Inbound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfInbound()
	 * @generated
	 * @ordered
	 */
	protected int numberOfInbound = NUMBER_OF_INBOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfOutBound() <em>Number Of Out Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfOutBound()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_OUT_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfOutBound() <em>Number Of Out Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfOutBound()
	 * @generated
	 * @ordered
	 */
	protected int numberOfOutBound = NUMBER_OF_OUT_BOUND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectivitySpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.CONNECTIVITY_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation getInBoundLinkAllocationSpecification() {
		if (inBoundLinkAllocationSpecification != null && inBoundLinkAllocationSpecification.eIsProxy()) {
			InternalEObject oldInBoundLinkAllocationSpecification = (InternalEObject) inBoundLinkAllocationSpecification;
			inBoundLinkAllocationSpecification = (LinkAllocation) eResolveProxy(oldInBoundLinkAllocationSpecification);
			if (inBoundLinkAllocationSpecification != oldInBoundLinkAllocationSpecification) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION,
							oldInBoundLinkAllocationSpecification, inBoundLinkAllocationSpecification));
			}
		}
		return inBoundLinkAllocationSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkAllocation basicGetInBoundLinkAllocationSpecification() {
		return inBoundLinkAllocationSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInBoundLinkAllocationSpecification(LinkAllocation newInBoundLinkAllocationSpecification) {
		LinkAllocation oldInBoundLinkAllocationSpecification = inBoundLinkAllocationSpecification;
		inBoundLinkAllocationSpecification = newInBoundLinkAllocationSpecification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION,
					oldInBoundLinkAllocationSpecification, inBoundLinkAllocationSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation getOutBoundLinkAllocationSpecification() {
		if (outBoundLinkAllocationSpecification != null && outBoundLinkAllocationSpecification.eIsProxy()) {
			InternalEObject oldOutBoundLinkAllocationSpecification = (InternalEObject) outBoundLinkAllocationSpecification;
			outBoundLinkAllocationSpecification = (LinkAllocation) eResolveProxy(
					oldOutBoundLinkAllocationSpecification);
			if (outBoundLinkAllocationSpecification != oldOutBoundLinkAllocationSpecification) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION,
							oldOutBoundLinkAllocationSpecification, outBoundLinkAllocationSpecification));
			}
		}
		return outBoundLinkAllocationSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkAllocation basicGetOutBoundLinkAllocationSpecification() {
		return outBoundLinkAllocationSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutBoundLinkAllocationSpecification(LinkAllocation newOutBoundLinkAllocationSpecification) {
		LinkAllocation oldOutBoundLinkAllocationSpecification = outBoundLinkAllocationSpecification;
		outBoundLinkAllocationSpecification = newOutBoundLinkAllocationSpecification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION,
					oldOutBoundLinkAllocationSpecification, outBoundLinkAllocationSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumberOfInbound() {
		return numberOfInbound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfInbound(int newNumberOfInbound) {
		int oldNumberOfInbound = numberOfInbound;
		numberOfInbound = newNumberOfInbound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND, oldNumberOfInbound,
					numberOfInbound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumberOfOutBound() {
		return numberOfOutBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfOutBound(int newNumberOfOutBound) {
		int oldNumberOfOutBound = numberOfOutBound;
		numberOfOutBound = newNumberOfOutBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND, oldNumberOfOutBound,
					numberOfOutBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION:
			if (resolve)
				return getInBoundLinkAllocationSpecification();
			return basicGetInBoundLinkAllocationSpecification();
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION:
			if (resolve)
				return getOutBoundLinkAllocationSpecification();
			return basicGetOutBoundLinkAllocationSpecification();
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND:
			return getNumberOfInbound();
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND:
			return getNumberOfOutBound();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION:
			setInBoundLinkAllocationSpecification((LinkAllocation) newValue);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION:
			setOutBoundLinkAllocationSpecification((LinkAllocation) newValue);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND:
			setNumberOfInbound((Integer) newValue);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND:
			setNumberOfOutBound((Integer) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION:
			setInBoundLinkAllocationSpecification((LinkAllocation) null);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION:
			setOutBoundLinkAllocationSpecification((LinkAllocation) null);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND:
			setNumberOfInbound(NUMBER_OF_INBOUND_EDEFAULT);
			return;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND:
			setNumberOfOutBound(NUMBER_OF_OUT_BOUND_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION:
			return inBoundLinkAllocationSpecification != null;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION:
			return outBoundLinkAllocationSpecification != null;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND:
			return numberOfInbound != NUMBER_OF_INBOUND_EDEFAULT;
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND:
			return numberOfOutBound != NUMBER_OF_OUT_BOUND_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (NumberOfInbound: ");
		result.append(numberOfInbound);
		result.append(", NumberOfOutBound: ");
		result.append(numberOfOutBound);
		result.append(')');
		return result.toString();
	}

} //ConnectivitySpecificationImpl
