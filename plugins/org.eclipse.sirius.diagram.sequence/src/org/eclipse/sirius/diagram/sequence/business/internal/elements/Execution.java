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
package org.eclipse.sirius.diagram.sequence.business.internal.elements;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.sequence.Messages;
import org.eclipse.sirius.diagram.sequence.business.internal.layout.LayoutConstants;
import org.eclipse.sirius.diagram.sequence.business.internal.ordering.EventEndHelper;
import org.eclipse.sirius.diagram.sequence.business.internal.query.ISequenceEventQuery;
import org.eclipse.sirius.diagram.sequence.business.internal.query.SequenceNodeQuery;
import org.eclipse.sirius.diagram.sequence.business.internal.util.ParentOperandFinder;
import org.eclipse.sirius.diagram.sequence.business.internal.util.RangeSetter;
import org.eclipse.sirius.diagram.sequence.business.internal.util.SubEventsHelper;
import org.eclipse.sirius.diagram.sequence.description.DescriptionPackage;
import org.eclipse.sirius.diagram.sequence.ordering.CompoundEventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.SingleEventEnd;
import org.eclipse.sirius.diagram.sequence.util.Range;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Represents an execution on a lifeline or another parent execution.
 * 
 * @author mporhel, pcdavid, smonnier
 */
public class Execution extends AbstractNodeEvent {
    /**
     * Predicate to filter Frames and Operand from possible new parents of an
     * execution reparent.
     */
    public static final Predicate<ISequenceEvent> NO_REPARENTABLE_EVENTS = new Predicate<ISequenceEvent>() {
        @Override
        public boolean apply(ISequenceEvent input) {
            return input instanceof AbstractFrame || input instanceof Operand || input instanceof Message;
        }
    };

    /**
     * The visual ID. Same as a normal bordered node.
     */
    public static final int VISUAL_ID = 3001;

    /**
     * Predicate to check whether a Sirius DDiagramElement represents an
     * execution.
     */
    private static enum SiriusElementPredicate implements Predicate<DDiagramElement> {
        INSTANCE;

        @Override
        public boolean apply(DDiagramElement input) {
            return AbstractSequenceElement.isSequenceDiagramElement(input, DescriptionPackage.eINSTANCE.getExecutionMapping())
                    && !InstanceRole.viewpointElementPredicate().apply((DDiagramElement) input.eContainer());
        }
    }

    /**
     * Constructor.
     * 
     * @param node
     *            the GMF Node representing this execution.
     */
    Execution(Node node) {
        super(node);
        Preconditions.checkArgument(Execution.notationPredicate().apply(node), Messages.Execution_nonExecutionNode);
    }

    /**
     * Returns a predicate to check whether a GMF View represents an execution.
     * 
     * @return a predicate to check whether a GMF View represents an execution.
     */
    public static Predicate<View> notationPredicate() {
        return new NotationPredicate(NotationPackage.eINSTANCE.getNode(), VISUAL_ID, Execution.viewpointElementPredicate());
    }

    /**
     * Returns a predicate to check whether a Sirius DDiagramElement represents
     * an execution.
     * 
     * @return a predicate to check whether a Sirius DDiagramElement represents
     *         an execution.
     */
    public static Predicate<DDiagramElement> viewpointElementPredicate() {
        return SiriusElementPredicate.INSTANCE;
    }

    @Override
    public List<Message> getLinkedMessages() {
        List<Message> linkedMessages = Lists.newArrayList();

        Option<Message> startMessage = getStartMessage();
        if (startMessage.some()) {
            linkedMessages.add(startMessage.get());
        }

        Option<Message> targetMessage = getEndMessage();
        if (targetMessage.some()) {
            linkedMessages.add(targetMessage.get());
        }

        return linkedMessages;
    }

    /**
     * Returns the message linked to the start (i.e. top side) of this
     * execution, if any.
     * 
     * @return the message linked to the start of this execution, if any.
     */
    public Option<Message> getStartMessage() {
        return getCompoundMessage(true);
    }

    private Option<Message> getCompoundMessage(boolean start) {
        Node node = getNotationNode();
        Set<Edge> edges = Sets.newHashSet();
        Iterables.addAll(edges, Iterables.filter(node.getSourceEdges(), Edge.class));
        Iterables.addAll(edges, Iterables.filter(node.getTargetEdges(), Edge.class));

        List<EventEnd> ends = EventEndHelper.findEndsFromSemanticOrdering(this);
        for (Edge edge : edges) {
            Option<Message> message = ISequenceElementAccessor.getMessage(edge);
            if (message.some()) {
                List<EventEnd> messageEnds = EventEndHelper.findEndsFromSemanticOrdering(message.get());
                Iterables.retainAll(messageEnds, ends);
                if (!messageEnds.isEmpty()) {
                    SingleEventEnd see = EventEndHelper.getSingleEventEnd(messageEnds.get(0), getSemanticTargetElement().get());
                    if (start == see.isStart()) {
                        return message;
                    }
                }
            }
        }
        return Options.newNone();
    }

