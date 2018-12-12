/*******************************************************************************
 * Copyright (c) 2007-2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.table.metamodel.table.description.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage;
import org.eclipse.sirius.table.metamodel.table.description.TableTool;
import org.eclipse.sirius.table.metamodel.table.provider.TableUIPlugin;
import org.eclipse.sirius.ui.tools.api.provider.ItemProviderHelper;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;

import com.google.common.collect.Lists;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.table.metamodel.table.description.TableTool}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TableToolItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    private static final Collection<EClass> TYPES_TO_HIDE = Lists.newArrayList(ToolPackage.Literals.DELETE_VIEW);

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TableToolItemProvider(AdapterFactory adapterFactory) {
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

        }
        return itemPropertyDescriptors;
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
            childrenFeatures.add(DescriptionPackage.Literals.TABLE_TOOL__VARIABLES);
            childrenFeatures.add(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION);
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
     * This returns TableTool.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/TableTool")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_TableTool_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(TableTool.class)) {
        case DescriptionPackage.TABLE_TOOL__VARIABLES:
        case DescriptionPackage.TABLE_TOOL__FIRST_MODEL_OPERATION:
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
     * @not-generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        collectNewChildDescriptorsGen(newChildDescriptors, object);
        ItemProviderHelper.filterChildDescriptorsByType(newChildDescriptors, TableToolItemProvider.TYPES_TO_HIDE);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void collectNewChildDescriptorsGen(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createExternalJavaAction()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createExternalJavaActionCall()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createSetObject()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createUnset()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createMoveElement()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createRemoveElement()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createFor()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createDeleteView()));

        newChildDescriptors.add(createChildParameter(DescriptionPackage.Literals.TABLE_TOOL__FIRST_MODEL_OPERATION, ToolFactory.eINSTANCE.createSwitch()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return TableUIPlugin.INSTANCE;
    }

}
