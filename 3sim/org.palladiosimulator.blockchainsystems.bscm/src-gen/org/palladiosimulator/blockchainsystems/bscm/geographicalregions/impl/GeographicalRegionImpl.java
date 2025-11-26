/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Geographical Region</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionImpl#getRegionName <em>Region Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GeographicalRegionImpl extends EntityImpl implements GeographicalRegion {
	/**
	 * The default value of the '{@link #getRegionName() <em>Region Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegionName()
	 * @generated
	 * @ordered
	 */
	protected static final String REGION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRegionName() <em>Region Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegionName()
	 * @generated
	 * @ordered
	 */
	protected String regionName = REGION_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GeographicalRegionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeographicalregionsPackage.Literals.GEOGRAPHICAL_REGION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRegionName() {
		return regionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRegionName(String newRegionName) {
		String oldRegionName = regionName;
		regionName = newRegionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GeographicalregionsPackage.GEOGRAPHICAL_REGION__REGION_NAME, oldRegionName, regionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GeographicalregionsPackage.GEOGRAPHICAL_REGION__REGION_NAME:
			return getRegionName();
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGION__REGION_NAME:
			setRegionName((String) newValue);
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGION__REGION_NAME:
			setRegionName(REGION_NAME_EDEFAULT);
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGION__REGION_NAME:
			return REGION_NAME_EDEFAULT == null ? regionName != null : !REGION_NAME_EDEFAULT.equals(regionName);
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
		result.append(" (RegionName: ");
		result.append(regionName);
		result.append(')');
		return result.toString();
	}

} //GeographicalRegionImpl