    /**
     * Returns the message linked to the end (i.e. bottom side) of this
     * execution, if any.
     * 
     * @return the message linked to the end of this execution, if any.
     */
    public Option<Message> getEndMessage() {
        return getCompoundMessage(false);
    }

    /**
     * Tests whether this execution starts with a reflective message.
     * 
     * @return <code>true</code> if this execution has a reflective message
     *         linked to its start.
     */
    public boolean startsWithReflectiveMessage() {
        Option<Message> startMessage = getStartMessage();
        if (startMessage.some()) {
            return startMessage.get().isReflective();
        } else {
            return false;
        }
    }

    /**
     * Tests whether this execution ends with a reflective message.
     * 
     * @return <code>true</code> if this execution has a reflective message
     *         linked to its end.
     */
    public boolean endsWithReflectiveMessage() {
        Option<Message> finishMessage = getEndMessage();
        if (finishMessage.some()) {
            return finishMessage.get().isReflective();
        } else {
            return false;
        }
    }

    /**
     * Validate that the execution is reflective. Therefore, its start message
     * must be reflective and its return message must be null (Asynchronous
     * message) or reflexive.
     * 
     * @return if the execution is reflective
     */
    public boolean isReflective() {
        Option<Message> startMessage = getStartMessage();
        Option<Message> endMessage = getEndMessage();
        return startMessage.some() && startMessage.get().isReflective() && (!endMessage.some() || endMessage.get().isReflective());
    }

    @Override
    public ISequenceEvent getParentEvent() {
        ISequenceEvent parent = getHierarchicalParentEvent();

        List<ISequenceEvent> potentialSiblings = parent.getSubEvents();
        if (!potentialSiblings.contains(this)) {
            // look for parentOperand
            parent = getParentOperand().get();
        }
        return parent;
    }

    @Override
    public ISequenceEvent getHierarchicalParentEvent() {
        EObject viewContainer = this.view.eContainer();
        if (viewContainer instanceof View) {
            View parentView = (View) viewContainer;
            Option<ISequenceEvent> parentElement = ISequenceElementAccessor.getISequenceEvent(parentView);
            if (parentElement.some()) {
                return parentElement.get();
            }
        }
        throw new RuntimeException(MessageFormat.format(Messages.Execution_invalidExecutionContext, this));
    }

    /**
     * Finds the deepest Operand container including the position if existing.
     * 
     * @param verticalPosition
     *            the position where to look for the deepest operand
     * @return the deepest Operand convering this lifeline at this range
     * @see ISequenceEvent#getParentOperand()
     */
    @Override
    public Option<Operand> getParentOperand(final int verticalPosition) {
        return new ParentOperandFinder(this).getParentOperand(new Range(verticalPosition, verticalPosition));
    }

    /**
     * Finds the deepest Operand container including the position if existing.
     * 
     * @param range
     *            the range where to look for the deepest operand
     * @return the deepest Operand convering this lifeline at this range
     * @see ISequenceEvent#getParentOperand()
     */
    @Override
    public Option<Operand> getParentOperand(final Range range) {
        return new ParentOperandFinder(this).getParentOperand(range);
    }

    /**
     * Finds the deepest Operand container if existing.
     * 
     * @return the deepest Operand container if existing
     */
    @Override
    public Option<Operand> getParentOperand() {
        return new ParentOperandFinder(this).getParentOperand();
    }

    @Override
    public List<ISequenceEvent> getSubEvents() {
        return new SubEventsHelper(this).getSubEvents();
    }

    @Override
    public Collection<ISequenceEvent> getEventsToMoveWith() {
        Set<ISequenceEvent> toMove = Sets.newLinkedHashSet();
        List<ISequenceEvent> subEvents = getSubEvents();
        toMove.addAll(findLinkedExecutions(subEvents));
        toMove.addAll(getLinkedMessages());
        toMove.addAll(findCoveredExecutions(subEvents));
        toMove.addAll(subEvents);
        return toMove;
    }

    private Collection<? extends ISequenceEvent> findLinkedExecutions(List<ISequenceEvent> subEvents) {
        Set<Execution> linkedExecutions = Sets.newLinkedHashSet();
        for (Message message : Iterables.filter(subEvents, Message.class)) {
            if (this.equals(message.getSourceElement()) && message.getTargetElement() instanceof Execution) {
                Execution targetExecution = (Execution) message.getTargetElement();
                for (CompoundEventEnd messageCompoundEventEnd : Iterables.filter(EventEndHelper.findEndsFromSemanticOrdering(message), CompoundEventEnd.class)) {
                    for (CompoundEventEnd executionCompoundEventEnd : Iterables.filter(EventEndHelper.findEndsFromSemanticOrdering(targetExecution), CompoundEventEnd.class)) {
                        if (messageCompoundEventEnd.equals(executionCompoundEventEnd)) {
                            if (!this.equals(targetExecution)) {
                                linkedExecutions.add(targetExecution);
                            }
                        }
                    }
                }
            }
        }
        return linkedExecutions;
    }

