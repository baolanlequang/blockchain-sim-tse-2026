/**
 */
package org.palladiosimulator.blockchainsystems.bscm.transactions.provider;

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

import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;

import org.palladiosimulator.pcm.core.entity.provider.EntityItemProvider;

/**
 * This is the item provider adapter for a {@link org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecificationValue} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TransactionPropertiesSpecificationValueItemProvider extends EntityItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransactionPropertiesSpecificationValueItemProvider(AdapterFactory adapterFactory) {
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

			addSizePropertyDescriptor(object);
			addFeePropertyDescriptor(object);
			addProbabilityPropertyDescriptor(object);
			addAmountPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSizePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransactionPropertiesSpecificationValue_Size_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_TransactionPropertiesSpecificationValue_Size_feature",
								"_UI_TransactionPropertiesSpecificationValue_type"),
						TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE, true, false,
						false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Fee feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFeePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransactionPropertiesSpecificationValue_Fee_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_TransactionPropertiesSpecificationValue_Fee_feature",
								"_UI_TransactionPropertiesSpecificationValue_type"),
						TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE, true, false,
						false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
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
				getString("_UI_TransactionPropertiesSpecificationValue_Probability_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_TransactionPropertiesSpecificationValue_Probability_feature",
						"_UI_TransactionPropertiesSpecificationValue_type"),
				TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY, true, false,
				false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Amount feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAmountPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransactionPropertiesSpecificationValue_Amount_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_TransactionPropertiesSpecificationValue_Amount_feature",
								"_UI_TransactionPropertiesSpecificationValue_type"),
						TransactionsPackage.Literals.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT, true, false,
						false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns TransactionPropertiesSpecificationValue.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
				getResourceLocator().getImage("full/obj16/TransactionPropertiesSpecificationValue"));
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
		String label = ((TransactionPropertiesSpecificationValue) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_TransactionPropertiesSpecificationValue_type")
				: getString("_UI_TransactionPropertiesSpecificationValue_type") + " " + label;
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

		switch (notification.getFeatureID(TransactionPropertiesSpecificationValue.class)) {
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__SIZE:
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__FEE:
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__PROBABILITY:
		case TransactionsPackage.TRANSACTION_PROPERTIES_SPECIFICATION_VALUE__AMOUNT:
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
