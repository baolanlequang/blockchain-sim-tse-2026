/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphLink;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connected Subgraphs Network Topology</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl#getSubgraphs <em>Subgraphs</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.ConnectedSubgraphsNetworkTopologyImpl#getSubgraphLinks <em>Subgraph Links</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectedSubgraphsNetworkTopologyImpl extends ConstraintNetworkTopologyImpl
		implements ConnectedSubgraphsNetworkTopology {
	/**
	 * The cached value of the '{@link #getSubgraphs() <em>Subgraphs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubgraphs()
	 * @generated
	 * @ordered
	 */
	protected EList<SubgraphSpecification> subgraphs;

	/**
	 * The cached value of the '{@link #getSubgraphLinks() <em>Subgraph Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubgraphLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<SubgraphLink> subgraphLinks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectedSubgraphsNetworkTopologyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SubgraphSpecification> getSubgraphs() {
		if (subgraphs == null) {
			subgraphs = new EObjectContainmentEList<SubgraphSpecification>(SubgraphSpecification.class, this,
					P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS);
		}
		return subgraphs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SubgraphLink> getSubgraphLinks() {
		if (subgraphLinks == null) {
			subgraphLinks = new EObjectContainmentEList<SubgraphLink>(SubgraphLink.class, this,
					P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS);
		}
		return subgraphLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS:
			return ((InternalEList<?>) getSubgraphs()).basicRemove(otherEnd, msgs);
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS:
			return ((InternalEList<?>) getSubgraphLinks()).basicRemove(otherEnd, msgs);
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
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS:
			return getSubgraphs();
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS:
			return getSubgraphLinks();
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
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS:
			getSubgraphs().clear();
			getSubgraphs().addAll((Collection<? extends SubgraphSpecification>) newValue);
			return;
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS:
			getSubgraphLinks().clear();
			getSubgraphLinks().addAll((Collection<? extends SubgraphLink>) newValue);
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
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS:
			getSubgraphs().clear();
			return;
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS:
			getSubgraphLinks().clear();
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
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPHS:
			return subgraphs != null && !subgraphs.isEmpty();
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY__SUBGRAPH_LINKS:
			return subgraphLinks != null && !subgraphLinks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ConnectedSubgraphsNetworkTopologyImpl
