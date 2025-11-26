/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.NetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>P2P Network</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.P2PNetworkImpl#getTopology <em>Topology</em>}</li>
 * </ul>
 *
 * @generated
 */
public class P2PNetworkImpl extends EntityImpl implements P2PNetwork {
	/**
	 * The cached value of the '{@link #getTopology() <em>Topology</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopology()
	 * @generated
	 * @ordered
	 */
	protected NetworkTopology topology;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected P2PNetworkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.P2P_NETWORK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NetworkTopology getTopology() {
		return topology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTopology(NetworkTopology newTopology, NotificationChain msgs) {
		NetworkTopology oldTopology = topology;
		topology = newTopology;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.P2P_NETWORK__TOPOLOGY, oldTopology, newTopology);
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
	public void setTopology(NetworkTopology newTopology) {
		if (newTopology != topology) {
			NotificationChain msgs = null;
			if (topology != null)
				msgs = ((InternalEObject) topology).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.P2P_NETWORK__TOPOLOGY, null, msgs);
			if (newTopology != null)
				msgs = ((InternalEObject) newTopology).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.P2P_NETWORK__TOPOLOGY, null, msgs);
			msgs = basicSetTopology(newTopology, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.P2P_NETWORK__TOPOLOGY, newTopology,
					newTopology));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case P2pnetworkPackage.P2P_NETWORK__TOPOLOGY:
			return basicSetTopology(null, msgs);
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
		case P2pnetworkPackage.P2P_NETWORK__TOPOLOGY:
			return getTopology();
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
		case P2pnetworkPackage.P2P_NETWORK__TOPOLOGY:
			setTopology((NetworkTopology) newValue);
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
		case P2pnetworkPackage.P2P_NETWORK__TOPOLOGY:
			setTopology((NetworkTopology) null);
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
		case P2pnetworkPackage.P2P_NETWORK__TOPOLOGY:
			return topology != null;
		}
		return super.eIsSet(featureID);
	}

} //P2PNetworkImpl
