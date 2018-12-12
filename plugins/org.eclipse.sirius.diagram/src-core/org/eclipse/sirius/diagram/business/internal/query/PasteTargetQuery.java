/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.query;

import java.util.Collection;

import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.business.api.query.DiagramDescriptionQuery;
import org.eclipse.sirius.diagram.business.api.query.DiagramElementMappingQuery;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.description.PasteTargetDescription;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.PasteDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * A class aggregating all the queries (read-only!) having a paste target as a
 * starting point.
 * 
 * @author mporhel
 * 
 */
public class PasteTargetQuery {

    private DSemanticDecorator semDec;

    /**
     * Create a new query.
     * 
     * @param decorator
     *            the element to query.
     */
    public PasteTargetQuery(DSemanticDecorator decorator) {
        this.semDec = decorator;
    }

    /**
     * Return all available paste tools for the current decorator, regarding its
     * description.
     * 
     * @return all available paste tools for the specified
     *         PasteTargetDescription.
     */
    public Collection<PasteDescription> getAvailablePasteTools() {
        final Collection<PasteDescription> result = Lists.newArrayList();
        if (semDec instanceof DDiagram) {
            DDiagram diag = (DDiagram) semDec;
            result.addAll(getPasteToolsOnActivatedLayers(diag, diag.getDescription()));
        } else if (semDec instanceof DDiagramElement) {
            DDiagramElement dde = (DDiagramElement) semDec;
            result.addAll(getPasteToolsOnActivatedLayers(dde.getParentDiagram(), dde.getDiagramElementMapping()));
        }
        // No other cases : DDiagram could only handle paste operation on itself
        // or on of its element.
        return result;
    }

    private Collection<PasteDescription> getPasteToolsOnActivatedLayers(final DDiagram dDiagram, final PasteTargetDescription pasteTargetDescription) {
        if (dDiagram.getDescription().getDefaultLayer() != null) {
            final Collection<AbstractToolDescription> allActivatedTools = Sets.newHashSet();
            allActivatedTools.addAll(dDiagram.getDescription().getDefaultLayer().getAllTools());
            for (Layer layer : dDiagram.getActivatedLayers()) {
                allActivatedTools.addAll(layer.getAllTools());
            }
            Collection<PasteDescription> pasteTools = getAllPasteTools(pasteTargetDescription);
            pasteTools.retainAll(allActivatedTools);
            return pasteTools;
        }
        return getAllPasteTools(pasteTargetDescription);
    }

    /**
     * Returns the paste tools of the pasteTargetDescription.
     * 
     * @param pasteTargetDescription
     * @return the paste tools of the pasteTargetDescription
     */
    private Collection<PasteDescription> getAllPasteTools(final PasteTargetDescription pasteTargetDescription) {
        Collection<PasteDescription> pasteTools = Sets.newHashSet();
        if (pasteTargetDescription instanceof DiagramElementMapping) {
            pasteTools = Sets.newHashSet(new DiagramElementMappingQuery((DiagramElementMapping) pasteTargetDescription).getAllPasteTools());
        } else if (pasteTargetDescription instanceof DiagramDescription) {
            pasteTools = Sets.newHashSet(new DiagramDescriptionQuery((DiagramDescription) pasteTargetDescription).getAllPasteTools());
        } else {
            pasteTools = Sets.newHashSet(pasteTargetDescription.getPasteDescriptions());
        }
        return pasteTools;
    }
}
