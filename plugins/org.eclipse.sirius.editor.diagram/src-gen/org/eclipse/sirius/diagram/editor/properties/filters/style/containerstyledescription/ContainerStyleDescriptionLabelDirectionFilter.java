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

package org.eclipse.sirius.diagram.editor.properties.filters.style.containerstyledescription;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.properties.filters.common.ViewpointPropertyFilter;

public class ContainerStyleDescriptionLabelDirectionFilter extends ViewpointPropertyFilter {
    /**
     * Description: getFeature
     * 
     * Author: yufang.li
     */
    protected EStructuralFeature getFeature() {
        return StylePackage.eINSTANCE.getContainerStyleDescription_ContainerLabelDirection();
    }

    /**
     * Description: check if it is right input type
     * 
     * Author: yufang.li
     */
    protected boolean isRightInputType(Object arg0) {
        return arg0 instanceof org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
    }

    /**Description: select
     * 
     * Author: yufang.li
     */
    @Override
    public boolean select(Object arg0) {
        if (isRightInputType(arg0) && isContainer(arg0)) {
            EStructuralFeature feature = getFeature();
            if (feature != null && isVisible(feature)) {
                return true;
            }
        }
        return false;
    }
    
    /**Description: check isContainer
     * 
     * Author: yufang.li
     */
    public boolean isContainer(Object arg0) {
        if (arg0 instanceof ContainerStyleDescription) {
            EObject nodeMapping = ((ContainerStyleDescription) arg0).eContainer();
            if (nodeMapping instanceof ConditionalContainerStyleDescription) {
                nodeMapping = nodeMapping.eContainer();
            }
            // We display this property section only for nodeStyleDescription
            // contained in a borderNodeMapping (a NodeMapping referenced by the
            // parent borderNodeMapping feature)
            if (nodeMapping instanceof ContainerMapping) {
                return true;
            }
        }
        return false;
    }
}
