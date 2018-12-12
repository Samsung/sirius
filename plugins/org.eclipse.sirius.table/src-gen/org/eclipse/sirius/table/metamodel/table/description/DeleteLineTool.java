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

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Delete Line Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.DeleteLineTool#getMapping
 * <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getDeleteLineTool()
 * @model
 * @generated
 */
public interface DeleteLineTool extends DeleteTool {
    /**
     * Returns the value of the '<em><b>Mapping</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.eclipse.sirius.table.metamodel.table.description.LineMapping#getDelete
     * <em>Delete</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapping</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Mapping</em>' container reference.
     * @see #setMapping(LineMapping)
     * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getDeleteLineTool_Mapping()
     * @see org.eclipse.sirius.table.metamodel.table.description.LineMapping#getDelete
     * @model opposite="delete" required="true" transient="false"
     * @generated
     */
    LineMapping getMapping();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.table.metamodel.table.description.DeleteLineTool#getMapping
     * <em>Mapping</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Mapping</em>' container reference.
     * @see #getMapping()
     * @generated
     */
    void setMapping(LineMapping value);

} // DeleteLineTool
