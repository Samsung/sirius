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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.JumpLinkStatus;
import org.eclipse.gmf.runtime.notation.JumpLinkType;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeRouting;
import org.eclipse.sirius.diagram.EdgeStyle;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DEdgeBeginNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DEdgeEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DEdgeEndNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DEdgeNameEditPart;
import org.eclipse.sirius.diagram.ui.part.SiriusVisualIDRegistry;

import com.google.common.collect.Lists;

/**
 * @was-generated
 */
public class DEdgeViewFactory extends AbstractDesignerEdgeFactory {

    /**
     * @was-generated
     */
    protected List<?> createStyles(final View view) {
        final List<Style> styles = Lists.newArrayList();
        styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        return styles;
    }

    /**
     * @not-generated
     */
    protected void decorateView(final View containerView, final View view, final IAdaptable semanticAdapter, String semanticHint, final int index, final boolean persisted) {
        if (semanticHint == null) {
            semanticHint = SiriusVisualIDRegistry.getType(DEdgeEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);
        IAdaptable eObjectAdapter = null;
        final EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter, view, SiriusVisualIDRegistry.getType(DEdgeNameEditPart.VISUAL_ID), ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(eObjectAdapter, view, SiriusVisualIDRegistry.getType(DEdgeBeginNameEditPart.VISUAL_ID), ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(eObjectAdapter, view, SiriusVisualIDRegistry.getType(DEdgeEndNameEditPart.VISUAL_ID), ViewUtil.APPEND, true, getPreferencesHint());
        if (eObject != null && (((EdgeStyle) ((DEdge) eObject).getStyle()).getRoutingStyle().equals(EdgeRouting.MANHATTAN_LITERAL)))
            setManahattanRoutingStyle(view);
        if (eObject != null && (((EdgeStyle) ((DEdge) eObject).getStyle()).getRoutingStyle().equals(EdgeRouting.TREE_LITERAL)))
            setTreeRoutingStyle(view);

    }

    /**
     * 
     * @not-generated
     */
    private static void setManahattanRoutingStyle(final View view) {
        // changing default routing style to manhattan
        final RoutingStyle rstyle = (RoutingStyle) view.getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
        rstyle.setJumpLinkType(JumpLinkType.get(JumpLinkType.SEMICIRCLE));
        rstyle.setJumpLinkStatus(JumpLinkStatus.get(JumpLinkStatus.NONE));
        rstyle.setRouting(Routing.RECTILINEAR_LITERAL);
    }

    /**
     * 
     * @not-generated
     */
    private static void setTreeRoutingStyle(final View view) {
        // changing default routing style to tree
        final RoutingStyle rstyle = (RoutingStyle) view.getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
        rstyle.setJumpLinkType(JumpLinkType.get(JumpLinkType.SEMICIRCLE));
        rstyle.setJumpLinkStatus(JumpLinkStatus.get(JumpLinkStatus.NONE));
        rstyle.setRouting(Routing.TREE_LITERAL);
    }
}
