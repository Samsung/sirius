/**
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.diagram.description.style.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.diagram.description.style.RoundedCornerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.description.style.RoundedCornerStyleDescription}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class RoundedCornerStyleDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public RoundedCornerStyleDescriptionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addArcWidthPropertyDescriptor(object);
            addArcHeightPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Arc Width feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addArcWidthPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_RoundedCornerStyleDescription_arcWidth_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_RoundedCornerStyleDescription_arcWidth_feature", "_UI_RoundedCornerStyleDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_WIDTH, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_CornerPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This adds a property descriptor for the Arc Height feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addArcHeightPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_RoundedCornerStyleDescription_arcHeight_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_RoundedCornerStyleDescription_arcHeight_feature", "_UI_RoundedCornerStyleDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_HEIGHT, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_CornerPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        Integer labelValue = ((RoundedCornerStyleDescription) object).getArcWidth();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_RoundedCornerStyleDescription_type") : //$NON-NLS-1$
                getString("_UI_RoundedCornerStyleDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(RoundedCornerStyleDescription.class)) {
        case StylePackage.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_WIDTH:
        case StylePackage.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_HEIGHT:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return DiagramUIPlugin.INSTANCE;
    }

}
