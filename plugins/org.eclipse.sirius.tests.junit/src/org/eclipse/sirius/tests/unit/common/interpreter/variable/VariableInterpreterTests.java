/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.unit.common.interpreter.variable;

import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.common.tools.api.interpreter.EvaluationException;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterContext;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterStatus;
import org.eclipse.sirius.common.tools.api.interpreter.VariableType;
import org.eclipse.sirius.common.tools.internal.interpreter.AbstractInterpreter;
import org.eclipse.sirius.common.tools.internal.interpreter.FeatureInterpreter;
import org.eclipse.sirius.common.tools.internal.interpreter.ServiceInterpreter;
import org.eclipse.sirius.common.tools.internal.interpreter.VariableInterpreter;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.sirius.diagram.EdgeStyle;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.tools.api.interpreter.context.SiriusInterpreterContextFactory;

/**
 * A Test case for the {@link VariableInterpreter}.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public class VariableInterpreterTests extends TestCase {

    private IInterpreter interpreter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        interpreter = new VariableInterpreter();
    }

    public void testVariableInterpreterEvaluationWithNullParameters() {
        try {
            Object result = interpreter.evaluate(null, null);
            assertNull("The evaluation result must be null because we have not provided context and expression", result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationWithNullTargetAndEmptyExpression() {
        try {
            Object result = interpreter.evaluate(null, "");
            assertNull("The evaluation result must be null because we have not provided context and expression", result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationWithNullExpression() {
        try {
            EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
            ePackage.setName("p1");
            Object result = interpreter.evaluate(ePackage, null);
            assertNull("The evaluation result must be null because we have not provided context and expression", result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationWithEmptyExpression() {
        try {
            // Setup
            EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
            ePackage.setName("p1");
            // Test
            Object result = interpreter.evaluate(ePackage, "");
            // Check
            assertNull("The evaluation result must be null because we have not provided context and expression", result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationWithSpecificServiceExpression() {
        try {
            // Setup
            EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
            eAttribute.setName("a1");
            String varExampleName = "varExampleName";
            String varExampleValue = "varExampleValue";
            interpreter.setVariable(varExampleName, varExampleValue);
            // Test
            Object result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            // Check
            assertEquals("The evaluation result must be the varExampleValue", varExampleValue, result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationAfterVariableModifiations() {
        try {
            // Setup
            EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
            eAttribute.setName("a1");
            String varExampleName = "varExampleName";
            String varExampleValue = "varExampleValue";
            interpreter.setVariable(varExampleName, varExampleValue);
            // Test
            Object result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            // Check
            assertEquals("The evaluation result must be the varExampleValue", varExampleValue, result);

            // Set a new value
            String varExampleValue2 = "varExampleValue2";
            interpreter.setVariable(varExampleName, varExampleValue2);
            // Test
            result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            // Check
            assertEquals("The evaluation result must be the varExampleValue", varExampleValue2, result);

            // Unset the variable, then the next expected evaluation result is
            // the previous value.
            interpreter.unSetVariable(varExampleName);
            // Test
            result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            // Check
            assertEquals("The evaluation result must be the varExampleValue", varExampleValue, result);
        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }
    }

    public void testVariableInterpreterEvaluationWithIncorretServiceExpression() {
        try {
            // Setup
            DEdge dEdge = DiagramFactory.eINSTANCE.createDEdge();
            EdgeStyle edgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
            dEdge.setOwnedStyle(edgeStyle);
            // Test
            interpreter.evaluate(dEdge, VariableInterpreter.PREFIX + "not_a_variable");
            // Check
            fail("EvaluationException should be thrown");
        } catch (EvaluationException e) {

        }
    }

    public void testVariableInterpreterValidationOnSpecificVariableExpression() {
        // Setup
        DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setDomainClass(EcorePackage.eNAME + "." + EcorePackage.Literals.EPACKAGE.getName());
        IInterpreterContext context = SiriusInterpreterContextFactory.createInterpreterContext(diagramDescription, DescriptionPackage.Literals.DIAGRAM_DESCRIPTION__PRECONDITION_EXPRESSION);
        String varExampleName = "varExampleName";
        String varExampleValue = "varExampleValue";
        context.getVariables().put(varExampleName, VariableType.fromString(varExampleValue));
        // Test
        Collection<IInterpreterStatus> status = interpreter.validateExpression(context, VariableInterpreter.PREFIX + varExampleName);
        // Check
        assertNotNull(status);
        assertTrue("The validation should be successful", status.isEmpty());
    }

    public void testVariableInterpreterValidationOnIncorrectVariableExpression() {
        // Setup
        DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setDomainClass(EcorePackage.eNAME + "." + EcorePackage.Literals.EPACKAGE.getName());
        IInterpreterContext context = SiriusInterpreterContextFactory.createInterpreterContext(diagramDescription, DescriptionPackage.Literals.DIAGRAM_DESCRIPTION__PRECONDITION_EXPRESSION);
        // Test
        Collection<IInterpreterStatus> status = interpreter.validateExpression(context, VariableInterpreter.PREFIX + "not_a_variable");
        // Check
        assertNotNull(status);
        assertEquals("The validation should return a IInterpreterStatus to show the error in the expression", 1, status.size());
        IInterpreterStatus interpreterStatus = status.iterator().next();
        assertEquals(DescriptionPackage.Literals.DIAGRAM_DESCRIPTION__PRECONDITION_EXPRESSION, interpreterStatus.getField());
    }

    public void testVariableInterpreterCollectionEvaluationOnArrayValues() {
        try {
            // Setup
            EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
            eAttribute.setName("a1");
            String varExampleName = "varWithArray";
            String[] arrayValue = new String[] { eAttribute.getName() };
            interpreter.setVariable(varExampleName, arrayValue);

            // Test 1: evaluate returns the String array.
            Object result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            assertEquals("The evaluate method should return the array.", arrayValue, result);

            // Test 2: evaluateCollection returns an empty
            // Collection<EObject>the String array.
            result = interpreter.evaluateCollection(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            assertTrue("The evaluateCollection method should return a collection.", result instanceof Collection);
            assertTrue("The evaluateCollection method should return a en emtpy collection, the array contains only Strings.", ((Collection<?>) result).isEmpty());

            // Change the value
            EObject[] eobjectArrayValue = new EObject[] { eAttribute };
            interpreter.setVariable(varExampleName, eobjectArrayValue);

            // Test 3: evaluate returns the EObject array.
            result = interpreter.evaluate(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            assertEquals("The evaluate method should return the array.", eobjectArrayValue, result);

            // Test 4: evaluateCollection returns a Collection<EObject> with the
            // array content
            result = interpreter.evaluateCollection(eAttribute, VariableInterpreter.PREFIX + varExampleName);
            assertTrue("The evaluateCollection method should return a collection.", result instanceof Collection);
            assertEquals("The evaluateCollection method should return a collection with the content of the array.", eobjectArrayValue[0], ((Collection<?>) result).iterator().next());

        } catch (EvaluationException e) {
            fail("EvaluationException should not be thrown");
        }

        try {
            assertEquals("The current test should be updated.", AbstractInterpreter.class, VariableInterpreter.class.getMethod("evaluateCollection", EObject.class, String.class).getDeclaringClass());
            assertEquals("FeatureInterpreterTests requires a similirar test.", AbstractInterpreter.class, FeatureInterpreter.class.getMethod("evaluateCollection", EObject.class, String.class).getDeclaringClass());
            assertEquals("ServiceInterpreterTests requires a similirar test.", AbstractInterpreter.class, ServiceInterpreter.class.getMethod("evaluateCollection", EObject.class, String.class).getDeclaringClass());
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException should not be thrown");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        interpreter = null;
        super.tearDown();
    }
}
