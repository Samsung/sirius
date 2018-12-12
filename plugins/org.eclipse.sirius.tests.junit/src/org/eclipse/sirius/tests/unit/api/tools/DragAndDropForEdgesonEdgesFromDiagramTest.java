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
package org.eclipse.sirius.tests.unit.api.tools;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.sirius.business.api.preferences.SiriusPreferencesKeys;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DragAndDropTarget;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramEdgeEditPart;
import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.support.api.EclipseTestsSupportHelper;
import org.eclipse.sirius.tests.support.api.TestsUtil;
import org.eclipse.sirius.tests.unit.api.mappings.edgeonedge.AbstractEdgeOnEdgeTest;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;

import com.google.common.base.Predicate;

/**
 * Ensures that DnD tools works correctly when creating Edges on Edges.
 * <p>
 * Tested parameters :
 * <ul>
 * <li>Manual/Automatic refresh</li>
 * <li>Undo/Redo</li>
 * <li>Editor reopenning</li>
 * <li>Dnd inside diagram/ outside diagram (from Model Content View)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Relevant tickets :
 * <ul>
 * <li>VP-1771 : Edges toward Edges</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 */
public class DragAndDropForEdgesonEdgesFromDiagramTest extends AbstractEdgeOnEdgeTest {

    private static final String FOLDER = FOLDER_PATH + "/drag_and_drop/from_diagram/";

    private static final String SEMANTIC_MODEL_PATH = "2182.ecore";

    private static final String SESSION_FILE_PATH = "2182-dnd.aird";

    private static final String MODELER_PATH = "2182-dnd.odesign";

    private Predicate<EPackage> dndFromModelContentViewFromNodeToEdgeSemanticPredicate_fromdiagram = new Predicate<EPackage>() {
        public boolean apply(EPackage semanticRoot) {
            boolean dndCorrectlyOccured = false;
            EClass targetContainer = ((EClass) semanticRoot.getEClassifier("C1"));
            dndCorrectlyOccured = targetContainer != null;
            if (dndCorrectlyOccured) {
                EAnnotation targetAnnotation = getAnnotationFromSource(targetContainer, "A5");
                dndCorrectlyOccured = targetContainer.getEAnnotations().contains(targetAnnotation);
            }
            return dndCorrectlyOccured;
        }
    };

    private Predicate<EPackage> dndFromModelContentViewFromEdgeToNodeSemanticPredicate_fromdiagram = new Predicate<EPackage>() {
        public boolean apply(EPackage semanticRoot) {
            boolean dndCorrectlyOccured = false;
            EClass targetContainer = ((EClass) semanticRoot.getEClassifier("C1"));
            dndCorrectlyOccured = targetContainer != null;
            if (dndCorrectlyOccured) {
                EAnnotation targetAnnotation = targetContainer.getEAnnotation("AnnotRef1");
                dndCorrectlyOccured = targetAnnotation != null;
            }
            return dndCorrectlyOccured;
        }
    };

    protected String getFolder() {
        return FOLDER;
    }

    @Override
    protected String getSemanticModelPath() {
        return SEMANTIC_MODEL_PATH;
    }

    @Override
    protected String getSessionFilePath() {
        return SESSION_FILE_PATH;
    }

