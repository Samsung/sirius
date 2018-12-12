/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.internal.providers;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.ui.providers.marker.AbstractModelMarkerNavigationProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.sirius.diagram.DiagramPlugin;
import org.eclipse.sirius.diagram.ui.part.SiriusDiagramEditorUtil;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.viewpoint.description.validation.ValidationRule;

/**
 * @was-generated
 */
public class SiriusMarkerNavigationProvider extends AbstractModelMarkerNavigationProvider {

    /**
     * @was-generated
     */
    public static final String MARKER_TYPE = DiagramUIPlugin.ID + ".diagnostic"; //$NON-NLS-1$

    /**
     * @was-generated
     */
    @Override
    protected void doGotoMarker(IMarker marker) {
        String elementId = marker.getAttribute(org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID, null);
        if (elementId == null || !(getEditor() instanceof DiagramEditor)) {
            return;
        }
        DiagramEditor editor = (DiagramEditor) getEditor();
        Map<?, ?> editPartRegistry = editor.getDiagramGraphicalViewer().getEditPartRegistry();
        Diagram diagram = editor.getDiagram();
        if (diagram == null) {
            return;
        }
        EObject targetView = diagram.eResource().getEObject(elementId);
        if (targetView == null) {
            return;
        }
        EditPart targetEditPart = (EditPart) editPartRegistry.get(targetView);
        if (targetEditPart != null) {
            SiriusDiagramEditorUtil.selectElementsInDiagram(editor, Arrays.asList(new EditPart[] { targetEditPart }));
        }
    }

    /**
     * @was-generated
     */
    public static void deleteMarkers(IResource resource) {
        try {
            resource.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
        } catch (CoreException e) {
            DiagramPlugin.getDefault().logError("Failed to delete validation markers", e); //$NON-NLS-1$
        }
    }

    /**
     * @was-generated
     */
    public static IMarker addMarker(IFile file, String elementId, String location, String message, int statusSeverity) {
        IMarker marker = null;
        try {
            marker = file.createMarker(MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.LOCATION, location);
            marker.setAttribute(org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID, elementId);
            int markerSeverity = IMarker.SEVERITY_INFO;
            if (statusSeverity == IStatus.WARNING) {
                markerSeverity = IMarker.SEVERITY_WARNING;
            } else if (statusSeverity == IStatus.ERROR || statusSeverity == IStatus.CANCEL) {
                markerSeverity = IMarker.SEVERITY_ERROR;
            }
            marker.setAttribute(IMarker.SEVERITY, markerSeverity);
        } catch (CoreException e) {
            DiagramPlugin.getDefault().logError(Messages.SiriusMarkerNavigationProvider_validationMarkerCreationError, e);
        }
        return marker;
    }

    /**
     * @param validationRule
     */
    public static IMarker addValidationRuleMarker(ValidationRule validationRule, IFile file, String elementId, String location, String message, int statusSeverity) {
        IMarker marker = null;
        try {
            marker = file.createMarker(MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.LOCATION, location);
            marker.setAttribute("rule", EcoreUtil.getURI(validationRule).toString()); //$NON-NLS-1$
            marker.setAttribute(org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID, elementId);
            int markerSeverity = IMarker.SEVERITY_INFO;
            if (statusSeverity == IStatus.WARNING) {
                markerSeverity = IMarker.SEVERITY_WARNING;
            } else if (statusSeverity == IStatus.ERROR || statusSeverity == IStatus.CANCEL) {
                markerSeverity = IMarker.SEVERITY_ERROR;
            }
            marker.setAttribute(IMarker.SEVERITY, markerSeverity);
        } catch (CoreException e) {
            DiagramPlugin.getDefault().logError(Messages.SiriusMarkerNavigationProvider_validationMarkerCreationError, e);
        }
        return marker;
    }

}
