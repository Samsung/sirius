/*******************************************************************************
 * Copyright (c) 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.api.helper.display;

import org.eclipse.sirius.diagram.business.internal.helper.display.DisplayServiceManagerImpl;

/**
 * The display service manager handles display services.
 * 
 * @author mchauvin
 */
public interface DisplayServiceManager {

    /** The shared instance. */
    DisplayServiceManager INSTANCE = DisplayServiceManagerImpl.init();

    /**
     * Get the appropriate display service.
     * 
     * @return the display service to use
     */
    DisplayService getDisplayService();

    /**
     * Get the appropriate display service.
     * 
     * @param mode
     *            the wanted mode
     * @return the requested display
     * @since 0.9.0
     */
    DisplayService getDisplayService(DisplayMode mode);

    /**
     * Activate a display mode.
     * 
     * @param mode
     *            the mode to activate
     */
    void activateMode(DisplayMode mode);

    /**
     * Get the current active mode.
     * 
     * @return the active mode
     */
    DisplayMode getMode();
}
