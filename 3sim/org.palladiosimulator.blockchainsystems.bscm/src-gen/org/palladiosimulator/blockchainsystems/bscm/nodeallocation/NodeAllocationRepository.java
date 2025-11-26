/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Allocation Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationRepository#getNodeAllocations <em>Node Allocations</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocationRepository()
 * @model
 * @generated
 */
public interface NodeAllocationRepository extends Entity {
	/**
	 * Returns the value of the '<em><b>Node Allocations</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Allocations</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocationRepository_NodeAllocations()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeAllocation> getNodeAllocations();

} // NodeAllocationRepository
