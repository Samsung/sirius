/******************************************************************************
 * Copyright (c) 2004, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Mariot Chauvin <mariot.chauvin@obeo.fr> - bug 233344 
 *    Obeo - Copied and adapted from org.eclipse.gmf.runtime.draw2d.ui.internal.routers.TreeRouter.
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.business.api.query;

import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.ITreeConnection;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * A class aggregating all the queries (read-only!) having a {@link Connection}
 * as a starting point.
 * 
 * @author lredor
 */
public class ConnectionQuery {

    private Connection connection;

    /**
     * Create a new query.
     * 
     * @param connection
     *            the starting point.
     */
    public ConnectionQuery(Connection connection) {
        this.connection = connection;
    }

    // CHECKSTYLE:OFF
    /**
     * Method copied from TreeRouter.<BR>
     * Utility method to determine if the given set of points conforms to the
     * constraints of being an orthogonal connection tree-branch. 1. Points size
     * must be 4. 2. Source point resides with-in boundary of source shape based
     * on orientation 3. Target point resides with-in boundary of target shape
     * based on orientation 4. Middle line is perpendicular to the 2 end lines.
     * 
     * @param points
     *            <code>PointList</code> to test constraints against
     * @return <code>boolean</code> <code>true</code> if points represent valid
     *         orthogonal tree branch, <code>false</code> otherwise.
     */
    public boolean isOrthogonalTreeBranch(PointList points) {
        if (isTreeBranch(points)) {
            LineSeg branch = new LineSeg(points.getPoint(0), points.getPoint(1));
            LineSeg trunkShoulder = new LineSeg(points.getPoint(1), points.getPoint(2));
            LineSeg trunk = new LineSeg(points.getPoint(2), points.getPoint(3));

            if (isTopDown())
                return branch.isVertical() && trunkShoulder.isHorizontal() && trunk.isVertical();
            else
                return branch.isHorizontal() && trunkShoulder.isVertical() && trunk.isHorizontal();
        }

        return false;
    }

    /**
     * Method copied from TreeRouter.<BR>
     * Utility method to determine if the given set of points conforms to the
     * constraints of being a connection tree-branch. 1. Points size must be 4.
     * 2. Source point resides with-in boundary of source shape based on
     * orientation 3. Target point resides with-in boundary of target shape
     * based on orientation
     * 
     * @param conn
     *            the <code>Connection</code> to test
     * @param points
     *            the <code>PointList</code> to test constraints against
     * @return <code>boolean</code> <code>true</code> if points represent valid
     *         tree branch, <code>false</code> otherwise.
     */
    protected boolean isTreeBranch(PointList points) {
        if (points.size() == 4 && connection.getSourceAnchor() != null && connection.getSourceAnchor().getOwner() != null && connection.getTargetAnchor() != null
                && connection.getTargetAnchor().getOwner() != null) {
            // just check if ends are with-in the owner bounding box
            Rectangle targetBounds = getTargetAnchorRelativeBounds();
            Rectangle sourceBounds = getSourceAnchorRelativeBounds();

            if (isTopDown()) {
                return (points.getPoint(0).x > sourceBounds.x && points.getPoint(0).x < sourceBounds.x + sourceBounds.width)
                        && (points.getPoint(3).x > targetBounds.x && points.getPoint(3).x < targetBounds.x + targetBounds.width);
            } else {
                return (points.getPoint(0).y > sourceBounds.y && points.getPoint(0).y < sourceBounds.y + sourceBounds.height)
                        && (points.getPoint(3).y > targetBounds.y && points.getPoint(3).y < targetBounds.y + targetBounds.height);
            }
        }

        return false;
    }

    /**
     * Method copied from TreeRouter.<BR>
     * isTopDown Utility method to determine if the connection should routed in
     * a top-down fashion or in a horizontal fashion.
     * 
     * @param conn
     *            Connection to query
     * @return boolean true if connection should be routed top-down, false
     *         otherwise.
     */
    protected boolean isTopDown() {
        boolean vertical = true;
        if (connection instanceof ITreeConnection) {
            vertical = ((ITreeConnection) connection).getOrientation().equals(ITreeConnection.Orientation.VERTICAL) ? true : false;
        }

        return vertical;
    }

