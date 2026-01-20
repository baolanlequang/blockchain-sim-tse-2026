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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subgraph Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl#getNodeTemplates <em>Node Templates</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl#getConnectivity <em>Connectivity</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl#getLinkAllocation <em>Link Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.impl.SubgraphSpecificationImpl#getConnectivitySpecification <em>Connectivity Specification</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubgraphSpecificationImpl extends EntityImpl implements SubgraphSpecification {
	/**
	 * The cached value of the '{@link #getNodeTemplates() <em>Node Templates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeTemplates()
	 * @generated
	 * @ordered
	 */
	protected EList<SubgraphNodeTemplate> nodeTemplates;

	/**
	 * The default value of the '{@link #getConnectivity() <em>Connectivity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectivity()
	 * @generated
	 * @ordered
	 */
	protected static final int CONNECTIVITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getConnectivity() <em>Connectivity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectivity()
	 * @generated
	 * @ordered
	 */
	protected int connectivity = CONNECTIVITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLinkAllocation() <em>Link Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkAllocation()
	 * @generated
	 * @ordered
	 */
	protected LinkAllocation linkAllocation;

	/**
	 * The cached value of the '{@link #getConnectivitySpecification() <em>Connectivity Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectivitySpecification()
	 * @generated
	 * @ordered
	 */
	protected ConnectivitySpecification connectivitySpecification;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubgraphSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return P2pnetworkPackage.Literals.SUBGRAPH_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SubgraphNodeTemplate> getNodeTemplates() {
		if (nodeTemplates == null) {
			nodeTemplates = new EObjectContainmentEList<SubgraphNodeTemplate>(SubgraphNodeTemplate.class, this,
					P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES);
		}
		return nodeTemplates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getConnectivity() {
		return connectivity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConnectivity(int newConnectivity) {
		int oldConnectivity = connectivity;
		connectivity = newConnectivity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY, oldConnectivity, connectivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation getLinkAllocation() {
		if (linkAllocation != null && linkAllocation.eIsProxy()) {
			InternalEObject oldLinkAllocation = (InternalEObject) linkAllocation;
			linkAllocation = (LinkAllocation) eResolveProxy(oldLinkAllocation);
			if (linkAllocation != oldLinkAllocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION, oldLinkAllocation,
							linkAllocation));
			}
		}
		return linkAllocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkAllocation basicGetLinkAllocation() {
		return linkAllocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLinkAllocation(LinkAllocation newLinkAllocation) {
		LinkAllocation oldLinkAllocation = linkAllocation;
		linkAllocation = newLinkAllocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION, oldLinkAllocation, linkAllocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConnectivitySpecification getConnectivitySpecification() {
		return connectivitySpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectivitySpecification(ConnectivitySpecification newConnectivitySpecification,
			NotificationChain msgs) {
		ConnectivitySpecification oldConnectivitySpecification = connectivitySpecification;
		connectivitySpecification = newConnectivitySpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION, oldConnectivitySpecification,
					newConnectivitySpecification);
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
	public void setConnectivitySpecification(ConnectivitySpecification newConnectivitySpecification) {
		if (newConnectivitySpecification != connectivitySpecification) {
			NotificationChain msgs = null;
			if (connectivitySpecification != null)
				msgs = ((InternalEObject) connectivitySpecification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION,
						null, msgs);
			if (newConnectivitySpecification != null)
				msgs = ((InternalEObject) newConnectivitySpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION,
						null, msgs);
			msgs = basicSetConnectivitySpecification(newConnectivitySpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION, newConnectivitySpecification,
					newConnectivitySpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES:
			return ((InternalEList<?>) getNodeTemplates()).basicRemove(otherEnd, msgs);
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION:
			return basicSetConnectivitySpecification(null, msgs);
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
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES:
			return getNodeTemplates();
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY:
			return getConnectivity();
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION:
			if (resolve)
				return getLinkAllocation();
			return basicGetLinkAllocation();
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION:
			return getConnectivitySpecification();
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
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES:
			getNodeTemplates().clear();
			getNodeTemplates().addAll((Collection<? extends SubgraphNodeTemplate>) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY:
			setConnectivity((Integer) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION:
			setLinkAllocation((LinkAllocation) newValue);
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION:
			setConnectivitySpecification((ConnectivitySpecification) newValue);
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
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES:
			getNodeTemplates().clear();
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY:
			setConnectivity(CONNECTIVITY_EDEFAULT);
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION:
			setLinkAllocation((LinkAllocation) null);
			return;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION:
			setConnectivitySpecification((ConnectivitySpecification) null);
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
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__NODE_TEMPLATES:
			return nodeTemplates != null && !nodeTemplates.isEmpty();
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY:
			return connectivity != CONNECTIVITY_EDEFAULT;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__LINK_ALLOCATION:
			return linkAllocation != null;
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION__CONNECTIVITY_SPECIFICATION:
			return connectivitySpecification != null;
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
		result.append(" (Connectivity: ");
		result.append(connectivity);
		result.append(')');
		return result.toString();
	}

} //SubgraphSpecificationImpl
