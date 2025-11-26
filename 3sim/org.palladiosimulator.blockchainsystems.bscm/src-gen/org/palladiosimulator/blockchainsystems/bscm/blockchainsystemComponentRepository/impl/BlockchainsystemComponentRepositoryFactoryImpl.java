/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BlockchainsystemComponentRepositoryFactoryImpl extends EFactoryImpl
		implements BlockchainsystemComponentRepositoryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BlockchainsystemComponentRepositoryFactory init() {
		try {
			BlockchainsystemComponentRepositoryFactory theBlockchainsystemComponentRepositoryFactory = (BlockchainsystemComponentRepositoryFactory) EPackage.Registry.INSTANCE
					.getEFactory(BlockchainsystemComponentRepositoryPackage.eNS_URI);
			if (theBlockchainsystemComponentRepositoryFactory != null) {
				return theBlockchainsystemComponentRepositoryFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BlockchainsystemComponentRepositoryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainsystemComponentRepositoryFactoryImpl() {
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
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY:
			return createBlockchainSystemNodeComponentRepository();
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT:
			return createBlockValidatorComponent();
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT:
			return createMiningProcessComponent();
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIATION_DURATION_SPECIFICATION:
			return createBlockValiationDurationSpecification();
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATION_DURATION_VALUE:
			return createBlockValidationDurationValue();
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
	public BlockchainSystemNodeComponentRepository createBlockchainSystemNodeComponentRepository() {
		BlockchainSystemNodeComponentRepositoryImpl blockchainSystemNodeComponentRepository = new BlockchainSystemNodeComponentRepositoryImpl();
		return blockchainSystemNodeComponentRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockValidatorComponent createBlockValidatorComponent() {
		BlockValidatorComponentImpl blockValidatorComponent = new BlockValidatorComponentImpl();
		return blockValidatorComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MiningProcessComponent createMiningProcessComponent() {
		MiningProcessComponentImpl miningProcessComponent = new MiningProcessComponentImpl();
		return miningProcessComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockValiationDurationSpecification createBlockValiationDurationSpecification() {
		BlockValiationDurationSpecificationImpl blockValiationDurationSpecification = new BlockValiationDurationSpecificationImpl();
		return blockValiationDurationSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockValidationDurationValue createBlockValidationDurationValue() {
		BlockValidationDurationValueImpl blockValidationDurationValue = new BlockValidationDurationValueImpl();
		return blockValidationDurationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainsystemComponentRepositoryPackage getBlockchainsystemComponentRepositoryPackage() {
		return (BlockchainsystemComponentRepositoryPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BlockchainsystemComponentRepositoryPackage getPackage() {
		return BlockchainsystemComponentRepositoryPackage.eINSTANCE;
	}

} //BlockchainsystemComponentRepositoryFactoryImpl
