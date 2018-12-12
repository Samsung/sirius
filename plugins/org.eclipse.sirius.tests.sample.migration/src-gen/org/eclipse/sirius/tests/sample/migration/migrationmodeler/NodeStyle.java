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
 * <em><b>Node Style</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Style of a node. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.NodeStyle#getLabelPosition
 * <em>Label Position</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.NodeStyle#isHideLabelByDefault
 * <em>Hide Label By Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getNodeStyle()
 * @model
 * @generated
 */
public interface NodeStyle extends LabelStyle, BorderedStyle {
    /**
     * Returns the value of the '<em><b>Label Position</b></em>' attribute. The
     * literals are from the enumeration
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.LabelPosition}
     * . <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The position of the label : BORDER : The label is around the node, on the
     * border. NODE : the label is in the node. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Label Position</em>' attribute.
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.LabelPosition
     * @see #setLabelPosition(LabelPosition)
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getNodeStyle_LabelPosition()
     * @model
     * @generated
     */
    LabelPosition getLabelPosition();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.NodeStyle#getLabelPosition
     * <em>Label Position</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Position</em>' attribute.
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.LabelPosition
     * @see #getLabelPosition()
     * @generated
     */
    void setLabelPosition(LabelPosition value);

    /**
     * Returns the value of the '<em><b>Hide Label By Default</b></em>'
     * attribute. The default value is <code>"false"</code>. <!-- begin-user-doc
     * --> <!-- end-user-doc --> <!-- begin-model-doc --> The default visibility
     * of the label (available only if labelPosition equals BORDER). A change of
     * this option does not affect already existing elements. <!-- end-model-doc
     * -->
     *
     * @return the value of the '<em>Hide Label By Default</em>' attribute.
     * @see #setHideLabelByDefault(boolean)
     * @see org.eclipse.sirius.tests.sample.migration.migrationmodeler.MigrationmodelerPackage#getNodeStyle_HideLabelByDefault()
     * @model default="false"
     * @generated
     */
    boolean isHideLabelByDefault();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.tests.sample.migration.migrationmodeler.NodeStyle#isHideLabelByDefault
     * <em>Hide Label By Default</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Hide Label By Default</em>'
     *            attribute.
     * @see #isHideLabelByDefault()
     * @generated
     */
    void setHideLabelByDefault(boolean value);

} // NodeStyle
