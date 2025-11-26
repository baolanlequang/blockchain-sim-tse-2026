/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.transactions.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransactionsFactoryImpl extends EFactoryImpl implements TransactionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransactionsFactory init() {
		try {
			TransactionsFactory theTransactionsFactory = (TransactionsFactory) EPackage.Registry.INSTANCE
					.getEFactory(TransactionsPackage.eNS_URI);
			if (theTransactionsFactory != null) {
				return theTransactionsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TransactionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransactionsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION:
			return createTransactionsSpecification();
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION:
			return createTransactionPropertiesSpecification();
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE:
			return createTransactionPropertiesSpecificationValue();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionsSpecification createTransactionsSpecification() {
		TransactionsSpecificationImpl transactionsSpecification = new TransactionsSpecificationImpl();
		return transactionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionPropertiesSpecification createTransactionPropertiesSpecification() {
		TransactionPropertiesSpecificationImpl transactionPropertiesSpecification = new TransactionPropertiesSpecificationImpl();
		return transactionPropertiesSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionPropertiesSpecificationValue createTransactionPropertiesSpecificationValue() {
		TransactionPropertiesSpecificationValueImpl transactionPropertiesSpecificationValue = new TransactionPropertiesSpecificationValueImpl();
		return transactionPropertiesSpecificationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionsPackage getTransactionsPackage() {
		return (TransactionsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TransactionsPackage getPackage() {
		return TransactionsPackage.eINSTANCE;
	}

} //TransactionsFactoryImpl
