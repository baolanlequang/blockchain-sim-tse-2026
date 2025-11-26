/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Allocation Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getAssemblyContext <em>Assembly Context</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getResourceContainer <em>Resource Container</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocationContext()
 * @model
 * @generated
 */
public interface NodeAllocationContext extends Entity {
	/**
	 * Returns the value of the '<em><b>Assembly Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assembly Context</em>' reference.
	 * @see #setAssemblyContext(BlockchainSystemNodeAssemblyContext)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocationContext_AssemblyContext()
	 * @model required="true"
	 * @generated
	 */
	BlockchainSystemNodeAssemblyContext getAssemblyContext();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getAssemblyContext <em>Assembly Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assembly Context</em>' reference.
	 * @see #getAssemblyContext()
	 * @generated
	 */
	void setAssemblyContext(BlockchainSystemNodeAssemblyContext value);

	/**
	 * Returns the value of the '<em><b>Resource Container</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Container</em>' reference.
	 * @see #setResourceContainer(NodeResourceContainer)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocationContext_ResourceContainer()
	 * @model
	 * @generated
	 */
	NodeResourceContainer getResourceContainer();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext#getResourceContainer <em>Resource Container</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Container</em>' reference.
	 * @see #getResourceContainer()
	 * @generated
	 */
	void setResourceContainer(NodeResourceContainer value);

} // NodeAllocationContext
