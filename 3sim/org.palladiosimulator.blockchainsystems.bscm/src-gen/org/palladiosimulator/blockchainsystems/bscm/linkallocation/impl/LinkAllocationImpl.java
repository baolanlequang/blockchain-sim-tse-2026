/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link Allocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl#getLatencySpecification <em>Latency Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl#getThroughputSpecification <em>Throughput Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl#getBandwidthSpecification <em>Bandwidth Specification</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkAllocationImpl extends EntityImpl implements LinkAllocation {
	/**
	 * The cached value of the '{@link #getLatencySpecification() <em>Latency Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatencySpecification()
	 * @generated
	 * @ordered
	 */
	protected LinkLatencySpecification latencySpecification;

	/**
	 * The cached value of the '{@link #getThroughputSpecification() <em>Throughput Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThroughputSpecification()
	 * @generated
	 * @ordered
	 */
	protected LinkThroughputSpecification throughputSpecification;

	/**
	 * The cached value of the '{@link #getBandwidthSpecification() <em>Bandwidth Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidthSpecification()
	 * @generated
	 * @ordered
	 */
	protected BandwidthSpecification bandwidthSpecification;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkAllocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkallocationPackage.Literals.LINK_ALLOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkLatencySpecification getLatencySpecification() {
		return latencySpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLatencySpecification(LinkLatencySpecification newLatencySpecification,
			NotificationChain msgs) {
		LinkLatencySpecification oldLatencySpecification = latencySpecification;
		latencySpecification = newLatencySpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION, oldLatencySpecification,
					newLatencySpecification);
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
	public void setLatencySpecification(LinkLatencySpecification newLatencySpecification) {
		if (newLatencySpecification != latencySpecification) {
			NotificationChain msgs = null;
			if (latencySpecification != null)
				msgs = ((InternalEObject) latencySpecification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION, null,
						msgs);
			if (newLatencySpecification != null)
				msgs = ((InternalEObject) newLatencySpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION, null,
						msgs);
			msgs = basicSetLatencySpecification(newLatencySpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION, newLatencySpecification,
					newLatencySpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkThroughputSpecification getThroughputSpecification() {
		return throughputSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThroughputSpecification(LinkThroughputSpecification newThroughputSpecification,
			NotificationChain msgs) {
		LinkThroughputSpecification oldThroughputSpecification = throughputSpecification;
		throughputSpecification = newThroughputSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION, oldThroughputSpecification,
					newThroughputSpecification);
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
	public void setThroughputSpecification(LinkThroughputSpecification newThroughputSpecification) {
		if (newThroughputSpecification != throughputSpecification) {
			NotificationChain msgs = null;
			if (throughputSpecification != null)
				msgs = ((InternalEObject) throughputSpecification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION, null,
						msgs);
			if (newThroughputSpecification != null)
				msgs = ((InternalEObject) newThroughputSpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION, null,
						msgs);
			msgs = basicSetThroughputSpecification(newThroughputSpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION, newThroughputSpecification,
					newThroughputSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BandwidthSpecification getBandwidthSpecification() {
		return bandwidthSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBandwidthSpecification(BandwidthSpecification newBandwidthSpecification,
			NotificationChain msgs) {
		BandwidthSpecification oldBandwidthSpecification = bandwidthSpecification;
		bandwidthSpecification = newBandwidthSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION, oldBandwidthSpecification,
					newBandwidthSpecification);
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
	public void setBandwidthSpecification(BandwidthSpecification newBandwidthSpecification) {
		if (newBandwidthSpecification != bandwidthSpecification) {
			NotificationChain msgs = null;
			if (bandwidthSpecification != null)
				msgs = ((InternalEObject) bandwidthSpecification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION, null,
						msgs);
			if (newBandwidthSpecification != null)
				msgs = ((InternalEObject) newBandwidthSpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION, null,
						msgs);
			msgs = basicSetBandwidthSpecification(newBandwidthSpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION, newBandwidthSpecification,
					newBandwidthSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION:
			return basicSetLatencySpecification(null, msgs);
		case LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION:
			return basicSetThroughputSpecification(null, msgs);
		case LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION:
			return basicSetBandwidthSpecification(null, msgs);
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
		case LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION:
			return getLatencySpecification();
		case LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION:
			return getThroughputSpecification();
		case LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION:
			return getBandwidthSpecification();
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
		case LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION:
			setLatencySpecification((LinkLatencySpecification) newValue);
			return;
		case LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION:
			setThroughputSpecification((LinkThroughputSpecification) newValue);
			return;
		case LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION:
			setBandwidthSpecification((BandwidthSpecification) newValue);
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
		case LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION:
			setLatencySpecification((LinkLatencySpecification) null);
			return;
		case LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION:
			setThroughputSpecification((LinkThroughputSpecification) null);
			return;
		case LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION:
			setBandwidthSpecification((BandwidthSpecification) null);
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
		case LinkallocationPackage.LINK_ALLOCATION__LATENCY_SPECIFICATION:
			return latencySpecification != null;
		case LinkallocationPackage.LINK_ALLOCATION__THROUGHPUT_SPECIFICATION:
			return throughputSpecification != null;
		case LinkallocationPackage.LINK_ALLOCATION__BANDWIDTH_SPECIFICATION:
			return bandwidthSpecification != null;
		}
		return super.eIsSet(featureID);
	}

} //LinkAllocationImpl
