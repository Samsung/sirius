/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Adaptations.
 *******************************************************************************/
package org.eclipse.sirius.common.ui.tools.api.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

/**
 * Workbench-level composite that combines a CheckboxTreeViewer and
 * CheckboxListViewer. All viewer selection-driven interactions are handled
 * within this object
 */
// This is copied from org.eclipse.ui.internal.ide.misc, so we'd rather keep it
// as close as the original than trying to fix the warnings.
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class CheckboxTreeAndListGroup implements ICheckStateListener, ISelectionChangedListener, ITreeViewerListener {
    private Object root;

    private Object currentTreeSelection;

    private List expandedTreeNodes = new ArrayList();

    private Map checkedStateStore = new HashMap(9);

    private List whiteCheckedTreeItems = new ArrayList();

    private ListenerList listeners = new ListenerList();

    private ITreeContentProvider treeContentProvider;

    private IStructuredContentProvider listContentProvider;

    private ILabelProvider treeLabelProvider;

    private ILabelProvider listLabelProvider;

    // widgets
    private CheckboxTreeViewer treeViewer;

    private CheckboxTableViewer listViewer;

    /**
     * Create an instance of this class. Use this constructor if you wish to
     * specify the width and/or height of the combined widget (to only hardcode
     * one of the sizing dimensions, specify the other dimension's value as -1)
     * 
     * @param parent
     *            the parent.
     * @param rootObject
     *            the root.
     * @param treeContentProvider
     *            the tree content provider.
     * @param treeLabelProvider
     *            the label provider.
     * @param listContentProvider
     *            the list content provider.
     * @param listLabelProvider
     *            the list label provider.
     * @param style
     *            the style.
     * @param width
     *            the width.
     * @param height
     *            the height.
     */
    // CHECKSTYLE:OFF
    public CheckboxTreeAndListGroup(final Composite parent, final Object rootObject, final ITreeContentProvider treeContentProvider, final ILabelProvider treeLabelProvider,
            final IStructuredContentProvider listContentProvider, final ILabelProvider listLabelProvider, final int style, final int width, final int height) {

        root = rootObject;
        this.treeContentProvider = treeContentProvider;
        this.listContentProvider = listContentProvider;
        this.treeLabelProvider = treeLabelProvider;
        this.listLabelProvider = listLabelProvider;
        createContents(parent, width, height, style);
    }

    // CHECKSTYLE:ON

    /**
     * Returns the tree viewer.
     * 
     * @return the tree viewer
     * @author www.obeo.fr
     */
    public CheckboxTreeViewer getTreeViewer() {
        return treeViewer;
    }

    /**
     * Returns the list viewer.
     * 
     * @return the list viewer
     * @author www.obeo.fr
     */
    public CheckboxTableViewer getListViewer() {
        return listViewer;
    }

    /**
     * This method must be called just before this window becomes visible.
     */
    public void aboutToOpen() {
        determineWhiteCheckedDescendents(root);
        checkNewTreeElements(treeContentProvider.getElements(root));
        currentTreeSelection = null;

        // select the first element in the list
        final Object[] elements = treeContentProvider.getElements(root);
        final Object primary = elements.length > 0 ? elements[0] : null;
        if (primary != null) {
            treeViewer.setSelection(new StructuredSelection(primary));
        }
        treeViewer.getControl().setFocus();
    }

    /**
     * Add the passed listener to self's collection of clients that listen for
     * changes to element checked states.
     * 
     * @param listener
     *            ICheckStateListener
     */
    public void addCheckStateListener(final ICheckStateListener listener) {
        listeners.add(listener);
    }

    /**
     * Add the receiver and all of it's ancestors to the checkedStateStore if
     * they are not already there.
     */
    private void addToHierarchyToCheckedStore(final Object treeElement) {

        // if this tree element is already gray then its ancestors all are as
        // well
        if (!checkedStateStore.containsKey(treeElement)) {
            checkedStateStore.put(treeElement, new ArrayList());
        }

        final Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            addToHierarchyToCheckedStore(parent);
        }
    }

    /**
     * Return a boolean indicating whether all children of the passed tree
     * element are currently white-checked.
     * 
     * @return boolean
     * @param treeElement
     *            java.lang.Object
     */
    protected boolean areAllChildrenWhiteChecked(final Object treeElement) {
        final Object[] children = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!whiteCheckedTreeItems.contains(children[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return a boolean indicating whether all list elements associated with the
     * passed tree element are currently checked.
     * 
     * @return boolean
     * @param treeElement
     *            java.lang.Object
     */
    protected boolean areAllElementsChecked(final Object treeElement) {
        final List checkedElements = (List) checkedStateStore.get(treeElement);
        if (checkedElements == null) {
            return false;
        }

        return getListItemsSize(treeElement) == checkedElements.size();
    }

    /**
     * Iterate through the passed elements which are being realized for the
     * first time and check each one in the tree viewer as appropriate.
     * 
     * @param elements
     *            the elements
     */
    protected void checkNewTreeElements(final Object[] elements) {
        for (int i = 0; i < elements.length; ++i) {
            final Object currentElement = elements[i];
            if (currentElement != null) {
                final boolean checked = checkedStateStore.containsKey(currentElement);
                treeViewer.setChecked(currentElement, checked);
                treeViewer.setGrayed(currentElement, checked && !whiteCheckedTreeItems.contains(currentElement));
            }
        }
    }

    /**
     * An item was checked in one of self's two views. Determine which view this
     * occurred in and delegate appropriately
     * 
     * @param event
     *            CheckStateChangedEvent
     */
    public void checkStateChanged(final CheckStateChangedEvent event) {

        // Potentially long operation - show a busy cursor
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(), new Runnable() {
            public void run() {
                if (event.getCheckable().equals(treeViewer)) {
                    treeItemChecked(event.getElement(), event.getChecked());
                } else {
                    listItemChecked(event.getElement(), event.getChecked(), true);
                }

                notifyCheckStateChangeListeners(event);
            }
        });
    }

    /**
     * Lay out and initialize self's visual components.
     * 
     * @param parent
     *            org.eclipse.swt.widgets.Composite
     * @param width
     *            int
     * @param height
     *            int
     * @param style
     *            the style.
     */
    protected void createContents(final Composite parent, final int width, final int height, final int style) {
        // group pane
        final Composite composite = new Composite(parent, style);
        final GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = true;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setFont(parent.getFont());

        createTreeViewer(composite, width / 2, height);
        createListViewer(composite, width / 2, height);

        initialize();
    }

    /**
     * Create this group's list viewer.
     * 
     * @param parent
     *            the parent
     * @param width
     *            int
     * @param height
     *            int
     */
    protected void createListViewer(final Composite parent, final int width, final int height) {
        listViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
        final GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = width;
        data.heightHint = height;
        listViewer.getTable().setLayoutData(data);
        listViewer.getTable().setFont(parent.getFont());
        listViewer.setContentProvider(listContentProvider);
        listViewer.setLabelProvider(listLabelProvider);
        listViewer.addCheckStateListener(this);
    }

    /**
     * Create this group's tree viewer.
     * 
     * @param parent
     *            the parent
     * @param width
     *            int
     * @param height
     *            int
     */
    protected void createTreeViewer(final Composite parent, final int width, final int height) {
        final Tree tree = new Tree(parent, SWT.CHECK | SWT.BORDER);
        final GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = width;
        data.heightHint = height;
        tree.setLayoutData(data);
        tree.setFont(parent.getFont());

        treeViewer = new CheckboxTreeViewer(tree);
        treeViewer.setContentProvider(treeContentProvider);
        treeViewer.setLabelProvider(treeLabelProvider);
        treeViewer.addTreeListener(this);
        treeViewer.addCheckStateListener(this);
        treeViewer.addSelectionChangedListener(this);
    }

    /**
     * Returns a boolean indicating whether the passed tree element should be at
     * LEAST gray-checked. Note that this method does not consider whether it
     * should be white-checked, so a specified tree item which should be
     * white-checked will result in a <code>true</code> answer from this method.
     * To determine whether a tree item should be white-checked use method
     * #determineShouldBeWhiteChecked(Object).
     * 
     * @param treeElement
     *            java.lang.Object
     * @return boolean
     * @see #determineShouldBeWhiteChecked(java.lang.Object)
     */
    protected boolean determineShouldBeAtLeastGrayChecked(final Object treeElement) {
        // if any list items associated with treeElement are checked then it
        // retains its gray-checked status regardless of its children
        boolean result = false;
        final List checked = (List) checkedStateStore.get(treeElement);
        if (checked != null && (!checked.isEmpty())) {
            result = true;
        }

        // if any children of treeElement are still gray-checked then
        // treeElement
        // must remain gray-checked as well
        final Object[] children = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (checkedStateStore.containsKey(children[i])) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Returns a boolean indicating whether the passed tree item should be
     * white-checked.
     * 
     * @return boolean
     * @param treeElement
     *            java.lang.Object
     */
    protected boolean determineShouldBeWhiteChecked(final Object treeElement) {
        return areAllChildrenWhiteChecked(treeElement) && areAllElementsChecked(treeElement);
    }

    /**
     * Recursively add appropriate tree elements to the collection of known
     * white-checked tree elements.
     * 
     * @param treeElement
     *            java.lang.Object
     */
    protected void determineWhiteCheckedDescendents(final Object treeElement) {
        // always go through all children first since their white-checked
        // statuses will be needed to determine the white-checked status for
        // this tree element
        final Object[] children = treeContentProvider.getElements(treeElement);
        for (int i = 0; i < children.length; ++i) {
            determineWhiteCheckedDescendents(children[i]);
        }

        // now determine the white-checked status for this tree element
        if (determineShouldBeWhiteChecked(treeElement)) {
            setWhiteChecked(treeElement, true);
        }
    }

    /**
     * Cause the tree viewer to expand all its items.
     */
    public void expandAll() {
        treeViewer.expandAll();
    }

    /**
     * Answer a flat collection of all of the checked elements in the list
     * portion of self.
     * 
     * @return java.util.Vector
     */
    public Iterator getAllCheckedListItems() {
        final List result = new ArrayList();
        final Iterator listCollectionsEnum = checkedStateStore.values().iterator();

        while (listCollectionsEnum.hasNext()) {
            final Iterator currentCollection = ((List) listCollectionsEnum.next()).iterator();
            while (currentCollection.hasNext()) {
                result.add(currentCollection.next());
            }
        }

        return result.iterator();
    }

    /**
     * Answer a collection of all of the checked elements in the tree portion of
     * self.
     * 
     * @return java.util.Vector
     */
    public Set getAllCheckedTreeItems() {
        return checkedStateStore.keySet();
    }

    /**
     * Answer the number of elements that have been checked by the user.
     * 
     * @return int
     */
    public int getCheckedElementCount() {
        return checkedStateStore.size();
    }

    /**
     * Return a count of the number of list items associated with a given tree
     * item.
     * 
     * @return int
     * @param treeElement
     *            java.lang.Object
     */
    protected int getListItemsSize(final Object treeElement) {
        final Object[] elements = listContentProvider.getElements(treeElement);
        return elements.length;
    }

    /**
     * Get the table the list viewer uses.
     * 
     * @return org.eclipse.swt.widgets.Table
     */
    public Table getListTable() {
        return this.listViewer.getTable();
    }

    /**
     * Logically gray-check all ancestors of treeItem by ensuring that they
     * appear in the checked table.
     * 
     * @param treeElement
     *            the tree element.
     */
    protected void grayCheckHierarchy(final Object treeElement) {

        // if this tree element is already gray then its ancestors all are as
        // well
        if (checkedStateStore.containsKey(treeElement)) {
            return; // no need to proceed upwards from here
        }

        checkedStateStore.put(treeElement, new ArrayList());
        if (determineShouldBeWhiteChecked(treeElement)) {
            setWhiteChecked(treeElement, true);
        }
        final Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            grayCheckHierarchy(parent);
        }
    }

    /**
     * Set the initial checked state of the passed list element to true.
     * 
     * @param element
     *            the element.
     */
    public void initialCheckListItem(final Object element) {
        final Object parent = treeContentProvider.getParent(element);
        currentTreeSelection = parent;
        // As this is not done from the UI then set the box for updating from
        // the selection to false
        listItemChecked(element, true, false);
        updateHierarchy(parent);
    }

    /**
     * Set the initial checked state of the passed element to true, as well as
     * to all of its children and associated list elements.
     * 
     * @param element
     *            the element.
     */
    public void initialCheckTreeItem(final Object element) {
        treeItemChecked(element, true);
    }

    /**
     * Initialize this group's viewers after they have been laid out.
     */
    protected void initialize() {
        treeViewer.setInput(root);
    }

    /**
     * Callback that's invoked when the checked status of an item in the list is
     * changed by the user. Do not try and update the hierarchy if we are
     * building the initial list.
     * 
     * @param listElement
     *            the elements.
     * @param state
     *            the state
     * @param updatingFromSelection
     *            updating.
     */
    protected void listItemChecked(final Object listElement, final boolean state, final boolean updatingFromSelection) {
        List checkedListItems = (List) checkedStateStore.get(currentTreeSelection);

        if (state) {
            if (checkedListItems == null) {
                // since the associated tree item has gone from 0 -> 1 checked
                // list items, tree checking may need to be updated
                grayCheckHierarchy(currentTreeSelection);
                checkedListItems = (List) checkedStateStore.get(currentTreeSelection);
            }
            checkedListItems.add(listElement);
        } else {
            checkedListItems.remove(listElement);
            if (checkedListItems.isEmpty()) {
                // since the associated tree item has gone from 1 -> 0 checked
                // list items, tree checking may need to be updated
                ungrayCheckHierarchy(currentTreeSelection);
            }
        }

        if (updatingFromSelection) {
            updateHierarchy(currentTreeSelection);
        }
    }

    /**
     * Notify all checked state listeners that the passed element has had its
     * checked state changed to the passed state.
     * 
     * @param event
     *            the event.
     */
    protected void notifyCheckStateChangeListeners(final CheckStateChangedEvent event) {
        final Object[] array = listeners.getListeners();
        for (Object element : array) {
            final ICheckStateListener l = (ICheckStateListener) element;
            SafeRunner.run(new SafeRunnable() {
                public void run() {
                    l.checkStateChanged(event);
                }
            });
        }
    }

    /**
     * Set the contents of the list viewer based upon the specified selected
     * tree element. This also includes checking the appropriate list items.
     * 
     * @param treeElement
     *            java.lang.Object
     */
    protected void populateListViewer(final Object treeElement) {
        listViewer.setInput(treeElement);
        final List listItemsToCheck = (List) checkedStateStore.get(treeElement);

        if (listItemsToCheck != null) {
            final Iterator listItemsEnum = listItemsToCheck.iterator();
            while (listItemsEnum.hasNext()) {
                listViewer.setChecked(listItemsEnum.next(), true);
            }
        }
    }

    /**
     * Remove the passed listener from self's collection of clients that listen
     * for changes to element checked states.
     * 
     * @param listener
     *            ICheckStateListener
     */
    public void removeCheckStateListener(final ICheckStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Handle the selection of an item in the tree viewer.
     * 
     * @param event
     *            SelectionChangedEvent
     */
    public void selectionChanged(final SelectionChangedEvent event) {
        final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        final Object selectedElement = selection.getFirstElement();
        if (selectedElement == null) {
            currentTreeSelection = null;
            listViewer.setInput(currentTreeSelection);
            return;
        }

        // ie.- if not an item deselection
        populateListViewer(selectedElement);
        currentTreeSelection = selectedElement;
    }

    /**
     * Select or deselect all of the elements in the tree depending on the value
     * of the selection boolean. Be sure to update the displayed files as well.
     * 
     * @param selection
     *            the selection.
     */
    public void setAllSelections(final boolean selection) {

        // Potentially long operation - show a busy cursor
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(), new Runnable() {
            public void run() {
                setTreeChecked(root, selection);
                listViewer.setAllChecked(selection);
            }
        });
    }

    /**
     * Set the list viewer's providers to those passed.
     * 
     * @param contentProvider
     *            ITreeContentProvider
     * @param labelProvider
     *            ILabelProvider
     */
    public void setListProviders(final IStructuredContentProvider contentProvider, final ILabelProvider labelProvider) {
        listViewer.setContentProvider(contentProvider);
        listViewer.setLabelProvider(labelProvider);
    }

    /**
     * Set the sorter that is to be applied to self's list viewer.
     * 
     * @param sorter
     *            the sorter.
     */
    public void setListSorter(final ViewerSorter sorter) {
        listViewer.setSorter(sorter);
    }

    /**
     * Set the root of the widget to be new Root. Regenerate all of the tables
     * and lists from this value.
     * 
     * @param newRoot
     *            the new root.
     */
    public void setRoot(final Object newRoot) {
        this.root = newRoot;
        initialize();
    }

    /**
     * Set the checked state of the passed tree element appropriately, and do so
     * recursively to all of its child tree elements as well.
     * 
     * @param treeElement
     *            the tree element.
     * @param state
     *            the state.
     */
    protected void setTreeChecked(final Object treeElement, final boolean state) {
        if (treeElement != null) {
            if (treeElement.equals(currentTreeSelection)) {
                listViewer.setAllChecked(state);
            }

            if (state) {
                final Object[] listItems = listContentProvider.getElements(treeElement);
                final List listItemsChecked = new ArrayList();
                for (int i = 0; i < listItems.length; ++i) {
                    listItemsChecked.add(listItems[i]);
                }

                checkedStateStore.put(treeElement, listItemsChecked);
            } else {
                checkedStateStore.remove(treeElement);
            }

            setWhiteChecked(treeElement, state);
            treeViewer.setChecked(treeElement, state);
            treeViewer.setGrayed(treeElement, false);

            // now logically check/uncheck all children as well
            final Object[] children = treeContentProvider.getChildren(treeElement);
            for (int i = 0; i < children.length; ++i) {
                setTreeChecked(children[i], state);
            }
        }
    }

    /**
     * Set the tree viewer's providers to those passed.
     * 
     * @param contentProvider
     *            ITreeContentProvider
     * @param labelProvider
     *            ILabelProvider
     */
    public void setTreeProviders(final ITreeContentProvider contentProvider, final ILabelProvider labelProvider) {
        treeViewer.setContentProvider(contentProvider);
        treeViewer.setLabelProvider(labelProvider);
    }

    /**
     * Set the sorter that is to be applied to self's tree viewer.
     * 
     * @param sorter
     *            the sorter.
     */
    public void setTreeSorter(final ViewerSorter sorter) {
        treeViewer.setSorter(sorter);
    }

    /**
     * Adjust the collection of references to white-checked tree elements
     * appropriately.
     * 
     * @param treeElement
     *            java.lang.Object
     * @param isWhiteChecked
     *            boolean
     */
    protected void setWhiteChecked(final Object treeElement, final boolean isWhiteChecked) {
        if (isWhiteChecked) {
            if (!whiteCheckedTreeItems.contains(treeElement)) {
                whiteCheckedTreeItems.add(treeElement);
            }
        } else {
            whiteCheckedTreeItems.remove(treeElement);
        }
    }

    /**
     * Handle the collapsing of an element in a tree viewer.
     * 
     * @param event
     *            the event.
     */
    public void treeCollapsed(final TreeExpansionEvent event) {
        // We don't need to do anything with this
    }

    /**
     * Handle the expansionsion of an element in a tree viewer.
     * 
     * @param event
     *            the event.
     */
    public void treeExpanded(final TreeExpansionEvent event) {

        final Object item = event.getElement();

        // First see if the children need to be given their checked state at
        // all. If they've
        // already been realized then this won't be necessary
        if (!expandedTreeNodes.contains(item)) {
            expandedTreeNodes.add(item);
            checkNewTreeElements(treeContentProvider.getChildren(item));
        }
    }

    /**
     * Callback that's invoked when the checked status of an item in the tree is
     * changed by the user.
     * 
     * @param treeElement
     *            the tree element.
     * @param state
     *            the state.
     */
    protected void treeItemChecked(final Object treeElement, final boolean state) {

        // recursively adjust all child tree elements appropriately
        setTreeChecked(treeElement, state);

        final Object parent = treeContentProvider.getParent(treeElement);
        if (parent == null) {
            return;
        }

        // now update upwards in the tree hierarchy
        if (state) {
            grayCheckHierarchy(parent);
        } else {
            ungrayCheckHierarchy(parent);
        }

        updateHierarchy(treeElement);
    }

    /**
     * Logically un-gray-check all ancestors of treeItem iff appropriate.
     * 
     * @param treeElement
     *            the tree element.
     */
    protected void ungrayCheckHierarchy(final Object treeElement) {
        if (!determineShouldBeAtLeastGrayChecked(treeElement)) {
            checkedStateStore.remove(treeElement);
        }

        final Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            ungrayCheckHierarchy(parent);
        }
    }

    /**
     * Set the checked state of self and all ancestors appropriately.
     * 
     * @param treeElement
     *            the tree element.
     */
    protected void updateHierarchy(final Object treeElement) {

        final boolean whiteChecked = determineShouldBeWhiteChecked(treeElement);
        final boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);

        treeViewer.setChecked(treeElement, shouldBeAtLeastGray);
        setWhiteChecked(treeElement, whiteChecked);
        if (!whiteChecked) {
            treeViewer.setGrayed(treeElement, shouldBeAtLeastGray);
        }

        // proceed up the tree element hierarchy
        final Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            updateHierarchy(parent);
        }
    }

    /**
     * Update the selections of the tree elements in items to reflect the new
     * selections provided.
     * 
     * @param items
     *            Map with keys of Object (the tree element) and values of List
     *            (the selected list elements).
     */
    public void updateSelections(final Map items) {

        // Potentially long operation - show a busy cursor
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(), new Runnable() {
            public void run() {
                Iterator<Entry> entryIterator = items.entrySet().iterator();

                // Update the store before the hierarchy to prevent
                // updating
                // parents before all of the children are done
                while (entryIterator.hasNext()) {
                    final Entry entry = entryIterator.next();
                    // Replace the items in the checked state store with
                    // those
                    // from the supplied items
                    final List selections = (List) entry.getValue();
                    if (selections.size() == 0) {
                        // If it is empty remove it from the list
                        checkedStateStore.remove(entry.getKey());
                    } else {
                        checkedStateStore.put(entry.getKey(), selections);
                        // proceed up the tree element hierarchy
                        final Object parent = treeContentProvider.getParent(entry.getKey());
                        if (parent != null) {
                            addToHierarchyToCheckedStore(parent);
                        }
                    }
                }

                // Now update hierarchies
                entryIterator = items.entrySet().iterator();

                while (entryIterator.hasNext()) {
                    final Entry entry = entryIterator.next();
                    updateHierarchy(entry.getKey());
                    if (currentTreeSelection != null && currentTreeSelection.equals(entry.getKey())) {
                        listViewer.setAllChecked(false);
                        listViewer.setCheckedElements(((List) entry.getValue()).toArray());
                    }
                }
            }
        });

    }
}
