/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer;
import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Resource Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeResourceContainerImpl#getResourcePower <em>Resource Power</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeResourceContainerImpl extends EntityImpl implements NodeResourceContainer {
	/**
	 * The default value of the '{@link #getResourcePower() <em>Resource Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcePower()
	 * @generated
	 * @ordered
	 */
	protected static final double RESOURCE_POWER_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getResourcePower() <em>Resource Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcePower()
	 * @generated
	 * @ordered
	 */
	protected double resourcePower = RESOURCE_POWER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeResourceContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodeenvironmentPackage.Literals.NODE_RESOURCE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getResourcePower() {
		return resourcePower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResourcePower(double newResourcePower) {
		double oldResourcePower = resourcePower;
		resourcePower = newResourcePower;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodeenvironmentPackage.NODE_RESOURCE_CONTAINER__RESOURCE_POWER, oldResourcePower, resourcePower));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case NodeenvironmentPackage.NODE_RESOURCE_CONTAINER__RESOURCE_POWER:
			return getResourcePower();
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
		case NodeenvironmentPackage.NODE_RESOURCE_CONTAINER__RESOURCE_POWER:
			setResourcePower((Double) newValue);
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
		case NodeenvironmentPackage.NODE_RESOURCE_CONTAINER__RESOURCE_POWER:
			setResourcePower(RESOURCE_POWER_EDEFAULT);
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
		case NodeenvironmentPackage.NODE_RESOURCE_CONTAINER__RESOURCE_POWER:
			return resourcePower != RESOURCE_POWER_EDEFAULT;
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
		result.append(" (ResourcePower: ");
		result.append(resourcePower);
		result.append(')');
		return result.toString();
	}

} //NodeResourceContainerImpl
