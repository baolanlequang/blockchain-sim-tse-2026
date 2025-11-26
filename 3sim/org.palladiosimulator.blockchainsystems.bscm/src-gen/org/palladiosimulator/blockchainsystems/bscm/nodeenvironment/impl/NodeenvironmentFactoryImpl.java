/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodeenvironmentFactoryImpl extends EFactoryImpl implements NodeenvironmentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NodeenvironmentFactory init() {
		try {
			NodeenvironmentFactory theNodeenvironmentFactory = (NodeenvironmentFactory) EPackage.Registry.INSTANCE
					.getEFactory(NodeenvironmentPackage.eNS_URI);
			if (theNodeenvironmentFactory != null) {
				return theNodeenvironmentFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NodeenvironmentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeenvironmentFactoryImpl() {
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
		case NodeenvironmentPackage.NODE_ENVIRONMENT:
			return createNodeEnvironment();
		case NodeenvironmentPackage.NODE_RESOURCE_CONTAINER:
			return createNodeResourceContainer();
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
	public NodeEnvironment createNodeEnvironment() {
		NodeEnvironmentImpl nodeEnvironment = new NodeEnvironmentImpl();
		return nodeEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeResourceContainer createNodeResourceContainer() {
		NodeResourceContainerImpl nodeResourceContainer = new NodeResourceContainerImpl();
		return nodeResourceContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeenvironmentPackage getNodeenvironmentPackage() {
		return (NodeenvironmentPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NodeenvironmentPackage getPackage() {
		return NodeenvironmentPackage.eINSTANCE;
	}

} //NodeenvironmentFactoryImpl
