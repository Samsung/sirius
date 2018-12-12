/*******************************************************************************
 * Copyright (c) 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.api.editor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.diagram.tools.api.command.IDiagramCommandFactoryProvider;
import org.eclipse.sirius.diagram.ui.tools.api.graphical.edit.palette.PaletteManager;
import org.eclipse.sirius.ecore.extender.business.api.permission.IPermissionAuthority;
import org.eclipse.sirius.ui.business.api.dialect.DialectEditor;

/**
 * The diagram editor.
 * 
 * @author mchauvin
 * @since 0.9.0
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface DDiagramEditor extends IAdaptable, DialectEditor {

    /**
     * The editor ID.
     */
    String EDITOR_ID = "org.eclipse.sirius.diagram.ui.part.SiriusDiagramEditorID"; //$NON-NLS-1$

    /**
     * Get the permission authority.
     * 
     * @return the current EditingPermission authority.
     */
    IPermissionAuthority getPermissionAuthority();

    /**
     * return the editor's session.
     * 
     * @return the editor's session.
     * @since 0.9.0
     */
    Session getSession();

    /**
     * Get the palette manager associated to this editor.
     * 
     * @return the palette, <code>null</code> if there is none
     * @since 0.9.0
     */
    PaletteManager getPaletteManager();

    /**
     * Get the manager which handles contribution items to the tab bar.
     * 
     * @return the manager it there is one, <code>false</code> otherwise
     * @since 0.9.0
     */
    IToolBarManager getTabBarManager();

    /**
     * Returns the command factory provider currently in use by this editor.
     * 
     * @return the command factory provider currently in use.
     * @since 0.9.0
     */
    IDiagramCommandFactoryProvider getEmfCommandFactoryProvider();

    /**
     * Sets the command factory provider to use for this editor.
     * 
     * @param emfCommandFactoryProvider
     *            the command factory provider to use.
     * @since 0.9.0
     */
    void setEmfCommandFactoryProvider(IDiagramCommandFactoryProvider emfCommandFactoryProvider);

    /**
     * Return the adapter factory used for providing views of the model of this
     * editor.
     * 
     * @return the adapter factory used for providing views of the model of this
     *         editor.
     */
    AdapterFactory getAdapterFactory();
}
