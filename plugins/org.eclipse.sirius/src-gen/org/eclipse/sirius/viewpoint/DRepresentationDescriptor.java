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
package org.eclipse.sirius.viewpoint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>DRepresentation Descriptor</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getDescription
 * <em>Description</em>}</li>
 * <li>{@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getTarget
 * <em>Target</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getRepresentation
 * <em>Representation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDRepresentationDescriptor()
 * @model
 * @generated
 */
public interface DRepresentationDescriptor extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default
     * value is <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> The name of the representation. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDRepresentationDescriptor_Name()
     * @model default=""
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * description of the representation targeted by this descriptor. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Description</em>' reference.
     * @see #setDescription(RepresentationDescription)
     * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDRepresentationDescriptor_Description()
     * @model required="true"
     * @generated
     */
    RepresentationDescription getDescription();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getDescription
     * <em>Description</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description</em>' reference.
     * @see #getDescription()
     * @generated
     */
    void setDescription(RepresentationDescription value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * referenced EObject. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Target</em>' reference.
     * @see #setTarget(EObject)
     * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDRepresentationDescriptor_Target()
     * @model required="true"
     * @generated
     */
    EObject getTarget();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getTarget
     * <em>Target</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Target</em>' reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(EObject value);

    /**
     * Returns the value of the '<em><b>Representation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * representation targeted by this descriptor. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Representation</em>' reference.
     * @see #setRepresentation(DRepresentation)
     * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDRepresentationDescriptor_Representation()
     * @model required="true"
     * @generated
     */
    DRepresentation getRepresentation();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DRepresentationDescriptor#getRepresentation
     * <em>Representation</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Representation</em>' reference.
     * @see #getRepresentation()
     * @generated
     */
    void setRepresentation(DRepresentation value);

} // DRepresentationDescriptor
