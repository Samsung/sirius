/*******************************************************************************
 * Copyright (c) 2011, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.internal.views.common.modelingproject.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.resources.mapping.ModelStatus;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.query.FileQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionStatus;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ui.tools.internal.views.common.modelingproject.InvalidModelingProjectMarkerUpdaterJob;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.sirius.viewpoint.provider.Messages;

/**
 * Modeling-aware model provider.
 *
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class ModelingModelProvider extends ModelProvider {

    /** The model provider id. */
    public static final String MODELING_MODEL_PROVIDER_ID = "org.eclipse.sirius.ui.modelProvider"; //$NON-NLS-1$

    /**
     * Creates a new java model provider.
     */
    public ModelingModelProvider() {
        // Used by the runtime
    }

    @Override
    public ResourceMapping[] getMappings(final IResource resource, final ResourceMappingContext context, final IProgressMonitor monitor) throws CoreException {
        ResourceMapping[] result = null;
        if (resource instanceof IProject) {
            Option<ModelingProject> optionalModelingProject = ModelingProject.asModelingProject((IProject) resource);
            if (optionalModelingProject.some()) {
                result = new ResourceMapping[] { ModelingElementResourceMapping.create(optionalModelingProject.get()) };
            }
        }
        if (result == null) {
            final Object adapted = resource.getAdapter(ResourceMapping.class);
            if (adapted instanceof ResourceMapping) {
                result = new ResourceMapping[] { (ResourceMapping) adapted };
            }
        }
        if (result == null) {
            result = new ResourceMapping[] { new ModelingResourceMapping(resource) };
        }
        return result;
    }

    @Override
    public IStatus validateChange(IResourceDelta delta, IProgressMonitor monitor) {
        IStatus result = null;
        if (delta != null) {
            try {
                ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
                delta.accept(visitor);
                if (visitor.getProjectContainingSessionToSaveToDelete().size() == 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID, Messages.ModelingModelProvider_satusUnsavedDataWillBeLost);
                } else if (visitor.getProjectContainingSessionToSaveToDelete().size() > 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID,
                            MessageFormat.format(Messages.ModelingModelProvider_satusUnsaveDataWillBeLostWithProjectNames, getProjectsName(visitor.getProjectContainingSessionToSaveToDelete())));
                } else if (visitor.getMainRepresentationsFilesToDelete().size() == 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID,
                            MessageFormat.format(Messages.ModelingModelProvider_mainRepresentationFileDeleted, visitor.getMainRepresentationsFilesToDelete().get(0).getProject().getName()));
                } else if (visitor.getMainRepresentationsFilesToDelete().size() > 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID,
                            MessageFormat.format(Messages.ModelingModelProvider_mainRepresentationFilesOfSomeProjectsDeleted, getFilesProjectName(visitor.getMainRepresentationsFilesToDelete())));
                } else if (visitor.getRepresentationsFileToAddOnValidModelingProject().size() == 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID, MessageFormat
                            .format(Messages.ModelingModelProvider_addAnotherRepresentationFile, visitor.getRepresentationsFileToAddOnValidModelingProject().get(0).getProject().getName()));
                } else if (visitor.getRepresentationsFileToAddOnValidModelingProject().size() > 1) {
                    result = new ModelStatus(IStatus.ERROR, SiriusPlugin.ID, ModelingModelProvider.MODELING_MODEL_PROVIDER_ID, MessageFormat
                            .format(Messages.ModelingModelProvider_addAnotherRepresentationFileSeveralProjects, getFilesProjectName(visitor.getRepresentationsFileToAddOnValidModelingProject())));
                }
            } catch (CoreException e) {
                /* do nothing */
            }
        }
        if (result == null) {
            result = super.validateChange(delta, monitor);
        }
        return result;
    }

    /**
     * Return a String representing the name of this projects separated by a
     * comma.
     *
     * @param projects
     *            the list of projects
     * @return a String representing the name of this projects separated by a
     *         comma.
     */
    private String getProjectsName(List<IProject> projects) {
        StringBuffer result = new StringBuffer();
        for (Iterator<IProject> iterator = projects.iterator(); iterator.hasNext(); /* */) {
            IProject project = iterator.next();
            result.append(project.getName());
            if (iterator.hasNext()) {
                result.append(", "); //$NON-NLS-1$
            }
        }
        return result.toString();
    }

    /**
     * Return a String representing the name of the project of this files
     * separated by a comma.
     *
     * @param files
     *            the list of files
     * @return a String representing the name of this projects separated by a
     *         comma.
     */
    private String getFilesProjectName(List<IFile> files) {
        StringBuffer result = new StringBuffer();
        for (Iterator<IFile> iterator = files.iterator(); iterator.hasNext(); /* */) {
            IFile file = iterator.next();
            result.append(file.getProject().getName());
            if (iterator.hasNext()) {
                result.append(", "); //$NON-NLS-1$
            }
        }
        return result.toString();
    }

    /**
     * The visitor used to detect resource changes :
     * <UL>
     * <LI>Deletion of a modeling project with opened representations file</LI>
     * <LI>Deletion of the main representations file of a modeling project</LI>
     * <LI>Add of representations file in a valid modeling project (already with
     * a main representations file).</LI>
     * </UL>
     *
     * @author lredor.
     */
    private final class ResourceDeltaVisitor implements IResourceDeltaVisitor {
        List<IProject> projectContainingSessionToSaveToDelete = new ArrayList<IProject>();

        List<IFile> mainRepresentationsFileToDelete = new ArrayList<IFile>();

        List<IFile> representationsFileToAddOnValidModelingProject = new ArrayList<IFile>();

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            boolean visitChildren = true;
            IResource res = delta.getResource();
            if (res.getType() == IResource.PROJECT) {
                visitChildren = visitProject(delta, (IProject) res);
            } else if (res.getType() == IResource.FILE) {
                visitChildren = visitFile(delta, (IFile) res);

            }
            return visitChildren;
        }

        /**
         * Visits the given resource delta that corresponds to a project.
         *
         * @param delta
         *            The given resource delta
         * @param project
         *            The project concerning by this delta
         * @return <code>true</code> if the resource delta's children should be
         *         visited; <code>false</code> if they should be skipped.
         * @exception CoreException
         *                if the visit fails for some reason.
         */
        protected boolean visitProject(IResourceDelta delta, IProject project) throws CoreException {
            // By default, we do not visit the resource delta's children
            boolean visitChildren = false;
            final Option<ModelingProject> optionalModelingProject = ModelingProject.asModelingProject(project);
            if (optionalModelingProject.some()) {
                if (IResourceDelta.REMOVED == delta.getKind()) {
                    Session session = optionalModelingProject.get().getSession();
                    if (session != null && session.isOpen() && SessionStatus.DIRTY.equals(session.getStatus())) {
                        projectContainingSessionToSaveToDelete.add(project);
                    }

                    return false;
                } else {
                    // Visit the resource delta's children (because they can
                    // corresponds to representations file deletion)
                    visitChildren = true;
                }
            }
            return visitChildren;
        }

        /**
         * Visits the given resource delta that corresponds to a file.
         *
         * @param delta
         *            The given resource delta
         * @param file
         *            The file concerning by this delta
         * @return <code>true</code> if the resource delta's children should be
         *         visited; <code>false</code> if they should be skipped.
         * @exception CoreException
         *                if the visit fails for some reason.
         */
        protected boolean visitFile(IResourceDelta delta, IFile currentFile) throws CoreException {
            // By default, we do not visit the resource delta's children
            boolean visitChildren = false;
            final Option<ModelingProject> optionalModelingProject = ModelingProject.asModelingProject(currentFile.getProject());
            if (optionalModelingProject.some()) {
                if (IResourceDelta.REMOVED == delta.getKind()) {
                    if (optionalModelingProject.get().isValid()) {
                        // Check that this IFile is not the main representations
                        // file of this project
                        if (optionalModelingProject.get().isMainRepresentationsFile(currentFile)) {
                            mainRepresentationsFileToDelete.add(currentFile);
                        }
                    } else if (new FileQuery(currentFile).isSessionResourceFile()) {
                        // If the project is not valid and the deleted file is a
                        // representations file, validate the project again.
                        try {
                            optionalModelingProject.get().getMainRepresentationsFileURI(new NullProgressMonitor(), true, true);
                        } catch (IllegalArgumentException e) {
                            IProject project = optionalModelingProject.get().getProject();
                            Job invalidModelingProjectMarkerUpdaterJob = new InvalidModelingProjectMarkerUpdaterJob(project, e.getMessage());
                            invalidModelingProjectMarkerUpdaterJob.schedule();
                        }
                    }
                } else if (IResourceDelta.ADDED == delta.getKind()) {
                    // Check that the corresponding project does not already
                    // have a main representations file
                    if (SiriusUtil.SESSION_RESOURCE_EXTENSION.equals(currentFile.getFileExtension())) {
                        if (optionalModelingProject.get().isValid()) {
                            representationsFileToAddOnValidModelingProject.add(currentFile);
                        }
                    }
                }
            }
            return visitChildren;
        }

        public List<IProject> getProjectContainingSessionToSaveToDelete() {
            return projectContainingSessionToSaveToDelete;
        }

        public List<IFile> getMainRepresentationsFilesToDelete() {
            return mainRepresentationsFileToDelete;
        }

        public List<IFile> getRepresentationsFileToAddOnValidModelingProject() {
            return representationsFileToAddOnValidModelingProject;
        }
    }
}
