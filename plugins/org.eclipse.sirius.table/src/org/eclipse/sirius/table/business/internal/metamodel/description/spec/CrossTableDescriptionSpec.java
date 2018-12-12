/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.table.business.internal.metamodel.description.spec;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.sirius.table.metamodel.table.description.CreateLineTool;
import org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage;
import org.eclipse.sirius.table.metamodel.table.description.LineMapping;
import org.eclipse.sirius.table.metamodel.table.description.impl.CrossTableDescriptionImpl;
import org.eclipse.sirius.viewpoint.description.tool.RepresentationCreationDescription;
import org.eclipse.sirius.viewpoint.description.tool.RepresentationNavigationDescription;

import com.google.common.collect.Lists;

/**
 * Specialization of the default implementation for
 * {@link CrossTableDescriptionImpl}.
 * 
 * @author pierre-charles.david@obeo.fr
 */
public class CrossTableDescriptionSpec extends CrossTableDescriptionImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    public EList<CreateLineTool> getAllCreateLine() {
        List<CreateLineTool> result = Lists.newArrayList();
        result.addAll(this.getOwnedCreateLine());
        result.addAll(this.getReusedCreateLine());
        return unionReference(DescriptionPackage.eINSTANCE.getTableDescription_AllCreateLine(), result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EList<RepresentationCreationDescription> getAllRepresentationCreationDescriptions() {
        List<RepresentationCreationDescription> result = Lists.newArrayList(); 
        result.addAll(this.getOwnedRepresentationCreationDescriptions());
        result.addAll(this.getReusedRepresentationCreationDescriptions());
        return unionReference(DescriptionPackage.eINSTANCE.getTableDescription_AllRepresentationCreationDescriptions(), result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EList<RepresentationNavigationDescription> getAllRepresentationNavigationDescriptions() {
        List<RepresentationNavigationDescription> result = Lists.newArrayList();
        result.addAll(this.getOwnedRepresentationNavigationDescriptions());
        result.addAll(this.getReusedRepresentationNavigationDescriptions());
        return unionReference(DescriptionPackage.eINSTANCE.getTableDescription_AllRepresentationNavigationDescriptions(), result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EList<LineMapping> getAllLineMappings() {
        List<LineMapping> result = Lists.newArrayList();
        result.addAll(this.getOwnedLineMappings());
        result.addAll(this.getReusedLineMappings());
        return unionReference(DescriptionPackage.eINSTANCE.getTableDescription_AllLineMappings(), result);
    }
    
    private <T> EList<T> unionReference(EStructuralFeature feature, List<T> values) {
        return new EcoreEList.UnmodifiableEList<T>(this, feature, values.size(), values.toArray());
    }
}
