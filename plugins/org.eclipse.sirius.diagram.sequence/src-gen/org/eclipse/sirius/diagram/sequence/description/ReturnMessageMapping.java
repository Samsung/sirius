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
package org.eclipse.sirius.diagram.sequence.description;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Return Message Mapping</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.diagram.sequence.description.ReturnMessageMapping#getInvocationMessageFinderExpression
 * <em>Invocation Message Finder Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.diagram.sequence.description.DescriptionPackage#getReturnMessageMapping()
 * @model
 * @generated
 */
public interface ReturnMessageMapping extends MessageMapping {
    /**
     * Returns the value of the '
     * <em><b>Invocation Message Finder Expression</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Invocation Message Finder Expression</em>'
     * attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Invocation Message Finder Expression</em>'
     *         attribute.
     * @see #setInvocationMessageFinderExpression(String)
     * @see org.eclipse.sirius.diagram.sequence.description.DescriptionPackage#getReturnMessageMapping_InvocationMessageFinderExpression()
     * @model dataType="org.eclipse.sirius.description.InterpretedExpression"
     *        required="true"
     * @generated
     */
    String getInvocationMessageFinderExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.sequence.description.ReturnMessageMapping#getInvocationMessageFinderExpression
     * <em>Invocation Message Finder Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Invocation Message Finder Expression</em>' attribute.
     * @see #getInvocationMessageFinderExpression()
     * @generated
     */
    void setInvocationMessageFinderExpression(String value);

} // ReturnMessageMapping
