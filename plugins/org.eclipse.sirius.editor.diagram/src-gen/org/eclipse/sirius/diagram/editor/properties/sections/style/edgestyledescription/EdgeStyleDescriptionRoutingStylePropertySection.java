/*******************************************************************************
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.editor.properties.sections.style.edgestyledescription;

// Start of user code imports

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.diagram.EdgeRouting;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractRadioButtonPropertySection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the routingStyle property of a EdgeStyleDescription object.
 */
public class EdgeStyleDescriptionRoutingStylePropertySection extends AbstractRadioButtonPropertySection {
    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getDefaultLabelText()
     */
    protected String getDefaultLabelText() {
        return "RoutingStyle"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getLabelText()
     */
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + "*:"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getFeature()
     */
    protected EAttribute getFeature() {
        return StylePackage.eINSTANCE.getEdgeStyleDescription_RoutingStyle();
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getFeatureValue(int)
     */
    protected Object getFeatureValue(int index) {
        return getChoiceOfValues().get(index);
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#isEqual(int)
     */
    protected boolean isEqual(int index) {
        return getChoiceOfValues().get(index).equals(eObject.eGet(getFeature()));
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractRadioButtonPropertySection#getEnumerationFeatureValues()
     */
    protected List<?> getChoiceOfValues() {
        return EdgeRouting.VALUES;
    }

    /**
     * {@inheritDoc}
     */
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required"));

    }
}