    private Collection<? extends ISequenceEvent> findCoveredExecutions(List<ISequenceEvent> subEvents) {
        Collection<Execution> coveredExecutions = Lists.newArrayList();
        for (AbstractFrame frame : Iterables.filter(subEvents, AbstractFrame.class)) {
            Collection<ISequenceEvent> parentEvents = frame.computeParentEvents();
            parentEvents.remove(this);
            Iterables.addAll(coveredExecutions, Iterables.filter(parentEvents, Execution.class));
        }
        return coveredExecutions;
    }

    @Override
    public Range getVerticalRange() {
        return new SequenceNodeQuery(getNotationNode()).getVerticalRange();
    }

    @Override
    public boolean isLogicallyInstantaneous() {
        return false;
    }

    @Override
    public void setVerticalRange(Range range) throws IllegalStateException {
        RangeSetter.setVerticalRange(this, range);
    }

    @Override
    public Option<Lifeline> getLifeline() {
        return getParentLifeline();
    }

    @Override
    public boolean canChildOccupy(ISequenceEvent child, Range range) {
        return new SubEventsHelper(this).canChildOccupy(child, range);
    }

    @Override
    public boolean canChildOccupy(ISequenceEvent child, Range range, List<ISequenceEvent> eventsToIgnore, Collection<Lifeline> lifelines) {
        return new SubEventsHelper(this).canChildOccupy(child, range, eventsToIgnore, lifelines);
    }

    @Override
    public Range getOccupiedRange() {
        return new ISequenceEventQuery(this).getOccupiedRange();
    }

    /**
     * Sub-events can occur anywhere on a normal execution as long as it is
     * strictly inside.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Range getValidSubEventsRange() {
        Range range = getVerticalRange();
        if (range.width() > 2 * LayoutConstants.EXECUTION_CHILDREN_MARGIN) {
            return range.shrinked(LayoutConstants.EXECUTION_CHILDREN_MARGIN);
        } else {
            return range;
        }
    }

    /**
     * Finds all linked executions (by messages with CompoundEventEnd) from
     * executionEditPart. Depending on the value of investigateRecursively, it
     * will recursively investigate the linked execution.
     * 
     * @param recurse
     *            investigate recursively if true
     * @return the list of linked {@link Execution} from executionEditPart
     */
    public List<Execution> findLinkedExecutions(boolean recurse) {
        List<Execution> impactedExecutions = Lists.newArrayList();
        findLinkedExecutions(impactedExecutions, this, recurse);
        return impactedExecutions;
    }

    /**
     * Recursive function of the previous one that add the result in the
     * parameter list impactedExecutionEditPart.
     * 
     * @param impactedExecutioExecutions
     *            the list of linked {@link Execution} from executionEditPart
     * @param execution
     *            the current {@link Execution}
     * @param recurse
     *            investigate recursively if true
     */
    private void findLinkedExecutions(List<Execution> impactedExecutions, Execution execution, boolean recurse) {
        List<Message> messagesFrom = new ISequenceEventQuery(execution).getAllMessagesFrom();
        for (Message message : messagesFrom) {
            boolean targetsUnseenExecution = message.getTargetElement() instanceof Execution && !impactedExecutions.contains(message.getTargetElement());
            if (targetsUnseenExecution) {
                Execution targetExecution = (Execution) message.getTargetElement();
                for (CompoundEventEnd messageCompoundEventEnd : Iterables.filter(EventEndHelper.findEndsFromSemanticOrdering(message), CompoundEventEnd.class)) {
                    for (CompoundEventEnd executionCompoundEventEnd : Iterables.filter(EventEndHelper.findEndsFromSemanticOrdering(targetExecution), CompoundEventEnd.class)) {
                        if (messageCompoundEventEnd.equals(executionCompoundEventEnd)) {
                            if (!impactedExecutions.contains(targetExecution)) {
                                impactedExecutions.add(targetExecution);
                                if (recurse) {
                                    findLinkedExecutions(impactedExecutions, targetExecution, recurse);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the extended vertical range of this execution, i.e. the vertical
     * range of the execution including any extensions like branches for linked
     * start/end reflective messages. This corresponds to the range of all the
     * elements which are tied to the execution and will move along with it when
     * the execution is moved.
     * 
     * @return the extended vertical range of this execution.
     */
    public Range getExtendedVerticalRange() {
        Range result = getVerticalRange();
        for (Message linkedMessage : getLinkedMessages()) {
            // For non-reflective and non-deferred messages, this is a no-op.
            result = result.union(linkedMessage.getVerticalRange());
        }
        return result;
    }
}
