/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block Validator Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.impl.BlockValidatorComponentImpl#getValidationDuration <em>Validation Duration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockValidatorComponentImpl extends BlockchainSystemNodeComponentImpl implements BlockValidatorComponent {
	/**
	 * The cached value of the '{@link #getValidationDuration() <em>Validation Duration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationDuration()
	 * @generated
	 * @ordered
	 */
	protected BlockValiationDurationSpecification validationDuration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockValidatorComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BlockchainsystemComponentRepositoryPackage.Literals.BLOCK_VALIDATOR_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockValiationDurationSpecification getValidationDuration() {
		return validationDuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValidationDuration(BlockValiationDurationSpecification newValidationDuration,
			NotificationChain msgs) {
		BlockValiationDurationSpecification oldValidationDuration = validationDuration;
		validationDuration = newValidationDuration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION,
					oldValidationDuration, newValidationDuration);
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
	public void setValidationDuration(BlockValiationDurationSpecification newValidationDuration) {
		if (newValidationDuration != validationDuration) {
			NotificationChain msgs = null;
			if (validationDuration != null)
				msgs = ((InternalEObject) validationDuration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION,
						null, msgs);
			if (newValidationDuration != null)
				msgs = ((InternalEObject) newValidationDuration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION,
						null, msgs);
			msgs = basicSetValidationDuration(newValidationDuration, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION,
					newValidationDuration, newValidationDuration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION:
			return basicSetValidationDuration(null, msgs);
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
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION:
			return getValidationDuration();
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
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION:
			setValidationDuration((BlockValiationDurationSpecification) newValue);
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
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION:
			setValidationDuration((BlockValiationDurationSpecification) null);
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
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT__VALIDATION_DURATION:
			return validationDuration != null;
		}
		return super.eIsSet(featureID);
	}

} //BlockValidatorComponentImpl
