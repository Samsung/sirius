/*******************************************************************************
 * Copyright (c) 2009, 2014 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.sync;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.DiagramPlugin;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.tools.internal.preferences.SiriusDiagramInternalPreferencesKeys;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;

/**
 * A wrapper because the original class should be here.
 * 
 * @see org.eclipse.sirius.diagram.business.internal.experimental.sync.DDiagramSynchronizer
 * @author mchauvin
 */
public class DDiagramSynchronizer extends org.eclipse.sirius.diagram.business.internal.experimental.sync.DDiagramSynchronizer {

    /**
     * Create a new synchronizer.
     * 
     * @param interpreter
     *            expression interpreter.
     * @param description
     *            diagram specification.
     * @param accessor
     *            model access layer.
     */
    public DDiagramSynchronizer(final IInterpreter interpreter, final DiagramDescription description, final ModelAccessor accessor) {
        super(interpreter, description, accessor);
    }

    /**
     * Initialize the diagram to synchronize.
     * 
     * @param name
     *            name of the diagram.
     * @param target
     *            the semantic element corresponding to the diagram root.
     * @param monitor
     *            to track the progress.
     */
    @Override
    public void initDiagram(final String name, final EObject target, final IProgressMonitor monitor) {
        super.initDiagram(name, target, monitor);
        boolean syncOnCreation = Platform.getPreferencesService().getBoolean(DiagramPlugin.ID, SiriusDiagramInternalPreferencesKeys.PREF_SYNCHRONIZE_DIAGRAM_ON_CREATION.name(), false, null);
        getDiagram().setSynchronized(syncOnCreation);
    }

    /**
     * return the diagram.
     * 
     * @return the diagram
     */
    @Override
    public DSemanticDiagram getDiagram() {
        return super.getDiagram();
    }

    /**
     * set a new diagram.
     * 
     * @param diagram
     *            the diagram to set
     */
    @Override
    public void setDiagram(final DSemanticDiagram diagram) {
        super.setDiagram(diagram);
    }

    /**
     * refresh the content.
     * 
     * @param monitor
     *            progress monitor for the processing.
     */
    @Override
    public void refresh(final IProgressMonitor monitor) {
        super.refresh(monitor);
    }

}
