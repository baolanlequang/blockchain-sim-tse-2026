/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Allocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getAllocationContexts <em>Allocation Contexts</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeAllocationEnvironment <em>Node Allocation Environment</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeSystem <em>Node System</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getGeographicalRegion <em>Geographical Region</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocation()
 * @model
 * @generated
 */
public interface NodeAllocation extends Entity {
	/**
	 * Returns the value of the '<em><b>Allocation Contexts</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation Contexts</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocation_AllocationContexts()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeAllocationContext> getAllocationContexts();

	/**
	 * Returns the value of the '<em><b>Node Allocation Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Allocation Environment</em>' containment reference.
	 * @see #setNodeAllocationEnvironment(NodeEnvironment)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocation_NodeAllocationEnvironment()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NodeEnvironment getNodeAllocationEnvironment();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeAllocationEnvironment <em>Node Allocation Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Allocation Environment</em>' containment reference.
	 * @see #getNodeAllocationEnvironment()
	 * @generated
	 */
	void setNodeAllocationEnvironment(NodeEnvironment value);

	/**
	 * Returns the value of the '<em><b>Node System</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node System</em>' containment reference.
	 * @see #setNodeSystem(BlockchainSystemNodeSystem)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocation_NodeSystem()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BlockchainSystemNodeSystem getNodeSystem();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getNodeSystem <em>Node System</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node System</em>' containment reference.
	 * @see #getNodeSystem()
	 * @generated
	 */
	void setNodeSystem(BlockchainSystemNodeSystem value);

	/**
	 * Returns the value of the '<em><b>Geographical Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Geographical Region</em>' reference.
	 * @see #setGeographicalRegion(GeographicalRegion)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage#getNodeAllocation_GeographicalRegion()
	 * @model required="true"
	 * @generated
	 */
	GeographicalRegion getGeographicalRegion();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation#getGeographicalRegion <em>Geographical Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Geographical Region</em>' reference.
	 * @see #getGeographicalRegion()
	 * @generated
	 */
	void setGeographicalRegion(GeographicalRegion value);

} // NodeAllocation
