/**
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.htm
 */
package org.eclipse.sirius.tests.rcptt.properties.propertiestests;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see org.eclipse.sirius.tests.rcptt.properties.propertiestests.PropertiestestsPackage
 * @generated
 */
public interface PropertiestestsFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    PropertiestestsFactory eINSTANCE = org.eclipse.sirius.tests.rcptt.properties.propertiestests.impl.PropertiestestsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Test Root</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Test Root</em>'.
     * @generated
     */
    TestRoot createTestRoot();

    /**
     * Returns a new object of class '<em>Test Element</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Test Element</em>'.
     * @generated
     */
    TestElement createTestElement();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    PropertiestestsPackage getPropertiestestsPackage();

} // PropertiestestsFactory
