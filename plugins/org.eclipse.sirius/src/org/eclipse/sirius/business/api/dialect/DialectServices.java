/*******************************************************************************
 * Copyright (c) 2007, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.api.dialect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.identifier.RepresentationElementIdentifier;
import org.eclipse.sirius.business.api.helper.task.AbstractCommandTask;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.tools.api.command.CommandContext;
import org.eclipse.sirius.tools.api.command.ui.UICallBack;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.RepresentationExtensionDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;

/**
 * Stateless services.
 * 
 * @author cbrun
 */
public interface DialectServices {
    /**
     * Return all the available {@link RepresentationDescription} the user might
     * use to create a new {@link DRepresentation}.
     * 
     * @param vps
     *            the currently enabled viewpoints.
     * @param semantic
     *            the selected semantic element.
     * @return a collection of all the applicable descriptions.
     */
    Collection<RepresentationDescription> getAvailableRepresentationDescriptions(Collection<Viewpoint> vps, EObject semantic);

    /**
     * Create a new representation using the representation description and keep
     * it in the given session. The new representation is refreshed.
     * 
     * @param name
     *            name of the representation to create.
     * 
     * @param semantic
     *            semantic root used to create the representation.
     * @param description
     *            representation description to use.
     * @param session
     *            session used to keep the data.
     * @param monitor
     *            to track progress.
     * @return the new representation .
     */
    DRepresentation createRepresentation(String name, EObject semantic, RepresentationDescription description, Session session, IProgressMonitor monitor);

    /**
     * Create a new representation from a given one (copy) and keep it in the
     * given session.
     * 
     * @param representation
     *            the representation to copy.
     * @param name
     *            name of the newly representation.
     * @param session
     *            session used to keep the data.
     * @param monitor
     *            to track progress.
     * @return the new representation .
     * @since 0.9.0
     */
    DRepresentation copyRepresentation(DRepresentation representation, String name, Session session, IProgressMonitor monitor);

    /**
     * Return the representations provided by the dialect.
     * 
     * @param semantic
     *            targeted semantic element.
     * @param session
     *            the current session.
     * @return the list of representations contributed by this dialect.
     */
    Collection<DRepresentation> getRepresentations(EObject semantic, Session session);

    /**
     * Return all the representations provided by the dialect.
     * 
     * @param session
     *            the current session.
     * @return the list of representations contributed by this dialect.
     */
    Collection<DRepresentation> getAllRepresentations(Session session);

    /**
     * Return the representations provided by the dialect from a description.
     * 
     * @param representationDescription
     *            the representation description instance
     * @param session
     *            the current session.
     * @return the list of representations contributed by this dialect.
     */
    Collection<DRepresentation> getRepresentations(RepresentationDescription representationDescription, Session session);

    /**
     * Return the representation descriptors provided by the dialect.
     * 
     * @param semantic
     *            targeted semantic element.
     * @param session
     *            the current session.
     * @return the list of representation descriptors contributed by this
     *         dialect.
     */
    Collection<DRepresentationDescriptor> getRepresentationDescriptors(EObject semantic, Session session);

    /**
     * Return all the representation descriptors provided by the dialect.
     * 
     * @param session
     *            the current session.
     * @return the list of representation descriptors contributed by this
     *         dialect.
     */
    Collection<DRepresentationDescriptor> getAllRepresentationDescriptors(Session session);

    /**
     * Return the representation descriptors provided by the dialect from a
     * description.
     * 
     * @param representationDescription
     *            the representation description instance
     * @param session
     *            the current session.
     * @return the list of representation descriptors contributed by this
     *         dialect.
     */
    Collection<DRepresentationDescriptor> getRepresentationDescriptors(RepresentationDescription representationDescription, Session session);

    /**
     * Refresh the actual representation description used for the specified
     * representation when the context changes.
     * 
     * @param representation
     *            the representation whose description to update.
     * @param monitor
     *            to monitor the refresh operation.
     */
    void refreshEffectiveRepresentationDescription(DRepresentation representation, IProgressMonitor monitor);

