/**
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - Initial API and implementation
 */
package org.eclipse.sirius.tests.swtbot.support.api.bot.description;

import org.eclipse.sirius.tests.swtbot.support.api.view.SiriusPropertiesView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * This class helps to manipulate a Group element in a VSM editor.
 * 
 * @author amartin
 */
public class GroupBot extends AbstractOdesignTreeItemBot {

    /**
     * The constructor.
     * 
     * @param bot
     *            the bot.
     * @param treeItem
     *            the treItem to manipulate.
     * @param propertiesView
     *            the properties view to edit the properties of the viewpoint
     *            element.
     * 
     */
    public GroupBot(SWTBot bot, SWTBotTreeItem treeItem, SiriusPropertiesView propertiesView) {
        super(bot, treeItem, propertiesView);
    }

}
