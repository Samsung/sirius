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
 * <em><b>Create Column Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.table.metamodel.table.description.CreateColumnTool#getMapping
 * <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getCreateColumnTool()
 * @model
 * @generated
 */
public interface CreateColumnTool extends CreateTool {
    /**
     * Returns the value of the '<em><b>Mapping</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.eclipse.sirius.table.metamodel.table.description.ElementColumnMapping#getCreate
     * <em>Create</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapping</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Mapping</em>' container reference.
     * @see #setMapping(ElementColumnMapping)
     * @see org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage#getCreateColumnTool_Mapping()
     * @see org.eclipse.sirius.table.metamodel.table.description.ElementColumnMapping#getCreate
     * @model opposite="create" required="true" transient="false"
     * @generated
     */
    ElementColumnMapping getMapping();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.table.metamodel.table.description.CreateColumnTool#getMapping
     * <em>Mapping</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Mapping</em>' container reference.
     * @see #getMapping()
     * @generated
     */
    void setMapping(ElementColumnMapping value);

} // CreateColumnTool
