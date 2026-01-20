/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subgraph Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getNodeTemplates <em>Node Templates</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivity <em>Connectivity</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getLinkAllocation <em>Link Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivitySpecification <em>Connectivity Specification</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphSpecification()
 * @model
 * @generated
 */
public interface SubgraphSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Node Templates</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Templates</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphSpecification_NodeTemplates()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<SubgraphNodeTemplate> getNodeTemplates();

	/**
	 * Returns the value of the '<em><b>Connectivity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connectivity</em>' attribute.
	 * @see #setConnectivity(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphSpecification_Connectivity()
	 * @model required="true"
	 * @generated
	 */
	int getConnectivity();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivity <em>Connectivity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connectivity</em>' attribute.
	 * @see #getConnectivity()
	 * @generated
	 */
	void setConnectivity(int value);

	/**
	 * Returns the value of the '<em><b>Link Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Allocation</em>' reference.
	 * @see #setLinkAllocation(LinkAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphSpecification_LinkAllocation()
	 * @model required="true"
	 * @generated
	 */
	LinkAllocation getLinkAllocation();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getLinkAllocation <em>Link Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Allocation</em>' reference.
	 * @see #getLinkAllocation()
	 * @generated
	 */
	void setLinkAllocation(LinkAllocation value);

	/**
	 * Returns the value of the '<em><b>Connectivity Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connectivity Specification</em>' containment reference.
	 * @see #setConnectivitySpecification(ConnectivitySpecification)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphSpecification_ConnectivitySpecification()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ConnectivitySpecification getConnectivitySpecification();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphSpecification#getConnectivitySpecification <em>Connectivity Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connectivity Specification</em>' containment reference.
	 * @see #getConnectivitySpecification()
	 * @generated
	 */
	void setConnectivitySpecification(ConnectivitySpecification value);

} // SubgraphSpecification
