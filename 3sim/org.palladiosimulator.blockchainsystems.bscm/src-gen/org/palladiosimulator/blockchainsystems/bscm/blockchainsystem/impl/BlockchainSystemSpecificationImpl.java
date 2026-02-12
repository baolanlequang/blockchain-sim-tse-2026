/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Blockchain System Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getMeanBlockTime <em>Mean Block Time</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getNumOfRequiredSecurityConfirmations <em>Num Of Required Security Confirmations</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getMaxBlockSize <em>Max Block Size</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getBlockReward <em>Block Reward</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getHashRateConcentration <em>Hash Rate Concentration</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.impl.BlockchainSystemSpecificationImpl#getNumberOfAttacker <em>Number Of Attacker</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockchainSystemSpecificationImpl extends EntityImpl implements BlockchainSystemSpecification {
	/**
	 * The default value of the '{@link #getMeanBlockTime() <em>Mean Block Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanBlockTime()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_BLOCK_TIME_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMeanBlockTime() <em>Mean Block Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanBlockTime()
	 * @generated
	 * @ordered
	 */
	protected double meanBlockTime = MEAN_BLOCK_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumOfRequiredSecurityConfirmations() <em>Num Of Required Security Confirmations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumOfRequiredSecurityConfirmations()
	 * @generated
	 * @ordered
	 */
	protected static final int NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumOfRequiredSecurityConfirmations() <em>Num Of Required Security Confirmations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumOfRequiredSecurityConfirmations()
	 * @generated
	 * @ordered
	 */
	protected int numOfRequiredSecurityConfirmations = NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxBlockSize() <em>Max Block Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxBlockSize()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_BLOCK_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxBlockSize() <em>Max Block Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxBlockSize()
	 * @generated
	 * @ordered
	 */
	protected int maxBlockSize = MAX_BLOCK_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBlockReward() <em>Block Reward</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockReward()
	 * @generated
	 * @ordered
	 */
	protected static final double BLOCK_REWARD_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBlockReward() <em>Block Reward</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockReward()
	 * @generated
	 * @ordered
	 */
	protected double blockReward = BLOCK_REWARD_EDEFAULT;

	/**
	 * The default value of the '{@link #getHashRateConcentration() <em>Hash Rate Concentration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashRateConcentration()
	 * @generated
	 * @ordered
	 */
	protected static final double HASH_RATE_CONCENTRATION_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHashRateConcentration() <em>Hash Rate Concentration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashRateConcentration()
	 * @generated
	 * @ordered
	 */
	protected double hashRateConcentration = HASH_RATE_CONCENTRATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfAttacker() <em>Number Of Attacker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAttacker()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_ATTACKER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfAttacker() <em>Number Of Attacker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfAttacker()
	 * @generated
	 * @ordered
	 */
	protected int numberOfAttacker = NUMBER_OF_ATTACKER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockchainSystemSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMeanBlockTime() {
		return meanBlockTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMeanBlockTime(double newMeanBlockTime) {
		double oldMeanBlockTime = meanBlockTime;
		meanBlockTime = newMeanBlockTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME, oldMeanBlockTime,
					meanBlockTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumOfRequiredSecurityConfirmations() {
		return numOfRequiredSecurityConfirmations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumOfRequiredSecurityConfirmations(int newNumOfRequiredSecurityConfirmations) {
		int oldNumOfRequiredSecurityConfirmations = numOfRequiredSecurityConfirmations;
		numOfRequiredSecurityConfirmations = newNumOfRequiredSecurityConfirmations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS,
					oldNumOfRequiredSecurityConfirmations, numOfRequiredSecurityConfirmations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMaxBlockSize() {
		return maxBlockSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxBlockSize(int newMaxBlockSize) {
		int oldMaxBlockSize = maxBlockSize;
		maxBlockSize = newMaxBlockSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE, oldMaxBlockSize,
					maxBlockSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getBlockReward() {
		return blockReward;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBlockReward(double newBlockReward) {
		double oldBlockReward = blockReward;
		blockReward = newBlockReward;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD, oldBlockReward,
					blockReward));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getHashRateConcentration() {
		return hashRateConcentration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHashRateConcentration(double newHashRateConcentration) {
		double oldHashRateConcentration = hashRateConcentration;
		hashRateConcentration = newHashRateConcentration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION,
					oldHashRateConcentration, hashRateConcentration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumberOfAttacker() {
		return numberOfAttacker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfAttacker(int newNumberOfAttacker) {
		int oldNumberOfAttacker = numberOfAttacker;
		numberOfAttacker = newNumberOfAttacker;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER, oldNumberOfAttacker,
					numberOfAttacker));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME:
			return getMeanBlockTime();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS:
			return getNumOfRequiredSecurityConfirmations();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE:
			return getMaxBlockSize();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD:
			return getBlockReward();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION:
			return getHashRateConcentration();
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER:
			return getNumberOfAttacker();
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME:
			setMeanBlockTime((Double) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS:
			setNumOfRequiredSecurityConfirmations((Integer) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE:
			setMaxBlockSize((Integer) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD:
			setBlockReward((Double) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION:
			setHashRateConcentration((Double) newValue);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER:
			setNumberOfAttacker((Integer) newValue);
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME:
			setMeanBlockTime(MEAN_BLOCK_TIME_EDEFAULT);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS:
			setNumOfRequiredSecurityConfirmations(NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS_EDEFAULT);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE:
			setMaxBlockSize(MAX_BLOCK_SIZE_EDEFAULT);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD:
			setBlockReward(BLOCK_REWARD_EDEFAULT);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION:
			setHashRateConcentration(HASH_RATE_CONCENTRATION_EDEFAULT);
			return;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER:
			setNumberOfAttacker(NUMBER_OF_ATTACKER_EDEFAULT);
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
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME:
			return meanBlockTime != MEAN_BLOCK_TIME_EDEFAULT;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS:
			return numOfRequiredSecurityConfirmations != NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS_EDEFAULT;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE:
			return maxBlockSize != MAX_BLOCK_SIZE_EDEFAULT;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD:
			return blockReward != BLOCK_REWARD_EDEFAULT;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__HASH_RATE_CONCENTRATION:
			return hashRateConcentration != HASH_RATE_CONCENTRATION_EDEFAULT;
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUMBER_OF_ATTACKER:
			return numberOfAttacker != NUMBER_OF_ATTACKER_EDEFAULT;
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
		result.append(" (MeanBlockTime: ");
		result.append(meanBlockTime);
		result.append(", NumOfRequiredSecurityConfirmations: ");
		result.append(numOfRequiredSecurityConfirmations);
		result.append(", MaxBlockSize: ");
		result.append(maxBlockSize);
		result.append(", BlockReward: ");
		result.append(blockReward);
		result.append(", HashRateConcentration: ");
		result.append(hashRateConcentration);
		result.append(", NumberOfAttacker: ");
		result.append(numberOfAttacker);
		result.append(')');
		return result.toString();
	}

} //BlockchainSystemSpecificationImpl
