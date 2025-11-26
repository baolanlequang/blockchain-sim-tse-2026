/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link Allocation Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationRepositoryImpl#getLinkAllocations <em>Link Allocations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkAllocationRepositoryImpl extends EntityImpl implements LinkAllocationRepository {
	/**
	 * The cached value of the '{@link #getLinkAllocations() <em>Link Allocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkAllocations()
	 * @generated
	 * @ordered
	 */
	protected EList<LinkAllocation> linkAllocations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkAllocationRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkallocationPackage.Literals.LINK_ALLOCATION_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LinkAllocation> getLinkAllocations() {
		if (linkAllocations == null) {
			linkAllocations = new EObjectContainmentEList<LinkAllocation>(LinkAllocation.class, this,
					LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS);
		}
		return linkAllocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS:
			return ((InternalEList<?>) getLinkAllocations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS:
			return getLinkAllocations();
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
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS:
			getLinkAllocations().clear();
			getLinkAllocations().addAll((Collection<? extends LinkAllocation>) newValue);
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
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS:
			getLinkAllocations().clear();
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
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS:
			return linkAllocations != null && !linkAllocations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LinkAllocationRepositoryImpl
