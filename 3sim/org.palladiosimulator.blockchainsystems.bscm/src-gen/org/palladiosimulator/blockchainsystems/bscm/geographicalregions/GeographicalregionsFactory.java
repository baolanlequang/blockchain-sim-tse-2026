/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage
 * @generated
 */
public interface GeographicalregionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GeographicalregionsFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Geographical Regions Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Geographical Regions Specification</em>'.
	 * @generated
	 */
	GeographicalRegionsSpecification createGeographicalRegionsSpecification();

	/**
	 * Returns a new object of class '<em>Geographical Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Geographical Region</em>'.
	 * @generated
	 */
	GeographicalRegion createGeographicalRegion();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GeographicalregionsPackage getGeographicalregionsPackage();

} //GeographicalregionsFactory
