/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bandwidth Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.BandwidthSpecificationImpl#getBandwidth <em>Bandwidth</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BandwidthSpecificationImpl extends EntityImpl implements BandwidthSpecification {
	/**
	 * The default value of the '{@link #getBandwidth() <em>Bandwidth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidth()
	 * @generated
	 * @ordered
	 */
	protected static final double BANDWIDTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBandwidth() <em>Bandwidth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidth()
	 * @generated
	 * @ordered
	 */
	protected double bandwidth = BANDWIDTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BandwidthSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkallocationPackage.Literals.BANDWIDTH_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getBandwidth() {
		return bandwidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBandwidth(double newBandwidth) {
		double oldBandwidth = bandwidth;
		bandwidth = newBandwidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.BANDWIDTH_SPECIFICATION__BANDWIDTH, oldBandwidth, bandwidth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkallocationPackage.BANDWIDTH_SPECIFICATION__BANDWIDTH:
			return getBandwidth();
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
		case LinkallocationPackage.BANDWIDTH_SPECIFICATION__BANDWIDTH:
			setBandwidth((Double) newValue);
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
		case LinkallocationPackage.BANDWIDTH_SPECIFICATION__BANDWIDTH:
			setBandwidth(BANDWIDTH_EDEFAULT);
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
		case LinkallocationPackage.BANDWIDTH_SPECIFICATION__BANDWIDTH:
			return bandwidth != BANDWIDTH_EDEFAULT;
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
		result.append(" (Bandwidth: ");
		result.append(bandwidth);
		result.append(')');
		return result.toString();
	}

} //BandwidthSpecificationImpl
