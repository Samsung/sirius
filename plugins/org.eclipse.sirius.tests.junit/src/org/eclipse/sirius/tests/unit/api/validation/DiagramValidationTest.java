/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.unit.api.validation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.ui.util.WorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DiagramPlugin;
import org.eclipse.sirius.diagram.ui.internal.providers.SiriusMarkerNavigationProvider;
import org.eclipse.sirius.diagram.ui.part.ValidateAction;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.support.api.SiriusDiagramTestCase;
import org.eclipse.sirius.tests.support.api.TestsUtil;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Validation tests.
 * 
 * @author dlecan
 */
public class DiagramValidationTest extends SiriusDiagramTestCase {

    private static final String SEMANTIC_MODEL_PATH = "/" + SiriusTestsPlugin.PLUGIN_ID + "/data/unit/validation/My.ecore";

    private static final String MODELER_PATH = "/" + SiriusTestsPlugin.PLUGIN_ID + "/data/unit/validation/ticket1666.odesign";

    private static final String VIEWPOINT_NAME = "Ticket 1666";

    private static final String REPRESENTATION_DESC_NAME = "Validation";

    private static final String REPRESENTATION_DESC_NAME_BREAKDOWN = "Breakdown";

    private static final String ECLASS_NAME = "demo2";

    private DDiagram diagram;

    private IEditorPart editorPart;

    private ILogListener logListener;

    private EPackage subTicket1666_1EPackage;

    private EPackage rootEPackage;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        TestsUtil.emptyEventsFromUIThread();
        logListener = createMock(ILogListener.class);
        replay(logListener);
        DiagramPlugin.getDefault().getLog().addLogListener(logListener);
        DiagramUIPlugin.getPlugin().getLog().addLogListener(logListener);

        genericSetUp(SEMANTIC_MODEL_PATH, MODELER_PATH);

