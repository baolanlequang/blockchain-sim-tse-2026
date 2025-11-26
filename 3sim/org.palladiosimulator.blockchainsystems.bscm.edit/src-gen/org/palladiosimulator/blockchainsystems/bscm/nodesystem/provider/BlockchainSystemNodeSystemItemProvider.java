/**
 */
package org.palladiosimulator.blockchainsystems.bscm.nodesystem.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.provider.BscmEditPlugin;

import org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemFactory;
import org.palladiosimulator.blockchainsystems.bscm.nodesystem.NodesystemPackage;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.nodesystem.BlockchainSystemNodeSystem} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BlockchainSystemNodeSystemItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainSystemNodeSystemItemProvider(AdapterFactory adapterFactory) {
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

			addMiningProcessAssemblyPropertyDescriptor(object);
			addBlockValidatorAssemblyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Mining Process Assembly feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMiningProcessAssemblyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_BlockchainSystemNodeSystem_MiningProcessAssembly_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_BlockchainSystemNodeSystem_MiningProcessAssembly_feature",
								"_UI_BlockchainSystemNodeSystem_type"),
						NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__MINING_PROCESS_ASSEMBLY, true, false,
						true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Block Validator Assembly feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBlockValidatorAssemblyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_BlockchainSystemNodeSystem_BlockValidatorAssembly_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_BlockchainSystemNodeSystem_BlockValidatorAssembly_feature",
						"_UI_BlockchainSystemNodeSystem_type"),
				NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BLOCK_VALIDATOR_ASSEMBLY, true, false, true,
				null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS);
			childrenFeatures.add(NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns BlockchainSystemNodeSystem.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/BlockchainSystemNodeSystem"));
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
		String label = ((BlockchainSystemNodeSystem) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_BlockchainSystemNodeSystem_type")
				: getString("_UI_BlockchainSystemNodeSystem_type") + " " + label;
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

		switch (notification.getFeatureID(BlockchainSystemNodeSystem.class)) {
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS:
		case NodesystemPackage.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors
				.add(createChildParameter(NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__ASSEMBLY_CONTEXTS,
						NodesystemFactory.eINSTANCE.createBlockchainSystemNodeAssemblyContext()));

		newChildDescriptors.add(createChildParameter(NodesystemPackage.Literals.BLOCKCHAIN_SYSTEM_NODE_SYSTEM__BEHAVIOR,
				NodesystemFactory.eINSTANCE.createNodeBehaviorSpecification()));
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
