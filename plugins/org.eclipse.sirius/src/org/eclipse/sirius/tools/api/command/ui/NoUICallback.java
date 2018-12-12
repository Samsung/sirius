/*******************************************************************************
 * Copyright (c) 2009, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tools.api.command.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.business.api.migration.AirdResourceVersionMismatchException;
import org.eclipse.sirius.business.api.resource.LoadEMFResource;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.TreeItemWrapper;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.TypedVariable;
import org.eclipse.sirius.viewpoint.description.tool.SelectModelElementVariable;

/**
 * This will return default values for the UI-dependent tools.
 * 
 * @author Laurent Goubet
 *         <a href="mailto:laurent.goubet@obeo.fr">laurent.goubet@obeo.fr</a>
 */
public class NoUICallback implements UICallBack {
    /**
     * Default constructor.
     */
    public NoUICallback() {
    }

    /**
     * {@inheritDoc}
     * 
     * @see viewpoint.command.ui.UICallBack#askForDetailName(java.lang.String)
     */
    @Override
    public String askForDetailName(final String defaultName) throws InterruptedException {
        return defaultName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#askForDetailName(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String askForDetailName(String defaultName, String representationDescription) throws InterruptedException {
        return defaultName;
    }

    @Override
    public String askForDetailName(String defaultName, String representationDescriptionName, String representationDescriptionDoc) throws InterruptedException {
        return defaultName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#askForVariableValues(org.eclipse.emf.ecore.EObject,
     *      org.eclipse.sirius.viewpoint.description.tool.SelectModelElementVariable)
     */
    @Override
    public Collection<EObject> askForVariableValues(final EObject model, final SelectModelElementVariable variable) throws InterruptedException {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#openEObjectsDialogMessage(java.util.Collection,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public boolean openEObjectsDialogMessage(final Collection<EObject> objects, final String title, final String message) {
        return false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void openRepresentation(Session openedSession, DRepresentation representation) {
        // doNothing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource loadResource(final EditingDomain domain, final IFile file) {
        final LoadEMFResource operation = new LoadEMFResource(domain.getResourceSet(), file);
        operation.run();
        return operation.getLoadedResource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<EObject> askForEObjects(String message, TreeItemWrapper input, AdapterFactory factory) throws InterruptedException {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EObject askForEObject(String message, TreeItemWrapper input, AdapterFactory factory) throws InterruptedException {
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#shouldClose(Session,
     *      Resource)
     */
    @Override
    public boolean shouldClose(Session session, Resource resource) {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#shouldReload(Resource)
     */
    @Override
    public boolean shouldReload(Resource resource) {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#shouldRemove(Resource)
     */
    @Override
    public boolean shouldRemove(Resource resource) {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#getSessionDisplayed(org.eclipse.sirius.business.api.session.Session)
     */
    @Override
    public String getSessionNameToDisplayWhileSaving(Session session) {
        return ""; //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.tools.api.command.ui.UICallBack#openError(java.lang
     *      .String, java.lang.String)
     */
    @Override
    public void openError(String title, String message) {
        // do nothing
    }

    @Override
    public List<String> askForTypedVariable(List<TypedVariable> typedVariableList, List<String> defaultValues) {
        return Collections.emptyList();
    }

    @Override
    public boolean askSessionReopeningWithResourceVersionMismatch(AirdResourceVersionMismatchException e) {
        return false;
    }
}
