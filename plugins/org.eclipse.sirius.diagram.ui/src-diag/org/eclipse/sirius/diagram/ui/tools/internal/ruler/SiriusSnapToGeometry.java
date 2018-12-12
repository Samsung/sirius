/*******************************************************************************
 * Copyright (c) 2015, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.internal.ruler;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.ruler.SnapToGeometryEx;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractBorderedDiagramElementEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramBorderNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IStyleEditPart;
import org.eclipse.sirius.diagram.ui.graphical.edit.policies.SnapChangeBoundsRequest;
import org.eclipse.sirius.diagram.ui.internal.edit.policies.SnapBendpointRequest;
import org.eclipse.sirius.diagram.ui.tools.internal.ui.NoCopyDragEditPartsTrackerEx;
import org.eclipse.sirius.ext.gef.query.EditPartQuery;
import org.eclipse.sirius.ext.gmf.runtime.editparts.GraphicalHelper;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Overridden to support all visible shapes in SnapToShape and not only brothers
 * ones. See {@link NoCopyDragEditPartsTrackerEx#SNAP_TO_ALL_SHAPE_KEY}.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class SiriusSnapToGeometry extends SnapToGeometryEx {

    boolean snapToAll;

    /**
     * A vertical or horizontal snapping point.<BR>
     * Only overridden to have access to constructor.
     */
    class SiriusEntry extends Entry {
        protected SiriusEntry(int type, int location) {
            super(type, location);
        }
    }

    /**
     * Default constructor.
     * 
     * @param container
     *            the container editpart
     */
    public SiriusSnapToGeometry(GraphicalEditPart container) {
        super(container);
    }

    @Override
    public int snapRectangle(Request request, int snapOrientation, PrecisionRectangle baseRect, PrecisionRectangle result) {
        // Get snapToAll mode from request (set by the
        // SiriusDragEditPartsTrackerEx or by the SiriusResizeTracker)
        Object snapToAllExtendedData = request.getExtendedData().get(NoCopyDragEditPartsTrackerEx.SNAP_TO_ALL_SHAPE_KEY);
        boolean oldSnapToAll = snapToAll;
        snapToAll = (snapToAllExtendedData == null && NoCopyDragEditPartsTrackerEx.DEFAULT_SNAP_TO_SHAPE_MODE) || (snapToAllExtendedData != null && ((Boolean) snapToAllExtendedData).booleanValue());
        if (!snapToAll) {
            if (request instanceof SnapChangeBoundsRequest) {
                snapToAll = ((SnapChangeBoundsRequest) request).isSnapToAllShape();
            } else if (request instanceof SnapBendpointRequest) {
                snapToAll = ((SnapBendpointRequest) request).isSnapToAllShape();
            }
        }

        if (oldSnapToAll != snapToAll) {
            // Reset previous computed horizontal rows and vertical column
            // being snapped to.
            rows = null;
            cols = null;
        }

        return super.snapRectangle(request, snapOrientation, baseRect, result);
    }

    @Override
    protected List generateSnapPartsList(List exclusions) {
        if (!snapToAll) {
            // Same code as super.generateSnapPartsList(exclusions); but with
            // border nodes add to children list
            // Don't snap to any figure that is being dragged
            List children = Lists.newArrayList(container.getChildren());
            // Add border nodes
            Iterables.addAll(children, Iterables.filter(container.getParent().getChildren(), AbstractDiagramBorderNodeEditPart.class));
            children.removeAll(exclusions);
            // Remove IStyleEditPart from list of children
            Iterable<Object> filteredChildren = Iterables.filter(children, Predicates.not(Predicates.instanceOf(IStyleEditPart.class)));

            // Don't snap to hidden figures
            List hiddenChildren = Lists.newArrayList();
            for (Iterator iter = filteredChildren.iterator(); iter.hasNext(); /* */) {
                GraphicalEditPart child = (GraphicalEditPart) iter.next();
                if (!child.getFigure().isVisible()) {
                    hiddenChildren.add(child);
                }
            }
            Iterables.removeAll(filteredChildren, hiddenChildren);
            return Lists.newArrayList(filteredChildren);
        } else {
            // Get all potential snap targets
            List<Class<?>> expectedClasses = Lists.newArrayList();
            expectedClasses.add(AbstractBorderedDiagramElementEditPart.class);
            expectedClasses.add(AbstractDiagramBorderNodeEditPart.class);
            List<EditPart> snapPartsList = Lists.newArrayList(new EditPartQuery(container.getRoot()).getAllChildren(false, expectedClasses));
            // Add children of elements that are being dragged
            List<EditPart> exclusionsWithChildren = Lists.newArrayList();
            for (Object editPart : exclusions) {
                if (editPart instanceof EditPart) {
                    exclusionsWithChildren.add((EditPart) editPart);
                    exclusionsWithChildren.addAll(new EditPartQuery((EditPart) editPart).getAllChildren(false, expectedClasses));
                }
            }
            // Don't snap to any figure that is being dragged
            snapPartsList.removeAll(exclusionsWithChildren);

            // Don't snap to hidden figures (not visible for end-user)
            for (Iterator<EditPart> iter = snapPartsList.iterator(); iter.hasNext(); /* */) {
                EditPart snapPart = iter.next();
                if (snapPart instanceof IGraphicalEditPart && !new org.eclipse.sirius.diagram.ui.tools.internal.util.EditPartQuery((IGraphicalEditPart) snapPart).isVisibleOnViewport()) {
                    iter.remove();
                }
            }

            return snapPartsList;
        }

    }

    @Override
    protected void populateRowsAndCols(List parts) {
        // Only center is considered for border nodes (top/middle/bottom and
        // left/center/right for others).
        int nbOfBorderNodes = Iterables.size(Iterables.filter(parts, AbstractDiagramBorderNodeEditPart.class));
        rows = new Entry[(parts.size() - nbOfBorderNodes) * 3 + nbOfBorderNodes];
        cols = new Entry[(parts.size() - nbOfBorderNodes) * 3 + nbOfBorderNodes];
        int currentIndex = 0;
        for (int i = 0; i < parts.size(); i++) {
            IGraphicalEditPart child = (IGraphicalEditPart) parts.get(i);
            Rectangle bounds;
            if (!snapToAll) {
                if (child instanceof AbstractDiagramBorderNodeEditPart) {
                    // Handle specific case of Border Node
                    bounds = GraphicalHelper.getAbsoluteBounds(child);
                    makeRelative(container.getContentPane(), bounds);
                } else {
                    // Same as in super.populateRowsAndCols(parts)
                    bounds = getFigureBounds(child);
                }
            } else {
                bounds = GraphicalHelper.getAbsoluteBounds(child);
                makeRelative(container.getContentPane(), bounds);
            }
            if (!(child instanceof AbstractDiagramBorderNodeEditPart)) {
                // Only center is considered for border node
                cols[currentIndex] = new SiriusEntry(-1, bounds.x);
                rows[currentIndex++] = new SiriusEntry(-1, bounds.y);
            }
            cols[currentIndex] = new SiriusEntry(0, bounds.x + (bounds.width - 1) / 2);
            rows[currentIndex++] = new SiriusEntry(0, bounds.y + (bounds.height - 1) / 2);
            if (!(child instanceof AbstractDiagramBorderNodeEditPart)) {
                // Only center is considered for border node
                cols[currentIndex] = new SiriusEntry(1, bounds.right() - 1);
                rows[currentIndex++] = new SiriusEntry(1, bounds.bottom() - 1);
            }
        }
    }
}
