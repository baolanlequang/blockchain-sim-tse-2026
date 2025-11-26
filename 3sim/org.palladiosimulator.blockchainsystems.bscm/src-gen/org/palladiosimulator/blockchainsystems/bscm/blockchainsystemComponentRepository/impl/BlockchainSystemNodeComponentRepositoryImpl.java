/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponent;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainSystemNodeComponentRepository;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Blockchain System Node Component Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockchainSystemNodeComponentRepositoryImpl#getComponents <em>Components</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockchainSystemNodeComponentRepositoryImpl extends EntityImpl
		implements BlockchainSystemNodeComponentRepository {
	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<BlockchainSystemNodeComponent> components;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockchainSystemNodeComponentRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BlockchainsystemComponentRepositoryPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BlockchainSystemNodeComponent> getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList<BlockchainSystemNodeComponent>(BlockchainSystemNodeComponent.class,
					this,
					BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS:
			return ((InternalEList<?>) getComponents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS:
			return getComponents();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS:
			getComponents().clear();
			getComponents().addAll((Collection<? extends BlockchainSystemNodeComponent>) newValue);
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
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS:
			getComponents().clear();
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
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY__COMPONENTS:
			return components != null && !components.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BlockchainSystemNodeComponentRepositoryImpl
