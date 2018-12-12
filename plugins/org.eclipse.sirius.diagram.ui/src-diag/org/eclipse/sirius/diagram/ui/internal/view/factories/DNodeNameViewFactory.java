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
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery;

import com.google.common.collect.Lists;

/**
 * @was-generated
 */
public class DNodeNameViewFactory extends AbstractLabelViewFactory {

    /**
     * @was-generated
     */
    public View createView(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
        Node view = (Node) super.createView(semanticAdapter, containerView, semanticHint, index, persisted, preferencesHint);
        DDiagramElement dDiagramElement = (DDiagramElement) semanticAdapter.getAdapter(DDiagramElement.class);
        view.setVisible(!new DDiagramElementQuery(dDiagramElement).isLabelHidden());
        Location location = (Location) view.getLayoutConstraint();
        IMapMode mapMode = MeasurementUnitHelper.getMapMode(containerView.getDiagram().getMeasurementUnit());
        location.setX(mapMode.DPtoLP(0));
        location.setY(mapMode.DPtoLP(5));
        return view;
    }

    /**
     * @was-generated
     */
    protected List<?> createStyles(View view) {
        return Lists.newArrayList();
    }
}
