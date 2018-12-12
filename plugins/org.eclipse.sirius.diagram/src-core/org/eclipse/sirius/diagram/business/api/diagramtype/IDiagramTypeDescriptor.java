/*******************************************************************************
 * Copyright (c) 2009, 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.api.diagramtype;

/**
 * Describes a diagram type.
 * 
 * @author ymortier
 */
public interface IDiagramTypeDescriptor {

    /**
     * Returns the provider of diagram description.
     * 
     * @return the provider of diagram description.
     */
    IDiagramDescriptionProvider getDiagramDescriptionProvider();

    /**
     * Returns the label of this diagram type.
     * 
     * @return the label of this diagram type.
     */
    String getLabel();

}
