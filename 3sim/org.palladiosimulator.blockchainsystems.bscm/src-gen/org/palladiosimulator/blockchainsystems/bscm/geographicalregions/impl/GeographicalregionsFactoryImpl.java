/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GeographicalregionsFactoryImpl extends EFactoryImpl implements GeographicalregionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GeographicalregionsFactory init() {
		try {
			GeographicalregionsFactory theGeographicalregionsFactory = (GeographicalregionsFactory) EPackage.Registry.INSTANCE
					.getEFactory(GeographicalregionsPackage.eNS_URI);
			if (theGeographicalregionsFactory != null) {
				return theGeographicalregionsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GeographicalregionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeographicalregionsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case GeographicalregionsPackage.GEOGRAPHICAL_REGIONS_SPECIFICATION:
			return createGeographicalRegionsSpecification();
		case GeographicalregionsPackage.GEOGRAPHICAL_REGION:
			return createGeographicalRegion();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalRegionsSpecification createGeographicalRegionsSpecification() {
		GeographicalRegionsSpecificationImpl geographicalRegionsSpecification = new GeographicalRegionsSpecificationImpl();
		return geographicalRegionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalRegion createGeographicalRegion() {
		GeographicalRegionImpl geographicalRegion = new GeographicalRegionImpl();
		return geographicalRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalregionsPackage getGeographicalregionsPackage() {
		return (GeographicalregionsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GeographicalregionsPackage getPackage() {
		return GeographicalregionsPackage.eINSTANCE;
	}

} //GeographicalregionsFactoryImpl
