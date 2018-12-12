/**
 * Copyright (c) 2011, 2014 THALES GLOBAL SERVICES
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - Initial API and implementation
 */
package org.eclipse.sirius.tests.swtbot.support.api.condition;

import java.util.Collection;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusDiagramEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;

import com.google.common.collect.Lists;

/**
 * Class to check if the edit part is selected.
 * 
 * @author smonnier
 */
public class CheckSelectedCondition extends DefaultCondition {

    /**
     * name of the edit part to wait for its selection.
     */
    private String labelOfEditPart;

    /**
     * the edit part to wait for its selection.
     */
    private EditPart editPartToWaitForSelection;

    /**
     * the class of the edit part to wait for its selection.
     */
    private Class<? extends IGraphicalEditPart> editPartClass;

    /**
     * Current editor.
     */
    private SWTBotSiriusDiagramEditor editor;

    /**
     * Constructor.
     * 
     * @param editor
     *            the current editor.
     * 
     * @param labelOfEditPart
     *            name of the edit part to wait for selection.
     */
    public CheckSelectedCondition(SWTBotSiriusDiagramEditor editor, String labelOfEditPart) {
        this.editor = editor;
        this.labelOfEditPart = labelOfEditPart;
    }

    /**
     * Constructor.
     * 
     * @param editor
     *            the current editor.
     * @param editPartToWaitForSelection
     *            the edit part to wait for selection.
     */
    public CheckSelectedCondition(SWTBotSiriusDiagramEditor editor, EditPart editPartToWaitForSelection) {
        this.editor = editor;
        this.editPartToWaitForSelection = editPartToWaitForSelection;
    }

    /**
     * Constructor.
     * 
     * @param editor
     *            the current editor.
     * @param labelOfEditPart
     *            name of the edit part to wait for selection.
     * @param editPartClass
     *            edit part class to wait for selection.
     */
    public CheckSelectedCondition(SWTBotSiriusDiagramEditor editor, String labelOfEditPart, Class<? extends IGraphicalEditPart> editPartClass) {
        this.editor = editor;
        this.labelOfEditPart = labelOfEditPart;
        this.editPartClass = editPartClass;
    }

    /**
     * {@inheritDoc}
     */
    public boolean test() throws Exception {
        return isSelected(getEditPart());
    }

    private boolean isSelected(EditPart part) {
        return part != null && part.getSelected() == EditPart.SELECTED_PRIMARY || part.getSelected() == EditPart.SELECTED;
    }

    private EditPart getEditPart() {
        EditPart part = editPartToWaitForSelection;
        if (part == null) {
            if (editPartClass != null) {
                part = editor.getEditPart(labelOfEditPart, editPartClass).part();
            } else {
                part = editor.getEditPart(labelOfEditPart).part().getParent();
            }
        }
        return part;
    }

    /**
     * {@inheritDoc}
     */
    public String getFailureMessage() {
        return "The edit part has not been selected";
    }

    /**
     * Create a compound condition to check the selection of the given parts.
     * 
     * @param editor
     *            the current editor.
     * @param editPartLabels
     *            names of the edit parts to wait for selection.
     * @return a compound condition to check the selection of the given parts.
     */
    public static CompoundCondition multipleSelection(SWTBotSiriusDiagramEditor editor, String... editPartLabels) {
        return multipleSelection(editor, null, editPartLabels);
    }

    /**
     * Create a compound condition to check the selection of the given parts.
     * 
     * @param editor
     *            the current editor.
     * @param editPartLabels
     *            names of the edit parts to wait for selection.
     * @param editPartClass
     *            edit part class to wait for selection.
     * @return a compound condition to check the selection of the given parts.
     */
    public static CompoundCondition multipleSelection(SWTBotSiriusDiagramEditor editor, Class<? extends IGraphicalEditPart> editPartClass, String... editPartLabels) {
        Collection<ICondition> conditions = Lists.newArrayList();
        for (String label : editPartLabels) {
            conditions.add(new CheckSelectedCondition(editor, label, editPartClass));
        }
        return new CompoundCondition(conditions);
    }

    /**
     * Create a compound condition to check the selection of the given parts.
     * 
     * @param editor
     *            the current editor.
     * @param editPartsToWaitForSelection
     *            the edit part to wait for selection.
     * @return a compound condition to check the selection of the given parts.
     */
    public static CompoundCondition multipleSelection(SWTBotSiriusDiagramEditor editor, EditPart... editPartsToWaitForSelection) {
        Collection<ICondition> conditions = Lists.newArrayList();
        for (EditPart part : editPartsToWaitForSelection) {
            conditions.add(new CheckSelectedCondition(editor, part));
        }
        return new CompoundCondition(conditions);
    }
}
