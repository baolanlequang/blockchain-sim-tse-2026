/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transaction Properties Specification Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl#getSize <em>Size</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl#getFee <em>Fee</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionPropertiesSpecificationValueImpl#getAmount <em>Amount</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransactionPropertiesSpecificationValueImpl extends EntityImpl
		implements TransactionPropertiesSpecificationValue {
	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected int size = SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFee() <em>Fee</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFee()
	 * @generated
	 * @ordered
	 */
	protected static final double FEE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getFee() <em>Fee</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFee()
	 * @generated
	 * @ordered
	 */
	protected double fee = FEE_EDEFAULT;

	/**
	 * The default value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected static final double PROBABILITY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected double probability = PROBABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAmount()
	 * @generated
	 * @ordered
	 */
	protected static final double AMOUNT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAmount()
	 * @generated
	 * @ordered
	 */
	protected double amount = AMOUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransactionPropertiesSpecificationValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSize(int newSize) {
		int oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE, oldSize, size));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getFee() {
		return fee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFee(double newFee) {
		double oldFee = fee;
		fee = newFee;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE, oldFee, fee));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProbability(double newProbability) {
		double oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY, oldProbability,
					probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAmount() {
		return amount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAmount(double newAmount) {
		double oldAmount = amount;
		amount = newAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT, oldAmount, amount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE:
			return getSize();
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE:
			return getFee();
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY:
			return getProbability();
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT:
			return getAmount();
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE:
			setSize((Integer) newValue);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE:
			setFee((Double) newValue);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY:
			setProbability((Double) newValue);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT:
			setAmount((Double) newValue);
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE:
			setSize(SIZE_EDEFAULT);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE:
			setFee(FEE_EDEFAULT);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY:
			setProbability(PROBABILITY_EDEFAULT);
			return;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT:
			setAmount(AMOUNT_EDEFAULT);
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
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE:
			return size != SIZE_EDEFAULT;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE:
			return fee != FEE_EDEFAULT;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY:
			return probability != PROBABILITY_EDEFAULT;
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT:
			return amount != AMOUNT_EDEFAULT;
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
		result.append(" (Size: ");
		result.append(size);
		result.append(", Fee: ");
		result.append(fee);
		result.append(", Probability: ");
		result.append(probability);
		result.append(", Amount: ");
		result.append(amount);
		result.append(')');
		return result.toString();
	}

} //TransactionPropertiesSpecificationValueImpl
