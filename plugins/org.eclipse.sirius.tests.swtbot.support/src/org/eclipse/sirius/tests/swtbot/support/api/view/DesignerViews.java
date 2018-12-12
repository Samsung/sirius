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
package org.eclipse.sirius.tests.swtbot.support.api.view;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Assert;

/**
 * Utility class to open and get the Eclipse views.
 * 
 * @author mchauvin
 */
public class DesignerViews {

    private static final String OUTLINE = "Outline";

    private static final String WINDOW = "Window";

    private static final String SHOW_VIEW = "Show View";

    private final SWTWorkbenchBot bot;

    /**
     * Construct a new instance.
     * 
     * @param bot
     *            the workbench bot
     */
    public DesignerViews(final SWTWorkbenchBot bot) {
        this.bot = bot;
    }

    /**
     * Open a view with the Eclipse API.
     * 
     * @param viewId
     *            The id of the view to open.
     * @param viewTitle
     *            The title of the view
     * @return The SWTBotView
     */
    public SWTBotView openViewByAPI(final String viewId, String viewTitle) {
        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
                } catch (PartInitException e) {
                    Assert.fail("Unable to open errorLog view : " + e.getMessage());
                }
            }
        });
        return bot.viewByTitle(viewTitle);
    }

    /**
     * Open a view.
     * 
     * @param categoryName
     *            The name of the category in the list of views
     * @param viewName
     *            The name of the label in the list of views and the name of the
     *            opened view.
     * @return The SWTBotView
     */
    public SWTBotView openView(String categoryName, String viewName) {
        return openView(categoryName, viewName, viewName);
    }

    /**
     * Open a view.
     * 
     * @param categoryName
     *            The name of the category in the list of views
     * @param labelInList
     *            The name of the label in the list of views
     * @param viewTitle
     *            The title of the view
     * @return The SWTBotView
     */
    public SWTBotView openView(String categoryName, String labelInList, String viewTitle) {
        bot.menu(DesignerViews.WINDOW).menu(DesignerViews.SHOW_VIEW).menu("Other...").click();
        bot.waitUntil(Conditions.shellIsActive("Show View"));

        bot.text().setText(labelInList);
        // Wait that the filter is apply on the tree and expand all the needed
        // nodes
        bot.sleep(500);
        // The node is already expanded (so don't use expand method)
        bot.tree().getTreeItem(categoryName).getNode(labelInList).select();
        final SWTBotButton okButton = bot.button("OK");
        bot.waitUntil(new OKButtonEnabledCondition(okButton));
        okButton.click();
        return bot.viewByTitle(viewTitle);
    }

    /**
     * Open the outline view.
     * 
     * @return the opened outline view
     */
    public SiriusOutlineView openOutlineView() {

        bot.menu(DesignerViews.WINDOW).menu(DesignerViews.SHOW_VIEW).menu(DesignerViews.OUTLINE).click();
        return getOutlineView();
    }

    /**
     * Get the already opened outline view.
     * 
     * @return the outline view
     */
    public SiriusOutlineView getOutlineView() {
        return new SiriusOutlineView(bot, bot.viewByTitle(DesignerViews.OUTLINE));
    }

    /**
     * A class to wait until a button is enabled.
     * 
     * @author mchauvin
     */
    private class OKButtonEnabledCondition extends DefaultCondition {

        private final SWTBotButton okButton;

        public OKButtonEnabledCondition(final SWTBotButton okButton) {
            this.okButton = okButton;
        }

        @Override
        public String getFailureMessage() {
            return "ok button is not enabled";
        }

        @Override
        public boolean test() throws Exception {
            return okButton.isEnabled();
        }
    }
}
