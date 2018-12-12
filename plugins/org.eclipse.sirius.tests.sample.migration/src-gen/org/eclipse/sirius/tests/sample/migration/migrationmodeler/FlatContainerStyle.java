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
package org.eclipse.sirius.tests.sample.migration.migrationmodeler;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Flat Container Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getBackgroundStyle
 * <em>Background Style</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getForegroundColor
 * <em>Foreground Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getFlatContainerStyle()
 * @model
 * @generated
 */
public interface FlatContainerStyle extends ContainerStyle {
    /**
     * Returns the value of the '<em><b>Background Style</b></em>' attribute.
     * The literals are from the enumeration
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.BackgroundStyle}
     * . <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The background style. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Background Style</em>' attribute.
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.BackgroundStyle
     * @see #setBackgroundStyle(BackgroundStyle)
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getFlatContainerStyle_BackgroundStyle()
     * @model required="true"
     * @generated
     */
    BackgroundStyle getBackgroundStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getBackgroundStyle
     * <em>Background Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Style</em>' attribute.
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.BackgroundStyle
     * @see #getBackgroundStyle()
     * @generated
     */
    void setBackgroundStyle(BackgroundStyle value);

    /**
     * Returns the value of the '<em><b>Background Color</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> The background color. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Background Color</em>' containment
     *         reference.
     * @see #setBackgroundColor(Color)
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getFlatContainerStyle_BackgroundColor()
     * @model containment="true"
     * @generated
     */
    Color getBackgroundColor();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getBackgroundColor
     * <em>Background Color</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' containment
     *            reference.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(Color value);

    /**
     * Returns the value of the '<em><b>Foreground Color</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> The foreground color. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Foreground Color</em>' containment
     *         reference.
     * @see #setForegroundColor(Color)
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getFlatContainerStyle_ForegroundColor()
     * @model containment="true"
     * @generated
     */
    Color getForegroundColor();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.FlatContainerStyle#getForegroundColor
     * <em>Foreground Color</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Foreground Color</em>' containment
     *            reference.
     * @see #getForegroundColor()
     * @generated
     */
    void setForegroundColor(Color value);

} // FlatContainerStyle
