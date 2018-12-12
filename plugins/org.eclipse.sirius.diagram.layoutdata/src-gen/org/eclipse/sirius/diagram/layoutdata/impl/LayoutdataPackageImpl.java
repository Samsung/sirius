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
package org.eclipse.sirius.diagram.layoutdata.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.sirius.diagram.layoutdata.AbstractLayoutData;
import org.eclipse.sirius.diagram.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.diagram.layoutdata.LayoutdataFactory;
import org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage;
import org.eclipse.sirius.diagram.layoutdata.NodeLayoutData;
import org.eclipse.sirius.diagram.layoutdata.Point;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class LayoutdataPackageImpl extends EPackageImpl implements LayoutdataPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass abstractLayoutDataEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeLayoutDataEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeLayoutDataEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pointEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.diagram.layoutdata.LayoutdataPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private LayoutdataPackageImpl() {
        super(LayoutdataPackage.eNS_URI, LayoutdataFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model,
     * and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link LayoutdataPackage#eINSTANCE}
     * when that field is accessed. Clients should not invoke it directly.
     * Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static LayoutdataPackage init() {
        if (LayoutdataPackageImpl.isInited) {
            return (LayoutdataPackage) EPackage.Registry.INSTANCE.getEPackage(LayoutdataPackage.eNS_URI);
        }

        // Obtain or create and register package
        LayoutdataPackageImpl theLayoutdataPackage = (LayoutdataPackageImpl) (EPackage.Registry.INSTANCE.get(LayoutdataPackage.eNS_URI) instanceof LayoutdataPackageImpl
                ? EPackage.Registry.INSTANCE.get(LayoutdataPackage.eNS_URI) : new LayoutdataPackageImpl());

        LayoutdataPackageImpl.isInited = true;

        // Initialize simple dependencies
        NotationPackage.eINSTANCE.eClass();
        ViewpointPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theLayoutdataPackage.createPackageContents();

        // Initialize created meta-data
        theLayoutdataPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theLayoutdataPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(LayoutdataPackage.eNS_URI, theLayoutdataPackage);
        return theLayoutdataPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAbstractLayoutData() {
        return abstractLayoutDataEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractLayoutData_Id() {
        return (EAttribute) abstractLayoutDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractLayoutData_Label() {
        return (EReference) abstractLayoutDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractLayoutData_SiriusStyle() {
        return (EReference) abstractLayoutDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractLayoutData_GmfView() {
        return (EReference) abstractLayoutDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeLayoutData() {
        return nodeLayoutDataEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeLayoutData_Width() {
        return (EAttribute) nodeLayoutDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeLayoutData_Height() {
        return (EAttribute) nodeLayoutDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeLayoutData_Children() {
        return (EReference) nodeLayoutDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeLayoutData_OutgoingEdges() {
        return (EReference) nodeLayoutDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeLayoutData_Location() {
        return (EReference) nodeLayoutDataEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeLayoutData() {
        return edgeLayoutDataEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_SourceTerminal() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_TargetTerminal() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_Routing() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeLayoutData_PointList() {
        return (EReference) edgeLayoutDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeLayoutData_SourceRefPoint() {
        return (EReference) edgeLayoutDataEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeLayoutData_TargetRefPoint() {
        return (EReference) edgeLayoutDataEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_JumpLinkStatus() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_JumpLinkType() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_ReverseJumpLink() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeLayoutData_Smoothness() {
        return (EAttribute) edgeLayoutDataEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPoint() {
        return pointEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPoint_X() {
        return (EAttribute) pointEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPoint_Y() {
        return (EAttribute) pointEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LayoutdataFactory getLayoutdataFactory() {
        return (LayoutdataFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to
     * have no affect on any invocation but its first. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) {
            return;
        }
        isCreated = true;

        // Create classes and their features
        abstractLayoutDataEClass = createEClass(LayoutdataPackage.ABSTRACT_LAYOUT_DATA);
        createEAttribute(abstractLayoutDataEClass, LayoutdataPackage.ABSTRACT_LAYOUT_DATA__ID);
        createEReference(abstractLayoutDataEClass, LayoutdataPackage.ABSTRACT_LAYOUT_DATA__LABEL);
        createEReference(abstractLayoutDataEClass, LayoutdataPackage.ABSTRACT_LAYOUT_DATA__SIRIUS_STYLE);
        createEReference(abstractLayoutDataEClass, LayoutdataPackage.ABSTRACT_LAYOUT_DATA__GMF_VIEW);

        nodeLayoutDataEClass = createEClass(LayoutdataPackage.NODE_LAYOUT_DATA);
        createEAttribute(nodeLayoutDataEClass, LayoutdataPackage.NODE_LAYOUT_DATA__WIDTH);
        createEAttribute(nodeLayoutDataEClass, LayoutdataPackage.NODE_LAYOUT_DATA__HEIGHT);
        createEReference(nodeLayoutDataEClass, LayoutdataPackage.NODE_LAYOUT_DATA__CHILDREN);
        createEReference(nodeLayoutDataEClass, LayoutdataPackage.NODE_LAYOUT_DATA__OUTGOING_EDGES);
        createEReference(nodeLayoutDataEClass, LayoutdataPackage.NODE_LAYOUT_DATA__LOCATION);

        edgeLayoutDataEClass = createEClass(LayoutdataPackage.EDGE_LAYOUT_DATA);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__SOURCE_TERMINAL);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__TARGET_TERMINAL);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__ROUTING);
        createEReference(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__POINT_LIST);
        createEReference(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__SOURCE_REF_POINT);
        createEReference(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__TARGET_REF_POINT);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__JUMP_LINK_STATUS);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__JUMP_LINK_TYPE);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__REVERSE_JUMP_LINK);
        createEAttribute(edgeLayoutDataEClass, LayoutdataPackage.EDGE_LAYOUT_DATA__SMOOTHNESS);

        pointEClass = createEClass(LayoutdataPackage.POINT);
        createEAttribute(pointEClass, LayoutdataPackage.POINT__X);
        createEAttribute(pointEClass, LayoutdataPackage.POINT__Y);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) {
            return;
        }
        isInitialized = true;

        // Initialize package
        setName(LayoutdataPackage.eNAME);
        setNsPrefix(LayoutdataPackage.eNS_PREFIX);
        setNsURI(LayoutdataPackage.eNS_URI);

        // Obtain other dependent packages
        ViewpointPackage theViewpointPackage = (ViewpointPackage) EPackage.Registry.INSTANCE.getEPackage(ViewpointPackage.eNS_URI);
        NotationPackage theNotationPackage = (NotationPackage) EPackage.Registry.INSTANCE.getEPackage(NotationPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        nodeLayoutDataEClass.getESuperTypes().add(this.getAbstractLayoutData());
        edgeLayoutDataEClass.getESuperTypes().add(this.getAbstractLayoutData());

        // Initialize classes and features; add operations and parameters
        initEClass(abstractLayoutDataEClass, AbstractLayoutData.class, "AbstractLayoutData", EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getAbstractLayoutData_Id(), ecorePackage.getEString(), "id", null, 1, 1, AbstractLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
                !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getAbstractLayoutData_Label(), this.getNodeLayoutData(), null, "label", null, 0, 1, AbstractLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getAbstractLayoutData_SiriusStyle(), theViewpointPackage.getStyle(), null, "siriusStyle", null, 1, 1, AbstractLayoutData.class, !EPackageImpl.IS_TRANSIENT, //$NON-NLS-1$
                !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
                EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getAbstractLayoutData_GmfView(), theNotationPackage.getView(), null, "gmfView", null, 1, 1, AbstractLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

        initEClass(nodeLayoutDataEClass, NodeLayoutData.class, "NodeLayoutData", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getNodeLayoutData_Width(), ecorePackage.getEInt(), "width", "-2", 0, 1, NodeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, //$NON-NLS-1$ //$NON-NLS-2$
                !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
                !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getNodeLayoutData_Height(), ecorePackage.getEInt(), "height", "-2", 0, 1, NodeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$ //$NON-NLS-2$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
                !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getNodeLayoutData_Children(), this.getNodeLayoutData(), null, "children", null, 0, -1, NodeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getNodeLayoutData_OutgoingEdges(), this.getEdgeLayoutData(), null, "outgoingEdges", null, 0, -1, NodeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getNodeLayoutData_Location(), this.getPoint(), null, "location", null, 0, 1, NodeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES,
                !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

        initEClass(edgeLayoutDataEClass, EdgeLayoutData.class, "EdgeLayoutData", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getEdgeLayoutData_SourceTerminal(), ecorePackage.getEString(), "sourceTerminal", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
                !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_TargetTerminal(), ecorePackage.getEString(), "targetTerminal", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
                !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_Routing(), ecorePackage.getEInt(), "routing", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
                !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getEdgeLayoutData_PointList(), this.getPoint(), null, "pointList", null, 0, -1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getEdgeLayoutData_SourceRefPoint(), this.getPoint(), null, "sourceRefPoint", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEReference(getEdgeLayoutData_TargetRefPoint(), this.getPoint(), null, "targetRefPoint", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE,
                EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_JumpLinkStatus(), ecorePackage.getEInt(), "jumpLinkStatus", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
                !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_JumpLinkType(), ecorePackage.getEInt(), "jumpLinkType", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID,
                EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_ReverseJumpLink(), ecorePackage.getEBoolean(), "reverseJumpLink", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
                !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getEdgeLayoutData_Smoothness(), ecorePackage.getEInt(), "smoothness", null, 0, 1, EdgeLayoutData.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, //$NON-NLS-1$
                EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID,
                EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

        initEClass(pointEClass, Point.class, "Point", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getPoint_X(), ecorePackage.getEInt(), "x", null, 0, 1, Point.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, //$NON-NLS-1$
                !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
        initEAttribute(getPoint_Y(), ecorePackage.getEInt(), "y", null, 0, 1, Point.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, //$NON-NLS-1$
                !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

        // Create resource
        createResource(LayoutdataPackage.eNS_URI);
    }

} // LayoutdataPackageImpl
