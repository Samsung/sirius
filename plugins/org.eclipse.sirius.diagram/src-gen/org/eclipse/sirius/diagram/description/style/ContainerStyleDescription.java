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
package org.eclipse.sirius.diagram.description.style;

import org.eclipse.sirius.diagram.ContainerLabelDirection;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;
import org.eclipse.sirius.viewpoint.description.style.TooltipStyleDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Container Style Description</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> The style of a container. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.description.style.ContainerStyleDescription#isRoundedCorner
 * <em>Rounded Corner</em>}</li>
 * <li>{@link org.eclipse.sirius.diagram.description.style.ContainerStyleDescription#getContainerLabelDirection
 * <em>Container Label Direction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.diagram.description.style.StylePackage#getContainerStyleDescription()
 * @model abstract="true"
 * @generated
 */
public interface ContainerStyleDescription extends RoundedCornerStyleDescription, BorderedStyleDescription, LabelStyleDescription, TooltipStyleDescription, HideLabelCapabilityStyleDescription {
    /**
     * Returns the value of the '<em><b>Rounded Corner</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * <!-- end-model-doc -->
     *
     * @return the value of the '<em>Rounded Corner</em>' attribute.
     * @see #setRoundedCorner(boolean)
     * @see org.eclipse.sirius.diagram.description.style.StylePackage#getContainerStyleDescription_RoundedCorner()
     * @model
     * @generated
     */
    boolean isRoundedCorner();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.diagram.description.style.ContainerStyleDescription#isRoundedCorner
     * <em>Rounded Corner</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Rounded Corner</em>' attribute.
     * @see #isRoundedCorner()
     * @generated
     */
    void setRoundedCorner(boolean value);

    /**
     * Returns the value of the '<em><b>Container Label Direction</b></em>'
     * attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.diagram.ContainerLabelDirection}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Container Label Direction</em>' attribute
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Container Label Direction</em>' attribute.
     * @see org.eclipse.sirius.diagram.ContainerLabelDirection
     * @see #setContainerLabelDirection(ContainerLabelDirection)
     * @see org.eclipse.sirius.diagram.description.style.StylePackage#getContainerStyleDescription_ContainerLabelDirection()
     * @model
     * @generated
     */
    ContainerLabelDirection getContainerLabelDirection();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.diagram.description.style.ContainerStyleDescription#getContainerLabelDirection
     * <em>Container Label Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Container Label Direction</em>'
     *            attribute.
     * @see org.eclipse.sirius.diagram.ContainerLabelDirection
     * @see #getContainerLabelDirection()
     * @generated
     */
    void setContainerLabelDirection(ContainerLabelDirection value);

} // ContainerStyleDescription
