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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.diagram.ContainerStyle;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.diagram.NodeStyle;
import org.eclipse.sirius.diagram.WorkspaceImage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.WorkspaceImage} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class WorkspaceImageItemProvider extends NodeStyleItemProvider {

    private List<IItemPropertyDescriptor> itemPropertyDescriptors_node;

    private List<IItemPropertyDescriptor> itemPropertyDescriptors_nodecontainer;

    private List<IItemPropertyDescriptor> itemPropertyDescriptors_others;

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public WorkspaceImageItemProvider(AdapterFactory adapterFactory) {
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

            addWorkspacePathPropertyDescriptor(object);

            ContainerStyle containerstyle = (ContainerStyle) object;
            EObject node = containerstyle.eContainer();

            if (node instanceof DNodeContainer) {
                addContainerLabelDirectionPropertyDescriptor(object);
                itemPropertyDescriptors_nodecontainer = itemPropertyDescriptors;

            } else if (node instanceof DNode) {

                itemPropertyDescriptors_node = itemPropertyDescriptors;
            } else {

                itemPropertyDescriptors_others = itemPropertyDescriptors;
            }
        } else {

            NodeStyle nodestyle = (NodeStyle) object;
            EObject node = nodestyle.eContainer();
            if (node instanceof DNodeContainer) {

                itemPropertyDescriptors = itemPropertyDescriptors_nodecontainer;

            } else if (node instanceof DNode) {
                itemPropertyDescriptors = itemPropertyDescriptors_node;
            } else {

                itemPropertyDescriptors = itemPropertyDescriptors_others;
            }

        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Container Label Direction
     * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addContainerLabelDirectionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ContainerStyle_containerLabelDirection_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_ContainerStyle_containerLabelDirection_feature", "_UI_ContainerStyle_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                DiagramPackage.Literals.CONTAINER_STYLE__CONTAINER_LABEL_DIRECTION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Workspace Path feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addWorkspacePathPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_WorkspaceImage_workspacePath_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_WorkspaceImage_workspacePath_feature", "_UI_WorkspaceImage_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        DiagramPackage.Literals.WORKSPACE_IMAGE__WORKSPACE_PATH, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This returns WorkspaceImage.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/WorkspaceImage")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText(Object object) {
        WorkspaceImage workspaceImage = (WorkspaceImage) object;
        return getString("_UI_WorkspaceImage_type") + " " + workspaceImage.getLabelSize(); //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(WorkspaceImage.class)) {
        case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
        case DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH:
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
