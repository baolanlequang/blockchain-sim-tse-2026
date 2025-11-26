/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage
 * @generated
 */
public interface NodesystemFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodesystemFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Blockchain System Node System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Blockchain System Node System</em>'.
	 * @generated
	 */
	BlockchainSystemNodeSystem createBlockchainSystemNodeSystem();

	/**
	 * Returns a new object of class '<em>Blockchain System Node Assembly Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Blockchain System Node Assembly Context</em>'.
	 * @generated
	 */
	BlockchainSystemNodeAssemblyContext createBlockchainSystemNodeAssemblyContext();

	/**
	 * Returns a new object of class '<em>Node Behavior Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Behavior Specification</em>'.
	 * @generated
	 */
	NodeBehaviorSpecification createNodeBehaviorSpecification();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NodesystemPackage getNodesystemPackage();

} //NodesystemFactory
