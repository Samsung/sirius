/*******************************************************************************
 * Copyright (c) 2008, 2016 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.edit.api.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.util.Proxy;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.LabelPosition;
import org.eclipse.sirius.diagram.NodeStyle;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusLayoutDataManager;
import org.eclipse.sirius.diagram.ui.business.internal.query.RequestQuery;
import org.eclipse.sirius.diagram.ui.edit.internal.part.AbstractDiagramNodeEditPartOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.CommonEditPartOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.DefaultDirectEditOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.DiagramBorderNodeEditPartOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.DiagramElementEditPartOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.DiagramNodeEditPartOperation;
import org.eclipse.sirius.diagram.ui.edit.internal.part.EditStatusUpdater;
import org.eclipse.sirius.diagram.ui.edit.internal.part.PortLayoutHelper;
import org.eclipse.sirius.diagram.ui.edit.internal.validators.ResizeValidator;
import org.eclipse.sirius.diagram.ui.graphical.edit.policies.SpecificBorderItemSelectionEditPolicy;
import org.eclipse.sirius.diagram.ui.internal.view.factories.ViewLocationHint;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.diagram.ui.tools.api.figure.DBorderedNodeFigure;
import org.eclipse.sirius.diagram.ui.tools.api.figure.FoldingToggleAwareClippingStrategy;
import org.eclipse.sirius.diagram.ui.tools.api.figure.FoldingToggleImageFigure;
import org.eclipse.sirius.diagram.ui.tools.api.layout.LayoutUtils;
import org.eclipse.sirius.diagram.ui.tools.api.permission.EditPartAuthorityListener;
import org.eclipse.sirius.diagram.ui.tools.internal.ruler.SiriusSnapToHelperUtil;
import org.eclipse.sirius.diagram.ui.tools.internal.ui.SiriusDragEditPartsTrackerEx;
import org.eclipse.sirius.ecore.extender.business.api.permission.IPermissionAuthority;
import org.eclipse.sirius.ecore.extender.business.api.permission.PermissionAuthorityRegistry;
import org.eclipse.sirius.ext.gmf.runtime.editpolicies.SiriusSnapFeedbackPolicy;
import org.eclipse.swt.graphics.Image;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Basic implementation of top level edit part for nodes that on the border of
 * another node.
 * 
 * @author ymortier
 */
public abstract class AbstractDiagramBorderNodeEditPart extends BorderedBorderItemEditPart implements IDiagramBorderNodeEditPart {

    /** The authority listener. */
    protected EditPartAuthorityListener authListener = new EditPartAuthorityListener(this);

    /** Listens the diagram element. */
    private NotificationListener adapterDiagramElement;

    private NotificationListener editModeListener = new EditStatusUpdater(this);

    /**
     * Creates a new Node edit part.
     * 
     * @param view
     *            the GMF view.
     */
    public AbstractDiagramBorderNodeEditPart(final View view) {
        super(view);
    }

    @Override
    protected NodeFigure createNodeFigure() {
        DBorderedNodeFigure nodeFigure = new DBorderedNodeFigure(createMainFigure());
        DiagramBorderNodeEditPartOperation.updateAuthorizedSide(nodeFigure, this);
        nodeFigure.getBorderItemContainer().add(new FoldingToggleImageFigure(this));
        nodeFigure.getBorderItemContainer().setClippingStrategy(new FoldingToggleAwareClippingStrategy());
        return nodeFigure;
    }

    @Override
    protected void registerModel() {
        super.registerModel();
        DiagramElementEditPartOperation.registerModel(this);
    }

    @Override
    protected void unregisterModel() {
        super.unregisterModel();
        DiagramElementEditPartOperation.unregisterModel(this);
    }

