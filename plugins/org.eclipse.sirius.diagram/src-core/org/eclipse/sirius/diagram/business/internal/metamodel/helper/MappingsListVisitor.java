/*******************************************************************************
 * Copyright (c) 2008, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.helper;

import java.util.Set;

import org.eclipse.sirius.diagram.business.internal.experimental.sync.AbstractDNodeCandidate;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;

/**
 * A visitor interface to iterate over a mappings list in the smartest ways for
 * layers.
 * 
 * @author mchauvin
 */
public interface MappingsListVisitor {

    /**
     * update the mapping.
     * 
     * @param mapping
     *            the mapping
     * @param candidateFilter
     *            the candidate elements to ignore for this iteration
     * @return the list of displayed candidate elements by this mapping. The
     *         method must return an empty list, if there is none.
     */
    Set<AbstractDNodeCandidate> visit(final DiagramElementMapping mapping, final Set<AbstractDNodeCandidate> candidateFilter);

}
