/*******************************************************************************
 * Copyright (c) 2015 OBEO.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.sirius.business.internal.query;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.sirius.business.api.query.ResourceQuery;

/**
 * This class aggregates queries (read-only!) from a {@link Resource} starting
 * point.
 * 
 * @author <a href="mailto:laurent.fasani@obeo.fr">Laurent Fasani</a>
 *
 */
public class ResourceQueryInternal extends ResourceQuery {

    /**
     * Create a new query handler.
     * 
     * @param resource
     *            the element to query.
     */
    public ResourceQueryInternal(Resource resource) {
        super(resource);
    }

    /**
     * Find the root EObject from the resource It is the first EObject of the
     * resource except for XSD like resource.
     *
     * @return the root EObject
     */
    public EObject findSemanticRoot() {
        EObject root = null;
        EList<EObject> contents = resource.getContents();
        if (contents != null && contents.size() > 0) {
            root = contents.get(0);

            /*
             * Attempt to determine if the Ecore model was generated from XSD by
             * inspecting the annotation on the root. XSD->ECore will use the
             * ExtendedMetaData as the annotation source, set the 'name' to an
             * empty string (since the generated DocumentRoot doesn't have an
             * underlying Element name), and have a 'kind' of 'mixed'.
             */
            EClass eClass = root.eClass();
            ExtendedMetaData extendedMetaData = ExtendedMetaData.INSTANCE;
            if (extendedMetaData.isDocumentRoot(eClass)) {

                /*
                 * Step over the "mixed", "xMLNSPrefixMap", and
                 * "xSISchemaLocation" features of the injected DocumentRoot
                 * found in an XSD generated Ecore model extracted from
                 * https://www
                 * .eclipse.org/modeling/emf/docs/overviews/XMLSchemaToEcoreMapping
                 * .pdf (section 1.5) ... The document root EClass looks like
                 * one corresponding to a mixed complex type (see section 3.4)
                 * including a "mixed" feature, and derived implementations for
                 * the other features in the class. This allows it to maintain
                 * comments and white space that appears in the document, before
                 * the root element. A document root class contains two more
                 * EMap features, both String to String, to record the namespace
                 * to prefix mappings (xMLNSPrefixMap) and xsi:schemaLocation
                 * mappings (xSISchemaLocation) of an XML instance document.
                 */
                EReference xmlnsPrefixMapFeature = extendedMetaData.getXMLNSPrefixMapFeature(eClass);
                EReference xsiSchemaLocationFeature = extendedMetaData.getXSISchemaLocationMapFeature(eClass);
                EAttribute mixedFeatureFeature = extendedMetaData.getMixedFeature(eClass);

                for (int featureID = 0; featureID < eClass.getFeatureCount(); featureID++) {
                    EStructuralFeature feature = eClass.getEStructuralFeature(featureID);
                    if (feature != mixedFeatureFeature && feature != xmlnsPrefixMapFeature && feature != xsiSchemaLocationFeature) {
                        Object obj = root.eGet(feature);
                        if (obj instanceof EObject) {
                            root = (EObject) obj;
                            break;
                        }
                    }
                } // for
            }
        }
        return root;
    }
}
