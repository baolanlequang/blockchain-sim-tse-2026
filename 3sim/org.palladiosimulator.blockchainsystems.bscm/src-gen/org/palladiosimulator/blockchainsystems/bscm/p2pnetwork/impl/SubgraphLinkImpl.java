/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subgraph Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphLinkImpl#getConnectedSubgraphs <em>Connected Subgraphs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubgraphLinkImpl extends EntityImpl implements SubgraphLink {
	/**
	 * The cached value of the '{@link #getAllocation() <em>Allocation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocation()
	 * @generated
	 * @ordered
	 */
	protected LinkAllocation allocation;

	/**
	 * The cached value of the '{@link #getConnectedSubgraphs() <em>Connected Subgraphs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectedSubgraphs()
	 * @generated
	 * @ordered
	 */
	protected EList<SubgraphSpecification> connectedSubgraphs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubgraphLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.SUBGRAPH_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation getAllocation() {
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAllocation(LinkAllocation newAllocation, NotificationChain msgs) {
		LinkAllocation oldAllocation = allocation;
		allocation = newAllocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION, oldAllocation, newAllocation);
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
	public void setAllocation(LinkAllocation newAllocation) {
		if (newAllocation != allocation) {
			NotificationChain msgs = null;
			if (allocation != null)
				msgs = ((InternalEObject) allocation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION, null, msgs);
			if (newAllocation != null)
				msgs = ((InternalEObject) newAllocation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION, null, msgs);
			msgs = basicSetAllocation(newAllocation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION,
					newAllocation, newAllocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SubgraphSpecification> getConnectedSubgraphs() {
		if (connectedSubgraphs == null) {
			connectedSubgraphs = new EObjectResolvingEList<SubgraphSpecification>(SubgraphSpecification.class, this,
					P2pnetworkPackage.SUBGRAPH_LINK__CONNECTED_SUBGRAPHS);
		}
		return connectedSubgraphs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION:
			return basicSetAllocation(null, msgs);
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
		case P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION:
			return getAllocation();
		case P2pnetworkPackage.SUBGRAPH_LINK__CONNECTED_SUBGRAPHS:
			return getConnectedSubgraphs();
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
		case P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION:
			setAllocation((LinkAllocation) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_LINK__CONNECTED_SUBGRAPHS:
			getConnectedSubgraphs().clear();
			getConnectedSubgraphs().addAll((Collection<? extends SubgraphSpecification>) newValue);
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
		case P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION:
			setAllocation((LinkAllocation) null);
			return;
		case P2pnetworkPackage.SUBGRAPH_LINK__CONNECTED_SUBGRAPHS:
			getConnectedSubgraphs().clear();
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
		case P2pnetworkPackage.SUBGRAPH_LINK__ALLOCATION:
			return allocation != null;
		case P2pnetworkPackage.SUBGRAPH_LINK__CONNECTED_SUBGRAPHS:
			return connectedSubgraphs != null && !connectedSubgraphs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SubgraphLinkImpl
