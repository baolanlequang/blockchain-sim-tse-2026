/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Static Link Throughput Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification#getThroughput <em>Throughput</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getStaticLinkThroughputSpecification()
 * @model
 * @generated
 */
public interface StaticLinkThroughputSpecification extends LinkThroughputSpecification {
	/**
	 * Returns the value of the '<em><b>Throughput</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: Bit per Second (bps)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Throughput</em>' attribute.
	 * @see #setThroughput(long)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getStaticLinkThroughputSpecification_Throughput()
	 * @model required="true"
	 * @generated
	 */
	long getThroughput();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification#getThroughput <em>Throughput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Throughput</em>' attribute.
	 * @see #getThroughput()
	 * @generated
	 */
	void setThroughput(long value);

} // StaticLinkThroughputSpecification
