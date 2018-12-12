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
package org.eclipse.sirius.diagram.ui.business.internal.query;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.WorkspaceImage;
import org.eclipse.sirius.diagram.ui.tools.api.figure.WorkspaceImageFigure;
import org.eclipse.sirius.diagram.ui.tools.api.layout.LayoutUtils;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Preconditions;

/**
 * Queries relative to a DNode.
 * 
 * @author mporhel
 */
public class DNodeQuery {

    private static final Dimension DEFAULT_NODE_DIMENSION = new Dimension(2, 2);

    private final DNode node;

    /**
     * Constructor.
     * 
     * @param node
     *            the node to query.
     */
    public DNodeQuery(DNode node) {
        this.node = Preconditions.checkNotNull(node);
    }

    /**
     * Return the default draw2D dimension according to the specified DNode.
     * 
     * @return the default draw2D dimension according to the specified DNode.
     */
    public Dimension getDefaultDimension() {
        final Dimension result = DEFAULT_NODE_DIMENSION.getCopy();

        if (node.getStyle() instanceof WorkspaceImage) {
            final WorkspaceImage workspaceImage = (WorkspaceImage) node.getStyle();
            final String path = workspaceImage.getWorkspacePath();
            final Image image;
            image = WorkspaceImageFigure.getImageInstanceFromPath(path);
            if (image != null) {
                // Use default image size
                if (node.getWidth() == null || Integer.valueOf(node.getWidth()) == -1) {
                    result.width = image.getBounds().width;
                    result.height = image.getBounds().height;
                } else {
                    // width is already defined, adapt height thanks to
                    // image ratio
                    final double ratio = (double) image.getBounds().width / image.getBounds().height;
                    double newHeight = node.getWidth().intValue() / ratio;

                    // Adapt to draw2D
                    result.width = node.getWidth().intValue() * LayoutUtils.SCALE;
                    result.height = (int) (newHeight * LayoutUtils.SCALE);
                }
            }
        } else {
            if (node.getWidth() != null) {
                result.width = node.getWidth().intValue();
            }
            if (node.getHeight() != null) {
                result.height = node.getHeight().intValue();
            }

            // Adapt to draw2D
            result.width = result.width * LayoutUtils.SCALE;
            result.height = result.height * LayoutUtils.SCALE;
        }

        return result;
    }

}
