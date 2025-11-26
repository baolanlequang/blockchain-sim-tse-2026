/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodesystemFactoryImpl extends EFactoryImpl implements NodesystemFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NodesystemFactory init() {
		try {
			NodesystemFactory theNodesystemFactory = (NodesystemFactory) EPackage.Registry.INSTANCE
					.getEFactory(NodesystemPackage.eNS_URI);
			if (theNodesystemFactory != null) {
				return theNodesystemFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NodesystemFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodesystemFactoryImpl() {
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
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM:
			return createBlockchainSystemNodeSystem();
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT:
			return createBlockchainSystemNodeAssemblyContext();
		case NodesystemPackage.NODE_BEHAVIOR_SPECIFICATION:
			return createNodeBehaviorSpecification();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case NodesystemPackage.NODE_BEHAVIOR:
			return createNodeBehaviorFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case NodesystemPackage.NODE_BEHAVIOR:
			return convertNodeBehaviorToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeSystem createBlockchainSystemNodeSystem() {
		BlockchainSystemNodeSystemImpl blockchainSystemNodeSystem = new BlockchainSystemNodeSystemImpl();
		return blockchainSystemNodeSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeAssemblyContext createBlockchainSystemNodeAssemblyContext() {
		BlockchainSystemNodeAssemblyContextImpl blockchainSystemNodeAssemblyContext = new BlockchainSystemNodeAssemblyContextImpl();
		return blockchainSystemNodeAssemblyContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeBehaviorSpecification createNodeBehaviorSpecification() {
		NodeBehaviorSpecificationImpl nodeBehaviorSpecification = new NodeBehaviorSpecificationImpl();
		return nodeBehaviorSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeBehavior createNodeBehaviorFromString(EDataType eDataType, String initialValue) {
		NodeBehavior result = NodeBehavior.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNodeBehaviorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodesystemPackage getNodesystemPackage() {
		return (NodesystemPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NodesystemPackage getPackage() {
		return NodesystemPackage.eINSTANCE;
	}

} //NodesystemFactoryImpl
