/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsFactory
 * @model kind="package"
 * @generated
 */
public interface GeographicalregionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "geographicalregions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/GeographicalRegions/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "geographicalregions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GeographicalregionsPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionsSpecificationImpl <em>Geographical Regions Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionsSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl#getGeographicalRegionsSpecification()
	 * @generated
	 */
	int GEOGRAPHICAL_REGIONS_SPECIFICATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGIONS_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGIONS_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Geographical Regions Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGIONS_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionImpl <em>Geographical Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl#getGeographicalRegion()
	 * @generated
	 */
	int GEOGRAPHICAL_REGION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Region Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGION__REGION_NAME = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Geographical Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEOGRAPHICAL_REGION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification <em>Geographical Regions Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Geographical Regions Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification
	 * @generated
	 */
	EClass getGeographicalRegionsSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Regions</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification#getRegions()
	 * @see #getGeographicalRegionsSpecification()
	 * @generated
	 */
	EReference getGeographicalRegionsSpecification_Regions();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion <em>Geographical Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Geographical Region</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion
	 * @generated
	 */
	EClass getGeographicalRegion();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion#getRegionName <em>Region Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Region Name</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion#getRegionName()
	 * @see #getGeographicalRegion()
	 * @generated
	 */
	EAttribute getGeographicalRegion_RegionName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GeographicalregionsFactory getGeographicalregionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionsSpecificationImpl <em>Geographical Regions Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionsSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl#getGeographicalRegionsSpecification()
		 * @generated
		 */
		EClass GEOGRAPHICAL_REGIONS_SPECIFICATION = eINSTANCE.getGeographicalRegionsSpecification();

		/**
		 * The meta object literal for the '<em><b>Regions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEOGRAPHICAL_REGIONS_SPECIFICATION__REGIONS = eINSTANCE
				.getGeographicalRegionsSpecification_Regions();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionImpl <em>Geographical Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalRegionImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.impl.GeographicalregionsPackageImpl#getGeographicalRegion()
		 * @generated
		 */
		EClass GEOGRAPHICAL_REGION = eINSTANCE.getGeographicalRegion();

		/**
		 * The meta object literal for the '<em><b>Region Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GEOGRAPHICAL_REGION__REGION_NAME = eINSTANCE.getGeographicalRegion_RegionName();

	}

} //GeographicalregionsPackage
