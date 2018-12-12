/*******************************************************************************
 * Copyright (c) 2011, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.api.query;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.diagram.BeginLabelStyle;
import org.eclipse.sirius.diagram.CenterLabelStyle;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeStyle;
import org.eclipse.sirius.diagram.EndLabelStyle;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.IEdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription;

/**
 * Queries for DEdges.
 *
 * @author nlepine
 */
public class DEdgeQuery extends DDiagramElementQuery {
    private final DEdge edge;

    /**
     * Constructor.
     *
     * @param edge
     *            the DEdge to query.
     */
    public DEdgeQuery(DEdge edge) {
        super(edge);
        this.edge = edge;
    }

    /**
     * Indicates if the node as a non-empty name definition, i.e. that its label
     * expression don't always return an empty string.
     *
     * @return true if the node as a non-null and non-empty name, false
     *         otherwise
     */
    public boolean hasNonEmptyNameDefinition() {
        return hasNonEmptyNameDefinition(StylePackage.Literals.EDGE_STYLE_DESCRIPTION__CENTER_LABEL_STYLE_DESCRIPTION);
    }

    private boolean hasNonEmptyNameDefinition(EStructuralFeature labelStyleFeature) {
        boolean hasEmptyNameDefinition = true;
        IEdgeMapping actualMapping = edge.getActualMapping();
        if (actualMapping instanceof EdgeMapping) {
            EdgeMapping edgeMapping = (EdgeMapping) actualMapping;
            EdgeStyleDescription style = edgeMapping.getStyle();
            if (style != null) {
                Object labelStyle = style.eGet(labelStyleFeature);
                if (labelStyle instanceof BasicLabelStyleDescription) {
                    String labelExpression = ((BasicLabelStyleDescription) labelStyle).getLabelExpression();
                    hasEmptyNameDefinition = labelExpression.trim().length() != 0;
                }
            }
        }
        return hasEmptyNameDefinition;
    }

    /**
     * Indicates if the node as a non-empty name definition, i.e. that its label
     * expression don't always return an empty string.
     *
     * @return true if the node as a non-null and non-empty name, false
     *         otherwise
     */
    public boolean hasNonEmptyNamesDefinition() {
        return hasNonEmptyBeginNameDefinition() || hasNonEmptyEndNameDefinition() || hasNonEmptyNameDefinition();
    }

    /**
     * Indicates if the node as a non-empty name definition, i.e. that its label
     * expression don't always return an empty string.
     *
     * @return true if the node as a non-null and non-empty name, false
     *         otherwise
     */
    public boolean hasNonEmptyBeginNameDefinition() {
        return hasNonEmptyNameDefinition(StylePackage.Literals.EDGE_STYLE_DESCRIPTION__BEGIN_LABEL_STYLE_DESCRIPTION);
    }

    /**
     * Indicates if the node as a non-empty name definition, i.e. that its label
     * expression don't always return an empty string.
     *
     * @return true if the node as a non-null and non-empty name, false
     *         otherwise
     */
    public boolean hasNonEmptyEndNameDefinition() {
        return hasNonEmptyNameDefinition(StylePackage.Literals.EDGE_STYLE_DESCRIPTION__END_LABEL_STYLE_DESCRIPTION);
    }

    /**
     * Return if the edge has a name.
     *
     * @return if the edge has a name
     */
    public boolean hasBeginName() {
        return edge.getBeginLabel() != null;
    }

    /**
     * Return if the edge has a name.
     *
     * @return if the edge has a name
     */
    public boolean hasEndName() {
        return edge.getEndLabel() != null;
    }

    /**
     * Return if the edge has a name.
     *
     * @return if the edge has a name
     */
    public boolean hasCenterName() {
        return edge.getName() != null;
    }

    /**
     * Get the {@link CenterLabelStyle} of the current {@link DEdge}.
     *
     * @return a {@link CenterLabelStyle}
     */
    public Option<CenterLabelStyle> getCenterLabelStyle() {
        return Options.newSome(edge.getOwnedStyle().getCenterLabelStyle());
    }

    /**
     * Get the {@link BeginLabelStyle} of the current {@link DEdge}.
     *
     * @return a {@link BeginLabelStyle}
     */
    public Option<BeginLabelStyle> getBeginLabelStyle() {
        return Options.newSome(edge.getOwnedStyle().getBeginLabelStyle());
    }

    /**
     * Get the {@link EndLabelStyle} of the current {@link DEdge}.
     *
     * @return a {@link EndLabelStyle}
     */
    public Option<EndLabelStyle> getEndLabelStyle() {
        return Options.newSome(edge.getOwnedStyle().getEndLabelStyle());
    }

    /**
     * Return if the edge has a name.
     *
     * @return if the edge has a name
     */
    public boolean hasName() {
        boolean hasName = edge.getName() != null || edge.getEndLabel() != null || edge.getBeginLabel() != null;
        return hasName;
    }

    /**
     * Check if the edgeStyle or one of its label style is custom.
     *
     * @see org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery#isCustomized
     *      ()
     * @return true if the edgeStyle or one of its label style is custom, false
     *         otherwise.
     */
    @Override
    public boolean isCustomized() {
        boolean isCustom = false;
        if (edge.getOwnedStyle() != null) {
            EdgeStyle edgeStyle = edge.getOwnedStyle();
            isCustom = !edgeStyle.getCustomFeatures().isEmpty();
            if (!isCustom && edgeStyle.getBeginLabelStyle() != null) {
                isCustom = !edgeStyle.getBeginLabelStyle().getCustomFeatures().isEmpty();
            }
            if (!isCustom && edgeStyle.getCenterLabelStyle() != null) {
                isCustom = !edgeStyle.getCenterLabelStyle().getCustomFeatures().isEmpty();
            }
            if (!isCustom && edgeStyle.getEndLabelStyle() != null) {
                isCustom = !edgeStyle.getEndLabelStyle().getCustomFeatures().isEmpty();
            }
        }
        return isCustom;
    }
}
