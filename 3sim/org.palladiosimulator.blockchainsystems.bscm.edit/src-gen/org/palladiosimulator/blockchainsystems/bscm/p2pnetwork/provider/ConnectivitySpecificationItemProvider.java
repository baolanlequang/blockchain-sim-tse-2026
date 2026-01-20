/**
 */
package org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.provider.BscmEditPlugin;

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ConnectivitySpecificationItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectivitySpecificationItemProvider(AdapterFactory adapterFactory) {
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

			addInBoundLinkAllocationSpecificationPropertyDescriptor(object);
			addOutBoundLinkAllocationSpecificationPropertyDescriptor(object);
			addNumberOfInboundPropertyDescriptor(object);
			addNumberOfOutBoundPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the In Bound Link Allocation Specification feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInBoundLinkAllocationSpecificationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ConnectivitySpecification_InBoundLinkAllocationSpecification_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ConnectivitySpecification_InBoundLinkAllocationSpecification_feature",
						"_UI_ConnectivitySpecification_type"),
				P2pnetworkPackage.Literals.CONNECTIVITY_SPECIFICATION__IN_BOUND_LINK_ALLOCATION_SPECIFICATION, true,
				false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Out Bound Link Allocation Specification feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOutBoundLinkAllocationSpecificationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ConnectivitySpecification_OutBoundLinkAllocationSpecification_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ConnectivitySpecification_OutBoundLinkAllocationSpecification_feature",
						"_UI_ConnectivitySpecification_type"),
				P2pnetworkPackage.Literals.CONNECTIVITY_SPECIFICATION__OUT_BOUND_LINK_ALLOCATION_SPECIFICATION, true,
				false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Number Of Inbound feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNumberOfInboundPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ConnectivitySpecification_NumberOfInbound_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_ConnectivitySpecification_NumberOfInbound_feature",
						"_UI_ConnectivitySpecification_type"),
				P2pnetworkPackage.Literals.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND, true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Number Of Out Bound feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNumberOfOutBoundPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ConnectivitySpecification_NumberOfOutBound_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_ConnectivitySpecification_NumberOfOutBound_feature", "_UI_ConnectivitySpecification_type"),
				P2pnetworkPackage.Literals.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND, true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns ConnectivitySpecification.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConnectivitySpecification"));
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
		String label = ((ConnectivitySpecification) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_ConnectivitySpecification_type")
				: getString("_UI_ConnectivitySpecification_type") + " " + label;
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

		switch (notification.getFeatureID(ConnectivitySpecification.class)) {
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_INBOUND:
		case P2pnetworkPackage.CONNECTIVITY_SPECIFICATION__NUMBER_OF_OUT_BOUND:
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
