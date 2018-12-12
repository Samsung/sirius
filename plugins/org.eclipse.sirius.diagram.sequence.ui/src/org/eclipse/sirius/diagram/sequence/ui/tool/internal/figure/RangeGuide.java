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
package org.eclipse.sirius.diagram.sequence.ui.tool.internal.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.sirius.diagram.sequence.util.Range;
import org.eclipse.swt.graphics.Color;

/**
 * A rectangle across the whole diagram, useful as feedback for sequence
 * diagrams where the vertical location of elements relative to each others is
 * significant.
 * 
 * @author mporhel
 */
public class RangeGuide extends Figure {
    private final Range range;

    /**
     * Creates a feedback figure with two horizontal guide placed at the given
     * range.
     * 
     * @param color
     *            the color of the line.
     * @param range
     *            the range of the guide.e wit
     * @param fill
     *            fills the range with the same color, but lighter and
     *            transparent.
     */
    public RangeGuide(Color color, Range range, boolean fill) {
        this.range = range;
        setForegroundColor(color);
        setBackgroundColor(color);
        super.setOpaque(fill);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle rect = getBounds().getCopy();
        rect.y = range.getLowerBound();
        rect.height = 1;

        graphics.drawLine(rect.getTopLeft(), rect.getTopRight());

        // if range have a null width, no need to fill it or to draw the bottom
        // line.
        // we have to reduce the height of the drawn rectangle to stay in the
        // bounds of the figure.
        if (range.width() > 1) {
            rect.height = range.width() - 1;
            graphics.drawLine(rect.getBottomLeft(), rect.getBottomRight());

            if (super.isOpaque()) {
                int alpha = graphics.getAlpha();
                graphics.setAlpha(25);
                graphics.fillRectangle(rect);
                graphics.setAlpha(alpha);
            }
        }
    }

}
