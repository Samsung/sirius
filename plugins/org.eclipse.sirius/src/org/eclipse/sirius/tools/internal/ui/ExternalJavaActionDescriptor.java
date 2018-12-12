/*******************************************************************************
 * Copyright (c) 2008, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tools.internal.ui;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.sirius.viewpoint.Messages;
import org.eclipse.sirius.viewpoint.SiriusPlugin;

/**
 * A factory proxy for creating a IExternalJavaAction.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class ExternalJavaActionDescriptor {
    /** "class" attribute of our extension point's contributions. */
    public static final String CLASS_ATTRIBUTE = "actionClass"; //$NON-NLS-1$

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private final String actionClass;

    private IConfigurationElement element;

    /**
     * Creates a new sorter node with the given configuration element.
     * 
     * @param element
     *            The configuration element
     */
    public ExternalJavaActionDescriptor(final IConfigurationElement element) {
        this.element = element;
        this.actionClass = element.getAttribute(CLASS_ATTRIBUTE);
    }

    /**
     * Creates a new java action from this node.
     * 
     * @return The new IJavaActionMenuItem
     */
    public IExternalJavaAction createJavaActionMenuItem() {
        try {
            return (IExternalJavaAction) element.createExecutableExtension(CLASS_ATTRIBUTE);
        } catch (final CoreException e) {
            SiriusPlugin.getDefault().error(MessageFormat.format(Messages.ExternalJavaActionDescriptor_actionCreationErrorMsg, element.getAttribute(CLASS_ATTRIBUTE)), e);
        } catch (final ClassCastException e) {
            SiriusPlugin.getDefault().error(MessageFormat.format(Messages.ExternalJavaActionDescriptor_actionCreationErrorMsg, element.getAttribute(CLASS_ATTRIBUTE)), e);
        }
        return null;
    }

    public String getActionClass() {
        return actionClass;
    }

    public String getId() {
        return element.getAttribute(ID_ATTRIBUTE);
    }
}
