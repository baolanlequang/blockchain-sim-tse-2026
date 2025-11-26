/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block Validator Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent#getValidationDuration <em>Validation Duration</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getBlockValidatorComponent()
 * @model
 * @generated
 */
public interface BlockValidatorComponent extends BlockchainSystemNodeComponent {
	/**
	 * Returns the value of the '<em><b>Validation Duration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Duration</em>' containment reference.
	 * @see #setValidationDuration(BlockValiationDurationSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getBlockValidatorComponent_ValidationDuration()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BlockValiationDurationSpecification getValidationDuration();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent#getValidationDuration <em>Validation Duration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Duration</em>' containment reference.
	 * @see #getValidationDuration()
	 * @generated
	 */
	void setValidationDuration(BlockValiationDurationSpecification value);

} // BlockValidatorComponent
