/*******************************************************************************
 * Copyright (c) 2007, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.internal.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.printing.actions.PrintPreviewAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.sirius.diagram.ui.tools.api.ui.actions.ActionIds;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.DeselectAllAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.SaveAsImageFileAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.SelectHiddenElementsAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.SiriusCopyAppearancePropertiesAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.SiriusEdgeSnapBackAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.SizeBothAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.TabbarRouterAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.distribute.DistributeAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.layout.ArrangeBorderNodesAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.layout.CopyFormatAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.layout.PasteFormatAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.layout.PasteLayoutAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.layout.PasteStyleAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.pinning.PinElementsEclipseAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.pinning.UnpinElementsEclipseAction;
import org.eclipse.sirius.diagram.ui.tools.internal.actions.straighten.StraightenToAction;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.actions.DistributeMenuManager;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.tabbar.actions.StraightenToMenuManager;
import org.eclipse.sirius.diagram.ui.tools.internal.print.SiriusDiagramPrintPreviewAction;
import org.eclipse.sirius.diagram.ui.tools.internal.print.SiriusEnhancedPrintActionHelper;
import org.eclipse.ui.IWorkbenchPage;

/**
 * @was-generated
 */
public class SiriusContributionItemProvider extends AbstractContributionItemProvider {

    /**
     * @was-generated NOT
     */
    @Override
    protected IAction createAction(final String actionId, final IWorkbenchPartDescriptor partDescriptor) {
        IAction result;
        final IWorkbenchPage workbenchPage = partDescriptor.getPartPage();
        // if (actionId.equals(ActionIds.ACTION_DELETE_FROM_DIAGRAM)) {
        // result = new DeleteFromDiagramAction(partDescriptor.getPartPage());
        // } else
        if (actionId.equals(PrintPreviewAction.ID)) {
            result = new SiriusDiagramPrintPreviewAction(new SiriusEnhancedPrintActionHelper());
        } else if (ActionIds.COPY_FORMAT.equals(actionId)) {
            result = new CopyFormatAction(workbenchPage);
        } else if (ActionIds.PASTE_FORMAT.equals(actionId)) {
            result = new PasteFormatAction(workbenchPage);
        } else if (ActionIds.PASTE_STYLE.equals(actionId)) {
            result = new PasteStyleAction(workbenchPage);
        } else if (ActionIds.PASTE_LAYOUT.equals(actionId)) {
            result = new PasteLayoutAction(workbenchPage);
        } else if (ActionIds.PIN_ELEMENTS.equals(actionId)) {
            result = new PinElementsEclipseAction();
        } else if (ActionIds.UNPIN_ELEMENTS.equals(actionId)) {
            result = new UnpinElementsEclipseAction();
        } else if (ActionIds.ARRANGE_BORDER_NODES.equals(actionId)) {
            result = ArrangeBorderNodesAction.createArrangeBorderNodesAction(workbenchPage);
        } else if (ActionIds.ARRANGE_BORDER_NODES_TOOLBAR.equals(actionId)) {
            result = ArrangeBorderNodesAction.createToolBarArrangeBorderNodesAction(workbenchPage);
        } else if (ActionIds.COPY_TO_IMAGE.equals(actionId)) {
            result = new SaveAsImageFileAction();
        } else if (ActionIds.SELECT_HIDDEN_ELEMENTS.equals(actionId)) {
            result = new SelectHiddenElementsAction(workbenchPage);
        } else if (ActionIds.ROUTING_STYLE.equals(actionId)) {
            result = TabbarRouterAction.createTreeRouterAction(workbenchPage);
        } else if (ActionIds.EDGE_SNAP_BACK.equals(actionId)) {
            result = new SiriusEdgeSnapBackAction(workbenchPage);
        } else if (ActionIds.DESELECT_ALL.equals(actionId)) {
            result = new DeselectAllAction();
        } else if (ActionIds.DISTRIBUTE_GAPS_HORIZONTALLY.equals(actionId)) {
            result = DistributeAction.createDistributeHorizontallyWithUniformGapsAction(workbenchPage, false);
        } else if (ActionIds.DISTRIBUTE_CENTERS_HORIZONTALLY.equals(actionId)) {
            result = DistributeAction.createDistributeCentersHorizontallyAction(workbenchPage, false);
        } else if (ActionIds.DISTRIBUTE_GAPS_VERTICALLY.equals(actionId)) {
            result = DistributeAction.createDistributeVerticallyWithUniformGapsAction(workbenchPage, false);
        } else if (ActionIds.DISTRIBUTE_CENTERS_VERTICALLY.equals(actionId)) {
            result = DistributeAction.createDistributeCentersVerticallyAction(workbenchPage, false);
        } else if (org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds.ACTION_MAKE_SAME_SIZE_BOTH.equals(actionId)) {
            result = new SizeBothAction(workbenchPage);
        } else if (actionId.equals(ActionIds.ACTION_SIRIUS_COPY_APPEARANCE_PROPERTIES)) {
            return new SiriusCopyAppearancePropertiesAction(workbenchPage);
        } else if (ActionIds.STRAIGHTEN_TO_TOP.equals(actionId)) {
            result = StraightenToAction.createStraightenToTopAction(workbenchPage);
        } else if (ActionIds.STRAIGHTEN_TO_BOTTOM.equals(actionId)) {
            result = StraightenToAction.createStraightenToBottomAction(workbenchPage);
        } else if (ActionIds.STRAIGHTEN_TO_LEFT.equals(actionId)) {
            result = StraightenToAction.createStraightenToLeftAction(workbenchPage);
        } else if (ActionIds.STRAIGHTEN_TO_RIGHT.equals(actionId)) {
            result = StraightenToAction.createStraightenToRightAction(workbenchPage);
        } else {
            result = super.createAction(actionId, partDescriptor);
        }
        return result;
    }

    @Override
    protected IMenuManager createMenuManager(String menuId, IWorkbenchPartDescriptor partDescriptor) {
        if (menuId.equals(ActionIds.MENU_DISTRIBUTE)) {
            return new DistributeMenuManager();
        } else if (menuId.equals(ActionIds.MENU_STRAIGHTEN_TO)) {
            return new StraightenToMenuManager();
        }
        return super.createMenuManager(menuId, partDescriptor);
    }
}
