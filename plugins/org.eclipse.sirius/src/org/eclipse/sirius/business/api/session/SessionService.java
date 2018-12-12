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
package org.eclipse.sirius.business.api.session;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

/**
 * Base services associated with an editing session.
 * 
 * @author cbrun
 * 
 */
public interface SessionService {

    /**
     * Tell the session to store specific data. Consecutive calls with the same
     * key and associated instance *wont* replace the data but add it to a
     * collection. Make sure you use clearCustomData if you want to replace some
     * data.
     * 
     * @param key
     *            a key telling the kind of data, you can use any key you want,
     *            it's just to be able to retrieve the data.
     * @param associatedInstance
     *            an EObject associated with the data.
     * @param data
     *            data to keep.
     */
    void putCustomData(String key, EObject associatedInstance, EObject data);

    /**
     * Retrieve the custom data associated with the given instance and the given
     * key.
     * 
     * @param key
     *            a key telling the kind of data.
     * @param associatedInstance
     *            the instance associated with the data.
     * @return a collection of all the data's associated with this.
     */
    Collection<EObject> getCustomData(String key, EObject associatedInstance);

    /**
     * Clear the custom data associated with the given instance and the given
     * key.
     * 
     * @param key
     *            a key telling the kind of data.
     * @param associatedInstance
     *            instance associated with the data.
     */
    void clearCustomData(String key, EObject associatedInstance);

    /**
     * Clear all the custom data associated with the given instance. It may be
     * called when this instance gets destroyed.
     * 
     * @param associatedInstance
     *            instance associated with the data.
     */
    void clearCustomData(EObject associatedInstance);
}
