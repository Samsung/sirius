/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.internal.layout.provider;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.sirius.diagram.DiagramPlugin;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.diagram.ui.tools.api.layout.provider.LayoutProvider;

/**
 * The descriptor of a {@link LayoutProvider}.
 *
 * @author ymortier
 */
public class LayoutProviderDescriptor extends AbstractProviderDescriptor {

    /** The layout provider. */
    private LayoutProvider provider;

    /**
     * Create a new descriptor with the specified configuration element.
     *
     * @param element
     *            the configuration element.
     */
    public LayoutProviderDescriptor(final IConfigurationElement element) {
        super(element);
    }

    /**
     * Return the layout provider.
     *
     * @return the layout provider.
     */
    public LayoutProvider getProviderInstance() {
        if (provider == null) {
            try {
                provider = (LayoutProvider) element.createExecutableExtension("providerClass"); //$NON-NLS-1$
            } catch (final CoreException e) {
                DiagramPlugin.getDefault().logError(MessageFormat.format(Messages.LayoutProviderDescriptor_initializationErrorMsg, this.getProviderClassName()), e);
            }
        }
        return provider;
    }

}
