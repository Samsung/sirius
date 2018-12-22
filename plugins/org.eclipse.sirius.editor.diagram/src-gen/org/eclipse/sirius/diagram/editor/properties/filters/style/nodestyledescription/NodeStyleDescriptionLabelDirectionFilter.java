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
package org.eclipse.sirius.diagram.editor.properties.filters.style.nodestyledescription;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.properties.filters.common.ViewpointPropertyFilter;

public class NodeStyleDescriptionLabelDirectionFilter extends ViewpointPropertyFilter {

    /**
     * Description: getFeature
     * 
     * Author: yufang.li
     */
    protected EStructuralFeature getFeature() {
        return StylePackage.eINSTANCE.getNodeStyleDescription_LabelDirection();
    }

    /**
     * Description: check if it is right input type
     * 
     * Author: yufang.li
     */
    protected boolean isRightInputType(Object arg0) {
        return arg0 instanceof org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
    }

    /**
     * Description: select
     * 
     * Author: yufang.li
     */
    @Override
    public boolean select(Object arg0) {
        if (isRightInputType(arg0) && isNode(arg0)) {
            EStructuralFeature feature = getFeature();
            if (feature != null && isVisible(feature)) {
                return true;
            }
        }
        return false;
    }
    
    /**Description: check isNode
     * 
     * Author: yufang.li
     */
    public boolean isNode(Object arg0) {
        if (arg0 instanceof NodeStyleDescription) {
            EObject nodeMapping = ((NodeStyleDescription) arg0).eContainer();
            if (nodeMapping instanceof ConditionalNodeStyleDescription) {
                nodeMapping = nodeMapping.eContainer();
            }
            // We display this property section only for nodeStyleDescription
            // contained in a borderNodeMapping (a NodeMapping referenced by the
            // parent borderNodeMapping feature)

            if (nodeMapping instanceof NodeMapping) {
                return true;
            }
        }
        return false;
    }

}
