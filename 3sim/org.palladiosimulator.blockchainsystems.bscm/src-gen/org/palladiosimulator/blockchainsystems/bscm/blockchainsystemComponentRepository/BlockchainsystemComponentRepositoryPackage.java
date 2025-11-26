/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository;

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
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryFactory
 * @model kind="package"
 * @generated
 */
public interface BlockchainsystemComponentRepositoryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "blockchainsystemComponentRepository";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/BlockchainSystemComponentRepository/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "blockchainsystemComponentRepository";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BlockchainsystemComponentRepositoryPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentImpl <em>Blockchain System Node Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockchainSystemNodeComponent()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The number of structural features of the '<em>Blockchain System Node Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentRepositoryImpl <em>Blockchain System Node Component Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentRepositoryImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockchainSystemNodeComponentRepository()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Blockchain System Node Component Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidatorComponentImpl <em>Block Validator Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidatorComponentImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValidatorComponent()
	 * @generated
	 */
	int BLOCK_VALIDATOR_COMPONENT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATOR_COMPONENT__ID = BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATOR_COMPONENT__ENTITY_NAME = BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Validation Duration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION = BLOCKCHAIN_SYSTEM_NODE_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Validator Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATOR_COMPONENT_FEATURE_COUNT = BLOCKCHAIN_SYSTEM_NODE_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.MiningProcessComponentImpl <em>Mining Process Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.MiningProcessComponentImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getMiningProcessComponent()
	 * @generated
	 */
	int MINING_PROCESS_COMPONENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINING_PROCESS_COMPONENT__ID = BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINING_PROCESS_COMPONENT__ENTITY_NAME = BLOCKCHAIN_SYSTEM_NODE_COMPONENT__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Is Mining Process Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED = BLOCKCHAIN_SYSTEM_NODE_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mining Process Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINING_PROCESS_COMPONENT_FEATURE_COUNT = BLOCKCHAIN_SYSTEM_NODE_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValiationDurationSpecificationImpl <em>Block Valiation Duration Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValiationDurationSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValiationDurationSpecification()
	 * @generated
	 */
	int BLOCK_VALIATION_DURATION_SPECIFICATION = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIATION_DURATION_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIATION_DURATION_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIATION_DURATION_SPECIFICATION__VALUES = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Valiation Duration Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIATION_DURATION_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidationDurationValueImpl <em>Block Validation Duration Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidationDurationValueImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValidationDurationValue()
	 * @generated
	 */
	int BLOCK_VALIDATION_DURATION_VALUE = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATION_DURATION_VALUE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATION_DURATION_VALUE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATION_DURATION_VALUE__DURATION = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATION_DURATION_VALUE__PROBABILITY = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Block Validation Duration Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_VALIDATION_DURATION_VALUE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent <em>Blockchain System Node Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System Node Component</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent
	 * @generated
	 */
	EClass getBlockchainSystemNodeComponent();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository <em>Blockchain System Node Component Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System Node Component Repository</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository
	 * @generated
	 */
	EClass getBlockchainSystemNodeComponentRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository#getComponents()
	 * @see #getBlockchainSystemNodeComponentRepository()
	 * @generated
	 */
	EReference getBlockchainSystemNodeComponentRepository_Components();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent <em>Block Validator Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Validator Component</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent
	 * @generated
	 */
	EClass getBlockValidatorComponent();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent#getValidationDuration <em>Validation Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Validation Duration</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent#getValidationDuration()
	 * @see #getBlockValidatorComponent()
	 * @generated
	 */
	EReference getBlockValidatorComponent_ValidationDuration();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent <em>Mining Process Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mining Process Component</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent
	 * @generated
	 */
	EClass getMiningProcessComponent();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent#isIsMiningProcessEnabled <em>Is Mining Process Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Mining Process Enabled</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent#isIsMiningProcessEnabled()
	 * @see #getMiningProcessComponent()
	 * @generated
	 */
	EAttribute getMiningProcessComponent_IsMiningProcessEnabled();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification <em>Block Valiation Duration Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Valiation Duration Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification
	 * @generated
	 */
	EClass getBlockValiationDurationSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification#getValues()
	 * @see #getBlockValiationDurationSpecification()
	 * @generated
	 */
	EReference getBlockValiationDurationSpecification_Values();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue <em>Block Validation Duration Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Validation Duration Value</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue
	 * @generated
	 */
	EClass getBlockValidationDurationValue();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue#getDuration()
	 * @see #getBlockValidationDurationValue()
	 * @generated
	 */
	EAttribute getBlockValidationDurationValue_Duration();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidationDurationValue#getProbability()
	 * @see #getBlockValidationDurationValue()
	 * @generated
	 */
	EAttribute getBlockValidationDurationValue_Probability();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BlockchainsystemComponentRepositoryFactory getBlockchainsystemComponentRepositoryFactory();

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
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentImpl <em>Blockchain System Node Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockchainSystemNodeComponent()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM_NODE_COMPONENT = eINSTANCE.getBlockchainSystemNodeComponent();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentRepositoryImpl <em>Blockchain System Node Component Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentRepositoryImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockchainSystemNodeComponentRepository()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY = eINSTANCE.getBlockchainSystemNodeComponentRepository();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS = eINSTANCE
				.getBlockchainSystemNodeComponentRepository_Components();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidatorComponentImpl <em>Block Validator Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidatorComponentImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValidatorComponent()
		 * @generated
		 */
		EClass BLOCK_VALIDATOR_COMPONENT = eINSTANCE.getBlockValidatorComponent();

		/**
		 * The meta object literal for the '<em><b>Validation Duration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION = eINSTANCE
				.getBlockValidatorComponent_ValidationDuration();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.MiningProcessComponentImpl <em>Mining Process Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.MiningProcessComponentImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getMiningProcessComponent()
		 * @generated
		 */
		EClass MINING_PROCESS_COMPONENT = eINSTANCE.getMiningProcessComponent();

		/**
		 * The meta object literal for the '<em><b>Is Mining Process Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED = eINSTANCE
				.getMiningProcessComponent_IsMiningProcessEnabled();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValiationDurationSpecificationImpl <em>Block Valiation Duration Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValiationDurationSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValiationDurationSpecification()
		 * @generated
		 */
		EClass BLOCK_VALIATION_DURATION_SPECIFICATION = eINSTANCE.getBlockValiationDurationSpecification();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_VALIATION_DURATION_SPECIFICATION__VALUES = eINSTANCE
				.getBlockValiationDurationSpecification_Values();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidationDurationValueImpl <em>Block Validation Duration Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidationDurationValueImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainsystemComponentRepositoryPackageImpl#getBlockValidationDurationValue()
		 * @generated
		 */
		EClass BLOCK_VALIDATION_DURATION_VALUE = eINSTANCE.getBlockValidationDurationValue();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_VALIDATION_DURATION_VALUE__DURATION = eINSTANCE.getBlockValidationDurationValue_Duration();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_VALIDATION_DURATION_VALUE__PROBABILITY = eINSTANCE
				.getBlockValidationDurationValue_Probability();

	}

} //BlockchainsystemComponentRepositoryPackage
