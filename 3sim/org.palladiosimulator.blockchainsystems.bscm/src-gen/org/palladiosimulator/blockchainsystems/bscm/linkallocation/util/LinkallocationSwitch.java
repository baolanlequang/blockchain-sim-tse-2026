/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.util;

import de.uka.ipd.sdq.identifier.Identifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.*;

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
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage
 * @generated
 */
public class LinkallocationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LinkallocationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkallocationSwitch() {
		if (modelPackage == null) {
			modelPackage = LinkallocationPackage.eINSTANCE;
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
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY: {
			LinkAllocationRepository linkAllocationRepository = (LinkAllocationRepository) theEObject;
			T result = caseLinkAllocationRepository(linkAllocationRepository);
			if (result == null)
				result = caseEntity(linkAllocationRepository);
			if (result == null)
				result = caseIdentifier(linkAllocationRepository);
			if (result == null)
				result = caseNamedElement(linkAllocationRepository);
			if (result == null)
				result = casePCMBaseClass(linkAllocationRepository);
			if (result == null)
				result = casePCMClass(linkAllocationRepository);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.LINK_ALLOCATION: {
			LinkAllocation linkAllocation = (LinkAllocation) theEObject;
			T result = caseLinkAllocation(linkAllocation);
			if (result == null)
				result = caseEntity(linkAllocation);
			if (result == null)
				result = caseIdentifier(linkAllocation);
			if (result == null)
				result = caseNamedElement(linkAllocation);
			if (result == null)
				result = casePCMBaseClass(linkAllocation);
			if (result == null)
				result = casePCMClass(linkAllocation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.LINK_LATENCY_SPECIFICATION: {
			LinkLatencySpecification linkLatencySpecification = (LinkLatencySpecification) theEObject;
			T result = caseLinkLatencySpecification(linkLatencySpecification);
			if (result == null)
				result = caseEntity(linkLatencySpecification);
			if (result == null)
				result = caseIdentifier(linkLatencySpecification);
			if (result == null)
				result = caseNamedElement(linkLatencySpecification);
			if (result == null)
				result = casePCMBaseClass(linkLatencySpecification);
			if (result == null)
				result = casePCMClass(linkLatencySpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE: {
			DynamicLinkLatencySpecificationValue dynamicLinkLatencySpecificationValue = (DynamicLinkLatencySpecificationValue) theEObject;
			T result = caseDynamicLinkLatencySpecificationValue(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = caseEntity(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = caseIdentifier(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = caseNamedElement(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = casePCMBaseClass(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = casePCMClass(dynamicLinkLatencySpecificationValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.LINK_THROUGHPUT_SPECIFICATION: {
			LinkThroughputSpecification linkThroughputSpecification = (LinkThroughputSpecification) theEObject;
			T result = caseLinkThroughputSpecification(linkThroughputSpecification);
			if (result == null)
				result = caseEntity(linkThroughputSpecification);
			if (result == null)
				result = caseIdentifier(linkThroughputSpecification);
			if (result == null)
				result = caseNamedElement(linkThroughputSpecification);
			if (result == null)
				result = casePCMBaseClass(linkThroughputSpecification);
			if (result == null)
				result = casePCMClass(linkThroughputSpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE: {
			DynamicLinkThroughputSpecificationValue dynamicLinkThroughputSpecificationValue = (DynamicLinkThroughputSpecificationValue) theEObject;
			T result = caseDynamicLinkThroughputSpecificationValue(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = caseEntity(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = caseIdentifier(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = caseNamedElement(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = casePCMBaseClass(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = casePCMClass(dynamicLinkThroughputSpecificationValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION: {
			DynamicLinkLatencySpecification dynamicLinkLatencySpecification = (DynamicLinkLatencySpecification) theEObject;
			T result = caseDynamicLinkLatencySpecification(dynamicLinkLatencySpecification);
			if (result == null)
				result = caseLinkLatencySpecification(dynamicLinkLatencySpecification);
			if (result == null)
				result = caseEntity(dynamicLinkLatencySpecification);
			if (result == null)
				result = caseIdentifier(dynamicLinkLatencySpecification);
			if (result == null)
				result = caseNamedElement(dynamicLinkLatencySpecification);
			if (result == null)
				result = casePCMBaseClass(dynamicLinkLatencySpecification);
			if (result == null)
				result = casePCMClass(dynamicLinkLatencySpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION: {
			DynamicLinkThroughputSpecification dynamicLinkThroughputSpecification = (DynamicLinkThroughputSpecification) theEObject;
			T result = caseDynamicLinkThroughputSpecification(dynamicLinkThroughputSpecification);
			if (result == null)
				result = caseLinkThroughputSpecification(dynamicLinkThroughputSpecification);
			if (result == null)
				result = caseEntity(dynamicLinkThroughputSpecification);
			if (result == null)
				result = caseIdentifier(dynamicLinkThroughputSpecification);
			if (result == null)
				result = caseNamedElement(dynamicLinkThroughputSpecification);
			if (result == null)
				result = casePCMBaseClass(dynamicLinkThroughputSpecification);
			if (result == null)
				result = casePCMClass(dynamicLinkThroughputSpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.STATIC_LINK_LATENCY_SPECIFICATION: {
			StaticLinkLatencySpecification staticLinkLatencySpecification = (StaticLinkLatencySpecification) theEObject;
			T result = caseStaticLinkLatencySpecification(staticLinkLatencySpecification);
			if (result == null)
				result = caseLinkLatencySpecification(staticLinkLatencySpecification);
			if (result == null)
				result = caseEntity(staticLinkLatencySpecification);
			if (result == null)
				result = caseIdentifier(staticLinkLatencySpecification);
			if (result == null)
				result = caseNamedElement(staticLinkLatencySpecification);
			if (result == null)
				result = casePCMBaseClass(staticLinkLatencySpecification);
			if (result == null)
				result = casePCMClass(staticLinkLatencySpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LinkallocationPackage.STATIC_LINK_THROUGHPUT_SPECIFICATION: {
			StaticLinkThroughputSpecification staticLinkThroughputSpecification = (StaticLinkThroughputSpecification) theEObject;
			T result = caseStaticLinkThroughputSpecification(staticLinkThroughputSpecification);
			if (result == null)
				result = caseLinkThroughputSpecification(staticLinkThroughputSpecification);
			if (result == null)
				result = caseEntity(staticLinkThroughputSpecification);
			if (result == null)
				result = caseIdentifier(staticLinkThroughputSpecification);
			if (result == null)
				result = caseNamedElement(staticLinkThroughputSpecification);
			if (result == null)
				result = casePCMBaseClass(staticLinkThroughputSpecification);
			if (result == null)
				result = casePCMClass(staticLinkThroughputSpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link Allocation Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link Allocation Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkAllocationRepository(LinkAllocationRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link Allocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link Allocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkAllocation(LinkAllocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link Latency Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link Latency Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkLatencySpecification(LinkLatencySpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dynamic Link Latency Specification Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dynamic Link Latency Specification Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDynamicLinkLatencySpecificationValue(DynamicLinkLatencySpecificationValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link Throughput Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link Throughput Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLinkThroughputSpecification(LinkThroughputSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dynamic Link Throughput Specification Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dynamic Link Throughput Specification Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDynamicLinkThroughputSpecificationValue(DynamicLinkThroughputSpecificationValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dynamic Link Latency Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dynamic Link Latency Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDynamicLinkLatencySpecification(DynamicLinkLatencySpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dynamic Link Throughput Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dynamic Link Throughput Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDynamicLinkThroughputSpecification(DynamicLinkThroughputSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Static Link Latency Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Static Link Latency Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStaticLinkLatencySpecification(StaticLinkLatencySpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Static Link Throughput Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Static Link Throughput Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStaticLinkThroughputSpecification(StaticLinkThroughputSpecification object) {
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

} //LinkallocationSwitch
