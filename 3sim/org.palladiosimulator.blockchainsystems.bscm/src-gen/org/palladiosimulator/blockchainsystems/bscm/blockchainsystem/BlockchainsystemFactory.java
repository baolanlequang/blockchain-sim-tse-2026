/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage
 * @generated
 */
public interface BlockchainsystemFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BlockchainsystemFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Blockchain System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Blockchain System</em>'.
	 * @generated
	 */
	BlockchainSystem createBlockchainSystem();

	/**
	 * Returns a new object of class '<em>Blockchain System Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Blockchain System Specification</em>'.
	 * @generated
	 */
	BlockchainSystemSpecification createBlockchainSystemSpecification();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BlockchainsystemPackage getBlockchainsystemPackage();

} //BlockchainsystemFactory
