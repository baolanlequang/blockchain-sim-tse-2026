/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl#getMeanTransactionCreationInterval <em>Mean Transaction Creation Interval</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.transactions.impl.TransactionsSpecificationImpl#getTransactionPropertiesSpecification <em>Transaction Properties Specification</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransactionsSpecificationImpl extends EntityImpl implements TransactionsSpecification {
	/**
	 * The default value of the '{@link #getMeanTransactionCreationInterval() <em>Mean Transaction Creation Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanTransactionCreationInterval()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_TRANSACTION_CREATION_INTERVAL_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMeanTransactionCreationInterval() <em>Mean Transaction Creation Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanTransactionCreationInterval()
	 * @generated
	 * @ordered
	 */
	protected double meanTransactionCreationInterval = MEAN_TRANSACTION_CREATION_INTERVAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTransactionPropertiesSpecification() <em>Transaction Properties Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionPropertiesSpecification()
	 * @generated
	 * @ordered
	 */
	protected TransactionPropertiesSpecification transactionPropertiesSpecification;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransactionsSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransactionsPackage.Literals.TRANSACTIONS_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMeanTransactionCreationInterval() {
		return meanTransactionCreationInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMeanTransactionCreationInterval(double newMeanTransactionCreationInterval) {
		double oldMeanTransactionCreationInterval = meanTransactionCreationInterval;
		meanTransactionCreationInterval = newMeanTransactionCreationInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL,
					oldMeanTransactionCreationInterval, meanTransactionCreationInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionPropertiesSpecification getTransactionPropertiesSpecification() {
		return transactionPropertiesSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransactionPropertiesSpecification(
			TransactionPropertiesSpecification newTransactionPropertiesSpecification, NotificationChain msgs) {
		TransactionPropertiesSpecification oldTransactionPropertiesSpecification = transactionPropertiesSpecification;
		transactionPropertiesSpecification = newTransactionPropertiesSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION,
					oldTransactionPropertiesSpecification, newTransactionPropertiesSpecification);
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
	public void setTransactionPropertiesSpecification(
			TransactionPropertiesSpecification newTransactionPropertiesSpecification) {
		if (newTransactionPropertiesSpecification != transactionPropertiesSpecification) {
			NotificationChain msgs = null;
			if (transactionPropertiesSpecification != null)
				msgs = ((InternalEObject) transactionPropertiesSpecification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION,
						null, msgs);
			if (newTransactionPropertiesSpecification != null)
				msgs = ((InternalEObject) newTransactionPropertiesSpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION,
						null, msgs);
			msgs = basicSetTransactionPropertiesSpecification(newTransactionPropertiesSpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION,
					newTransactionPropertiesSpecification, newTransactionPropertiesSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION:
			return basicSetTransactionPropertiesSpecification(null, msgs);
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
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL:
			return getMeanTransactionCreationInterval();
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION:
			return getTransactionPropertiesSpecification();
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
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL:
			setMeanTransactionCreationInterval((Double) newValue);
			return;
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION:
			setTransactionPropertiesSpecification((TransactionPropertiesSpecification) newValue);
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
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL:
			setMeanTransactionCreationInterval(MEAN_TRANSACTION_CREATION_INTERVAL_EDEFAULT);
			return;
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION:
			setTransactionPropertiesSpecification((TransactionPropertiesSpecification) null);
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
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__MEAN_TRANSACTION_CREATION_INTERVAL:
			return meanTransactionCreationInterval != MEAN_TRANSACTION_CREATION_INTERVAL_EDEFAULT;
		case TransactionsPackage.TRANSACTIONS_SPECIFICATION__TRANSACTION_PROPERTIES_SPECIFICATION:
			return transactionPropertiesSpecification != null;
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
		result.append(" (MeanTransactionCreationInterval: ");
		result.append(meanTransactionCreationInterval);
		result.append(')');
		return result.toString();
	}

} //TransactionsSpecificationImpl
