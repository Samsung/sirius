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
package org.eclipse.sirius.ui.business.internal.template;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.common.tools.api.util.EclipseUtil;
import org.eclipse.sirius.ui.business.api.template.RepresentationTemplateEdit;
import org.eclipse.sirius.ui.business.api.template.RepresentationTemplateEditManager;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.sirius.viewpoint.description.RepresentationTemplate;

import com.google.common.collect.Lists;

/**
 * Implementation of the aggregation service.
 * 
 * @author cbrun
 * 
 */
public class RepresentationTemplateEditManagerImpl implements RepresentationTemplateEditManager {

    private List<RepresentationTemplateEdit> edits;

    /**
     * {@inheritDoc}
     */
    public Collection<? extends Object> provideNewChildDescriptors() {
        Collection result = Lists.newArrayList();
        for (RepresentationTemplateEdit edit : edits) {
            result.add(edit.getNewChildDescriptor());
        }
        return result;
    }

    /**
     * return a newly created instance prepopulated with contributions.
     * 
     * @return a newly created instance prepopulated with contributions.
     */
    public static RepresentationTemplateEditManager init() {
        final RepresentationTemplateEditManagerImpl manager = new RepresentationTemplateEditManagerImpl();
        if (SiriusPlugin.IS_ECLIPSE_RUNNING) {
            final List<RepresentationTemplateEdit> edits = EclipseUtil.getExtensionPlugins(RepresentationTemplateEdit.class, RepresentationTemplateEditManager.ID,
                    RepresentationTemplateEditManager.CLASS_ATTRIBUTE);
            manager.setTemplateEdits(edits);
        }
        return manager;
    }

    /**
     * {@inheritDoc}
     */
    public EObject getSourceElement(EObject produced) {
        for (RepresentationTemplateEdit edit : edits) {
            EObject found = edit.getSourceElement(produced);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void update(RepresentationTemplate template) {
        for (RepresentationTemplateEdit edit : edits) {
            edit.update(template);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isGenerated(EObject vsmObject) {
        for (RepresentationTemplateEdit edit : edits) {
            if (edit.isGenerated(vsmObject)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOverriden(EObject eObj, EStructuralFeature feature) {
        for (RepresentationTemplateEdit edit : edits) {
            if (edit.isOverriden(eObj, feature)) {
                return true;
            }
        }
        return false;
    }

    private void setTemplateEdits(List<RepresentationTemplateEdit> contributedEdits) {
        this.edits = contributedEdits;

    }

}
