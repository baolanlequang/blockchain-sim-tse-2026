/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Allocation Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationRepositoryImpl#getNodeAllocations <em>Node Allocations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeAllocationRepositoryImpl extends EntityImpl implements NodeAllocationRepository {
	/**
	 * The cached value of the '{@link #getNodeAllocations() <em>Node Allocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeAllocations()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeAllocation> nodeAllocations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeAllocationRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodeallocationPackage.Literals.NODE_ALLOCATION_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NodeAllocation> getNodeAllocations() {
		if (nodeAllocations == null) {
			nodeAllocations = new EObjectContainmentEList<NodeAllocation>(NodeAllocation.class, this,
					NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS);
		}
		return nodeAllocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS:
			return ((InternalEList<?>) getNodeAllocations()).basicRemove(otherEnd, msgs);
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
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS:
			return getNodeAllocations();
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
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS:
			getNodeAllocations().clear();
			getNodeAllocations().addAll((Collection<? extends NodeAllocation>) newValue);
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
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS:
			getNodeAllocations().clear();
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
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY__NODE_ALLOCATIONS:
			return nodeAllocations != null && !nodeAllocations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NodeAllocationRepositoryImpl
