/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.business.api.query;

import java.util.List;

import org.eclipse.gmf.runtime.notation.ConnectorStyle;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeRouting;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.collect.Lists;

/**
 * A class aggregating all the queries (read-only!) having a {@link Edge} as a
 * starting point.
 * 
 * @author lredor
 */
public class EdgeQuery {

    private Edge edge;

    /**
     * Create a new query.
     * 
     * @param edge
     *            the starting point.
     */
    public EdgeQuery(Edge edge) {
        this.edge = edge;
    }

    /**
     * Return the target anchor of the first brother of this edge (same target
     * and different source). This anchor can be null if there is no brother of
     * if the brothers have not target anchor.
     * 
     * @return the optional target identity anchor of the first brother of this
     *         edge
     */
    public Option<IdentityAnchor> getTargetAnchorOfFirstBrotherWithSameTarget() {
        for (Object brother : edge.getTarget().getTargetEdges()) {
            if (brother instanceof Edge && ((Edge) brother).getSource() != null && !((Edge) brother).getSource().equals(edge.getSource())) {
                IdentityAnchor anchor = (IdentityAnchor) ((Edge) brother).getTargetAnchor();
                return Options.newSome(anchor);
            }
        }
        return Options.newNone();
    }

    /**
     * Return the source anchor of the first brother of this edge (same source
     * and different target). This anchor can be null if there is no brother of
     * if the brothers have not source anchor.
     * 
     * @return the optional source identity anchor of the first brother of this
     *         edge
     */
    public Option<IdentityAnchor> getSourceAnchorOfFirstBrotherWithSameSource() {
        for (Object brother : edge.getSource().getSourceEdges()) {
            if (brother instanceof Edge && ((Edge) brother).getTarget() != null && !((Edge) brother).getTarget().equals(edge.getTarget())) {
                IdentityAnchor anchor = (IdentityAnchor) ((Edge) brother).getSourceAnchor();
                return Options.newSome(anchor);
            }
        }
        return Options.newNone();
    }

    /**
     * Check if the is edge has DEdge as model and this DEdge has a Tree routing
     * style.
     * 
     * @return true if the edge used tree router, false otherwise.
     */
    public boolean usedTreeRouter() {
        boolean edgeUsedTreeRouter = false;
        if (edge.getElement() instanceof DEdge) {
            if (((DEdge) edge.getElement()).getOwnedStyle() != null) {
                if (EdgeRouting.TREE_LITERAL.equals(((DEdge) edge.getElement()).getOwnedStyle().getRoutingStyle())) {
                    edgeUsedTreeRouter = true;
                }
            }
        }
        return edgeUsedTreeRouter;
    }

    /**
     * Check if the edge used tree router and has at least one brother edge (on
     * target side) using too tree router.
     * 
     * @return true if the edge used tree router and has at least one brother
     *         edge (on target side) using too tree router, false otherwise.
     */
    public boolean isEdgeOnTreeOnTargetSide() {
        if (usedTreeRouter() && edge.getTarget() != null) {
            for (Object brother : edge.getTarget().getTargetEdges()) {
                if (!edge.equals(brother) && brother instanceof Edge) {
                    if (new EdgeQuery((Edge) brother).usedTreeRouter()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the edge used tree router and has at least one brother edge (on
     * source side) using too tree router.
     * 
     * @return true if the edge used tree router and has at least one brother
     *         edge (on source side) using too tree router, false otherwise.
     */
    public boolean isEdgeOnTreeOnSourceSide() {
        if (usedTreeRouter() && edge.getSource() != null) {
            for (Object brother : edge.getSource().getSourceEdges()) {
                if (!edge.equals(brother) && brother instanceof Edge) {
                    if (new EdgeQuery((Edge) brother).usedTreeRouter()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return the list of brothers of this edge (on target side) that used tree
     * router too.
     * 
     * @return the list of brothers
     */
    public List<Edge> getBrothersOnTreeOnTargetSide() {
        List<Edge> brothers = Lists.newArrayList();
        for (Object brother : edge.getTarget().getTargetEdges()) {
            if (!edge.equals(brother) && brother instanceof Edge && new EdgeQuery((Edge) brother).usedTreeRouter()) {
                brothers.add((Edge) brother);
            }
        }
        return brothers;
    }

    /**
     * Return the list of brothers of this edge (on source side) that used tree
     * router too.
     * 
     * @return the list of brothers
     */
    public List<Edge> getBrothersOnTreeOnSourceSide() {
        List<Edge> brothers = Lists.newArrayList();
        for (Object brother : edge.getSource().getSourceEdges()) {
            if (!edge.equals(brother) && brother instanceof Edge && new EdgeQuery((Edge) brother).usedTreeRouter()) {
                brothers.add((Edge) brother);
            }
        }
        return brothers;
    }

    /**
     * Check if the edge has a tree routing style.
     * 
     * @return true if the edge has a tree routing style.
     */
    public boolean isEdgeWithTreeRoutingStyle() {
        boolean isEdgeTreeRoutingStyle = false;
        ConnectorStyle connectorStyle = getConnectorStyle();
        if (connectorStyle != null) {
            if (Routing.TREE_LITERAL.getLiteral().equals(connectorStyle.getRouting().getLiteral())) {
                isEdgeTreeRoutingStyle = true;
            }
        }
        return isEdgeTreeRoutingStyle;
    }

    /**
     * Check if the edge has a rectilinear routing style.
     * 
     * @return true if the edge has a rectilinear routing style.
     */
    public boolean isEdgeWithRectilinearRoutingStyle() {
        boolean isEdgeRectilinearRoutingStyle = false;
        ConnectorStyle connectorStyle = getConnectorStyle();
        if (connectorStyle != null) {
            if (Routing.RECTILINEAR_LITERAL.getLiteral().equals(connectorStyle.getRouting().getLiteral())) {
                isEdgeRectilinearRoutingStyle = true;
            }
        }
        return isEdgeRectilinearRoutingStyle;
    }

    /**
     * Check if the edge has an oblique routing style.
     * 
     * @return true if the edge has an oblique routing style.
     */
    public boolean isEdgeWithObliqueRoutingStyle() {
        boolean isEdgeObliqueRoutingStyle = false;
        ConnectorStyle connectorStyle = getConnectorStyle();
        if (connectorStyle != null) {
            if (Routing.MANUAL_LITERAL.getLiteral().equals(connectorStyle.getRouting().getLiteral())) {
                isEdgeObliqueRoutingStyle = true;
            }
        }
        return isEdgeObliqueRoutingStyle;
    }

    private ConnectorStyle getConnectorStyle() {
        ConnectorStyle connectorStyle = null;
        if (edge instanceof ConnectorStyle) {
            connectorStyle = (ConnectorStyle) edge;
        } else {
            for (Object style : edge.getStyles()) {
                if (style instanceof ConnectorStyle) {
                    connectorStyle = (ConnectorStyle) style;
                    break;
                }
            }
        }
        return connectorStyle;
    }
}
