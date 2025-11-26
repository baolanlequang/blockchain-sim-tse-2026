/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.BidirectionalLink;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bidirectional Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.BidirectionalLinkImpl#getConnectedNodes <em>Connected Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BidirectionalLinkImpl extends LinkImpl implements BidirectionalLink {
	/**
	 * The cached value of the '{@link #getConnectedNodes() <em>Connected Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectedNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> connectedNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BidirectionalLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.BIDIRECTIONAL_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Node> getConnectedNodes() {
		if (connectedNodes == null) {
			connectedNodes = new EObjectResolvingEList<Node>(Node.class, this,
					P2pnetworkPackage.BIDIRECTIONAL_LINK__CONNECTED_NODES);
		}
		return connectedNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case P2pnetworkPackage.BIDIRECTIONAL_LINK__CONNECTED_NODES:
			return getConnectedNodes();
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
		case P2pnetworkPackage.BIDIRECTIONAL_LINK__CONNECTED_NODES:
			getConnectedNodes().clear();
			getConnectedNodes().addAll((Collection<? extends Node>) newValue);
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
		case P2pnetworkPackage.BIDIRECTIONAL_LINK__CONNECTED_NODES:
			getConnectedNodes().clear();
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
		case P2pnetworkPackage.BIDIRECTIONAL_LINK__CONNECTED_NODES:
			return connectedNodes != null && !connectedNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BidirectionalLinkImpl
