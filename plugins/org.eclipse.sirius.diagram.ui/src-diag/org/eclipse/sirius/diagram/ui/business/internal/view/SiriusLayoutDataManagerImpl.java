/*******************************************************************************
 * Copyright (c) 2009, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.business.internal.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.LayoutHelper;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.IInternalLayoutRunnable;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.LayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DiagramPlugin;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusLayoutDataManager;
import org.eclipse.sirius.diagram.ui.graphical.figures.SiriusLayoutHelper;
import org.eclipse.sirius.diagram.ui.provider.Messages;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * Manage the LayoutData during node creation or drag'n'drop.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
@SuppressWarnings("restriction")
public final class SiriusLayoutDataManagerImpl implements SiriusLayoutDataManager {

    /**
     * An adapter to mark the View as layout by the SiriusLayoutDataManager.
     */
    private static final Adapter LAYOUT_MARKER_ADAPTER = new Adapter() {

        @Override
        public void setTarget(final Notifier newTarget) {
        }

        @Override
        public void notifyChanged(final Notification notification) {
        }

        @Override
        public boolean isAdapterForType(final Object type) {
            return type instanceof SiriusLayoutDataManager;
        }

        @Override
        public Notifier getTarget() {
            return null;
        }

    };

    /**
     * An adapter to mark the View as layout by the SiriusLayoutDataManager.
     */
    private static final Adapter LAYOUT_MARKER_ADAPTER_ON_OPENING = new Adapter() {

        @Override
        public void setTarget(final Notifier newTarget) {
        }

        @Override
        public void notifyChanged(final Notification notification) {
        };

        @Override
        public boolean isAdapterForType(final Object type) {
            return type instanceof SiriusLayoutDataManager;
        }

        @Override
        public Notifier getTarget() {
            return null;
        }

    };

    /**
     * An adapter to mark the View as layout by the SiriusLayoutDataManager.
     */
    private static final Adapter CENTER_LAYOUT_MARKER_ADAPTER = new Adapter() {

        @Override
        public void setTarget(final Notifier newTarget) {
        }

        @Override
        public void notifyChanged(final Notification notification) {
        }

        @Override
        public boolean isAdapterForType(final Object type) {
            return type instanceof SiriusLayoutDataManager;
        }

        @Override
        public Notifier getTarget() {
            return null;
        }

    };

    /**
     * A list of layout data (the root can be an AbstractDNode or a DDiagram).
     * The layout data are removed from this list when all the layout data in
     * its are consumed.
     */
    List<AbstractLayoutData> rootsLayoutData = new ArrayList<AbstractLayoutData>();

    /**
     * Predicate to filter created views to layout on diagram opening.
     */
    Predicate<View> predicate = new Predicate<View>() {

        @Override
        public boolean apply(View input) {
            return hasToArrange(input);
        }
    };

    private Map<Diagram, Set<View>> createdViewToLayout = new HashMap<Diagram, Set<View>>();

    private IOperationHistoryListener viewPointLayoutDataFlusher;

    private boolean ignoreConsumeState;

    /**
     * List of created view to arrange with a center layout : case where one or
     * several views are created and a position has been registered in the
     * layoutDataManager : the view must be created in the center location of
     * the visible part of its container.
     */
    private Map<Diagram, Set<View>> createdViewWithCenterLayout = new HashMap<Diagram, Set<View>>();

    /** layout helper used to help locate shape created with undefined points */
    private SiriusLayoutHelper layoutHelper;

    /**
     * Avoid instantiation.
     */
    private SiriusLayoutDataManagerImpl() {
        OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(getSiriusLayoutDataFlusher());
    }

    /**
     * Construct a new instance.
     * 
     * @return a new instance
     */
    public static SiriusLayoutDataManager init() {
        return new SiriusLayoutDataManagerImpl();
    }

    /**
     * Add a new AbstractLayoutData of this SiriusLayoutDataManagerImpl.
     * 
     * @param aLayoutData
     *            The rootLayoutData
     */
    @Override
    public void addData(final AbstractLayoutData aLayoutData) {
        // if a rootLayoutData exist with the same target, we delete the
        // previous rootLayoutData
        final Iterator<AbstractLayoutData> iterator = rootsLayoutData.iterator();
        while (iterator.hasNext()) {
            final AbstractLayoutData existingRootLayoutData = iterator.next();
            if (existingRootLayoutData instanceof LayoutData && aLayoutData instanceof LayoutData) {
                LayoutData existingLayoutData = (LayoutData) existingRootLayoutData;
                LayoutData aLayoutDataInParam = (LayoutData) aLayoutData;
                if (existingLayoutData.getTarget().equals(aLayoutDataInParam.getTarget())) {
                    iterator.remove();
                    // DiagramPlugin.getDefault().logWarning("The previous layout data were replaced by new ones. It may leads to unexpected layout behavior.");
                }
            }
        }
        this.rootsLayoutData.add(aLayoutData);
    }

    /**
     * Search recursively in all the LayoutData is there is one which have the
     * node for target.
     * 
     * @param node
     *            The search element
     * @param searchParent
     *            true if the data must be retrieve from the node parent
     * @return the corresponding LayoutData or null if not found.
     */
    @Override
    public LayoutData getData(final AbstractDNode node, final boolean searchParent) {
        LayoutData result = null;
        if (node != null) {
            AbstractDNode searchNode = node;
            if (searchParent) {
                if (node.eContainer() instanceof AbstractDNode) {
                    searchNode = (AbstractDNode) node.eContainer();
                } else if (node.eContainer() instanceof DDiagram) {
                    result = getData((DDiagram) node.eContainer());
                } else {
                    DiagramPlugin.getDefault().logWarning(MessageFormat.format(Messages.SiriusLayoutDataManagerImpl_unhandledContainerKind, node.eContainer().getClass().getName()));
                }
            }
            if (result == null) {
                // Search the node in all rootsLayoutData
                for (final AbstractLayoutData abstractLayoutData : rootsLayoutData) {
                    if (abstractLayoutData instanceof LayoutData) {
                        LayoutData layoutData = (LayoutData) abstractLayoutData;
                        result = layoutData.getData(searchNode, ignoreConsumeState);
                        if (result != null) {
                            break;
                        }
                    }
                }
            }
            if (result == null) {
                // Search on EdgeLayoutData
                result = getLayoutDataFromEdgeLayoutData(searchNode);
            }
        }
        if (result != null) {
            result.setConsume(true);
        }
        return result;

    }

    private LayoutData getLayoutDataFromEdgeLayoutData(AbstractDNode searchNode) {
        LayoutData result = null;
        for (final AbstractLayoutData abstractLayoutData : rootsLayoutData) {
            if (abstractLayoutData instanceof EdgeLayoutData) {
                EdgeLayoutData edgeLayoutData = (EdgeLayoutData) abstractLayoutData;
                LayoutData edgeSourceLayoutData = edgeLayoutData.getEdgeSourceLayoutData();
                if (edgeSourceLayoutData != null) {
                    result = edgeSourceLayoutData.getData(searchNode, ignoreConsumeState);
                }
                if (result == null) {
                    LayoutData edgeTargetLayoutData = edgeLayoutData.getEdgeTargetLayoutData();
                    if (edgeTargetLayoutData != null) {
                        result = edgeTargetLayoutData.getData(searchNode, ignoreConsumeState);
                    }
                } else {
                    break;
                }
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Search recursively in all the LayoutData is there is one which have the
     * edge for target.
     * 
     * @param edge
     *            The search element
     * @param searchParent
     *            true if the data must be retrieve from the node parent
     * @return the corresponding EdgeLayoutData or null if not found.
     */
    @Override
    public EdgeLayoutData getData(final DEdge edge, final boolean searchParent) {
        Option<EdgeLayoutData> noEdgeLayoutData = Options.newNone();
        return getData(edge, searchParent, noEdgeLayoutData);
    }

    @Override
    public EdgeLayoutData getOppositeEdgeLayoutData(EdgeLayoutData egdeLayoutData, boolean searchParent) {
        DEdge edge = egdeLayoutData.getTarget();
        if (edge == null) {
            return null;
        }
        return getData(edge, searchParent, Options.newSome(egdeLayoutData));
    }

    private EdgeLayoutData getData(DEdge edge, boolean searchParent, Option<EdgeLayoutData> optionalOppositeEgdeLayoutData) {
        EdgeLayoutData result = null;
        if (result == null) {
            // Search the edge in all rootsLayoutData
            for (final AbstractLayoutData abstractLayoutData : rootsLayoutData) {
                if (abstractLayoutData instanceof LayoutData) {
                    LayoutData layoutData = (LayoutData) abstractLayoutData;
                    result = layoutData.getData(edge, ignoreConsumeState);
                    if (result != null && (!optionalOppositeEgdeLayoutData.some() || !(optionalOppositeEgdeLayoutData.get().equals(result)))) {
                        break;
                    }
                } else if (abstractLayoutData instanceof EdgeLayoutData && (!optionalOppositeEgdeLayoutData.some() || !(optionalOppositeEgdeLayoutData.get().equals(abstractLayoutData)))) {
                    EdgeLayoutData edgeLayoutData = (EdgeLayoutData) abstractLayoutData;
                    EdgeTarget edgeSource = edge.getSourceNode();
                    EdgeTarget edgeTarget = edge.getTargetNode();
                    LayoutData edgeSourceLayoutData = edgeLayoutData.getEdgeSourceLayoutData();
                    LayoutData edgeTargetLayoutData = edgeLayoutData.getEdgeTargetLayoutData();

                    if (edgeSource != null && edgeSourceLayoutData instanceof RootLayoutData) {
                        RootLayoutData edgeSourceRootLayoutData = (RootLayoutData) edgeSourceLayoutData;
                        if ((ignoreConsumeState || !edgeSourceRootLayoutData.isConsume()) && edgeSource.equals(edgeSourceRootLayoutData.getTarget())) {
                            result = edgeLayoutData;
                            break;
                        }
                    }
                    if (result == null && edgeTarget != null && edgeTargetLayoutData instanceof RootLayoutData) {
                        RootLayoutData edgeTargetRootLayoutData = (RootLayoutData) edgeTargetLayoutData;
                        if ((ignoreConsumeState || !edgeTargetRootLayoutData.isConsume()) && edgeTarget.equals(edgeTargetRootLayoutData.getTarget())) {
                            result = edgeLayoutData;
                            break;
                        }
                    }
                }
            }
        }

        // Virtual consumption as this edge has no port.
        if (result == null) {
            final EdgeTarget sourceNode = edge.getSourceNode();
            if (sourceNode instanceof AbstractDNode) {
                getData((AbstractDNode) sourceNode);
            }
            final EdgeTarget targetNode = edge.getTargetNode();
            if (targetNode instanceof AbstractDNode) {
                getData((AbstractDNode) targetNode);
            }
        }
        if (result != null) {
            result.setConsume(true);
        }
        return result;

    }

    /**
     * Search recursively in all the LayoutData is there is one which have the
     * diagram for target.
     * 
     * @param diagram
     *            The search element
     * @return the corresponding LayoutData or null if not found.
     */
    protected LayoutData getData(final DDiagram diagram) {
        LayoutData result = null;
        for (final AbstractLayoutData layoutData : rootsLayoutData) {
            if (layoutData instanceof RootLayoutData) {
                RootLayoutData rootLayoutData = (RootLayoutData) layoutData;
                result = rootLayoutData.getData(diagram, ignoreConsumeState);
            }
            if (result != null) {
                break;
            }
        }
        if (result != null) {
            result.setConsume(true);
        }
        return result;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getData(org.eclipse.sirius.viewpoint.AbstractDNode)
     */
    @Override
    public LayoutData getData(final AbstractDNode node) {
        return getData(node, false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getAdapterMarker()
     */
    @Override
    public Adapter getAdapterMarker() {
        return LAYOUT_MARKER_ADAPTER;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getCenterAdapterMarker()
     */
    @Override
    public Adapter getCenterAdapterMarker() {
        return CENTER_LAYOUT_MARKER_ADAPTER;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getAddAdapterMakerCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
     *      org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor)
     */
    @Override
    public AbstractTransactionalCommand getAddAdapterMakerCommand(final TransactionalEditingDomain domain, final IAdaptable viewAdapter) {
        return new AbstractTransactionalCommand(domain, Messages.SiriusLayoutDataManagerImpl_addLayoutMarkerCommandLabel, null) {
            @Override
            protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
                final View view = (View) viewAdapter.getAdapter(View.class);
                if (view != null && !view.eAdapters().contains(LAYOUT_MARKER_ADAPTER)) {
                    view.eAdapters().add(LAYOUT_MARKER_ADAPTER);
                }
                return CommandResult.newOKCommandResult();
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getAddAdapterMakerCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
     *      org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor)
     */
    @Override
    public AbstractTransactionalCommand getAddCenterAdapterMakerCommand(final TransactionalEditingDomain domain, final IAdaptable viewAdapter) {
        return new AbstractTransactionalCommand(domain, Messages.SiriusLayoutDataManagerImpl_addCenterLayoutMarkerCommandLabel, null) {
            @Override
            protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
                final View view = (View) viewAdapter.getAdapter(View.class);
                if (view != null && !view.eAdapters().contains(CENTER_LAYOUT_MARKER_ADAPTER)) {
                    view.eAdapters().add(CENTER_LAYOUT_MARKER_ADAPTER);
                }
                return CommandResult.newOKCommandResult();
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getAddAdapterMakerOnOpeningCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
     *      org.eclipse.gmf.runtime.notation.View)
     */
    public AbstractTransactionalCommand getAddAdapterMakerOnOpeningCommand(final TransactionalEditingDomain domain, final IAdaptable viewAdapter) {
        return new AbstractTransactionalCommand(domain, Messages.SiriusLayoutDataManagerImpl_addLayoutMarkerOnOpeningCommandLabel, null) {
            @Override
            protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
                final View view = (View) viewAdapter.getAdapter(View.class);
                if (view != null && !view.eAdapters().contains(LAYOUT_MARKER_ADAPTER_ON_OPENING)) {
                    view.eAdapters().add(LAYOUT_MARKER_ADAPTER_ON_OPENING);
                }
                return CommandResult.newOKCommandResult();
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getAddAdapterMakerOnOpeningCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
     *      org.eclipse.gmf.runtime.notation.View)
     */
    @Override
    public AbstractTransactionalCommand getAddAdapterMakerOnOpeningCommand(final TransactionalEditingDomain domain, final View view) {
        return new AbstractTransactionalCommand(domain, Messages.SiriusLayoutDataManagerImpl_addLayoutMarkerOnOpeningCommandLabel, null) {
            @Override
            protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
                if (view != null && !view.eAdapters().contains(LAYOUT_MARKER_ADAPTER_ON_OPENING)) {
                    view.eAdapters().add(LAYOUT_MARKER_ADAPTER_ON_OPENING);
                }
                return CommandResult.newOKCommandResult();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EdgeLabelLayoutData getLabelData(final DEdge edge) {
        EdgeLabelLayoutData result = null;

        if (edge != null) {
            final EdgeLayoutData data = getData(edge, false);

            if (data != null) {
                result = data.getEdgeLabelLayoutData();
                // We consume edgeLayoutData only for its labelData so remove
                // the consume status
                data.setConsume(false);
            }
        }
        if (result != null) {
            result.setConsume(true);

        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#arrangeCreatedViews(java.util.List,
     *      org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)
     */
    @Override
    public Command getArrangeCreatedViewsCommand(List<IAdaptable> createdViews, List<IAdaptable> createdViewsWithCenterLayout, IGraphicalEditPart host) {
        // Layout only the views that are not
        // already layout (by a drag'n'drop for example)
        // if (createdViewsToLayout.size() == 1) {
        // cc.add(new
        // ICommandProxy(getAddAdapterMakerOnOpeningCommand(host.getEditingDomain(),
        // (View) createdViewsToLayout.get(0))));
        // }
        if (createdViews != null && createdViews.size() == 1 && isAlreadyArrange(createdViews.get(0)) && !hasCenterLayout(createdViews.get(0))) {
            removeAlreadyArrangeMarker(createdViews.get(0));
            return UnexecutableCommand.INSTANCE;
        }
        return getCreatedViewsCommandFromLayoutType(createdViews, createdViewsWithCenterLayout, host);
    }

    /**
     * Get the arrange command
     * 
     * @param createdViews
     * @param createdViewsWithCenterLayout
     * @param host
     * @return the arrange command
     */
    private Command getCreatedViewsCommandFromLayoutType(List<IAdaptable> createdViews, List<IAdaptable> createdViewsWithCenterLayout, IGraphicalEditPart host) {
        CompoundCommand cc = new CompoundCommand();
        // Center Layout case
        if (createdViewsWithCenterLayout != null) {
            Point previousCenterLocation = null;
            // We compute the center location for the first view then we shift
            // the others
            for (IAdaptable iAdaptable : createdViewsWithCenterLayout) {
                previousCenterLocation = calculateCenterLocation(host, cc, iAdaptable, previousCenterLocation);
            }
            return cc;
        }

        // "normal" layout case
        return arrangeSeveralCreatedViews(createdViews, host);
    }

    /**
     * Calculate the center location of the visible part of the container. Do
     * the same work than XYLayoutEditPolicy.getConstraintFor(request).
     * 
     * @param host
     * @param cc
     * @param iAdaptable
     */
    private Point calculateCenterLocation(IGraphicalEditPart host, CompoundCommand cc, IAdaptable iAdaptable, Point previousCenterLocation) {
        Rectangle rect = new Rectangle();
        rect.setSize(LayoutHelper.UNDEFINED.getSize());
        Point centerLocation;
        if (previousCenterLocation == null) {
            Point result = getLayoutHelper().getReferencePosition(host.getContentPane(), ((FigureCanvas) host.getViewer().getControl()).getViewport(), host);
            rect.setLocation(result);
            Point point = getLayoutHelper().validatePosition(host.getContentPane(), rect);
            centerLocation = point.getCopy();
        } else {
            centerLocation = new Point(previousCenterLocation).getTranslated(SiriusLayoutDataManager.PADDING, SiriusLayoutDataManager.PADDING);
        }
        IGraphicalEditPart part = (IGraphicalEditPart) host.getViewer().getEditPartRegistry().get(iAdaptable.getAdapter(View.class));
        cc.add(new ICommandProxy(new SetBoundsCommand(host.getEditingDomain(), DiagramUIMessages.SetLocationCommand_Label_Resize, part, centerLocation)));
        return centerLocation.getCopy();
    }

    /**
     * Return this layout helper.
     * 
     * @return this layout helper
     */
    private SiriusLayoutHelper getLayoutHelper() {
        if (layoutHelper == null) {
            layoutHelper = new SiriusLayoutHelper();
        }
        return layoutHelper;
    }

    /**
     * Arrange views.
     * 
     * @param createdViewsAdapters
     * @param host
     * @return arrange command
     */
    private Command arrangeSeveralCreatedViews(List<IAdaptable> createdViewsAdapters, IGraphicalEditPart host) {
        if (createdViewsAdapters != null) {
            int size = createdViewsAdapters.size();
            CompoundCommand cc = new CompoundCommand();
            if (size > 0) {
                // perform a layout of the container
                final List<IAdaptable> createdViewsToLayoutAdapters = new LinkedList<IAdaptable>();
                for (IAdaptable viewAdapter : createdViewsAdapters) {
                    if (!isAlreadyArrange(viewAdapter)) {
                        createdViewsToLayoutAdapters.add(viewAdapter);
                    } else {
                        removeAlreadyArrangeMarker(viewAdapter);
                    }
                }

                if (createdViewsToLayoutAdapters.size() > 0) {
                    final DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host.getEditingDomain(), createdViewsToLayoutAdapters, host);
                    cc.add(new ICommandProxy(layoutCmd));
                    return cc;
                }
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#arrangeCreatedViewsOnOpening(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)
     */
    @Override
    public Command getArrangeCreatedViewsOnOpeningCommand(final IGraphicalEditPart host) {
        CompoundCommand cc = new CompoundCommand(Messages.SiriusLayoutDataManagerImpl_createdViewsArrangCommandLabel);
        View containerView = (View) host.getModel();
        Command layoutCommand = getlayoutCommand(host, containerView);
        if (layoutCommand instanceof CompoundCommand && !((CompoundCommand) layoutCommand).isEmpty()) {
            cc.add(layoutCommand);
        }

        for (Object child : containerView.getChildren()) {
            Command cmd = getlayoutCommand((IGraphicalEditPart) host.getViewer().getEditPartRegistry().get(containerView), (View) child);
            if (cmd instanceof CompoundCommand && !((CompoundCommand) cmd).isEmpty()) {
                cc.add(cmd);
            }
        }
        if (cc.isEmpty()) {
            return UnexecutableCommand.INSTANCE;
        }
        return cc;
    }

    /**
     * Create layout command for all created views.
     * 
     * @param host
     *            edit part parent
     * @param cc
     *            compound command
     * @param containerView
     *            parent view
     * @return the layout commands
     */
    private Command getlayoutCommand(final IGraphicalEditPart host, View containerView) {
        CompoundCommand cc = new CompoundCommand(Messages.SiriusLayoutDataManagerImpl_createdViewsArrangCommandLabel);

        // get the created views and remove the layout adapter
        @SuppressWarnings("unchecked")
        Collection<View> createdViews = Collections2.filter(containerView.getChildren(), predicate);
        LinkedList<IAdaptable> createdViewstoLayout = Lists.newLinkedList();
        int size = createdViews.size();
        for (Object view : createdViews) {
            createdViewstoLayout.add(new EObjectAdapter((View) view));
            if (size != 1) {
                removeAlreadyArrangeMarkeronOpening((View) view);
            }
        }

        /* create layout command */
        if (!createdViewstoLayout.isEmpty()) {
            if (host != null) {
                final DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host.getEditingDomain(), createdViewstoLayout, host);
                cc.add(new ICommandProxy(layoutCmd));
            }
        }

        /* create layout command for view child */
        for (Object child : containerView.getChildren()) {
            /* host may be null here, so avoid to explode */
            if (host != null) {
                Command cmd = getlayoutCommand((IGraphicalEditPart) host.getViewer().getEditPartRegistry().get(containerView), (View) child);
                if (cmd instanceof CompoundCommand && !((CompoundCommand) cmd).isEmpty()) {
                    cc.add(cmd);
                }
            }
        }
        return cc;
    }

    /**
     * Return true if this view is already arrange, false otherwise.
     * 
     * @param viewAdapter
     *            The view to check
     * @return true if this view is already arrange, false otherwise (need
     *         arrange)
     */
    private boolean isAlreadyArrange(IAdaptable viewAdapter) {
        boolean alreadyArranged = false;
        final View view = (View) viewAdapter.getAdapter(View.class);
        if (view != null) {
            for (Adapter adapter : view.eAdapters()) {
                if (adapter.isAdapterForType(SiriusLayoutDataManager.INSTANCE)) {
                    alreadyArranged = true;
                    break;
                }
            }
        }
        return alreadyArranged;
    }

    /**
     * Return true if this view has a center layout, false otherwise.
     * 
     * @param viewAdapter
     *            The view to check
     * @return true if this view has a center layout, false otherwise (need
     *         arrange)
     */
    private boolean hasCenterLayout(IAdaptable viewAdapter) {
        boolean centerLayout = false;
        final View view = (View) viewAdapter.getAdapter(View.class);
        if (view != null) {
            for (Adapter adapter : view.eAdapters()) {
                if (adapter.isAdapterForType(SiriusLayoutDataManager.INSTANCE)) {
                    centerLayout = true;
                    break;
                }
            }
        }
        return centerLayout;
    }

    /**
     * Return true if this view is to arrange, false otherwise.
     * 
     * @param view
     *            The view to check
     * @return true if this view is to arrange, false otherwise
     */
    @Override
    public boolean hasToArrange(View view) {
        boolean arranged = false;
        if (view != null) {
            for (Adapter adapter : view.eAdapters()) {
                if (adapter.isAdapterForType(SiriusLayoutDataManager.INSTANCE)) {
                    arranged = true;
                    break;
                }
            }
        }
        return arranged;
    }

    /**
     * Remove the adapter marker that reveal an already arranged view.
     * 
     * @param viewAdapter
     *            The view to check
     */
    private void removeAlreadyArrangeMarker(IAdaptable viewAdapter) {
        final View view = (View) viewAdapter.getAdapter(View.class);
        if (view != null) {
            for (Iterator<Adapter> iterator = view.eAdapters().iterator(); iterator.hasNext(); /**/) {
                Adapter adapter = iterator.next();
                if (adapter.isAdapterForType(SiriusLayoutDataManager.INSTANCE)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * Remove the adapter marker that reveal an already arranged view.
     * 
     * @param view
     *            The view to check
     */
    private void removeAlreadyArrangeMarkeronOpening(View view) {
        if (view != null) {
            for (Iterator<Adapter> iterator = view.eAdapters().iterator(); iterator.hasNext(); /**/) {
                Adapter adapter = iterator.next();
                if (adapter.isAdapterForType(SiriusLayoutDataManager.INSTANCE)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getArrangeCommand(org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest,
     *      org.eclipse.gef.EditPart)
     */
    @Override
    @SuppressWarnings({ "unchecked" })
    public Command getArrangeCommand(ArrangeRequest request, EditPart host) {
        Command arrangeCommand = null;

        // layout new created views at diagram
        // opening : launch arrange selection even if there is only one new
        // created view
        // Code from
        // org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy.getArrangeComamand(ArrangeRequest)

        String layoutDesc = request.getLayoutType() != null ? request.getLayoutType() : LayoutType.DEFAULT;
        boolean offsetFromBoundingBox = false;
        List<EditPart> editparts = new ArrayList<EditPart>();

        if (ActionIds.ACTION_ARRANGE_SELECTION.equals(request.getType())) {
            editparts = request.getPartsToArrange();
            offsetFromBoundingBox = true;
        }

        if (!editparts.isEmpty()) {
            List<LayoutNode> nodes = new ArrayList<LayoutNode>(editparts.size());
            ListIterator<EditPart> li = editparts.listIterator();
            while (li.hasNext()) {
                IGraphicalEditPart ep = (IGraphicalEditPart) li.next();
                View view = ep.getNotationView();
                if (view instanceof Node) {
                    Rectangle bounds = ep.getFigure().getBounds();
                    nodes.add(new LayoutNode((Node) view, bounds.width, bounds.height));
                }
                // remove adapters
                removeAlreadyArrangeMarkeronOpening(view);
            }

            List<Object> hints = new ArrayList<Object>(2);
            hints.add(layoutDesc);
            hints.add(host);
            IAdaptable layoutHint = new ObjectAdapter(hints);
            final Runnable layoutRun = layoutNodes(nodes, offsetFromBoundingBox, layoutHint);

            if (layoutRun instanceof IInternalLayoutRunnable) {
                arrangeCommand = ((IInternalLayoutRunnable) layoutRun).getCommand();
            } else {
                TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) host).getEditingDomain();
                arrangeCommand = new ICommandProxy(new AbstractTransactionalCommand(editingDomain, "", null) { //$NON-NLS-1$
                            @Override
                            protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
                                layoutRun.run();
                                return CommandResult.newOKCommandResult();
                            }
                        });
            }
        }
        return arrangeCommand;
    }

    /**
     * Getter for the SiriusLayoutDataFlusher which listens IOperationHistory
     * events to flush the list RootsLayoutData after a
     * OperationHistoryEvent#DONE event.
     */
    private IOperationHistoryListener getSiriusLayoutDataFlusher() {
        if (viewPointLayoutDataFlusher == null) {
            viewPointLayoutDataFlusher = new SiriusLayoutDataFlusher(this);
        }
        return viewPointLayoutDataFlusher;
    }

    /**
     * package internal method to flush the list of {@link RootLayoutData}, used
     * by the {@link SiriusLayoutDataFlusher}.
     */
    protected void flushRootLayoutDatas() {
        rootsLayoutData.clear();
    }

    /**
     * Overridden to remove the SiriusLayoutDataFlusher from the
     * IOperationHistoryListener list of listeners.
     * 
     * @throws Throwable
     * 
     *             {@inheritDoc}
     */
    @Override
    protected void finalize() throws Throwable {
        OperationHistoryFactory.getOperationHistory().removeOperationHistoryListener(viewPointLayoutDataFlusher);
        super.finalize();
    }

    /**
     * Layout nodes.
     * 
     * @param offsetFromBoundingBox
     *            offset
     * @param nodes
     *            list of nodes
     * @param layoutHint
     *            layout hint
     * @return runnable
     */
    public Runnable layoutNodes(List<LayoutNode> nodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
        final Runnable layoutRun = LayoutService.getInstance().layoutLayoutNodes(nodes, offsetFromBoundingBox, layoutHint);
        return layoutRun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCreatedViewsToLayout(Diagram gmfDiagram, LinkedHashSet<View> createdViewsToLayout) {
        createdViewToLayout.put(gmfDiagram, createdViewsToLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Diagram, Set<View>> getCreatedViewsToLayout() {
        return createdViewToLayout;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#setIgnoreConsumeState(boolean)
     */
    @Override
    public void setIgnoreConsumeState(boolean ignoreConsumeState) {
        this.ignoreConsumeState = ignoreConsumeState;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getData()
     */
    @Override
    public Option<AbstractLayoutData> getData() {
        if (!rootsLayoutData.isEmpty()) {
            return Options.newSome(rootsLayoutData.iterator().next());
        }
        return Options.newNone();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#getCreatedViewWithSpecialLayout()
     */
    @Override
    public Map<Diagram, Set<View>> getCreatedViewWithCenterLayout() {
        return createdViewWithCenterLayout;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#addCreatedViewWithCenterLayout(org.eclipse.gmf.runtime.notation.View)
     */
    @Override
    public void addCreatedViewWithCenterLayout(Diagram gmfDiagram, LinkedHashSet<View> createdViewsToLayout) {
        this.createdViewWithCenterLayout.put(gmfDiagram, createdViewsToLayout);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.business.api.view.SiriusLayoutDataManager#isSpecialLayout(org.eclipse.gmf.runtime.notation.View)
     */
    public boolean isCenterLayout(Diagram gmfDiagram, View view) {
        Set<View> set = createdViewWithCenterLayout.get(gmfDiagram);
        return set.contains(view);
    }
}
