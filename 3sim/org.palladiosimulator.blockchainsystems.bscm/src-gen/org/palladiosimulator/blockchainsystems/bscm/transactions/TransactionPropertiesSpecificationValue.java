/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transaction Properties Specification Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getSize <em>Size</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getFee <em>Fee</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getAmount <em>Amount</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecificationValue()
 * @model
 * @generated
 */
public interface TransactionPropertiesSpecificationValue extends Entity {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecificationValue_Size()
	 * @model required="true"
	 * @generated
	 */
	int getSize();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(int value);

	/**
	 * Returns the value of the '<em><b>Fee</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fee</em>' attribute.
	 * @see #setFee(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecificationValue_Fee()
	 * @model required="true"
	 * @generated
	 */
	double getFee();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getFee <em>Fee</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fee</em>' attribute.
	 * @see #getFee()
	 * @generated
	 */
	void setFee(double value);

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecificationValue_Probability()
	 * @model required="true"
	 * @generated
	 */
	double getProbability();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(double value);

	/**
	 * Returns the value of the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Amount</em>' attribute.
	 * @see #setAmount(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionPropertiesSpecificationValue_Amount()
	 * @model
	 * @generated
	 */
	double getAmount();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getAmount <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Amount</em>' attribute.
	 * @see #getAmount()
	 * @generated
	 */
	void setAmount(double value);

} // TransactionPropertiesSpecificationValue
