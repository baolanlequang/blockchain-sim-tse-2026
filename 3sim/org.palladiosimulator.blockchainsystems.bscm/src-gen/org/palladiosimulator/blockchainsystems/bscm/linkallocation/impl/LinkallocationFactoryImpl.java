/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LinkallocationFactoryImpl extends EFactoryImpl implements LinkallocationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LinkallocationFactory init() {
		try {
			LinkallocationFactory theLinkallocationFactory = (LinkallocationFactory) EPackage.Registry.INSTANCE
					.getEFactory(LinkallocationPackage.eNS_URI);
			if (theLinkallocationFactory != null) {
				return theLinkallocationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LinkallocationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkallocationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case LinkallocationPackage.LINK_ALLOCATION_REPOSITORY:
			return createLinkAllocationRepository();
		case LinkallocationPackage.LINK_ALLOCATION:
			return createLinkAllocation();
		case LinkallocationPackage.BANDWIDTH_SPECIFICATION:
			return createBandwidthSpecification();
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION_VALUE:
			return createDynamicLinkLatencySpecificationValue();
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE:
			return createDynamicLinkThroughputSpecificationValue();
		case LinkallocationPackage.DYNAMIC_LINK_LATENCY_SPECIFICATION:
			return createDynamicLinkLatencySpecification();
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION:
			return createDynamicLinkThroughputSpecification();
		case LinkallocationPackage.STATIC_LINK_LATENCY_SPECIFICATION:
			return createStaticLinkLatencySpecification();
		case LinkallocationPackage.STATIC_LINK_THROUGHPUT_SPECIFICATION:
			return createStaticLinkThroughputSpecification();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocationRepository createLinkAllocationRepository() {
		LinkAllocationRepositoryImpl linkAllocationRepository = new LinkAllocationRepositoryImpl();
		return linkAllocationRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkAllocation createLinkAllocation() {
		LinkAllocationImpl linkAllocation = new LinkAllocationImpl();
		return linkAllocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BandwidthSpecification createBandwidthSpecification() {
		BandwidthSpecificationImpl bandwidthSpecification = new BandwidthSpecificationImpl();
		return bandwidthSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicLinkLatencySpecificationValue createDynamicLinkLatencySpecificationValue() {
		DynamicLinkLatencySpecificationValueImpl dynamicLinkLatencySpecificationValue = new DynamicLinkLatencySpecificationValueImpl();
		return dynamicLinkLatencySpecificationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicLinkThroughputSpecificationValue createDynamicLinkThroughputSpecificationValue() {
		DynamicLinkThroughputSpecificationValueImpl dynamicLinkThroughputSpecificationValue = new DynamicLinkThroughputSpecificationValueImpl();
		return dynamicLinkThroughputSpecificationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicLinkLatencySpecification createDynamicLinkLatencySpecification() {
		DynamicLinkLatencySpecificationImpl dynamicLinkLatencySpecification = new DynamicLinkLatencySpecificationImpl();
		return dynamicLinkLatencySpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicLinkThroughputSpecification createDynamicLinkThroughputSpecification() {
		DynamicLinkThroughputSpecificationImpl dynamicLinkThroughputSpecification = new DynamicLinkThroughputSpecificationImpl();
		return dynamicLinkThroughputSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StaticLinkLatencySpecification createStaticLinkLatencySpecification() {
		StaticLinkLatencySpecificationImpl staticLinkLatencySpecification = new StaticLinkLatencySpecificationImpl();
		return staticLinkLatencySpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StaticLinkThroughputSpecification createStaticLinkThroughputSpecification() {
		StaticLinkThroughputSpecificationImpl staticLinkThroughputSpecification = new StaticLinkThroughputSpecificationImpl();
		return staticLinkThroughputSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkallocationPackage getLinkallocationPackage() {
		return (LinkallocationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LinkallocationPackage getPackage() {
		return LinkallocationPackage.eINSTANCE;
	}

} //LinkallocationFactoryImpl
