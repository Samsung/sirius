/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.filter.spec;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.business.api.logger.RuntimeLoggerManager;
import org.eclipse.sirius.common.tools.api.interpreter.EvaluationException;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.EObjectVariableValue;
import org.eclipse.sirius.diagram.TypedVariableValue;
import org.eclipse.sirius.diagram.VariableValue;
import org.eclipse.sirius.diagram.description.filter.FilterPackage;
import org.eclipse.sirius.diagram.description.filter.impl.VariableFilterImpl;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.sirius.viewpoint.description.AbstractVariable;
import org.eclipse.sirius.viewpoint.description.TypedVariable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * Customizations for the implementation of <code>VariableFilter</code>.
 * 
 * @author cbrun
 * 
 */
public class VariableFilterSpec extends VariableFilterImpl {

    private Multimap<String, EObject> variables;

    private Map<String, Object> typedVariables;

    private DDiagram curDiagram;

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.description.filter.impl.FilterImpl#isVisible(DDiagramElement)
     */
    @Override
    public boolean isVisible(final DDiagramElement element) {
        //
        // If the element has no container it will be deleted.
        // Let's return false.
        if (element.getParentDiagram() == null) {
            return false;
        }

        /*
         * We'll init the variables using the history contained in the
         * viewpoint.
         */
        getVariablesFromDiagram(element.getParentDiagram());
        boolean valid = true;
        final IInterpreter interpreter = SiriusPlugin.getDefault().getInterpreterRegistry().getInterpreter(element);
        /*
         * Prepare the variables...
         */
        if (variables != null) {
            for (final String key : variables.keySet()) {
                final Collection<EObject> value = variables.get(key);
                interpreter.setVariable(key, value);
            }
        }
        if (typedVariables != null) {
            for (final String key : typedVariables.keySet()) {
                final Object value = typedVariables.get(key);
                interpreter.setVariable(key, value);
            }
        }

        if (getSemanticConditionExpression() != null) {
            final EObject target = ((DSemanticDecorator) element).getTarget();
            if (target == null || target.eResource() == null) {
                valid = false;
            } else {
                try {
                    valid = interpreter.evaluateBoolean(element.getTarget(), getSemanticConditionExpression());
                } catch (final EvaluationException e) {
                    RuntimeLoggerManager.INSTANCE.error(this, FilterPackage.eINSTANCE.getVariableFilter_SemanticConditionExpression(), e);
                }
            }
        }

        // Unset the variables
        if (variables != null) {
            for (final String key : variables.keySet()) {
                interpreter.unSetVariable(key);
            }
        }
        if (typedVariables != null) {
            for (final String key : typedVariables.keySet()) {
                interpreter.unSetVariable(key);
            }
        }
        return valid;
    }

    private void getVariablesFromDiagram(final DDiagram dDiagram) {
        if (curDiagram == null || dDiagram != curDiagram) {
            variables = ArrayListMultimap.create();
            typedVariables = Maps.newLinkedHashMap();

            if (dDiagram.getFilterVariableHistory() != null) {
                final Iterator<VariableValue> it = dDiagram.getFilterVariableHistory().getOwnedValues().iterator();
                while (it.hasNext()) {
                    final VariableValue value = it.next();
                    if (value instanceof EObjectVariableValue) {
                        EObjectVariableValue objectVarValue = (EObjectVariableValue) value;
                        if (getOwnedVariables().contains(objectVarValue.getVariableDefinition())) {
                            variables.put(((AbstractVariable) objectVarValue.getVariableDefinition()).getName(), objectVarValue.getModelElement());
                        }
                    } else if (value instanceof TypedVariableValue) {
                        TypedVariableValue typeVariableValue = (TypedVariableValue) value;
                        TypedVariable variableDefinition = typeVariableValue.getVariableDefinition();
                        if (getOwnedVariables().contains(variableDefinition)) {
                            // Instantiate value which dataType is defined on
                            // TypedVariable.valueType.
                            Object convertedObject = EcoreUtil.createFromString(variableDefinition.getValueType(), typeVariableValue.getValue());
                            typedVariables.put(((AbstractVariable) typeVariableValue.getVariableDefinition()).getName(), convertedObject);
                        }
                    }
                }
            }
            curDiagram = dDiagram;
        }
    }

    @Override
    public void resetVariables() {
        this.curDiagram = null;
    }
}
