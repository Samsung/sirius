/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES and Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.session.danalysis;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistryListener2;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.session.SessionListener;
import org.eclipse.sirius.business.internal.migration.resource.ResourceFileExtensionPredicate;
import org.eclipse.sirius.tools.api.interpreter.InterpreterRegistry;
import org.eclipse.sirius.viewpoint.Messages;
import org.eclipse.sirius.viewpoint.SiriusPlugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Reload the VSMs used inside a session when the global registry detects their
 * content has changed.
 * 
 * @author pcdavid
 */
public class SessionVSMUpdater implements ViewpointRegistryListener2 {
    private final DAnalysisSessionImpl session;

    /**
     * Create a new update for the specified session.
     * 
     * @param session
     *            the session to update.
     */
    public SessionVSMUpdater(DAnalysisSessionImpl session) {
        this.session = Preconditions.checkNotNull(session);
    }

    @Override
    public void modelerDesciptionFilesLoaded() {
        Collection<Resource> allResources = Lists.newArrayList(session.getTransactionalEditingDomain().getResourceSet().getResources());
        for (Resource res : Iterables.filter(allResources, new ResourceFileExtensionPredicate(SiriusUtil.DESCRIPTION_MODEL_EXTENSION, true))) {
            // Unload emtpy odesign.
            if (!res.isModified() && res.isLoaded() && res.getContents().isEmpty()) {
                session.unregisterResourceInCrossReferencer(res);
                res.unload();
            }
            // Reload unloaded odesign (ViewpointRegistry can unload them).
            IFile correspondingFile = WorkspaceSynchronizer.getFile(res);
            if (!res.isLoaded() && correspondingFile != null && correspondingFile.exists()) {
                try {
                    res.load(Collections.emptyMap());
                    if (res.isLoaded() && !res.getContents().isEmpty()) {
                        session.registerResourceInCrossReferencer(res);
                        // Refresh the imports of interpreter in case of new
                        // Java Extension
                        InterpreterRegistry.prepareImportsFromSession(session.getInterpreter(), session);
                    }
                } catch (IOException e) {
                    SiriusPlugin.getDefault().warning(MessageFormat.format(Messages.SessionVSMUpdater_VSMLoadErrorMsg, res.getURI()), e);
                }
            }
        }
        session.notifyListeners(SessionListener.VSM_UPDATED);
    }
}
