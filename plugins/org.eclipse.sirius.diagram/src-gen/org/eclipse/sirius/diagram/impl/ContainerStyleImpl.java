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
package org.eclipse.sirius.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.diagram.BorderedStyle;
import org.eclipse.sirius.diagram.ContainerStyle;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.diagram.HideLabelCapabilityStyle;
import org.eclipse.sirius.diagram.LineStyle;
import org.eclipse.sirius.viewpoint.DRefreshable;
import org.eclipse.sirius.viewpoint.RGBValues;
import org.eclipse.sirius.viewpoint.Style;
import org.eclipse.sirius.viewpoint.ViewpointFactory;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.description.style.StyleDescription;
import org.eclipse.sirius.viewpoint.impl.LabelStyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Container Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#getDescription
 * <em>Description</em>}</li>
 * <li>{@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#getBorderSize
 * <em>Border Size</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#getBorderSizeComputationExpression
 * <em>Border Size Computation Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#getBorderColor
 * <em>Border Color</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#getBorderLineStyle
 * <em>Border Line Style</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.diagram.impl.ContainerStyleImpl#isHideLabelByDefault
 * <em>Hide Label By Default</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ContainerStyleImpl extends LabelStyleImpl implements ContainerStyle {
    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected StyleDescription description;

    /**
     * The default value of the '{@link #getBorderSize() <em>Border Size</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected static final Integer BORDER_SIZE_EDEFAULT = new Integer(0);

    /**
     * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected Integer borderSize = ContainerStyleImpl.BORDER_SIZE_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderSizeComputationExpression()
     * <em>Border Size Computation Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderSizeComputationExpression()
     * @generated
     * @ordered
     */
    protected static final String BORDER_SIZE_COMPUTATION_EXPRESSION_EDEFAULT = "0"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBorderSizeComputationExpression()
     * <em>Border Size Computation Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderSizeComputationExpression()
     * @generated
     * @ordered
     */
    protected String borderSizeComputationExpression = ContainerStyleImpl.BORDER_SIZE_COMPUTATION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderColor() <em>Border Color</em>}
     * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected static final RGBValues BORDER_COLOR_EDEFAULT = (RGBValues) ViewpointFactory.eINSTANCE.createFromString(ViewpointPackage.eINSTANCE.getRGBValues(), "0,0,0"); //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected RGBValues borderColor = ContainerStyleImpl.BORDER_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderLineStyle()
     * <em>Border Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected static final LineStyle BORDER_LINE_STYLE_EDEFAULT = LineStyle.SOLID_LITERAL;

    /**
     * The cached value of the '{@link #getBorderLineStyle()
     * <em>Border Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected LineStyle borderLineStyle = ContainerStyleImpl.BORDER_LINE_STYLE_EDEFAULT;

    /**
     * The default value of the '{@link #isHideLabelByDefault()
     * <em>Hide Label By Default</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isHideLabelByDefault()
     * @generated
     * @ordered
     */
    protected static final boolean HIDE_LABEL_BY_DEFAULT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isHideLabelByDefault()
     * <em>Hide Label By Default</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isHideLabelByDefault()
     * @generated
     * @ordered
     */
    protected boolean hideLabelByDefault = ContainerStyleImpl.HIDE_LABEL_BY_DEFAULT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainerStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.CONTAINER_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public StyleDescription getDescription() {
        if (description != null && description.eIsProxy()) {
            InternalEObject oldDescription = (InternalEObject) description;
            description = (StyleDescription) eResolveProxy(oldDescription);
            if (description != oldDescription) {
                if (eNotificationRequired()) {
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.CONTAINER_STYLE__DESCRIPTION, oldDescription, description));
                }
            }
        }
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public StyleDescription basicGetDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDescription(StyleDescription newDescription) {
        StyleDescription oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__DESCRIPTION, oldDescription, description));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Integer getBorderSize() {
        return borderSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderSize(Integer newBorderSize) {
        Integer oldBorderSize = borderSize;
        borderSize = newBorderSize;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__BORDER_SIZE, oldBorderSize, borderSize));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getBorderSizeComputationExpression() {
        return borderSizeComputationExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderSizeComputationExpression(String newBorderSizeComputationExpression) {
        String oldBorderSizeComputationExpression = borderSizeComputationExpression;
        borderSizeComputationExpression = newBorderSizeComputationExpression;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION, oldBorderSizeComputationExpression,
                    borderSizeComputationExpression));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RGBValues getBorderColor() {
        return borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderColor(RGBValues newBorderColor) {
        RGBValues oldBorderColor = borderColor;
        borderColor = newBorderColor;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__BORDER_COLOR, oldBorderColor, borderColor));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LineStyle getBorderLineStyle() {
        return borderLineStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderLineStyle(LineStyle newBorderLineStyle) {
        LineStyle oldBorderLineStyle = borderLineStyle;
        borderLineStyle = newBorderLineStyle == null ? ContainerStyleImpl.BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, borderLineStyle));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isHideLabelByDefault() {
        return hideLabelByDefault;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHideLabelByDefault(boolean newHideLabelByDefault) {
        boolean oldHideLabelByDefault = hideLabelByDefault;
        hideLabelByDefault = newHideLabelByDefault;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT, oldHideLabelByDefault, hideLabelByDefault));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void refresh() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DiagramPackage.CONTAINER_STYLE__DESCRIPTION:
            if (resolve) {
                return getDescription();
            }
            return basicGetDescription();
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE:
            return getBorderSize();
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
            return getBorderSizeComputationExpression();
        case DiagramPackage.CONTAINER_STYLE__BORDER_COLOR:
            return getBorderColor();
        case DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE:
            return getBorderLineStyle();
        case DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT:
            return isHideLabelByDefault();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DiagramPackage.CONTAINER_STYLE__DESCRIPTION:
            setDescription((StyleDescription) newValue);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE:
            setBorderSize((Integer) newValue);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
            setBorderSizeComputationExpression((String) newValue);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_COLOR:
            setBorderColor((RGBValues) newValue);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE:
            setBorderLineStyle((LineStyle) newValue);
            return;
        case DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT:
            setHideLabelByDefault((Boolean) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DiagramPackage.CONTAINER_STYLE__DESCRIPTION:
            setDescription((StyleDescription) null);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE:
            setBorderSize(ContainerStyleImpl.BORDER_SIZE_EDEFAULT);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
            setBorderSizeComputationExpression(ContainerStyleImpl.BORDER_SIZE_COMPUTATION_EXPRESSION_EDEFAULT);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_COLOR:
            setBorderColor(ContainerStyleImpl.BORDER_COLOR_EDEFAULT);
            return;
        case DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE:
            setBorderLineStyle(ContainerStyleImpl.BORDER_LINE_STYLE_EDEFAULT);
            return;
        case DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT:
            setHideLabelByDefault(ContainerStyleImpl.HIDE_LABEL_BY_DEFAULT_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DiagramPackage.CONTAINER_STYLE__DESCRIPTION:
            return description != null;
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE:
            return ContainerStyleImpl.BORDER_SIZE_EDEFAULT == null ? borderSize != null : !ContainerStyleImpl.BORDER_SIZE_EDEFAULT.equals(borderSize);
        case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
            return ContainerStyleImpl.BORDER_SIZE_COMPUTATION_EXPRESSION_EDEFAULT == null ? borderSizeComputationExpression != null
                    : !ContainerStyleImpl.BORDER_SIZE_COMPUTATION_EXPRESSION_EDEFAULT.equals(borderSizeComputationExpression);
        case DiagramPackage.CONTAINER_STYLE__BORDER_COLOR:
            return ContainerStyleImpl.BORDER_COLOR_EDEFAULT == null ? borderColor != null : !ContainerStyleImpl.BORDER_COLOR_EDEFAULT.equals(borderColor);
        case DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE:
            return borderLineStyle != ContainerStyleImpl.BORDER_LINE_STYLE_EDEFAULT;
        case DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT:
            return hideLabelByDefault != ContainerStyleImpl.HIDE_LABEL_BY_DEFAULT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == DRefreshable.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == Style.class) {
            switch (derivedFeatureID) {
            case DiagramPackage.CONTAINER_STYLE__DESCRIPTION:
                return ViewpointPackage.STYLE__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == BorderedStyle.class) {
            switch (derivedFeatureID) {
            case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE:
                return DiagramPackage.BORDERED_STYLE__BORDER_SIZE;
            case DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
                return DiagramPackage.BORDERED_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION;
            case DiagramPackage.CONTAINER_STYLE__BORDER_COLOR:
                return DiagramPackage.BORDERED_STYLE__BORDER_COLOR;
            case DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE:
                return DiagramPackage.BORDERED_STYLE__BORDER_LINE_STYLE;
            default:
                return -1;
            }
        }
        if (baseClass == HideLabelCapabilityStyle.class) {
            switch (derivedFeatureID) {
            case DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT:
                return DiagramPackage.HIDE_LABEL_CAPABILITY_STYLE__HIDE_LABEL_BY_DEFAULT;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == DRefreshable.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        if (baseClass == Style.class) {
            switch (baseFeatureID) {
            case ViewpointPackage.STYLE__DESCRIPTION:
                return DiagramPackage.CONTAINER_STYLE__DESCRIPTION;
            default:
                return -1;
            }
        }
        if (baseClass == BorderedStyle.class) {
            switch (baseFeatureID) {
            case DiagramPackage.BORDERED_STYLE__BORDER_SIZE:
                return DiagramPackage.CONTAINER_STYLE__BORDER_SIZE;
            case DiagramPackage.BORDERED_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION:
                return DiagramPackage.CONTAINER_STYLE__BORDER_SIZE_COMPUTATION_EXPRESSION;
            case DiagramPackage.BORDERED_STYLE__BORDER_COLOR:
                return DiagramPackage.CONTAINER_STYLE__BORDER_COLOR;
            case DiagramPackage.BORDERED_STYLE__BORDER_LINE_STYLE:
                return DiagramPackage.CONTAINER_STYLE__BORDER_LINE_STYLE;
            default:
                return -1;
            }
        }
        if (baseClass == HideLabelCapabilityStyle.class) {
            switch (baseFeatureID) {
            case DiagramPackage.HIDE_LABEL_CAPABILITY_STYLE__HIDE_LABEL_BY_DEFAULT:
                return DiagramPackage.CONTAINER_STYLE__HIDE_LABEL_BY_DEFAULT;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (borderSize: "); //$NON-NLS-1$
        result.append(borderSize);
        result.append(", borderSizeComputationExpression: "); //$NON-NLS-1$
        result.append(borderSizeComputationExpression);
        result.append(", borderColor: "); //$NON-NLS-1$
        result.append(borderColor);
        result.append(", borderLineStyle: "); //$NON-NLS-1$
        result.append(borderLineStyle);
        result.append(", hideLabelByDefault: "); //$NON-NLS-1$
        result.append(hideLabelByDefault);
        result.append(')');
        return result.toString();
    }

} // ContainerStyleImpl
