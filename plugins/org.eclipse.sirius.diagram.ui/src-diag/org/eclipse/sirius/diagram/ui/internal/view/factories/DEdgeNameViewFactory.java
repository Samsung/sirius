/*******************************************************************************
 * Copyright (c) 2007, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.internal.view.factories;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusLayoutDataManager;
import org.eclipse.sirius.diagram.ui.business.internal.view.EdgeLabelLayoutData;

import com.google.common.collect.Lists;

/**
 * @was-generated
 */
public class DEdgeNameViewFactory extends AbstractLabelViewFactory {

    /**
     * Overridden to create bounds instead of location in order to restore
     * properly the label if it is on multiple lines.
     * 
     * @not-generated
     */
    @Override
    protected LayoutConstraint createLayoutConstraint() {
        return NotationFactory.eINSTANCE.createBounds();
    }

    /**
     * @not-generated
     */
    public View createView(final IAdaptable semanticAdapter, final View containerView, final String semanticHint, final int index, final boolean persisted, final PreferencesHint preferencesHint) {
        final Node view = (Node) super.createView(semanticAdapter, containerView, semanticHint, index, persisted, preferencesHint);
        final Location location = (Location) view.getLayoutConstraint();
        final IMapMode mapMode = MeasurementUnitHelper.getMapMode(containerView.getDiagram().getMeasurementUnit());
        DEdge dEdge = (DEdge) semanticAdapter.getAdapter(DEdge.class);
        view.setVisible(!new DDiagramElementQuery(dEdge).isLabelHidden());
        final EdgeLabelLayoutData data = SiriusLayoutDataManager.INSTANCE.getLabelData(dEdge);

        int x;
        int y;
        if (data == null) {
            x = mapMode.DPtoLP(0);
            y = mapMode.DPtoLP(-10);
        } else {
            final Point labelLocation = data.getLocation();
            x = labelLocation.x;
            y = labelLocation.y;
            final Rectangle labelBounds = data.getLabelBounds();
            if (labelBounds != null && location instanceof Bounds) {
                ((Bounds) location).setWidth(labelBounds.width);
                ((Bounds) location).setHeight(labelBounds.height);
            }
        }
        location.setX(x);
        location.setY(y);

        return view;
    }

    /**
     * @was-generated
     */
    protected List<?> createStyles(final View view) {
        return Lists.newArrayList();
    }
}