        initViewpoint(VIEWPOINT_NAME);
        subTicket1666_1EPackage = ((EPackage) semanticModel).getESubpackages().get(1);
        rootEPackage = ((EPackage) semanticModel).getESubpackages().get(2);
    }

    /**
     * Test case. It checks that source point location of a specific edge does
     * not change after zoom in operation.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testValidation() throws Exception {
        diagram = (DDiagram) createRepresentation(REPRESENTATION_DESC_NAME, subTicket1666_1EPackage);

        editorPart = DialectUIManager.INSTANCE.openEditor(session, diagram, new NullProgressMonitor());
        TestsUtil.synchronizationWithUIThread();
        ConstraintStub.setEClassName(ECLASS_NAME);
        ConstraintStub.setCalled(false);

        // Save edit part to create session .aird file
        editorPart.doSave(new NullProgressMonitor());

        // Run validation
        WorkbenchPartDescriptor workbenchPartDescriptor = new WorkbenchPartDescriptor(editorPart.getSite().getId(), editorPart.getClass(), editorPart.getSite().getPage());
        ValidateAction va = new ValidateAction(workbenchPartDescriptor);
        va.run();

        assertTrue("Validation constraint has not been called", ConstraintStub.hasBeenCalled());

        // Get session .aird file to find validation warnings
        IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
        file.refreshLocal(1, new NullProgressMonitor());
        IMarker[] findUIMarkers = file.findMarkers(SiriusMarkerNavigationProvider.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
        assertTrue("At least one marker must be found", findUIMarkers.length >= 1);

        boolean found = false;
        for (int i = 0; i < findUIMarkers.length && !found; i++) {
            IMarker iMarker = findUIMarkers[i];
            String message = (String) iMarker.getAttribute(IMarker.MESSAGE);
            found = message != null && message.indexOf(ECLASS_NAME) > -1;
        }
        assertTrue("Marker created by validation was not found", found);

        int numberOfShellBefore = getShellsNumber();
        if (editorPart instanceof IDiagramWorkbenchPart) {
            final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) editorPart;
            ValidateAction.runNonUIValidation(part.getDiagram());
        }
        assertEquals("A new shell has not been disposed.", numberOfShellBefore, getShellsNumber());

        verify(logListener);
    }

    /**
     * Test there is only one error by elements not validated and not many
     * errors message duplicate for same element. See VP-2940
     * 
     * Also check that there is only one semantic error for the root element.
     * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=441642
     * 
     * @throws CoreException
     *             Test errors.
     */
    public void testValidationOnlyOneErrorMessage() throws CoreException {
        diagram = (DDiagram) createRepresentation(REPRESENTATION_DESC_NAME_BREAKDOWN, rootEPackage);

        editorPart = DialectUIManager.INSTANCE.openEditor(session, diagram, new NullProgressMonitor());
        TestsUtil.synchronizationWithUIThread();
        ConstraintStub.setCalled(false);

        // Save edit part to create session .aird file
        editorPart.doSave(new NullProgressMonitor());

        // Run validation
        WorkbenchPartDescriptor workbenchPartDescriptor = new WorkbenchPartDescriptor(editorPart.getSite().getId(), editorPart.getClass(), editorPart.getSite().getPage());
        ValidateAction va = new ValidateAction(workbenchPartDescriptor);
        va.run();

        // Get session .aird file to find validation warnings
        IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
        file.refreshLocal(1, new NullProgressMonitor());
        IMarker[] foundUIMarkers = file.findMarkers(SiriusMarkerNavigationProvider.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
        assertEquals("Five markers must be found", 5, foundUIMarkers.length);

        // Check markers
        EPackage root = (EPackage) ((DSemanticDecorator) diagram).getTarget();
        checkSemanticMarkers(root, foundUIMarkers, 1);

        EPackage ap1 = root.getESubpackages().get(0);
        checkMarkers(ap1, foundUIMarkers, 1, 1);

        EPackage ap2 = ap1.getESubpackages().get(0);
        checkMarkers(ap2, foundUIMarkers, 1, 1);

        verify(logListener);
    }

    /**
     * Get the first {@link DNode} with target equals to the parameter.
     * 
     * @param target
     *            target to find
     * @return first valid {@link DNode} found
     */
    private DDiagramElement getGraphicNodeElement(final EObject target) {
        return Iterables.find(diagram.getDiagramElements(), new Predicate<DDiagramElement>() {
            @Override
            public boolean apply(DDiagramElement element) {
                return element instanceof DNode && element.getTarget() == target;
            }
        });
    }

    /**
     * Check markers for {@code semanticElement}.
     * 
     * @param semanticElement
     *            semantic element to check
     * @param foundMarkers
     *            markers found
     * @param expectedNbViewMarker
     *            expected number of view marker for the first {@link DNode}
     *            with target equals to {@code semanticElement}
     * @param expectedNbSemanticMarker
     *            expected number of semantic marker for {@code semanticElement}
     */
    private void checkMarkers(EPackage semanticElement, IMarker[] foundMarkers, int expectedNbViewMarker, int expectedNbSemanticMarker) {
        DDiagramElement graphicElement = getGraphicNodeElement(semanticElement);
        String semanticName = semanticElement.getName();
        String graphicName = graphicElement.getName();

        // Two cases to find:
        String graphicCase = "Graphic - The " + graphicName + " element is KO -> View rule OK";
        String semanticCase = "The " + semanticName + " element is KO -> Semantic rule OK";

        int graphicMarkers = 0;
        int semanticMarkers = 0;
        for (IMarker marker : foundMarkers) {
            try {
                String message = (String) marker.getAttribute(IMarker.MESSAGE);
                if (message.equals(graphicCase)) {
                    graphicMarkers++;
                } else if (message.equals(semanticCase)) {
                    semanticMarkers++;
                }
            } catch (CoreException e) {
                fail(e.getMessage());
            }
        }

        assertEquals("The graphic element '" + graphicName + "' does not have the expected ViewValidationRule markers", expectedNbViewMarker, graphicMarkers);
        assertEquals("The semantic element '" + semanticName + "' does not have the expected SemanticValidationRule markers", expectedNbSemanticMarker, semanticMarkers);
    }

    /**
     * Check semantic markers for {@code semanticElement}.
     * 
     * @param semanticElement
     *            semantic element to check
     * @param foundMarkers
     *            markers found
     * @param expectedNbSemanticMarker
     *            expected number of semantic marker for {@code semanticElement}
     */
    private void checkSemanticMarkers(EPackage semanticElement, IMarker[] foundMarkers, int expectedNbSemanticMarker) {
        String semanticName = semanticElement.getName();
        String expectedMessage = "The " + semanticName + " element is KO -> Semantic rule OK";

        int semanticMarkers = 0;
        for (IMarker marker : foundMarkers) {
            try {
                String message = (String) marker.getAttribute(IMarker.MESSAGE);
                if (message.equals(expectedMessage)) {
                    semanticMarkers++;
                }
            } catch (CoreException e) {
                fail(e.getMessage());
            }
        }

        assertEquals("The semantic element '" + semanticName + "' does not have the expected SemanticValidationRule markers", expectedNbSemanticMarker, semanticMarkers);
    }

    private int getShellsNumber() {
        RunnableWithResult<Integer> withResult = new RunnableWithResult.Impl<Integer>() {
            @Override
            public void run() {
                setResult(Display.getDefault().getShells().length);
            }
        };
        Display.getDefault().syncExec(withResult);
        return withResult.getResult();
    }

    @Override
    protected void tearDown() throws Exception {
        DialectUIManager.INSTANCE.closeEditor(editorPart, false);
        TestsUtil.synchronizationWithUIThread();

        diagram = null;
        editorPart = null;
        logListener = null;
        rootEPackage = null;
        subTicket1666_1EPackage = null;
        super.tearDown();
    }
}
