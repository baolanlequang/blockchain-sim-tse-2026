/**
 */
package org.palladiosimulator.blockchainsystems.bscm.linkallocation.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.provider.BscmEditPlugin;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecificationValue} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DynamicLinkThroughputSpecificationValueItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DynamicLinkThroughputSpecificationValueItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addThroughputPropertyDescriptor(object);
			addProbabilityPropertyDescriptor(object);
			addDurationPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Throughput feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addThroughputPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_DynamicLinkThroughputSpecificationValue_Throughput_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_DynamicLinkThroughputSpecificationValue_Throughput_feature",
						"_UI_DynamicLinkThroughputSpecificationValue_type"),
				LinkallocationPackage.Literals.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__THROUGHPUT, true, false,
				false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Probability feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addProbabilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_DynamicLinkThroughputSpecificationValue_Probability_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_DynamicLinkThroughputSpecificationValue_Probability_feature",
						"_UI_DynamicLinkThroughputSpecificationValue_type"),
				LinkallocationPackage.Literals.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__PROBABILITY, true, false,
				false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Duration feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDurationPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_DynamicLinkThroughputSpecificationValue_Duration_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_DynamicLinkThroughputSpecificationValue_Duration_feature",
								"_UI_DynamicLinkThroughputSpecificationValue_type"),
						LinkallocationPackage.Literals.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__DURATION, true,
						false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns DynamicLinkThroughputSpecificationValue.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/DynamicLinkThroughputSpecificationValue"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((DynamicLinkThroughputSpecificationValue) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_DynamicLinkThroughputSpecificationValue_type")
				: getString("_UI_DynamicLinkThroughputSpecificationValue_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(DynamicLinkThroughputSpecificationValue.class)) {
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__THROUGHPUT:
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__PROBABILITY:
		case LinkallocationPackage.DYNAMIC_LINK_THROUGHPUT_SPECIFICATION_VALUE__DURATION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return BscmEditPlugin.INSTANCE;
	}

}
