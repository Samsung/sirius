/*******************************************************************************
 * Copyright (c) 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.api.helper.graphicalfilters;

import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.HideFilterHelperImpl;

/**
 * Helper to handle HideFilter.
 * 
 * @author mporhel
 * @since 0.9.0
 */
public interface HideFilterHelper {

    /**
     * The singleton instance of the HideFilterHelper.
     */
    HideFilterHelper INSTANCE = HideFilterHelperImpl.init();

    /**
     * Hide the current element.
     * 
     * @param element
     *            the element to hide
     */
    void hide(final DDiagramElement element);

    /**
     * Reveal the current element.
     * 
     * @param element
     *            the element to reveal
     */
    void reveal(final DDiagramElement element);

    /**
     * Hide the label of the current element.
     * 
     * @param element
     *            the element to hide
     */
    void hideLabel(final DDiagramElement element);

    /**
     * Reveal the label of the current element.
     * 
     * @param element
     *            the element to reveal
     */
    void revealLabel(final DDiagramElement element);
}
