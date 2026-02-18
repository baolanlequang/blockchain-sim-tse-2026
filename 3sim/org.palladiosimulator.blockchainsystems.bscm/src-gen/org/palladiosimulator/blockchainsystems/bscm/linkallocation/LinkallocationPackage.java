/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

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
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationFactory
 * @model kind="package"
 * @generated
 */
public interface LinkallocationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "linkallocation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://palladiosimulator.org/BlockchainSystemComponentModel/LinkAllocation/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "linkallocation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LinkallocationPackage eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationRepositoryImpl <em>Link Allocation Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationRepositoryImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkAllocationRepository()
	 * @generated
	 */
	int LINK_ALLOCATION_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION_REPOSITORY__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION_REPOSITORY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Link Allocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Link Allocation Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION_REPOSITORY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl <em>Link Allocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkAllocation()
	 * @generated
	 */
	int LINK_ALLOCATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Latency Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION__LATENCY_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Throughput Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION__THROUGHPUT_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Bandwidth Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION__BANDWIDTH_SPECIFICATION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Link Allocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ALLOCATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkLatencySpecificationImpl <em>Link Latency Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkLatencySpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkLatencySpecification()
	 * @generated
	 */
	int LINK_LATENCY_SPECIFICATION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_LATENCY_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_LATENCY_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The number of structural features of the '<em>Link Latency Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_LATENCY_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.BandwidthSpecificationImpl <em>Bandwidth Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.BandwidthSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getBandwidthSpecification()
	 * @generated
	 */
	int BANDWIDTH_SPECIFICATION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Bandwidth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION__BANDWIDTH = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Heterogeneity Link Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION__HETEROGENEITY_LINK_TARGET = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Heterogeneity Node Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION__HETEROGENEITY_NODE_TARGET = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Bandwidth Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BANDWIDTH_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl <em>Dynamic Link Latency Specification Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkLatencySpecificationValue()
	 * @generated
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Latency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Dynamic Link Latency Specification Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkThroughputSpecificationImpl <em>Link Throughput Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkThroughputSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkThroughputSpecification()
	 * @generated
	 */
	int LINK_THROUGHPUT_SPECIFICATION = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_THROUGHPUT_SPECIFICATION__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_THROUGHPUT_SPECIFICATION__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The number of structural features of the '<em>Link Throughput Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationValueImpl <em>Dynamic Link Throughput Specification Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationValueImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkThroughputSpecificationValue()
	 * @generated
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__ID = EntityPackage.ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Throughput</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__THROUGHPUT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__PROBABILITY = EntityPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__DURATION = EntityPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Dynamic Link Throughput Specification Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationImpl <em>Dynamic Link Latency Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkLatencySpecification()
	 * @generated
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION__ID = LINK_LATENCY_SPECIFICATION__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION__ENTITY_NAME = LINK_LATENCY_SPECIFICATION__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION__VALUES = LINK_LATENCY_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dynamic Link Latency Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_LATENCY_SPECIFICATION_FEATURE_COUNT = LINK_LATENCY_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationImpl <em>Dynamic Link Throughput Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkThroughputSpecification()
	 * @generated
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION__ID = LINK_THROUGHPUT_SPECIFICATION__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION__ENTITY_NAME = LINK_THROUGHPUT_SPECIFICATION__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION__VALUES = LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dynamic Link Throughput Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT = LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkLatencySpecificationImpl <em>Static Link Latency Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkLatencySpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getStaticLinkLatencySpecification()
	 * @generated
	 */
	int STATIC_LINK_LATENCY_SPECIFICATION = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_LATENCY_SPECIFICATION__ID = LINK_LATENCY_SPECIFICATION__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_LATENCY_SPECIFICATION__ENTITY_NAME = LINK_LATENCY_SPECIFICATION__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Latency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_LATENCY_SPECIFICATION__LATENCY = LINK_LATENCY_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Static Link Latency Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_LATENCY_SPECIFICATION_FEATURE_COUNT = LINK_LATENCY_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkThroughputSpecificationImpl <em>Static Link Throughput Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkThroughputSpecificationImpl
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getStaticLinkThroughputSpecification()
	 * @generated
	 */
	int STATIC_LINK_THROUGHPUT_SPECIFICATION = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_THROUGHPUT_SPECIFICATION__ID = LINK_THROUGHPUT_SPECIFICATION__ID;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_THROUGHPUT_SPECIFICATION__ENTITY_NAME = LINK_THROUGHPUT_SPECIFICATION__ENTITY_NAME;

	/**
	 * The feature id for the '<em><b>Throughput</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_THROUGHPUT_SPECIFICATION__THROUGHPUT = LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Static Link Throughput Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT = LINK_THROUGHPUT_SPECIFICATION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository <em>Link Allocation Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Allocation Repository</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository
	 * @generated
	 */
	EClass getLinkAllocationRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository#getLinkAllocations <em>Link Allocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link Allocations</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository#getLinkAllocations()
	 * @see #getLinkAllocationRepository()
	 * @generated
	 */
	EReference getLinkAllocationRepository_LinkAllocations();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation <em>Link Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Allocation</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation
	 * @generated
	 */
	EClass getLinkAllocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getLatencySpecification <em>Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Latency Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getLatencySpecification()
	 * @see #getLinkAllocation()
	 * @generated
	 */
	EReference getLinkAllocation_LatencySpecification();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getThroughputSpecification <em>Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Throughput Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getThroughputSpecification()
	 * @see #getLinkAllocation()
	 * @generated
	 */
	EReference getLinkAllocation_ThroughputSpecification();

	/**
	 * Returns the meta object for the containment reference '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getBandwidthSpecification <em>Bandwidth Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bandwidth Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation#getBandwidthSpecification()
	 * @see #getLinkAllocation()
	 * @generated
	 */
	EReference getLinkAllocation_BandwidthSpecification();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification <em>Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Latency Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification
	 * @generated
	 */
	EClass getLinkLatencySpecification();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification <em>Bandwidth Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bandwidth Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification
	 * @generated
	 */
	EClass getBandwidthSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getBandwidth <em>Bandwidth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bandwidth</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getBandwidth()
	 * @see #getBandwidthSpecification()
	 * @generated
	 */
	EAttribute getBandwidthSpecification_Bandwidth();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityLinkTarget <em>Heterogeneity Link Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Heterogeneity Link Target</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityLinkTarget()
	 * @see #getBandwidthSpecification()
	 * @generated
	 */
	EAttribute getBandwidthSpecification_HeterogeneityLinkTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityNodeTarget <em>Heterogeneity Node Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Heterogeneity Node Target</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification#getHeterogeneityNodeTarget()
	 * @see #getBandwidthSpecification()
	 * @generated
	 */
	EAttribute getBandwidthSpecification_HeterogeneityNodeTarget();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue <em>Dynamic Link Latency Specification Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Link Latency Specification Value</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue
	 * @generated
	 */
	EClass getDynamicLinkLatencySpecificationValue();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getLatency <em>Latency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Latency</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getLatency()
	 * @see #getDynamicLinkLatencySpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkLatencySpecificationValue_Latency();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getProbability()
	 * @see #getDynamicLinkLatencySpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkLatencySpecificationValue_Probability();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue#getDuration()
	 * @see #getDynamicLinkLatencySpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkLatencySpecificationValue_Duration();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification <em>Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Throughput Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification
	 * @generated
	 */
	EClass getLinkThroughputSpecification();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue <em>Dynamic Link Throughput Specification Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Link Throughput Specification Value</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue
	 * @generated
	 */
	EClass getDynamicLinkThroughputSpecificationValue();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getThroughput <em>Throughput</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Throughput</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getThroughput()
	 * @see #getDynamicLinkThroughputSpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkThroughputSpecificationValue_Throughput();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getProbability()
	 * @see #getDynamicLinkThroughputSpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkThroughputSpecificationValue_Probability();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue#getDuration()
	 * @see #getDynamicLinkThroughputSpecificationValue()
	 * @generated
	 */
	EAttribute getDynamicLinkThroughputSpecificationValue_Duration();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification <em>Dynamic Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Link Latency Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification
	 * @generated
	 */
	EClass getDynamicLinkLatencySpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification#getValues()
	 * @see #getDynamicLinkLatencySpecification()
	 * @generated
	 */
	EReference getDynamicLinkLatencySpecification_Values();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification <em>Dynamic Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Link Throughput Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification
	 * @generated
	 */
	EClass getDynamicLinkThroughputSpecification();

	/**
	 * Returns the meta object for the containment reference list '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification#getValues()
	 * @see #getDynamicLinkThroughputSpecification()
	 * @generated
	 */
	EReference getDynamicLinkThroughputSpecification_Values();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification <em>Static Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Link Latency Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification
	 * @generated
	 */
	EClass getStaticLinkLatencySpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification#getLatency <em>Latency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Latency</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification#getLatency()
	 * @see #getStaticLinkLatencySpecification()
	 * @generated
	 */
	EAttribute getStaticLinkLatencySpecification_Latency();

	/**
	 * Returns the meta object for class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification <em>Static Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Link Throughput Specification</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification
	 * @generated
	 */
	EClass getStaticLinkThroughputSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification#getThroughput <em>Throughput</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Throughput</em>'.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification#getThroughput()
	 * @see #getStaticLinkThroughputSpecification()
	 * @generated
	 */
	EAttribute getStaticLinkThroughputSpecification_Throughput();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LinkallocationFactory getLinkallocationFactory();

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
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationRepositoryImpl <em>Link Allocation Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationRepositoryImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkAllocationRepository()
		 * @generated
		 */
		EClass LINK_ALLOCATION_REPOSITORY = eINSTANCE.getLinkAllocationRepository();

		/**
		 * The meta object literal for the '<em><b>Link Allocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ALLOCATION_REPOSITORY__LINK_ALLOCATIONS = eINSTANCE
				.getLinkAllocationRepository_LinkAllocations();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl <em>Link Allocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkAllocationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkAllocation()
		 * @generated
		 */
		EClass LINK_ALLOCATION = eINSTANCE.getLinkAllocation();

		/**
		 * The meta object literal for the '<em><b>Latency Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ALLOCATION__LATENCY_SPECIFICATION = eINSTANCE.getLinkAllocation_LatencySpecification();

		/**
		 * The meta object literal for the '<em><b>Throughput Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ALLOCATION__THROUGHPUT_SPECIFICATION = eINSTANCE.getLinkAllocation_ThroughputSpecification();

		/**
		 * The meta object literal for the '<em><b>Bandwidth Specification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ALLOCATION__BANDWIDTH_SPECIFICATION = eINSTANCE.getLinkAllocation_BandwidthSpecification();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkLatencySpecificationImpl <em>Link Latency Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkLatencySpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkLatencySpecification()
		 * @generated
		 */
		EClass LINK_LATENCY_SPECIFICATION = eINSTANCE.getLinkLatencySpecification();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.BandwidthSpecificationImpl <em>Bandwidth Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.BandwidthSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getBandwidthSpecification()
		 * @generated
		 */
		EClass BANDWIDTH_SPECIFICATION = eINSTANCE.getBandwidthSpecification();

		/**
		 * The meta object literal for the '<em><b>Bandwidth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BANDWIDTH_SPECIFICATION__BANDWIDTH = eINSTANCE.getBandwidthSpecification_Bandwidth();

		/**
		 * The meta object literal for the '<em><b>Heterogeneity Link Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BANDWIDTH_SPECIFICATION__HETEROGENEITY_LINK_TARGET = eINSTANCE
				.getBandwidthSpecification_HeterogeneityLinkTarget();

		/**
		 * The meta object literal for the '<em><b>Heterogeneity Node Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BANDWIDTH_SPECIFICATION__HETEROGENEITY_NODE_TARGET = eINSTANCE
				.getBandwidthSpecification_HeterogeneityNodeTarget();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl <em>Dynamic Link Latency Specification Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationValueImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkLatencySpecificationValue()
		 * @generated
		 */
		EClass DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE = eINSTANCE.getDynamicLinkLatencySpecificationValue();

		/**
		 * The meta object literal for the '<em><b>Latency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__LATENCY = eINSTANCE
				.getDynamicLinkLatencySpecificationValue_Latency();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__PROBABILITY = eINSTANCE
				.getDynamicLinkLatencySpecificationValue_Probability();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE__DURATION = eINSTANCE
				.getDynamicLinkLatencySpecificationValue_Duration();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkThroughputSpecificationImpl <em>Link Throughput Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkThroughputSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getLinkThroughputSpecification()
		 * @generated
		 */
		EClass LINK_THROUGHPUT_SPECIFICATION = eINSTANCE.getLinkThroughputSpecification();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationValueImpl <em>Dynamic Link Throughput Specification Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationValueImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkThroughputSpecificationValue()
		 * @generated
		 */
		EClass DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE = eINSTANCE.getDynamicLinkThroughputSpecificationValue();

		/**
		 * The meta object literal for the '<em><b>Throughput</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__THROUGHPUT = eINSTANCE
				.getDynamicLinkThroughputSpecificationValue_Throughput();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__PROBABILITY = eINSTANCE
				.getDynamicLinkThroughputSpecificationValue_Probability();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__DURATION = eINSTANCE
				.getDynamicLinkThroughputSpecificationValue_Duration();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationImpl <em>Dynamic Link Latency Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkLatencySpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkLatencySpecification()
		 * @generated
		 */
		EClass DYNAMIC_LINK_LATENCY_SPECIFICATION = eINSTANCE.getDynamicLinkLatencySpecification();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DYNAMIC_LINK_LATENCY_SPECIFICATION__VALUES = eINSTANCE.getDynamicLinkLatencySpecification_Values();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationImpl <em>Dynamic Link Throughput Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.DynamicLinkThroughputSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getDynamicLinkThroughputSpecification()
		 * @generated
		 */
		EClass DYNAMIC_LINK_THROUGHPUT_SPECIFICATION = eINSTANCE.getDynamicLinkThroughputSpecification();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DYNAMIC_LINK_THROUGHPUT_SPECIFICATION__VALUES = eINSTANCE
				.getDynamicLinkThroughputSpecification_Values();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkLatencySpecificationImpl <em>Static Link Latency Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkLatencySpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getStaticLinkLatencySpecification()
		 * @generated
		 */
		EClass STATIC_LINK_LATENCY_SPECIFICATION = eINSTANCE.getStaticLinkLatencySpecification();

		/**
		 * The meta object literal for the '<em><b>Latency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATIC_LINK_LATENCY_SPECIFICATION__LATENCY = eINSTANCE.getStaticLinkLatencySpecification_Latency();

		/**
		 * The meta object literal for the '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkThroughputSpecificationImpl <em>Static Link Throughput Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.StaticLinkThroughputSpecificationImpl
		 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationPackageImpl#getStaticLinkThroughputSpecification()
		 * @generated
		 */
		EClass STATIC_LINK_THROUGHPUT_SPECIFICATION = eINSTANCE.getStaticLinkThroughputSpecification();

		/**
		 * The meta object literal for the '<em><b>Throughput</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATIC_LINK_THROUGHPUT_SPECIFICATION__THROUGHPUT = eINSTANCE
				.getStaticLinkThroughputSpecification_Throughput();

	}

} //LinkallocationPackage
