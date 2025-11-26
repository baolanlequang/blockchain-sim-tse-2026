/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem;

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
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemFactory
 * @model kind="package"
 * @generated
 */
public interface BlockchainsystemPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "blockchainsystem";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/BlockchainSystem/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "blockchainsystem";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BlockchainsystemPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl <em>Blockchain System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl#getBlockchainSystem()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Network</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__NETWORK = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transactions Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Geographical Regions Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Blockchain System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl <em>Blockchain System Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl#getBlockchainSystemSpecification()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Mean Block Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Num Of Required Security Confirmations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS = EntityPackage.ENTITY_FEATURE_COUNT
			+ 1;

	/**
	 * The feature id for the '<em><b>Max Block Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Block Reward</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Blockchain System Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem <em>Blockchain System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem
	 * @generated
	 */
	EClass getBlockchainSystem();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getNetwork <em>Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Network</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getNetwork()
	 * @see #getBlockchainSystem()
	 * @generated
	 */
	EReference getBlockchainSystem_Network();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getSpecification()
	 * @see #getBlockchainSystem()
	 * @generated
	 */
	EReference getBlockchainSystem_Specification();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getTransactionsSpecification <em>Transactions Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Transactions Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getTransactionsSpecification()
	 * @see #getBlockchainSystem()
	 * @generated
	 */
	EReference getBlockchainSystem_TransactionsSpecification();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getGeographicalRegionsSpecification <em>Geographical Regions Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Geographical Regions Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getGeographicalRegionsSpecification()
	 * @see #getBlockchainSystem()
	 * @generated
	 */
	EReference getBlockchainSystem_GeographicalRegionsSpecification();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification <em>Blockchain System Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification
	 * @generated
	 */
	EClass getBlockchainSystemSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMeanBlockTime <em>Mean Block Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean Block Time</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMeanBlockTime()
	 * @see #getBlockchainSystemSpecification()
	 * @generated
	 */
	EAttribute getBlockchainSystemSpecification_MeanBlockTime();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumOfRequiredSecurityConfirmations <em>Num Of Required Security Confirmations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Of Required Security Confirmations</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumOfRequiredSecurityConfirmations()
	 * @see #getBlockchainSystemSpecification()
	 * @generated
	 */
	EAttribute getBlockchainSystemSpecification_NumOfRequiredSecurityConfirmations();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMaxBlockSize <em>Max Block Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Block Size</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMaxBlockSize()
	 * @see #getBlockchainSystemSpecification()
	 * @generated
	 */
	EAttribute getBlockchainSystemSpecification_MaxBlockSize();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getBlockReward <em>Block Reward</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Block Reward</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getBlockReward()
	 * @see #getBlockchainSystemSpecification()
	 * @generated
	 */
	EAttribute getBlockchainSystemSpecification_BlockReward();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BlockchainsystemFactory getBlockchainsystemFactory();

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
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl <em>Blockchain System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl#getBlockchainSystem()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM = eINSTANCE.getBlockchainSystem();

		/**
		 * The meta object literal for the '<em><b>Network</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM__NETWORK = eINSTANCE.getBlockchainSystem_Network();

		/**
		 * The meta object literal for the '<em><b>Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM__SPECIFICATION = eINSTANCE.getBlockchainSystem_Specification();

		/**
		 * The meta object literal for the '<em><b>Transactions Specification</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION = eINSTANCE
				.getBlockchainSystem_TransactionsSpecification();

		/**
		 * The meta object literal for the '<em><b>Geographical Regions Specification</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION = eINSTANCE
				.getBlockchainSystem_GeographicalRegionsSpecification();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl <em>Blockchain System Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainsystemPackageImpl#getBlockchainSystemSpecification()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM_SPECIFICATION = eINSTANCE.getBlockchainSystemSpecification();

		/**
		 * The meta object literal for the '<em><b>Mean Block Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME = eINSTANCE
				.getBlockchainSystemSpecification_MeanBlockTime();

		/**
		 * The meta object literal for the '<em><b>Num Of Required Security Confirmations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS = eINSTANCE
				.getBlockchainSystemSpecification_NumOfRequiredSecurityConfirmations();

		/**
		 * The meta object literal for the '<em><b>Max Block Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE = eINSTANCE
				.getBlockchainSystemSpecification_MaxBlockSize();

		/**
		 * The meta object literal for the '<em><b>Block Reward</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD = eINSTANCE
				.getBlockchainSystemSpecification_BlockReward();

	}

} //BlockchainsystemPackage
