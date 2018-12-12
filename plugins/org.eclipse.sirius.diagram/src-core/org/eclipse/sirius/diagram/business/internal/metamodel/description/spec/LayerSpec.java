/*******************************************************************************
 * Copyright (c) 2008, 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.spec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.ContentHelper;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.impl.LayerImpl;
import org.eclipse.sirius.diagram.description.tool.ToolGroup;
import org.eclipse.sirius.diagram.description.tool.ToolSection;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.ToolEntry;

/**
 * Specialization of Layer.
 * 
 * @author mchauvin
 */
public class LayerSpec extends LayerImpl {

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.impl.LayerImpl#getAllTools()
     */
    @Override
    public EList<AbstractToolDescription> getAllTools() {
        final Set<AbstractToolDescription> result = new LinkedHashSet<AbstractToolDescription>();
        final Iterator<EObject> it = eAllContents();
        while (it.hasNext()) {
            final EObject eObj = it.next();
            result.addAll(getTools(eObj));
        }
        return new EcoreEList.UnmodifiableEList<AbstractToolDescription>(eInternalContainer(), DescriptionPackage.eINSTANCE.getLayer_AllTools(), result.size(), result.toArray());
    }

    private Collection<AbstractToolDescription> getTools(final EObject eObj) {
        final Collection<AbstractToolDescription> tools = new ArrayList<AbstractToolDescription>();
        if (eObj instanceof AbstractToolDescription) {
            tools.add((AbstractToolDescription) eObj);
        } else if (eObj instanceof ToolGroup) {
            tools.addAll(((ToolGroup) eObj).getTools());
        } else if (eObj instanceof ToolSection) {
            for (final ToolEntry toolEntry : ((ToolSection) eObj).getReusedTools()) {
                tools.addAll(getTools(toolEntry));
            }
            for (final ToolEntry toolEntry : ((ToolSection) eObj).getOwnedTools()) {
                tools.addAll(getTools(toolEntry));
            }
        }
        return tools;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.viewpoint.description.impl.LayerImpl#getAllEdgeMappings()
     */
    @Override
    public EList<EdgeMapping> getAllEdgeMappings() {
        return ContentHelper.getAllEdgeMappings(this);
    }
}
