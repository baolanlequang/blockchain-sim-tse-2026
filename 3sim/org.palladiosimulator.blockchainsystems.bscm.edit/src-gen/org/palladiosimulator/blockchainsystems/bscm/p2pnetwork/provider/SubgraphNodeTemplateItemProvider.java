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

import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.SubgraphNodeTemplate} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SubgraphNodeTemplateItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubgraphNodeTemplateItemProvider(AdapterFactory adapterFactory) {
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

			addNumberOfNodeOccurencesPropertyDescriptor(object);
			addAllocationPropertyDescriptor(object);
			addIsSubgraphProxyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Number Of Node Occurences feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNumberOfNodeOccurencesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_SubgraphNodeTemplate_NumberOfNodeOccurences_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_SubgraphNodeTemplate_NumberOfNodeOccurences_feature", "_UI_SubgraphNodeTemplate_type"),
				P2pnetworkPackage.Literals.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES, true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Allocation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAllocationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_SubgraphNodeTemplate_Allocation_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_SubgraphNodeTemplate_Allocation_feature",
						"_UI_SubgraphNodeTemplate_type"),
				P2pnetworkPackage.Literals.SUBGRAPH_NODE_TEMPLATE__ALLOCATION, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Is Subgraph Proxy feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIsSubgraphProxyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_SubgraphNodeTemplate_IsSubgraphProxy_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_SubgraphNodeTemplate_IsSubgraphProxy_feature", "_UI_SubgraphNodeTemplate_type"),
						P2pnetworkPackage.Literals.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns SubgraphNodeTemplate.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/SubgraphNodeTemplate"));
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
		String label = ((SubgraphNodeTemplate) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_SubgraphNodeTemplate_type")
				: getString("_UI_SubgraphNodeTemplate_type") + " " + label;
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

		switch (notification.getFeatureID(SubgraphNodeTemplate.class)) {
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__NUMBER_OF_NODE_OCCURENCES:
		case P2pnetworkPackage.SUBGRAPH_NODE_TEMPLATE__IS_SUBGRAPH_PROXY:
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
