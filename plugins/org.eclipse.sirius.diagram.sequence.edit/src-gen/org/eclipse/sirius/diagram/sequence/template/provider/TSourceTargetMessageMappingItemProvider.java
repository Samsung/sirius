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
package org.eclipse.sirius.diagram.sequence.template.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.diagram.sequence.template.TSourceTargetMessageMapping;
import org.eclipse.sirius.diagram.sequence.template.TemplatePackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.sequence.template.TSourceTargetMessageMapping}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TSourceTargetMessageMappingItemProvider extends TMessageMappingItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TSourceTargetMessageMappingItemProvider(AdapterFactory adapterFactory) {
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

            addSourcePropertyDescriptor(object);
            addSourceFinderExpressionPropertyDescriptor(object);
            addTargetFinderExpressionPropertyDescriptor(object);
            addUseDomainElementPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Source feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addSourcePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(
                createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_TSourceTargetMessageMapping_source_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_TSourceTargetMessageMapping_source_feature", "_UI_TSourceTargetMessageMapping_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        TemplatePackage.Literals.TSOURCE_TARGET_MESSAGE_MAPPING__SOURCE, true, false, true, null, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This adds a property descriptor for the Source Finder Expression feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addSourceFinderExpressionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_TSourceTargetMessageMapping_sourceFinderExpression_feature"), //$NON-NLS-1$
                getString("_UI_TSourceTargetMessageMapping_sourceFinderExpression_description"), //$NON-NLS-1$
                TemplatePackage.Literals.TSOURCE_TARGET_MESSAGE_MAPPING__SOURCE_FINDER_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This adds a property descriptor for the Target Finder Expression feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTargetFinderExpressionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_TSourceTargetMessageMapping_targetFinderExpression_feature"), //$NON-NLS-1$
                getString("_UI_TSourceTargetMessageMapping_targetFinderExpression_description"), //$NON-NLS-1$
                TemplatePackage.Literals.TSOURCE_TARGET_MESSAGE_MAPPING__TARGET_FINDER_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                null));
    }

    /**
     * This adds a property descriptor for the Use Domain Element feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addUseDomainElementPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_TSourceTargetMessageMapping_useDomainElement_feature"), //$NON-NLS-1$
                getString("_UI_TSourceTargetMessageMapping_useDomainElement_description"), //$NON-NLS-1$
                TemplatePackage.Literals.TSOURCE_TARGET_MESSAGE_MAPPING__USE_DOMAIN_ELEMENT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
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
        String label = ((TSourceTargetMessageMapping) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_TSourceTargetMessageMapping_type") : //$NON-NLS-1$
                getString("_UI_TSourceTargetMessageMapping_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(TSourceTargetMessageMapping.class)) {
        case TemplatePackage.TSOURCE_TARGET_MESSAGE_MAPPING__SOURCE_FINDER_EXPRESSION:
        case TemplatePackage.TSOURCE_TARGET_MESSAGE_MAPPING__TARGET_FINDER_EXPRESSION:
        case TemplatePackage.TSOURCE_TARGET_MESSAGE_MAPPING__USE_DOMAIN_ELEMENT:
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
