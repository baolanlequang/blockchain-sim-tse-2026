/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mining Process Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.MiningProcessComponentImpl#isIsMiningProcessEnabled <em>Is Mining Process Enabled</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MiningProcessComponentImpl extends BlockchainSystemNodeComponentImpl implements MiningProcessComponent {
	/**
	 * The default value of the '{@link #isIsMiningProcessEnabled() <em>Is Mining Process Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsMiningProcessEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_MINING_PROCESS_ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsMiningProcessEnabled() <em>Is Mining Process Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsMiningProcessEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean isMiningProcessEnabled = IS_MINING_PROCESS_ENABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MiningProcessComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BlockchainsystemComponentRepositoryPackage.Literals.MINING_PROCESS_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsMiningProcessEnabled() {
		return isMiningProcessEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsMiningProcessEnabled(boolean newIsMiningProcessEnabled) {
		boolean oldIsMiningProcessEnabled = isMiningProcessEnabled;
		isMiningProcessEnabled = newIsMiningProcessEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED,
					oldIsMiningProcessEnabled, isMiningProcessEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED:
			return isIsMiningProcessEnabled();
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
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED:
			setIsMiningProcessEnabled((Boolean) newValue);
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
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED:
			setIsMiningProcessEnabled(IS_MINING_PROCESS_ENABLED_EDEFAULT);
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
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT__IS_MINING_PROCESS_ENABLED:
			return isMiningProcessEnabled != IS_MINING_PROCESS_ENABLED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (IsMiningProcessEnabled: ");
		result.append(isMiningProcessEnabled);
		result.append(')');
		return result.toString();
	}

} //MiningProcessComponentImpl
