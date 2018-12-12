/**
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.properties.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.properties.FILL_LAYOUT_ORIENTATION;
import org.eclipse.sirius.properties.FillLayoutDescription;
import org.eclipse.sirius.properties.PropertiesPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.properties.FillLayoutDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FillLayoutDescriptionItemProvider extends LayoutDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FillLayoutDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            addOrientationPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Orientation feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addOrientationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_FillLayoutDescription_orientation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_FillLayoutDescription_orientation_feature", "_UI_FillLayoutDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        PropertiesPackage.Literals.FILL_LAYOUT_DESCRIPTION__ORIENTATION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns FillLayoutDescription.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        FILL_LAYOUT_ORIENTATION labelValue = ((FillLayoutDescription) object).getOrientation();
        if (labelValue == FILL_LAYOUT_ORIENTATION.HORIZONTAL) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/FillLayoutDescriptionHorizontal")); //$NON-NLS-1$
        } else {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/FillLayoutDescriptionVertical")); //$NON-NLS-1$
        }
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        FILL_LAYOUT_ORIENTATION labelValue = ((FillLayoutDescription) object).getOrientation();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_FillLayoutDescription_type") : //$NON-NLS-1$
                label;
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

        switch (notification.getFeatureID(FillLayoutDescription.class)) {
        case PropertiesPackage.FILL_LAYOUT_DESCRIPTION__ORIENTATION:
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

}
