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
package org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.tools;

import java.util.Map;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.SelectConnectionEditPartTracker;
import org.eclipse.sirius.diagram.sequence.business.internal.query.ISequenceEventQuery;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.SequenceMessageEditPart;
import org.eclipse.sirius.diagram.sequence.util.Range;
import org.eclipse.sirius.ext.gmf.runtime.editparts.GraphicalHelper;

/**
 * Specific connection selection tracker to handle move of messageToSelf
 * messages.
 * 
 * @author mporhel
 * 
 */
@SuppressWarnings("restriction")
public class SequenceMessageSelectConnectionEditPartTracker extends SelectConnectionEditPartTracker implements DragTracker {

    private boolean fromTop = true;

    private BendpointRequest bendpointRequest;

    private boolean msgToSelfMove;

    /**
     * Method SequenceMessageSelectConnectionEditPartTracker.
     * 
     * @param owner
     *            ConnectionNodeEditPart that creates and owns the tracker
     *            object
     */
    public SequenceMessageSelectConnectionEditPartTracker(ConnectionEditPart owner) {
        super(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Request createSourceRequest() {
        Request rq = super.createSourceRequest();
        if (rq instanceof BendpointRequest) {
            bendpointRequest = (BendpointRequest) rq;
        }
        return rq;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateSourceRequest() {
        super.updateSourceRequest();
        if (bendpointRequest != null) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> extData = bendpointRequest.getExtendedData();
            if (msgToSelfMove) {
                extData.put(SequenceMessageEditPart.MSG_TO_SELF_TOP_MOVE, fromTop);
            } else {
                extData.remove(SequenceMessageEditPart.MSG_TO_SELF_TOP_MOVE);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean handleButtonDown(int button) {
        boolean res = super.handleButtonDown(button);
        SequenceMessageEditPart smep = (SequenceMessageEditPart) getSourceEditPart();
        if (new ISequenceEventQuery(smep.getISequenceEvent()).isReflectiveMessage()) {
            Range range = smep.getISequenceEvent().getVerticalRange();
            Point location = getLocation().getCopy();
            GraphicalHelper.screen2logical(location, smep);

            Connection connection = smep.getConnectionFigure();

            int x = connection.getPoints().getMidpoint().x;
            if (x == location.x) {
                msgToSelfMove = false;
            } else {
                fromTop = location.y <= range.getLowerBound() || location.y < range.middleValue();
                msgToSelfMove = true;
            }
        }
        return res;
    }
}
