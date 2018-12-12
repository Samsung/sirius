/*******************************************************************************
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.editor.editorPlugin;

import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * This interface helps defining common behavior for the Viewpoint model
 * editors.
 */
public interface IAdapterFactoryProvider {
    /**
     * @return the editor adapter factory
     */
    public AdapterFactory getAdapterFactory();
}
