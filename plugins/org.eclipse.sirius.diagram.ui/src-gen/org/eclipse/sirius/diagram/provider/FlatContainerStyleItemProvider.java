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
package org.eclipse.sirius.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.diagram.FlatContainerStyle;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.FlatContainerStyle} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FlatContainerStyleItemProvider extends ContainerStyleItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FlatContainerStyleItemProvider(AdapterFactory adapterFactory) {
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

            addBackgroundStylePropertyDescriptor(object);
            addBackgroundColorPropertyDescriptor(object);
            addForegroundColorPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Background Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_FlatContainerStyle_backgroundStyle_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_FlatContainerStyle_backgroundStyle_feature", "_UI_FlatContainerStyle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        DiagramPackage.Literals.FLAT_CONTAINER_STYLE__BACKGROUND_STYLE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This adds a property descriptor for the Background Color feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundColorPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_FlatContainerStyle_backgroundColor_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_FlatContainerStyle_backgroundColor_feature", "_UI_FlatContainerStyle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        DiagramPackage.Literals.FLAT_CONTAINER_STYLE__BACKGROUND_COLOR, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This adds a property descriptor for the Foreground Color feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addForegroundColorPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_FlatContainerStyle_foregroundColor_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_FlatContainerStyle_foregroundColor_feature", "_UI_FlatContainerStyle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        DiagramPackage.Literals.FLAT_CONTAINER_STYLE__FOREGROUND_COLOR, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This returns FlatContainerStyle.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/FlatContainerStyle")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        FlatContainerStyle flatContainerStyle = (FlatContainerStyle) object;
        return getString("_UI_FlatContainerStyle_type") + " " + flatContainerStyle.getLabelSize(); //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(FlatContainerStyle.class)) {
        case DiagramPackage.FLAT_CONTAINER_STYLE__BACKGROUND_STYLE:
        case DiagramPackage.FLAT_CONTAINER_STYLE__BACKGROUND_COLOR:
        case DiagramPackage.FLAT_CONTAINER_STYLE__FOREGROUND_COLOR:
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
