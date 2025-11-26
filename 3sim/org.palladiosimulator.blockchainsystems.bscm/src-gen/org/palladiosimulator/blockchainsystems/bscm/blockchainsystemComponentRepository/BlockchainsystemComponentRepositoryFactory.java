/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage
 * @generated
 */
public interface BlockchainsystemComponentRepositoryFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BlockchainsystemComponentRepositoryFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Blockchain System Node Component Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Blockchain System Node Component Repository</em>'.
	 * @generated
	 */
	BlockchainSystemNodeComponentRepository createBlockchainSystemNodeComponentRepository();

	/**
	 * Returns a new object of class '<em>Block Validator Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Validator Component</em>'.
	 * @generated
	 */
	BlockValidatorComponent createBlockValidatorComponent();

	/**
	 * Returns a new object of class '<em>Mining Process Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mining Process Component</em>'.
	 * @generated
	 */
	MiningProcessComponent createMiningProcessComponent();

	/**
	 * Returns a new object of class '<em>Block Valiation Duration Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Valiation Duration Specification</em>'.
	 * @generated
	 */
	BlockValiationDurationSpecification createBlockValiationDurationSpecification();

	/**
	 * Returns a new object of class '<em>Block Validation Duration Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Validation Duration Value</em>'.
	 * @generated
	 */
	BlockValidationDurationValue createBlockValidationDurationValue();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BlockchainsystemComponentRepositoryPackage getBlockchainsystemComponentRepositoryPackage();

} //BlockchainsystemComponentRepositoryFactory
