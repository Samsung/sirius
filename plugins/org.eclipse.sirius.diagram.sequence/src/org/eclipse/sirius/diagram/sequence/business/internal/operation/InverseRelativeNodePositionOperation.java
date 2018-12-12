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

import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.sirius.diagram.sequence.Messages;
import org.eclipse.sirius.diagram.ui.business.internal.operation.AbstractModelChangeOperation;

import com.google.common.base.Preconditions;

/**
 * An operation to inverse the relative position of a node on both axes. This is
 * used for labels on messages to make sure they are always above the message,
 * regardless of its direction (left-to-right or right-to-left).
 * 
 * @author pcdavid, smonnier
 */
public class InverseRelativeNodePositionOperation extends AbstractModelChangeOperation<Void> {
    private final Node node;

    /**
     * Constructor.
     * 
     * @param node
     *            the node whose position to inverse.
     */
    public InverseRelativeNodePositionOperation(Node node) {
        super(Messages.InverseRelativeNodePositionOperation_operationName);
        this.node = Preconditions.checkNotNull(node);
    }

    @Override
    public Void execute() {
        if (node.getLayoutConstraint() instanceof Location) {
            Location l = (Location) node.getLayoutConstraint();
            l.setY(-l.getY());
            l.setX(-l.getX());
        }
        return null;
    }

}
