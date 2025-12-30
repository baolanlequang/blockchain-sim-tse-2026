/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Allocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getLatencySpecification <em>Latency Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getThroughputSpecification <em>Throughput Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getBandwidthSpecification <em>Bandwidth Specification</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocation()
 * @model
 * @generated
 */
public interface LinkAllocation extends Entity {
	/**
	 * Returns the value of the '<em><b>Latency Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Latency Specification</em>' containment reference.
	 * @see #setLatencySpecification(LinkLatencySpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocation_LatencySpecification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LinkLatencySpecification getLatencySpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getLatencySpecification <em>Latency Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Latency Specification</em>' containment reference.
	 * @see #getLatencySpecification()
	 * @generated
	 */
	void setLatencySpecification(LinkLatencySpecification value);

	/**
	 * Returns the value of the '<em><b>Throughput Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Throughput Specification</em>' containment reference.
	 * @see #setThroughputSpecification(LinkThroughputSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocation_ThroughputSpecification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LinkThroughputSpecification getThroughputSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getThroughputSpecification <em>Throughput Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Throughput Specification</em>' containment reference.
	 * @see #getThroughputSpecification()
	 * @generated
	 */
	void setThroughputSpecification(LinkThroughputSpecification value);

	/**
	 * Returns the value of the '<em><b>Bandwidth Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bandwidth Specification</em>' containment reference.
	 * @see #setBandwidthSpecification(BandwidthSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocation_BandwidthSpecification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BandwidthSpecification getBandwidthSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getBandwidthSpecification <em>Bandwidth Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bandwidth Specification</em>' containment reference.
	 * @see #getBandwidthSpecification()
	 * @generated
	 */
	void setBandwidthSpecification(BandwidthSpecification value);

} // LinkAllocation
