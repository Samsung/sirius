/*******************************************************************************
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.table.editor.properties.sections.description.columnmapping;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.properties.sections.common.AbstractSpinnerPropertySection;
import org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A section for the initialWidth property of a ColumnMapping object.
 */
public class ColumnMappingInitialWidthPropertySection extends AbstractSpinnerPropertySection {
    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#getDefaultLabelText()
     */
    protected String getDefaultLabelText() {
        return "InitialWidth"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#getLabelText()
     */
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + ":"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#getFeature()
     */
    protected EAttribute getFeature() {
        return DescriptionPackage.eINSTANCE.getColumnMapping_InitialWidth();
    }

    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#getFeatureAsInteger()
     */
    protected String getFeatureAsText() {
        String value = new String();
        if (eObject.eGet(getFeature()) != null)
            value = toInteger(eObject.eGet(getFeature()).toString()).toString();
        return value;
    }

    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#isEqual(int)
     */
    protected boolean isEqual(String newText) {
        boolean equal = true;
        if (toInteger(newText) != null)
            equal = getFeatureAsText().equals(toInteger(newText).toString());
        else
            refresh();
        return equal;
    }

    /**
     * @see org.eclipse.sirius.table.editor.properties.sections.AbstractSpinnerPropertySection#getFeatureValue(int)
     */
    protected Object getFeatureValue(String newText) {
        return toInteger(newText);
    }

    /**
     * Converts the given text to the integer it represents if applicable.
     * 
     * @return The integer the given text represents if applicable,
     *         <code>null</code> otherwise.
     */
    private Integer toInteger(String text) {
        Integer integerValue = null;
        try {
            integerValue = new Integer(text);
        } catch (NumberFormatException e) {
            // Not a Integer
        }
        return integerValue;
    }

    /**
     * {@inheritDoc}
     */
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        nameLabel.setToolTipText("The initial width of the column (calculated if not available).");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(nameLabel, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("The initial width of the column (calculated if not available).");
        // Start of user code create controls

        // End of user code create controls
    }
}
