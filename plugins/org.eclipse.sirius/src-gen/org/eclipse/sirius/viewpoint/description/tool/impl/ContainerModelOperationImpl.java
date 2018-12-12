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
package org.eclipse.sirius.viewpoint.description.tool.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.viewpoint.description.tool.ContainerModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Container Model Operation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.tool.impl.ContainerModelOperationImpl#getSubModelOperations
 * <em>Sub Model Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ContainerModelOperationImpl extends ModelOperationImpl implements ContainerModelOperation {
    /**
     * The cached value of the '{@link #getSubModelOperations()
     * <em>Sub Model Operations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubModelOperations()
     * @generated
     * @ordered
     */
    protected EList<ModelOperation> subModelOperations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainerModelOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ToolPackage.Literals.CONTAINER_MODEL_OPERATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ModelOperation> getSubModelOperations() {
        if (subModelOperations == null) {
            subModelOperations = new EObjectContainmentEList.Resolving<ModelOperation>(ModelOperation.class, this, ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS);
        }
        return subModelOperations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS:
            return ((InternalEList<?>) getSubModelOperations()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS:
            return getSubModelOperations();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS:
            getSubModelOperations().clear();
            getSubModelOperations().addAll((Collection<? extends ModelOperation>) newValue);
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
        case ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS:
            getSubModelOperations().clear();
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
        case ToolPackage.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS:
            return subModelOperations != null && !subModelOperations.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ContainerModelOperationImpl