    /**
     * Refresh a representation. By default a lazy refresh is done, i.e. only
     * view model elements for which UI parts will be visible will be created by
     * the refresh. To do a full refresh use
     * {@link DialectServices#refresh(DRepresentation, boolean, IProgressMonitor)}
     * with true as second parameter.
     * 
     * @param representation
     *            representation to refresh.
     * @param monitor
     *            to monitor the refresh operation.
     */
    void refresh(DRepresentation representation, IProgressMonitor monitor);

    /**
     * Refresh a representation.
     * 
     * @param representation
     *            representation to refresh.
     * @param fullRefresh
     *            if true, all the representation elements are refreshed (or
     *            created/removed as needed), including elements which may not
     *            be visible to the user. If false, the implementation is
     *            allowed (but not required) not to refresh some representation
     *            elements if it can determine that the user can not see them
     *            and interact with them. Note that a full refresh could not
     *            finish in some case like recursive import mapping.
     * @param monitor
     *            to monitor the refresh operation.
     */
    void refresh(DRepresentation representation, boolean fullRefresh, IProgressMonitor monitor);

    /**
     * Performs a refresh only for elements impacted by the given notifications
     * list.
     * 
     * @param representation
     *            representation to refresh.
     * @param notifications
     *            the notifications that triggered this refresh. This list does
     *            not contain touch notifications.
     * @param monitor
     *            to monitor the refresh operation.
     */
    void refreshImpactedElements(DRepresentation representation, Collection<Notification> notifications, IProgressMonitor monitor);

    /**
     * Tell whether the dialect is able to refresh the given representation.
     * 
     * NOTE: a prerequisite to have a {@link DRepresentation} refreshable is to
     * have its required viewpoints selected in the current session.
     * 
     * @param representation
     *            representation to refresh.
     * @return true if the dialect can refresh the representation, false
     *         otherwise.
     * @see DialectServices#getRequiredViewpoints(DRepresentation)
     */
    boolean canRefresh(DRepresentation representation);

    /**
     * Tell whether the dialect is able to create a representation from the
     * given representation description and the semantic object.
     * 
     * NOTE: If the semantic does not belong to a session we do not check that
     * required viewpoint is selected but only others things like domainClass
     * 
     * @param semantic
     *            semantic object used to create the representation.
     * @param desc
     *            {@link RepresentationDescription}.
     * @return true if the dialect manages such
     *         {@link RepresentationDescription}, false otherwise.
     */
    boolean canCreate(EObject semantic, RepresentationDescription desc);

    /**
     * Called when a new representation has been created,deleted or any other
     * handled notifications. The service might want to update the model state
     * and create links for instance.
     * 
     * @param notification
     *            what's new.
     */
    void notify(RepresentationNotification notification);

    /**
     * Delete the given representation. The implementation should only handle
     * representations it is aware of.
     * 
     * @param repDescriptor
     *            the descriptor of the representation to delete.
     * @param session
     *            the current session.
     * @return true if the dialect did the delete, false otherwise.
     */
    boolean deleteRepresentation(DRepresentationDescriptor repDescriptor, Session session);

    /**
     * Returns the description of the given representation.
     * 
     * @param representation
     *            the representation.
     * @return the description of the given representation.
     */
    RepresentationDescription getDescription(DRepresentation representation);

    /**
     * Init all the representations of a the viewpoint.
     * 
     * @param vp
     *            the viewpoint
     * @param semantic
     *            the semantic element
     * @param monitor
     *            a {@link IProgressMonitor} to show progression of
     *            representations initialization
     */
    void initRepresentations(Viewpoint vp, EObject semantic, IProgressMonitor monitor);

    /**
     * Tell whether the dialect is able to create an identifier.
     * 
     * @param representationElement
     *            the representation element for which to create an identifier
     * @return <code>true</code> if it cans, <code>false</code> otherwise.
     */
    boolean canCreateIdentifier(EObject representationElement);

