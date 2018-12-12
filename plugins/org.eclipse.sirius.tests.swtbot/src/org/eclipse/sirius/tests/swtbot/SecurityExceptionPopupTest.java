/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.eclipse.sirius.tests.swtbot.support.api.condition.ShellChangedCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;

/**
 * This test checks that the securityException opens a popup only in the case of
 * a nonempty message.
 * 
 * @author Florian Barbin
 *
 */
public class SecurityExceptionPopupTest extends AbstractSiriusSwtBotGefTestCase {

    /**
     * Logs a SecurityException with a message and makes sure the pop-up is
     * opened.
     */
    public void testSecurityExceptionWithMessage() {
        launchExceptionAndCheck(true);
    }

    /**
     * Logs a SecurityException without message and checks that no pop-up is
     * opened.
     */
    public void testSecurityExceptionWithoutMessage() {
        launchExceptionAndCheck(false);
    }

    private void launchExceptionAndCheck(final boolean withMessage) {

        // Step 1 - Log a security exception.
        SecurityException exception = null;
        if (withMessage) {
            exception = new SecurityException("test");
        } else {
            exception = new SecurityException();
        }
        ShellChangedCondition shellChangedCondition = new ShellChangedCondition(bot.activeShell());
        SiriusTestsPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SiriusTestsPlugin.PLUGIN_ID, "exception occurs", exception));

        // Step 2 - Check whether the dialog is opened
        // or not (if there is no message in the exception, the dialog
        // should not be opened).

        SWTBotShell shell = null;
        try {
            bot.waitUntil(shellChangedCondition);
            shell = bot.activeShell();
            assertEquals("Permission Issue", shell.getText());
        } catch (TimeoutException e) {
            // We throw the exception only in the case where the
            // dialog is expected (a SecurityException with a
            // message).
            if (withMessage) {
                throw e;
            }
        }
        errors.clear();
        if (shell != null) {
            shell.close();
            // If the exception was logged without message, we
            // shouldn't have found a shell.
            if (!withMessage) {
                fail("There should not be a dialog with empty message");
            }
        }
    }
}
