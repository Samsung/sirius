/*******************************************************************************
 * Copyright (c) 2004, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Copy for specific needs
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.internal.views.common.navigator.filter;

import java.text.BreakIterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.sirius.common.ui.tools.api.navigator.GroupingItem;
import org.eclipse.sirius.ui.tools.api.views.common.item.CommonSessionItem;
import org.eclipse.sirius.ui.tools.api.views.common.item.ProjectDependenciesItem;
import org.eclipse.sirius.ui.tools.api.views.common.item.RepresentationDescriptionItem;
import org.eclipse.sirius.ui.tools.api.views.common.item.ResourcesFolderItem;
import org.eclipse.sirius.ui.tools.api.views.common.item.ViewpointsFolderItem;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.internal.misc.StringMatcher;
import org.eclipse.ui.navigator.CommonViewer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * A filter used in conjunction with <code>FilteredCommonTree</code>. In order
 * to determine if a node should be filtered it uses the content and label
 * provider of the tree to do pattern matching on its children. This causes the
 * entire tree structure to be realized. Note that the label provider must
 * implement ILabelProvider. The matching is made on all elements contained in
 * the opened representations file (or in their semantic resources).
 * 
 * This class is a copy from {@link org.eclipse.ui.dialogs.PatternFilter} with
 * some specific adaptations.
 * 
 * @see FilteredCommonTree
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class PatternFilter extends ViewerFilter {
    /**
     * The pattern filter declared in plugin.xml that must be used by the
     * CommonFilteredTree.
     */
    public static final String ID = "org.eclipse.sirius.ui.commonFilter.pattern"; //$NON-NLS-1$

    private static final Object[] EMPTY = new Object[0];

    /*
     * Cache of filtered elements in the tree
     */
    private Map<Object, Object[]> cache = Maps.newHashMap();

    /*
     * Maps parent elements to TRUE or FALSE
     */
    private Map<Object, Boolean> foundAnyCache = Maps.newHashMap();

    private boolean useCache;

    /**
     * Whether to include a leading wildcard for all provided patterns. A
     * trailing wildcard is always included.
     */
    private boolean includeLeadingWildcard;

    /**
     * The string pattern matcher used for this pattern filter.
     */
    private StringMatcher matcher;

    private boolean useEarlyReturnIfMatcherIsNull = true;

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter#filter(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object[])
     */
    public final Object[] filter(Viewer viewer, Object parent, Object[] elements) {
        Object[] result;
        // we don't want to optimize if we've extended the filter ... this
        // needs to be addressed in 3.4
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=186404
        if (matcher == null && useEarlyReturnIfMatcherIsNull) {
            result = elements;
        } else if (!useCache) {
            result = super.filter(viewer, parent, elements);
        } else {

            Object[] filtered = cache.get(parent);
            if (filtered == null) {
                Boolean foundAny = foundAnyCache.get(parent);
                if (foundAny != null && !foundAny.booleanValue()) {
                    filtered = EMPTY;
                } else {
                    filtered = super.filter(viewer, parent, elements);
                }
                cache.put(parent, filtered);
            }
            result = filtered;
        }
        return result;
    }

    /**
     * Returns true if any of the elements makes it through the filter. This
     * method uses caching if enabled; the computation is done in
     * computeAnyVisible.
     * 
     * @param viewer
     * @param parent
     * @param elements
     *            the elements (must not be an empty array)
     * @return true if any of the elements makes it through the filter.
     */
    private boolean isAnyVisible(Viewer viewer, Object parent, Object[] elements) {
        boolean result;
        if (matcher == null) {
            result = true;
        } else if (!useCache) {
            result = computeAnyVisible(viewer, elements);
        } else {
            Object[] filtered = cache.get(parent);
            if (filtered != null) {
                result = filtered.length > 0;
            } else {
                Boolean foundAny = foundAnyCache.get(parent);
                if (foundAny == null) {
                    foundAny = computeAnyVisible(viewer, elements) ? Boolean.TRUE : Boolean.FALSE;
                    foundAnyCache.put(parent, foundAny);
                }
                result = foundAny.booleanValue();
            }
        }
        return result;
    }

    /**
     * Returns true if any of the elements makes it through the filter.
     * 
     * @param viewer
     *            the viewer
     * @param elements
     *            the elements to test
     * @return <code>true</code> if any of the elements makes it through the
     *         filter
     */
    private boolean computeAnyVisible(Viewer viewer, Object[] elements) {
        boolean elementFound = false;
        for (int i = 0; i < elements.length && !elementFound; i++) {
            Object element = elements[i];
            elementFound = isElementVisible(viewer, element);
        }
        return elementFound;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public final boolean select(Viewer viewer, Object parentElement, Object element) {
        return isElementVisible(viewer, element);
    }

    /**
     * Sets whether a leading wildcard should be attached to each pattern
     * string.
     * 
     * @param includeLeadingWildcard
     *            Whether a leading wildcard should be added.
     */
    public final void setIncludeLeadingWildcard(final boolean includeLeadingWildcard) {
        this.includeLeadingWildcard = includeLeadingWildcard;
    }

    /**
     * The pattern string for which this filter should select elements in the
     * viewer.
     * 
     * @param patternString
     *            the pattern to use in this filter
     */
    public void setPattern(String patternString) {
        // these 2 strings allow the PatternFilter to be extended in
        // 3.3 - https://bugs.eclipse.org/bugs/show_bug.cgi?id=186404
        if ("org.eclipse.ui.keys.optimization.true".equals(patternString)) { //$NON-NLS-1$
            useEarlyReturnIfMatcherIsNull = true;
            return;
        } else if ("org.eclipse.ui.keys.optimization.false".equals(patternString)) { //$NON-NLS-1$
            useEarlyReturnIfMatcherIsNull = false;
            return;
        }
        clearCaches();
        if (patternString == null || patternString.equals("")) { //$NON-NLS-1$
            matcher = null;
        } else {
            String pattern = patternString + "*"; //$NON-NLS-1$
            if (includeLeadingWildcard) {
                pattern = "*" + pattern; //$NON-NLS-1$
            }
            matcher = new StringMatcher(pattern, true, false);
        }
    }

    /**
     * Clears the caches used for optimizing this filter. Needs to be called
     * whenever the tree content changes.
     */
    /* package */void clearCaches() {
        cache.clear();
        foundAnyCache.clear();
    }

    /**
     * Answers whether the given String matches the pattern.
     * 
     * @param string
     *            the String to test
     * 
     * @return whether the string matches the pattern
     */
    private boolean match(String string) {
        if (matcher == null) {
            return true;
        }
        return matcher.match(string);
    }

    /**
     * Answers whether the given element is a valid selection in the filtered
     * tree. For example, if a tree has items that are categorized, the category
     * itself may not be a valid selection since it is used merely to organize
     * the elements.
     * 
     * @param element
     *            the element in the tree to check
     * @return true if this element is eligible for automatic selection
     */
    public boolean isElementSelectable(Object element) {
        return element != null;
    }

    /**
     * Answers whether the given element in the given viewer matches the filter
     * pattern. This is a default implementation that will show a leaf element
     * in the tree based on whether the provided filter text matches the text of
     * the given element's text, or that of it's children (if the element has
     * any).
     * 
     * Subclasses may override this method.
     * 
     * @param viewer
     *            the tree viewer in which the element resides
     * @param element
     *            the element in the tree to check for a match
     * 
     * @return true if the element matches the filter pattern
     */
    public boolean isElementVisible(Viewer viewer, Object element) {
        boolean result;
        if (matcher == null) {
            result = true;
        } else if (isOnlySearchIn(element)) {
            result = isParentMatch(viewer, element);
        } else if (element instanceof RepresentationDescriptionItem) {
            result = isParentMatch(viewer, element) || (isLeafMatch(viewer, element) && !isFilterWithEmptyDescription(viewer, (RepresentationDescriptionItem) element));
        } else if (element instanceof EObject || element instanceof CommonSessionItem || element instanceof ResourceFactoryImpl || element instanceof XMLResource) {
            result = isParentMatch(viewer, element) || isLeafMatch(viewer, element);
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Check if this RepresentationDescriptionItem is filtered by the filter
     * {@link RepresentationDescriptionWithoutRepresentationCommonFilter}.
     * 
     * @param viewer
     *            The current viewer
     * @param element
     *            The RepresentationDescriptionItem to check
     * @return true if this RepresentationDescriptionItem is filtered by the
     *         filter
     *         {@link RepresentationDescriptionWithoutRepresentationCommonFilter}
     *         , false otherwise.
     */
    private boolean isFilterWithEmptyDescription(Viewer viewer, RepresentationDescriptionItem element) {
        boolean result = false;
        if (viewer instanceof CommonViewer) {
            boolean isRepresentationDescriptionWithoutRepresentationFilterActive = ((CommonViewer) viewer).getNavigatorContentService().getFilterService()
                    .isActive(RepresentationDescriptionWithoutRepresentationCommonFilter.ID);
            if (isRepresentationDescriptionWithoutRepresentationFilterActive) {
                Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer).getContentProvider()).getChildren(element);
                if (children == null || children.length == 0) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Check if the element does not need a match but only search match in its
     * children. We go down (and not match the element) for :
     * <UL>
     * <LI>Working sets</LI>
     * <LI>Project</LI>
     * <LI>Folder</LI>
     * <LI>File</LI>
     * <LI>Specific folder Representations per resource</LI>
     * <LI>Specific folder Representations per category</LI>
     * <LI>The group Project Dependencies</LI>
     * </UL>
     * 
     * @param element
     *            the element in the tree to check if we only search in its
     *            children and not check itself for a match.
     * @return true is this element only needed a search in its children, false
     *         otherwise.
     */
    public boolean isOnlySearchIn(Object element) {
        boolean result = element instanceof IWorkingSet || element instanceof IProject || element instanceof IFolder || element instanceof IFile;
        result = result || element instanceof ViewpointsFolderItem || element instanceof ResourcesFolderItem || element instanceof ProjectDependenciesItem;
        result = result || element instanceof GroupingItem;
        return result;
    }

    /**
     * Check if the parent (category) is a match to the filter text. The default
     * behavior returns true if the element has at least one child element that
     * is a match with the filter text.
     * 
     * Subclasses may override this method.
     * 
     * @param viewer
     *            the viewer that contains the element
     * @param element
     *            the tree element to check
     * @return true if the given element has children that matches the filter
     *         text
     */
    protected boolean isParentMatch(Viewer viewer, Object element) {
        Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer).getContentProvider()).getChildren(element);

        if ((children != null) && (children.length > 0)) {
            return isAnyVisible(viewer, element, children);
        }
        return false;
    }

    /**
     * Check if the current (leaf) element is a match with the filter text. The
     * default behavior checks that the label of the element is a match.
     * 
     * Subclasses should override this method.
     * 
     * @param viewer
     *            the viewer that contains the element
     * @param element
     *            the tree element to check
     * @return true if the given element's label matches the filter text
     */
    protected boolean isLeafMatch(Viewer viewer, Object element) {
        String labelText = ((ILabelProvider) ((StructuredViewer) viewer).getLabelProvider()).getText(element);

        if (labelText == null) {
            return false;
        }
        return wordMatches(labelText);
    }

    /**
     * Take the given filter text and break it down into words using a
     * BreakIterator.
     * 
     * @param text
     * @return an array of words
     */
    private String[] getWords(String text) {
        List<String> words = Lists.newArrayList();
        // Break the text up into words, separating based on whitespace and
        // common punctuation.
        // Previously used String.split(..., "\\W"), where "\W" is a regular
        // expression (see the Javadoc for class Pattern).
        // Need to avoid both String.split and regular expressions, in order to
        // compile against JCL Foundation.
        // Also need to do this in an NL-sensitive way.

        BreakIterator iter = BreakIterator.getWordInstance();
        iter.setText(text);
        int i = iter.first();
        while (i != java.text.BreakIterator.DONE && i < text.length()) {
            int j = iter.following(i);
            if (j == java.text.BreakIterator.DONE) {
                j = text.length();
            }
            // match the word
            if (Character.isLetterOrDigit(text.charAt(i))) {
                String word = text.substring(i, j);
                words.add(word);
            }
            i = j;
        }
        return words.toArray(new String[words.size()]);
    }

    /**
     * Return whether or not if any of the words in text satisfy the match
     * criteria.
     * 
     * @param text
     *            the text to match
     * @return boolean <code>true</code> if one of the words in text satisfies
     *         the match criteria.
     */
    protected boolean wordMatches(String text) {
        boolean result;
        if (text == null) {
            result = false;
        } else if (match(text)) {
            // If the whole text matches we are all set
            result = true;
        } else {
            // Otherwise check if any of the words of the text matches
            String[] words = getWords(text);
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (match(word)) {
                    result = true;
                }
            }
            result = false;
        }
        return result;
    }

    /**
     * Can be called by the filtered tree to turn on caching.
     * 
     * @param useCache
     *            The useCache to set.
     */
    void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }
}
