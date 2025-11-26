/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Link Latency Specification Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl#getLatency <em>Latency</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicLinkLatencySpecificationValueImpl extends EntityImpl
		implements DynamicLinkLatencySpecificationValue {
	/**
	 * The default value of the '{@link #getLatency() <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatency()
	 * @generated
	 * @ordered
	 */
	protected static final long LATENCY_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getLatency() <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatency()
	 * @generated
	 * @ordered
	 */
	protected long latency = LATENCY_EDEFAULT;

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
	 * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected static final long DURATION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected long duration = DURATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DynamicLinkLatencySpecificationValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkallocationPackage.Literals.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getLatency() {
		return latency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLatency(long newLatency) {
		long oldLatency = latency;
		latency = newLatency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY, oldLatency, latency));
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
					LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY, oldProbability,
					probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getDuration() {
		return duration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDuration(long newDuration) {
		long oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION, oldDuration, duration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY:
			return getLatency();
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY:
			return getProbability();
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION:
			return getDuration();
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
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY:
			setLatency((Long) newValue);
			return;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY:
			setProbability((Double) newValue);
			return;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION:
			setDuration((Long) newValue);
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
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY:
			setLatency(LATENCY_EDEFAULT);
			return;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY:
			setProbability(PROBABILITY_EDEFAULT);
			return;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION:
			setDuration(DURATION_EDEFAULT);
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
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY:
			return latency != LATENCY_EDEFAULT;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY:
			return probability != PROBABILITY_EDEFAULT;
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION:
			return duration != DURATION_EDEFAULT;
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
		result.append(" (Latency: ");
		result.append(latency);
		result.append(", Probability: ");
		result.append(probability);
		result.append(", Duration: ");
		result.append(duration);
		result.append(')');
		return result.toString();
	}

} //DynamicLinkLatencySpecificationValueImpl
