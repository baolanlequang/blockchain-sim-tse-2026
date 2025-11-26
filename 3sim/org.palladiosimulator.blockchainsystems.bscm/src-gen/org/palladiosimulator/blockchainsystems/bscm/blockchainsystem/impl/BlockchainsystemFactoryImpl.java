/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BlockchainsystemFactoryImpl extends EFactoryImpl implements BlockchainsystemFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BlockchainsystemFactory init() {
		try {
			BlockchainsystemFactory theBlockchainsystemFactory = (BlockchainsystemFactory) EPackage.Registry.INSTANCE
					.getEFactory(BlockchainsystemPackage.eNS_URI);
			if (theBlockchainsystemFactory != null) {
				return theBlockchainsystemFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BlockchainsystemFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainsystemFactoryImpl() {
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM:
			return createBlockchainSystem();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION:
			return createBlockchainSystemSpecification();
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
	public BlockchainSystem createBlockchainSystem() {
		BlockchainSystemImpl blockchainSystem = new BlockchainSystemImpl();
		return blockchainSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemSpecification createBlockchainSystemSpecification() {
		BlockchainSystemSpecificationImpl blockchainSystemSpecification = new BlockchainSystemSpecificationImpl();
		return blockchainSystemSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainsystemPackage getBlockchainsystemPackage() {
		return (BlockchainsystemPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BlockchainsystemPackage getPackage() {
		return BlockchainsystemPackage.eINSTANCE;
	}

} //BlockchainsystemFactoryImpl
