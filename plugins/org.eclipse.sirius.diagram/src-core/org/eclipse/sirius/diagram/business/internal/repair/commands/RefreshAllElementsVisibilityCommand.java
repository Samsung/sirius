/*******************************************************************************
 * Copyright (c) 2007, 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.repair.commands;

import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.business.api.helper.display.DisplayServiceManager;

/**
 * RefreshAllElementsVisibilityRecordingCommand.
 * 
 * @author esteban
 */
public class RefreshAllElementsVisibilityCommand extends IdentityCommand {

    private DDiagram representation;

    /**
     * Default constructor.
     * 
     * @param representation
     *            {@link DDiagram}
     */
    public RefreshAllElementsVisibilityCommand(DDiagram representation) {
        super();
        this.representation = representation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        DisplayServiceManager.INSTANCE.getDisplayService().refreshAllElementsVisibility(representation);
        representation = null;
    }

    /**
     * Overridden to avoid the CommandStack to keep a reference to this command.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return false;
    }
}
