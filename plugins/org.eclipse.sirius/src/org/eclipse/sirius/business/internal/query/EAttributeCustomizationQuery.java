/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.query;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.description.EAttributeCustomization;

/**
 * A query for {@link EAttributeCustomization}.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public class EAttributeCustomizationQuery {

    /** The concern {@link EAttributeCustomization}. */
    protected EAttributeCustomization eAttributeCustomization;

    /**
     * Default constructor.
     * 
     * @param eAttributeCustomization
     *            the {@link EAttributeCustomization} to query
     */
    public EAttributeCustomizationQuery(EAttributeCustomization eAttributeCustomization) {
        this.eAttributeCustomization = eAttributeCustomization;
    }

    /**
     * Tells if the current {@link EAttributeCustomization} is conforms to the
     * specified style description element.
     * 
     * @param eObject
     *            the specified style description element
     * @return true if the current {@link EAttributeCustomization} is conforms,
     *         false else
     */
    public boolean isStyleDescriptionEltConformToEAttributeCustomization(EObject eObject) {
        boolean isStyleDescriptionEltConformToEAttributeCustomization = false;
        String attributeName = eAttributeCustomization.getAttributeName();
        if (attributeName != null && attributeName.length() > 0) {
            isStyleDescriptionEltConformToEAttributeCustomization = eObject.eClass().getEStructuralFeature(attributeName) instanceof EAttribute;
        } else {
            isStyleDescriptionEltConformToEAttributeCustomization = true;
        }
        return isStyleDescriptionEltConformToEAttributeCustomization;
    }
}
