/**
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.properties;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Select Widget Conditional Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.properties.SelectWidgetConditionalStyle#getStyle
 * <em>Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.properties.PropertiesPackage#getSelectWidgetConditionalStyle()
 * @model
 * @generated
 */
public interface SelectWidgetConditionalStyle extends WidgetConditionalStyle {
    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Style</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(SelectWidgetStyle)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getSelectWidgetConditionalStyle_Style()
     * @model containment="true"
     * @generated
     */
    SelectWidgetStyle getStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.SelectWidgetConditionalStyle#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(SelectWidgetStyle value);

} // SelectWidgetConditionalStyle
