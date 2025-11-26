/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Blockchain System Node System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getMiningProcessAssembly <em>Mining Process Assembly</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBlockValidatorAssembly <em>Block Validator Assembly</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getAssemblyContexts <em>Assembly Contexts</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeSystem()
 * @model
 * @generated
 */
public interface BlockchainSystemNodeSystem extends Entity {
	/**
	 * Returns the value of the '<em><b>Mining Process Assembly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mining Process Assembly</em>' reference.
	 * @see #setMiningProcessAssembly(BlockchainSystemNodeAssemblyContext)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeSystem_MiningProcessAssembly()
	 * @model required="true"
	 * @generated
	 */
	BlockchainSystemNodeAssemblyContext getMiningProcessAssembly();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getMiningProcessAssembly <em>Mining Process Assembly</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mining Process Assembly</em>' reference.
	 * @see #getMiningProcessAssembly()
	 * @generated
	 */
	void setMiningProcessAssembly(BlockchainSystemNodeAssemblyContext value);

	/**
	 * Returns the value of the '<em><b>Block Validator Assembly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Block Validator Assembly</em>' reference.
	 * @see #setBlockValidatorAssembly(BlockchainSystemNodeAssemblyContext)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeSystem_BlockValidatorAssembly()
	 * @model required="true"
	 * @generated
	 */
	BlockchainSystemNodeAssemblyContext getBlockValidatorAssembly();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBlockValidatorAssembly <em>Block Validator Assembly</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Block Validator Assembly</em>' reference.
	 * @see #getBlockValidatorAssembly()
	 * @generated
	 */
	void setBlockValidatorAssembly(BlockchainSystemNodeAssemblyContext value);

	/**
	 * Returns the value of the '<em><b>Assembly Contexts</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assembly Contexts</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeSystem_AssemblyContexts()
	 * @model containment="true"
	 * @generated
	 */
	EList<BlockchainSystemNodeAssemblyContext> getAssemblyContexts();

	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' containment reference.
	 * @see #setBehavior(NodeBehaviorSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeSystem_Behavior()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NodeBehaviorSpecification getBehavior();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBehavior <em>Behavior</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' containment reference.
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(NodeBehaviorSpecification value);

} // BlockchainSystemNodeSystem
