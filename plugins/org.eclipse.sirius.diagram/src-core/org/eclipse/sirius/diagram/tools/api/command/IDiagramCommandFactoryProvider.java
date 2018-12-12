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
package org.eclipse.sirius.diagram.tools.api.command;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * this interface need to be implemented if you want to provide an
 * EMFCommandFactory.
 * 
 * @author Mariot Chauvin (mchauvin)
 * @since 0.9.0
 */
public interface IDiagramCommandFactoryProvider {

    /**
     * Get the command factory.
     * 
     * @param editingDomain
     *            the editing domain to use
     * @return the command factory
     */
    IDiagramCommandFactory getCommandFactory(TransactionalEditingDomain editingDomain);

}
