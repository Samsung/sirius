/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tools.internal.ui;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.sirius.tools.api.ui.ExternalJavaActionProvider;

/**
 * Initially copied from
 * org.eclipse.acceleo.engine.internal.utils.EngineRegistryListener. This
 * listener will allow us to be aware of contribution changes against the
 * external java actions extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ExternalJavaActionRegistryListener implements IRegistryEventListener {
    /** Name of the extension point to parse for template locations. */
    public static final String EXTERNAL_JAVA_ACTION_EXTENSION_POINT = "org.eclipse.sirius.externalJavaAction"; //$NON-NLS-1$

    /**
     * Register this listener and parse initial contributions.
     */
    public void init() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        registry.addListener(this, EXTERNAL_JAVA_ACTION_EXTENSION_POINT);
        parseInitialContributions();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
     */
    public void added(IExtension[] extensions) {
        for (IExtension extension : extensions) {
            parseExtension(extension);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
     */
    public void added(IExtensionPoint[] extensionPoints) {
        // no need to listen to this event
    }

    /**
     * Unregisters this listener from the global eclipse registry, then clears
     * the java actions registry itself.
     */
    public void dispose() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        registry.removeListener(this);
        ExternalJavaActionProvider.INSTANCE.clearRegistry();
    }

    /**
     * Though this listener reacts to the extension point changes, there could
     * have been contributions before it's been registered. This will parse
     * these extensions.
     */
    private void parseInitialContributions() {
        final IExtensionRegistry registry = Platform.getExtensionRegistry();

        for (IExtension extension : registry.getExtensionPoint(EXTERNAL_JAVA_ACTION_EXTENSION_POINT).getExtensions()) {
            parseExtension(extension);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
     */
    public void removed(IExtension[] extensions) {
        for (IExtension extension : extensions) {
            final IConfigurationElement[] configElements = extension.getConfigurationElements();
            for (IConfigurationElement elem : configElements) {
                final String className = elem.getAttribute(ExternalJavaActionDescriptor.CLASS_ATTRIBUTE);
                ExternalJavaActionProvider.INSTANCE.removeAction(className);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
     */
    public void removed(IExtensionPoint[] extensionPoints) {
        // no need to listen to this event
    }

    /**
     * Parses a single extension contribution.
     * 
     * @param extension
     *            Parses the given extension and adds its contribution to the
     *            registry.
     */
    private void parseExtension(IExtension extension) {
        final IConfigurationElement[] configElements = extension.getConfigurationElements();
        for (IConfigurationElement elem : configElements) {
            ExternalJavaActionProvider.INSTANCE.addAction(new ExternalJavaActionDescriptor(elem));
        }
    }
}
