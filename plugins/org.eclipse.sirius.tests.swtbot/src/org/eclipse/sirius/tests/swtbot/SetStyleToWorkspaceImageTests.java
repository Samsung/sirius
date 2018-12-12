/*******************************************************************************
 * Copyright (c) 2010, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramBorderNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramContainerEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramElementContainerEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramListEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramNameEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IAbstractDiagramNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramContainerEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramListEditPart;
import org.eclipse.sirius.diagram.ui.tools.api.figure.AlphaDropShadowBorder;
import org.eclipse.sirius.diagram.ui.tools.api.figure.GradientRoundedRectangle;
import org.eclipse.sirius.diagram.ui.tools.api.figure.IWorkspaceImageFigure;
import org.eclipse.sirius.diagram.ui.tools.api.figure.ViewNodeContainerRectangleFigureDesc;
import org.eclipse.sirius.diagram.ui.tools.api.figure.WorkspaceImageFigure;
import org.eclipse.sirius.diagram.ui.tools.api.preferences.SiriusDiagramUiPreferencesKeys;
import org.eclipse.sirius.tests.support.api.PluginVersionCompatibility;
import org.eclipse.sirius.tests.support.api.TestsUtil;
import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.eclipse.sirius.tests.swtbot.support.api.business.UILocalSession;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckNbVisibleElementsInTree;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckSelectedCondition;
import org.eclipse.sirius.tests.swtbot.support.api.condition.OperationDoneCondition;
import org.eclipse.sirius.tests.swtbot.support.api.condition.WidgetIsDisabledCondition;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.osgi.framework.Version;

/**
 * Tests the style customization.
 * 
 * @author mporhel
 */
public class SetStyleToWorkspaceImageTests extends AbstractSiriusSwtBotGefTestCase {

    private static final String MODEL = "tc2225.ecore";

    private static final String DESIGN_FILE = "tc2225.odesign";

    private static final String SESSION_FILE = "tc2225.aird";

    private static final String IMG_FILE = "aircraft.jpg";

    private static final String IMG_FILE1 = "aircraft1.JPG";

    private static final String IMG_SVG_FILE = "image.svg";

    private static final String DATA_UNIT_DIR = "data/unit/style/";

    private static final String FILE_DIR = "/";

    private static final String REPRESENTATION_INSTANCE_NAME = "2225 package entities";

    private static final String REPRESENTATION_NAME = "Entities2225";

    private static final String DIALOG_TITLE = "Select background image from workspace";

    private static String C1 = "c1";

    private static String A1 = "a1";

    private static final String C1_NODE = C1 + "Node";

    private static final String C1_CONTAINER = C1 + "Container";

    private static final String C1_LIST = C1 + "List";

    private static final String A1C1_NODE = A1 + C1_NODE;

    private static final String BUNDLE_IMAGE_SUFFIX = "_BI";

    private static final String A1C1_CONTAINER = A1 + C1_CONTAINER;

    private static final String A1C1_LIST = A1 + C1_LIST;

    private UIResource sessionAirdResource;

    private UILocalSession localSession;