    @Override
    protected String getModelerPath() {
        return MODELER_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        EclipseTestsSupportHelper.INSTANCE.copyFile(SiriusTestsPlugin.PLUGIN_ID, getFolder() + "/" + getSemanticModelPath(), TEMPORARY_PROJECT_NAME + "/" + getSemanticModelPath());
        EclipseTestsSupportHelper.INSTANCE.copyFile(SiriusTestsPlugin.PLUGIN_ID, getFolder() + "/" + getModelerPath(), TEMPORARY_PROJECT_NAME + "/" + getModelerPath());
        EclipseTestsSupportHelper.INSTANCE.copyFile(SiriusTestsPlugin.PLUGIN_ID, getFolder() + "/" + getSessionFilePath(), TEMPORARY_PROJECT_NAME + "/" + getSessionFilePath());
        genericSetUp(TEMPORARY_PROJECT_NAME + "/" + getSemanticModelPath(), TEMPORARY_PROJECT_NAME + "/" + getModelerPath(), TEMPORARY_PROJECT_NAME + "/" + getSessionFilePath());
        diagram = (DDiagram) getRepresentations(REPRESENTATION_DECRIPTION_NAME).toArray()[0];
        assertNotNull(diagram);
        editor = DialectUIManager.INSTANCE.openEditor(session, diagram, new NullProgressMonitor());
        TestsUtil.synchronizationWithUIThread();
        TestsUtil.emptyEventsFromUIThread();
        semanticRoot = (EPackage) semanticModel;
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in manual refresh mode.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInManualRefresh() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in manual refresh mode and with
     * unsynchronized sourceMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInManualRefreshUnsynchronizedSourceMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);
        // EMa TC1 (source edge mapping) : unsynchronized
        // EAnnot TC1 (target node mapping) : synchronized
        // RefToEAnnot TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EAnnot TC1", "RefToEAnnot TC1");

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in manual refresh mode and with
     * unsynchronized targetMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInManualRefreshUnsynchronizedTargetMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);
        // EMa TC1 (source edge mapping) : synchronized
        // EAnnot TC1 (target node mapping) : unsynchronized
        // RefToEAnnot TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EMa TC1", "RefToEAnnot TC1");

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in automatic refresh mode.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInAutoRefresh() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), true);

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in automatic refresh mode with unsynchronized
     * sourceMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInAutoRefreshUnsynchronizedSourceMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), true);
        // EMa TC1 (source edge mapping) : unsynchronized
        // EAnnot TC1 (target node mapping) : synchronized
        // RefToEAnnot TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EAnnot TC1", "RefToEAnnot TC1");

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from an edge to a
     * node mode works as expected in automatic refresh mode with unsynchronized
     * targetMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromEdgeToNodeInAutoRefreshUnsynchronizedTargetMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), true);
        // EMa TC1 (source edge mapping) : synchronized
        // EAnnot TC1 (target node mapping) : unsynchronized
        // RefToEAnnot TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EMa TC1", "RefToEAnnot TC1");

        genericTestEdgeDnDFromEdgeToNode();
    }

    /**
     * Ensures that a DnD operation that should create an edge from a node to an
     * edge mode works as expected in manual refresh mode.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromNodeToEdgeInManualRefresh() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);

        genericTestEdgeDnDFromNodeToEdge();
    }

    /**
     * Ensures that a DnD operation that should create an edge from a node to an
     * edge mode works as expected in manual refresh mode and with
     * unsynchronized sourceMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromNodeToEdgeInManualRefreshUnsynchronizedSourceMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);
        // EAnnot TC1 (source node mapping) : unsynchronized
        // EMa TC1 (target edge mapping) : synchronized
        // EAnnotToRef TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EMa TC1", "EAnnotToRef TC1");

        genericTestEdgeDnDFromNodeToEdge();
    }

    /**
     * Ensures that a DnD operation that should create an edge from a node to an
     * edge mode works as expected in manual refresh mode and with
     * unsynchronized targetMapping.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromNodeToEdgeInManualRefreshUnsynchronizedTargetMapping() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), false);
        // EAnnot TC1 (source node mapping) : synchronized
        // EMa TC1 (target edge mapping) : unsynchronized
        // EAnnotToRef TC1 (edge on edge mapping ) : synchronized
        unsynchronizeAllMappingsExcept("EAnnot TC1", "EAnnotToRef TC1");

        genericTestEdgeDnDFromNodeToEdge();
    }

    /**
     * Ensures that a DnD operation that should create an edge from a node to a
     * edge mode works as expected in automatic refresh mode.
     * 
     * @throws Exception
     */
    public void testEdgeDnDFromNodeToEdgeInAutoRefresh() throws Exception {
        changeSiriusPreference(SiriusPreferencesKeys.PREF_AUTO_REFRESH.name(), true);

        genericTestEdgeDnDFromNodeToEdge();
    }

    /**
     * Ensures that, when droping an EClass that contains informations that
     * should lead to the creation of Edges on Edges (from Node to Edge), these
     * Edges on Edges arce correctly created.
     * 
     * @throws Exception
     */
    private void genericTestEdgeDnDFromNodeToEdge() throws Exception {
        // Step 1 : getting :
        // the annotation to drop
        EAnnotation annotationToDrop = semanticRoot.getEAnnotation("A5");
        // the semantic target container
        EClass targetContainer = (EClass) semanticRoot.getEClassifier("C1");
        // the expected edge on edge source
        EAnnotation semanticEdgeSource = annotationToDrop;
        // the expected edge on edge target
        EReference semanticEdgeTarget = ((EClass) semanticRoot.getEClassifier("C0")).getEReferences().iterator().next();

        // Step 2 : checking that DnD correctly occurs
        genericTestEdgeOnEdgeDnD(annotationToDrop, targetContainer, "dndAnnotationInClass", dndFromModelContentViewFromNodeToEdgeSemanticPredicate_fromdiagram, semanticEdgeSource, semanticEdgeTarget,
                false, true);

    }

    /**
     * Ensures that, when droping an EClass that contains informations that
     * should lead to the creation of Edges on Edges (from Edge to Node), these
     * Edges on Edges arce correctly created.
     * 
     * @throws Exception
     */
    private void genericTestEdgeDnDFromEdgeToNode() throws Exception {

        // Step 1 : getting :
        // the annotation to drop
        EAnnotation annotationToDrop = ((EClass) semanticRoot.getEClassifier("C0")).getEReferences().iterator().next().getEAnnotation("AnnotRef1");
        // the semantic target container
        EClass targetContainer = (EClass) semanticRoot.getEClassifier("C1");
        // the expected edge on edge source
        EReference semanticEdgeSource = ((EClass) semanticRoot.getEClassifier("C0")).getEReferences().iterator().next();
        // the expected edge on edge target
        EAnnotation semanticEdgeTarget = annotationToDrop;

        // Step 2 : checking that DnD correctly occurs
        genericTestEdgeOnEdgeDnD(annotationToDrop, targetContainer, "dndAnnotationInClass", dndFromModelContentViewFromEdgeToNodeSemanticPredicate_fromdiagram, semanticEdgeSource, semanticEdgeTarget,
                true, false);

    }

    /**
     * Ensures that a DnD modifies correctly the semantic and graphical model,
     * with undo/redo, editor reopenning...
     * 
     * @param droppedElement
     *            the semantic element to drop
     * @param targetSemanticContainer
     * 
     * @param containerClass
     *            the number of graphical elements that should be created after
     *            this Dnd
     * 
     * @param dndToolName
     *            the name of the edge creation tool to use
     * @param semanticPredicate
     *            the predicate that checks that semantic model has correctly
     *            been modified
     * @param semanticEdgeSource
     * @param semanticEdgeTarget
     * @param sourceShouldBeAnEge
     * @param targetShouldBeAnEdge
     * @throws Exception
     */
    public void genericTestEdgeOnEdgeDnD(EObject droppedElement, EObject targetSemanticContainer, String dndToolName, Predicate<EPackage> semanticPredicate, EObject semanticEdgeSource,
            EObject semanticEdgeTarget, boolean sourceShouldBeAnEge, boolean targetShouldBeAnEdge) throws Exception {

        // Step 1 : create an edge on edge
        // edge should not exist before tool applying
        assertFalse("Invalid initial state", semanticPredicate.apply(semanticRoot));

        DragAndDropTarget targetContainer = (DragAndDropTarget) getFirstDiagramElement(diagram, targetSemanticContainer);
        assertNotNull("Cannot find Drag and Drop target for semantic element : " + targetSemanticContainer, targetContainer);
        assertTrue("Dnd tool was not correctly applied", applyContainerDropDescriptionTool(diagram, dndToolName, targetContainer, droppedElement));

        // Step 2 : check that edge has been created
        checkEdgeAsBeenDroppedGraphicallyAndSemantically(semanticPredicate, semanticEdgeSource, semanticEdgeTarget, semanticPredicate, sourceShouldBeAnEge, targetShouldBeAnEdge);

        // Step 3 : testing undo/redo
        // Step 3.1 : Undo the creation of the edge (and the associated
        // arrange created views)
        undo();
        undo();
        // -> semantic model should have been modified
        assertFalse("Undo failed", semanticPredicate.apply(semanticRoot));

        // Step 3.2 : Redo the creation of the edge (and the associated
        // arrange created views)
        redo();
        redo();
        // -> semantic model should have been modified and edge should be
        // visible
        checkEdgeAsBeenDroppedGraphicallyAndSemantically(semanticPredicate, semanticEdgeSource, semanticEdgeTarget, semanticPredicate, sourceShouldBeAnEge, targetShouldBeAnEdge);

        // Step 4 : refreshing diagram
        refresh(diagram);
        checkEdgeAsBeenDroppedGraphicallyAndSemantically(semanticPredicate, semanticEdgeSource, semanticEdgeTarget, semanticPredicate, sourceShouldBeAnEge, targetShouldBeAnEdge);

        // Step 5 : Reopen editor
        closeAndReopenEditor();
        checkEdgeAsBeenDroppedGraphicallyAndSemantically(semanticPredicate, semanticEdgeSource, semanticEdgeTarget, semanticPredicate, sourceShouldBeAnEge, targetShouldBeAnEdge);
    }

    /**
     * Ensures that the DnD operation occurred as expected.
     * 
     * @param semanticSource
     *            the semantic source
     * @param semanticTarget
     *            the semantic target
     * @param predicate
     *            the predicate validating that semantic model has correctly
     *            been modified
     * @param sourceShouldBeAnEge
     *            indicates if the edge's source should be an edge
     * @param targetShouldBeAnEdge
     *            indicates if the edge's target should be an edge
     */
    private void checkEdgeAsBeenDroppedGraphicallyAndSemantically(Predicate<EPackage> semanticPredicate, EObject semanticSource, EObject semanticTarget, Predicate<EPackage> predicate,
            boolean sourceShouldBeAnEge, boolean targetShouldBeAnEdge) {

        assertTrue("Semantic model was not correctly updated", semanticPredicate.apply(semanticRoot));
        DEdge edgeElement = null;
        for (final DEdge edge : diagram.getEdges()) {
            EObject edgeSource = ((DDiagramElement) edge.getSourceNode()).getTarget();
            EObject edgeTarget = ((DDiagramElement) edge.getTargetNode()).getTarget();
            if (edgeSource.equals(semanticSource) && edgeTarget.equals(semanticTarget)) {
                edgeElement = edge;
            }
        }
        assertNotNull("Edge should have been created on diagram " + diagram.getName(), edgeElement);

        // Step 2.2 : an editpart should have been created
        IDiagramEdgeEditPart gmfEP = (IDiagramEdgeEditPart) getEditPart(edgeElement, editor);
        assertNotNull("Edit Part corresponding to edge should have been created ", gmfEP);

        // Step 2.3 : checking the created edge's source and target
        // Step 2.3.1 : checking the created edge's source
        DDiagramElement sourceElement = getFirstNodeElement(diagram, semanticSource);
        if (!(sourceElement instanceof Edge) && sourceShouldBeAnEge) {
            sourceElement = getFirstEdgeElement(diagram, semanticSource);
        }
        assertEquals("Wrong Edge source", sourceElement, ((Edge) gmfEP.getModel()).getSource().getElement());

        // Step 2.3.2 : checking the created edge's target
        DDiagramElement targetElement = getFirstNodeElement(diagram, semanticTarget);
        if (!(targetElement instanceof Edge) && targetShouldBeAnEdge) {
            targetElement = getFirstEdgeElement(diagram, semanticTarget);
        }
        assertEquals("Wrong Edge target", targetElement, ((Edge) gmfEP.getModel()).getTarget().getElement());

    }

}
