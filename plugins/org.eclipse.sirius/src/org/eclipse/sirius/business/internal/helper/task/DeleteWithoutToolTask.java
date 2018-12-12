/*******************************************************************************
 * Copyright (c) 2014, 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.helper.task;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.helper.task.DeleteEObjectTask;
import org.eclipse.sirius.business.api.helper.task.ICommandTask;
import org.eclipse.sirius.business.api.helper.task.TaskHelper;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.Messages;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Delete without tool task.
 * 
 * @author mporhel
 */
public class DeleteWithoutToolTask extends AbstractCompoundTask {

    private DRepresentationElement element;

    private Set<EObject> semanticElements;

    private ModelAccessor modelAccessor;

    private TaskHelper taskHelper;

    /**
     * Construct a delete without tool task.
     * 
     * @param element
     *            the current {@link DRepresentationElement} to delete.
     * @param semanticElements
     *            All the precomputed semantic elements to delete.
     * @param modelAccessor
     *            the model accessor to use for deletion
     * @param taskHelper
     *            the task helper to use to compute the DSemanticDecorator to
     *            delete
     */
    public DeleteWithoutToolTask(DRepresentationElement element, Set<EObject> semanticElements, ModelAccessor modelAccessor, TaskHelper taskHelper) {
        this.element = element;
        this.semanticElements = semanticElements;
        this.modelAccessor = modelAccessor;
        this.taskHelper = taskHelper;
    }

    /**
     * Construct a delete without tool task. The semantic elements to delete
     * will be computed from the given DRepresentationElement element.
     * 
     * @param element
     *            the current {@link DRepresentationElement} to delete.
     * @param modelAccessor
     *            the model accessor to use for deletion
     * @param taskHelper
     *            the task helper to use to compute the DSemanticDecorator to
     *            delete
     */
    public DeleteWithoutToolTask(DRepresentationElement element, ModelAccessor modelAccessor, TaskHelper taskHelper) {
        this.element = element;
        this.modelAccessor = modelAccessor;
        this.taskHelper = taskHelper;
    }

    @Override
    public String getLabel() {
        return Messages.DeleteWithoutToolTask_label;
    }

    @Override
    protected List<ICommandTask> prepareSubTasks() {
        List<ICommandTask> subTasks = Lists.newArrayList();
        Option<DRepresentation> parentRepresentation = new EObjectQuery(element).getRepresentation();
        if (parentRepresentation.some()) {

            if (semanticElements == null) {
                EObject semanticElement = element.getTarget();
                if (semanticElement == null) {
                    semanticElements = Collections.emptySet();
                } else {
                    semanticElements = Collections.singleton(semanticElement);
                }
            }

            // Now delete all the representation elements corresponding to the
            // semantic elements to delete
            final Set<DSemanticDecorator> diagramElements = taskHelper.getDElementToClearFromSemanticElements(parentRepresentation.get(), getAllSemanticElements());
            for (final DSemanticDecorator decorator : diagramElements) {
                subTasks.add(new DeleteEObjectTask(decorator, modelAccessor));
                addDialectSpecificAdditionalDeleteSubTasks(decorator, subTasks);
            }
        }

        // Now delete all the semantic elements
        for (final EObject semantic : semanticElements) {
            DeleteEObjectTask deleteSemanticElementTask = new DeleteEObjectTask(semantic, modelAccessor);
            subTasks.add(deleteSemanticElementTask);
        }

        return subTasks;
    }

    /**
     * This method can be overridden to add Dialect specific additional delete
     * tasks. A DeleteEObjectTask for the given decorator has already been added
     * to subtasks.
     * 
     * @param decorator
     *            the current decorator to delete
     * @param subTasks
     *            a List<ICommand> to complete
     */
    protected void addDialectSpecificAdditionalDeleteSubTasks(DSemanticDecorator decorator, List<ICommandTask> subTasks) {
        // Nothing to add per default.
    }

    /**
     * Get all semantic elements.
     * 
     * @return all semantic elements
     */
    private Set<EObject> getAllSemanticElements() {
        Set<EObject> result = Sets.newHashSet(semanticElements);
        for (EObject sem : semanticElements) {
            Iterators.addAll(result, sem.eAllContents());
        }
        return result;
    }
}
