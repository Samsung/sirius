/*******************************************************************************
 * Copyright (c) 2008, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.EdgeMappingImport;
import org.eclipse.sirius.diagram.description.IEdgeMapping;
import org.eclipse.sirius.viewpoint.Style;
import org.eclipse.sirius.viewpoint.description.style.StyleDescription;

/**
 * Helper to create/update diagram elements.
 * 
 * @author ymortier
 */
public final class MappingHelper {

    private BestStyleDescriptionRegistry bestStyleDescriptionRegistry;

    private StyleHelper styleHelper;

    /**
     * Create the helper.
     * 
     * @param interpreter
     *            interpreter used to evaluate expressions.
     */
    public MappingHelper(IInterpreter interpreter) {
        this.bestStyleDescriptionRegistry = new BestStyleDescriptionRegistry(interpreter);
        this.styleHelper = new StyleHelper(interpreter);
    }

    /**
     * Affect the style to the specified diagram element with the given mapping,
     * semantic element, view and container and refresh it.
     * 
     * @param mapping
     *            the mapping.
     * @param diagramElement
     *            the diagram element.
     * @param semanticElement
     *            the semantic element of the diagram element.
     * @param containerVariable
     *            the semantic element of the container of the view.
     * @param parentDiagram
     *            the parent diagram of the edge
     */
    public void affectAndRefreshStyle(final DiagramElementMapping mapping, final DDiagramElement diagramElement, final EObject semanticElement, final EObject containerVariable,
            final DDiagram parentDiagram) {
        final Style currentStyle = diagramElement.getStyle();
        final Style bestStyle = getBestStyle(mapping, semanticElement, diagramElement, containerVariable, parentDiagram);

        this.styleHelper.setAndRefreshStyle(diagramElement, currentStyle, bestStyle);
    }

    /**
     * Gets the best style to use.
     * 
     * @param mapping
     *            the mapping.
     * @param modelElement
     *            the semantic element.
     * @param viewVariable
     *            the view variable.
     * @param containerVariable
     *            the semantic container variable.
     * @param parentDiagram
     *            the parent diagram of the edge
     * @return the best style to use.
     */
    public Style getBestStyle(final DiagramElementMapping mapping, final EObject modelElement, final EObject viewVariable, final EObject containerVariable, final DDiagram parentDiagram) {
        final StyleDescription description = getBestStyleDescription(mapping, modelElement, viewVariable, containerVariable, parentDiagram);
        Style result = null;
        if (description != null) {
            result = this.styleHelper.createStyle(description);
        }
        return result;
    }

    /**
     * Returns the best style description to use for the given mapping.
     * 
     * @param mapping
     *            the mapping.
     * @param modelElement
     *            the semantic element.
     * @param viewVariable
     *            the view variable.
     * @param containerVariable
     *            the semantic container variable.
     * @param parentDiagram
     *            the parent diagram of the edge
     * @return the best style description to use for the given mapping.
     */
    public StyleDescription getBestStyleDescription(final DiagramElementMapping mapping, final EObject modelElement, final EObject viewVariable, final EObject containerVariable,
            final DDiagram parentDiagram) {
        BestStyleDescriptionKey bestStyleDescriptionKey = new BestStyleDescriptionKey(mapping, modelElement, viewVariable, containerVariable, parentDiagram);
        StyleDescription result = bestStyleDescriptionRegistry.get(bestStyleDescriptionKey);
        return result;
    }

    /**
     * Returns the default style description of the given mapping.
     * 
     * @param mapping
     *            the mapping.
     * @return the default style description of the given mapping.
     */
    public static StyleDescription getDefaultStyleDescription(final DiagramElementMapping mapping) {
        return new GetDefaultStyle().doSwitch(mapping);
    }

    /**
     * Return the edgeMapping imported by this edgeMappingImport.
     * 
     * @param edgeMappingImport
     *            The edgeMappingImport
     * @return the edgeMapping imported by this edgeMappingImport
     */
    public static EdgeMapping getEdgeMapping(final EdgeMappingImport edgeMappingImport) {
        EdgeMapping result = null;
        final IEdgeMapping iEdgeMapping = edgeMappingImport.getImportedMapping();
        if (iEdgeMapping instanceof EdgeMapping) {
            result = (EdgeMapping) iEdgeMapping;
        } else if (iEdgeMapping instanceof EdgeMappingImport) {
            result = MappingHelper.getEdgeMapping((EdgeMappingImport) iEdgeMapping);
        }
        return result;
    }
}
