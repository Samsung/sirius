/*******************************************************************************
 * Copyright (c) 2007, 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.experimental.sync;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.util.RefreshIdsHolder;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DragAndDropTarget;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMappingImport;
import org.eclipse.sirius.diagram.description.NodeMappingImport;
import org.eclipse.sirius.viewpoint.description.AbstractMappingImport;

/**
 * This class represents a candidate for a AbstractDNode, a candidate is a
 * "possible" AbstractDNode which has not been confirmed yet by validation and
 * preconditions.
 * 
 * @author cbrun
 * 
 */
public class AbstractDNodeCandidate {
    private DragAndDropTarget viewContainer;

    private EObject semantic;

    private AbstractNodeMapping mapping;

    private AbstractNodeMapping rootMapping;

    /**
     * The original element from which the candidate has been created. May be
     * null if no element has been used.
     */
    private AbstractDNode element;

    private RefreshIdsHolder ids;

    /**
     * Create a new candidate.
     * 
     * @param mapping
     *            the node mapping.
     * @param semanticElement
     *            the target semantic element.
     * @param viewContainer
     *            the view container.
     * @param ids
     *            the refresh ids holder.
     */
    public AbstractDNodeCandidate(final AbstractNodeMapping mapping, final EObject semanticElement, final DragAndDropTarget viewContainer, RefreshIdsHolder ids) {
        super();
        this.ids = ids;
        this.mapping = mapping;
        this.semantic = semanticElement;
        this.viewContainer = viewContainer;
        this.rootMapping = getRootMapping(mapping);
    }

    /**
     * Create a new candidate from a diagram element.
     * 
     * @param diagElement
     *            an existing diagram element.
     * @param ids
     *            the refresh ids holder.
     */
    public AbstractDNodeCandidate(final AbstractDNode diagElement, RefreshIdsHolder ids) {
        this.ids = ids;
        this.mapping = (AbstractNodeMapping) diagElement.getMapping();
        this.semantic = diagElement.getTarget();
        this.viewContainer = (DragAndDropTarget) diagElement.eContainer();
        this.element = diagElement;
        this.rootMapping = getRootMapping(this.mapping);
    }

    /**
     * Tells whether this candidate has been created from an existing element or
     * not.
     * 
     * @return true if the candidate has been created from an existing element.
     */
    public boolean comesFromDiagramElement() {
        return getOriginalElement() != null;
    }

    /**
     * Return the original element which has been used for the candidate
     * creation.
     * 
     * @return the original element which has been used for the candidate
     *         creation, null if no element has been used.
     */
    public AbstractDNode getOriginalElement() {
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rootMapping == null) ? 0 : rootMapping.hashCode());
        result = prime * result + ((semantic == null) ? 0 : getSemanticID().hashCode());
        result = prime * result + ((viewContainer == null) ? 0 : getViewContainerID().hashCode());
        return result;
    }

    /**
     * return the view container URI.
     * 
     * @return the view container URI.
     */
    private Id getViewContainerID() {
        if (semantic == null) {
            return Id.invalid();
        }
        return new Id(this.ids.getOrCreateID(viewContainer));
    }

    /**
     * return the semantic id.
     * 
     * @return the semantic ID
     */
    private Id getSemanticID() {
        if (semantic == null) {
            return Id.invalid();
        }
        return new Id(this.ids.getOrCreateID(semantic));
    }

    /**
     * {@inheritDoc}
     */
    // CHECKSTYLE:OFF
    @Override
    // Eclipse generated
    // Beware of us of get*URI() methods
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractDNodeCandidate)) {
            return false;
        }
        AbstractDNodeCandidate other = (AbstractDNodeCandidate) obj;
        if (rootMapping == null) {
            if (other.rootMapping != null) {
                return false;
            }
        } else if (!rootMapping.equals(other.rootMapping)) {
            return false;
        }
        if (semantic == null) {
            if (other.semantic != null) {
                return false;
            }
        } else if (!getSemanticID().equals(other.getSemanticID())) {
            return false;
        }
        if (viewContainer == null) {
            if (other.viewContainer != null) {
                return false;
            }
        } else if (!getViewContainerID().equals(other.getViewContainerID())) {
            return false;
        }
        return true;
    }

    // CHECKSTYLE:ON

    public AbstractNodeMapping getMapping() {
        return this.mapping;
    }

    public EObject getSemantic() {
        return this.semantic;
    }

    public DragAndDropTarget getViewContainer() {
        return this.viewContainer;
    }

    private AbstractNodeMapping getRootMapping(final AbstractNodeMapping mappingImport) {
        if (mappingImport == null) {
            return null;
        }
        AbstractNodeMapping result = mappingImport;
        while (result instanceof AbstractMappingImport) {
            if (result instanceof ContainerMappingImport) {
                result = ((ContainerMappingImport) result).getImportedMapping();
            } else if (result instanceof NodeMappingImport) {
                result = ((NodeMappingImport) result).getImportedMapping();
            }
        }
        return result;
    }

    /**
     * An identifier class based on an integer with a specific equals method
     * which handles mobility.
     * 
     * @author mchauvin
     */
    private static final class Id {

        private Integer value;

        public Id(final Integer value) {
            this.value = value;
        }

        private static Id invalid() {
            return new Id(-1);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        // CHECKSTYLE:OFF
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Id)) {
                return false;
            }
            final Id other = (Id) obj;;
            return value.equals(other.value);
        }
        // CHECKSTYLE:ON

    }

}
