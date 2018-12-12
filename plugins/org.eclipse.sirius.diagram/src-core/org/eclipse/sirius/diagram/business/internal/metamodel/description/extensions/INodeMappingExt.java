/*******************************************************************************
 * Copyright (c) 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.extensions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.util.EObjectCouple;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

/**
 * Extension to the ContainerMapping interface used by the actual
 * implementation.
 * 
 * @author pcdavid
 */
public interface INodeMappingExt extends NodeMapping {
    /**
     * Returns the 'viewNodesDone' map for this mapping.
     * 
     * @return the 'viewNodesDone' map for this mapping.
     */
    Map<EObject, EList<DSemanticDecorator>> getViewNodesDone();

    /**
     * Return the candidates cache for this mapping.
     * 
     * @return the candidates cache for this mapping.
     */
    Map<EObjectCouple, EList<EObject>> getCandidatesCache();

    /**
     * Create the bordering nodes of this mapping.
     * 
     * @param modelElement
     *            the semantic element.
     * @param vpElement
     *            the viewpoint element.
     * @param filterSemantic
     *            a collection of already managed elements.
     * @param viewPoint
     *            the parent {@link DDiagram}.
     * 
     */
    void createBorderingNodes(EObject modelElement, DDiagramElement vpElement, Collection filterSemantic, DDiagram viewPoint);
}
