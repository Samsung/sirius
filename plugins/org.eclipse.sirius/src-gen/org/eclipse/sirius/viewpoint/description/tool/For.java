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
package org.eclipse.sirius.viewpoint.description.tool;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>For</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This operation allows to iterate a list of elements.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.viewpoint.description.tool.For#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.viewpoint.description.tool.For#getIteratorName
 * <em>Iterator Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getFor()
 * @model
 * @generated
 */
public interface For extends ContainerModelOperation {
    /**
     * Returns the value of the '<em><b>Expression</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Expression returning the elements to iterate on. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Expression</em>' attribute.
     * @see #setExpression(String)
     * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getFor_Expression()
     * @model dataType=
     *        "org.eclipse.sirius.viewpoint.description.InterpretedExpression"
     *        required="true" annotation=
     *        "http://www.eclipse.org/emf/2002/GenModel contentassist=''"
     *        annotation=
     *        "http://www.eclipse.org/sirius/interpreted/expression/returnType returnType='a Collection<EObject> or an EObject.'"
     * @generated
     */
    String getExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.tool.For#getExpression
     * <em>Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Expression</em>' attribute.
     * @see #getExpression()
     * @generated
     */
    void setExpression(String value);

    /**
     * Returns the value of the '<em><b>Iterator Name</b></em>' attribute. The
     * default value is <code>"i"</code>. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> On every iteration, the current
     * element will be binded with the given name. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Iterator Name</em>' attribute.
     * @see #setIteratorName(String)
     * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getFor_IteratorName()
     * @model default="i" required="true"
     * @generated
     */
    String getIteratorName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.tool.For#getIteratorName
     * <em>Iterator Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Iterator Name</em>' attribute.
     * @see #getIteratorName()
     * @generated
     */
    void setIteratorName(String value);

} // For
