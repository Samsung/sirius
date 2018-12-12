/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *    IBM Corporation and others - The code for default load/save options was lifted
 *      from GMF's org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory.
 *******************************************************************************/
package org.eclipse.sirius.business.internal.resource;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.eclipse.sirius.business.internal.migration.RepresentationsFileMigrationService;
import org.eclipse.sirius.business.internal.migration.RepresentationsFileVersionSAXParser;
import org.eclipse.sirius.common.tools.api.resource.ResourceMigrationMarker;
import org.osgi.framework.Version;

import com.google.common.collect.Maps;

/**
 * A resource factory decorator to set XMI encodings.
 * 
 * @author ymortier
 */
public class AirDResourceFactory extends XMIResourceFactoryImpl {

    private static final String XMI_ENCODING = "UTF-8"; //$NON-NLS-1$

    // default load options.
    private static final Map<Object, Object> DEFAULT_LOAD_OPTIONS = Maps.newHashMap();

    // default save options.
    private static final Map<Object, Object> DEFAULT_SAVE_OPTIONS = Maps.newHashMap();

    static {

        XMIResource resource = new XMIResourceImpl();

        // default load options.
        DEFAULT_LOAD_OPTIONS.putAll(resource.getDefaultLoadOptions());
        DEFAULT_LOAD_OPTIONS.put(XMIResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);

        // default save options.
        DEFAULT_SAVE_OPTIONS.putAll(resource.getDefaultSaveOptions());
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_DECLARE_XML, Boolean.TRUE);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
        DEFAULT_SAVE_OPTIONS.put(XMIResource.OPTION_ENCODING, XMI_ENCODING);
    }

    /**
     * Get default load options.
     * 
     * @return the default load options
     */
    public static Map<Object, Object> getDefaultLoadOptions() {
        return DEFAULT_LOAD_OPTIONS;
    }

    /**
     * Get default save options.
     * 
     * @return the default save options
     */
    public static Map<Object, Object> getDefaultSaveOptions() {
        return DEFAULT_SAVE_OPTIONS;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory#createResource(org.eclipse.emf.common.util.URI)
     */
    @Override
    public Resource createResource(final URI uri) {
        RepresentationsFileVersionSAXParser parser = new RepresentationsFileVersionSAXParser(uri);
        boolean migrationIsNeeded = true;
        String loadedVersion = parser.getVersion(new NullProgressMonitor());
        if (loadedVersion != null) {
            migrationIsNeeded = RepresentationsFileMigrationService.getInstance().isMigrationNeeded(Version.parseVersion(loadedVersion));
        }

        final XMIResource resource = doCreateAirdResourceImpl(uri);
        setOptions(resource, migrationIsNeeded, loadedVersion);

        if (!resource.getEncoding().equals(XMI_ENCODING)) {
            resource.setEncoding(XMI_ENCODING);
        }

        if (migrationIsNeeded) {
            ResourceMigrationMarker.addMigrationMarker(resource);
        }
        return resource;
    }

    /**
     * Returns the implementation of the AirdResourceImpl to use.
     * 
     * @param uri
     *            the uri of the AirdResource
     * @return the implementation of the AirdResourceImpl to use
     */
    protected XMIResource doCreateAirdResourceImpl(URI uri) {
        return new AirDResourceImpl(uri);
    }

    /**
     * Sets the options to associate to the AirDResource.
     * 
     * @param resource
     *            the resource being loaded
     * @param migrationIsNeeded
     *            if a migration is needed.
     * @param loadedVersion
     *            the loaded version.
     */
    private void setOptions(XMIResource resource, boolean migrationIsNeeded, String loadedVersion) {

        final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
        final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
        /* default load options. */
        loadOptions.putAll(getDefaultLoadOptions());
        /* default save options. */
        saveOptions.putAll(getDefaultSaveOptions());

        loadOptions.put(XMLResource.OPTION_DEFER_ATTACHMENT, true);
        loadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, true);
        loadOptions.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, false);
        loadOptions.put(XMLResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl(true));
        loadOptions.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, Maps.newHashMap());

        // extendedMetaData and resourceHandler

        if (migrationIsNeeded) {
            AirDResourceImpl.addMigrationOptions(loadedVersion, loadOptions, saveOptions);
        }

        loadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
        saveOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

        resource.getDefaultSaveOptions().putAll(saveOptions);
        resource.getDefaultLoadOptions().putAll(loadOptions);
    }

}
