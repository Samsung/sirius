/*******************************************************************************
 * Copyright (c) 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot.modelexplorer;

import java.util.Collections;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.sirius.common.ui.tools.api.util.EclipseUIUtil;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ExtenderConstants;
import org.eclipse.sirius.ecore.extender.business.api.permission.IPermissionProvider;
import org.eclipse.sirius.ecore.extender.business.api.permission.PermissionAuthorityRegistry;
import org.eclipse.sirius.ecore.extender.business.internal.permission.DefaultPermissionProvider;
import org.eclipse.sirius.ecore.extender.business.internal.permission.PermissionService;
import org.eclipse.sirius.ecore.extender.business.internal.permission.ReadOnlyPermissionAuthority;
import org.eclipse.sirius.ecore.extender.business.internal.permission.descriptors.StandalonePermissionProviderDescriptor;
import org.eclipse.sirius.tests.swtbot.Activator;
import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusDiagramEditor;
import org.eclipse.sirius.tests.swtbot.support.utils.SWTBotUtils;
import org.eclipse.sirius.ui.business.api.dialect.DialectEditor;
import org.eclipse.sirius.ui.tools.api.views.RefreshLabelImageJob;
import org.eclipse.sirius.ui.tools.api.views.modelexplorerview.IModelExplorerView;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;

/**
 * Verify that ModelExplorer view is refreshed on notifications about lock
 * status changes.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class LockedModelExplorerTest extends AbstractSiriusSwtBotGefTestCase {

    private static final String SEMANTIC_RESOURCE_NAME = "My.ecore";

    private static final String SESSION_RESOURCE_NAME = "representations.aird";

    private static final String DATA_UNIT_DIR = "data/unit/modelExplorer/";

    private static final String REPRESENTATION_DESCRIPTION_NAME = "P0 package entities";

    private UIResource sessionAirdResource;

    SWTBotView modelExplorerView;

    IJobChangeListener jobChangeListener;

    boolean refreshJobScheduled;

    StandalonePermissionProviderDescriptor permissionProviderDescriptor;

    @Override
    protected void onSetUpBeforeClosingWelcomePage() throws Exception {
        // We close the Sirius perspective. If it was opened by another test
        // before, the ModelExplorer view is open in 2 perspectives and the
        // close view made in test has not the expected result.
        designerPerspectives.closeSiriusPerspective();
        copyFileToTestProject(Activator.PLUGIN_ID, DATA_UNIT_DIR, SEMANTIC_RESOURCE_NAME, SESSION_RESOURCE_NAME);
    }

    @Override
    protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
        // Init Sirius with a custom readOnlyPermissionAuthority.
        ReadOnlyPermissionAuthority readOnlyPermissionAuthority = new ReadOnlyPermissionAuthority();
        IPermissionProvider permissionProvider = new DefaultPermissionProvider(readOnlyPermissionAuthority);
        permissionProviderDescriptor = new StandalonePermissionProviderDescriptor("org.eclipse.sirius.tests.swtbot.lockModelExplorerPermissionAuthorityProvider", ExtenderConstants.HIGHEST_PRIORITY,
                permissionProvider);
        PermissionService.addExtension(permissionProviderDescriptor);

        // Open the session
        sessionAirdResource = new UIResource(designerProject, SESSION_RESOURCE_NAME);
        localSession = designerPerspective.openSessionFromFile(sessionAirdResource, true);

        // Open the editor
        editor = (SWTBotSiriusDiagramEditor) openRepresentation(localSession.getOpenedSession(), "Entities", REPRESENTATION_DESCRIPTION_NAME, DDiagram.class);

        modelExplorerView = bot.viewById(IModelExplorerView.ID);
        modelExplorerView.setFocus();

        // Add a IJobChangeListener only to detect if a refresh job is scheduled
        // (and so launched)
        jobChangeListener = new JobChangeAdapter() {
            @Override
            public void scheduled(IJobChangeEvent event) {
                refreshJobScheduled = event.getJob().belongsTo(RefreshLabelImageJob.FAMILY);
            }
        };
        Job.getJobManager().addJobChangeListener(jobChangeListener);
    }

    @Override
    protected void tearDown() throws Exception {
        // Remove the job change listener
        Job.getJobManager().removeJobChangeListener(jobChangeListener);
        // Remove the permission provider
        PermissionService.removeExtension(permissionProviderDescriptor);
        permissionProviderDescriptor = null;
        jobChangeListener = null;
        sessionAirdResource = null;
        modelExplorerView = null;
        super.tearDown();
    }

    /**
     * Check that the refresh job is launched when a permission authority
     * notification is launched and the ModelExplorer view is opened. And also
     * check that the refresh job is not launched when a permission authority
     * notification is launched and the ModelExplorer view is closed.
     */
    public void testRefreshJobForModelExplorerView() {

        assertFalse("The job should not be scheduled as no notification has been send.", refreshJobScheduled);
        lockRepresentation(true);
        assertTrue("The job should be scheduled as one lock notification has been send and ModelExplorer view is opened.", refreshJobScheduled);
        try {
            Job.getJobManager().join(RefreshLabelImageJob.FAMILY, new NullProgressMonitor());
        } catch (OperationCanceledException e) {
            fail("Problem during waiting of RefreshLabelImageJob: " + e.getMessage());
        } catch (InterruptedException e) {
            fail("Problem during waiting of RefreshLabelImageJob: " + e.getMessage());
        }
        refreshJobScheduled = false;
        modelExplorerView.setFocus();
        SWTBotUtils.waitAllUiEvents();
        modelExplorerView.close();
        try {
            SWTBotUtils.waitAllUiEvents();
            lockRepresentation(false);
            assertFalse("The job should not be scheduled as one unlock notification has been send and ModelExplorer view is not opened.", refreshJobScheduled);
        } finally {
            takeScreenshot("before reopening of ModelExplorer view");
            // Reopen the model explorer view (for following tests in suite)
            Display.getDefault().syncExec(new Runnable() {
                @Override
                public void run() {
                    EclipseUIUtil.showView(IModelExplorerView.ID);
                }
            });
        }
    }

    /**
     * Lock the representation of the current editor.
     */
    private void lockRepresentation(boolean lock) {
        // activate the ReadOnlyPermission Authority on the representation
        DialectEditor dialectEditor = (DialectEditor) editor.getReference().getEditor(false);
        DRepresentation representation = dialectEditor.getRepresentation();
        ReadOnlyPermissionAuthority permissionAuthority = (ReadOnlyPermissionAuthority) PermissionAuthorityRegistry.getDefault().getPermissionAuthority(representation);
        permissionAuthority.activate();
        if (lock) {
            permissionAuthority.notifyLock(Collections.singleton(representation));
        } else {
            permissionAuthority.notifyUnlock(Collections.singleton(representation));
        }
    }
}
