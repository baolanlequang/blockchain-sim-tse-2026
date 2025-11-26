/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentPackage
 * @generated
 */
public interface NodeenvironmentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodeenvironmentFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Node Environment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Environment</em>'.
	 * @generated
	 */
	NodeEnvironment createNodeEnvironment();

	/**
	 * Returns a new object of class '<em>Node Resource Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Resource Container</em>'.
	 * @generated
	 */
	NodeResourceContainer createNodeResourceContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NodeenvironmentPackage getNodeenvironmentPackage();

} //NodeenvironmentFactory
