/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getMeanTransactionCreationInterval <em>Mean Transaction Creation Interval</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getTransactionPropertiesSpecification <em>Transaction Properties Specification</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionsSpecification()
 * @model
 * @generated
 */
public interface TransactionsSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Mean Transaction Creation Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean Transaction Creation Interval</em>' attribute.
	 * @see #setMeanTransactionCreationInterval(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionsSpecification_MeanTransactionCreationInterval()
	 * @model required="true"
	 * @generated
	 */
	double getMeanTransactionCreationInterval();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getMeanTransactionCreationInterval <em>Mean Transaction Creation Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean Transaction Creation Interval</em>' attribute.
	 * @see #getMeanTransactionCreationInterval()
	 * @generated
	 */
	void setMeanTransactionCreationInterval(double value);

	/**
	 * Returns the value of the '<em><b>Transaction Properties Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transaction Properties Specification</em>' containment reference.
	 * @see #setTransactionPropertiesSpecification(TransactionPropertiesSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage#getTransactionsSpecification_TransactionPropertiesSpecification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TransactionPropertiesSpecification getTransactionPropertiesSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getTransactionPropertiesSpecification <em>Transaction Properties Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transaction Properties Specification</em>' containment reference.
	 * @see #getTransactionPropertiesSpecification()
	 * @generated
	 */
	void setTransactionPropertiesSpecification(TransactionPropertiesSpecification value);

} // TransactionsSpecification
