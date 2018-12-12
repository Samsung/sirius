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
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DDiagramEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListViewNodeListCompartment2EditPart;
import org.eclipse.sirius.diagram.ui.part.SiriusVisualIDRegistry;

import com.google.common.collect.Lists;

/**
 * @was-generated
 */
public class DNodeListViewFactory extends AbstractContainerViewFactory {

    /**
     * @was-generated
     */
    protected List<?> createStyles(View view) {
        List<Style> styles = Lists.newArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        return styles;
    }

    /**
     * @was-generated
     */
    protected void decorateView(View containerView, View view, IAdaptable semanticAdapter, String semanticHint, int index, boolean persisted) {
        if (semanticHint == null) {
            semanticHint = SiriusVisualIDRegistry.getType(DNodeListEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);
        if (!DDiagramEditPart.MODEL_ID.equals(SiriusVisualIDRegistry.getModelID(containerView))) {
            EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails().put("modelID", DDiagramEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter, view, SiriusVisualIDRegistry.getType(DNodeListNameEditPart.VISUAL_ID), ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(eObjectAdapter, view, SiriusVisualIDRegistry.getType(DNodeListViewNodeListCompartment2EditPart.VISUAL_ID), ViewUtil.APPEND, true, getPreferencesHint());
        updateLayoutConstraint(view, semanticAdapter);
    }
}
