/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.helper;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.logger.RuntimeLoggerManager;
import org.eclipse.sirius.common.tools.DslCommonPlugin;
import org.eclipse.sirius.common.tools.api.interpreter.EvaluationException;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterSiriusVariables;
import org.eclipse.sirius.common.tools.api.util.EObjectCouple;
import org.eclipse.sirius.common.tools.api.util.RefreshIdsHolder;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeListElement;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.sirius.diagram.Messages;
import org.eclipse.sirius.diagram.NodeStyle;
import org.eclipse.sirius.diagram.ResizeKind;
import org.eclipse.sirius.diagram.business.internal.metamodel.description.extensions.INodeMappingExt;
import org.eclipse.sirius.diagram.business.internal.metamodel.description.operations.SiriusElementMappingSpecOperations;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.tools.api.profiler.SiriusTasksKey;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.sirius.viewpoint.Style;
import org.eclipse.sirius.viewpoint.description.style.StyleDescription;
import org.eclipse.sirius.viewpoint.description.style.StylePackage;

/**
 * Utility class to factor customizations for ContainerMapping and related.
 * 
 * @author pcdavid
 */
public final class NodeMappingHelper {
    private IInterpreter interpreter;

    private StyleHelper styleHelper;

    /**
     * Create the helper.
     * 
     * @param interpreter
     *            interpreter used to evaluate expressions.
     */
    public NodeMappingHelper(IInterpreter interpreter) {
        this.interpreter = interpreter;
        this.styleHelper = new StyleHelper(interpreter);
    }

    /**
     * Implementation of
     * {@link NodeMapping#getNodesCandidates(EObject, EObject)}.
     * 
     * @param self
     *            the node mapping.
     * @param semanticOrigin
     *            the root element.
     * @param container
     *            the container element.
     * @return all semantic elements that are candidates for the mapping.
     */
    public static EList<EObject> getNodesCandidates(NodeMapping self, EObject semanticOrigin, EObject container) {
        SiriusPlugin.getDefault().warning(Messages.NodeMappingHelper_methodInvocationErrorMsg, null);
        return self.getNodesCandidates(semanticOrigin, container, null);
    }

    /**
     * ! Implementation of
     * {@link NodeMapping#getNodesCandidates(EObject, EObject, EObject)}.
     * 
     * @param self
     *            the node mapping.
     * @param semanticOrigin
     *            the root element.
     * @param container
     *            the container element.
     * @param containerView
     *            the view of the container.
     * @return all semantic elements that are candidates for the mapping.
     */
    public static EList<EObject> getNodesCandidates(INodeMappingExt self, EObject semanticOrigin, final EObject container, final EObject containerView) {
        DslCommonPlugin.PROFILER.startWork(SiriusTasksKey.GET_NODE_CANDIDATES_KEY);
        EObject safeContainer;
        if (container == null) {
            safeContainer = semanticOrigin;
        } else {
            safeContainer = container;
        }

        DDiagram diagram = null;
        if (containerView instanceof DDiagramElement) {
            diagram = ((DDiagramElement) containerView).getParentDiagram();
        } else if (containerView instanceof DDiagram) {
            diagram = (DDiagram) containerView;
        }
        final EObjectCouple couple = new EObjectCouple(semanticOrigin, safeContainer, RefreshIdsHolder.getOrCreateHolder(diagram));
        EList<EObject> result = self.getCandidatesCache().get(couple);
        if (result == null) {
            result = new UniqueEList<EObject>();
            Iterator<EObject> it;
            it = DiagramElementMappingHelper.getSemanticIterator(self, semanticOrigin, diagram);
            if (self.getDomainClass() != null) {
                while (it.hasNext()) {
                    final EObject eObj = it.next();
                    if (NodeMappingHelper.isInstanceOf(eObj, self.getDomainClass()) && SiriusElementMappingSpecOperations.checkPrecondition(self, eObj, safeContainer, containerView)) {
                        result.add(eObj);
                    }
                }
            } else {
                SiriusPlugin.getDefault().error(Messages.NodeMappingHelper_nodeCreationErrorMsg, new RuntimeException());
            }
            self.getCandidatesCache().put(couple, result);
        }
        DslCommonPlugin.PROFILER.stopWork(SiriusTasksKey.GET_NODE_CANDIDATES_KEY);
        return result;
    }

