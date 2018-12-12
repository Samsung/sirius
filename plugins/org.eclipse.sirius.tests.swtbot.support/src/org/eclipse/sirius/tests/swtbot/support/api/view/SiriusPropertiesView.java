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
package org.eclipse.sirius.tests.swtbot.support.api.view;

import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;

/**
 * Handle the Properties view of the odesign editor.
 * 
 * @author amartin
 * 
 */
public class SiriusPropertiesView {

    private final SWTBotView propertiesView;

    private final SWTBot bot;

    /**
     * 
     * The constructor.
     * 
     * @param bot
     *            the bot.
     * @param view
     *            the view to handle.
     */
    public SiriusPropertiesView(SWTBot bot, SWTBotView view) {
        this.bot = bot;
        propertiesView = view;
    }

    /**
     * Set the property Name in the properties view.
     * 
     * @param name
     *            the name to input.
     * 
     */
    public void setName(String name) {
        propertiesView.setFocus();
        SWTBotSiriusHelper.selectPropertyTabItem("General");
        bot.textWithLabel("Name").setText(name);
    }

    /**
     * Retrieve the name in the properties view.
     * 
     * @return the value in the field Name.
     */
    public String getName() {
        propertiesView.setFocus();
        SWTBotSiriusHelper.selectPropertyTabItem("General");
        /*
         * maybe will have pb, in other case there has a .setfocus before the
         * getText
         */
        return bot.textWithLabel("Name").getText();
    }
}
