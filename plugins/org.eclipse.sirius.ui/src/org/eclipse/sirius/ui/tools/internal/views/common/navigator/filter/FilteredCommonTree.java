/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jacek Pospychala - bug 187762
 *     Mohamed Tarief - tarief@eg.ibm.com - IBM - Bug 174481
 *     Obeo - Adapt original FilteredTree for tree viewer of common navigator
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.internal.views.common.navigator.filter;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.sirius.ui.tools.internal.views.modelexplorer.TooltipAwareNavigatorDecoratingLabelProvider;
import org.eclipse.sirius.viewpoint.provider.Messages;
import org.eclipse.sirius.viewpoint.provider.SiriusEditPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.navigator.NavigatorFilterService;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * A simple control that provides a text widget and a common viewer (the Tree
 * Viewer for the Common Navigator). The contents of the text widget are used to
 * drive a PatternFilter that is on the viewer.
 *
 * This control is inspired from the FilteredTree control.
 *
 * @see org.eclipse.ui.dialogs.FilteredTree
 * @see PatternFilter
 */
public class FilteredCommonTree extends Composite {

    /**
     * Image descriptor for enabled clear button.
     */
    private static final String CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEAR_ICON"; //$NON-NLS-1$

    /**
     * Image descriptor for disabled clear button.
     */
    private static final String DISABLED_CLEAR_ICON = "org.eclipse.ui.internal.dialogs.DCLEAR_ICON"; //$NON-NLS-1$

    /**
     * Maximum time spent expanding the tree after the filter text has been
     * updated (this is only used if we were able to at least expand the visible
     * nodes)
     */
    private static final long SOFT_MAX_EXPAND_TIME = 200;

    private static Boolean useNativeSearchField;

    /**
     * The filter text widget to be used by this tree. This value may be
     * <code>null</code> if there is no filter widget, or if the controls have
     * not yet been created.
     */
    protected Text filterText;

    /**
     * The control representing the clear button for the filter text entry. This
     * value may be <code>null</code> if no such button exists, or if the
     * controls have not yet been created.
     * <p>
     * <strong>Note:</strong> As of 3.5, this is not used if the new look is
     * chosen.
     * </p>
     */
    protected ToolBarManager filterToolBar;

    /**
     * The control representing the clear button for the filter text entry. This
     * value may be <code>null</code> if no such button exists, or if the
     * controls have not yet been created.
     * <p>
     * <strong>Note:</strong> This is only used if the new look is chosen.
     * </p>
     *
     * @since 0.9.0
     */
    protected Control clearButtonControl;

    /**
     * The viewer for the filtered tree. This value should never be
     * <code>null</code> after the widget creation methods are complete.
     */
    protected CommonViewer commonTreeViewer;

    /**
     * The Composite on which the filter controls are created. This is used to
     * set the background color of the filter controls to match the surrounding
     * controls.
     */
    protected Composite filterComposite;

    /**
     * The text to initially show in the filter text control.
     */
    protected String initialText = ""; //$NON-NLS-1$

    /**
     * The parent composite of the filtered tree.
     *
     * @since 0.9.0
     */
    protected Composite parent;

    /**
     * Whether or not to show the filter controls (text and clear button). The
     * default is to show these controls. This can be overridden by providing a
     * setting in the product configuration file. The setting to add to not show
     * these controls is:
     *
     * org.eclipse.ui/SHOW_FILTERED_TEXTS=false
     */
    protected boolean showFilterControls;

    /**
     * The tree composite of this control.
     *
     * @since 0.9.0
     */
    protected Composite treeComposite;

    /**
     * An id tied to the extensions that is used to focus specific content to a
     * particular instance of the Common Navigator.
     */
    protected String viewerId;

    /**
     * The pattern filter for the tree. This value must not be <code>null</code>
     * .
     */
    private PatternFilter patternFilter;

    /**
     * The job used to refresh the tree.
     */
    private Job refreshJob;

    /**
     * Tells whether to use the pre 3.5 or the new look.
     *
     * @since 0.9.0
     */
    private boolean useNewLook;

    private String previousFilterText;

    private boolean narrowingDown;

