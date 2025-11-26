/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transaction Properties Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransactionPropertiesSpecificationImpl extends EntityImpl implements TransactionPropertiesSpecification {
	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<TransactionPropertiesSpecificationValue> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransactionPropertiesSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TransactionPropertiesSpecificationValue> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<TransactionPropertiesSpecificationValue>(
					TransactionPropertiesSpecificationValue.class, this,
					TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES:
			return ((InternalEList<?>) getValues()).basicRemove(otherEnd, msgs);
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES:
			return getValues();
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES:
			getValues().clear();
			getValues().addAll((Collection<? extends TransactionPropertiesSpecificationValue>) newValue);
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES:
			getValues().clear();
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION__VALUES:
			return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TransactionPropertiesSpecificationImpl
