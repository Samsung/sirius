/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.api.query;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.helper.SiriusUtil;

/**
 * Query allowing to determine the type of a file (i.e. if it contains a VSM, a
 * DAnalysis...). It is leaner than the {@link ResourceQuery}, which requires a
 * loaded EMF resource.
 * 
 * @author <a href="mailto:alex.lagarde@obeo.fr">Alex Lagarde</a>
 * 
 */
public class FileQuery {

    private static Object sessionResourceFactory;

    private String fileExtension;

    /**
     * Constructor.
     * 
     * @param file
     *            the file to check
     */
    public FileQuery(IFile file) {
        this.fileExtension = file.getFileExtension();
    }

    /**
     * Constructor.
     * 
     * @param fileExtension
     *            the file extension to check
     */
    public FileQuery(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Indicates if the file extension indicates that it contains a VSM.
     * 
     * @return true if the file extension indicates that it contains a VSM,
     *         false otherwise
     */
    public boolean isVSMFile() {

        return SiriusUtil.DESCRIPTION_MODEL_EXTENSION.equals(fileExtension);
    }

    /**
     * Indicates if the file extension indicates that it contains a DAnalysis
     * (aird, aird fragments...).
     * 
     * @return true if the file extension indicates that it contains a
     *         DAnalysis, false otherwise
     */
    public boolean isSessionResourceFile() {
        Object fileResourceFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get(fileExtension);
        // A session resource file should be associated to the same
        // ResourceFactory than "aird" files
        return fileResourceFactory != null && fileResourceFactory.equals(getSessionResourceFactory());
    }

    private static Object getSessionResourceFactory() {
        if (sessionResourceFactory == null) {
            sessionResourceFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get(SiriusUtil.SESSION_RESOURCE_EXTENSION);
        }
        return sessionResourceFactory;
    }

}
