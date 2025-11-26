/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subgraph Node Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl#getNumberOfNodeOccurences <em>Number Of Node Occurences</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphNodeTemplateImpl#isIsSubgraphProxy <em>Is Subgraph Proxy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubgraphNodeTemplateImpl extends EntityImpl implements SubgraphNodeTemplate {
	/**
	 * The default value of the '{@link #getNumberOfNodeOccurences() <em>Number Of Node Occurences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNodeOccurences()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_NODE_OCCURENCES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfNodeOccurences() <em>Number Of Node Occurences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfNodeOccurences()
	 * @generated
	 * @ordered
	 */
	protected int numberOfNodeOccurences = NUMBER_OF_NODE_OCCURENCES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAllocation() <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocation()
	 * @generated
	 * @ordered
	 */
	protected NodeAllocation allocation;

	/**
	 * The default value of the '{@link #isIsSubgraphProxy() <em>Is Subgraph Proxy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSubgraphProxy()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SUBGRAPH_PROXY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsSubgraphProxy() <em>Is Subgraph Proxy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSubgraphProxy()
	 * @generated
	 * @ordered
	 */
	protected boolean isSubgraphProxy = IS_SUBGRAPH_PROXY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubgraphNodeTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.SUBGRAPH_NODE_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumberOfNodeOccurences() {
		return numberOfNodeOccurences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfNodeOccurences(int newNumberOfNodeOccurences) {
		int oldNumberOfNodeOccurences = numberOfNodeOccurences;
		numberOfNodeOccurences = newNumberOfNodeOccurences;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES, oldNumberOfNodeOccurences,
					numberOfNodeOccurences));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeAllocation getAllocation() {
		if (allocation != null && allocation.eIsProxy()) {
			InternalEObject oldAllocation = (InternalEObject) allocation;
			allocation = (NodeAllocation) eResolveProxy(oldAllocation);
			if (allocation != oldAllocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION, oldAllocation, allocation));
			}
		}
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeAllocation basicGetAllocation() {
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAllocation(NodeAllocation newAllocation) {
		NodeAllocation oldAllocation = allocation;
		allocation = newAllocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION,
					oldAllocation, allocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsSubgraphProxy() {
		return isSubgraphProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsSubgraphProxy(boolean newIsSubgraphProxy) {
		boolean oldIsSubgraphProxy = isSubgraphProxy;
		isSubgraphProxy = newIsSubgraphProxy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY, oldIsSubgraphProxy, isSubgraphProxy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES:
			return getNumberOfNodeOccurences();
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION:
			if (resolve)
				return getAllocation();
			return basicGetAllocation();
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY:
			return isIsSubgraphProxy();
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
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES:
			setNumberOfNodeOccurences((Integer) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION:
			setAllocation((NodeAllocation) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY:
			setIsSubgraphProxy((Boolean) newValue);
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
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES:
			setNumberOfNodeOccurences(NUMBER_OF_NODE_OCCURENCES_EDEFAULT);
			return;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION:
			setAllocation((NodeAllocation) null);
			return;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY:
			setIsSubgraphProxy(IS_SUBGRAPH_PROXY_EDEFAULT);
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
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES:
			return numberOfNodeOccurences != NUMBER_OF_NODE_OCCURENCES_EDEFAULT;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__ALLOCATION:
			return allocation != null;
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY:
			return isSubgraphProxy != IS_SUBGRAPH_PROXY_EDEFAULT;
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
		result.append(" (NumberOfNodeOccurences: ");
		result.append(numberOfNodeOccurences);
		result.append(", IsSubgraphProxy: ");
		result.append(isSubgraphProxy);
		result.append(')');
		return result.toString();
	}

} //SubgraphNodeTemplateImpl
