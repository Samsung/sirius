/*******************************************************************************
 * Copyright (c) 2012, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.internal.actions.export;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.sirius.business.api.query.URIQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ui.tools.api.actions.export.ExportAction;
import org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog;
import org.eclipse.sirius.ui.tools.api.dialogs.ExportOneRepresentationAsImageDialog;
import org.eclipse.sirius.ui.tools.api.dialogs.ExportSeveralRepresentationsAsImagesDialog;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.provider.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Abstract action to export representations as images.
 * 
 * @author mporhel
 */
public abstract class AbstractExportRepresentationsAction extends Action {

    /**
     * Creates a new AbstractExportRepresentationsAction with the given text and
     * image.
     * 
     * @param text
     *            the action's text, or <code>null</code> if there is no text
     * @param image
     *            the action's image, or <code>null</code> if there is no image
     * @see #setText
     * @see #setImageDescriptor
     */
    protected AbstractExportRepresentationsAction(String text, ImageDescriptor image) {
        super(text, image);
    }

    /**
     * Collect the diagrams to export, get the corresponding session, compute
     * the export path and then show the path and file format dialog to the user
     * before exporting export the diagrams.
     */
    @Override
    public void run() {
        Collection<DRepresentationDescriptor> collectedRepDescriptors = getRepresentationToExport();
        Iterable<DRepresentationDescriptor> dRepresentationsToExport = Iterables.filter(collectedRepDescriptors, Predicates.notNull());
        if (!Iterables.isEmpty(dRepresentationsToExport)) {
            DRepresentationDescriptor firstDRepDescriptorToExport = dRepresentationsToExport.iterator().next();
            Session session = getSession(firstDRepDescriptorToExport);
            if (session != null) {
                IPath exportPath = getExportPath(firstDRepDescriptorToExport, session);

                if (exportPath != null) {
                    exportRepresentation(exportPath, dRepresentationsToExport, session);
                }
            }
        }
    }

    /**
     * Collect the representations to export.
     * 
     * @return the {@link DRepresentationDescriptor} corresponding to the
     *         representation to export.
     */
    protected abstract Collection<DRepresentationDescriptor> getRepresentationToExport();

    /**
     * Get the corresponding session.
     * 
     * @param repDescriptor
     *            a representation descriptor.
     * @return the session.
     */
    protected abstract Session getSession(DRepresentationDescriptor repDescriptor);

    /**
     * Display the export path and file format dialog and then export the
     * representations.
     * 
     * @param exportPath
     *            the default export path.
     * @param representationsToExport
     *            represents the representation to export.
     * @param session
     *            the corresponding session.
     */
    protected void exportRepresentation(IPath exportPath, Iterable<DRepresentationDescriptor> representationsToExport, Session session) {
        final Shell shell = Display.getCurrent().getActiveShell();

        final AbstractExportRepresentationsAsImagesDialog dialog;
        if (Iterables.size(representationsToExport) > 1) {
            dialog = new ExportSeveralRepresentationsAsImagesDialog(shell, exportPath);
        } else {
            dialog = new ExportOneRepresentationAsImageDialog(shell, exportPath, representationsToExport.iterator().next().getName());
        }

        if (dialog.open() == Window.OK) {
            Collection<DRepresentation> dRepresentations = Lists.newArrayList();
            for (DRepresentationDescriptor dRepresentationDescriptor : representationsToExport) {
                dRepresentations.add(dRepresentationDescriptor.getRepresentation());
            }
            List<DRepresentation> toExport = Lists.<DRepresentation> newArrayList(dRepresentations);
            final ExportAction exportAction = new ExportAction(session, toExport, dialog.getOutputPath(), dialog.getImageFormat(), dialog.isExportToHtml());
            final ProgressMonitorDialog pmd = new ProgressMonitorDialog(shell);
            try {
                pmd.run(false, false, exportAction);
            } catch (final InvocationTargetException e) {
                MessageDialog.openError(shell, Messages.AbstractExportRepresentationsAction_error, e.getTargetException().getMessage());
            } catch (final InterruptedException e) {
                MessageDialog.openInformation(shell, Messages.AbstractExportRepresentationsAction_error, e.getMessage());
            } finally {
                pmd.close();
            }
        } else {
            dialog.close();
        }
    }

    /**
     * Compute the default export path.
     * 
     * @param repDescriptor
     *            the first selected representation descriptor.
     * @param session
     *            the corresponding session.
     * @return the export path.
     */
    protected IPath getExportPath(DRepresentationDescriptor repDescriptor, Session session) {
        URI representationResourceURI = repDescriptor.getRepresentation().eResource().getURI();
        URIQuery uriQuery = new URIQuery(representationResourceURI);
        Option<IResource> iResourceOption = uriQuery.getCorrespondingResource();
        if (iResourceOption.some()) {
            return iResourceOption.get().getParent().getLocation();
        }

        IPath exportPath = Platform.getLocation();
        URI mainSessionResourceURI = session.getSessionResource().getURI();
        uriQuery = new URIQuery(mainSessionResourceURI);
        iResourceOption = uriQuery.getCorrespondingResource();
        if (iResourceOption.some()) {
            exportPath = iResourceOption.get().getParent().getLocation();
        } else if (uriQuery.isInMemoryURI()) {
            String opaquePart = mainSessionResourceURI.opaquePart();
            if (!StringUtil.isEmpty(opaquePart)) {
                String[] segments = opaquePart.split(Pattern.quote("/")); //$NON-NLS-1$
                if (segments != null && segments.length > 0) {
                    String projectName = segments[0];
                    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
                    if (project.exists()) {
                        exportPath = project.getLocation();
                    }
                }
            }
        }
        return exportPath;
    }
}
