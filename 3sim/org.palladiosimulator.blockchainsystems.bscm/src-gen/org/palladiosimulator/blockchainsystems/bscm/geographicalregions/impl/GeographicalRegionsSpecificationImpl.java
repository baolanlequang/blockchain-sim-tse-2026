/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Geographical Regions Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionsSpecificationImpl#getRegions <em>Regions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GeographicalRegionsSpecificationImpl extends EntityImpl implements GeographicalRegionsSpecification {
	/**
	 * The cached value of the '{@link #getRegions() <em>Regions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<GeographicalRegion> regions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GeographicalRegionsSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeographicalregionsPackage.Literals.GEOGRAPHICAL_REGIONS_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GeographicalRegion> getRegions() {
		if (regions == null) {
			regions = new EObjectContainmentEList<GeographicalRegion>(GeographicalRegion.class, this,
					GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS);
		}
		return regions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS:
			return ((InternalEList<?>) getRegions()).basicRemove(otherEnd, msgs);
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS:
			return getRegions();
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS:
			getRegions().clear();
			getRegions().addAll((Collection<? extends GeographicalRegion>) newValue);
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS:
			getRegions().clear();
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
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS:
			return regions != null && !regions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GeographicalRegionsSpecificationImpl
