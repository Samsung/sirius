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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.business.api.query.ColorQuery;
import org.eclipse.sirius.diagram.description.style.ShapeContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.description.style.ShapeContainerStyleDescription}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ShapeContainerStyleDescriptionItemProvider extends ContainerStyleDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ShapeContainerStyleDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            addWidthComputationExpressionPropertyDescriptor(object);
            addHeightComputationExpressionPropertyDescriptor(object);
            addShapePropertyDescriptor(object);
            addBackgroundColorPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Width Computation Expression
     * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addWidthComputationExpressionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_SizeComputationContainerStyleDescription_widthComputationExpression_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_SizeComputationContainerStyleDescription_widthComputationExpression_feature", "_UI_SizeComputationContainerStyleDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                StylePackage.Literals.SIZE_COMPUTATION_CONTAINER_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                getString("_UI_AdvancedPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This adds a property descriptor for the Height Computation Expression
     * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addHeightComputationExpressionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_SizeComputationContainerStyleDescription_heightComputationExpression_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_SizeComputationContainerStyleDescription_heightComputationExpression_feature", //$NON-NLS-1$//$NON-NLS-2$
                        "_UI_SizeComputationContainerStyleDescription_type"), //$NON-NLS-1$
                StylePackage.Literals.SIZE_COMPUTATION_CONTAINER_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                getString("_UI_AdvancedPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This adds a property descriptor for the Shape feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addShapePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_ShapeContainerStyleDescription_shape_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ShapeContainerStyleDescription_shape_feature", "_UI_ShapeContainerStyleDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        StylePackage.Literals.SHAPE_CONTAINER_STYLE_DESCRIPTION__SHAPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This adds a property descriptor for the Background Color feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundColorPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ShapeContainerStyleDescription_backgroundColor_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_ShapeContainerStyleDescription_backgroundColor_feature", "_UI_ShapeContainerStyleDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                StylePackage.Literals.SHAPE_CONTAINER_STYLE_DESCRIPTION__BACKGROUND_COLOR, true, false, false, null, getString("_UI_ColorPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This returns ShapeContainerStyleDescription.gif. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ShapeContainerStyleDescription")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @not-generated
     */
    @Override
    public String getText(Object object) {

        String color = new ColorQuery(((ShapeContainerStyleDescription) object).getBackgroundColor()).getLabel();
        String label = getString("_UI_ShapeContainerStyleDescription_type"); //$NON-NLS-1$

        if (color != null) {
            return label + " " + color; //$NON-NLS-1$
        } else {
            return label;
        }
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

        switch (notification.getFeatureID(ShapeContainerStyleDescription.class)) {
        case StylePackage.SHAPE_CONTAINER_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION:
        case StylePackage.SHAPE_CONTAINER_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION:
        case StylePackage.SHAPE_CONTAINER_STYLE_DESCRIPTION__SHAPE:
        case StylePackage.SHAPE_CONTAINER_STYLE_DESCRIPTION__BACKGROUND_COLOR:
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