    /**
     * Method copied from TreeRouter.<BR>
     */
    private Rectangle getTargetAnchorRelativeBounds() {
        final Rectangle bounds = connection.getTargetAnchor().getOwner().getBounds().getCopy();
        connection.getTargetAnchor().getOwner().translateToAbsolute(bounds);
        connection.translateToRelative(bounds);
        return bounds;
    }

    /**
     * Method copied from TreeRouter.<BR>
     */
    private Rectangle getSourceAnchorRelativeBounds() {
        final Rectangle bounds = connection.getSourceAnchor().getOwner().getBounds().getCopy();
        connection.getSourceAnchor().getOwner().translateToAbsolute(bounds);
        connection.translateToRelative(bounds);
        return bounds;
    }

    // CHECKSTYLE:ON

    /**
     * Return the constraint of the connection as list of RelativeBendpoint only
     * if :
     * <UL>
     * <LI>the constraint is a list of relative bendpoints</LI>
     * <LI>this list contains 4 points (corresponding to a branch of a tree).</LI>
     * </UL>
     * 
     * @return an optional list of {@link RelativeBendpoint}
     */
    public Option<List<RelativeBendpoint>> getTreeRelativeBendpointsConstraint() {

        Object cons = connection.getRoutingConstraint();
        if (cons instanceof List) {
            if (((List<?>) cons).size() == 4) {
                return getRelativeBendpointsConstraint();
            }
        }
        return Options.newNone();
    }

    /**
     * Return the constraint of the connection as list of RelativeBendpoint only
     * if :
     * <UL>
     * <LI>the constraint is a list of absolute bendpoints</LI>
     * <LI>this list contains 4 points (corresponding to a branch of a tree).</LI>
     * </UL>
     * 
     * @return an optional list of {@link AbsoluteBendpoint}
     */
    public Option<List<AbsoluteBendpoint>> getTreeAbsoluteBendpointsConstraint() {

        Object cons = connection.getRoutingConstraint();
        if (cons instanceof List) {
            if (((List<?>) cons).size() == 4) {
                return getAbsoluteBendpointsConstraint();
            }
        }
        return Options.newNone();
    }

    /**
     * Return the constraint of the connection as list of RelativeBendpoint only
     * if the constraint is a list of absolute bendpoints.
     * 
     * @return an optional list of {@link AbsoluteBendpoint}
     */
    public Option<List<AbsoluteBendpoint>> getAbsoluteBendpointsConstraint() {

        Object cons = connection.getRoutingConstraint();
        if (cons instanceof List) {
            List<?> constraintsList = (List<?>) cons;
            if (Iterators.all(constraintsList.iterator(), Predicates.instanceOf(AbsoluteBendpoint.class))) {
                List<AbsoluteBendpoint> result = Lists.newLinkedList();
                for (Object object : constraintsList) {
                    result.add((AbsoluteBendpoint) object);
                }
                return Options.newSome(result);
            }
        }
        return Options.newNone();
    }

    /**
     * Return the constraint of the connection as list of RelativeBendpoint only
     * if the constraint is a list of relative bendpoints.
     * 
     * @return an optional list of {@link RelativeBendpoint}
     */
    public Option<List<RelativeBendpoint>> getRelativeBendpointsConstraint() {

        Object cons = connection.getRoutingConstraint();
        if (cons instanceof List) {
            List<?> constraintsList = (List<?>) cons;
            if (Iterators.all(constraintsList.iterator(), Predicates.instanceOf(RelativeBendpoint.class))) {
                List<RelativeBendpoint> result = Lists.newLinkedList();
                for (Object object : constraintsList) {
                    result.add((RelativeBendpoint) object);
                }
                return Options.newSome(result);
            }
        }
        return Options.newNone();
    }
}
