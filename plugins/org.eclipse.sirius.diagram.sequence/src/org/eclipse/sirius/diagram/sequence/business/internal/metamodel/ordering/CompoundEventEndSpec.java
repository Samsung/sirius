/*******************************************************************************
 * Copyright (c) 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.business.internal.metamodel.ordering;

import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.sequence.ordering.CompoundEventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.SingleEventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.impl.CompoundEventEndImpl;

import com.google.common.collect.Sets;

/**
 * Implementation of <code>CompoundEventEnd</code>.
 * 
 * @author mporhel
 */
public class CompoundEventEndSpec extends CompoundEventEndImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    public EList<EObject> getSemanticEvents() {
        EList<EObject> result = new BasicEList<EObject>();
        Set<EObject> semantics = Sets.newHashSet();
        for (SingleEventEnd see : getEventEnds()) {
            semantics.add(see.getSemanticEvent());
        }
        result.addAll(semantics);
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @not-generated
     */
    @Override
    public boolean equals(Object obj) {
        boolean areEquals = false;
        if (this == obj) {
            areEquals = true;
        } else if (obj instanceof CompoundEventEnd) {
            CompoundEventEnd that = (CompoundEventEnd) obj;
            areEquals = this.getSemanticEnd().equals(that.getSemanticEnd()) && this.getEventEnds().size() == that.getEventEnds().size()
                    && this.getSemanticEvents().containsAll(that.getSemanticEvents());
        }
        return areEquals;
    }

    /**
     * {@inheritDoc}
     * 
     * @not-generated
     */
    @Override
    public int hashCode() {
        int subHash = 1;
        for (EObject obj : this.getSemanticEvents()) {
            subHash *= obj.hashCode();
        }
        return 31 * this.getSemanticEnd().hashCode() * subHash;
    }

}
