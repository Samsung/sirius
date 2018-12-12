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
package org.eclipse.sirius.table.metamodel.table.description.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage;
import org.eclipse.sirius.table.metamodel.table.description.TableDescription;
import org.eclipse.sirius.table.metamodel.table.description.TableNavigationDescription;
import org.eclipse.sirius.viewpoint.description.tool.impl.RepresentationNavigationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Table Navigation Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.impl.TableNavigationDescriptionImpl#getTableDescription
 * <em>Table Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableNavigationDescriptionImpl extends RepresentationNavigationDescriptionImpl implements TableNavigationDescription {
    /**
     * The cached value of the '{@link #getTableDescription()
     * <em>Table Description</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTableDescription()
     * @generated
     * @ordered
     */
    protected TableDescription tableDescription;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TableNavigationDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptionPackage.Literals.TABLE_NAVIGATION_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TableDescription getTableDescription() {
        if (tableDescription != null && tableDescription.eIsProxy()) {
            InternalEObject oldTableDescription = (InternalEObject) tableDescription;
            tableDescription = (TableDescription) eResolveProxy(oldTableDescription);
            if (tableDescription != oldTableDescription) {
                if (eNotificationRequired()) {
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION, oldTableDescription, tableDescription));
                }
            }
        }
        return tableDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TableDescription basicGetTableDescription() {
        return tableDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTableDescription(TableDescription newTableDescription) {
        TableDescription oldTableDescription = tableDescription;
        tableDescription = newTableDescription;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION, oldTableDescription, tableDescription));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION:
            if (resolve) {
                return getTableDescription();
            }
            return basicGetTableDescription();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION:
            setTableDescription((TableDescription) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION:
            setTableDescription((TableDescription) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DescriptionPackage.TABLE_NAVIGATION_DESCRIPTION__TABLE_DESCRIPTION:
            return tableDescription != null;
        }
        return super.eIsSet(featureID);
    }

} // TableNavigationDescriptionImpl
