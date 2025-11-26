/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Link Throughput Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecification()
 * @model
 * @generated
 */
public interface DynamicLinkThroughputSpecification extends LinkThroughputSpecification {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getDynamicLinkThroughputSpecification_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<DynamicLinkThroughputSpecificationValue> getValues();

} // DynamicLinkThroughputSpecification
