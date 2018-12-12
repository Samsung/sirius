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
package org.eclipse.sirius.diagram.sequence.template.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.diagram.sequence.template.TCreationMessageMapping;
import org.eclipse.sirius.diagram.sequence.template.TLifelineMapping;
import org.eclipse.sirius.diagram.sequence.template.TemplatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>TCreation Message Mapping</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.diagram.sequence.template.impl.TCreationMessageMappingImpl#getTarget
 * <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TCreationMessageMappingImpl extends TSourceTargetMessageMappingImpl implements TCreationMessageMapping {
    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected EList<TLifelineMapping> target;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TCreationMessageMappingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TemplatePackage.Literals.TCREATION_MESSAGE_MAPPING;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TLifelineMapping> getTarget() {
        if (target == null) {
            target = new EObjectResolvingEList<TLifelineMapping>(TLifelineMapping.class, this, TemplatePackage.TCREATION_MESSAGE_MAPPING__TARGET);
        }
        return target;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case TemplatePackage.TCREATION_MESSAGE_MAPPING__TARGET:
            return getTarget();
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
        case TemplatePackage.TCREATION_MESSAGE_MAPPING__TARGET:
            getTarget().clear();
            getTarget().addAll((Collection<? extends TLifelineMapping>) newValue);
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
        case TemplatePackage.TCREATION_MESSAGE_MAPPING__TARGET:
            getTarget().clear();
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
        case TemplatePackage.TCREATION_MESSAGE_MAPPING__TARGET:
            return target != null && !target.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // TCreationMessageMappingImpl
