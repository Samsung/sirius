/*******************************************************************************
 * Copyright (c) 2007, 2016 THALES GLOBAL SERVICES.
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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.business.api.migration.AirdResourceVersionMismatchException;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.TreeItemWrapper;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.TypedVariable;
import org.eclipse.sirius.viewpoint.description.tool.SelectModelElementVariable;

/**
 * This interface is called when one need a UI callback, for variables
 * selection, or any other user interface stuff.
 * 
 * @author cbrun
 */
public interface UICallBack {
    /**
     * Called when the user interface should prompt for a variable selection.
     * 
     * @param model
     *            eObject of the current context.
     * 
     * @param variable
     *            the variable to select.
     * @return a Collection of selected EObjects.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    Collection<EObject> askForVariableValues(EObject model, SelectModelElementVariable variable) throws InterruptedException;

    /**
     * Called when the user interface should prompt for a name.
     * 
     * @param defaultName
     *            the default name.
     * @return the name that has been inputed by the user.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    String askForDetailName(String defaultName) throws InterruptedException;

    /**
     * Called when the user interface should prompt for a name.
     * 
     * @param defaultName
     *            the default name.
     * @param representationDescriptionDoc
     *            the documentation of the future representation
     * @return the name that has been inputed by the user.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     * @deprecated Replaced by {@link #askForDetailName(String, String, String)}
     * 
     */
    @Deprecated
    String askForDetailName(String defaultName, String representationDescriptionDoc) throws InterruptedException;

    /**
     * Called when the user interface should prompt for a name.
     * 
     * @param defaultName
     *            the default name.
     * @param representationDescriptionName
     *            the name of the representation description used for the future
     *            representation
     * @param representationDescriptionDoc
     *            the documentation of the future representation
     * @return the name that has been inputed by the user.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    String askForDetailName(String defaultName, String representationDescriptionName, String representationDescriptionDoc) throws InterruptedException;

    /**
     * Called when the user interface should prompt for a selection of EObject
     * instances.
     * 
     * @param message
     *            the message to display.
     * @param input
     *            the tree of objects as input.
     * @param factory
     *            the adapter factory to provides labels and icons for the
     *            objects.
     * @return a list of the selected {@link EObject}.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    Collection<EObject> askForEObjects(String message, TreeItemWrapper input, AdapterFactory factory) throws InterruptedException;

    /**
     * Called when the user interface should prompt for an EObject.
     * 
     * @param message
     *            the message to display.
     * @param input
     *            the tree of objects as input.
     * @param factory
     *            the adapter factory to provides labels and icons for the
     *            objects.
     * @return the selected {@link EObject}.
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    EObject askForEObject(String message, TreeItemWrapper input, AdapterFactory factory) throws InterruptedException;

    /**
     * Open a dialog and ask for yes/no.
     * 
     * @param eObjects
     *            the list of EObjects to display.
     * @param title
     *            the dialog title.
     * @param message
     *            the displayed message.
     * @return true if user choose ok, false otherwise.
     */
    boolean openEObjectsDialogMessage(Collection<EObject> eObjects, String title, String message);

    /**
     * Open a {@link DRepresentation} using a {@link Session}.
     * 
     * @param openedSession
     *            the current session.
     * @param representation
     *            the representation to open with the session.
     */
    void openRepresentation(final Session openedSession, final DRepresentation representation);

    /**
     * Load a resource in the resource set associated to the editing domain
     * given as parameter.
     * 
     * @param file
     *            the file
     * @param domain
     *            the editing domain
     * @return the loaded resource, <code>null</code> if the loading fails.
     * @since 0.9.0
     */
    Resource loadResource(final EditingDomain domain, final IFile file);

    /**
     * Called when the user interface should prompt for a choice.
     * 
     * @param resource
     *            the externally changed resource.
     * @return <code>true</code> if the resource should be reloaded.
     * @since 0.9.0
     */
    boolean shouldReload(final Resource resource);

    /**
     * Called when the user interface should prompt for a choice.
     * 
     * @param resource
     *            the externally deleted resource.
     * @return <code>true</code> if the resource should be removed from the
     *         session.
     * @since 0.9.0
     */
    boolean shouldRemove(final Resource resource);

    /**
     * Called when the user interface should prompt for a choice. The deleted
     * resource contains session critical data.
     * 
     * @param session
     *            the current session.
     * @param resource
     *            the externally deleted resource.
     * @return <code>true</code> if the session should be closed.
     * @since 0.9.0
     */
    boolean shouldClose(final Session session, final Resource resource);

    /**
     * Session name to display while saving this.
     * 
     * @param session
     *            the representation's session.
     * @return part of message to display while saving session.
     */
    String getSessionNameToDisplayWhileSaving(Session session);

    /**
     * Open error message.
     * 
     * @param title
     *            of the dialog
     * @param message
     *            of the dialog
     */
    void openError(String title, String message);

    /**
     * Open an UI to ask the user the value corresponding to each TypedVariable
     * of typedVariableList. </br>
     * The returned list has the same size as typedVariableList
     * 
     * @param typedVariableList
     *            the list of variable for which to get the values
     * @param defaultValues
     *            the default values used to initialize UI. This list must have
     *            the typedVariableList size.
     * @return the value provided by the user
     * @throws InterruptedException
     *             when the process is interrupted (for instance the user
     *             pressed "cancel".)
     */
    List<String> askForTypedVariable(List<TypedVariable> typedVariableList, List<String> defaultValues) throws InterruptedException;;

    /**
     * Ask to end-user if he wants to open the session ignoring the resource
     * version mismatch or not.
     * 
     * @param e
     *            the {@link AirdResourceVersionMismatchException} holding
     *            mismatch informations
     * 
     * @return true to reopen session false otherwise
     * @since 4.0
     */
    boolean askSessionReopeningWithResourceVersionMismatch(AirdResourceVersionMismatchException e);
}
