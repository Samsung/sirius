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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Group Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getIdentifier
 * <em>Identifier</em>}</li>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getLabelExpression
 * <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getDomainClass
 * <em>Domain Class</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.properties.GroupDescription#getSemanticCandidateExpression
 * <em>Semantic Candidate Expression</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.properties.GroupDescription#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getControls
 * <em>Controls</em>}</li>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getValidationSet
 * <em>Validation Set</em>}</li>
 * <li>{@link org.eclipse.sirius.properties.GroupDescription#getStyle
 * <em>Style</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.properties.GroupDescription#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription()
 * @model
 * @generated
 */
public interface GroupDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Identifier</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Identifier</em>' attribute.
     * @see #setIdentifier(String)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_Identifier()
     * @model required="true"
     * @generated
     */
    String getIdentifier();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getIdentifier
     * <em>Identifier</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Identifier</em>' attribute.
     * @see #getIdentifier()
     * @generated
     */
    void setIdentifier(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label Expression</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_LabelExpression()
     * @model dataType=
     *        "org.eclipse.sirius.viewpoint.description.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Domain Class</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Domain Class</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Class</em>' attribute.
     * @see #setDomainClass(String)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_DomainClass()
     * @model dataType="org.eclipse.sirius.viewpoint.description.TypeName"
     * @generated
     */
    String getDomainClass();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getDomainClass
     * <em>Domain Class</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Class</em>' attribute.
     * @see #getDomainClass()
     * @generated
     */
    void setDomainClass(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidate Expression</b></em>'
     * attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Semantic Candidate Expression</em>' attribute
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidate Expression</em>'
     *         attribute.
     * @see #setSemanticCandidateExpression(String)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_SemanticCandidateExpression()
     * @model dataType=
     *        "org.eclipse.sirius.viewpoint.description.InterpretedExpression"
     * @generated
     */
    String getSemanticCandidateExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getSemanticCandidateExpression
     * <em>Semantic Candidate Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Candidate Expression</em>'
     *            attribute.
     * @see #getSemanticCandidateExpression()
     * @generated
     */
    void setSemanticCandidateExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>'
     * attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Precondition Expression</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_PreconditionExpression()
     * @model dataType=
     *        "org.eclipse.sirius.viewpoint.description.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>'
     *            attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Controls</b></em>' containment reference
     * list. The list contents are of type
     * {@link org.eclipse.sirius.properties.ControlDescription}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Controls</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Controls</em>' containment reference list.
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_Controls()
     * @model containment="true"
     * @generated
     */
    EList<ControlDescription> getControls();

    /**
     * Returns the value of the '<em><b>Validation Set</b></em>' containment
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Validation Set</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Validation Set</em>' containment reference.
     * @see #setValidationSet(GroupValidationSetDescription)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_ValidationSet()
     * @model containment="true"
     * @generated
     */
    GroupValidationSetDescription getValidationSet();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getValidationSet
     * <em>Validation Set</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Validation Set</em>' containment
     *            reference.
     * @see #getValidationSet()
     * @generated
     */
    void setValidationSet(GroupValidationSetDescription value);

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
     * @see #setStyle(GroupStyle)
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_Style()
     * @model containment="true"
     * @generated
     */
    GroupStyle getStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.properties.GroupDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(GroupStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.eclipse.sirius.properties.GroupConditionalStyle}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Conditional Styles</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment
     *         reference list.
     * @see org.eclipse.sirius.properties.PropertiesPackage#getGroupDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<GroupConditionalStyle> getConditionalStyles();

} // GroupDescription
