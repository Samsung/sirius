/*******************************************************************************
 * Copyright (c) 2007-2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.description.tool.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.diagram.sequence.description.DescriptionFactory;
import org.eclipse.sirius.diagram.sequence.description.provider.SequenceEditPlugin;
import org.eclipse.sirius.diagram.sequence.description.tool.ReorderTool;
import org.eclipse.sirius.diagram.sequence.description.tool.ToolPackage;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.viewpoint.description.tool.provider.AbstractToolDescriptionItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.sequence.description.tool.ReorderTool}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ReorderToolItemProvider extends AbstractToolDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ReorderToolItemProvider(AdapterFactory adapterFactory) {
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

            addMappingsPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Mappings feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addMappingsPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_ReorderTool_mappings_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ReorderTool_mappings_feature", "_UI_ReorderTool_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        ToolPackage.Literals.REORDER_TOOL__MAPPINGS, true, false, true, null, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_BEFORE);
            childrenFeatures.add(ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_AFTER);
            childrenFeatures.add(ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_BEFORE);
            childrenFeatures.add(ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_AFTER);
            childrenFeatures.add(ToolPackage.Literals.REORDER_TOOL__ON_EVENT_MOVED_OPERATION);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns ReorderTool.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ReorderTool")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @not-generated
     */
    @Override
    public String getText(Object object) {
        String label = new IdentifiedElementQuery((IdentifiedElement) object).getLabel();
        return label == null || label.length() == 0 ? getString("_UI_ReorderTool_type") : getString("_UI_ReorderTool_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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

        switch (notification.getFeatureID(ReorderTool.class)) {
        case ToolPackage.REORDER_TOOL__STARTING_END_PREDECESSOR_BEFORE:
        case ToolPackage.REORDER_TOOL__STARTING_END_PREDECESSOR_AFTER:
        case ToolPackage.REORDER_TOOL__FINISHING_END_PREDECESSOR_BEFORE:
        case ToolPackage.REORDER_TOOL__FINISHING_END_PREDECESSOR_AFTER:
        case ToolPackage.REORDER_TOOL__ON_EVENT_MOVED_OPERATION:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_BEFORE, DescriptionFactory.eINSTANCE.createMessageEndVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_AFTER, DescriptionFactory.eINSTANCE.createMessageEndVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_BEFORE, DescriptionFactory.eINSTANCE.createMessageEndVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_AFTER, DescriptionFactory.eINSTANCE.createMessageEndVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.REORDER_TOOL__ON_EVENT_MOVED_OPERATION, ToolFactory.eINSTANCE.createInitialOperation()));
    }

    /**
     * This returns the label text for
     * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_BEFORE || childFeature == ToolPackage.Literals.REORDER_TOOL__STARTING_END_PREDECESSOR_AFTER
                || childFeature == ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_BEFORE || childFeature == ToolPackage.Literals.REORDER_TOOL__FINISHING_END_PREDECESSOR_AFTER;

        if (qualify) {
            return getString("_UI_CreateChild_text2", //$NON-NLS-1$
                    new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return SequenceEditPlugin.INSTANCE;
    }

}