    /**
     * Implementation of
     * {@link NodeMapping#createNode(EObject, EObject, DDiagram)}.
     * 
     * @param self
     *            the node mapping.
     * @param modelElement
     *            the semantic element.
     * @param container
     *            the semantic container of the element.
     * @param diagram
     *            the diagram.
     * @return the node created.
     */
    public DNode createNode(INodeMappingExt self, EObject modelElement, EObject container, DDiagram diagram) {
        final DNode newNode = DiagramFactory.eINSTANCE.createDNode();

        // getting the right style description : default or conditional
        final NodeStyleDescription style = (NodeStyleDescription) new MappingHelper(interpreter).getBestStyleDescription(self, modelElement, newNode, container, diagram);

        newNode.setTarget(modelElement);
        newNode.setActualMapping(self);

        DiagramElementMappingHelper.refreshSemanticElements(self, newNode, interpreter);

        interpreter.setVariable(IInterpreterSiriusVariables.DIAGRAM, diagram);
        interpreter.setVariable(IInterpreterSiriusVariables.VIEW, newNode);
        if (style != null && style.getLabelExpression() != null) {
            try {
                final String name = interpreter.evaluateString(modelElement, style.getLabelExpression());
                newNode.setName(name);
            } catch (final EvaluationException e) {
                RuntimeLoggerManager.INSTANCE.error(style, StylePackage.eINSTANCE.getBasicLabelStyleDescription_LabelExpression(), e);
            }
        }
        if (style != null) {
            newNode.setResizeKind(style.getResizeKind());

            styleHelper.setComputedSize(newNode, style);
        }
        final NodeStyle bestStyle = (NodeStyle) new MappingHelper(interpreter).getBestStyle(self, modelElement, newNode, container, diagram);
        if (bestStyle != null) {
            newNode.setOwnedStyle(bestStyle);
        }

        self.addDoneNode(newNode);

        interpreter.unSetVariable(IInterpreterSiriusVariables.VIEW);
        interpreter.unSetVariable(IInterpreterSiriusVariables.DIAGRAM);

        /*
         * Iterate over the border mapping of the node to initialize the border
         * nodes.
         */
        self.createBorderingNodes(modelElement, newNode, Collections.EMPTY_LIST, diagram);
        if (newNode.getOwnedStyle() != null) {
            Option<NodeStyle> noPreviousStyle = Options.newNone();
            new StyleHelper(interpreter).refreshStyle(newNode.getOwnedStyle(), noPreviousStyle);
        }
        return newNode;
    }

    /**
     * Implementation of {@link NodeMapping#updateNode(DNode)}.
     * 
     * @param self
     *            the node mapping.
     * @param node
     *            the node to update.
     */
    public void updateNode(NodeMapping self, DNode node) {
        final EObject modelElement = node.getTarget();

        final DSemanticDecorator container = (DSemanticDecorator) node.eContainer();
        NodeStyleDescription style = null;

        // getting the right style description : default or conditional
        if (container != null) {
            style = (NodeStyleDescription) new MappingHelper(interpreter).getBestStyleDescription(self, modelElement, node, container.getTarget(), node.getParentDiagram());
        }

        if (style != null && style.getLabelExpression() != null) {
            try {
                interpreter.setVariable(IInterpreterSiriusVariables.DIAGRAM, node.getParentDiagram());
                interpreter.setVariable(IInterpreterSiriusVariables.VIEW, node);
                final String name = interpreter.evaluateString(modelElement, style.getLabelExpression());
                node.setName(name);
            } catch (final EvaluationException e) {
                RuntimeLoggerManager.INSTANCE.error(style, StylePackage.eINSTANCE.getBasicLabelStyleDescription_LabelExpression(), e);
            } finally {
                interpreter.unSetVariable(IInterpreterSiriusVariables.DIAGRAM);
                interpreter.unSetVariable(IInterpreterSiriusVariables.VIEW);
            }
        }

        if (style != null && style.getTooltipExpression() != null) {
            try {
                interpreter.setVariable(IInterpreterSiriusVariables.DIAGRAM, node.getParentDiagram());
                interpreter.setVariable(IInterpreterSiriusVariables.VIEW, node);
                final String tooltip = interpreter.evaluateString(modelElement, style.getTooltipExpression());
                node.setTooltipText(tooltip);
            } catch (final EvaluationException e) {
                RuntimeLoggerManager.INSTANCE.error(style, StylePackage.eINSTANCE.getTooltipStyleDescription_TooltipExpression(), e);
            } finally {
                interpreter.unSetVariable(IInterpreterSiriusVariables.DIAGRAM);
                interpreter.unSetVariable(IInterpreterSiriusVariables.VIEW);
            }
        }

        /*
         * Getting the node size
         */
        if (node.getResizeKind() == ResizeKind.NONE_LITERAL) {
            styleHelper.setComputedSize(node, style);
        }

        // semantic elements
        DiagramElementMappingHelper.refreshSemanticElements(self, node, interpreter);

        EObject containerVariable = null;
        if (node.eContainer() instanceof DSemanticDecorator) {
            containerVariable = ((DSemanticDecorator) node.eContainer()).getTarget();
        }

        final StyleDescription bestStyleDescription = new MappingHelper(interpreter).getBestStyleDescription(self, modelElement, node, containerVariable, node.getParentDiagram());
        Style bestStyle = node.getStyle();
        if ((bestStyle == null || bestStyle.getDescription() != bestStyleDescription) && bestStyleDescription != null) {
            bestStyle = styleHelper.createStyle(bestStyleDescription);
        }

        styleHelper.setAndRefreshStyle(node, node.getStyle(), bestStyle);

        self.addDoneNode(node);
    }

