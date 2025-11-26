/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unidirectional Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getFromNode <em>From Node</em>}</li>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getToNode <em>To Node</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getUnidirectionalLink()
 * @model
 * @generated
 */
public interface UnidirectionalLink extends Link {
	/**
	 * Returns the value of the '<em><b>From Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Node</em>' reference.
	 * @see #setFromNode(Node)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getUnidirectionalLink_FromNode()
	 * @model required="true"
	 * @generated
	 */
	Node getFromNode();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getFromNode <em>From Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Node</em>' reference.
	 * @see #getFromNode()
	 * @generated
	 */
	void setFromNode(Node value);

	/**
	 * Returns the value of the '<em><b>To Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Node</em>' reference.
	 * @see #setToNode(Node)
	 * @see org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage#getUnidirectionalLink_ToNode()
	 * @model required="true"
	 * @generated
	 */
	Node getToNode();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.UnidirectionalLink#getToNode <em>To Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Node</em>' reference.
	 * @see #getToNode()
	 * @generated
	 */
	void setToNode(Node value);

} // UnidirectionalLink
