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

import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionStatus;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * Condition to check that the Session has {@link SessionStatus#SYNC} status.
 * 
 * @author edugueperoux
 */
public class SessionSavedCondition extends DefaultCondition {

    private final Session session;

    /**
     * Default Constructor.
     * 
     * @param session
     *            the Session
     */
    public SessionSavedCondition(Session session) {
        this.session = session;
    }

    @Override
    public boolean test() throws Exception {
        return SessionStatus.SYNC == session.getStatus();
    }

    @Override
    public String getFailureMessage() {
        return "Session not saved. Session status is " + session.getStatus();
    }
}
