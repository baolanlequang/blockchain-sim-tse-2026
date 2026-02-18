/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bandwidth Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getBandwidth <em>Bandwidth</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityLinkTarget <em>Heterogeneity Link Target</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityNodeTarget <em>Heterogeneity Node Target</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getBandwidthSpecification()
 * @model
 * @generated
 */
public interface BandwidthSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Bandwidth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bandwidth</em>' attribute.
	 * @see #setBandwidth(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getBandwidthSpecification_Bandwidth()
	 * @model
	 * @generated
	 */
	double getBandwidth();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getBandwidth <em>Bandwidth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bandwidth</em>' attribute.
	 * @see #getBandwidth()
	 * @generated
	 */
	void setBandwidth(double value);

	/**
	 * Returns the value of the '<em><b>Heterogeneity Link Target</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heterogeneity Link Target</em>' attribute.
	 * @see #setHeterogeneityLinkTarget(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getBandwidthSpecification_HeterogeneityLinkTarget()
	 * @model default="0.0" required="true"
	 * @generated
	 */
	double getHeterogeneityLinkTarget();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityLinkTarget <em>Heterogeneity Link Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heterogeneity Link Target</em>' attribute.
	 * @see #getHeterogeneityLinkTarget()
	 * @generated
	 */
	void setHeterogeneityLinkTarget(double value);

	/**
	 * Returns the value of the '<em><b>Heterogeneity Node Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heterogeneity Node Target</em>' attribute.
	 * @see #setHeterogeneityNodeTarget(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getBandwidthSpecification_HeterogeneityNodeTarget()
	 * @model required="true"
	 * @generated
	 */
	double getHeterogeneityNodeTarget();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityNodeTarget <em>Heterogeneity Node Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heterogeneity Node Target</em>' attribute.
	 * @see #getHeterogeneityNodeTarget()
	 * @generated
	 */
	void setHeterogeneityNodeTarget(double value);

} // BandwidthSpecification