    /**
     * Implementation of {@link NodeMapping#createListElement(EObject)}.
     * 
     * @param self
     *            the node mapping.
     * @param modelElement
     *            the semantic model element.
     * @param diagram
     *            the diagram.
     * @return the element created.
     */
    public DNodeListElement createListElement(NodeMapping self, EObject modelElement, DDiagram diagram) {

        final DNodeListElement newNode = DiagramFactory.eINSTANCE.createDNodeListElement();

        // getting the right style description : default or conditional
        final NodeStyleDescription style = (NodeStyleDescription) new MappingHelper(interpreter).getBestStyleDescription(self, modelElement, newNode, null, diagram);

        newNode.setTarget(modelElement);
        newNode.setActualMapping(self);

        // semantic elements
        DiagramElementMappingHelper.refreshSemanticElements(self, newNode, interpreter);

        if (style != null && !StringUtil.isEmpty(style.getLabelExpression())) {
            newNode.setName(DiagramElementMappingHelper.computeLabel(newNode, style, diagram, interpreter));
        }

        /*
         * Getting the node size
         */
        self.addDoneNode(newNode);

        final NodeStyle bestStyle = (NodeStyle) new MappingHelper(interpreter).getBestStyle(self, modelElement, newNode, null, diagram);
        if (bestStyle != null) {
            newNode.setOwnedStyle(bestStyle);
        }

        return newNode;
    }

    /**
     * Implementation of {@link NodeMapping#updateListElement(DNodeListElement)}
     * .
     * 
     * @param self
     *            the node mapping.
     * @param listElement
     *            the view node list element to update.
     */
    public void updateListElement(NodeMapping self, DNodeListElement listElement) {

        final EObject modelElement = listElement.getTarget();
        final DSemanticDecorator container = (DSemanticDecorator) listElement.eContainer();
        NodeStyleDescription style = null;

        // getting the right style description : default or conditional
        if (container != null) {
            style = (NodeStyleDescription) new MappingHelper(interpreter).getBestStyleDescription(self, modelElement, listElement, container.getTarget(), listElement.getParentDiagram());
        }

        if (style != null && !StringUtil.isEmpty(style.getLabelExpression())) {
            listElement.setName(DiagramElementMappingHelper.computeLabel(listElement, style, listElement.getParentDiagram(), interpreter));
        }

        EObject containerVariable = null;
        if (listElement.eContainer() instanceof DSemanticDecorator) {
            containerVariable = ((DSemanticDecorator) listElement.eContainer()).getTarget();
        }

        // semantic elements
        DiagramElementMappingHelper.refreshSemanticElements(self, listElement, interpreter);

        final NodeStyle bestStyle = (NodeStyle) new MappingHelper(interpreter).getBestStyle(self, modelElement, listElement, containerVariable, listElement.getParentDiagram());

        if ((bestStyle == null || bestStyle.getDescription() != style) && style != null) {
            listElement.setOwnedStyle(bestStyle);
        }
        styleHelper.setAndRefreshStyle(listElement, listElement.getStyle(), bestStyle);
        self.addDoneNode(listElement);
    }

    /**
     * Implementation of {@link NodeMapping#findDNodeFromEObject(EObject)}.
     * 
     * @param self
     *            the node mapping.
     * @param object
     *            the semantic element.
     * @return the node found.
     */
    public static EList<DDiagramElement> findDNodeFromEObject(INodeMappingExt self, EObject object) {
        EList result = self.getViewNodesDone().get(object);
        if (result == null) {
            result = new BasicEList<DDiagramElement>();
        }
        return result;
    }

    /**
     * Implementation of {@link NodeMapping#addDoneNode(DSemanticDecorator)}.
     * 
     * @param self
     *            the node mapping.
     * @param node
     *            the node to add.
     */
    public static void addDoneNode(INodeMappingExt self, DSemanticDecorator node) {
        EList<DSemanticDecorator> list = self.getViewNodesDone().get(node.getTarget());
        if (list == null) {
            list = new BasicEList<DSemanticDecorator>();
            self.getViewNodesDone().put(node.getTarget(), list);
        }
        list.add(node);
    }

    /**
     * Implementation of {@link NodeMapping#clearDNodesDone()}.
     * 
     * @param self
     *            the node mapping.
     */
    public static void clearDNodesDone(INodeMappingExt self) {
        self.getViewNodesDone().clear();
        self.getCandidatesCache().clear();
    }

    /**
     * Return <code>true</code> if <code>eObj</code> is an instance of
     * <code>typename</code>.
     * 
     * @param eObj
     *            an object.
     * @param typename
     *            the name of the type to check.
     * @return <code>true</code> if <code>eObj</code> is an instance of
     *         <code>typename</code>.
     */
    private static boolean isInstanceOf(final EObject eObj, final String typename) {
        DslCommonPlugin.PROFILER.startWork(SiriusTasksKey.INSTANCE_OF_KEY);
        final boolean result = SiriusPlugin.getDefault().getModelAccessorRegistry().getModelAccessor(eObj).eInstanceOf(eObj, typename);
        DslCommonPlugin.PROFILER.stopWork(SiriusTasksKey.INSTANCE_OF_KEY);
        return result;
    }

}
