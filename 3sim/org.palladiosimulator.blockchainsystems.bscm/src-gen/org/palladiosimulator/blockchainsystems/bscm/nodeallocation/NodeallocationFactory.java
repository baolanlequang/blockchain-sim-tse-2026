/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeallocation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage
 * @generated
 */
public interface NodeallocationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodeallocationFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodeallocation.impl.NodeallocationFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Node Allocation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Allocation</em>'.
	 * @generated
	 */
	NodeAllocation createNodeAllocation();

	/**
	 * Returns a new object of class '<em>Node Allocation Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Allocation Repository</em>'.
	 * @generated
	 */
	NodeAllocationRepository createNodeAllocationRepository();

	/**
	 * Returns a new object of class '<em>Node Allocation Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Allocation Context</em>'.
	 * @generated
	 */
	NodeAllocationContext createNodeAllocationContext();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NodeallocationPackage getNodeallocationPackage();

} //NodeallocationFactory
