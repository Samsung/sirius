/**
 * Copyright (c) 2009, 2014 THALES GLOBAL SERVICES
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - Initial API and implementation
 */
package org.eclipse.sirius.tests.swtbot.support.api.business;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * Object to manage diagram representations.
 * 
 * @author dlecan
 */
public class UITableRepresentation extends AbstractUIRepresentation<SWTBotEditor> {

    /**
     * Constructor.
     * 
     * @param treeItem
     *            Tree item.
     * 
     * @param representationName
     *            Representation name.
     */
    public UITableRepresentation(final SWTBotTreeItem treeItem, final String representationName) {
        super(treeItem, representationName);
    }

    /**
     * Open current representation. Does nothing if current diagram was created
     * in this test session instead of being simply opened.
     * 
     * @return Current representation.
     */
    public UITableRepresentation open() {
        super.doOpen();
        return this;
    }

    /**
     * save current representation.
     * 
     * @return Current representation.
     */
    public UITableRepresentation save() {
        super.doSave();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SWTBotEditor getEditor() {
        final SWTBotEditor editorByTitle = designerBot.editorByTitle(getRepresentationName());
        // As of 2009-12-18, SWTBotEditor#setFocus() doesn't do anything
        editorByTitle.show();
        return editorByTitle;
    }

    /**
     * Get table.
     * 
     * @return Current table.
     */
    public SWTBotTree getTable() {
        return getEditorBot().tree();
    }

    /**
     * Get editor bot.
     * 
     * @return Editor bot.
     */
    public SWTBot getEditorBot() {
        return getEditor().bot();
    }

}
