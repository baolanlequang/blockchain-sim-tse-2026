/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Blockchain System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl#getNetwork <em>Network</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl#getTransactionsSpecification <em>Transactions Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemImpl#getGeographicalRegionsSpecification <em>Geographical Regions Specification</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockchainSystemImpl extends EntityImpl implements BlockchainSystem {
	/**
	 * The cached value of the '{@link #getNetwork() <em>Network</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNetwork()
	 * @generated
	 * @ordered
	 */
	protected P2PNetwork network;

	/**
	 * The cached value of the '{@link #getSpecification() <em>Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecification()
	 * @generated
	 * @ordered
	 */
	protected BlockchainSystemSpecification specification;

	/**
	 * The cached value of the '{@link #getTransactionsSpecification() <em>Transactions Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionsSpecification()
	 * @generated
	 * @ordered
	 */
	protected TransactionsSpecification transactionsSpecification;

	/**
	 * The cached value of the '{@link #getGeographicalRegionsSpecification() <em>Geographical Regions Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeographicalRegionsSpecification()
	 * @generated
	 * @ordered
	 */
	protected GeographicalRegionsSpecification geographicalRegionsSpecification;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockchainSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public P2PNetwork getNetwork() {
		if (network != null && network.eIsProxy()) {
			InternalEObject oldNetwork = (InternalEObject) network;
			network = (P2PNetwork) eResolveProxy(oldNetwork);
			if (network != oldNetwork) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK, oldNetwork, network));
			}
		}
		return network;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public P2PNetwork basicGetNetwork() {
		return network;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNetwork(P2PNetwork newNetwork) {
		P2PNetwork oldNetwork = network;
		network = newNetwork;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK,
					oldNetwork, network));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockchainSystemSpecification getSpecification() {
		return specification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecification(BlockchainSystemSpecification newSpecification,
			NotificationChain msgs) {
		BlockchainSystemSpecification oldSpecification = specification;
		specification = newSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION, oldSpecification, newSpecification);
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
	public void setSpecification(BlockchainSystemSpecification newSpecification) {
		if (newSpecification != specification) {
			NotificationChain msgs = null;
			if (specification != null)
				msgs = ((InternalEObject) specification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION, null, msgs);
			if (newSpecification != null)
				msgs = ((InternalEObject) newSpecification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION, null, msgs);
			msgs = basicSetSpecification(newSpecification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION, newSpecification, newSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransactionsSpecification getTransactionsSpecification() {
		if (transactionsSpecification != null && transactionsSpecification.eIsProxy()) {
			InternalEObject oldTransactionsSpecification = (InternalEObject) transactionsSpecification;
			transactionsSpecification = (TransactionsSpecification) eResolveProxy(oldTransactionsSpecification);
			if (transactionsSpecification != oldTransactionsSpecification) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION,
							oldTransactionsSpecification, transactionsSpecification));
			}
		}
		return transactionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransactionsSpecification basicGetTransactionsSpecification() {
		return transactionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransactionsSpecification(TransactionsSpecification newTransactionsSpecification) {
		TransactionsSpecification oldTransactionsSpecification = transactionsSpecification;
		transactionsSpecification = newTransactionsSpecification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION, oldTransactionsSpecification,
					transactionsSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeographicalRegionsSpecification getGeographicalRegionsSpecification() {
		if (geographicalRegionsSpecification != null && geographicalRegionsSpecification.eIsProxy()) {
			InternalEObject oldGeographicalRegionsSpecification = (InternalEObject) geographicalRegionsSpecification;
			geographicalRegionsSpecification = (GeographicalRegionsSpecification) eResolveProxy(
					oldGeographicalRegionsSpecification);
			if (geographicalRegionsSpecification != oldGeographicalRegionsSpecification) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION,
							oldGeographicalRegionsSpecification, geographicalRegionsSpecification));
			}
		}
		return geographicalRegionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeographicalRegionsSpecification basicGetGeographicalRegionsSpecification() {
		return geographicalRegionsSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGeographicalRegionsSpecification(
			GeographicalRegionsSpecification newGeographicalRegionsSpecification) {
		GeographicalRegionsSpecification oldGeographicalRegionsSpecification = geographicalRegionsSpecification;
		geographicalRegionsSpecification = newGeographicalRegionsSpecification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION,
					oldGeographicalRegionsSpecification, geographicalRegionsSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION:
			return basicSetSpecification(null, msgs);
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK:
			if (resolve)
				return getNetwork();
			return basicGetNetwork();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION:
			return getSpecification();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION:
			if (resolve)
				return getTransactionsSpecification();
			return basicGetTransactionsSpecification();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION:
			if (resolve)
				return getGeographicalRegionsSpecification();
			return basicGetGeographicalRegionsSpecification();
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK:
			setNetwork((P2PNetwork) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION:
			setSpecification((BlockchainSystemSpecification) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION:
			setTransactionsSpecification((TransactionsSpecification) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION:
			setGeographicalRegionsSpecification((GeographicalRegionsSpecification) newValue);
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK:
			setNetwork((P2PNetwork) null);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION:
			setSpecification((BlockchainSystemSpecification) null);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION:
			setTransactionsSpecification((TransactionsSpecification) null);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION:
			setGeographicalRegionsSpecification((GeographicalRegionsSpecification) null);
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__NETWORK:
			return network != null;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__SPECIFICATION:
			return specification != null;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__TRANSACTIONS_SPECIFICATION:
			return transactionsSpecification != null;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM__GEOGRAPHICAL_REGIONS_SPECIFICATION:
			return geographicalRegionsSpecification != null;
		}
		return super.eIsSet(featureID);
	}

} //BlockchainSystemImpl
