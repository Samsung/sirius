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
package org.eclipse.sirius.tests.swtbot.support.api.condition;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;

/**
 * Abstract SWTBot condition to check that the last operation on the command
 * stack has been changed.
 * 
 * @author edugueperoux
 */
public abstract class AbstractOperationCondition extends DefaultCondition implements IOperationHistoryListener {

    private boolean operationState;

    private final IOperationHistory operationHistory;

    private final int eventType;

    /**
     * Constructor to initialize hook on the CommandStack.
     * 
     * @param eventType
     *            the type of event from the command stack to listens
     */
    public AbstractOperationCondition(int eventType) {
        super();
        this.eventType = eventType;
        IWorkbenchOperationSupport operationSupport = PlatformUI.getWorkbench().getOperationSupport();
        operationHistory = operationSupport.getOperationHistory();
        operationHistory.addOperationHistoryListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test() throws Exception {
        return operationState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void historyNotification(OperationHistoryEvent event) {
        if (event.getEventType() == eventType) {
            this.operationState = true;
            operationHistory.removeOperationHistoryListener(this);
        }
    }

}
