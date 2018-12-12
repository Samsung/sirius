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
package org.eclipse.sirius.table.metamodel.table.description;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Cell Updater</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.CellUpdater#getDirectEdit
 * <em>Direct Edit</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.CellUpdater#getCanEdit
 * <em>Can Edit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getCellUpdater()
 * @model
 * @generated
 */
public interface CellUpdater extends EObject {
    /**
     * Returns the value of the '<em><b>Direct Edit</b></em>' containment
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Direct Edit</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Direct Edit</em>' containment reference.
     * @see #setDirectEdit(LabelEditTool)
     * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getCellUpdater_DirectEdit()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getDirectEdit();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.table.metamodel.table.description.CellUpdater#getDirectEdit
     * <em>Direct Edit</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Direct Edit</em>' containment
     *            reference.
     * @see #getDirectEdit()
     * @generated
     */
    void setDirectEdit(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>Can Edit</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Can Edit</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Can Edit</em>' attribute.
     * @see #setCanEdit(String)
     * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getCellUpdater_CanEdit()
     * @model dataType="org.eclipse.sirius.description.InterpretedExpression"
     * @generated
     */
    String getCanEdit();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.table.metamodel.table.description.CellUpdater#getCanEdit
     * <em>Can Edit</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Can Edit</em>' attribute.
     * @see #getCanEdit()
     * @generated
     */
    void setCanEdit(String value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation" dataType=
     *        "org.eclipse.sirius.viewpoint.description.InterpretedExpression"
     *        required="true"
     * @generated
     */
    String getLabelComputationExpression();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation"
     * @generated
     */
    CreateCellTool getCreateCell();

} // CellUpdater
