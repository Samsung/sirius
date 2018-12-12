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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.table.metamodel.table.description.CreateTool;
import org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage;
import org.eclipse.sirius.table.metamodel.table.description.TableTool;
import org.eclipse.sirius.table.metamodel.table.description.TableVariable;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.impl.AbstractToolDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Create Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.impl.CreateToolImpl#getVariables
 * <em>Variables</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.impl.CreateToolImpl#getFirstModelOperation
 * <em>First Model Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CreateToolImpl extends AbstractToolDescriptionImpl implements CreateTool {
    /**
     * The cached value of the '{@link #getVariables() <em>Variables</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getVariables()
     * @generated
     * @ordered
     */
    protected EList<TableVariable> variables;

    /**
     * The cached value of the '{@link #getFirstModelOperation()
     * <em>First Model Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFirstModelOperation()
     * @generated
     * @ordered
     */
    protected ModelOperation firstModelOperation;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptionPackage.Literals.CREATE_TOOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TableVariable> getVariables() {
        if (variables == null) {
            variables = new EObjectContainmentEList<TableVariable>(TableVariable.class, this, DescriptionPackage.CREATE_TOOL__VARIABLES);
        }
        return variables;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ModelOperation getFirstModelOperation() {
        return firstModelOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetFirstModelOperation(ModelOperation newFirstModelOperation, NotificationChain msgs) {
        ModelOperation oldFirstModelOperation = firstModelOperation;
        firstModelOperation = newFirstModelOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION, oldFirstModelOperation, newFirstModelOperation);
            if (msgs == null) {
                msgs = notification;
            } else {
                msgs.add(notification);
            }
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFirstModelOperation(ModelOperation newFirstModelOperation) {
        if (newFirstModelOperation != firstModelOperation) {
            NotificationChain msgs = null;
            if (firstModelOperation != null) {
                msgs = ((InternalEObject) firstModelOperation).eInverseRemove(this, InternalEObject.EOPPOSITE_FEATURE_BASE - DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION, null, msgs);
            }
            if (newFirstModelOperation != null) {
                msgs = ((InternalEObject) newFirstModelOperation).eInverseAdd(this, InternalEObject.EOPPOSITE_FEATURE_BASE - DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION, null, msgs);
            }
            msgs = basicSetFirstModelOperation(newFirstModelOperation, msgs);
            if (msgs != null) {
                msgs.dispatch();
            }
        } else if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION, newFirstModelOperation, newFirstModelOperation));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DescriptionPackage.CREATE_TOOL__VARIABLES:
            return ((InternalEList<?>) getVariables()).basicRemove(otherEnd, msgs);
        case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
            return basicSetFirstModelOperation(null, msgs);
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
        case DescriptionPackage.CREATE_TOOL__VARIABLES:
            return getVariables();
        case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
            return getFirstModelOperation();
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
        case DescriptionPackage.CREATE_TOOL__VARIABLES:
            getVariables().clear();
            getVariables().addAll((Collection<? extends TableVariable>) newValue);
            return;
        case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
            setFirstModelOperation((ModelOperation) newValue);
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
        case DescriptionPackage.CREATE_TOOL__VARIABLES:
            getVariables().clear();
            return;
        case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
            setFirstModelOperation((ModelOperation) null);
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
        case DescriptionPackage.CREATE_TOOL__VARIABLES:
            return variables != null && !variables.isEmpty();
        case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
            return firstModelOperation != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == TableTool.class) {
            switch (derivedFeatureID) {
            case DescriptionPackage.CREATE_TOOL__VARIABLES:
                return DescriptionPackage.TABLE_TOOL__VARIABLES;
            case DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION:
                return DescriptionPackage.TABLE_TOOL__FIRST_MODEL_OPERATION;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == TableTool.class) {
            switch (baseFeatureID) {
            case DescriptionPackage.TABLE_TOOL__VARIABLES:
                return DescriptionPackage.CREATE_TOOL__VARIABLES;
            case DescriptionPackage.TABLE_TOOL__FIRST_MODEL_OPERATION:
                return DescriptionPackage.CREATE_TOOL__FIRST_MODEL_OPERATION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // CreateToolImpl
