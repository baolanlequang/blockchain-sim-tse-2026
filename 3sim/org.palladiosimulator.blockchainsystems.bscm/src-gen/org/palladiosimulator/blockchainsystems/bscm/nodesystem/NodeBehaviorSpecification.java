/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Behavior Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getNodeBehaviorSpecification()
 * @model
 * @generated
 */
public interface NodeBehaviorSpecification extends Entity {
	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * The literals are from the enumeration {@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior
	 * @see #setBehavior(NodeBehavior)
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage#getNodeBehaviorSpecification_Behavior()
	 * @model required="true"
	 * @generated
	 */
	NodeBehavior getBehavior();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification#getBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' attribute.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(NodeBehavior value);

} // NodeBehaviorSpecification
