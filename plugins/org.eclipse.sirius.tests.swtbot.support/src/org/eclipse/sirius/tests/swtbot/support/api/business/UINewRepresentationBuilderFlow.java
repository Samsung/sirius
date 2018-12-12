/**
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - Initial API and implementation
 */
package org.eclipse.sirius.tests.swtbot.support.api.business;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * Class which stores flow declaration for {@link UINewRepresentationBuilder}.
 * 
 * @author dlecan
 */
public class UINewRepresentationBuilderFlow {

    /**
     * Choice of item.
     * 
     * @author dlecan
     * @param <R>
     *            Type of UI representation.
     */
    public interface ItemChoice<R extends AbstractUIRepresentation<?>> {
        /**
         * On method.
         * 
         * @param treeItem
         *            Tree item on which you want to create new representation.
         * @return Current {@link NameChoice}
         */
        NameChoice<R> on(final SWTBotTreeItem treeItem);
    }

    /**
     * Choice of name.
     * 
     * @param <R>
     *            Type of UI representation
     * 
     * @author dlecan
     */
    public interface NameChoice<R extends AbstractUIRepresentation<?>> {

        /**
         * withName method.
         * 
         * @param name
         *            name of the new representation.
         * @return Current {@link EndFlow}
         */
        EndFlow<R> withName(final String name);

        /**
         * New representation with default name.
         * 
         * @return Current {@link EndFlow}
         */
        EndFlow<R> withDefaultName();

    }

    /**
     * End of flow.
     * 
     * @param <R>
     *            Type of UI representation
     * 
     * @author dlecan
     */
    public interface EndFlow<R extends AbstractUIRepresentation<?>> {

        /**
         * Finish creation with click on "ok".
         * 
         * @return Representation.
         */
        R ok();

        /**
         * Finish creation with click on "ok".
         * 
         * @param disableSnapToGridOnThisEditor
         *            true if the snapToGrid must be disable for this editor,
         *            false otherwise
         * 
         * @return Representation.
         */
        R ok(boolean disableSnapToGridOnThisEditor);

        /**
         * Finish creation with click on "cancel".
         */
        void cancel();
    }

}