    /**
     * Get image descriptors for the clear button.
     */
    static {
        ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID, "$nl$/icons/full/etool16/clear_co.gif"); //$NON-NLS-1$
        if (descriptor != null) {
            JFaceResources.getImageRegistry().put(FilteredCommonTree.CLEAR_ICON, descriptor);
        }
        descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID, "$nl$/icons/full/dtool16/clear_co.gif"); //$NON-NLS-1$
        if (descriptor != null) {
            JFaceResources.getImageRegistry().put(FilteredCommonTree.DISABLED_CLEAR_ICON, descriptor);
        }
    }

    /**
     * Create a new instance of the receiver.
     *
     * @param viewerId
     *            An id tied to the extensions that is used to focus specific
     *            content to a particular instance of the Common Navigator
     * @param parent
     *            the parent <code>Composite</code>
     * @param treeStyle
     *            the style bits for the <code>Tree</code>
     * @param useNewLook
     *            <code>true</code> if the new 3.5 look should be used
     * @since 0.9.0
     */
    public FilteredCommonTree(String viewerId, Composite parent, int treeStyle, boolean useNewLook) {
        super(parent, SWT.NONE);
        this.parent = parent;
        this.useNewLook = useNewLook;
        this.viewerId = viewerId;
        init(treeStyle);
    }

    /**
     * Create a new instance of the receiver. Subclasses that wish to override
     * the default creation behavior may use this constructor, but must ensure
     * that the <code>init(composite, int, PatternFilter)</code> method is
     * called in the overriding constructor.
     *
     * @param viewerId
     *            An id tied to the extensions that is used to focus specific
     *            content to a particular instance of the Common Navigator
     * @param parent
     *            the parent <code>Composite</code>
     * @param useNewLook
     *            <code>true</code> if the new 3.5 look should be used
     * @see #init(int, PatternFilter)
     *
     * @since 0.9.0
     */
    protected FilteredCommonTree(String viewerId, Composite parent, boolean useNewLook) {
        super(parent, SWT.NONE);
        this.parent = parent;
        this.useNewLook = useNewLook;
        this.viewerId = viewerId;
    }

    private static boolean useNativeSearchField(Composite composite) {
        if (FilteredCommonTree.useNativeSearchField == null) {
            FilteredCommonTree.useNativeSearchField = Boolean.FALSE;
            Text testText = null;
            try {
                testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
                FilteredCommonTree.useNativeSearchField = new Boolean((testText.getStyle() & SWT.ICON_CANCEL) != 0);
            } finally {
                if (testText != null) {
                    testText.dispose();
                }
            }

        }
        return FilteredCommonTree.useNativeSearchField.booleanValue();
    }

    /**
     * Create the filtered tree.
     *
     * @param treeStyle
     *            the style bits for the <code>Tree</code>
     *
     * @since 0.9.0
     */
    protected void init(int treeStyle) {
        showFilterControls = PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_FILTERED_TEXTS);
        createControl(parent, treeStyle);
        createRefreshJob();
        setInitialText(WorkbenchMessages.FilteredTree_FilterMessage);
        setFont(parent.getFont());

    }

    /**
     * Create the filtered tree's controls. Subclasses should override.
     *
     * @param aParent
     *            the parent composite
     * @param treeStyle
     *            the style bits for the <code>Tree</code>
     */
    protected void createControl(Composite aParent, int treeStyle) {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        if (showFilterControls) {
            if (!useNewLook || FilteredCommonTree.useNativeSearchField(aParent)) {
                filterComposite = new Composite(this, SWT.NONE);
            } else {
                filterComposite = new Composite(this, SWT.BORDER);
                filterComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
            }
            GridLayout filterLayout = new GridLayout(2, false);
            filterLayout.marginHeight = 0;
            filterLayout.marginWidth = 0;
            filterComposite.setLayout(filterLayout);
            filterComposite.setFont(aParent.getFont());

            createFilterControls(filterComposite);
            filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        }

        treeComposite = new Composite(this, SWT.NONE);
        GridLayout treeCompositeLayout = new GridLayout();
        treeCompositeLayout.marginHeight = 0;
        treeCompositeLayout.marginWidth = 0;
        treeComposite.setLayout(treeCompositeLayout);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        treeComposite.setLayoutData(data);
        createTreeControl(treeComposite, treeStyle);
        // Enable tooltip
        ColumnViewerToolTipSupport.enableFor(getViewer());
    }

    /**
     * Create the filter controls. By default, a text and corresponding tool bar
     * button that clears the contents of the text is created. Subclasses may
     * override.
     *
     * @param aParent
     *            parent <code>Composite</code> of the filter controls
     * @return the <code>Composite</code> that contains the filter controls
     */
    protected Composite createFilterControls(Composite aParent) {
        createFilterText(aParent);
        if (useNewLook) {
            createClearTextNew(aParent);
        } else {
            createClearTextOld(aParent);
        }
        if (clearButtonControl != null) {
            // initially there is no text to clear
            clearButtonControl.setVisible(false);
        }
        if (filterToolBar != null) {
            filterToolBar.update(false);
            // initially there is no text to clear
            filterToolBar.getControl().setVisible(false);
        }
        return aParent;
    }

    /**
     * Creates and set up the tree and tree viewer. This method calls
     * {@link #doCreateTreeViewer(Composite, int)} to create the tree viewer.
     * Subclasses should override {@link #doCreateTreeViewer(Composite, int)}
     * instead of overriding this method.
     *
     * @param aParent
     *            parent <code>Composite</code>
     * @param style
     *            SWT style bits used to create the tree
     * @return the tree
     */
    protected Control createTreeControl(Composite aParent, int style) {
        commonTreeViewer = doCreateCommonViewer(aParent, style);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        commonTreeViewer.getControl().setLayoutData(data);
        commonTreeViewer.getControl().addDisposeListener(new DisposeListener() {
            /*
             * (non-Javadoc)
             * @see
             * org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse
             * .swt.events.DisposeEvent)
             */
            @Override
            public void widgetDisposed(DisposeEvent e) {
                refreshJob.cancel();
            }
        });
        if (commonTreeViewer instanceof NotifyingCommonViewer) {
            patternFilter.setUseCache(true);
        }
        commonTreeViewer.addFilter(patternFilter);
        return commonTreeViewer.getControl();
    }

    /**
     * Creates the tree viewer. Subclasses may override.
     *
     * @param aParent
     *            the parent composite
     * @param style
     *            SWT style bits used to create the tree viewer
     * @return the tree viewer
     *
     * @since 0.9.0
     */
    protected CommonViewer doCreateCommonViewer(Composite aParent, int style) {
        return new NotifyingCommonViewer(this.viewerId, aParent, style);
    }

    /**
     * Return the first item in the tree that matches the filter pattern.
     *
     * @param items
     * @return the first matching TreeItem
     */
    private TreeItem getFirstMatchingItem(TreeItem[] items) {
        TreeItem result = null;
        for (TreeItem item : items) {
            if (patternFilter.isLeafMatch(commonTreeViewer, item.getData()) && patternFilter.isElementSelectable(item.getData())) {
                result = item;
                break;
            }
            TreeItem treeItem = getFirstMatchingItem(item.getItems());
            if (treeItem != null) {
                result = treeItem;
                break;
            }
        }
        return result;
    }

    /**
     * Create the refresh job for the receiver.
     *
     */
    private void createRefreshJob() {
        refreshJob = doCreateRefreshJob();
        refreshJob.setSystem(true);
    }

    /**
     * Creates a workbench job that will refresh the tree based on the current
     * filter text. Subclasses may override.
     *
     * @return a workbench job that can be scheduled to refresh the tree
     *
     * @since 0.9.0
     */
    // CHECKSTYLE:OFF Original method
    protected WorkbenchJob doCreateRefreshJob() {
        return new WorkbenchJob(Messages.FilteredCommonTree_refreshFilterJob) {
            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
                IStatus result = null;
                if (commonTreeViewer.getControl().isDisposed()) {
                    result = Status.CANCEL_STATUS;
                } else {
                    String text = getFilterString();
                    if (text == null) {
                        result = Status.OK_STATUS;
                    } else {
                        boolean initial = initialText != null && initialText.equals(text);
                        if (initial) {
                            patternFilter.setPattern(null);
                        } else {
                            patternFilter.setPattern(text);
                        }

                        Control redrawFalseControl = treeComposite != null ? treeComposite : commonTreeViewer.getControl();
                        try {
                            // don't want the user to see updates that will be
                            // made to
                            // the tree
                            // we are setting redraw(false) on the composite to
                            // avoid
                            // dancing scrollbar
                            redrawFalseControl.setRedraw(false);
                            if (!narrowingDown) {
                                // collapse all
                                TreeItem[] is = commonTreeViewer.getTree().getItems();
                                for (TreeItem item : is) {
                                    if (item.getExpanded()) {
                                        commonTreeViewer.setExpandedState(item.getData(), false);
                                    }
                                }
                            }
                            commonTreeViewer.refresh(true);

                            if (text.length() > 0 && !initial) {
                                /*
                                 * Expand elements one at a time. After each is
                                 * expanded, check to see if the filter text has
                                 * been modified. If it has, then cancel the
                                 * refresh job so the user doesn't have to
                                 * endure expansion of all the nodes.
                                 */
                                TreeItem[] items = getViewer().getTree().getItems();
                                int treeHeight = getViewer().getTree().getBounds().height;
                                int numVisibleItems = treeHeight / getViewer().getTree().getItemHeight();
                                long stopTime = FilteredCommonTree.SOFT_MAX_EXPAND_TIME + System.currentTimeMillis();
                                boolean cancel = false;
                                if (items.length > 0 && recursiveExpand(items, monitor, stopTime, new int[] { numVisibleItems })) {
                                    cancel = true;
                                }

                                // enabled toolbar - there is text to clear
                                // and the list is currently being filtered
                                updateToolbar(true);

                                if (cancel) {
                                    result = Status.CANCEL_STATUS;
                                }
                            } else {
                                // disabled toolbar - there is no text to clear
                                // and the list is currently not filtered
                                updateToolbar(false);
                            }
                        } finally {
                            // done updating the tree - set redraw back to true
                            TreeItem[] items = getViewer().getTree().getItems();
                            if (items.length > 0 && getViewer().getTree().getSelectionCount() == 0) {
                                commonTreeViewer.getTree().setTopItem(items[0]);
                            }
                            redrawFalseControl.setRedraw(true);
                        }
                        if (result == null) {
                            result = Status.OK_STATUS;
                        }
                    }
                }
                return result;
            }

            /**
             * Returns true if the job should be canceled (because of timeout or
             * actual cancellation).
             *
             * @param items
             * @param monitor
             * @param cancelTime
             * @param numItemsLeft
             * @return true if canceled
             */
            private boolean recursiveExpand(TreeItem[] items, IProgressMonitor monitor, long cancelTime, int[] numItemsLeft) {
                boolean canceled = false;
                for (int i = 0; !canceled && i < items.length; i++) {
                    TreeItem item = items[i];
                    boolean visible = numItemsLeft[0]-- >= 0;
                    if (monitor.isCanceled() || (!visible && System.currentTimeMillis() > cancelTime)) {
                        canceled = true;
                    } else {
                        Object itemData = item.getData();
                        if (itemData != null) {
                            if (!item.getExpanded()) {
                                // do the expansion through the viewer so that
                                // it can refresh children appropriately.
                                commonTreeViewer.setExpandedState(itemData, true);
                            }
                            TreeItem[] children = item.getItems();
                            if (items.length > 0) {
                                canceled = recursiveExpand(children, monitor, cancelTime, numItemsLeft);
                            }
                        }
                    }
                }
                return canceled;
            }

        };
    }

    // CHECKSTYLE:ON

    /**
     * Update the tool bar according to the new visible status.
     *
     * @param visible
     *            The new visible status.
     */
    protected void updateToolbar(boolean visible) {
        if (clearButtonControl != null) {
            clearButtonControl.setVisible(visible);
        }
        if (filterToolBar != null) {
            filterToolBar.getControl().setVisible(visible);
        }
    }

    /**
     * Creates the filter text and adds listeners. This method calls
     * {@link #doCreateFilterText(Composite)} to create the text control.
     * Subclasses should override {@link #doCreateFilterText(Composite)} instead
     * of overriding this method.
     *
     * @param aParent
     *            <code>Composite</code> of the filter text
     */
    // CHECKSTYLE:OFF Original method
    protected void createFilterText(Composite aParent) {
        filterText = doCreateFilterText(aParent);
        filterText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
            @Override
            public void getName(AccessibleEvent e) {
                String filterTextString = filterText.getText();
                if (filterTextString.length() == 0 || filterTextString.equals(initialText)) {
                    e.result = initialText;
                } else {
                    e.result = MessageFormat.format(WorkbenchMessages.FilteredTree_AccessibleListenerFiltered, new String[] { filterTextString, String.valueOf(getFilteredItemsCount()) });
                }
            }

            /**
             * Return the number of filtered items
             *
             * @return int
             */
            private int getFilteredItemsCount() {
                int total = 0;
                TreeItem[] items = getViewer().getTree().getItems();
                for (TreeItem item : items) {
                    total += itemCount(item);
                }
                return total;
            }

            /**
             * Return the count of treeItem and it's children to infinite depth.
             *
             * @param treeItem
             * @return int
             */
            private int itemCount(TreeItem treeItem) {
                int count = 1;
                TreeItem[] children = treeItem.getItems();
                for (TreeItem element : children) {
                    count += itemCount(element);
                }
                return count;
            }
        });

        filterText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!useNewLook) {
                    /*
                     * Running in an asyncExec because the selectAll() does not
                     * appear to work when using mouse to give focus to text.
                     */
                    Display display = filterText.getDisplay();
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            if (!filterText.isDisposed()) {
                                if (getInitialText().equals(filterText.getText().trim())) {
                                    filterText.selectAll();
                                }
                            }
                        }
                    });
                    return;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!useNewLook) {
                    return;
                }
                if (filterText.getText().equals(initialText)) {
                    setFilterText(""); //$NON-NLS-1$
                    textChanged();
                }
            }
        });

        if (useNewLook) {
            filterText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseDown(MouseEvent e) {
                    if (filterText.getText().equals(initialText)) {
                        // XXX: We cannot call clearText() due to
                        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=260664
                        setFilterText(""); //$NON-NLS-1$
                        textChanged();
                    }
                }
            });
        }

        filterText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // on a CR we want to transfer focus to the list
                boolean hasItems = getViewer().getTree().getItemCount() > 0;
                if (hasItems && e.keyCode == SWT.ARROW_DOWN) {
                    commonTreeViewer.getTree().setFocus();
                    return;
                }
            }
        });

        // enter key set focus to tree
        filterText.addTraverseListener(new TraverseListener() {
            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_RETURN) {
                    e.doit = false;
                    if (getViewer().getTree().getItemCount() == 0) {
                        Display.getCurrent().beep();
                    } else {
                        // if the initial filter text hasn't changed, do not try
                        // to match
                        boolean hasFocus = getViewer().getTree().setFocus();
                        boolean textChanged = !getInitialText().equals(filterText.getText().trim());
                        if (hasFocus && textChanged && filterText.getText().trim().length() > 0) {
                            Tree tree = getViewer().getTree();
                            TreeItem item;
                            if (tree.getSelectionCount() > 0) {
                                item = getFirstMatchingItem(tree.getSelection());
                            } else {
                                item = getFirstMatchingItem(tree.getItems());
                            }
                            if (item != null) {
                                tree.setSelection(new TreeItem[] { item });
                                ISelection sel = getViewer().getSelection();
                                getViewer().setSelection(sel, true);
                            }
                        }
                    }
                }
            }
        });

        filterText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                textChanged();
            }
        });

        // if we're using a field with built in cancel we need to listen for
        // default selection changes (which tell us the cancel button has been
        // pressed)
        if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
            filterText.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    if (e.detail == SWT.ICON_CANCEL) {
                        clearText();
                    }
                }
            });
        }

        GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        // Add top and left margin for best visibility under windows
        gridData.verticalIndent = 2;
        gridData.horizontalIndent = 2;
        // if the text widget supported cancel then it will have it's own
        // integrated button. We can take all of the space.
        if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
            gridData.horizontalSpan = 2;
        }
        filterText.setLayoutData(gridData);
    }

    // CHECKSTYLE:ON

    /**
     * Creates the text control for entering the filter text. Subclasses may
     * override.
     *
     * @param aParent
     *            the parent composite
     * @return the text widget
     *
     * @since 0.9.0
     */
    protected Text doCreateFilterText(Composite aParent) {
        if (!useNewLook || FilteredCommonTree.useNativeSearchField(aParent)) {
            return new Text(aParent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
        }
        return new Text(aParent, SWT.SINGLE);
    }

    /**
     * Update the receiver after the text has changed.
     */
    protected void textChanged() {
        narrowingDown = previousFilterText == null || previousFilterText.equals(WorkbenchMessages.FilteredTree_FilterMessage) || getFilterString().startsWith(previousFilterText);
        previousFilterText = getFilterString();
        // cancel currently running job first, to prevent unnecessary redraw
        refreshJob.cancel();
        refreshJob.schedule(getRefreshJobDelay());
    }

    /**
     * Return the time delay that should be used when scheduling the filter
     * refresh job. Subclasses may override.
     *
     * @return a time delay in milliseconds before the job should run
     *
     * @since 0.9.0
     */
    protected long getRefreshJobDelay() {
        return 200;
    }

    /**
     * Set the background for the widgets that support the filter text area.
     *
     * @param background
     *            background <code>Color</code> to set
     */
    @Override
    public void setBackground(Color background) {
        super.setBackground(background);
        if (filterComposite != null && (!useNewLook || FilteredCommonTree.useNativeSearchField(filterComposite))) {
            filterComposite.setBackground(background);
        }
        if (filterToolBar != null && filterToolBar.getControl() != null) {
            filterToolBar.getControl().setBackground(background);
        }
    }

    /**
     * Create the button that clears the text.
     *
     * @param aParent
     *            parent <code>Composite</code> of toolbar button
     */
    private void createClearTextOld(Composite aParent) {
        // only create the button if the text widget doesn't support one
        // natively
        if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
            filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
            filterToolBar.createControl(aParent);

            IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
                /*
                 * (non-Javadoc)
                 * @see org.eclipse.jface.action.Action#run()
                 */
                @Override
                public void run() {
                    clearText();
                }
            };

            clearTextAction.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
            clearTextAction.setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(FilteredCommonTree.CLEAR_ICON));
            clearTextAction.setDisabledImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(FilteredCommonTree.DISABLED_CLEAR_ICON));

            filterToolBar.add(clearTextAction);
        }
    }

    /**
     * Create the button that clears the text.
     *
     * @param aParent
     *            parent <code>Composite</code> of toolbar button
     */
    private void createClearTextNew(Composite aParent) {
        // only create the button if the text widget doesn't support one
        // natively
        if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
            final Image inactiveImage = JFaceResources.getImageRegistry().getDescriptor(FilteredCommonTree.DISABLED_CLEAR_ICON).createImage();
            final Image activeImage = JFaceResources.getImageRegistry().getDescriptor(FilteredCommonTree.CLEAR_ICON).createImage();
            final Image pressedImage = new Image(getDisplay(), activeImage, SWT.IMAGE_GRAY);

            final Label clearButton = new Label(aParent, SWT.NONE);
            clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
            clearButton.setImage(inactiveImage);
            clearButton.setBackground(aParent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
            clearButton.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
            clearButton.addMouseListener(new MouseAdapter() {
                private MouseMoveListener fMoveListener;

                @Override
                public void mouseDown(MouseEvent e) {
                    clearButton.setImage(pressedImage);
                    fMoveListener = new MouseMoveListener() {
                        private boolean fMouseInButton = true;

                        @Override
                        public void mouseMove(MouseEvent e) {
                            boolean mouseInButton = isMouseInButton(e);
                            if (mouseInButton != fMouseInButton) {
                                fMouseInButton = mouseInButton;
                                clearButton.setImage(mouseInButton ? pressedImage : inactiveImage);
                            }
                        }
                    };
                    clearButton.addMouseMoveListener(fMoveListener);
                }

                @Override
                public void mouseUp(MouseEvent e) {
                    if (fMoveListener != null) {
                        clearButton.removeMouseMoveListener(fMoveListener);
                        fMoveListener = null;
                        boolean mouseInButton = isMouseInButton(e);
                        clearButton.setImage(mouseInButton ? activeImage : inactiveImage);
                        if (mouseInButton) {
                            clearText();
                            filterText.setFocus();
                        }
                    }
                }

                private boolean isMouseInButton(MouseEvent e) {
                    Point buttonSize = clearButton.getSize();
                    return 0 <= e.x && e.x < buttonSize.x && 0 <= e.y && e.y < buttonSize.y;
                }
            });
            clearButton.addMouseTrackListener(new MouseTrackListener() {
                @Override
                public void mouseEnter(MouseEvent e) {
                    clearButton.setImage(activeImage);
                }

                @Override
                public void mouseExit(MouseEvent e) {
                    clearButton.setImage(inactiveImage);
                }

                @Override
                public void mouseHover(MouseEvent e) {
                }
            });
            clearButton.addDisposeListener(new DisposeListener() {
                @Override
                public void widgetDisposed(DisposeEvent e) {
                    inactiveImage.dispose();
                    activeImage.dispose();
                    pressedImage.dispose();
                }
            });
            clearButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {
                @Override
                public void getName(AccessibleEvent e) {
                    e.result = WorkbenchMessages.FilteredTree_AccessibleListenerClearButton;
                }
            });
            clearButton.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
                @Override
                public void getRole(AccessibleControlEvent e) {
                    e.detail = ACC.ROLE_PUSHBUTTON;
                }
            });
            this.clearButtonControl = clearButton;
        }
    }

    /**
     * Clears the text in the filter text widget.
     */
    protected void clearText() {
        setFilterText(""); //$NON-NLS-1$
        textChanged();
    }

    /**
     * Set the text in the filter control.
     *
     * @param string
     *            The new filter text
     */
    protected void setFilterText(String string) {
        if (filterText != null) {
            filterText.setText(string);
            selectAll();
        }
    }

    /**
     * Returns the pattern filter used by this tree.
     *
     * @return The pattern filter; never <code>null</code>.
     */
    public final PatternFilter getPatternFilter() {
        return patternFilter;
    }

    /**
     * Get the tree viewer of the receiver.
     *
     * @return the tree viewer
     */
    public CommonViewer getViewer() {
        return commonTreeViewer;
    }

    /**
     * Get the filter text for the receiver, if it was created. Otherwise return
     * <code>null</code>.
     *
     * @return the filter Text, or null if it was not created
     */
    public Text getFilterControl() {
        return filterText;
    }

    /**
     * Convenience method to return the text of the filter control. If the text
     * widget is not created, then null is returned.
     *
     * @return String in the text, or null if the text does not exist
     */
    protected String getFilterString() {
        return filterText != null ? filterText.getText() : null;
    }

    /**
     * Set the text that will be shown until the first focus. A default value is
     * provided, so this method only need be called if overriding the default
     * initial text is desired.
     *
     * @param text
     *            initial text to appear in text field
     */
    public void setInitialText(String text) {
        initialText = text;
        if (useNewLook && filterText != null) {
            filterText.setMessage(text);
            if (filterText.isFocusControl()) {
                setFilterText(initialText);
                textChanged();
            } else {
                getDisplay().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        if (!filterText.isDisposed() && filterText.isFocusControl()) {
                            setFilterText(initialText);
                            textChanged();
                        }
                    }
                });
            }
        } else {
            setFilterText(initialText);
            textChanged();
        }
    }

    /**
     * Select all text in the filter text field.
     *
     */
    protected void selectAll() {
        if (filterText != null) {
            filterText.selectAll();
        }
    }

    /**
     * Get the initial text for the receiver.
     *
     * @return String
     */
    protected String getInitialText() {
        return initialText;
    }

    /**
     * Return a bold font if the given element matches the given pattern.
     * Clients can opt to call this method from a Viewer's label provider to get
     * a bold font for which to highlight the given element in the tree.
     *
     * @param element
     *            element for which a match should be determined
     * @param tree
     *            FilteredTree in which the element resides
     * @param filter
     *            PatternFilter which determines a match
     *
     * @return bold font
     */
    public static Font getBoldFont(Object element, FilteredCommonTree tree, PatternFilter filter) {
        Font result = null;

        String filterText = tree.getFilterString();

        if (filterText != null) {
            // Do nothing if it's empty string
            String initialText = tree.getInitialText();
            if (!filterText.equals("") && !filterText.equals(initialText)) { //$NON-NLS-1$
                if (tree.getPatternFilter() != filter) {
                    boolean initial = initialText != null && initialText.equals(filterText);
                    if (initial) {
                        filter.setPattern(null);
                    } else {
                        filter.setPattern(filterText);
                    }
                }
                if (filter.isElementVisible(tree.getViewer(), element) && filter.isLeafMatch(tree.getViewer(), element)) {
                    result = JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
                }
            }
        }
        return result;
    }

    /**
     * Custom tree viewer subclass that clears the caches in patternFilter on
     * any change to the tree. See bug 187200.
     *
     * @since 0.9.0
     *
     */
    class NotifyingCommonViewer extends CommonViewer {

        /**
         * @param viewerId
         * @param parent
         * @param style
         */
        public NotifyingCommonViewer(String viewerId, Composite parent, int style) {
            super(viewerId, parent, style);
        }

        /**
         * Initialize the pattern filter before calling the super.init() to
         * avoid NPE when accessing patterf filter. {@inheritDoc}
         *
         * @see org.eclipse.ui.navigator.CommonViewer#init()
         */
        @Override
        protected void init() {
            // Search the pattern filter
            ViewerFilter[] visibleFilters = this.getNavigatorContentService().getFilterService().getVisibleFilters(false);
            for (ViewerFilter visibleFilter : visibleFilters) {
                if (visibleFilter instanceof PatternFilter) {
                    patternFilter = (PatternFilter) visibleFilter;
                    // Force activation
                    if (!this.getNavigatorContentService().getFilterService().isActive(PatternFilter.ID)) {
                        if (this.getNavigatorContentService().getFilterService() instanceof NavigatorFilterService) {
                            ((NavigatorFilterService) this.getNavigatorContentService().getFilterService()).addActiveFilterIds(new String[] { PatternFilter.ID });
                        }
                    }
                    break;
                }
            }
            if (patternFilter == null) {
                SiriusEditPlugin
                        .getPlugin()
                        .getLog()
                        .log(new Status(IStatus.ERROR, SiriusEditPlugin.ID, MessageFormat.format(
                                Messages.FilteredCommonTree_missingFilter, PatternFilter.ID)));
            }
            super.init();
            setLabelProvider(new TooltipAwareNavigatorDecoratingLabelProvider(getNavigatorContentService().createCommonLabelProvider()));
        }

        @Override
        public void add(Object parentElementOrTreePath, Object childElement) {
            getPatternFilter().clearCaches();
            super.add(parentElementOrTreePath, childElement);
        }

        @Override
        public void add(Object parentElementOrTreePath, Object[] childElements) {
            getPatternFilter().clearCaches();
            super.add(parentElementOrTreePath, childElements);
        }

        @Override
        protected void inputChanged(Object input, Object oldInput) {
            getPatternFilter().clearCaches();
            super.inputChanged(input, oldInput);
        }

        @Override
        public void insert(Object parentElementOrTreePath, Object element, int position) {
            getPatternFilter().clearCaches();
            super.insert(parentElementOrTreePath, element, position);
        }

        @Override
        public void refresh() {
            getPatternFilter().clearCaches();
            super.refresh();
        }

        @Override
        public void refresh(boolean updateLabels) {
            getPatternFilter().clearCaches();
            super.refresh(updateLabels);
        }

        @Override
        public void refresh(Object element) {
            getPatternFilter().clearCaches();
            super.refresh(element);
        }

        @Override
        public void refresh(Object element, boolean updateLabels) {
            getPatternFilter().clearCaches();
            super.refresh(element, updateLabels);
        }

        @Override
        public void remove(Object elementsOrTreePaths) {
            getPatternFilter().clearCaches();
            super.remove(elementsOrTreePaths);
        }

        @Override
        public void remove(Object aParent, Object[] elements) {
            getPatternFilter().clearCaches();
            super.remove(aParent, elements);
        }

        @Override
        public void remove(Object[] elementsOrTreePaths) {
            getPatternFilter().clearCaches();
            super.remove(elementsOrTreePaths);
        }

        @Override
        public void replace(Object parentElementOrTreePath, int index, Object element) {
            getPatternFilter().clearCaches();
            super.replace(parentElementOrTreePath, index, element);
        }

        @Override
        public void setChildCount(Object elementOrTreePath, int count) {
            getPatternFilter().clearCaches();
            super.setChildCount(elementOrTreePath, count);
        }

        @Override
        public void setContentProvider(IContentProvider provider) {
            getPatternFilter().clearCaches();
            super.setContentProvider(provider);
        }

        @Override
        public void setHasChildren(Object elementOrTreePath, boolean hasChildren) {
            getPatternFilter().clearCaches();
            super.setHasChildren(elementOrTreePath, hasChildren);
        }

    }
}
