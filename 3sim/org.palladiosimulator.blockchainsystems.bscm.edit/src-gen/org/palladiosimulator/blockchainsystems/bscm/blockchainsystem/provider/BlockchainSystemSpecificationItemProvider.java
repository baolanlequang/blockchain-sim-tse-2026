/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.provider.BscmEditPlugin;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystemSpecification} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BlockchainSystemSpecificationItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockchainSystemSpecificationItemProvider(AdapterFactory adapterFactory) {
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

			addMeanBlockTimePropertyDescriptor(object);
			addNumOfRequiredSecurityConfirmationsPropertyDescriptor(object);
			addMaxBlockSizePropertyDescriptor(object);
			addBlockRewardPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Mean Block Time feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMeanBlockTimePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_BlockchainSystemSpecification_MeanBlockTime_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_BlockchainSystemSpecification_MeanBlockTime_feature",
								"_UI_BlockchainSystemSpecification_type"),
						BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME, true, false,
						false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Num Of Required Security Confirmations feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNumOfRequiredSecurityConfirmationsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_BlockchainSystemSpecification_NumOfRequiredSecurityConfirmations_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_BlockchainSystemSpecification_NumOfRequiredSecurityConfirmations_feature",
						"_UI_BlockchainSystemSpecification_type"),
				BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS,
				true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Max Block Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMaxBlockSizePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_BlockchainSystemSpecification_MaxBlockSize_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_BlockchainSystemSpecification_MaxBlockSize_feature",
								"_UI_BlockchainSystemSpecification_type"),
						BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE, true, false,
						false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Block Reward feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBlockRewardPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_BlockchainSystemSpecification_BlockReward_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_BlockchainSystemSpecification_BlockReward_feature",
						"_UI_BlockchainSystemSpecification_type"),
				BlockchainsystemPackage.Literals.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD, true, false, false,
				ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns BlockchainSystemSpecification.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/BlockchainSystemSpecification"));
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
		String label = ((BlockchainSystemSpecification) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_BlockchainSystemSpecification_type")
				: getString("_UI_BlockchainSystemSpecification_type") + " " + label;
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

		switch (notification.getFeatureID(BlockchainSystemSpecification.class)) {
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MEAN_BLOCK_TIME:
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__NUM_OF_REQUIRED_SECURITY_CONFIRMATIONS:
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__MAX_BLOCK_SIZE:
		case BlockchainsystemPackage.BLOCKCHAIN_SYSTEM_SPECIFICATION__BLOCK_REWARD:
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
