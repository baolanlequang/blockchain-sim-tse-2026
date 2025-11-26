/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodeallocationFactoryImpl extends EFactoryImpl implements NodeallocationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NodeallocationFactory init() {
		try {
			NodeallocationFactory theNodeallocationFactory = (NodeallocationFactory) EPackage.Registry.INSTANCE
					.getEFactory(NodeallocationPackage.eNS_URI);
			if (theNodeallocationFactory != null) {
				return theNodeallocationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NodeallocationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeallocationFactoryImpl() {
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
		case NodeallocationPackage.NODE_ALLOCATION:
			return createNodeAllocation();
		case NodeallocationPackage.NODE_ALLOCATION_REPOSITORY:
			return createNodeAllocationRepository();
		case NodeallocationPackage.NODE_ALLOCATION_CONTEXT:
			return createNodeAllocationContext();
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
	public NodeAllocation createNodeAllocation() {
		NodeAllocationImpl nodeAllocation = new NodeAllocationImpl();
		return nodeAllocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeAllocationRepository createNodeAllocationRepository() {
		NodeAllocationRepositoryImpl nodeAllocationRepository = new NodeAllocationRepositoryImpl();
		return nodeAllocationRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeAllocationContext createNodeAllocationContext() {
		NodeAllocationContextImpl nodeAllocationContext = new NodeAllocationContextImpl();
		return nodeAllocationContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeallocationPackage getNodeallocationPackage() {
		return (NodeallocationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NodeallocationPackage getPackage() {
		return NodeallocationPackage.eINSTANCE;
	}

} //NodeallocationFactoryImpl
