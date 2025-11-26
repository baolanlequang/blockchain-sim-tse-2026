/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Static Link Latency Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification#getLatency <em>Latency</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getStaticLinkLatencySpecification()
 * @model
 * @generated
 */
public interface StaticLinkLatencySpecification extends LinkLatencySpecification {
	/**
	 * Returns the value of the '<em><b>Latency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: ms
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Latency</em>' attribute.
	 * @see #setLatency(long)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getStaticLinkLatencySpecification_Latency()
	 * @model required="true"
	 * @generated
	 */
	long getLatency();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification#getLatency <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Latency</em>' attribute.
	 * @see #getLatency()
	 * @generated
	 */
	void setLatency(long value);

} // StaticLinkLatencySpecification