    /**
     * Create an identifier.
     * 
     * @param representationElement
     *            the representation element for which to create an identifier
     * @param elementToIdentifier
     *            a map which contains element as key and identifier as value
     * @return the created identifier, or <code>null</code> if it can not be
     *         created.
     */
    RepresentationElementIdentifier createIdentifier(EObject representationElement, Map<EObject, RepresentationElementIdentifier> elementToIdentifier);

    /**
     * Update all the existing representations in a session which are extended
     * by a viewpoint activated or deactivated.
     * 
     * @param session
     *            the session
     * @param viewpoint
     *            the viewpoint activated or deactivated.
     * @param activated
     *            <code>true</code> if this viewpoint has to be activated,
     *            <code>false</code> if it has to be deactivated.
     * @since 0.9.0
     */
    void updateRepresentationsExtendedBy(Session session, Viewpoint viewpoint, boolean activated);

    /**
     * Creates an {@link IInterpretedExpressionQuery} that will query
     * representation descriptions to determine useful informations (like the
     * type to consider for Interpreted expressions).
     * 
     * @param target
     *            the target of this query (must part of a representation
     *            description)
     * @param feature
     *            the feature to consider (for example
     *            {@link org.eclipse.sirius.viewpoint.description.DescriptionPackage#eINSTANCE
     *            #getDiagramElementMapping_SemanticCandidatesExpression()}.
     * @return an {@link IInterpretedExpressionQuery} that will query
     *         representation descriptions to determine useful informations
     *         (like the type to consider for Interpreted expressions)
     * @since 0.9.0
     */
    IInterpretedExpressionQuery createInterpretedExpressionQuery(EObject target, EStructuralFeature feature);

    /**
     * Indicates if the current dialect handles the given
     * {@link RepresentationDescription}.
     * 
     * @param representationDescription
     *            the representationDescription to test
     * @return true if the current dialect handles the given
     *         {@link RepresentationDescription}
     * @since 0.9.0
     */
    boolean handles(RepresentationDescription representationDescription);

    /**
     * Indicates if the current dialect handles the given
     * {@link RepresentationExtensionDescription}.
     * 
     * @param representationExtensionDescription
     *            the representationExtensionDescription to test
     * @return true if the current dialect handles the given
     *         {@link RepresentationExtensionDescription}
     * @since 1.0.0 M6
     */
    boolean handles(RepresentationExtensionDescription representationExtensionDescription);

    /**
     * Each dialect can have its proper mapping cache. This method can be called
     * when this cache should be clean and computed again (for example when the
     * selected viewpoints list is changed).
     */
    void invalidateMappingCache();

    /**
     * Create a new task from the {@link ModelOperation}.
     * 
     * @param context
     *            current context.
     * @param extPackage
     *            access to semantic model.
     * @param op
     *            the operation to transform to Task
     * @param session
     *            the {@link Session} to be used.
     * @param uiCallback
     *            user interface interactions.
     * @return an optional task
     */
    Option<? extends AbstractCommandTask> createTask(CommandContext context, ModelAccessor extPackage, ModelOperation op, Session session, UICallBack uiCallback);

    /**
     * Indicates if the current dialect allows the customization of the given
     * specification element.
     * 
     * @param element
     *            a VSM element.
     * @return true if the current dialect allows the customization of the given
     *         VSM element.
     * @since 1.0.0 M6
     */
    boolean allowsEStructuralFeatureCustomization(EObject element);

    /**
     * Tell which {@link Viewpoint viewpoints} are required to do a refresh of
     * the specified <code>representation</code>.
     * 
     * NOTE: only available viewpoints are returned. In some cases, the
     * Viewpoint of a selected layer might not be available: for example if it
     * is not installed.
     * 
     * @param representation
     *            the specified {@link DRepresentation}
     * @return the collection of required {@link Viewpoint viewpoints}
     */
    Set<Viewpoint> getRequiredViewpoints(DRepresentation representation);
}
