/*******************************************************************************
 * Copyright (c) 2007, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.internal.providers.decorators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.ui.part.SiriusDiagramEditor;

/**
 * 
 * This decorator is installed on SiriusElements edit parts. It display an icon
 * on the bottom right corner when the element provides detail diagrams.
 * 
 * @author cbrun
 */
public class SubDiagramDecoratorProvider extends AbstractProvider implements IDecoratorProvider {

    private static final String KEY = "subDiagramStatus"; //$NON-NLS-1$

    @Override
    public boolean provides(IOperation operation) {
        if (!(operation instanceof CreateDecoratorsOperation)) {
            return false;
        }

        boolean provide = false;
        IDecoratorTarget decoratorTarget = ((CreateDecoratorsOperation) operation).getDecoratorTarget();
        View view = (View) decoratorTarget.getAdapter(View.class);
        if (view != null) {
            EObject model = view.getElement();
            provide = model instanceof DNode || model instanceof DDiagramElementContainer;
        }
        return provide;
    }

    @Override
    public void createDecorators(IDecoratorTarget decoratorTarget) {
        EditPart editPart = (EditPart) decoratorTarget.getAdapter(EditPart.class);
        if (editPart instanceof GraphicalEditPart || editPart instanceof AbstractConnectionEditPart) {
            Object model = editPart.getModel();
            if (model instanceof View) {
                View view = (View) model;
                if (!(view instanceof Edge) && !view.isSetElement()) {
                    return;
                }
            }
            EditDomain ed = editPart.getViewer().getEditDomain();
            if (!(ed instanceof DiagramEditDomain)) {
                return;
            }
            if (((DiagramEditDomain) ed).getEditorPart() == null) {
                decoratorTarget.installDecorator(KEY, new SubDiagramDecorator(decoratorTarget));
            } else if (((DiagramEditDomain) ed).getEditorPart() instanceof SiriusDiagramEditor) {
                decoratorTarget.installDecorator(KEY, new SubDiagramDecorator(decoratorTarget));
            }
        }

    }

}
