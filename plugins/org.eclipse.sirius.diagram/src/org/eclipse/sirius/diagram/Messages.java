/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram;

import org.eclipse.sirius.ext.base.I18N;
import org.eclipse.sirius.ext.base.I18N.TranslatableMessage;

/**
 * Helper class to obtains translated strings.
 * 
 * @author Florian Barbin
 *
 */
public final class Messages {
    static {
        I18N.initializeMessages(Messages.class, DiagramPlugin.INSTANCE);
    }

    // CHECKSTYLE:OFF

    @TranslatableMessage
    public static String AbstractDDiagramConstraint_validationErrorMessage;

    @TranslatableMessage
    public static String AbstractDiagramCommandBuilder_diagramVariableTaskLabel;

    @TranslatableMessage
    public static String AbstractDiagramCommandBuilder_refreshTaskLabel;

    @TranslatableMessage
    public static String AbstractNodeMappingQuery_evaluationErrorMsg;

    @TranslatableMessage
    public static String ChangeLayerActivationCommand_executeMsg;

    @TranslatableMessage
    public static String ChangeLayerActivationCommand_hideLabel;

    @TranslatableMessage
    public static String ChangeLayerActivationCommand_showLabel;

    @TranslatableMessage
    public static String ContainerDropDescriptionSpec_unknownTgtMsg;

    @TranslatableMessage
    public static String ContainerMappingHelper_nodeCreationErrorMsg;

    @TranslatableMessage
    public static String CreateContainerTask_label;

    @TranslatableMessage
    public static String CreateDEdgeTask_label;

    @TranslatableMessage
    public static String CreateDNodeTask_label;

    @TranslatableMessage
    public static String CreateViewTask_label;

    @TranslatableMessage
    public static String DDiagramElementContainerSpecOperations_tooMuchDropDescErrorMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_borderNodeRefreshMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_deleteNodesMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_initDiagramMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_nodeCreationMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_nodeRefreshMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshContainerChildsMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshContainerMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshEdgeMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshMappingsMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshNodeMsg;

    @TranslatableMessage
    public static String DDiagramSynchronizer_refreshNodesMsg;

    @TranslatableMessage
    public static String DEdgeSpec_deprecatedMsg;

    @TranslatableMessage
    public static String DeletionCommandBuilder_deleteDiagramLabel;

    @TranslatableMessage
    public static String DeletionCommandBuilder_deleteFromDiagramLabel;

    @TranslatableMessage
    public static String DeletionCommandBuilder_deleteFromModelLabel;

    @TranslatableMessage
    public static String DeletionCommandBuilder_deleteLabel;

    @TranslatableMessage
    public static String DiagramDialectServices_createDiagramMsg;

    @TranslatableMessage
    public static String DiagramDialectServices_initializeDiagramMsg;

    @TranslatableMessage
    public static String DiagramDialectServices_refreshDiagramMsg;

    @TranslatableMessage
    public static String DiagramDialectServices_refreshImactedElementsMsg;

    @TranslatableMessage
    public static String DiagramElementMappingHelper_edgeTargetMsg;

    @TranslatableMessage
    public static String DiagramElementMappingQuery_diagramRootExpressionEvaluationErrorMsg;

    @TranslatableMessage
    public static String DiagramElementMappingQuery_mappingCandidateExpressionEvaluationErrorMsg;

    @TranslatableMessage
    public static String DiagramIdentifier_parametersErrorMsg;

    @TranslatableMessage
    public static String DiagramMappingsManagerRegistryImpl_diagramParamErrorMsg;

    @TranslatableMessage
    public static String DiagramTypeDescriptorRegistry_loadingErrorMsg;

    @TranslatableMessage
    public static String DirectEditCommandBuilder_editLabel;

    @TranslatableMessage
    public static String DnDTasksOperations_modelErrorMsg;

    @TranslatableMessage
    public static String DropinForContainerTaskCommand_taskLabel;

    @TranslatableMessage
    public static String DropinForNodeTaskCommand_taskLabel;

    @TranslatableMessage
    public static String EdgeFilter_errorMsg;

    @TranslatableMessage
    public static String EdgeFilter_semanticIsNullErrorMsg;

    @TranslatableMessage
    public static String EdgeIdentifier_parametersErrorMsg;

    @TranslatableMessage
    public static String EdgeMappingQuery_preconditionEvaluationErrorMsg;

    @TranslatableMessage
    public static String EdgeMappingQuery_sourceFinderEvaluationErrorMsg;

    @TranslatableMessage
    public static String EdgeMappingQuery_targetFinderEvaluationErrorMsg;

    @TranslatableMessage
    public static String GetConditionalStyle_errorMsg;

    @TranslatableMessage
    public static String GetStyleDescription_errorMsg;

    @TranslatableMessage
    public static String GlobalMappingsTable_mappingErrorMsg;

    @TranslatableMessage
    public static String HideDDiagramElement_hideElementLabel;

    @TranslatableMessage
    public static String HideDDiagramElement_hideElementsLabel;

    @TranslatableMessage
    public static String HideDDiagramElementLabel_hideLabel;

    @TranslatableMessage
    public static String HideDDiagramElementLabel_hideLabels;

    @TranslatableMessage
    public static String NodeFilter_notNullErrorMsg;

    @TranslatableMessage
    public static String NodeIdentifier_parametersErrorMsg;

    @TranslatableMessage
    public static String NodeMappingHelper_methodInvocationErrorMsg;

    @TranslatableMessage
    public static String NodeMappingHelper_nodeCreationErrorMsg;

    @TranslatableMessage
    public static String PasteCommandBuilder_pasteLabel;

    @TranslatableMessage
    public static String PinElementsCommand_commandLabel;

    @TranslatableMessage
    public static String ReconnectionCommandBuilder_initVariablesMsg;

    @TranslatableMessage
    public static String ReconnectionCommandBuilder_mappingImportErrorMsg;

    @TranslatableMessage
    public static String ReconnectSourceNodeCommand_commandLabel;

    @TranslatableMessage
    public static String RefreshExtensionProviderDescriptor_instantiatingErrorMsg;

    @TranslatableMessage
    public static String RefreshSiriusElement_refreshRepresentationMsg;

    @TranslatableMessage
    public static String RepresentationExtensionDescriptionRegexConstraint_errorMsg;

    @TranslatableMessage
    public static String RevealAllElementsCommand_revealAllElementsLabel;

    @TranslatableMessage
    public static String RevealDDiagramElements_revealElementLabel;

    @TranslatableMessage
    public static String RevealDDiagramElements_revealElementsLabel;

    @TranslatableMessage
    public static String RevealDDiagramElementsLabel_revealLabel;

    @TranslatableMessage
    public static String RevealDDiagramElementsLabel_revealLabels;

    @TranslatableMessage
    public static String SetEdgeActualMappingCommand_commandLabel;

    @TranslatableMessage
    public static String UndoRedoCapableEMFCommandFactory_addDiagramVariableLabel;

    @TranslatableMessage
    public static String UndoRedoCapableEMFCommandFactory_createNewDiagramLabel;

    @TranslatableMessage
    public static String UndoRedoCapableEMFCommandFactory_createRepresentationLabel;

    @TranslatableMessage
    public static String UndoRedoCapableEMFCommandFactory_quickFixLabel;

    @TranslatableMessage
    public static String UnpinElementsCommand_commandLabel;

    @TranslatableMessage
    public static String ValidStyleConstraint_validationErrorMsg;

    // CHECKSTYLE:ON
    private Messages() {
        // Prevents instanciation.
    }
}
