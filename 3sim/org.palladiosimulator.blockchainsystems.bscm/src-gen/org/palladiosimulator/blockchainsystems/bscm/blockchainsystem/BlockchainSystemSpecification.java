/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Blockchain System Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMeanBlockTime <em>Mean Block Time</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumOfRequiredSecurityConfirmations <em>Num Of Required Security Confirmations</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMaxBlockSize <em>Max Block Size</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getBlockReward <em>Block Reward</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getHashRateConcentration <em>Hash Rate Concentration</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumberOfAttacker <em>Number Of Attacker</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification()
 * @model
 * @generated
 */
public interface BlockchainSystemSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Mean Block Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: ms
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mean Block Time</em>' attribute.
	 * @see #setMeanBlockTime(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_MeanBlockTime()
	 * @model required="true"
	 * @generated
	 */
	double getMeanBlockTime();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMeanBlockTime <em>Mean Block Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean Block Time</em>' attribute.
	 * @see #getMeanBlockTime()
	 * @generated
	 */
	void setMeanBlockTime(double value);

	/**
	 * Returns the value of the '<em><b>Num Of Required Security Confirmations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num Of Required Security Confirmations</em>' attribute.
	 * @see #setNumOfRequiredSecurityConfirmations(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_NumOfRequiredSecurityConfirmations()
	 * @model required="true"
	 * @generated
	 */
	int getNumOfRequiredSecurityConfirmations();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumOfRequiredSecurityConfirmations <em>Num Of Required Security Confirmations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num Of Required Security Confirmations</em>' attribute.
	 * @see #getNumOfRequiredSecurityConfirmations()
	 * @generated
	 */
	void setNumOfRequiredSecurityConfirmations(int value);

	/**
	 * Returns the value of the '<em><b>Max Block Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit: Byte
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Block Size</em>' attribute.
	 * @see #setMaxBlockSize(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_MaxBlockSize()
	 * @model required="true"
	 * @generated
	 */
	int getMaxBlockSize();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getMaxBlockSize <em>Max Block Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Block Size</em>' attribute.
	 * @see #getMaxBlockSize()
	 * @generated
	 */
	void setMaxBlockSize(int value);

	/**
	 * Returns the value of the '<em><b>Block Reward</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Block Reward</em>' attribute.
	 * @see #setBlockReward(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_BlockReward()
	 * @model
	 * @generated
	 */
	double getBlockReward();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getBlockReward <em>Block Reward</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Block Reward</em>' attribute.
	 * @see #getBlockReward()
	 * @generated
	 */
	void setBlockReward(double value);

	/**
	 * Returns the value of the '<em><b>Hash Rate Concentration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hash Rate Concentration</em>' attribute.
	 * @see #setHashRateConcentration(double)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_HashRateConcentration()
	 * @model required="true"
	 * @generated
	 */
	double getHashRateConcentration();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getHashRateConcentration <em>Hash Rate Concentration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash Rate Concentration</em>' attribute.
	 * @see #getHashRateConcentration()
	 * @generated
	 */
	void setHashRateConcentration(double value);

	/**
	 * Returns the value of the '<em><b>Number Of Attacker</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Attacker</em>' attribute.
	 * @see #setNumberOfAttacker(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage#getBlockchainSystemSpecification_NumberOfAttacker()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getNumberOfAttacker();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification#getNumberOfAttacker <em>Number Of Attacker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Attacker</em>' attribute.
	 * @see #getNumberOfAttacker()
	 * @generated
	 */
	void setNumberOfAttacker(int value);

} // BlockchainSystemSpecification
