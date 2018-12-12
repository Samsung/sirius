/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.sirius.business.api.migration;

import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * A {@link RuntimeException} used when the VSM version is more recent than the
 * last Sirius migration version.
 * 
 * @author <a href="mailto:laurent.fasani@obeo.fr">Laurent Fasani</a>
 */
public class DescriptionResourceVersionMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Collection<ResourceVersionMismatchDiagnostic> diagnostics = Lists.newArrayList();

    /**
     * Default constructor.
     * 
     * @param diagnostics
     *            the diagnotic behind this exception
     */
    public DescriptionResourceVersionMismatchException(Collection<ResourceVersionMismatchDiagnostic> diagnostics) {
        super(computeMessage(diagnostics));
        this.diagnostics.addAll(diagnostics);
    }

    public Collection<ResourceVersionMismatchDiagnostic> getDiagnostics() {
        return diagnostics;
    }

    private static String computeMessage(Collection<ResourceVersionMismatchDiagnostic> diagnostics) {
        StringBuilder sb = new StringBuilder();
        for (ResourceVersionMismatchDiagnostic resourceVersionMismatchDiagnostic : diagnostics) {
            sb.append(resourceVersionMismatchDiagnostic.getMessage());
        }

        return sb.toString();
    }
}