    @Override
    protected void handleNotificationEvent(final Notification notification) {
        EditPart styleEditPart = getStyleEditPart();
        // Refreshes edit part.
        if (styleEditPart != null) {
            final EObject element = ((IGraphicalEditPart) styleEditPart).resolveSemanticElement();
            if (element != null && element.eResource() != null) {
                styleEditPart.refresh();
            }
        }
        final EObject element = resolveSemanticElement();
        if (element != null && element.eResource() != null && getParent() != null) {
            refresh();
        }
        super.handleNotificationEvent(notification);
        AbstractDiagramNodeEditPartOperation.handleNotificationEvent(this, notification);
        DiagramBorderNodeEditPartOperation.handleNotificationEvent(this, notification);
    }

    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        AbstractDiagramNodeEditPartOperation.createDefaultEditPolicies(this);
        // Add the Sirius feedback policy to have a the same lighter color for
        // guides of snap features for border nodes on BorderNode
        installEditPolicy(EditPolicyRoles.SNAP_FEEDBACK_ROLE, new SiriusSnapFeedbackPolicy());
    }

    @Override
    public NotificationListener getEAdapterDiagramElement() {
        if (this.adapterDiagramElement == null) {
            this.adapterDiagramElement = DiagramElementEditPartOperation.createEApdaterDiagramElement(this);
        }
        return this.adapterDiagramElement;
    }

    @Override
    public NotificationListener getEditModeListener() {
        return this.editModeListener;
    }

    @Override
    public IStyleEditPart getStyleEditPart() {
        return DiagramElementEditPartOperation.getStyleEditPart(this);
    }

    @Override
    public List<EObject> resolveAllSemanticElements() {
        return DiagramElementEditPartOperation.resolveAllSemanticElements(this);
    }

    @Override
    public DDiagramElement resolveDiagramElement() {
        return DiagramElementEditPartOperation.resolveDiagramElement(this);
    }

    @Override
    public EObject resolveTargetSemanticElement() {
        return DiagramElementEditPartOperation.resolveTargetSemanticElement(this);
    }

    @Override
    public EditPartAuthorityListener getEditPartAuthorityListener() {
        return this.authListener;
    }

    @Override
    public void activate() {
        if (!isActive()) {
            final IPermissionAuthority auth = PermissionAuthorityRegistry.getDefault().getPermissionAuthority(getEditingDomain().getResourceSet());
            auth.addAuthorityListener(this.getEditPartAuthorityListener());
            super.activate();
            DiagramElementEditPartOperation.activate(this);
        }
        this.getEditPartAuthorityListener().refreshEditMode();
    }

    @Override
    public void enableEditMode() {
        /*
         * We want to be sure nobody is enabling the edit mode if the element is
         * locked.
         */
        if (!this.getEditPartAuthorityListener().isLocked()) {
            super.enableEditMode();
        }
    }

    @Override
    public void deactivate() {
        DiagramElementEditPartOperation.deactivate(this);
        final IPermissionAuthority auth = PermissionAuthorityRegistry.getDefault().getPermissionAuthority(getEditingDomain().getResourceSet());
        auth.removeAuthorityListener(this.getEditPartAuthorityListener());
        if (isActive()) {
            super.deactivate();
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        List<?> children = getChildren();
        for (int i = 0; i < children.size(); i++) {
            EditPart editPart = (EditPart) children.get(i);
            editPart.refresh();
        }
    }

    @Override
    protected void refreshChildren() {
        super.refreshChildren();
        /*
         * We need to call this method back as it's setting things on the
         * children and as such we want to be sure it's going to be called
         * *after* the children are created.
         */
        refreshVisuals();
    }

    @Override
    protected List<?> getModelChildren() {
        // create a new view to avoid to change the super.getModelChildren list.
        @SuppressWarnings("unchecked")
        final List<?> modelChildren = new ArrayList<Object>(super.getModelChildren());
        DiagramElementEditPartOperation.removeInvisibleElements(modelChildren);
        final EObject diagramElement = this.resolveDiagramElement();
        if (diagramElement instanceof DNode) {
            final DNode node = (DNode) diagramElement;
            if (((NodeStyle) node.getStyle()).getLabelPosition() == LabelPosition.NODE_LITERAL || StringUtil.isEmpty(node.getName())) {
                DiagramNodeEditPartOperation.removeLabel(this, modelChildren);
            }
        }
        return modelChildren;
    }

    @Override
    protected List<?> getModelSourceConnections() {
        // create a new view to avoid to change the
        // super.getModelSourceConnections list.
        List<View> modelChildren = Lists.newArrayList(Iterables.filter(super.getModelSourceConnections(), View.class));
        DiagramElementEditPartOperation.removeInvisibleElements(modelChildren);
        return modelChildren;
    }

    @Override
    protected List<View> getModelTargetConnections() {
        // create a new view to avoid to change the
        // super.getModelTargetConnections list.
        final List<View> modelChildren = Lists.newArrayList(Iterables.filter(super.getModelTargetConnections(), View.class));
        DiagramElementEditPartOperation.removeInvisibleElements(modelChildren);
        return modelChildren;
    }

    @Override
    public IBorderItemLocator createBorderItemLocator(final IFigure figure, final DDiagramElement vpElementBorderItem) {
        return AbstractDiagramNodeEditPartOperation.createBorderItemLocator(this, figure, vpElementBorderItem);
    }

    /**
     * Get the command to create Port.
     * 
     * @param originalCommand
     *            the orginal command, which will be wrapped in the new command
     * @param request
     *            the create view request
     * @return a command to create port which wrap the original command
     */
    protected Command getPortCreationCommand(final Command originalCommand, final CreateViewRequest request) {
        final CompositeCommand compositeCommand = new CompositeCommand(Messages.IAbstractDiagramNodeEditPart_createViewCommandLabel);
        compositeCommand.compose(new CommandProxy(originalCommand));
        LayoutUtils.prepareFigureForDummyAdds(this.getBorderedFigure().getBorderItemContainer());
        for (ViewDescriptor viewDescriptor : Iterables.filter(request.getViewDescriptors(), ViewDescriptor.class)) {
            final IAdaptable adapt = viewDescriptor.getElementAdapter();
            if (adapt instanceof Proxy) {
                final Object createdElement = ((Proxy) adapt).getRealObject();
                if (createdElement instanceof DNode) {
                    final EObject containerSemanticElement = this.resolveSemanticElement();
                    if (((DNode) containerSemanticElement).getActualMapping().getAllBorderedNodeMappings().contains(((DNode) createdElement).getActualMapping())) {
                        //
                        // Create a port...
                        final Rectangle bounds = PortLayoutHelper.getBounds(this, (DNode) createdElement, viewDescriptor, (DDiagramElement) containerSemanticElement);
                        viewDescriptor.setPersisted(true);
                        compositeCommand.compose(new SetBoundsCommand(getEditingDomain(), DiagramUIMessages.SetLocationCommand_Label_Resize, viewDescriptor, bounds));
                        compositeCommand.compose(SiriusLayoutDataManager.INSTANCE.getAddAdapterMakerCommand(getEditingDomain(), viewDescriptor));
                    }
                }
            }
        }
        LayoutUtils.releaseDummys(this.getBorderedFigure().getBorderItemContainer());
        return new ICommandProxy(compositeCommand.reduce());
    }

    /**
     * Return a command that changes the bounds of a border items.
     * 
     * @param request
     *            the request.
     * @return the command that changes the bounds of a border items.
     */
    public Command getResizeBorderItemCommand(final ChangeBoundsRequest request) {
        Command cmd = UnexecutableCommand.INSTANCE;
        boolean valid = true;
        ResizeValidator resizeValidator = new ResizeValidator(request);
        valid = resizeValidator.validate();

        if (valid) {
            for (Object next : request.getEditParts()) {
                if (!(next instanceof IGraphicalEditPart)) {
                    // break the loop and return null
                    break;
                }
                IGraphicalEditPart graphicalEditPart = (IGraphicalEditPart) next;
                Command result = DiagramBorderNodeEditPartOperation.getResizeBorderItemCommand(graphicalEditPart, request);
                /*
                 * Return immediately the first command created successfully.
                 */
                if (result != null) {
                    cmd = result;
                    break;
                }
            }
        }
        return cmd;
    }

    @Override
    public Image getLabelIcon() {
        return DiagramElementEditPartOperation.getLabelIcon(this);
    }

    @Override
    public void refreshFigure() {
        IStyleEditPart styleEditPart = getStyleEditPart();
        if (styleEditPart != null) {
            DiagramNodeEditPartOperation.refreshFigure(styleEditPart);
        }
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
        DiagramBorderNodeEditPartOperation.refreshVisuals(this);
    }

    @Override
    protected void refreshFont() {
        super.refreshFont();
        DiagramBorderNodeEditPartOperation.refreshFont(this);
    }

    @Override
    public EditPolicy getPrimaryDragEditPolicy() {
        final ResizableEditPolicy result = new SpecificBorderItemSelectionEditPolicy();

        DDiagramElement dde = this.resolveDiagramElement();
        if (dde instanceof DNode) {
            DNode node = (DNode) dde;
            DiagramBorderNodeEditPartOperation.updateResizeKind(result, node);
        }

        return result;
    }

    /**
     * Sets the tooltip of this {@link org.eclipse.gef.EditPart} to the
     * specified text.
     * 
     * @param text
     *            the tooltip's text.
     * @since 0.9.0
     */
    @Override
    public void setTooltipText(final String text) {
        AbstractDiagramNodeEditPartOperation.setTooltipText(this, text);
    }

    @Override
    public Command getCommand(final Request request) {
        Command result = UnexecutableCommand.INSTANCE;
        RequestQuery requestQuery = new RequestQuery(request);
        if (!requestQuery.isNoteCreationRequest() && !requestQuery.isTextCreationRequest() && !requestQuery.isNoteDropRequest() && !requestQuery.isTextDropRequest()) {
            Command cmd = super.getCommand(request);
            result = CommonEditPartOperation.handleAutoPinOnInteractiveMove(this, request, cmd);
        }
        return result;
    }

    @Override
    public ZoomManager getZoomManager() {
        return AbstractDiagramNodeEditPartOperation.getZoomManager(this);
    }

    /**
     * See comments in
     * {@link AbstractDiagramListEditPart#getSourceConnectionAnchor(Request)}.
     * 
     * {@inheritDoc}
     * 
     * @not-generated
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
        if (request instanceof DropRequest) {
            final DropRequest dr = (DropRequest) request;
            if (dr.getLocation() != null) {
                ViewLocationHint.getInstance().putData(ViewLocationHint.SOURCE_ANCHOR_LOCATION, dr.getLocation());
            } else {
                final Point realLocation = (Point) ViewLocationHint.getInstance().getData(ViewLocationHint.SOURCE_ANCHOR_LOCATION);
                return getNodeFigure().getSourceConnectionAnchorAt(realLocation);
            }
        }
        return super.getSourceConnectionAnchor(request);
    }

    /**
     * See comments in
     * {@link AbstractDiagramListEditPart#getSourceConnectionAnchor(Request)}.
     * 
     * {@inheritDoc}
     * 
     * @not-generated
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
        if (request instanceof DropRequest) {
            final DropRequest dr = (DropRequest) request;
            if (dr.getLocation() != null) {
                ViewLocationHint.getInstance().putData(ViewLocationHint.TARGET_ANCHOR_LOCATION, dr.getLocation());
            } else {
                final Point realLocation = (Point) ViewLocationHint.getInstance().getData(ViewLocationHint.TARGET_ANCHOR_LOCATION);
                return getNodeFigure().getTargetConnectionAnchorAt(realLocation);
            }
        }
        return super.getTargetConnectionAnchor(request);
    }

    @Override
    public DragTracker getDragTracker(final Request request) {
        DragTracker result;
        if (request instanceof SelectionRequest && ((SelectionRequest) request).getLastButtonPressed() == 3) {
            result = null;
        } else {
            result = new SiriusDragEditPartsTrackerEx(this);
        }
        return result;
    }

    @Override
    protected void performDirectEditRequest(final Request request) {
        if (this.getChildren().isEmpty() || !(this.getChildren().get(0) instanceof IDiagramNameEditPart && this.getNodeLabel() != null)) {
            DefaultDirectEditOperation.performDirectEditRequest(this, request, this.getNodeLabel());
        } else {
            super.performDirectEditRequest(request);
        }
    }

    @Override
    public Object getAdapter(Class key) {
        if (key == SnapToHelper.class) {
            return SiriusSnapToHelperUtil.getSnapHelper(this);
        }
        return super.getAdapter(key);
    }
}
