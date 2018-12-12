/*******************************************************************************
 * Copyright (c) 2010, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tree.business.internal.internal.color;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.color.AbstractColorUpdater;
import org.eclipse.sirius.tree.TreeItemStyle;
import org.eclipse.sirius.viewpoint.RGBValues;
import org.eclipse.sirius.viewpoint.description.ColorDescription;

/**
 * Tree implementation to update the item style colors.
 * 
 * @author nlepine
 * 
 */
public class TreeStyleColorUpdater extends AbstractColorUpdater {

    /**
     * Update the background color, if needed, of a cell style.
     * 
     * @param style
     *            style to update.
     * @param colorDescription
     *            the color description.
     * @param target
     *            the current semantic element.
     */
    public void updateBackgroundColor(final TreeItemStyle style, final ColorDescription colorDescription, final EObject target) {
        final RGBValues values = getRGBValuesFromColorDescription(target, colorDescription);
        if (values != style.getBackgroundColor()) {
            style.setBackgroundColor(values);
        }

    }

    /**
     * Update the foreground color, if needed, of a cell style.
     * 
     * @param style
     *            style to update.
     * @param colorDescription
     *            the color description.
     * @param target
     *            the current semantic element.
     */
    public void updateLabelColor(final TreeItemStyle style, final ColorDescription colorDescription, final EObject target) {
        final RGBValues values = getRGBValuesFromColorDescription(target, colorDescription);
        if (values != style.getLabelColor()) {
            style.setLabelColor(values);
        }
    }
}
