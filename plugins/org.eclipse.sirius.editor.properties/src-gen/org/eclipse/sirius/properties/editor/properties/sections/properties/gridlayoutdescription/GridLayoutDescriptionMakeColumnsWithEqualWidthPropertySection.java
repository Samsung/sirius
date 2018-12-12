/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.properties.editor.properties.sections.properties.gridlayoutdescription;

// Start of user code imports

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.properties.sections.common.AbstractCheckBoxPropertySection;
// End of user code imports
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A section for the makeColumnsWithEqualWidth property of a
 * GridLayoutDescription object.
 */
public class GridLayoutDescriptionMakeColumnsWithEqualWidthPropertySection extends AbstractCheckBoxPropertySection {
    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#getDefaultLabelText()
     */
    @Override
    protected String getDefaultLabelText() {
        return "MakeColumnsWithEqualWidth"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#getLabelText()
     */
    @Override
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + ":"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#getFeature()
     */
    @Override
    protected EAttribute getFeature() {
        return PropertiesPackage.eINSTANCE.getGridLayoutDescription_MakeColumnsWithEqualWidth();
    }

    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#getFeatureAsInteger()
     */
    @Override
    protected String getDefaultFeatureAsText() {
        String value = new String();
        if (eObject.eGet(getFeature()) != null) {
            value = toBoolean(eObject.eGet(getFeature()).toString()).toString();
        }
        return value;
    }

    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#getFeatureValue(int)
     */
    @Override
    protected Object getFeatureValue(String newText) {
        return toBoolean(newText);
    }

    /**
     * @see org.eclipse.sirius.properties.editor.properties.sections.AbstractCheckBoxPropertySection#isEqual(int)
     */
    @Override
    protected boolean isEqual(String newText) {
        boolean equal = true;
        if (toBoolean(newText) != null) {
            equal = getFeatureAsText().equals(toBoolean(newText).toString());
        } else {
            refresh();
        }
        return equal;
    }

    /**
     * Converts the given text to the boolean it represents if applicable.
     *
     * @return The boolean the given text represents if applicable,
     *         <code>null</code> otherwise.
     */
    private Boolean toBoolean(String text) {
        Boolean booleanValue = null;
        if (text.toLowerCase().matches("true|false")) {
            booleanValue = Boolean.parseBoolean(text);
        }
        return booleanValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
    }
}
