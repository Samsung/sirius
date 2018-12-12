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
package org.eclipse.sirius.diagram.sequence.template;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>TConditional Message Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.diagram.sequence.template.TConditionalMessageStyle#getPredicateExpression
 * <em>Predicate Expression</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.sequence.template.TConditionalMessageStyle#getStyle
 * <em>Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.diagram.sequence.template.TemplatePackage#getTConditionalMessageStyle()
 * @model
 * @generated
 */
public interface TConditionalMessageStyle extends TTransformer {
    /**
     * Returns the value of the '<em><b>Predicate Expression</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This expression will get evaluated and if it returns
     * true the contained style will be choosen. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Predicate Expression</em>' attribute.
     * @see #setPredicateExpression(String)
     * @see org.eclipse.sirius.diagram.sequence.template.TemplatePackage#getTConditionalMessageStyle_PredicateExpression()
     * @model dataType="org.eclipse.sirius.description.InterpretedExpression"
     *        required="true" annotation=
     *        "http://www.eclipse.org/sirius/interpreted/expression/returnType returnType='a boolean.'"
     *        annotation=
     *        "http://www.eclipse.org/sirius/interpreted/expression/variables view='the current view.' container='the semantic container.'"
     * @generated
     */
    String getPredicateExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.sequence.template.TConditionalMessageStyle#getPredicateExpression
     * <em>Predicate Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Predicate Expression</em>'
     *            attribute.
     * @see #getPredicateExpression()
     * @generated
     */
    void setPredicateExpression(String value);

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
     * @see #setStyle(TMessageStyle)
     * @see org.eclipse.sirius.diagram.sequence.template.TemplatePackage#getTConditionalMessageStyle_Style()
     * @model containment="true" required="true"
     * @generated
     */
    TMessageStyle getStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.sequence.template.TConditionalMessageStyle#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(TMessageStyle value);

} // TConditionalMessageStyle
