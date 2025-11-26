/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Link;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.LinkImpl#getAllocation <em>Allocation</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LinkImpl extends EntityImpl implements Link {
	/**
	 * The cached value of the '{@link #getAllocation() <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocation()
	 * @generated
	 * @ordered
	 */
	protected LinkAllocation allocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation getAllocation() {
		if (allocation != null && allocation.eIsProxy()) {
			InternalEObject oldAllocation = (InternalEObject) allocation;
			allocation = (LinkAllocation) eResolveProxy(oldAllocation);
			if (allocation != oldAllocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, P2pnetworkPackage.LINK__ALLOCATION,
							oldAllocation, allocation));
			}
		}
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkAllocation basicGetAllocation() {
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAllocation(LinkAllocation newAllocation) {
		LinkAllocation oldAllocation = allocation;
		allocation = newAllocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.LINK__ALLOCATION, oldAllocation,
					allocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case P2pnetworkPackage.LINK__ALLOCATION:
			if (resolve)
				return getAllocation();
			return basicGetAllocation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case P2pnetworkPackage.LINK__ALLOCATION:
			setAllocation((LinkAllocation) newValue);
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
		case P2pnetworkPackage.LINK__ALLOCATION:
			setAllocation((LinkAllocation) null);
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
		case P2pnetworkPackage.LINK__ALLOCATION:
			return allocation != null;
		}
		return super.eIsSet(featureID);
	}

} //LinkImpl
