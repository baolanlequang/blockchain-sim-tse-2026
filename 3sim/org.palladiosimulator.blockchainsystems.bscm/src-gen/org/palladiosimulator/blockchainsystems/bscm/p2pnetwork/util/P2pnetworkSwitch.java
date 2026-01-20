/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.util;

import de.uka.ipd.sdq.identifier.Identifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.*;

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
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage
 * @generated
 */
public class P2pnetworkSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static P2pnetworkPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public P2pnetworkSwitch() {
		if (modelPackage == null) {
			modelPackage = P2pnetworkPackage.eINSTANCE;
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
		case P2pnetworkPackage.P2P_NETWORK: {
			P2PNetwork p2PNetwork = (P2PNetwork) theEObject;
			T result = caseP2PNetwork(p2PNetwork);
			if (result == null)
				result = caseEntity(p2PNetwork);
			if (result == null)
				result = caseIdentifier(p2PNetwork);
			if (result == null)
				result = caseNamedElement(p2PNetwork);
			if (result == null)
				result = casePCMBaseClass(p2PNetwork);
			if (result == null)
				result = casePCMClass(p2PNetwork);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.NETWORK_TOPOLOGY: {
			NetworkTopology networkTopology = (NetworkTopology) theEObject;
			T result = caseNetworkTopology(networkTopology);
			if (result == null)
				result = caseEntity(networkTopology);
			if (result == null)
				result = caseIdentifier(networkTopology);
			if (result == null)
				result = caseNamedElement(networkTopology);
			if (result == null)
				result = casePCMBaseClass(networkTopology);
			if (result == null)
				result = casePCMClass(networkTopology);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.EXPLICIT_NETWORK_TOPOLOGY: {
			ExplicitNetworkTopology explicitNetworkTopology = (ExplicitNetworkTopology) theEObject;
			T result = caseExplicitNetworkTopology(explicitNetworkTopology);
			if (result == null)
				result = caseNetworkTopology(explicitNetworkTopology);
			if (result == null)
				result = caseEntity(explicitNetworkTopology);
			if (result == null)
				result = caseIdentifier(explicitNetworkTopology);
			if (result == null)
				result = caseNamedElement(explicitNetworkTopology);
			if (result == null)
				result = casePCMBaseClass(explicitNetworkTopology);
			if (result == null)
				result = casePCMClass(explicitNetworkTopology);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.CONSTRAINT_NETWORK_TOPOLOGY: {
			ConstraintNetworkTopology constraintNetworkTopology = (ConstraintNetworkTopology) theEObject;
			T result = caseConstraintNetworkTopology(constraintNetworkTopology);
			if (result == null)
				result = caseNetworkTopology(constraintNetworkTopology);
			if (result == null)
				result = caseEntity(constraintNetworkTopology);
			if (result == null)
				result = caseIdentifier(constraintNetworkTopology);
			if (result == null)
				result = caseNamedElement(constraintNetworkTopology);
			if (result == null)
				result = casePCMBaseClass(constraintNetworkTopology);
			if (result == null)
				result = casePCMClass(constraintNetworkTopology);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.NODE: {
			Node node = (Node) theEObject;
			T result = caseNode(node);
			if (result == null)
				result = caseEntity(node);
			if (result == null)
				result = caseIdentifier(node);
			if (result == null)
				result = caseNamedElement(node);
			if (result == null)
				result = casePCMBaseClass(node);
			if (result == null)
				result = casePCMClass(node);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.LINK: {
			Link link = (Link) theEObject;
			T result = caseLink(link);
			if (result == null)
				result = caseEntity(link);
			if (result == null)
				result = caseIdentifier(link);
			if (result == null)
				result = caseNamedElement(link);
			if (result == null)
				result = casePCMBaseClass(link);
			if (result == null)
				result = casePCMClass(link);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.CONNECTED_SUBGRAPHS_NETWORK_TOPOLOGY: {
			ConnectedSubgraphsNetworkTopology connectedSubgraphsNetworkTopology = (ConnectedSubgraphsNetworkTopology) theEObject;
			T result = caseConnectedSubgraphsNetworkTopology(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = caseConstraintNetworkTopology(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = caseNetworkTopology(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = caseEntity(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = caseIdentifier(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = caseNamedElement(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = casePCMBaseClass(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = casePCMClass(connectedSubgraphsNetworkTopology);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.SUBGRAPH_SPECIFICATION: {
			SubgraphSpecification subgraphSpecification = (SubgraphSpecification) theEObject;
			T result = caseSubgraphSpecification(subgraphSpecification);
			if (result == null)
				result = caseEntity(subgraphSpecification);
			if (result == null)
				result = caseIdentifier(subgraphSpecification);
			if (result == null)
				result = caseNamedElement(subgraphSpecification);
			if (result == null)
				result = casePCMBaseClass(subgraphSpecification);
			if (result == null)
				result = casePCMClass(subgraphSpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION: {
			ConnectivitySpecification connectivitySpecification = (ConnectivitySpecification) theEObject;
			T result = caseConnectivitySpecification(connectivitySpecification);
			if (result == null)
				result = caseEntity(connectivitySpecification);
			if (result == null)
				result = caseIdentifier(connectivitySpecification);
			if (result == null)
				result = caseNamedElement(connectivitySpecification);
			if (result == null)
				result = casePCMBaseClass(connectivitySpecification);
			if (result == null)
				result = casePCMClass(connectivitySpecification);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.SUBGRAPH_LINK: {
			SubgraphLink subgraphLink = (SubgraphLink) theEObject;
			T result = caseSubgraphLink(subgraphLink);
			if (result == null)
				result = caseEntity(subgraphLink);
			if (result == null)
				result = caseIdentifier(subgraphLink);
			if (result == null)
				result = caseNamedElement(subgraphLink);
			if (result == null)
				result = casePCMBaseClass(subgraphLink);
			if (result == null)
				result = casePCMClass(subgraphLink);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE: {
			SubgraphNodeTemplate subgraphNodeTemplate = (SubgraphNodeTemplate) theEObject;
			T result = caseSubgraphNodeTemplate(subgraphNodeTemplate);
			if (result == null)
				result = caseEntity(subgraphNodeTemplate);
			if (result == null)
				result = caseIdentifier(subgraphNodeTemplate);
			if (result == null)
				result = caseNamedElement(subgraphNodeTemplate);
			if (result == null)
				result = casePCMBaseClass(subgraphNodeTemplate);
			if (result == null)
				result = casePCMClass(subgraphNodeTemplate);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.BIDIRECTIONAL_LINK: {
			BidirectionalLink bidirectionalLink = (BidirectionalLink) theEObject;
			T result = caseBidirectionalLink(bidirectionalLink);
			if (result == null)
				result = caseLink(bidirectionalLink);
			if (result == null)
				result = caseEntity(bidirectionalLink);
			if (result == null)
				result = caseIdentifier(bidirectionalLink);
			if (result == null)
				result = caseNamedElement(bidirectionalLink);
			if (result == null)
				result = casePCMBaseClass(bidirectionalLink);
			if (result == null)
				result = casePCMClass(bidirectionalLink);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case P2pnetworkPackage.UNIDIRECTIONAL_LINK: {
			UnidirectionalLink unidirectionalLink = (UnidirectionalLink) theEObject;
			T result = caseUnidirectionalLink(unidirectionalLink);
			if (result == null)
				result = caseLink(unidirectionalLink);
			if (result == null)
				result = caseEntity(unidirectionalLink);
			if (result == null)
				result = caseIdentifier(unidirectionalLink);
			if (result == null)
				result = caseNamedElement(unidirectionalLink);
			if (result == null)
				result = casePCMBaseClass(unidirectionalLink);
			if (result == null)
				result = casePCMClass(unidirectionalLink);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>P2P Network</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>P2P Network</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseP2PNetwork(P2PNetwork object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Network Topology</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNetworkTopology(NetworkTopology object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Explicit Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Explicit Network Topology</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExplicitNetworkTopology(ExplicitNetworkTopology object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constraint Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constraint Network Topology</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstraintNetworkTopology(ConstraintNetworkTopology object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLink(Link object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connected Subgraphs Network Topology</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connected Subgraphs Network Topology</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectedSubgraphsNetworkTopology(ConnectedSubgraphsNetworkTopology object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subgraph Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subgraph Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubgraphSpecification(SubgraphSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connectivity Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connectivity Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectivitySpecification(ConnectivitySpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subgraph Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subgraph Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubgraphLink(SubgraphLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subgraph Node Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subgraph Node Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubgraphNodeTemplate(SubgraphNodeTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bidirectional Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bidirectional Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBidirectionalLink(BidirectionalLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unidirectional Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unidirectional Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnidirectionalLink(UnidirectionalLink object) {
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

} //P2pnetworkSwitch
