/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot;

import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.eclipse.sirius.tests.swtbot.support.api.condition.TreeItemExpanded;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotVSMEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * Test that the invalid Meta-model is displayed in the error log.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy MALLET</a>
 */
public class InvalidMetamodelRessourceTest extends AbstractSiriusSwtBotGefTestCase {

    /**
     * Viewpoint Specific Model.
     */
    private static final String VSM = "invalidMetamodelUri.odesign";

    /**
     * VSM path.
     */
    private static final String ODESIGN = "platform:/resource/DesignerTestProject/" + VSM;

    /**
     * Test repository.
     */
    private static final String DATA_UNIT_DIR = "data/unit/viewpoint_uri/bugzilla-431196/";

    /**
     * Sirius Group.
     */
    private static final String GROUP = "Group";

    /**
     * Properties view tab Meta-models.
     */
    private static final String METAMODELS = "Metamodels";

    /**
     * Properties view.
     */
    protected static final String PROPERTIES = "Properties";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpBeforeClosingWelcomePage() throws Exception {
        copyFileToTestProject(Activator.PLUGIN_ID, DATA_UNIT_DIR, VSM);
    }

    /**
     * Test that the invalid meta-model appears in the "Error log" view.
     */
    public void testDefaultLabelExpressionValue() {
        // Opened VSM
        SWTBotVSMEditor odesignEditor = openViewpointSpecificationModel(VSM);
        // expand the tree : Node Mapping
        SWTBotTree tree = odesignEditor.bot().tree();
        tree.expandNode(ODESIGN).expandNode(GROUP).expandNode("testInvalidMetamodelUri").expandNode("Family").select();
        // accesses to property view
        SWTBotView propertiesBot = bot.viewByTitle(PROPERTIES);
        propertiesBot.setFocus();
        // accesses to tab Meta-model
        SWTBotSiriusHelper.selectPropertyTabItem(METAMODELS);
        checkMessageErrorLog();
    }

    /**
     * Check that the error message for invalid meta-model URI appears in the
     * "Error Log" view.
     */
    private void checkMessageErrorLog() {
        try {
            openErrorLogView();
            SWTBotView logViewBot = bot.viewByTitle("Error Log");
            assertTrue("Invalid Metamodel URI does not appear in the error log.", isMessageInErrorLog(logViewBot));
            logViewBot.close();
        } finally {
            // Reset to previous environment
            errors.clear();
        }
    }

    /**
     * Open the error log view.
     */
    private void openErrorLogView() {
        bot.menu("Window").menu("Show View").menu("Other...").click();
        SWTBotTree viewsTreeBot = bot.tree();
        bot.text().setText("Error");
        SWTBotTreeItem expandNode = viewsTreeBot.expandNode("General");
        bot.waitUntil(new TreeItemExpanded(expandNode, expandNode.getText()));
        expandNode.getNode("Error Log").doubleClick();
    }

    /**
     * Check that the error message appears in the "Error Log" view.
     * 
     * @param logViewBot
     *            the view to check
     */
    private Boolean isMessageInErrorLog(SWTBotView logViewBot) {
        logViewBot.show();
        SWTBotTree treeError = logViewBot.bot().tree();
        String errorMessage = "Invalid ressource access for the metamodel file:/C:/Users/Stephan%20Kranz/workspace/basicfamily/model/basicfamily.ecore";
        for (SWTBotTreeItem treeItem : treeError.getAllItems()) {
            if (errorMessage.equals(treeItem.getText())) {
                return true;
            }
        }
        return false;
    }
}
