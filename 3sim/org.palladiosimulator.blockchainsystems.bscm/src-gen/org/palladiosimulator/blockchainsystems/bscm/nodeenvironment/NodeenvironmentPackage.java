/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodeenvironment;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeenvironmentFactory
 * @model kind="package"
 * @generated
 */
public interface NodeenvironmentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nodeenvironment";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/NodeEnvironment/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nodeenvironment";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodeenvironmentPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeEnvironmentImpl <em>Node Environment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeEnvironmentImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl#getNodeEnvironment()
	 * @generated
	 */
	int NODE_ENVIRONMENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ENVIRONMENT__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ENVIRONMENT__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Resource Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ENVIRONMENT__RESOURCE_CONTAINERS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Environment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_ENVIRONMENT_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeResourceContainerImpl <em>Node Resource Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeResourceContainerImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl#getNodeResourceContainer()
	 * @generated
	 */
	int NODE_RESOURCE_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RESOURCE_CONTAINER__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RESOURCE_CONTAINER__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Resource Power</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RESOURCE_CONTAINER__RESOURCE_POWER = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Resource Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RESOURCE_CONTAINER_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment <em>Node Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Environment</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment
	 * @generated
	 */
	EClass getNodeEnvironment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment#getResourceContainers <em>Resource Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource Containers</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeEnvironment#getResourceContainers()
	 * @see #getNodeEnvironment()
	 * @generated
	 */
	EReference getNodeEnvironment_ResourceContainers();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer <em>Node Resource Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Resource Container</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer
	 * @generated
	 */
	EClass getNodeResourceContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer#getResourcePower <em>Resource Power</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Power</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.NodeResourceContainer#getResourcePower()
	 * @see #getNodeResourceContainer()
	 * @generated
	 */
	EAttribute getNodeResourceContainer_ResourcePower();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NodeenvironmentFactory getNodeenvironmentFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeEnvironmentImpl <em>Node Environment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeEnvironmentImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl#getNodeEnvironment()
		 * @generated
		 */
		EClass NODE_ENVIRONMENT = eINSTANCE.getNodeEnvironment();

		/**
		 * The meta object literal for the '<em><b>Resource Containers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_ENVIRONMENT__RESOURCE_CONTAINERS = eINSTANCE.getNodeEnvironment_ResourceContainers();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeResourceContainerImpl <em>Node Resource Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeResourceContainerImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodeenvironment.impl.NodeenvironmentPackageImpl#getNodeResourceContainer()
		 * @generated
		 */
		EClass NODE_RESOURCE_CONTAINER = eINSTANCE.getNodeResourceContainer();

		/**
		 * The meta object literal for the '<em><b>Resource Power</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_RESOURCE_CONTAINER__RESOURCE_POWER = eINSTANCE.getNodeResourceContainer_ResourcePower();

	}

} //NodeenvironmentPackage
