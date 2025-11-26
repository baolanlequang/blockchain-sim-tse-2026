/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.util;

import de.uka.ipd.sdq.identifier.Identifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.*;

import org.palladiosimulator.pcm.PCMBaseClass;
import org.palladiosimulator.pcm.PCMClass;

import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage
 * @generated
 */
public class BlockchainsystemComponentRepositorySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BlockchainsystemComponentRepositoryPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainsystemComponentRepositorySwitch() {
		if (modelPackage == null) {
			modelPackage = BlockchainsystemComponentRepositoryPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT: {
			BlockchainSystemNodeComponent blockchainSystemNodeComponent = (BlockchainSystemNodeComponent) theEObject;
			T result = caseBlockchainSystemNodeComponent(blockchainSystemNodeComponent);
			if (result == null)
				result = caseEntity(blockchainSystemNodeComponent);
			if (result == null)
				result = caseIdentifier(blockchainSystemNodeComponent);
			if (result == null)
				result = caseNamedElement(blockchainSystemNodeComponent);
			if (result == null)
				result = casePCMBaseClass(blockchainSystemNodeComponent);
			if (result == null)
				result = casePCMClass(blockchainSystemNodeComponent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BlockchainsystemComponentRepositoryPackage.BLOCKCHAIN_SYSTEM_NODE_COMPONENT_REPOSITORY: {
			BlockchainSystemNodeComponentRepository blockchainSystemNodeComponentRepository = (BlockchainSystemNodeComponentRepository) theEObject;
			T result = caseBlockchainSystemNodeComponentRepository(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = caseEntity(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = caseIdentifier(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = caseNamedElement(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = casePCMBaseClass(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = casePCMClass(blockchainSystemNodeComponentRepository);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATOR_COMPONENT: {
			BlockValidatorComponent blockValidatorComponent = (BlockValidatorComponent) theEObject;
			T result = caseBlockValidatorComponent(blockValidatorComponent);
			if (result == null)
				result = caseBlockchainSystemNodeComponent(blockValidatorComponent);
			if (result == null)
				result = caseEntity(blockValidatorComponent);
			if (result == null)
				result = caseIdentifier(blockValidatorComponent);
			if (result == null)
				result = caseNamedElement(blockValidatorComponent);
			if (result == null)
				result = casePCMBaseClass(blockValidatorComponent);
			if (result == null)
				result = casePCMClass(blockValidatorComponent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BlockchainsystemComponentRepositoryPackage.MINING_PROCESS_COMPONENT: {
			MiningProcessComponent miningProcessComponent = (MiningProcessComponent) theEObject;
			T result = caseMiningProcessComponent(miningProcessComponent);
			if (result == null)
				result = caseBlockchainSystemNodeComponent(miningProcessComponent);
			if (result == null)
				result = caseEntity(miningProcessComponent);
			if (result == null)
				result = caseIdentifier(miningProcessComponent);
			if (result == null)
				result = caseNamedElement(miningProcessComponent);
			if (result == null)
				result = casePCMBaseClass(miningProcessComponent);
			if (result == null)
				result = casePCMClass(miningProcessComponent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIATION_DURATION_SPECIFICATION: {
			BlockValiationDurationSpecification blockValiationDurationSpecification = (BlockValiationDurationSpecification) theEObject;
			T result = caseBlockValiationDurationSpecification(blockValiationDurationSpecification);
			if (result == null)
				result = caseEntity(blockValiationDurationSpecification);
			if (result == null)
				result = caseIdentifier(blockValiationDurationSpecification);
			if (result == null)
				result = caseNamedElement(blockValiationDurationSpecification);
			if (result == null)
				result = casePCMBaseClass(blockValiationDurationSpecification);
			if (result == null)
				result = casePCMClass(blockValiationDurationSpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BlockchainsystemComponentRepositoryPackage.BLOCK_VALIDATION_DURATION_VALUE: {
			BlockValidationDurationValue blockValidationDurationValue = (BlockValidationDurationValue) theEObject;
			T result = caseBlockValidationDurationValue(blockValidationDurationValue);
			if (result == null)
				result = caseEntity(blockValidationDurationValue);
			if (result == null)
				result = caseIdentifier(blockValidationDurationValue);
			if (result == null)
				result = caseNamedElement(blockValidationDurationValue);
			if (result == null)
				result = casePCMBaseClass(blockValidationDurationValue);
			if (result == null)
				result = casePCMClass(blockValidationDurationValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Blockchain System Node Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Blockchain System Node Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockchainSystemNodeComponent(BlockchainSystemNodeComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Blockchain System Node Component Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Blockchain System Node Component Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockchainSystemNodeComponentRepository(BlockchainSystemNodeComponentRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Validator Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Validator Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockValidatorComponent(BlockValidatorComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mining Process Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mining Process Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMiningProcessComponent(MiningProcessComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Valiation Duration Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Valiation Duration Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockValiationDurationSpecification(BlockValiationDurationSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Validation Duration Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Validation Duration Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockValidationDurationValue(BlockValidationDurationValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifier(Identifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PCM Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PCM Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePCMClass(PCMClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PCM Base Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PCM Base Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePCMBaseClass(PCMBaseClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //BlockchainsystemComponentRepositorySwitch
