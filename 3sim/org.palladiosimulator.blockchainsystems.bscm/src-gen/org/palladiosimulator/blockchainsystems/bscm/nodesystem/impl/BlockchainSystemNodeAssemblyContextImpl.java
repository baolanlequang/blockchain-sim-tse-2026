/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Blockchain System Node Assembly Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeAssemblyContextImpl#getEncapsulatedComponent <em>Encapsulated Component</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockchainSystemNodeAssemblyContextImpl extends EntityImpl implements BlockchainSystemNodeAssemblyContext {
	/**
	 * The cached value of the '{@link #getEncapsulatedComponent() <em>Encapsulated Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncapsulatedComponent()
	 * @generated
	 * @ordered
	 */
	protected BlockchainSystemNodeComponent encapsulatedComponent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockchainSystemNodeAssemblyContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeComponent getEncapsulatedComponent() {
		if (encapsulatedComponent != null && encapsulatedComponent.eIsProxy()) {
			InternalEObject oldEncapsulatedComponent = (InternalEObject) encapsulatedComponent;
			encapsulatedComponent = (BlockchainSystemNodeComponent) eResolveProxy(oldEncapsulatedComponent);
			if (encapsulatedComponent != oldEncapsulatedComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT,
							oldEncapsulatedComponent, encapsulatedComponent));
			}
		}
		return encapsulatedComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainSystemNodeComponent basicGetEncapsulatedComponent() {
		return encapsulatedComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEncapsulatedComponent(BlockchainSystemNodeComponent newEncapsulatedComponent) {
		BlockchainSystemNodeComponent oldEncapsulatedComponent = encapsulatedComponent;
		encapsulatedComponent = newEncapsulatedComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT,
					oldEncapsulatedComponent, encapsulatedComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT:
			if (resolve)
				return getEncapsulatedComponent();
			return basicGetEncapsulatedComponent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT:
			setEncapsulatedComponent((BlockchainSystemNodeComponent) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT:
			setEncapsulatedComponent((BlockchainSystemNodeComponent) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT:
			return encapsulatedComponent != null;
		}
		return super.eIsSet(featureID);
	}

} //BlockchainSystemNodeAssemblyContextImpl
