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
package org.eclipse.sirius.diagram.business.internal.repair.resource.session.diagram.data;

import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;

/**
 * Behavior to follow for lost element data which need to find corresponding
 * parent container.
 * 
 * @author dlecan
 */
public interface ILostElementDataContainer extends ISimilarLostElementData {

    /**
     * Find corresponding container to the current lost element data in the
     * specified diagram and add the current lost element data in this
     * container.
     * 
     * @param diagram
     *            Diagram where to find container.
     * @param diagramElement
     *            Diagram element to add.
     * @return <code>true</code> if element was added in corresponding
     *         container.
     */
    LostElementDataState addDiagramElementInCorrespondingParentContainer(DDiagram diagram, DDiagramElement diagramElement);

}
