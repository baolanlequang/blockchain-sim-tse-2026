/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Blockchain System Node Assembly Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext#getEncapsulatedComponent <em>Encapsulated Component</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeAssemblyContext()
 * @model
 * @generated
 */
public interface BlockchainSystemNodeAssemblyContext extends Entity {
	/**
	 * Returns the value of the '<em><b>Encapsulated Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encapsulated Component</em>' reference.
	 * @see #setEncapsulatedComponent(BlockchainSystemNodeComponent)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getBlockchainSystemNodeAssemblyContext_EncapsulatedComponent()
	 * @model required="true"
	 * @generated
	 */
	BlockchainSystemNodeComponent getEncapsulatedComponent();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext#getEncapsulatedComponent <em>Encapsulated Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encapsulated Component</em>' reference.
	 * @see #getEncapsulatedComponent()
	 * @generated
	 */
	void setEncapsulatedComponent(BlockchainSystemNodeComponent value);

} // BlockchainSystemNodeAssemblyContext
