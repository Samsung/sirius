/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.policy;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElementAccessor;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Operand;
import org.eclipse.sirius.diagram.sequence.ui.Messages;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.operation.SequenceEditPartsOperations;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.OperandEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.validator.OperandResizeValidator;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.util.RequestQuery;
import org.eclipse.sirius.diagram.sequence.util.Range;
import org.eclipse.sirius.diagram.ui.graphical.edit.policies.AirResizableEditPolicy;
import org.eclipse.sirius.ext.base.Option;

import com.google.common.collect.Iterables;

/**
 * A specific AirResizableEditPolicy to operand roles move & resize requests.
 * 
 * @author smonnier
 */
public class OperandResizableEditPolicy extends AirResizableEditPolicy {

    /**
     * Constructor.
     */
    public OperandResizableEditPolicy() {
        super();
        setResizeDirections(PositionConstants.NORTH_SOUTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getResizeCommand(ChangeBoundsRequest request) {
        cancelHorizontalDelta(request);

        OperandEditPart oep = (OperandEditPart) getHost();
        OperandResizeValidator operandResizeValidator = new OperandResizeValidator((Operand) oep.getISequenceEvent(), new RequestQuery(request));
        operandResizeValidator.validate();

        Command result;
        if (operandResizeValidator.isValid()) {
            result = getResizeCustomCommand(oep, request);
        } else {
            result = UnexecutableCommand.INSTANCE;
        }
        return result;
    }

    private static Point getPositionFromView(IGraphicalEditPart part) {
        final Point position = new Point();
        if (part.getNotationView() instanceof Node && ((Node) part.getNotationView()).getLayoutConstraint() instanceof Location) {
            final Location location = (Location) ((Node) part.getNotationView()).getLayoutConstraint();
            position.x = location.getX();
            position.y = location.getY();
        }
        return position;
    }

    private static Dimension getDimensionFromView(IGraphicalEditPart part) {
        final Dimension dimension = new Dimension();
        if (part.getNotationView() instanceof Node && ((Node) part.getNotationView()).getLayoutConstraint() instanceof Size) {
            final Size size = (Size) ((Node) part.getNotationView()).getLayoutConstraint();
            dimension.width = size.getWidth();
            dimension.height = size.getHeight();
        }
        return dimension;
    }

    /**
     * Returns the command to resize a bordered node.
     * 
     * @param part
     *            the edit part corresponding to the bordered node.
     * @param request
     *            the request for a resize.
     * @return the command to resize a bordered node.
     */
    public static AbstractTransactionalCommand getResizeBorderItemTCommand(IGraphicalEditPart part, ChangeBoundsRequest request) {
        final EObject semantic = part.resolveSemanticElement();
        if (semantic instanceof DNodeContainer) {
            final double zoom = ((ZoomManager) part.getViewer().getProperty(ZoomManager.class.toString())).getZoom();
            final Dimension dimension = OperandResizableEditPolicy.getDimensionFromView(part);
            final Point position = OperandResizableEditPolicy.getPositionFromView(part);
            dimension.height += request.getSizeDelta().height / zoom;
            switch (request.getResizeDirection()) {
            case PositionConstants.NORTH:
            case PositionConstants.NORTH_WEST:
            case PositionConstants.NORTH_EAST:
                position.y -= request.getSizeDelta().height / zoom;
                break;
            default:
                break;
            }
            return new SetBoundsCommand(part.getEditingDomain(), Messages.OperandResizableEditPolicy_resizeSubCommand, new EObjectAdapter(part.getNotationView()), new Rectangle(position, dimension));
        }
        return null;
    }

    private Command getResizeCustomCommand(OperandEditPart self, ChangeBoundsRequest request) {
        CompositeTransactionalCommand ctc = new CompositeTransactionalCommand(self.getEditingDomain(), Messages.OperandResizableEditPolicy_resizeCompositeCommand);
        ctc.add(OperandResizableEditPolicy.getResizeBorderItemTCommand(self, request));
        Option<Operand> operandOption = ISequenceElementAccessor.getOperand(self.getNotationView());
        if (operandOption.some()) {
            Operand operand = operandOption.get();
            int operandIndex = operand.getIndex();
            if (request.getResizeDirection() == PositionConstants.NORTH) {
                // Resizing the operand from north face must resize the
                // previous operand
                OperandEditPart previousOperandEditPart = getPreviousOperandEditPart(operandIndex);
                if (previousOperandEditPart == null && self.getSelected() != EditPart.SELECTED_NONE) {
                    // There is no previous operand, resize from north face
                    // is forbidden
                    ctc.add(new CommandProxy(UnexecutableCommand.INSTANCE));
                } else if (previousOperandEditPart != null) {
                    // We apply the inverse resize to the previous operand
                    Option<Operand> previousOperandOption = ISequenceElementAccessor.getOperand(previousOperandEditPart.getNotationView());
                    Operand previousOperand = previousOperandOption.get();
                    Range previousOperandVerticalRange = previousOperand.getVerticalRange();
                    Range combinedFragmentVerticalRange = previousOperand.getCombinedFragment().getVerticalRange();
                    int delta = previousOperandVerticalRange.getLowerBound() - combinedFragmentVerticalRange.getLowerBound();
                    Point newLocation = new Point(0, delta);
                    Dimension newDimension = new Dimension(previousOperand.getBounds().width, previousOperandVerticalRange.width() - request.getSizeDelta().height);
                    ctc.add(createOperandSetBoundsCommand(previousOperandEditPart, newLocation, newDimension));
                    postProcessDefaultCommand(ctc, self);
                }
            } else if (request.getResizeDirection() == PositionConstants.SOUTH) {
                OperandEditPart followingOperandEditPart = getFollowingOperandEditPart(operandIndex);
                if (followingOperandEditPart == null && self.getSelected() != EditPart.SELECTED_NONE) {
                    // There is no following operand, resize from south face
                    // is forbidden
                    ctc.add(new CommandProxy(UnexecutableCommand.INSTANCE));
                } else if (followingOperandEditPart != null) {
                    // We apply the inverse resize to the following operand
                    Option<Operand> followingOperandOption = ISequenceElementAccessor.getOperand(followingOperandEditPart.getNotationView());
                    Operand followingOperand = followingOperandOption.get();
                    Range followingOperandVerticalRange = followingOperand.getVerticalRange();
                    Range combinedFragmentVerticalRange = followingOperand.getCombinedFragment().getVerticalRange();
                    int delta = followingOperandVerticalRange.getLowerBound() - combinedFragmentVerticalRange.getLowerBound();
                    Point newLocation = new Point(0, delta + request.getSizeDelta().height);
                    Dimension newDimension = new Dimension(followingOperand.getBounds().width, followingOperandVerticalRange.width() - request.getSizeDelta().height);
                    ctc.add(createOperandSetBoundsCommand(followingOperandEditPart, newLocation, newDimension));
                    postProcessDefaultCommand(ctc, self);
                }
            }
        }
        return new ICommandProxy(ctc);
    }

    /**
     * Refresh ordering.
     * 
     * @param ctc
     *            the current command create on operand resize
     * @param self
     *            the {@link OperandEditPart} that is resizing
     */
    private void postProcessDefaultCommand(CompositeTransactionalCommand ctc, OperandEditPart self) {
        SequenceEditPartsOperations.addRefreshGraphicalOrderingCommand(ctc, self);
        SequenceEditPartsOperations.addRefreshSemanticOrderingCommand(ctc, self);
        SequenceEditPartsOperations.addSynchronizeSemanticOrderingCommand(ctc, self.getISequenceEvent());
    }

    /**
     * Creates the {@link SetBoundsCommand} to resize an operand.
     * 
     * @param part
     *            the {@link OperandEditPart}
     * @param location
     *            the new location of the operand
     * @param dimension
     *            the new dimension of the operand
     * @return a command to resize an operand
     */
    private AbstractTransactionalCommand createOperandSetBoundsCommand(IGraphicalEditPart part, Point location, Dimension dimension) {
        return new SetBoundsCommand(part.getEditingDomain(), Messages.OperandResizableEditPolicy_resizeSubCommand, new EObjectAdapter(part.getNotationView()), new Rectangle(location, dimension));
    }

    /**
     * Finds the previous {@link OperandEditPart} of the current
     * {@link OperandEditPart} identified by the index currentOperandIndex.
     * 
     * @param currentOperandIndex
     *            the index of the current {@link OperandEditPart}
     * @return the previous {@link OperandEditPart}
     */
    private OperandEditPart getPreviousOperandEditPart(int currentOperandIndex) {
        for (OperandEditPart operandEditPart : Iterables.filter(getHost().getParent().getChildren(), OperandEditPart.class)) {
            Option<Operand> operandOption = ISequenceElementAccessor.getOperand(operandEditPart.getNotationView());
            if (operandOption.some()) {
                Operand operand = operandOption.get();
                int operandIndex = operand.getIndex();
                if (operandIndex == currentOperandIndex - 1) {
                    return operandEditPart;
                }
            }
        }
        return null;
    }

    /**
     * Finds the following {@link OperandEditPart} of the current
     * {@link OperandEditPart} identified by the index currentOperandIndex.
     * 
     * @param currentOperandIndex
     *            the index of the current {@link OperandEditPart}
     * @return the following {@link OperandEditPart}
     */
    private OperandEditPart getFollowingOperandEditPart(int currentOperandIndex) {
        for (OperandEditPart operandEditPart : Iterables.filter(getHost().getParent().getChildren(), OperandEditPart.class)) {
            Option<Operand> operandOption = ISequenceElementAccessor.getOperand(operandEditPart.getNotationView());
            if (operandOption.some()) {
                Operand operand = operandOption.get();
                int operandIndex = operand.getIndex();
                if (operandIndex == currentOperandIndex + 1) {
                    return operandEditPart;
                }
            }
        }
        return null;
    }

    private void cancelHorizontalDelta(ChangeBoundsRequest request) {
        if (request == null) {
            return;
        }

        Point moveDelta = request.getMoveDelta();
        if (moveDelta != null) {
            request.setMoveDelta(new Point(0, moveDelta.y));
        }

        Dimension sizeDelta = request.getSizeDelta();
        if (sizeDelta != null) {
            request.setSizeDelta(new Dimension(0, sizeDelta.height));
        }
    }

    private void cancelVerticalDelta(ChangeBoundsRequest request) {
        if (request == null) {
            return;
        }

        Point moveDelta = request.getMoveDelta();
        if (moveDelta != null) {
            request.setMoveDelta(new Point(moveDelta.x, 0));
        }

        Dimension sizeDelta = request.getSizeDelta();
        if (sizeDelta != null) {
            request.setSizeDelta(new Dimension(sizeDelta.width, 0));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getAutoSizeCommand(Request request) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getAlignCommand(AlignmentRequest request) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
        cancelHorizontalDelta(request);
        RequestQuery query = new RequestQuery(request);
        if (query.isMove()) {
            cancelVerticalDelta(request);
        }
        super.showChangeBoundsFeedback(request);
    }

    /**
     * Operand can only be vertically resized.
     * 
     * {@inheritDoc}
     */
    @Override
    protected void createResizeHandle(List handles, int direction) {
        // Move is not allowed. Do not display corner drag handles.
        if ((PositionConstants.NORTH_SOUTH & direction) == direction) {
            super.createResizeHandle(handles, direction);
        }
    }

}
