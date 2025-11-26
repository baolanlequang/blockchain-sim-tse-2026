/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem;

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2PNetwork;

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsSpecification;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Blockchain System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getNetwork <em>Network</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getTransactionsSpecification <em>Transactions Specification</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getGeographicalRegionsSpecification <em>Geographical Regions Specification</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystem()
 * @model
 * @generated
 */
public interface BlockchainSystem extends Entity {
	/**
	 * Returns the value of the '<em><b>Network</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Network</em>' reference.
	 * @see #setNetwork(P2PNetwork)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystem_Network()
	 * @model required="true"
	 * @generated
	 */
	P2PNetwork getNetwork();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getNetwork <em>Network</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Network</em>' reference.
	 * @see #getNetwork()
	 * @generated
	 */
	void setNetwork(P2PNetwork value);

	/**
	 * Returns the value of the '<em><b>Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specification</em>' containment reference.
	 * @see #setSpecification(BlockchainSystemSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystem_Specification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BlockchainSystemSpecification getSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getSpecification <em>Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specification</em>' containment reference.
	 * @see #getSpecification()
	 * @generated
	 */
	void setSpecification(BlockchainSystemSpecification value);

	/**
	 * Returns the value of the '<em><b>Transactions Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transactions Specification</em>' reference.
	 * @see #setTransactionsSpecification(TransactionsSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystem_TransactionsSpecification()
	 * @model required="true"
	 * @generated
	 */
	TransactionsSpecification getTransactionsSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getTransactionsSpecification <em>Transactions Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transactions Specification</em>' reference.
	 * @see #getTransactionsSpecification()
	 * @generated
	 */
	void setTransactionsSpecification(TransactionsSpecification value);

	/**
	 * Returns the value of the '<em><b>Geographical Regions Specification</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Geographical Regions Specification</em>' reference.
	 * @see #setGeographicalRegionsSpecification(GeographicalRegionsSpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystem_GeographicalRegionsSpecification()
	 * @model required="true"
	 * @generated
	 */
	GeographicalRegionsSpecification getGeographicalRegionsSpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem#getGeographicalRegionsSpecification <em>Geographical Regions Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Geographical Regions Specification</em>' reference.
	 * @see #getGeographicalRegionsSpecification()
	 * @generated
	 */
	void setGeographicalRegionsSpecification(GeographicalRegionsSpecification value);

} // BlockchainSystem
