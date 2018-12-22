package org.eclipse.sirius.diagram.editor.properties.sections.style.containerstyledescription;
/************************************************************************************
 * 
 * @section  Copyright    Copyright
 *   COPYRIGHT. 2014-2018 SAMSUNG ELECTRONICS CO., LTD. ALL RIGHTS RESERVED
 * 
 *   Permission is hereby granted to licensees of Samsung Electronics
 *   Co., Ltd. products to use or abstract this computer program for the
 *   sole purpose of implementing a product based on Samsung
 *   Electronics Co., Ltd. products. No other rights to reproduce, use,
 *   or disseminate this computer program, whether in part or in whole,
 *   are granted.
 * 
 *   Samsung Electronics Co., Ltd. makes no representation or warranties
 *   with respect to the performance of this computer program, and
 *   specifically disclaims any responsibility for any damages,
 *   special or consequential, connected with the use of this program.
 * 
 *************************************************************************************/
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.diagram.ContainerLabelDirection;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.properties.sections.common.AbstractRadioButtonPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ContainerStyleDescriptionLabelDirectionPropertySection extends AbstractRadioButtonPropertySection {
    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getDefaultLabelText()
     */
    protected String getDefaultLabelText() {
        return "Container Label Direction"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getLabelText()
     */
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + ":"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getFeature()
     */
    protected EAttribute getFeature() {
        return StylePackage.eINSTANCE.getContainerStyleDescription_ContainerLabelDirection();
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getFeatureValue(int)
     */
    protected Object getFeatureValue(int index) {
        return getChoiceOfValues().get(index);
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#isEqual(int)
     */
    protected boolean isEqual(int index) {
        return getChoiceOfValues().get(index).equals(eObject.eGet(getFeature()));
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getEnumerationFeatureValues()
     */
    protected List<?> getChoiceOfValues() {
        // return LabelDirection.VALUES;
        List<ContainerLabelDirection> VALUES = Collections.unmodifiableList(Arrays.asList(new ContainerLabelDirection[] { ContainerLabelDirection.HORIZONTAL, ContainerLabelDirection.VERTICAL, }));
        return VALUES;
    }

    /**
     * {@inheritDoc}
     */
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        nameLabel.setToolTipText("The direction of the container label.");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(nameLabel, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("The direction of the container label.");

    }
}
