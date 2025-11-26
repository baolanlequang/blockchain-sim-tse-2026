/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block Valiation Duration Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getBlockValiationDurationSpecification()
 * @model
 * @generated
 */
public interface BlockValiationDurationSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getBlockValiationDurationSpecification_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<BlockValidationDurationValue> getValues();

} // BlockValiationDurationSpecification
