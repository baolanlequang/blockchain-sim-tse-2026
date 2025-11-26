/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Blockchain System Node System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl#getMiningProcessAssembly <em>Mining Process Assembly</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl#getBlockValidatorAssembly <em>Block Validator Assembly</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl#getAssemblyContexts <em>Assembly Contexts</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockchainSystemNodeSystemImpl extends EntityImpl implements BlockchainSystemNodeSystem {
	/**
	 * The cached value of the '{@link #getMiningProcessAssembly() <em>Mining Process Assembly</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMiningProcessAssembly()
	 * @generated
	 * @ordered
	 */
	protected BlockchainSystemNodeAssemblyContext miningProcessAssembly;

	/**
	 * The cached value of the '{@link #getBlockValidatorAssembly() <em>Block Validator Assembly</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockValidatorAssembly()
	 * @generated
	 * @ordered
	 */
	protected BlockchainSystemNodeAssemblyContext blockValidatorAssembly;

	/**
	 * The cached value of the '{@link #getAssemblyContexts() <em>Assembly Contexts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssemblyContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<BlockchainSystemNodeAssemblyContext> assemblyContexts;

	/**
	 * The cached value of the '{@link #getBehavior() <em>Behavior</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected NodeBehaviorSpecification behavior;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockchainSystemNodeSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeAssemblyContext getMiningProcessAssembly() {
		if (miningProcessAssembly != null && miningProcessAssembly.eIsProxy()) {
			InternalEObject oldMiningProcessAssembly = (InternalEObject) miningProcessAssembly;
			miningProcessAssembly = (BlockchainSystemNodeAssemblyContext) eResolveProxy(oldMiningProcessAssembly);
			if (miningProcessAssembly != oldMiningProcessAssembly) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY,
							oldMiningProcessAssembly, miningProcessAssembly));
			}
		}
		return miningProcessAssembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainSystemNodeAssemblyContext basicGetMiningProcessAssembly() {
		return miningProcessAssembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMiningProcessAssembly(BlockchainSystemNodeAssemblyContext newMiningProcessAssembly) {
		BlockchainSystemNodeAssemblyContext oldMiningProcessAssembly = miningProcessAssembly;
		miningProcessAssembly = newMiningProcessAssembly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY, oldMiningProcessAssembly,
					miningProcessAssembly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeAssemblyContext getBlockValidatorAssembly() {
		if (blockValidatorAssembly != null && blockValidatorAssembly.eIsProxy()) {
			InternalEObject oldBlockValidatorAssembly = (InternalEObject) blockValidatorAssembly;
			blockValidatorAssembly = (BlockchainSystemNodeAssemblyContext) eResolveProxy(oldBlockValidatorAssembly);
			if (blockValidatorAssembly != oldBlockValidatorAssembly) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY,
							oldBlockValidatorAssembly, blockValidatorAssembly));
			}
		}
		return blockValidatorAssembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainSystemNodeAssemblyContext basicGetBlockValidatorAssembly() {
		return blockValidatorAssembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBlockValidatorAssembly(BlockchainSystemNodeAssemblyContext newBlockValidatorAssembly) {
		BlockchainSystemNodeAssemblyContext oldBlockValidatorAssembly = blockValidatorAssembly;
		blockValidatorAssembly = newBlockValidatorAssembly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY,
					oldBlockValidatorAssembly, blockValidatorAssembly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BlockchainSystemNodeAssemblyContext> getAssemblyContexts() {
		if (assemblyContexts == null) {
			assemblyContexts = new EObjectContainmentEList<BlockchainSystemNodeAssemblyContext>(
					BlockchainSystemNodeAssemblyContext.class, this,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS);
		}
		return assemblyContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeBehaviorSpecification getBehavior() {
		return behavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBehavior(NodeBehaviorSpecification newBehavior, NotificationChain msgs) {
		NodeBehaviorSpecification oldBehavior = behavior;
		behavior = newBehavior;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR, oldBehavior, newBehavior);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBehavior(NodeBehaviorSpecification newBehavior) {
		if (newBehavior != behavior) {
			NotificationChain msgs = null;
			if (behavior != null)
				msgs = ((InternalEObject) behavior).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR, null, msgs);
			if (newBehavior != null)
				msgs = ((InternalEObject) newBehavior).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR, null, msgs);
			msgs = basicSetBehavior(newBehavior, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR, newBehavior, newBehavior));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
			return ((InternalEList<?>) getAssemblyContexts()).basicRemove(otherEnd, msgs);
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			return basicSetBehavior(null, msgs);
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
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY:
			if (resolve)
				return getMiningProcessAssembly();
			return basicGetMiningProcessAssembly();
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY:
			if (resolve)
				return getBlockValidatorAssembly();
			return basicGetBlockValidatorAssembly();
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
			return getAssemblyContexts();
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			return getBehavior();
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
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY:
			setMiningProcessAssembly((BlockchainSystemNodeAssemblyContext) newValue);
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY:
			setBlockValidatorAssembly((BlockchainSystemNodeAssemblyContext) newValue);
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
			getAssemblyContexts().clear();
			getAssemblyContexts().addAll((Collection<? extends BlockchainSystemNodeAssemblyContext>) newValue);
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			setBehavior((NodeBehaviorSpecification) newValue);
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
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY:
			setMiningProcessAssembly((BlockchainSystemNodeAssemblyContext) null);
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY:
			setBlockValidatorAssembly((BlockchainSystemNodeAssemblyContext) null);
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
			getAssemblyContexts().clear();
			return;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			setBehavior((NodeBehaviorSpecification) null);
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
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY:
			return miningProcessAssembly != null;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY:
			return blockValidatorAssembly != null;
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
			return assemblyContexts != null && !assemblyContexts.isEmpty();
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			return behavior != null;
		}
		return super.eIsSet(featureID);
	}

} //BlockchainSystemNodeSystemImpl
