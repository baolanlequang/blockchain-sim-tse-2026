/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Geographical Regions Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification#getRegions <em>Regions</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage#getGeographicalRegionsSpecification()
 * @model
 * @generated
 */
public interface GeographicalRegionsSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage#getGeographicalRegionsSpecification_Regions()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<GeographicalRegion> getRegions();

} // GeographicalRegionsSpecification
