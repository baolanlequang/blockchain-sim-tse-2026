/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.util;

import de.uka.ipd.sdq.identifier.Identifier;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.*;

import org.palladiosimulator.pcm.PCMBaseClass;
import org.palladiosimulator.pcm.PCMClass;

import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage
 * @generated
 */
public class LinkallocationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LinkallocationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkallocationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = LinkallocationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkallocationSwitch<Adapter> modelSwitch = new LinkallocationSwitch<Adapter>() {
		@Override
		public Adapter caseLinkAllocationRepository(LinkAllocationRepository object) {
			return createLinkAllocationRepositoryAdapter();
		}

		@Override
		public Adapter caseLinkAllocation(LinkAllocation object) {
			return createLinkAllocationAdapter();
		}

		@Override
		public Adapter caseLinkLatencySpecification(LinkLatencySpecification object) {
			return createLinkLatencySpecificationAdapter();
		}

		@Override
		public Adapter caseDynamicLinkLatencySpecificationValue(DynamicLinkLatencySpecificationValue object) {
			return createDynamicLinkLatencySpecificationValueAdapter();
		}

		@Override
		public Adapter caseLinkThroughputSpecification(LinkThroughputSpecification object) {
			return createLinkThroughputSpecificationAdapter();
		}

		@Override
		public Adapter caseDynamicLinkThroughputSpecificationValue(DynamicLinkThroughputSpecificationValue object) {
			return createDynamicLinkThroughputSpecificationValueAdapter();
		}

		@Override
		public Adapter caseDynamicLinkLatencySpecification(DynamicLinkLatencySpecification object) {
			return createDynamicLinkLatencySpecificationAdapter();
		}

		@Override
		public Adapter caseDynamicLinkThroughputSpecification(DynamicLinkThroughputSpecification object) {
			return createDynamicLinkThroughputSpecificationAdapter();
		}

		@Override
		public Adapter caseStaticLinkLatencySpecification(StaticLinkLatencySpecification object) {
			return createStaticLinkLatencySpecificationAdapter();
		}

		@Override
		public Adapter caseStaticLinkThroughputSpecification(StaticLinkThroughputSpecification object) {
			return createStaticLinkThroughputSpecificationAdapter();
		}

		@Override
		public Adapter caseIdentifier(Identifier object) {
			return createIdentifierAdapter();
		}

		@Override
		public Adapter casePCMClass(PCMClass object) {
			return createPCMClassAdapter();
		}

		@Override
		public Adapter casePCMBaseClass(PCMBaseClass object) {
			return createPCMBaseClassAdapter();
		}

		@Override
		public Adapter caseNamedElement(NamedElement object) {
			return createNamedElementAdapter();
		}

		@Override
		public Adapter caseEntity(Entity object) {
			return createEntityAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository <em>Link Allocation Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocationRepository
	 * @generated
	 */
	public Adapter createLinkAllocationRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation <em>Link Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkAllocation
	 * @generated
	 */
	public Adapter createLinkAllocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification <em>Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification
	 * @generated
	 */
	public Adapter createLinkLatencySpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue <em>Dynamic Link Latency Specification Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecificationValue
	 * @generated
	 */
	public Adapter createDynamicLinkLatencySpecificationValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification <em>Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification
	 * @generated
	 */
	public Adapter createLinkThroughputSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue <em>Dynamic Link Throughput Specification Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue
	 * @generated
	 */
	public Adapter createDynamicLinkThroughputSpecificationValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification <em>Dynamic Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification
	 * @generated
	 */
	public Adapter createDynamicLinkLatencySpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification <em>Dynamic Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification
	 * @generated
	 */
	public Adapter createDynamicLinkThroughputSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification <em>Static Link Latency Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification
	 * @generated
	 */
	public Adapter createStaticLinkLatencySpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification <em>Static Link Throughput Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification
	 * @generated
	 */
	public Adapter createStaticLinkThroughputSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.identifier.Identifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uka.ipd.sdq.identifier.Identifier
	 * @generated
	 */
	public Adapter createIdentifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.pcm.PCMClass <em>PCM Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.pcm.PCMClass
	 * @generated
	 */
	public Adapter createPCMClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.pcm.PCMBaseClass <em>PCM Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.pcm.PCMBaseClass
	 * @generated
	 */
	public Adapter createPCMBaseClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.pcm.core.entity.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.pcm.core.entity.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.palladiosimulator.pcm.core.entity.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.palladiosimulator.pcm.core.entity.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //LinkallocationAdapterFactory
