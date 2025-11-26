/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsFactory
 * @model kind="package"
 * @generated
 */
public interface TransactionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "transactions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/Transactions/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "transactions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransactionsPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl <em>Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionsSpecification()
	 * @generated
	 */
	int TRANSACTIONS_SPECIFICATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Mean Transaction Creation Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Transaction Properties Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTIONS_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationImpl <em>Transaction Properties Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionPropertiesSpecification()
	 * @generated
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION__VALUES = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Transaction Properties Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl <em>Transaction Properties Specification Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionPropertiesSpecificationValue()
	 * @generated
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fee</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Transaction Properties Specification Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_PROPERTIES_SPECIFICATION_VALUE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification
	 * @generated
	 */
	EClass getTransactionsSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getMeanTransactionCreationInterval <em>Mean Transaction Creation Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean Transaction Creation Interval</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getMeanTransactionCreationInterval()
	 * @see #getTransactionsSpecification()
	 * @generated
	 */
	EAttribute getTransactionsSpecification_MeanTransactionCreationInterval();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getTransactionPropertiesSpecification <em>Transaction Properties Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transaction Properties Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification#getTransactionPropertiesSpecification()
	 * @see #getTransactionsSpecification()
	 * @generated
	 */
	EReference getTransactionsSpecification_TransactionPropertiesSpecification();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification <em>Transaction Properties Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transaction Properties Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification
	 * @generated
	 */
	EClass getTransactionPropertiesSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification#getValues()
	 * @see #getTransactionPropertiesSpecification()
	 * @generated
	 */
	EReference getTransactionPropertiesSpecification_Values();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue <em>Transaction Properties Specification Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transaction Properties Specification Value</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue
	 * @generated
	 */
	EClass getTransactionPropertiesSpecificationValue();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getSize()
	 * @see #getTransactionPropertiesSpecificationValue()
	 * @generated
	 */
	EAttribute getTransactionPropertiesSpecificationValue_Size();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getFee <em>Fee</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fee</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getFee()
	 * @see #getTransactionPropertiesSpecificationValue()
	 * @generated
	 */
	EAttribute getTransactionPropertiesSpecificationValue_Fee();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getProbability()
	 * @see #getTransactionPropertiesSpecificationValue()
	 * @generated
	 */
	EAttribute getTransactionPropertiesSpecificationValue_Probability();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getAmount <em>Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amount</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue#getAmount()
	 * @see #getTransactionPropertiesSpecificationValue()
	 * @generated
	 */
	EAttribute getTransactionPropertiesSpecificationValue_Amount();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransactionsFactory getTransactionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl <em>Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionsSpecification()
		 * @generated
		 */
		EClass TRANSACTIONS_SPECIFICATION = eINSTANCE.getTransactionsSpecification();

		/**
		 * The meta object literal for the '<em><b>Mean Transaction Creation Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL = eINSTANCE
				.getTransactionsSpecification_MeanTransactionCreationInterval();

		/**
		 * The meta object literal for the '<em><b>Transaction Properties Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION = eINSTANCE
				.getTransactionsSpecification_TransactionPropertiesSpecification();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationImpl <em>Transaction Properties Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionPropertiesSpecification()
		 * @generated
		 */
		EClass TRANSACTION_PROPERTIES_SPECIFICATION = eINSTANCE.getTransactionPropertiesSpecification();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSACTION_PROPERTIES_SPECIFICATION__VALUES = eINSTANCE
				.getTransactionPropertiesSpecification_Values();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl <em>Transaction Properties Specification Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsPackageImpl#getTransactionPropertiesSpecificationValue()
		 * @generated
		 */
		EClass TRANSACTION_PROPERTIES_SPECIFICATION_VALUE = eINSTANCE.getTransactionPropertiesSpecificationValue();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE = eINSTANCE
				.getTransactionPropertiesSpecificationValue_Size();

		/**
		 * The meta object literal for the '<em><b>Fee</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE = eINSTANCE
				.getTransactionPropertiesSpecificationValue_Fee();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY = eINSTANCE
				.getTransactionPropertiesSpecificationValue_Probability();

		/**
		 * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT = eINSTANCE
				.getTransactionPropertiesSpecificationValue_Amount();

	}

} //TransactionsPackage
