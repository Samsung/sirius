/*******************************************************************************
 * Copyright (c) 2015, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.internal.edit.parts;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DescriptionCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.editparts.TextEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.tools.internal.ui.SnapToAllDragEditPartsTracker;

import com.google.common.collect.Iterables;

/**
 * A specific TextEditPart to handle the snapToAllShapes.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class SiriusTextEditPart extends TextEditPart {

    /**
     * Default constructor.
     * 
     * @param view
     *            the view controlled by this edit part
     */
    public SiriusTextEditPart(View view) {
        super(view);
    }

    @Override
    public DragTracker getDragTracker(Request request) {
        return new SnapToAllDragEditPartsTracker(this);
    }

    /**
     * Redirect the direct edit request to the
     * {@link DescriptionCompartmentEditPart}.
     * 
     * @param request
     *            the direct edit request
     */
    @Override
    protected void performDirectEditRequest(Request request) {
        Iterable<DescriptionCompartmentEditPart> descriptionCompartmentEditPartsfilter = Iterables.filter(this.getChildren(), DescriptionCompartmentEditPart.class);
        if (Iterables.size(descriptionCompartmentEditPartsfilter) == 1) {
            DescriptionCompartmentEditPart descriptionCompartmentEditPart = Iterables.getOnlyElement(descriptionCompartmentEditPartsfilter);
            descriptionCompartmentEditPart.performRequest(request);
        }
    }
}
