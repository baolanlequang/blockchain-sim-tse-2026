/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage
 * @generated
 */
public interface LinkallocationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LinkallocationFactory eINSTANCE = org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl.LinkallocationFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Link Allocation Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link Allocation Repository</em>'.
	 * @generated
	 */
	LinkAllocationRepository createLinkAllocationRepository();

	/**
	 * Returns a new object of class '<em>Link Allocation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link Allocation</em>'.
	 * @generated
	 */
	LinkAllocation createLinkAllocation();

	/**
	 * Returns a new object of class '<em>Bandwidth Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bandwidth Specification</em>'.
	 * @generated
	 */
	BandwidthSpecification createBandwidthSpecification();

	/**
	 * Returns a new object of class '<em>Dynamic Link Latency Specification Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dynamic Link Latency Specification Value</em>'.
	 * @generated
	 */
	DynamicLinkLatencySpecificationValue createDynamicLinkLatencySpecificationValue();

	/**
	 * Returns a new object of class '<em>Dynamic Link Throughput Specification Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dynamic Link Throughput Specification Value</em>'.
	 * @generated
	 */
	DynamicLinkThroughputSpecificationValue createDynamicLinkThroughputSpecificationValue();

	/**
	 * Returns a new object of class '<em>Dynamic Link Latency Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dynamic Link Latency Specification</em>'.
	 * @generated
	 */
	DynamicLinkLatencySpecification createDynamicLinkLatencySpecification();

	/**
	 * Returns a new object of class '<em>Dynamic Link Throughput Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dynamic Link Throughput Specification</em>'.
	 * @generated
	 */
	DynamicLinkThroughputSpecification createDynamicLinkThroughputSpecification();

	/**
	 * Returns a new object of class '<em>Static Link Latency Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Static Link Latency Specification</em>'.
	 * @generated
	 */
	StaticLinkLatencySpecification createStaticLinkLatencySpecification();

	/**
	 * Returns a new object of class '<em>Static Link Throughput Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Static Link Throughput Specification</em>'.
	 * @generated
	 */
	StaticLinkThroughputSpecification createStaticLinkThroughputSpecification();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LinkallocationPackage getLinkallocationPackage();

} //LinkallocationFactory
