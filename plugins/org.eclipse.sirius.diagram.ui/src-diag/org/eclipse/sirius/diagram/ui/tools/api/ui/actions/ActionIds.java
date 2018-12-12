/*******************************************************************************
 * Copyright (c) 2009, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.api.ui.actions;

import org.eclipse.sirius.diagram.ui.tools.api.requests.RequestConstants;

/**
 * A list of constants defining the diagram action and menu action ids.
 * <p>
 * This interface defines constants only, it is <EM>not</EM> intended to be
 * implemented by clients.
 * </p>
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 * 
 */
// CHECKSTYLE:OFF
public interface ActionIds {
    // CHECKSTYLE:ON

    /** Action contribution id for the copy format (layout and style). */
    String COPY_FORMAT = "copyFormatAction"; //$NON-NLS-1$

    /** Id of menu that groups paste actions. **/
    String MENU_PASTE_FORMAT = "pasteFormatMenu"; //$NON-NLS-1$

    /** Action contribution id for the paste format (layout and style). */
    String PASTE_FORMAT = "pasteFormatAction"; //$NON-NLS-1$

    /** Action contribution id for the paste style. */
    String PASTE_LAYOUT = "pasteLayoutAction"; //$NON-NLS-1$

    /** Action contribution id for the copy style. */
    String PASTE_STYLE = "pasteStyleAction"; //$NON-NLS-1$

    /** Action id for pin elements action. */
    String PIN_ELEMENTS = "pinElementsAction"; //$NON-NLS-1$

    /** Action id for unpin elements action. */
    String UNPIN_ELEMENTS = "unpinElementsAction"; //$NON-NLS-1$

    /** Action id for arrange border nodes action. */
    String ARRANGE_BORDER_NODES = "arrangeBorderNodesAction"; //$NON-NLS-1$

    /** Action id for arrange border nodes action. */
    String ARRANGE_BORDER_NODES_TOOLBAR = "arrangeBorderNodesActionToolBar"; //$NON-NLS-1$

    /**
     * Action id for arrange border nodes action.
     * 
     * @deprecated use {@link ActionIds#ARRANGE_BORDER_NODES} instead
     */
    @Deprecated
    String ARRANGE_BORDERED_NODES = "arrangeBorderNodesAction"; //$NON-NLS-1$

    /**
     * Action id for arrange bordered nodes action.
     * 
     * @deprecated use {@link ActionIds#ARRANGE_BORDER_NODES_TOOLBAR} instead
     */
    @Deprecated
    String ARRANGE_BORDERED_NODES_TOOLBAR = "arrangeBorderNodesActionToolBar"; //$NON-NLS-1$

    /** Action for copy (export) to an image action. */
    String COPY_TO_IMAGE = "newCopyToImageAction"; //$NON-NLS-1$

    /** Arrange menu on diagram. */
    String MENU_ARRANGE = "arrangeMenu"; //$NON-NLS-1$

    /** Action for show/hide elements. */
    String SELECT_HIDDEN_ELEMENTS = "selectHiddenElementsAction"; //$NON-NLS-1$

    /** Action for show/hide elements. */
    String ROUTING_STYLE = "treeRoutingStyleAction"; //$NON-NLS-1$

    /** Action for Activating/Deactivating the Layouting mode. **/
    String SWITCH_LAYOUTING_MODE = "switchLayoutingMode"; //$NON-NLS-1$

    /** Action to deselect all elements (select diagram). **/
    String DESELECT_ALL = "deselectAll"; //$NON-NLS-1$

    /** Id of menu that groups distribute actions. **/
    String MENU_DISTRIBUTE = "distributeMenu"; //$NON-NLS-1$

    /** Action's id to distribute centers horizontally. */
    String DISTRIBUTE_CENTERS_HORIZONTALLY = RequestConstants.REQ_DISTRIBUTE + "CentersHorizontally"; //$NON-NLS-1$

    /** Action's id to distribute horizontally with uniform gaps. */
    String DISTRIBUTE_GAPS_HORIZONTALLY = RequestConstants.REQ_DISTRIBUTE + "GapsHorizontally"; //$NON-NLS-1$

    /** Action's id to distribute centers vertically. */
    String DISTRIBUTE_CENTERS_VERTICALLY = RequestConstants.REQ_DISTRIBUTE + "CentersVertically"; //$NON-NLS-1$

    /** Action's id to distribute vertically with uniform gaps. */
    String DISTRIBUTE_GAPS_VERTICALLY = RequestConstants.REQ_DISTRIBUTE + "GapsVertically"; //$NON-NLS-1$

    /**
     * Action's id to the custom Sirius Copy Appearance Properties Action.
     */
    String ACTION_SIRIUS_COPY_APPEARANCE_PROPERTIES = "siriusCopyAppearancePropertiesAction"; //$NON-NLS-1$

    /**
     * Action's id of the action snap backing all labels of an edge.
     */
    String EDGE_SNAP_BACK = "edgeSnapBackAction"; //$NON-NLS-1$

    /** Id of menu that groups straighten actions. **/
    String MENU_STRAIGHTEN_TO = "straightenToMenu"; //$NON-NLS-1$

    /** Action's id to straighten to top. */
    String STRAIGHTEN_TO_TOP = RequestConstants.REQ_STRAIGHTEN + "Top"; //$NON-NLS-1$

    /** Action's id to straighten to bottom. */
    String STRAIGHTEN_TO_BOTTOM = RequestConstants.REQ_STRAIGHTEN + "Bottom"; //$NON-NLS-1$

    /** Action's id to straighten to left. */
    String STRAIGHTEN_TO_LEFT = RequestConstants.REQ_STRAIGHTEN + "Left"; //$NON-NLS-1$

    /** Action's id to straighten to right. */
    String STRAIGHTEN_TO_RIGHT = RequestConstants.REQ_STRAIGHTEN + "Right"; //$NON-NLS-1$

}
