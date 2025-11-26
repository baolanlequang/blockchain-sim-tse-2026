/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegion;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocationContext;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Allocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl#getAllocationContexts <em>Allocation Contexts</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl#getNodeAllocationEnvironment <em>Node Allocation Environment</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl#getNodeSystem <em>Node System</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeAllocationImpl#getGeographicalRegion <em>Geographical Region</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeAllocationImpl extends EntityImpl implements NodeAllocation {
	/**
	 * The cached value of the '{@link #getAllocationContexts() <em>Allocation Contexts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocationContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeAllocationContext> allocationContexts;

	/**
	 * The cached value of the '{@link #getNodeAllocationEnvironment() <em>Node Allocation Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeAllocationEnvironment()
	 * @generated
	 * @ordered
	 */
	protected NodeEnvironment nodeAllocationEnvironment;

	/**
	 * The cached value of the '{@link #getNodeSystem() <em>Node System</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeSystem()
	 * @generated
	 * @ordered
	 */
	protected BlockchainSystemNodeSystem nodeSystem;

	/**
	 * The cached value of the '{@link #getGeographicalRegion() <em>Geographical Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeographicalRegion()
	 * @generated
	 * @ordered
	 */
	protected GeographicalRegion geographicalRegion;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeAllocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodeallocationPackage.Literals.NODE_ALLOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NodeAllocationContext> getAllocationContexts() {
		if (allocationContexts == null) {
			allocationContexts = new EObjectContainmentEList<NodeAllocationContext>(NodeAllocationContext.class, this,
					NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS);
		}
		return allocationContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NodeEnvironment getNodeAllocationEnvironment() {
		return nodeAllocationEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNodeAllocationEnvironment(NodeEnvironment newNodeAllocationEnvironment,
			NotificationChain msgs) {
		NodeEnvironment oldNodeAllocationEnvironment = nodeAllocationEnvironment;
		nodeAllocationEnvironment = newNodeAllocationEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT, oldNodeAllocationEnvironment,
					newNodeAllocationEnvironment);
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
	public void setNodeAllocationEnvironment(NodeEnvironment newNodeAllocationEnvironment) {
		if (newNodeAllocationEnvironment != nodeAllocationEnvironment) {
			NotificationChain msgs = null;
			if (nodeAllocationEnvironment != null)
				msgs = ((InternalEObject) nodeAllocationEnvironment).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT,
						null, msgs);
			if (newNodeAllocationEnvironment != null)
				msgs = ((InternalEObject) newNodeAllocationEnvironment).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT,
						null, msgs);
			msgs = basicSetNodeAllocationEnvironment(newNodeAllocationEnvironment, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT, newNodeAllocationEnvironment,
					newNodeAllocationEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemNodeSystem getNodeSystem() {
		return nodeSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNodeSystem(BlockchainSystemNodeSystem newNodeSystem, NotificationChain msgs) {
		BlockchainSystemNodeSystem oldNodeSystem = nodeSystem;
		nodeSystem = newNodeSystem;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM, oldNodeSystem, newNodeSystem);
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
	public void setNodeSystem(BlockchainSystemNodeSystem newNodeSystem) {
		if (newNodeSystem != nodeSystem) {
			NotificationChain msgs = null;
			if (nodeSystem != null)
				msgs = ((InternalEObject) nodeSystem).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM, null, msgs);
			if (newNodeSystem != null)
				msgs = ((InternalEObject) newNodeSystem).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM, null, msgs);
			msgs = basicSetNodeSystem(newNodeSystem, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM,
					newNodeSystem, newNodeSystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalRegion getGeographicalRegion() {
		if (geographicalRegion != null && geographicalRegion.eIsProxy()) {
			InternalEObject oldGeographicalRegion = (InternalEObject) geographicalRegion;
			geographicalRegion = (GeographicalRegion) eResolveProxy(oldGeographicalRegion);
			if (geographicalRegion != oldGeographicalRegion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION, oldGeographicalRegion,
							geographicalRegion));
			}
		}
		return geographicalRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeographicalRegion basicGetGeographicalRegion() {
		return geographicalRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGeographicalRegion(GeographicalRegion newGeographicalRegion) {
		GeographicalRegion oldGeographicalRegion = geographicalRegion;
		geographicalRegion = newGeographicalRegion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION, oldGeographicalRegion,
					geographicalRegion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS:
			return ((InternalEList<?>) getAllocationContexts()).basicRemove(otherEnd, msgs);
		case NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT:
			return basicSetNodeAllocationEnvironment(null, msgs);
		case NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM:
			return basicSetNodeSystem(null, msgs);
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
		case NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS:
			return getAllocationContexts();
		case NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT:
			return getNodeAllocationEnvironment();
		case NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM:
			return getNodeSystem();
		case NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION:
			if (resolve)
				return getGeographicalRegion();
			return basicGetGeographicalRegion();
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
		case NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS:
			getAllocationContexts().clear();
			getAllocationContexts().addAll((Collection<? extends NodeAllocationContext>) newValue);
			return;
		case NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT:
			setNodeAllocationEnvironment((NodeEnvironment) newValue);
			return;
		case NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM:
			setNodeSystem((BlockchainSystemNodeSystem) newValue);
			return;
		case NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION:
			setGeographicalRegion((GeographicalRegion) newValue);
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
		case NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS:
			getAllocationContexts().clear();
			return;
		case NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT:
			setNodeAllocationEnvironment((NodeEnvironment) null);
			return;
		case NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM:
			setNodeSystem((BlockchainSystemNodeSystem) null);
			return;
		case NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION:
			setGeographicalRegion((GeographicalRegion) null);
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
		case NodeallocationPackage.NODE_ALLOCATION__ALLOCATION_CONTEXTS:
			return allocationContexts != null && !allocationContexts.isEmpty();
		case NodeallocationPackage.NODE_ALLOCATION__NODE_ALLOCATION_ENVIRONMENT:
			return nodeAllocationEnvironment != null;
		case NodeallocationPackage.NODE_ALLOCATION__NODE_SYSTEM:
			return nodeSystem != null;
		case NodeallocationPackage.NODE_ALLOCATION__GEOGRAPHICAL_REGION:
			return geographicalRegion != null;
		}
		return super.eIsSet(featureID);
	}

} //NodeAllocationImpl
