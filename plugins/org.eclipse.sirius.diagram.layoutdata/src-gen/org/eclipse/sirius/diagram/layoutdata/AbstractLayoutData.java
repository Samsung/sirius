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
package org.eclipse.sirius.diagram.layoutdata;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.viewpoint.Style;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Abstract Layout Data</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> An astract class for all layout data. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getId
 * <em>Id</em>}</li>
 * <li>{@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getLabel
 * <em>Label</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getSiriusStyle
 * <em>Sirius Style</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getGmfView
 * <em>Gmf View</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#getAbstractLayoutData()
 * @model abstract="true"
 * @generated
 */
public interface AbstractLayoutData extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Serialization of the ID of the associated graphical element <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#getAbstractLayoutData_Id()
     * @model id="true" required="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getId
     * <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Represents the layout of the label of this edge (only the location is
     * used). <!-- end-model-doc -->
     *
     * @return the value of the '<em>Label</em>' containment reference.
     * @see #setLabel(NodeLayoutData)
     * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#getAbstractLayoutData_Label()
     * @model containment="true" resolveProxies="true"
     * @generated
     */
    NodeLayoutData getLabel();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getLabel
     * <em>Label</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label</em>' containment reference.
     * @see #getLabel()
     * @generated
     */
    void setLabel(NodeLayoutData value);

    /**
     * Returns the value of the '<em><b>Sirius Style</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> A copy of the original Sirius style of this
     * DDiagramElement. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Sirius Style</em>' containment reference.
     * @see #setSiriusStyle(Style)
     * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#getAbstractLayoutData_SiriusStyle()
     * @model containment="true" resolveProxies="true" required="true"
     * @generated
     */
    Style getSiriusStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getSiriusStyle
     * <em>Sirius Style</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Sirius Style</em>' containment
     *            reference.
     * @see #getSiriusStyle()
     * @generated
     */
    void setSiriusStyle(Style value);

    /**
     * Returns the value of the '<em><b>Gmf View</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> A copy of the original GMF View corresponding to this
     * DDiagramElement (without copying the element reference). <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Gmf View</em>' containment reference.
     * @see #setGmfView(View)
     * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#getAbstractLayoutData_GmfView()
     * @model containment="true" resolveProxies="true" required="true"
     * @generated
     */
    View getGmfView();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData#getGmfView
     * <em>Gmf View</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Gmf View</em>' containment
     *            reference.
     * @see #getGmfView()
     * @generated
     */
    void setGmfView(View value);

} // AbstractLayoutData
