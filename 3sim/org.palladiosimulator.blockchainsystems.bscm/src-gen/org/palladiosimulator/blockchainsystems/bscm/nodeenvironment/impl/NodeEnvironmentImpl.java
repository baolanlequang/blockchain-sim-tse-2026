/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment;
import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer;
import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Environment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeEnvironmentImpl#getResourceContainers <em>Resource Containers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeEnvironmentImpl extends EntityImpl implements NodeEnvironment {
	/**
	 * The cached value of the '{@link #getResourceContainers() <em>Resource Containers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceContainers()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeResourceContainer> resourceContainers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeEnvironmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodeenvironmentPackage.Literals.NODE_ENVIRONMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NodeResourceContainer> getResourceContainers() {
		if (resourceContainers == null) {
			resourceContainers = new EObjectContainmentEList<NodeResourceContainer>(NodeResourceContainer.class, this,
					NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS);
		}
		return resourceContainers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS:
			return ((InternalEList<?>) getResourceContainers()).basicRemove(otherEnd, msgs);
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
		case NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS:
			return getResourceContainers();
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
		case NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS:
			getResourceContainers().clear();
			getResourceContainers().addAll((Collection<? extends NodeResourceContainer>) newValue);
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
		case NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS:
			getResourceContainers().clear();
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
		case NodeenvironmentPackage.NODE_ENVIRONMENT__RESOURCE_CONTAINERS:
			return resourceContainers != null && !resourceContainers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NodeEnvironmentImpl
