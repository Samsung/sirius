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
package org.eclipse.sirius.viewpoint;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Customizable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.viewpoint.Customizable#getCustomFeatures
 * <em>Custom Features</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getCustomizable()
 * @model abstract="true"
 * @generated
 */
public interface Customizable extends EObject {
    /**
     * Returns the value of the '<em><b>Custom Features</b></em>' attribute
     * list. The list contents are of type {@link java.lang.String}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Custom Features</em>' attribute list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Custom Features</em>' attribute list.
     * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getCustomizable_CustomFeatures()
     * @model
     * @generated
     */
    EList<String> getCustomFeatures();

} // Customizable
