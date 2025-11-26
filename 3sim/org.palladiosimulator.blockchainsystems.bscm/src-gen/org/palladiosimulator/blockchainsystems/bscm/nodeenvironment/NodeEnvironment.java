/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment;

import org.eclipse.emf.common.util.EList;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Environment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment#getResourceContainers <em>Resource Containers</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage#getNodeEnvironment()
 * @model
 * @generated
 */
public interface NodeEnvironment extends Entity {
	/**
	 * Returns the value of the '<em><b>Resource Containers</b></em>' containment reference list.
	 * The list contents are of type {@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Containers</em>' containment reference list.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage#getNodeEnvironment_ResourceContainers()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeResourceContainer> getResourceContainers();

} // NodeEnvironment
