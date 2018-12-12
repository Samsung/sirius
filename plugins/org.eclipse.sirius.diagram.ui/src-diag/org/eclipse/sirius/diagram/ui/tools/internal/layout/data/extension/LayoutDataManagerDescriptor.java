/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.internal.layout.data.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.sirius.common.ui.SiriusTransPlugin;
import org.eclipse.sirius.diagram.ui.tools.api.layout.ILayoutDataManagerProvider;

/**
 * Describes a extension as contributed to the
 * {@link LayoutDataManagerRegistryListener#LAYOUT_DATA_MANAGER_PROVIDER_EXTENSION_POINT}
 * extension point.
 * 
 * @author mporhel
 * 
 */
public class LayoutDataManagerDescriptor {

    /**
     * Name of the attribute corresponding to the contributed class's path.
     */
    public static final String LAYOUT_DATA_MANAGER_PROVIDER_CLASS_NAME = "class"; //$NON-NLS-1$

    /**
     * Name of the attribute corresponding to the contributed id.
     */
    public static final String LAYOUT_DATA_MANAGER_PROVIDER_ID = "icon"; //$NON-NLS-1$

    /**
     * Configuration element of this descriptor .
     */
    private final IConfigurationElement element;

    /**
     * The path of the contributed class.
     */
    private String extensionClassName;

    /**
     * The {@link ILayoutDataManagerProvider} described by this descriptor.
     */
    private ILayoutDataManagerProvider extension;

    private String id;

    /**
     * Instantiates a descriptor with all information.
     * 
     * @param configuration
     *            Configuration element from which to create this descriptor.
     */
    public LayoutDataManagerDescriptor(IConfigurationElement configuration) {
        element = configuration;
        extensionClassName = configuration.getAttribute(LAYOUT_DATA_MANAGER_PROVIDER_CLASS_NAME);
    }

    /**
     * Creates an instance of this descriptor's
     * {@link ILayoutDataManagerProvider} .
     * 
     * @return A new instance of this descriptor's
     *         {@link ILayoutDataManagerProvider}.
     */
    public ILayoutDataManagerProvider getLayoutDataManagerProvider() {
        if (extension == null) {
            try {
                extension = (ILayoutDataManagerProvider) element.createExecutableExtension(LAYOUT_DATA_MANAGER_PROVIDER_CLASS_NAME);
            } catch (CoreException e) {
                SiriusTransPlugin.INSTANCE.error(e.getMessage(), e);
            }
        }
        return extension;
    }

    /**
     * Return the id of the current tab extension.
     * 
     * @return the id of the current tab extension.
     */
    public String getId() {
        if (id == null) {
            id = element.getAttribute(LAYOUT_DATA_MANAGER_PROVIDER_ID);
        }
        return id;
    }

    /**
     * Returns the fully qualified name of the contributed class.
     * 
     * @return the fully qualified name of the contributed class
     */
    public String getExtensionClassName() {
        return extensionClassName;
    }
}
