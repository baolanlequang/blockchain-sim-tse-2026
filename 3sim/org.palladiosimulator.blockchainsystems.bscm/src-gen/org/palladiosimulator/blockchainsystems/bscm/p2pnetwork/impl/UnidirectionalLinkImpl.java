/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unidirectional Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl#getFromNode <em>From Node</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.UnidirectionalLinkImpl#getToNode <em>To Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnidirectionalLinkImpl extends LinkImpl implements UnidirectionalLink {
	/**
	 * The cached value of the '{@link #getFromNode() <em>From Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromNode()
	 * @generated
	 * @ordered
	 */
	protected Node fromNode;

	/**
	 * The cached value of the '{@link #getToNode() <em>To Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToNode()
	 * @generated
	 * @ordered
	 */
	protected Node toNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnidirectionalLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.UNIDIRECTIONAL_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node getFromNode() {
		if (fromNode != null && fromNode.eIsProxy()) {
			InternalEObject oldFromNode = (InternalEObject) fromNode;
			fromNode = (Node) eResolveProxy(oldFromNode);
			if (fromNode != oldFromNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE, oldFromNode, fromNode));
			}
		}
		return fromNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetFromNode() {
		return fromNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFromNode(Node newFromNode) {
		Node oldFromNode = fromNode;
		fromNode = newFromNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE,
					oldFromNode, fromNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node getToNode() {
		if (toNode != null && toNode.eIsProxy()) {
			InternalEObject oldToNode = (InternalEObject) toNode;
			toNode = (Node) eResolveProxy(oldToNode);
			if (toNode != oldToNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE, oldToNode, toNode));
			}
		}
		return toNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetToNode() {
		return toNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setToNode(Node newToNode) {
		Node oldToNode = toNode;
		toNode = newToNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE,
					oldToNode, toNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE:
			if (resolve)
				return getFromNode();
			return basicGetFromNode();
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE:
			if (resolve)
				return getToNode();
			return basicGetToNode();
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
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE:
			setFromNode((Node) newValue);
			return;
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE:
			setToNode((Node) newValue);
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
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE:
			setFromNode((Node) null);
			return;
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE:
			setToNode((Node) null);
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
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__FROM_NODE:
			return fromNode != null;
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK__TO_NODE:
			return toNode != null;
		}
		return super.eIsSet(featureID);
	}

} //UnidirectionalLinkImpl
