/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemFactory
 * @model kind="package"
 * @generated
 */
public interface NodesystemPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nodesystem";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/NodeSystem/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nodesystem";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodesystemPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl <em>Blockchain System Node System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getBlockchainSystemNodeSystem()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Mining Process Assembly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Block Validator Assembly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Assembly Contexts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Blockchain System Node System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_SYSTEM_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeAssemblyContextImpl <em>Blockchain System Node Assembly Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeAssemblyContextImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getBlockchainSystemNodeAssemblyContext()
	 * @generated
	 */
	int BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Encapsulated Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Blockchain System Node Assembly Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodeBehaviorSpecificationImpl <em>Node Behavior Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodeBehaviorSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getNodeBehaviorSpecification()
	 * @generated
	 */
	int NODE_BEHAVIOR_SPECIFICATION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BEHAVIOR_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BEHAVIOR_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BEHAVIOR_SPECIFICATION__BEHAVIOR = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Behavior Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BEHAVIOR_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior <em>Node Behavior</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getNodeBehavior()
	 * @generated
	 */
	int NODE_BEHAVIOR = 3;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem <em>Blockchain System Node System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System Node System</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem
	 * @generated
	 */
	EClass getBlockchainSystemNodeSystem();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getMiningProcessAssembly <em>Mining Process Assembly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mining Process Assembly</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getMiningProcessAssembly()
	 * @see #getBlockchainSystemNodeSystem()
	 * @generated
	 */
	EReference getBlockchainSystemNodeSystem_MiningProcessAssembly();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBlockValidatorAssembly <em>Block Validator Assembly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Block Validator Assembly</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBlockValidatorAssembly()
	 * @see #getBlockchainSystemNodeSystem()
	 * @generated
	 */
	EReference getBlockchainSystemNodeSystem_BlockValidatorAssembly();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getAssemblyContexts <em>Assembly Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assembly Contexts</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getAssemblyContexts()
	 * @see #getBlockchainSystemNodeSystem()
	 * @generated
	 */
	EReference getBlockchainSystemNodeSystem_AssemblyContexts();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Behavior</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem#getBehavior()
	 * @see #getBlockchainSystemNodeSystem()
	 * @generated
	 */
	EReference getBlockchainSystemNodeSystem_Behavior();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext <em>Blockchain System Node Assembly Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blockchain System Node Assembly Context</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext
	 * @generated
	 */
	EClass getBlockchainSystemNodeAssemblyContext();

	/**
	 * Returns the meta object for the reference '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext#getEncapsulatedComponent <em>Encapsulated Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Encapsulated Component</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeAssemblyContext#getEncapsulatedComponent()
	 * @see #getBlockchainSystemNodeAssemblyContext()
	 * @generated
	 */
	EReference getBlockchainSystemNodeAssemblyContext_EncapsulatedComponent();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification <em>Node Behavior Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Behavior Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification
	 * @generated
	 */
	EClass getNodeBehaviorSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Behavior</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehaviorSpecification#getBehavior()
	 * @see #getNodeBehaviorSpecification()
	 * @generated
	 */
	EAttribute getNodeBehaviorSpecification_Behavior();

	/**
	 * Returns the meta object for enum '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior <em>Node Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Node Behavior</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior
	 * @generated
	 */
	EEnum getNodeBehavior();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NodesystemFactory getNodesystemFactory();

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
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl <em>Blockchain System Node System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeSystemImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getBlockchainSystemNodeSystem()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM_NODE_SYSTEM = eINSTANCE.getBlockchainSystemNodeSystem();

		/**
		 * The meta object literal for the '<em><b>Mining Process Assembly</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY = eINSTANCE
				.getBlockchainSystemNodeSystem_MiningProcessAssembly();

		/**
		 * The meta object literal for the '<em><b>Block Validator Assembly</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY = eINSTANCE
				.getBlockchainSystemNodeSystem_BlockValidatorAssembly();

		/**
		 * The meta object literal for the '<em><b>Assembly Contexts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS = eINSTANCE
				.getBlockchainSystemNodeSystem_AssemblyContexts();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR = eINSTANCE.getBlockchainSystemNodeSystem_Behavior();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeAssemblyContextImpl <em>Blockchain System Node Assembly Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.BlockchainSystemNodeAssemblyContextImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getBlockchainSystemNodeAssemblyContext()
		 * @generated
		 */
		EClass BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT = eINSTANCE.getBlockchainSystemNodeAssemblyContext();

		/**
		 * The meta object literal for the '<em><b>Encapsulated Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCKCHAIN_SYSTEM_NODE_ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT = eINSTANCE
				.getBlockchainSystemNodeAssemblyContext_EncapsulatedComponent();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodeBehaviorSpecificationImpl <em>Node Behavior Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodeBehaviorSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getNodeBehaviorSpecification()
		 * @generated
		 */
		EClass NODE_BEHAVIOR_SPECIFICATION = eINSTANCE.getNodeBehaviorSpecification();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_BEHAVIOR_SPECIFICATION__BEHAVIOR = eINSTANCE.getNodeBehaviorSpecification_Behavior();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior <em>Node Behavior</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodeBehavior
		 * @see org.palladiosimulator.blockchainsystems.bscm.nodesystem.impl.NodesystemPackageImpl#getNodeBehavior()
		 * @generated
		 */
		EEnum NODE_BEHAVIOR = eINSTANCE.getNodeBehavior();

	}

} //NodesystemPackage
