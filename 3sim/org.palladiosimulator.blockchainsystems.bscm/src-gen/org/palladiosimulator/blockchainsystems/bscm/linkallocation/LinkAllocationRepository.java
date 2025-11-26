/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Allocation Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository#getLinkAllocations <em>Link Allocations</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocationRepository()
 * @model
 * @generated
 */
public interface LinkAllocationRepository extends Entity {
	/**
	 * Returns the value of the '<em><b>Link Allocations</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Allocations</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage#getLinkAllocationRepository_LinkAllocations()
	 * @model containment="true"
	 * @generated
	 */
	EList<LinkAllocation> getLinkAllocations();

} // LinkAllocationRepository
