/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.tools.api.command.view;

import java.util.Set;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.Messages;
import org.eclipse.sirius.diagram.business.api.helper.graphicalfilters.HideFilterHelper;

/**
 * A simple command to reveal the label of diagram elements.
 * 
 * @author lredor
 * @since 0.9.0
 */
public class RevealDDiagramElementsLabel extends RecordingCommand {

    /**
     * Label for hide many labels.
     */
    public static final String REVEAL_LABELS = Messages.RevealDDiagramElementsLabel_revealLabels;

    /**
     * Label for hide one label.
     */
    public static final String REVEAL_LABEL = Messages.RevealDDiagramElementsLabel_revealLabel;

    /** The viewpoint elements for which we want to reveal th label. */
    private final Set<DDiagramElement> diagramElements;

    /**
     * Create a new {@link RevealDDiagramElementsLabel}.
     * 
     * @param domain
     *            the editing domain.
     * @param diagramElements
     *            the diagram element.
     */
    public RevealDDiagramElementsLabel(final TransactionalEditingDomain domain, final Set<DDiagramElement> diagramElements) {
        super(domain, RevealDDiagramElementsLabel.getLabel(diagramElements));
        this.diagramElements = diagramElements;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
     */
    @Override
    protected void doExecute() {
        for (final DDiagramElement diagramElement : diagramElements) {
            HideFilterHelper.INSTANCE.revealLabel(diagramElement);
        }
    }

    /**
     * Compute label.
     * 
     * @param diagramElements
     *            Elements.
     * @return Label.
     */
    public static String getLabel(final Set<DDiagramElement> diagramElements) {
        String result;
        if (diagramElements != null && diagramElements.size() > 1) {
            result = RevealDDiagramElementsLabel.REVEAL_LABELS;
        } else {
            result = RevealDDiagramElementsLabel.REVEAL_LABEL;
        }
        return result;
    }
}
