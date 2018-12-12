/*******************************************************************************
 * Copyright (c) 2009, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.internal.format;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.ConnectorStyle;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.diagram.formatdata.AbstractFormatData;
import org.eclipse.sirius.diagram.formatdata.EdgeFormatData;
import org.eclipse.sirius.diagram.formatdata.FormatdataFactory;
import org.eclipse.sirius.diagram.formatdata.NodeFormatData;
import org.eclipse.sirius.diagram.formatdata.Point;
import org.eclipse.sirius.diagram.ui.business.api.query.NodeQuery;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper;
import org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataKey;
import org.eclipse.sirius.diagram.ui.tools.internal.format.semantic.SemanticEdgeFormatDataKey;
import org.eclipse.sirius.diagram.ui.tools.internal.format.semantic.SemanticNodeFormatDataKey;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.draw2d.figure.FigureUtilities;
import org.eclipse.sirius.viewpoint.DStylizable;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

/**
 * Helper to manage the format data.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class FormatDataHelperImpl implements FormatDataHelper {

    private static final Predicate<EObject> ROOT_PREDICATE = new Predicate<EObject>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean apply(final EObject input) {
            return input.eContainer() == null;
        }
    };

    /**
     * Creates the default FormatDataHelper implementation.
     */
    public FormatDataHelperImpl() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper#createNodeFormatData(org.eclipse.gmf.runtime.notation.Node,
     *      org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart,
     *      org.eclipse.sirius.diagram.formatdata.NodeFormatData)
     */
    @Override
    public NodeFormatData createNodeFormatData(final Node node, final IGraphicalEditPart editPart, final NodeFormatData parentFormatData) {
        final NodeFormatData result = FormatdataFactory.eINSTANCE.createNodeFormatData();

        Dimension primarySize = new Dimension(0, 0);
        // Compute the relative location from the parent format data
        // 1-Get the relative location
        final org.eclipse.draw2d.geometry.Point relativeLocation = editPart.getFigure().getBounds().getLocation().getCopy();

        // 2-Transform to absolute location
        FigureUtilities.translateToAbsoluteByIgnoringScrollbar(editPart.getFigure(), relativeLocation);

        boolean isCollapsed = false;
        if (new DDiagramElementQuery((DDiagramElement) node.getElement()).isIndirectlyCollapsed()) {
            isCollapsed = true;
        }
        if (isCollapsed) {
            LayoutConstraint formatConstraint = node.getLayoutConstraint();
            if (formatConstraint instanceof Bounds) {
                NodeQuery nodeQuery = new NodeQuery(node);
                Option<Bounds> option = nodeQuery.getExtendedBounds();
                if (option.some()) {
                    Bounds unCollapseBounds = option.get();
                    int deltaX = ((Bounds) formatConstraint).getX() - unCollapseBounds.getX();
                    int deltaY = ((Bounds) formatConstraint).getY() - unCollapseBounds.getY();

                    relativeLocation.setLocation(relativeLocation.x - deltaX, relativeLocation.y - deltaY);
                    primarySize = new Dimension(unCollapseBounds.getWidth(), unCollapseBounds.getHeight());
                }
            }
        } else {
            final Integer width = (Integer) ViewUtil.getStructuralFeatureValue(node, NotationPackage.eINSTANCE.getSize_Width());
            final Integer height = (Integer) ViewUtil.getStructuralFeatureValue(node, NotationPackage.eINSTANCE.getSize_Height());
            primarySize = new Dimension(width.intValue(), height.intValue());
            if (width.intValue() == -1 || height.intValue() == -1) {
                primarySize = editPart.getFigure().getSize().getCopy();
            }
        }

        // 3-Remove the parent absolute location
        if (parentFormatData != null) {
            final Point parentAbsoluteLocation = FormatDataHelper.INSTANCE.getAbsoluteLocation(parentFormatData);
            relativeLocation.translate(-parentAbsoluteLocation.getX(), -parentAbsoluteLocation.getY());
        }

        result.setHeight(primarySize.height);
        result.setWidth(primarySize.width);
        final Point location = FormatdataFactory.eINSTANCE.createPoint();
        location.setX(relativeLocation.x);
        location.setY(relativeLocation.y);
        result.setLocation(location);

        // 4-Copy Sirius and GMF styles
        copyViewStyleInFormatData(result, node);

        return result;
    }

    private void copyViewStyleInFormatData(AbstractFormatData formatData, View view) {
        // 1-Copy Sirius Style
        if (view.getElement() instanceof DStylizable) {
            formatData.setSiriusStyle(EcoreUtil.copy(((DStylizable) view.getElement()).getStyle()));
        }

        // 2-Copy GMF view to retrieve GMF style
        EcoreUtil.Copier copierWithoutElementRef = new EcoreUtil.Copier(false, false);
        View viewCopy = (View) copierWithoutElementRef.copy(view);
        formatData.setGmfView(viewCopy);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper#createEdgeFormatData(org.eclipse.gmf.runtime.notation.Edge)
     */
    @Override
    public EdgeFormatData createEdgeFormatData(final Edge gmfEdge, final ConnectionEditPart connectionEditPart) {
        final EdgeFormatData result = FormatdataFactory.eINSTANCE.createEdgeFormatData();
        final ConnectorStyle connectorStyle = (ConnectorStyle) gmfEdge.getStyle(NotationPackage.eINSTANCE.getConnectorStyle());
        if (connectorStyle != null) {
            result.setRouting(connectorStyle.getRouting().getValue());
            result.setJumpLinkStatus(connectorStyle.getJumpLinkStatus().getValue());
            result.setJumpLinkType(connectorStyle.getJumpLinkType().getValue());
            result.setReverseJumpLink(connectorStyle.isJumpLinksReverse());
            result.setSmoothness(connectorStyle.getSmoothness().getValue());
        }
        if (connectionEditPart != null) {
            final PolylineConnectionEx polylineConnectionEx = (PolylineConnectionEx) connectionEditPart.getFigure();
            polylineConnectionEx.getConnectionRouter().route(polylineConnectionEx);
            final org.eclipse.draw2d.geometry.Point originialSourceRefPoint = polylineConnectionEx.getSourceAnchor().getReferencePoint().getCopy();
            polylineConnectionEx.translateToRelative(originialSourceRefPoint);
            result.setSourceRefPoint(createPoint(originialSourceRefPoint));

            final org.eclipse.draw2d.geometry.Point originialTargetRefPoint = polylineConnectionEx.getTargetAnchor().getReferencePoint().getCopy();
            polylineConnectionEx.translateToRelative(originialTargetRefPoint);
            result.setTargetRefPoint(createPoint(originialTargetRefPoint));

            initPointList(result.getPointList(), polylineConnectionEx.getPoints().getCopy());
        }
        if (gmfEdge.getSourceAnchor() instanceof IdentityAnchor) {
            result.setSourceTerminal(((IdentityAnchor) gmfEdge.getSourceAnchor()).getId());
        }
        if (gmfEdge.getTargetAnchor() instanceof IdentityAnchor) {
            result.setTargetTerminal(((IdentityAnchor) gmfEdge.getTargetAnchor()).getId());
        }

        // 4-Copy Sirius and GMF styles
        copyViewStyleInFormatData(result, gmfEdge);

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper#createLabelFormatData(org.eclipse.gmf.runtime.notation.Node)
     */
    @Override
    public NodeFormatData createLabelFormatData(final Node labelNode) {
        final NodeFormatData result = FormatdataFactory.eINSTANCE.createNodeFormatData();

        if (labelNode.getLayoutConstraint() instanceof Size) {
            final Integer width = (Integer) ViewUtil.getStructuralFeatureValue(labelNode, NotationPackage.eINSTANCE.getSize_Width());
            final Integer height = (Integer) ViewUtil.getStructuralFeatureValue(labelNode, NotationPackage.eINSTANCE.getSize_Height());

            result.setWidth(width);
            result.setHeight(height);
        }

        final Integer x = (Integer) ViewUtil.getStructuralFeatureValue(labelNode, NotationPackage.eINSTANCE.getLocation_X());
        final Integer y = (Integer) ViewUtil.getStructuralFeatureValue(labelNode, NotationPackage.eINSTANCE.getLocation_Y());

        final Point location = FormatdataFactory.eINSTANCE.createPoint();
        location.setX(x);
        location.setY(y);

        result.setLocation(location);

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper#getAbsoluteLocation(org.eclipse.sirius.diagram.formatdata.NodeFormatData)
     */
    @Override
    public Point getAbsoluteLocation(final NodeFormatData nodeFormatData) {
        Point result = getCopy(nodeFormatData.getLocation());
        if (nodeFormatData.eContainer() instanceof NodeFormatData) {
            result = getTranslated(result, getAbsoluteLocation((NodeFormatData) nodeFormatData.eContainer()));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.ui.tools.api.format.FormatDataHelper#getRelativeLocation(org.eclipse.sirius.diagram.formatdata.NodeFormatData,
     *      org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)
     */
    @Override
    public Point getRelativeLocation(final NodeFormatData formatData, final IGraphicalEditPart editPart) {
        final Point result = getAbsoluteLocation(formatData);
        final org.eclipse.draw2d.geometry.Point p = new org.eclipse.draw2d.geometry.Point(result.getX(), result.getY());
        FigureUtilities.translateToRelativeByIgnoringScrollbar(editPart.getFigure(), p);
        result.setX(p.x);
        result.setY(p.y);

        return result;
    }

    /**
     * Create a new point from a draw2d point.
     * 
     * @param draw2dPoint
     *            The original point
     * @return A new point
     */
    private Point createPoint(final org.eclipse.draw2d.geometry.Point draw2dPoint) {
        final Point newPoint = FormatdataFactory.eINSTANCE.createPoint();
        newPoint.setX(draw2dPoint.x);
        newPoint.setY(draw2dPoint.y);
        return newPoint;
    }

    /**
     * Initialize a list of {@link Point} from a {@link PointList}.
     * 
     * @param pointsList
     *            the list to initialize
     * @param draw2dPointsList
     *            the original points of the line
     */
    private void initPointList(final EList<Point> pointList, final PointList draw2dPointsList) {
        for (int i = 0; i < draw2dPointsList.size(); i++) {
            pointList.add(createPoint(draw2dPointsList.getPoint(i)));
        }

    }

    /**
     * Return a copy of this Point.
     * 
     * @param point
     *            the point to copy
     * @return a copy of this Point
     */
    private Point getCopy(final Point point) {
        final Point copy = FormatdataFactory.eINSTANCE.createPoint();
        copy.setX(point.getX());
        copy.setY(point.getY());
        return copy;
    }

    /**
     * Creates a new Point which is translated by the values of the provided
     * Point.
     * 
     * @param originalPoint
     *            The point to translate.
     * @param pt
     *            Point which provides the translation amounts.
     * @return A new Point
     */
    protected Point getTranslated(final Point originalPoint, final Point pt) {
        final Point translatedPoint = FormatdataFactory.eINSTANCE.createPoint();
        translatedPoint.setX(originalPoint.getX() + pt.getX());
        translatedPoint.setY(originalPoint.getY() + pt.getY());
        return translatedPoint;
    }

    /**
     * Creates a new Point which is translated by the values of the provided
     * Point.
     * 
     * @param originalPoint
     *            The point to translate.
     * @param pt
     *            Point which provides the translation amounts.
     * @return A new Point
     */
    @Override
    public Point getTranslated(final Point originalPoint, final org.eclipse.draw2d.geometry.Point pt) {
        final Point translatedPoint = FormatdataFactory.eINSTANCE.createPoint();
        translatedPoint.setX(originalPoint.getX() + pt.x);
        translatedPoint.setY(originalPoint.getY() + pt.y);
        return translatedPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<? extends FormatDataKey, ? extends AbstractFormatData> getRootFormatData(final Map<? extends FormatDataKey, ? extends AbstractFormatData> collection) {
        return Maps.filterValues(collection, ROOT_PREDICATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormatDataKey createKey(final AbstractFormatData formatData) {
        FormatDataKey result;
        if (formatData instanceof NodeFormatData) {
            result = new SemanticNodeFormatDataKey(formatData.getId());
        } else if (formatData instanceof EdgeFormatData) {
            result = new SemanticEdgeFormatDataKey(formatData.getId());
        } else {
            throw new IllegalArgumentException(MessageFormat.format(Messages.LayoutDataHelperImpl_unkownLayoutData, formatData.getClass()));
        }
        return result;
    }
}
