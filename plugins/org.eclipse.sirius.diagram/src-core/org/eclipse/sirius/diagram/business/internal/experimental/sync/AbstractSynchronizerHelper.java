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
package org.eclipse.sirius.diagram.business.internal.experimental.sync;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.DslCommonPlugin;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.DragAndDropTarget;
import org.eclipse.sirius.diagram.business.api.query.DiagramElementMappingQuery;
import org.eclipse.sirius.diagram.business.api.query.EObjectQuery;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.collect.MultipleCollection;
import org.eclipse.sirius.tools.api.interpreter.InterpreterUtil;
import org.eclipse.sirius.tools.api.profiler.SiriusTasksKey;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * A common helper for nodes and edges.
 * 
 * @author mchauvin
 */
public abstract class AbstractSynchronizerHelper {

    /** the semantic diagram. */
    protected final DSemanticDiagram diagram;

    /** the model accessor. **/
    protected ModelAccessor accessor;

    /** the interpreter. */
    protected final IInterpreter interpreter;

    /** the session. */
    protected Session session;

    /** the main synchronizer. */
    protected DDiagramSynchronizer sync;

    /**
     * comes from tool ?.
     */
    protected boolean tool;

    /**
     * A cache for candidates common to all mapping with empty semantic
     * candidates expression (i.e. eAllContents() from root) and with same
     * domainClass.
     */
    private Multimap<String, EObject> candidatesMap = LinkedHashMultimap.create();

    /**
     * Constructor.
     * 
     * @param sync
     *            the main synchronizer
     * @param accessor
     *            the model accessor
     * @param diagram
     *            the semantic diagram
     */
    public AbstractSynchronizerHelper(final DDiagramSynchronizer sync, final DSemanticDiagram diagram, final ModelAccessor accessor) {
        this.diagram = diagram;
        this.accessor = accessor;
        this.session = new EObjectQuery(diagram.getTarget()).getSession();
        if (session == null) {
            this.session = new EObjectQuery(diagram).getSession();
        }
        this.interpreter = session != null ? session.getInterpreter() : InterpreterUtil.getInterpreter(diagram.getTarget());
        this.sync = sync;
        this.tool = false;
    }

    /**
     * 
     * Evaluate the semantic candidates for the given context.
     * 
     * @param container
     *            the container of the potential views.
     * @param mapping
     *            the mapping of the potential candidates.
     * @return a collection of semantic candidates.
     */
    protected Collection<EObject> evaluateCandidateExpression(final DragAndDropTarget container, final DiagramElementMapping mapping) {
        return new DiagramElementMappingQuery(mapping).evaluateCandidateExpression(diagram, interpreter, container);
    }

    /**
     * Get all candidates corresponding to the the given mapping in the current
     * session.
     * 
     * @param mapping
     *            mapping to use
     * @return candidates
     */
    protected Collection<EObject> getAllCandidates(DiagramElementMapping mapping) {
        final Collection<EObject> semantics = new MultipleCollection<EObject>();

        Option<String> domainClass = new DiagramElementMappingQuery(mapping).getDomainClass();
        if (domainClass.some()) {
            if (candidatesMap.containsKey(domainClass.get())) {
                semantics.addAll(candidatesMap.get(domainClass.get()));
            } else {
                for (final Resource resource : session.getSemanticResources()) {
                    for (final EObject root : resource.getContents()) {
                        semantics.addAll(this.accessor.eAllContents(root, domainClass.get()));
                    }
                }
                candidatesMap.putAll(domainClass.get(), semantics);
            }
        }
        return semantics;
    }

    /**
     * .
     * 
     * @param container
     *            .
     * @param mapping
     *            .
     * @return .
     */
    protected Collection<EObject> getPreviousSemanticsElements(DragAndDropTarget container, DiagramElementMapping mapping) {
        Collection<EObject> transformed = Collections2.transform(sync.getPreviousDiagramElements(container, mapping), new Function<DDiagramElement, EObject>() {
            @Override
            public EObject apply(final DDiagramElement from) {
                return from.getTarget();
            }
        });
        return ImmutableSet.copyOf(Collections2.filter(transformed, Predicates.and(Predicates.notNull(), new Predicate<EObject>() {
            // We don't keep semantic element that is invalid
            @Override
            public boolean apply(final EObject input) {
                return (input.eContainer() != null && input.eContainer().eResource() != null) || input.eResource() != null;
            }
        })));
    }

    /**
     * Get the semantic candidates for the given context.
     * 
     * @param container
     *            the container of future views created from potential
     *            candidates.
     * @param mapping
     *            the current mapping
     * @return candidates
     */
    protected Iterable<EObject> getSemanticCandidates(DragAndDropTarget container, DiagramElementMapping mapping) {
        DslCommonPlugin.PROFILER.startWork(SiriusTasksKey.GET_NODE_CANDIDATES_KEY);
        Iterable<EObject> semantics = Lists.newArrayList();
        boolean synchronizedAndCreateElement = new DiagramElementMappingQuery(mapping).isSynchronizedAndCreateElement(diagram);
        synchronizedAndCreateElement = tool || synchronizedAndCreateElement;
        if (new DiagramElementMappingQuery(mapping).hasCandidatesExpression()) {
            final Collection<EObject> allCandidates = evaluateCandidateExpression(container, mapping);
            if (synchronizedAndCreateElement) {
                /* Check domain class */
                final Option<String> domainClassOption = new DiagramElementMappingQuery(mapping).getDomainClass();
                if (domainClassOption.some()) {
                    Predicate<EObject> domainClass = new Predicate<EObject>() {
                        @Override
                        public boolean apply(EObject input) {
                            return accessor.eInstanceOf(input, domainClassOption.get());
                        }
                    };
                    semantics = Iterables.concat(semantics, Iterables.filter(allCandidates, domainClass));
                }
            } else {
                sync.forceRetrieve();
                final Collection<EObject> previousSemanticsElements = getPreviousSemanticsElements(container, mapping);
                sync.resetforceRetrieve();
                final Predicate<EObject> stillCandidate = new Predicate<EObject>() {
                    @Override
                    public boolean apply(final EObject input) {
                        return allCandidates.contains(input);
                    }
                };

                final Collection<EObject> keeped = ImmutableSet.copyOf(Collections2.filter(previousSemanticsElements, stillCandidate));
                semantics = Iterables.concat(semantics, keeped);
            }
        } else {
            if (synchronizedAndCreateElement) {
                semantics = Iterables.concat(semantics, getAllCandidates(mapping));
            } else {
                sync.forceRetrieve();
                semantics = Iterables.concat(semantics, getPreviousSemanticsElements(container, mapping));
                sync.resetforceRetrieve();
            }
        }
        DslCommonPlugin.PROFILER.stopWork(SiriusTasksKey.GET_NODE_CANDIDATES_KEY);
        return semantics;
    }

    public void setTool(boolean b) {
        tool = b;
    }

}
