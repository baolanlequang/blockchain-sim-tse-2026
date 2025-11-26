/**
 */
package org.palladiosimulator.blockchainsystems.bscm.geographicalregions;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Geographical Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion#getRegionName <em>Region Name</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage#getGeographicalRegion()
 * @model
 * @generated
 */
public interface GeographicalRegion extends Entity {
	/**
	 * Returns the value of the '<em><b>Region Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Region Name</em>' attribute.
	 * @see #setRegionName(String)
	 * @see org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage#getGeographicalRegion_RegionName()
	 * @model required="true"
	 * @generated
	 */
	String getRegionName();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion#getRegionName <em>Region Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Region Name</em>' attribute.
	 * @see #getRegionName()
	 * @generated
	 */
	void setRegionName(String value);

} // GeographicalRegion
