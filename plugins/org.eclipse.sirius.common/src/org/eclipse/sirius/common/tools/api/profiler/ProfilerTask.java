/*******************************************************************************
 * Copyright (c) 2007, 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.common.tools.api.profiler;

/**
 * Represents a task.
 * 
 * @author ymortier
 */
public class ProfilerTask {

    private static final String THE_NAME_IS_NULL = "the name is null"; //$NON-NLS-1$

    private static final String THE_CATEGORY_IS_NULL = "the category is null"; //$NON-NLS-1$

    /** The category. */
    private String category;

    /** The name. */
    private String name;

    /** The image path of the category. */
    private String categoryImage;

    /** The image path of the task. */
    private String taskImage;

    /**
     * Create a new Task.
     * 
     * @param category
     *            the category.
     * @param name
     *            the name.
     * @throws IllegalArgumentException
     *             if <code>category</code> or <code>name</code> is
     *             <code>null</code>.
     */
    public ProfilerTask(final String category, final String name) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException(THE_CATEGORY_IS_NULL);
        }
        if (name == null) {
            throw new IllegalArgumentException(THE_NAME_IS_NULL);
        }
        this.category = category;
        this.name = name;
    }

    /**
     * Create a new Task.
     * 
     * @param category
     *            the category.
     * @param name
     *            the name.
     * @param categoryImage
     *            the image path of the category.
     * @throws IllegalArgumentException
     *             if <code>category</code> or <code>name</code> is
     *             <code>null</code>.
     */
    public ProfilerTask(final String category, final String name, final String categoryImage) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException(THE_CATEGORY_IS_NULL);
        }
        if (name == null) {
            throw new IllegalArgumentException(THE_NAME_IS_NULL);
        }
        this.category = category;
        this.name = name;
        this.categoryImage = categoryImage;
    }

    /**
     * Create a new Task.
     * 
     * @param category
     *            the category.
     * @param name
     *            the name.
     * @param categoryImage
     *            the image path of the category.
     * @param taskImage
     *            the image path of the task.
     * @throws IllegalArgumentException
     *             if <code>category</code> or <code>name</code> is
     *             <code>null</code>.
     */
    public ProfilerTask(final String category, final String name, final String categoryImage, final String taskImage) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException(THE_CATEGORY_IS_NULL);
        }
        if (name == null) {
            throw new IllegalArgumentException(THE_NAME_IS_NULL);
        }
        this.category = category;
        this.name = name;
        this.categoryImage = categoryImage;
        this.taskImage = taskImage;
    }

    /**
     * Return the category.
     * 
     * @return the category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Return the name.
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringBuffer(category.length() + name.length() + 2).append('[').append(category).append(']').append(name).toString();
    }

    @Override
    public int hashCode() {
        return category.hashCode() ^ name.hashCode();
    }

    /**
     * Return the image path of the task.
     * 
     * @return the image path of the task.
     */
    public String getTaskImage() {
        return taskImage;
    }

    /**
     * Return the image path of the category.
     * 
     * @return the image path of the category.
     */
    public String getCategoryImage() {
        return categoryImage;
    }

}