    private String oldDefaultFontName;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpBeforeClosingWelcomePage() throws Exception {
        // Set the default fontName to have tests on
        // "Reset style properties to default values" button works.
        oldDefaultFontName = changeDefaultFontName("Times New Roman");

        copyFileToTestProject(Activator.PLUGIN_ID, DATA_UNIT_DIR, MODEL, SESSION_FILE, DESIGN_FILE, IMG_FILE, IMG_FILE1, IMG_SVG_FILE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tearDown() throws Exception {
        // Reset the default fontName
        changeDefaultFontName(oldDefaultFontName);

        super.tearDown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
        changeDiagramUIPreference(SiriusDiagramUiPreferencesKeys.PREF_OLD_UI.name(), false);

        sessionAirdResource = new UIResource(designerProject, FILE_DIR, SESSION_FILE);
        localSession = designerPerspective.openSessionFromFile(sessionAirdResource);

        editor = openDiagram(localSession.getOpenedSession(), REPRESENTATION_NAME, REPRESENTATION_INSTANCE_NAME, DDiagram.class);
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleCancelFromAppearanceSection() throws Exception {
        if (TestsUtil.shouldSkipUnreliableTests()) {
            /*
             * org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException:
             * Could not find widget. at
             * org.eclipse.swtbot.swt.finder.SWTBotFactory
             * .waitUntilWidgetAppears(SWTBotFactory.java:357) at
             * org.eclipse.sirius
             * .tests.swtbot.support.api.editor.SWTBotSiriusHelper
             * .widget(SWTBotSiriusHelper.java:126) at
             * org.eclipse.sirius.tests.swtbot
             * .support.api.editor.SWTBotSiriusHelper
             * .selectPropertyTabItem(SWTBotSiriusHelper.java:99) at
             * org.eclipse.
             * sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase
             * .getSectionButton(AbstractSiriusSwtBotGefTestCase.java:1232) at
             * org.eclipse.sirius.tests.swtbot.support.api.
             * AbstractSiriusSwtBotGefTestCase
             * .getSetStyleToWorkspaceImageButtonFromAppearanceTab
             * (AbstractSiriusSwtBotGefTestCase.java:1178) at
             * org.eclipse.sirius.
             * tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase
             * .getSetStyleToWorkspaceImageButton
             * (AbstractSiriusSwtBotGefTestCase.java:1126) at
             * org.eclipse.sirius. tests.swtbot.SetStyleToWorkspaceImageTests.
             * testSetWkpImageStyleCancel
             * (SetStyleToWorkspaceImageTests.java:404) at
             * org.eclipse.sirius.tests.swtbot.SetStyleToWorkspaceImageTests.
             * testSetWkpImageStyleCancelFromAppearanceSection
             * (SetStyleToWorkspaceImageTests.java:147) Caused by:
             * org.eclipse.swtbot.swt.finder.widgets.TimeoutException: Timeout
             * after: 10000 ms.: Could not find widget matching: (of type
             * 'TabbedPropertyList')
             */
            return;
        }
        // Appearance tab tests
        testSetWkpImageStyleCancel(C1_NODE, AbstractDiagramNodeEditPart.class, false);
        testSetWkpImageStyleCancel(C1_LIST, AbstractDiagramListEditPart.class, false);
        testSetWkpImageStyleCancel(C1_CONTAINER, AbstractDiagramContainerEditPart.class, false);
        testSetWkpImageStyleCancel(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, false);
        testSetWkpImageStyleCancel(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, false);
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleCancelFromTabbar() throws Exception {
        // Tabbar tests
        testSetWkpImageStyleCancel(C1_NODE, AbstractDiagramNodeEditPart.class, true);
        testSetWkpImageStyleCancel(C1_LIST, AbstractDiagramListEditPart.class, true);
        testSetWkpImageStyleCancel(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true);
        testSetWkpImageStyleCancel(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true);
        testSetWkpImageStyleCancel(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true);
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleDisabledOnListElementFromAppearanceSection() throws Exception {
        testSetWkpImageStyleDisabled(A1C1_LIST, AbstractDiagramNameEditPart.class, false);
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleDisabledOnListElementFromTabbar() throws Exception {
        testSetWkpImageStyleDisabled(A1C1_LIST, AbstractDiagramNameEditPart.class, true);
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnNodeFromTabbar() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_NODE, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnListFromTabbar() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_LIST, AbstractDiagramListEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnContainerFromTabbar() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnBorderedNodeFromTabbar() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnContainedNodeFromTabbar() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromJpgToSvgOnNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_NODE, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_NODE, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());

        testChangeWkpImageStyle(C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromJpgToSvgOnListFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_LIST, AbstractDiagramListEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_LIST, AbstractDiagramListEditPart.class, true, getSvgImagePath());

        testChangeWkpImageStyle(C1_LIST + BUNDLE_IMAGE_SUFFIX, AbstractDiagramListEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_LIST + BUNDLE_IMAGE_SUFFIX, AbstractDiagramListEditPart.class, true, getSvgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromJpgToSvgOnContainerFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true, getSvgImagePath());

        testChangeWkpImageStyle(C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramContainerEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramContainerEditPart.class, true, getSvgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromJpgToSvgOnBorderedNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true, getSvgImagePath());

        testChangeWkpImageStyle(A1C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramBorderNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(A1C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramBorderNodeEditPart.class, true, getSvgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromJpgToSvgOnContainedNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());

        testChangeWkpImageStyle(A1C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
        testChangeWkpImageStyle(A1C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromSvgToJpgOnNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_NODE, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_NODE, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());

        testChangeWkpImageStyle(C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromSvgToJpgOnListFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_LIST, AbstractDiagramListEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_LIST, AbstractDiagramListEditPart.class, true, getJpgImagePath());

        testChangeWkpImageStyle(C1_LIST + BUNDLE_IMAGE_SUFFIX, AbstractDiagramListEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_LIST + BUNDLE_IMAGE_SUFFIX, AbstractDiagramListEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromSvgToJpgOnContainerFromTabbar() throws Exception {
        testChangeWkpImageStyle(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_CONTAINER, AbstractDiagramContainerEditPart.class, true, getJpgImagePath());

        testChangeWkpImageStyle(C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramContainerEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramContainerEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromSvgToJpgOnBorderedNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, true, getJpgImagePath());

        testChangeWkpImageStyle(A1C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramBorderNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(A1C1_NODE + BUNDLE_IMAGE_SUFFIX, AbstractDiagramBorderNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testChangeWkpImageStyleFromSvgToJpgOnContainedNodeFromTabbar() throws Exception {
        testChangeWkpImageStyle(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());

        testChangeWkpImageStyle(A1C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getSvgImagePath());
        testChangeWkpImageStyle(A1C1_CONTAINER + BUNDLE_IMAGE_SUFFIX, AbstractDiagramNodeEditPart.class, true, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnNodeFromAppearanceSection() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_NODE, AbstractDiagramNodeEditPart.class, false, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnListFromAppearanceSection() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_LIST, AbstractDiagramListEditPart.class, false, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnContainerFromAppearanceSection() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(C1_CONTAINER, AbstractDiagramContainerEditPart.class, false, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnBorderNodeFromAppearanceSection() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class, false, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnContainedNodeFromAppearanceSection() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, false, getJpgImagePath());
    }

    /**
     * Test method.
     * 
     * @throws Exception
     *             Test error.
     */
    public void testSetWkpImageStyleOnContainedNodeFromAppearanceSectionWithPluginImagePath() throws Exception {
        testSetWkpImageStyleApplicationAndCancel(A1C1_CONTAINER, AbstractDiagramNodeEditPart.class, false,
                "/org.eclipse.sirius.tests.junit/data/unit/migration/do_not_migrate/campaign/TestCampaign_10/image.bmp");
    }

    /**
     * Test the filter function of the workspace selection dialog (VP-3520).
     */
    public void testTheFilterArea() {
        boolean afterEclipse3_5 = true;
        if (new PluginVersionCompatibility("org.eclipse.ui.navigator").compareTo(new Version("3.4.2.M20100120-0800")) <= 0) {
            afterEclipse3_5 = false;
        }

        // Select one edit part (to enable the button).
        selectAndCheckEditPart(A1C1_NODE, AbstractDiagramBorderNodeEditPart.class);
        // Get the button, of the tab Appearance of the properties view, to
        // change background image.
        AbstractSWTBot<? extends Widget> wkpImageButton = getSetStyleToWorkspaceImageButton(false, true);
        // Open the first dialog
        click(wkpImageButton);
        openSelectImageDialog();
        // Open the browse workspace selection dialog
        SWTBotButton button = bot.button(0);
        assertNotNull(button);
        // get button from index and check requested text allows to check
        // position
        assertEquals("Browse", button.getText());
        click(button);
        bot.waitUntil(Conditions.shellIsActive("Background image"));
        SWTBotShell shell = bot.shell("Background image");
        shell.setFocus();
        // Check that the filter area is here.
        try {
            bot.text();
        } catch (WidgetNotFoundException e) {
            fail("A text area should be displayed in the dialog to browse the workspace to select image.");
        }
        bot.waitUntil(new CheckNbVisibleElementsInTree(bot.tree(), 1, "The dialog should display only the project at starting."));
        // Check that nothing is displayed if there is no match
        expandIfNeeded(!afterEclipse3_5);
        bot.text().setText("noMatch");
        bot.waitUntil(new CheckNbVisibleElementsInTree(bot.tree(), 0, "The dialog should display nothing if the filter does not match with anything."));
        // Check that the contents of the representations file is filtered
        expandIfNeeded(!afterEclipse3_5);
        String fileName = "air*";
        bot.text().setText(fileName);
        bot.waitUntil(new CheckNbVisibleElementsInTree(bot.tree(), 3, "The dialog should display all emements needed to access the file \"" + fileName + "\" (project/file)."));
        // Check that nothing is filtered if there is no filter
        expandIfNeeded(true);
        bot.text().setText("");
        bot.waitUntil(new CheckNbVisibleElementsInTree(bot.tree(), 4, "The dialog should display a normal view if there is no filter."));
    }

    /**
     * For Eclipse 3.5 (and before), we need a expand all on treeViewer because
     * in Eclipse 3.5, the filter is only applied on expanded elements.
     * 
     * @param expandNeeded
     *            true if the expandAll is needed
     */
    protected void expandIfNeeded(boolean expandNeeded) {
        if (expandNeeded) {
            // Check that nothing is filtered if there is no filter
            bot.text().setText("");
            bot.waitUntil(new CheckNbVisibleElementsInTree(bot.tree(), 1, "The dialog should display a normal view if there is no filter."));
            // Expand all (because in Eclipse 3.5, the filter only applied on
            // expand elements
            bot.tree().expandNode(getProjectName(), true);
        }
    }

    private void testSetWkpImageStyleCancel(String name, Class<? extends IGraphicalEditPart> type, boolean tabbar) throws Exception {
        openErrorLogViewByAPI();
        SWTBotView errorLogView = bot.viewByTitle("Error Log");
        errorLogView.setFocus();
        int errorCount = errorLogView.bot().tree().rowCount();

        SWTBotGefEditPart botPart = selectAndCheckEditPart(name, type);

        AbstractSWTBot<? extends Widget> wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);
        AbstractSWTBot<? extends Widget> cancelCustomButton = getResetStylePropertiesToDefaultValuesButton(tabbar, false);

        click(wkpImageButton);

        openSelectImageDialog();

        cancel(botPart);

        assertNotNull(cancelCustomButton);
        assertFalse(cancelCustomButton.isEnabled());

        errorLogView.setFocus();
        assertEquals("An error occurs during this test.", errorCount, errorLogView.bot().tree().rowCount());
        closeErrorLogView();
    }

    private void testSetWkpImageStyleDisabled(String name, Class<? extends IGraphicalEditPart> type, boolean tabbar) throws Exception {
        selectAndCheckEditPart(name, type);

        if (!tabbar) {
            // no appearance tab in properties view
            return;
        }

        getSetStyleToWorkspaceImageButton(tabbar, false);
        getResetStylePropertiesToDefaultValuesButton(tabbar, false);
    }

    private void testSetWkpImageStyleApplicationAndCancel(String name, Class<? extends IGraphicalEditPart> type, boolean tabbar, String imagePath) throws Exception {
        SWTBotGefEditPart botPart = selectAndCheckEditPart(name, type);
        IAbstractDiagramNodeEditPart part = (IAbstractDiagramNodeEditPart) botPart.part();

        Dimension oldGMFSize = getSize((Node) part.getNotationView());
        Dimension oldD2DSize = getSize(part.getFigure());

        if (oldGMFSize.height != -1) {
            assertEquals(oldGMFSize.width, oldD2DSize.width);
        }

        if (oldGMFSize.height != -1) {
            assertEquals(oldGMFSize.height, oldD2DSize.height);
        }

        AbstractSWTBot<? extends Widget> wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);
        AbstractSWTBot<? extends Widget> resetStylePropertiesToDefaultValuesButton = getResetStylePropertiesToDefaultValuesButton(tabbar, false);

        click(wkpImageButton);

        openSelectImageDialog();

        setImage(imagePath);

        if (tabbar) {
            editor.click(editor.mainEditPart());

            botPart = selectAndCheckEditPart(name, type);
            wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);
            resetStylePropertiesToDefaultValuesButton = getResetStylePropertiesToDefaultValuesButton(tabbar, true);
        }

        assertNotNull(resetStylePropertiesToDefaultValuesButton);
        assertTrue("Reset style button should be enabled.", resetStylePropertiesToDefaultValuesButton.isEnabled());

        checkCustom(part, true);

        Image image = WorkspaceImageFigure.flyWeightImage(imagePath);
        double ratio = (double) image.getBounds().width / image.getBounds().height;
        int newHeight = (int) (oldD2DSize.width / ratio);

        Dimension newGMFSize = getSize((Node) part.getNotationView());
        Dimension newD2DSize = getSize(part.getFigure());

        assertEquals("The GMF height should be set to -1.", -1, newGMFSize.height);
        assertEquals("The GMF width should be kept.", oldGMFSize.width, newGMFSize.width);

        if (part instanceof IDiagramContainerEditPart || part instanceof IDiagramListEditPart) {
            // Auto-sized container are resized on set wkp image to get the
            // image size.
            assertEquals("The GMF width was and stays -1.", -1, oldGMFSize.width);
            assertEquals("The figure size should correspond to the image width.", image.getBounds().width, newD2DSize.width, 2);
            assertEquals("The figure size should correspond to the image width.", image.getBounds().height, newD2DSize.height, 2);
            assertTrue("The primary shape should be a ViewNodeContainerRectangleFigureDesc.",
                    ((AbstractDiagramElementContainerEditPart) part).getPrimaryShape() instanceof ViewNodeContainerRectangleFigureDesc);
            assertTrue("The background figure should be a IWorkspaceImageFigure.", ((AbstractDiagramElementContainerEditPart) part).getBackgroundFigure() instanceof IWorkspaceImageFigure);
            assertNull("The image figure should not have a drop shadow border.", ((AbstractDiagramElementContainerEditPart) part).getMainFigure().getBorder());
        } else {
            // Nodes keep their size and the height is modified to keep the
            // image ratio
            assertEquals("The node GMF width should not be impacted.", oldD2DSize.width, newD2DSize.width);
            assertEquals("The node figure should have the same ratio than the image.", newHeight, newD2DSize.height, 2);
        }

        click(resetStylePropertiesToDefaultValuesButton);

        if (tabbar) {
            editor.click(editor.mainEditPart());

            botPart = selectAndCheckEditPart(name, type);
            wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);
            resetStylePropertiesToDefaultValuesButton = getResetStylePropertiesToDefaultValuesButton(tabbar, false);
        }
        bot.waitUntil(new WidgetIsDisabledCondition(resetStylePropertiesToDefaultValuesButton));
        checkCustom(part, false);

        Dimension newGMFSize2 = getSize((Node) part.getNotationView());
        Dimension newD2DSize2 = getSize(part.getFigure());
        assertEquals(oldD2DSize.width, newD2DSize2.width);
        assertEquals(oldD2DSize.height, newD2DSize2.height);

        assertEquals(oldGMFSize.width, newGMFSize2.width);
        // assertEquals(oldGMFSize.height, newGMFSize2.height);

        if (part instanceof IDiagramContainerEditPart || part instanceof IDiagramListEditPart) {
            assertTrue("The primary shape should be a GradientRoundedRectangle.", ((AbstractDiagramElementContainerEditPart) part).getPrimaryShape() instanceof GradientRoundedRectangle);
            assertNull("The background figure should be null for a gradient style.", ((AbstractDiagramElementContainerEditPart) part).getBackgroundFigure());
            // Alpha drop shadow has been recreated.
            assertTrue("The drop shadow border should have been recreated.", ((AbstractDiagramElementContainerEditPart) part).getMainFigure().getBorder() instanceof AlphaDropShadowBorder);
        }
    }

    private void testChangeWkpImageStyle(String name, Class<? extends IGraphicalEditPart> type, boolean tabbar, String imagePath) throws Exception {
        SWTBotGefEditPart botPart = selectAndCheckEditPart(name, type);
        IAbstractDiagramNodeEditPart part = (IAbstractDiagramNodeEditPart) botPart.part();

        AbstractSWTBot<? extends Widget> wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);

        click(wkpImageButton);

        openSelectImageDialog();

        setImage(imagePath);

        if (tabbar) {
            editor.click(editor.mainEditPart());

            botPart = selectAndCheckEditPart(name, type);
            wkpImageButton = getSetStyleToWorkspaceImageButton(tabbar, true);
        }

        assertFalse("No message should be log in error log after a change of image:" + getErrorLoggersMessage(), doesAnErrorOccurs());
        checkCustom(part, true);
    }

    private Dimension getSize(Node gmfNode) {
        Size size = (Size) gmfNode.getLayoutConstraint();
        return new Dimension(size.getWidth(), size.getHeight());
    }

    private Dimension getSize(IFigure figure) {
        return new Dimension(figure.getSize());
    }

    private void setImage(String imagePath) {
        SWTBotText text = bot.text();
        text.setFocus();
        text.setText(imagePath);

        ICondition done = new OperationDoneCondition();
        bot.button("OK").click();
        bot.waitUntil(done);
    }

    private String getJpgImagePath() {
        return designerProject.getName() + "/" + IMG_FILE;
    }

    private String getSvgImagePath() {
        return designerProject.getName() + "/" + IMG_SVG_FILE;
    }

    private void cancel(SWTBotGefEditPart botPart) {
        bot.button("Cancel").click();
        checkCustom((IAbstractDiagramNodeEditPart) botPart.part(), false);
    }

    private SWTBotGefEditPart selectAndCheckEditPart(String name, Class<? extends IGraphicalEditPart> type) {
        editor.setFocus();
        editor.reveal(name);

        SWTBotGefEditPart botPart = editor.getEditPart(name, type);
        assertNotNull("The requested edit part should not be null", botPart);

        CheckSelectedCondition cs = new CheckSelectedCondition(editor, name, type);
        editor.click(botPart);
        botPart.select();
        bot.waitUntil(cs);

        return botPart;
    }

    private void openSelectImageDialog() {
        bot.waitUntil(Conditions.shellIsActive(DIALOG_TITLE));

        SWTBotShell shell = bot.shell(DIALOG_TITLE);
        shell.setFocus();
    }

    private void checkCustom(IAbstractDiagramNodeEditPart part, boolean custom) {
        AbstractDNode node = (AbstractDNode) part.resolveDiagramElement();
        boolean isCustom = new DDiagramElementQuery(node).isCustomized();
        assertEquals(custom, isCustom);
    }

    /**
     * Close error log.
     * 
     * @throws Exception
     *             any exception
     */
    protected void closeErrorLogView() throws Exception {
        bot.viewByTitle("Error Log").close();
    }
}
