/*******************************************************************************
 * Copyright (c) 2007, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.tool.spec;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.diagram.description.tool.impl.DiagramNavigationDescriptionImpl;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.RepresentationElementMapping;

/**
 * Implementation of
 * {@link org.eclipse.sirius.viewpoint.description.DiagramDescription}.
 * 
 * @author cbrun
 */
public class DiagramNavigationDescriptionSpec extends DiagramNavigationDescriptionImpl {

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.tool.impl.RepresentationCreationDescriptionImpl#basicGetRepresentationDescription()
     */
    @Override
    public RepresentationDescription basicGetRepresentationDescription() {
        return getDiagramDescription();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.tool.impl.RepresentationNavigationDescriptionImpl#getRepresentationDescription()
     */
    @Override
    public RepresentationDescription getRepresentationDescription() {
        return getDiagramDescription();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.tool.impl.RepresentationNavigationDescriptionImpl#getMappings()
     */
    @Override
    public EList<RepresentationElementMapping> getMappings() {
        Resource r = this.eResource();
        if (r == null) {
            throw new UnsupportedOperationException();
        }
        ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(r);
        if (crossReferencer == null) {
            throw new UnsupportedOperationException();
        }
        final List<RepresentationElementMapping> edgeMappings = new LinkedList<RepresentationElementMapping>();
        final Collection<Setting> settings = crossReferencer.getInverseReferences(this, true);
        for (final Setting setting : settings) {
            final EObject eReferencer = setting.getEObject();
            final EStructuralFeature eFeature = setting.getEStructuralFeature();
            if (eReferencer instanceof RepresentationElementMapping && eFeature.equals(DescriptionPackage.eINSTANCE.getRepresentationElementMapping_NavigationDescriptions())) {
                edgeMappings.add((RepresentationElementMapping) eReferencer);
            }
        }
        return new BasicEList<RepresentationElementMapping>(edgeMappings);
    }

}
