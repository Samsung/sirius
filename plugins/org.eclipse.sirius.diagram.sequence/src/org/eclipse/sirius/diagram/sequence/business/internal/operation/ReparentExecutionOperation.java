/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.business.internal.operation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.sequence.Messages;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.AbstractNodeEvent;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.ui.business.internal.operation.AbstractModelChangeOperation;

import com.google.common.base.Preconditions;

/**
 * Update the structure of DNodes and GMF Nodes to reflect that an execution has
 * a new parent event.
 * 
 * @author pcdavid, smonnier, mporhel
 */
public class ReparentExecutionOperation extends AbstractModelChangeOperation<Void> {
    private final AbstractNodeEvent execution;

    private final ISequenceEvent finalParent;

    /**
     * Constructor.
     * 
     * @param execution
     *            the execution whose parent changed.
     * @param finalParent
     *            the new parent event of the execution.
     */
    public ReparentExecutionOperation(AbstractNodeEvent execution, ISequenceEvent finalParent) {
        super(Messages.ReparentExecutionOperation_operationName);
        this.execution = Preconditions.checkNotNull(execution);
        this.finalParent = Preconditions.checkNotNull(finalParent);
        Preconditions.checkArgument(execution.getNotationView().getElement() instanceof DNode);
        Preconditions.checkArgument(finalParent.getNotationView().getElement() instanceof AbstractDNode);
    }

    @Override
    public Void execute() {
        DNode thisSem = (DNode) execution.getNotationView().getElement();
        Node thisNode = (Node) execution.getNotationView();

        DNode newSem = (DNode) finalParent.getNotationView().getElement();
        Node newParentNode = (Node) finalParent.getNotationView();

        // real reconnection ?
        if (validate(thisSem, thisNode, newSem, newParentNode)) {
            @SuppressWarnings("unchecked")
            EList<View> persistedChildren = newParentNode.getPersistedChildren();
            persistedChildren.add(thisNode);
            newSem.getOwnedBorderedNodes().add(thisSem);
        }
        return null;
    }

    private boolean validate(DNode thisSem, Node thisNode, DNode newSem, Node newParentNode) {
        boolean validation = thisSem != null && newSem != null && newParentNode != null;
        validation = validation && !newSem.equals(thisSem.eContainer()) && !newParentNode.equals(thisNode.eContainer());
        return validation;
    }
}
