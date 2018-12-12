/*******************************************************************************
 * Copyright (c) 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.sirius.diagram.ui.tools.internal.routers;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramEdgeEditPart.ViewEdgeFigure;

/**
 * A Sirius specific Rectilinear Router to keep edges centered.
 * 
 * @author Florian Barbin
 *
 */
@SuppressWarnings("restriction")
public class SiriusRectilinearRouter extends RectilinearRouter {

    @Override
    public void routeLine(Connection conn, int nestedRoutingDepth, PointList newLine) {
        super.routeLine(conn, nestedRoutingDepth, newLine);

        // if the edge is currently reconnected for instance, we do not perform
        // the centering.
        if (!isReorienting(conn)) {
            handleEdgeCentering(conn, newLine);
        }
    }

    /**
     * Handle the edge centering in the case where at least one end is centered.
     * 
     * @param conn
     *            the connection figure.
     * @param newLine
     *            the current routed point list.
     */
    private void handleEdgeCentering(Connection conn, PointList newLine) {
        if (conn instanceof ViewEdgeFigure) {
            if (((ViewEdgeFigure) conn).isSourceCentered() || ((ViewEdgeFigure) conn).isTargetCentered()) {
                Point srcRefPoint = null;
                Point tgtRefPoint = null;

                // if the source is centered, we compute the source figure
                // center since the source anchor could not be centered.
                if (((ViewEdgeFigure) conn).isSourceCentered()) {
                    srcRefPoint = getAnchorOwnerCenter(conn.getSourceAnchor());
                }

                // if the target is centered, we compute the target figure
                // center since the target anchor could not be centered.
                if (((ViewEdgeFigure) conn).isTargetCentered()) {
                    tgtRefPoint = getAnchorOwnerCenter(conn.getTargetAnchor());
                }

                if (srcRefPoint == null) {
                    ConnectionAnchor srcAnchor = conn.getSourceAnchor();
                    srcRefPoint = srcAnchor.getReferencePoint();
                }

                if (tgtRefPoint == null) {
                    ConnectionAnchor tgtAnchor = conn.getTargetAnchor();
                    tgtRefPoint = tgtAnchor.getReferencePoint();
                }
                // We translate the source and target anchor into the connection
                // coordinate system which is absolute.

                conn.translateToRelative(srcRefPoint);
                conn.translateToRelative(tgtRefPoint);

                RectilinearEdgeUtil.centerEdgeEnds(newLine, srcRefPoint, tgtRefPoint, ((ViewEdgeFigure) conn).getCenteringStyle());

            }
        }
    }

    private Point getAnchorOwnerCenter(ConnectionAnchor anchor) {
        Rectangle rBox = anchor.getOwner() instanceof HandleBounds ? new PrecisionRectangle(((HandleBounds) anchor.getOwner()).getHandleBounds()) : new PrecisionRectangle(anchor.getOwner()
                .getBounds());
        anchor.getOwner().translateToAbsolute(rBox);
        return rBox.getCenter();
    }
}
