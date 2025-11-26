/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subgraph Node Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getNumberOfNodeOccurences <em>Number Of Node Occurences</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#isIsSubgraphProxy <em>Is Subgraph Proxy</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphNodeTemplate()
 * @model
 * @generated
 */
public interface SubgraphNodeTemplate extends Entity {
	/**
	 * Returns the value of the '<em><b>Number Of Node Occurences</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Node Occurences</em>' attribute.
	 * @see #setNumberOfNodeOccurences(int)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphNodeTemplate_NumberOfNodeOccurences()
	 * @model required="true"
	 * @generated
	 */
	int getNumberOfNodeOccurences();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getNumberOfNodeOccurences <em>Number Of Node Occurences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Node Occurences</em>' attribute.
	 * @see #getNumberOfNodeOccurences()
	 * @generated
	 */
	void setNumberOfNodeOccurences(int value);

	/**
	 * Returns the value of the '<em><b>Allocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation</em>' reference.
	 * @see #setAllocation(NodeAllocation)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphNodeTemplate_Allocation()
	 * @model required="true"
	 * @generated
	 */
	NodeAllocation getAllocation();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#getAllocation <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allocation</em>' reference.
	 * @see #getAllocation()
	 * @generated
	 */
	void setAllocation(NodeAllocation value);

	/**
	 * Returns the value of the '<em><b>Is Subgraph Proxy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Subgraph Proxy</em>' attribute.
	 * @see #setIsSubgraphProxy(boolean)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getSubgraphNodeTemplate_IsSubgraphProxy()
	 * @model required="true"
	 * @generated
	 */
	boolean isIsSubgraphProxy();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate#isIsSubgraphProxy <em>Is Subgraph Proxy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Subgraph Proxy</em>' attribute.
	 * @see #isIsSubgraphProxy()
	 * @generated
	 */
	void setIsSubgraphProxy(boolean value);

} // SubgraphNodeTemplate
