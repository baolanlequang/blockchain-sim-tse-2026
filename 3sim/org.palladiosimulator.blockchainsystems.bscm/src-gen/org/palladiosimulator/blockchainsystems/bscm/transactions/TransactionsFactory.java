/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage
 * @generated
 */
public interface TransactionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransactionsFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Specification</em>'.
	 * @generated
	 */
	TransactionsSpecification createTransactionsSpecification();

	/**
	 * Returns a new object of class '<em>Transaction Properties Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transaction Properties Specification</em>'.
	 * @generated
	 */
	TransactionPropertiesSpecification createTransactionPropertiesSpecification();

	/**
	 * Returns a new object of class '<em>Transaction Properties Specification Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transaction Properties Specification Value</em>'.
	 * @generated
	 */
	TransactionPropertiesSpecificationValue createTransactionPropertiesSpecificationValue();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransactionsPackage getTransactionsPackage();

} //TransactionsFactory
