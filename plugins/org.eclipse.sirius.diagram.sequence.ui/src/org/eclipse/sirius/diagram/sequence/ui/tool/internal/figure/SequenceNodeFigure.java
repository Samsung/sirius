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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.sirius.diagram.ui.tools.api.figure.AirDefaultSizeNodeFigure;
import org.eclipse.sirius.diagram.ui.tools.api.figure.anchor.AnchorProvider;

/**
 * This specific figure overrides {@link AirDefaultSizeNodeFigure} to create
 * specific anchor.
 * 
 * @author mporhel
 * 
 */
public class SequenceNodeFigure extends AirDefaultSizeNodeFigure {

    /**
     * Create a new {@link SequenceNodeFigure}.
     * 
     * @param defSize
     *            the size.
     * @param anchorProvider
     *            the anchor provider.
     */
    public SequenceNodeFigure(final Dimension defSize, final AnchorProvider anchorProvider) {
        super(defSize, anchorProvider);
    }

    /**
     * Create a new {@link SequenceNodeFigure}.
     * 
     * @param width
     *            the width.
     * @param height
     *            the height.
     * @param anchorProvider
     *            the anchor provider.
     */
    public SequenceNodeFigure(final int width, final int height, final AnchorProvider anchorProvider) {
        super(width, height, anchorProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConnectionAnchor createAnchor(PrecisionPoint p) {
        return new SequenceSlidableAnchor(this, p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConnectionAnchor createDefaultAnchor() {
        return new SequenceSlidableAnchor(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionAnchor getConnectionAnchor(final String terminal) {
        ConnectionAnchor connectAnchor = super.getConnectionAnchor(terminal);
        if (connectAnchor instanceof SequenceSlidableAnchor) {
            ((SequenceSlidableAnchor) connectAnchor).updateCustomStatus(terminal);
        }
        return connectAnchor;
    }

    /**
     * Returns a new anchor for this node figure.
     * 
     * Handles sequence nodes with a 0 width and non 0 height (collapse) as if
     * they have a 1 pixel width.
     * 
     * {@inheritDoc}
     */
    @Override
    protected ConnectionAnchor createConnectionAnchor(Point p) {
        ConnectionAnchor anchor;

        // Handle Sequence collapsed elements.
        Rectangle bounds = getBounds().getCopy();
        if (p != null && bounds.width == 0 && bounds.height != 0) {
            bounds.width = 1;
            Point temp = p.getCopy();
            translateToRelative(temp);
            PrecisionPoint pt = BaseSlidableAnchor.getAnchorRelativeLocation(temp, bounds);
            if (isDefaultAnchorArea(pt)) {
                anchor = getConnectionAnchor(szAnchor);
            } else {
                anchor = createAnchor(pt);
            }
        } else {
            anchor = super.createConnectionAnchor(p);
        }
        return anchor;
    }
}
