/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Link Throughput Specification Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getThroughput <em>Throughput</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecificationValue()
 * @model
 * @generated
 */
public interface DynamicLinkThroughputSpecificationValue extends Entity {
	/**
	 * Returns the value of the '<em><b>Throughput</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: Bit per Second (bps)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Throughput</em>' attribute.
	 * @see #setThroughput(long)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecificationValue_Throughput()
	 * @model required="true"
	 * @generated
	 */
	long getThroughput();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getThroughput <em>Throughput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Throughput</em>' attribute.
	 * @see #getThroughput()
	 * @generated
	 */
	void setThroughput(long value);

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: 0.0 - 1.0
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecificationValue_Probability()
	 * @model required="true"
	 * @generated
	 */
	double getProbability();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(double value);

	/**
	 * Returns the value of the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: ms
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(long)
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecificationValue_Duration()
	 * @model required="true"
	 * @generated
	 */
	long getDuration();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getDuration <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated
	 */
	void setDuration(long value);

} // DynamicLinkThroughputSpecificationValue
