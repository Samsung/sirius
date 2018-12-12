/*******************************************************************************
 * Copyright (c) 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.internal.edit.policies;

import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Vector;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.sirius.diagram.ui.business.api.query.ConnectionEditPartQuery;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.AbstractDEdgeNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.locator.EdgeLabelLocator;
import org.eclipse.sirius.diagram.ui.tools.api.figure.SiriusWrapLabel;

/**
 * This manager is used to store the initial points of an edge before the
 * feedback is used. These initial points are stored in the request.<BR>
 * The method
 * {@link #storeInitialPointsInRequest(LocationRequest, ConnectionEditPart, Connection)}
 * must be called in the corresponding policy before drawing the feedback.<BR>
 * And the method {@link #eraseInitialPoints(Connection)} must be called at the
 * end of the feedback drawing (eraseConnection*Feedback methods of Policy
 * classes).
 *
 *
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class InitialPointsOfRequestDataManager {
    /**
     * The key used to store the initial points of the edge (before the feedback
     * drawing).
     */
    private static final String INITIAL_POINTS_KEY = "InitialPointsManagerForEdgePolicy.initialPointsKey"; //$NON-NLS-1$

    /** The list of points before feedback drawing. */
    private PointList initialPoints;

    /**
     * Store the initial points of the edge in the request (before feedback
     * drawing). This data can be used later for computing the location of the
     * labels of this edge in the command construction.<BR>
     * This method also set the feedback data of the {@link EdgeLabelLocator} of
     * the labels of the current connection to correctly draw the label feedback
     * during the label move.
     *
     * @param request
     *            the request in which to store the original points of the edge.
     * @param connectionEditPart
     *            the editPart of the edge
     */
    public void storeInitialPointsInRequest(LocationRequest request, ConnectionEditPart connectionEditPart) {
        if (initialPoints == null) {
            initialPoints = new PointList();
            Connection connection = (Connection) connectionEditPart.getFigure();
            for (int i = 0; i < connection.getPoints().size(); i++) {
                initialPoints.addPoint(connection.getPoints().getPoint(i).getCopy());
            }

            request.getExtendedData().put(INITIAL_POINTS_KEY, initialPoints);
            List<?> children = connectionEditPart.getChildren();
            for (Object child : children) {
                if (child instanceof AbstractDEdgeNameEditPart) {
                    Object view = ((AbstractDEdgeNameEditPart) child).getModel();
                    IFigure figure = ((AbstractDEdgeNameEditPart) child).getFigure();
                    if (view instanceof Node && figure instanceof SiriusWrapLabel) {
                        Object currentConstraint = connection.getLayoutManager().getConstraint(figure);
                        if (currentConstraint instanceof EdgeLabelLocator) {
                            EdgeLabelLocator edgeLabelLocator = (EdgeLabelLocator) currentConstraint;
                            edgeLabelLocator.setFeedbackData(initialPoints, new Vector(edgeLabelLocator.getOffset().x, edgeLabelLocator.getOffset().y),
                                    new ConnectionEditPartQuery(connectionEditPart).isEdgeWithObliqueRoutingStyle());
                        }
                    }
                }
            }
        }
    }

    /**
     * Reset the initial points stored in this manager and also erase the
     * feedback data from all the {@link EdgeLabelLocator} of the labels of the
     * current connection.
     *
     * @param connection
     *            The connection from which to erase feedback data
     */
    public void eraseInitialPoints(Connection connection) {
        initialPoints = null;
        for (Object object : connection.getChildren()) {
            if (object instanceof SiriusWrapLabel) {
                Object currentConstraint = connection.getLayoutManager().getConstraint((SiriusWrapLabel) object);
                if (currentConstraint instanceof EdgeLabelLocator) {
                    ((EdgeLabelLocator) currentConstraint).eraseFeedbackData();
                }
            }
        }
    }

    /**
     * Get the initial points list of the edge stored in the
     * <code>request</code>.
     *
     * @param request
     *            The request to query
     * @return the original points list or null if no original points are stored
     *         in this request.
     */
    public static PointList getOriginalPoints(LocationRequest request) {
        return (PointList) request.getExtendedData().get(INITIAL_POINTS_KEY);
    }
}
