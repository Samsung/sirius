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
package org.eclipse.sirius.diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Resize Kind</b></em>', and utility methods for working with them. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.sirius.diagram.DiagramPackage#getResizeKind()
 * @model
 * @generated
 */
public enum ResizeKind implements Enumerator {
    /**
     * The '<em><b>NONE</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #NONE
     * @generated
     * @ordered
     */
    NONE_LITERAL(0, "NONE", "NONE"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NSEW</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #NSEW
     * @generated
     * @ordered
     */
    NSEW_LITERAL(1, "NSEW", "NSEW"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NORTH SOUTH</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #NORTH_SOUTH
     * @generated
     * @ordered
     */
    NORTH_SOUTH_LITERAL(2, "NORTH_SOUTH", "NORTH_SOUTH"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EAST WEST</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #EAST_WEST
     * @generated
     * @ordered
     */
    EAST_WEST_LITERAL(3, "EAST_WEST", "EAST_WEST"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NONE</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #NONE_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NONE = 0;

    /**
     * The '<em><b>NSEW</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NSEW</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #NSEW_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NSEW = 1;

    /**
     * The '<em><b>NORTH SOUTH</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NORTH SOUTH</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #NORTH_SOUTH_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NORTH_SOUTH = 2;

    /**
     * The '<em><b>EAST WEST</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EAST WEST</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #EAST_WEST_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int EAST_WEST = 3;

    /**
     * An array of all the '<em><b>Resize Kind</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final ResizeKind[] VALUES_ARRAY = new ResizeKind[] { NONE_LITERAL, NSEW_LITERAL, NORTH_SOUTH_LITERAL, EAST_WEST_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Resize Kind</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<ResizeKind> VALUES = Collections.unmodifiableList(Arrays.asList(ResizeKind.VALUES_ARRAY));

    /**
     * Returns the '<em><b>Resize Kind</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResizeKind get(String literal) {
        for (ResizeKind result : ResizeKind.VALUES_ARRAY) {
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resize Kind</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResizeKind getByName(String name) {
        for (ResizeKind result : ResizeKind.VALUES_ARRAY) {
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resize Kind</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResizeKind get(int value) {
        switch (value) {
        case NONE:
            return NONE_LITERAL;
        case NSEW:
            return NSEW_LITERAL;
        case NORTH_SOUTH:
            return NORTH_SOUTH_LITERAL;
        case EAST_WEST:
            return EAST_WEST_LITERAL;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private ResizeKind(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string
     * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // ResizeKind
