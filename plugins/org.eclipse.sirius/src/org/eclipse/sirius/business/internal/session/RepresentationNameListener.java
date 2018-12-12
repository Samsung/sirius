/*******************************************************************************
 * Copyright (c) 2011, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.session;

import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

/**
 * Post-commit listener which notifies the session manager when a representation
 * is renamed so that session manager listeners (e.g. views) can react).
 * 
 * @author pcdavid
 */
public class RepresentationNameListener extends ResourceSetListenerImpl {

    private Session session;

    /**
     * Default constructor.
     * 
     * @param session
     *            the {@link Session}
     */
    public RepresentationNameListener(Session session) {
        super(NotificationFilter.NOT_TOUCH
                .and(NotificationFilter.createNotifierTypeFilter(DRepresentation.class).and(NotificationFilter.createFeatureFilter(ViewpointPackage.Literals.DREPRESENTATION__NAME))));
        this.session = session;
        session.getTransactionalEditingDomain().addResourceSetListener(this);
    }

    @Override
    public boolean isPostcommitOnly() {
        return true;
    }

    @Override
    public void resourceSetChanged(ResourceSetChangeEvent event) {
        for (int i = 0; i < event.getNotifications().size(); i++) {
            SessionManager.INSTANCE.notifyRepresentationRenamed(session);
        }
    }

    /**
     * Dispose this resource.
     */
    public void dispose() {
        getTarget().removeResourceSetListener(this);
        session = null;
    }
}
