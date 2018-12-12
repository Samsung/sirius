/*******************************************************************************
 * Copyright (c) 2008, 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.business.api.preferences;

/**
 * This class bundles all the preferences keys for viewpoint.
 * 
 * @author ymortier
 */
public enum SiriusUIPreferencesKeys {

    /**
     * Says if the refresh should automatically be done on representation
     * opening.
     */
    PREF_REFRESH_ON_REPRESENTATION_OPENING,

    /**
     * Says if session should return to the last
     * {@link org.eclipse.sirius.business.api.session.SessionStatus.SYNC}
     * state when closing its last opened editor.
     * 
     * For a coherent save behavior, the preference should have the same value
     * than PREF_SAVE_WHEN_NO_EDITOR.
     */
    PREF_RELOAD_ON_LAST_EDITOR_CLOSE,

    /**
     * Says if session should be saved on a resource change of there is no
     * opened dialect editors.
     * 
     * For a coherent save behavior, the preference should have the same value
     * than PREF_RELOAD_ON_LAST_EDITOR_CLOSE.
     */
    PREF_SAVE_WHEN_NO_EDITOR,

    /**
     * Specifies whether any permission issue should be displayed graphically to
     * end-user. If false, then it will simply be logged in the Error Log.
     */
    PREF_REACT_TO_PERMISSION_ISSUES_BY_GRAPHICAL_DISPLAY,

    /**
     * <b>Not considered if
     * {@link DesignerUIPreferencesKeys#PREF_REACT_TO_PERMISSION_ISSUES_BY_GRAPHICAL_DISPLAY}
     * is false</b>. When a a permission issue should be displayed graphically,
     * specifies wether it should be displayed through the Dialect (e.g. a
     * NotificationFigure inside a DDiagram) or through a standard pop-up.
     */
    PREF_DISPLAY_PERMISSION_ISSUES_THROUGH_DIALOG;

}
