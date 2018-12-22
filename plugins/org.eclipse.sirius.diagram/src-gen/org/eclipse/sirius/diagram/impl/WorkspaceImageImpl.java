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
package org.eclipse.sirius.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.diagram.ContainerLabelDirection;
import org.eclipse.sirius.diagram.ContainerStyle;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.diagram.WorkspaceImage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Workspace Image</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.impl.WorkspaceImageImpl#getContainerLabelDirection
 * <em>Container Label Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.diagram.impl.WorkspaceImageImpl#getWorkspacePath
 * <em>Workspace Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkspaceImageImpl extends NodeStyleImpl implements WorkspaceImage {
    /**
     * The default value of the '{@link #getContainerLabelDirection()
     * <em>Container Label Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getContainerLabelDirection()
     * @generated
     * @ordered
     */
    protected static final ContainerLabelDirection CONTAINER_LABEL_DIRECTION_EDEFAULT = ContainerLabelDirection.HORIZONTAL;

    /**
     * The cached value of the '{@link #getContainerLabelDirection()
     * <em>Container Label Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getContainerLabelDirection()
     * @generated
     * @ordered
     */
    protected ContainerLabelDirection containerLabelDirection = WorkspaceImageImpl.CONTAINER_LABEL_DIRECTION_EDEFAULT;

    /**
     * The default value of the '{@link #getWorkspacePath() <em>Workspace
     * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getWorkspacePath()
     * @generated
     * @ordered
     */
    protected static final String WORKSPACE_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkspacePath() <em>Workspace
     * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getWorkspacePath()
     * @generated
     * @ordered
     */
    protected String workspacePath = WorkspaceImageImpl.WORKSPACE_PATH_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected WorkspaceImageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.WORKSPACE_IMAGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ContainerLabelDirection getContainerLabelDirection() {
        return containerLabelDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setContainerLabelDirection(ContainerLabelDirection newContainerLabelDirection) {
        ContainerLabelDirection oldContainerLabelDirection = containerLabelDirection;
        containerLabelDirection = newContainerLabelDirection == null ? WorkspaceImageImpl.CONTAINER_LABEL_DIRECTION_EDEFAULT : newContainerLabelDirection;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION, oldContainerLabelDirection, containerLabelDirection));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getWorkspacePath() {
        return workspacePath;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setWorkspacePath(String newWorkspacePath) {
        String oldWorkspacePath = workspacePath;
        workspacePath = newWorkspacePath;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH, oldWorkspacePath, workspacePath));
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
        case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
            return getContainerLabelDirection();
        case DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH:
            return getWorkspacePath();
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
        case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
            setContainerLabelDirection((ContainerLabelDirection) newValue);
            return;
        case DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH:
            setWorkspacePath((String) newValue);
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
        case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
            setContainerLabelDirection(WorkspaceImageImpl.CONTAINER_LABEL_DIRECTION_EDEFAULT);
            return;
        case DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH:
            setWorkspacePath(WorkspaceImageImpl.WORKSPACE_PATH_EDEFAULT);
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
        case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
            return containerLabelDirection != WorkspaceImageImpl.CONTAINER_LABEL_DIRECTION_EDEFAULT;
        case DiagramPackage.WORKSPACE_IMAGE__WORKSPACE_PATH:
            return WorkspaceImageImpl.WORKSPACE_PATH_EDEFAULT == null ? workspacePath != null : !WorkspaceImageImpl.WORKSPACE_PATH_EDEFAULT.equals(workspacePath);
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
        if (baseClass == ContainerStyle.class) {
            switch (derivedFeatureID) {
            case DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION:
                return DiagramPackage.CONTAINER_STYLE__CONTAINER_LABEL_DIRECTION;
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
        if (baseClass == ContainerStyle.class) {
            switch (baseFeatureID) {
            case DiagramPackage.CONTAINER_STYLE__CONTAINER_LABEL_DIRECTION:
                return DiagramPackage.WORKSPACE_IMAGE__CONTAINER_LABEL_DIRECTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (containerLabelDirection: "); //$NON-NLS-1$
        result.append(containerLabelDirection);
        result.append(", workspacePath: "); //$NON-NLS-1$
        result.append(workspacePath);
        result.append(')');
        return result.toString();
    }

} // WorkspaceImageImpl
