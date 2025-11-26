/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transaction Properties Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecification()
 * @model
 * @generated
 */
public interface TransactionPropertiesSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecification_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TransactionPropertiesSpecificationValue> getValues();

} // TransactionPropertiesSpecification
