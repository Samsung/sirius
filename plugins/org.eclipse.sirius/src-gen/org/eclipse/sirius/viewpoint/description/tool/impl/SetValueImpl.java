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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.viewpoint.description.tool.SetValue;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Set Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.tool.impl.SetValueImpl#getFeatureName
 * <em>Feature Name</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.tool.impl.SetValueImpl#getValueExpression
 * <em>Value Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SetValueImpl extends ContainerModelOperationImpl implements SetValue {
    /**
     * The default value of the '{@link #getFeatureName() <em>Feature Name</em>}
     * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFeatureName()
     * @generated
     * @ordered
     */
    protected static final String FEATURE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFeatureName() <em>Feature Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFeatureName()
     * @generated
     * @ordered
     */
    protected String featureName = SetValueImpl.FEATURE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getValueExpression()
     * <em>Value Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValueExpression()
     * <em>Value Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected String valueExpression = SetValueImpl.VALUE_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SetValueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ToolPackage.Literals.SET_VALUE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getFeatureName() {
        return featureName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFeatureName(String newFeatureName) {
        String oldFeatureName = featureName;
        featureName = newFeatureName;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ToolPackage.SET_VALUE__FEATURE_NAME, oldFeatureName, featureName));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = valueExpression;
        valueExpression = newValueExpression;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ToolPackage.SET_VALUE__VALUE_EXPRESSION, oldValueExpression, valueExpression));
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
        case ToolPackage.SET_VALUE__FEATURE_NAME:
            return getFeatureName();
        case ToolPackage.SET_VALUE__VALUE_EXPRESSION:
            return getValueExpression();
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
        case ToolPackage.SET_VALUE__FEATURE_NAME:
            setFeatureName((String) newValue);
            return;
        case ToolPackage.SET_VALUE__VALUE_EXPRESSION:
            setValueExpression((String) newValue);
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
        case ToolPackage.SET_VALUE__FEATURE_NAME:
            setFeatureName(SetValueImpl.FEATURE_NAME_EDEFAULT);
            return;
        case ToolPackage.SET_VALUE__VALUE_EXPRESSION:
            setValueExpression(SetValueImpl.VALUE_EXPRESSION_EDEFAULT);
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
        case ToolPackage.SET_VALUE__FEATURE_NAME:
            return SetValueImpl.FEATURE_NAME_EDEFAULT == null ? featureName != null : !SetValueImpl.FEATURE_NAME_EDEFAULT.equals(featureName);
        case ToolPackage.SET_VALUE__VALUE_EXPRESSION:
            return SetValueImpl.VALUE_EXPRESSION_EDEFAULT == null ? valueExpression != null : !SetValueImpl.VALUE_EXPRESSION_EDEFAULT.equals(valueExpression);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (featureName: "); //$NON-NLS-1$
        result.append(featureName);
        result.append(", valueExpression: "); //$NON-NLS-1$
        result.append(valueExpression);
        result.append(')');
        return result.toString();
    }

} // SetValueImpl
