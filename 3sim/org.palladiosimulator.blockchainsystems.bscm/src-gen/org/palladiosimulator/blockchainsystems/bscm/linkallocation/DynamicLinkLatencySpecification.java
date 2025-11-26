/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Link Latency Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkLatencySpecification()
 * @model
 * @generated
 */
public interface DynamicLinkLatencySpecification extends LinkLatencySpecification {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkLatencySpecification_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<DynamicLinkLatencySpecificationValue> getValues();

} // DynamicLinkLatencySpecification
