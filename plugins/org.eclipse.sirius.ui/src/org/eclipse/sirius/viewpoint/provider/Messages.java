/*******************************************************************************
 * Copyright (c) 2015, 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.viewpoint.provider;

import org.eclipse.sirius.ext.base.I18N;
import org.eclipse.sirius.ext.base.I18N.TranslatableMessage;

/**
 * Helper class to obtain translated strings.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public final class Messages {

    static {
        I18N.initializeMessages(Messages.class, SiriusEditPlugin.INSTANCE);
    }

    // CHECKSTYLE:OFF
    @TranslatableMessage
    public static String AbstractCompositeEObjectPropertySource_missingEObject;

    @TranslatableMessage
    public static String AbstractCompositeEObjectPropertySource_missingId;

    @TranslatableMessage
    public static String AbstractCreateRepresentationFromRepresentationCreationDescription_creationError_message;

    @TranslatableMessage
    public static String AbstractCreateRepresentationFromRepresentationCreationDescription_creationError_title;

    @TranslatableMessage
    public static String AbstractCreateRepresentationFromRepresentationCreationDescription_defaultName;

    @TranslatableMessage
    public static String AbstractDTreeEditor_eclipseWindowTitle;

    @TranslatableMessage
    public static String AbstractDTreeEditor_modelerDescriptionFilesLoadedJob;

    @TranslatableMessage
    public static String AbstractDTreeEditor_noSessionError;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAction_cancelled;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAction_error;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_accessError;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_blankFolderError;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_browseLabel;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_folderDoesNotExist;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_htmlExport;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_imageFormatLabel;

    @TranslatableMessage
    public static String AbstractExportRepresentationsAsImagesDialog_invalidFolderPathError;

    @TranslatableMessage
    public static String AbstractSWTCallback_askForDetailName_canceled;

    @TranslatableMessage
    public static String AbstractSWTCallback_loadResourceError;

    @TranslatableMessage
    public static String AbstractSWTCallback_models;

    @TranslatableMessage
    public static String AbstractSWTCallback_modelsAndRepresentations;

    @TranslatableMessage
    public static String AbstractSWTCallback_modelsAndRepresentationsInProject;

    @TranslatableMessage
    public static String AbstractSWTCallback_modelsInProject;

    @TranslatableMessage
    public static String AbstractSWTCallback_representations;

    @TranslatableMessage
    public static String AbstractSWTCallback_representationsInProject;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldClose_message;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldClose_title;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldReload_message;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldReload_title;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldRemove_message;

    @TranslatableMessage
    public static String AbstractSWTCallback_shouldRemove_title;

    @TranslatableMessage
    public static String AddModelDependencyAction_error;

    @TranslatableMessage
    public static String AddModelDependencyAction_resourceSelectionMessage;

    @TranslatableMessage
    public static String AddModelDependencyAction_title;

    @TranslatableMessage
    public static String AddSemanticResourceAction_title;

    public static String AskSessionOpeningRunnable_title;

    @TranslatableMessage
    public static String AskSessionOpeningRunnable_message_aird;

    @TranslatableMessage
    public static String AskSessionOpeningRunnable_message_confirm;

    @TranslatableMessage
    public static String ChangeViewpointSelectionCommand_activationError;

    @TranslatableMessage
    public static String ChangeViewpointSelectionCommand_applySelectionTask;

    @TranslatableMessage
    public static String ChangeViewpointSelectionCommand_deselectViewpointTask;

    @TranslatableMessage
    public static String ChangeViewpointSelectionCommand_label;

    @TranslatableMessage
    public static String ChangeViewpointSelectionCommand_selectViewpointTask;

    @TranslatableMessage
    public static String CloseSessionsAction_error;

    @TranslatableMessage
    public static String CloseUISessionCommand_closeRepresentationFileTask;

    @TranslatableMessage
    public static String CloseUISessionCommand_closingError;

    @TranslatableMessage
    public static String CloseUISessionCommand_saveDialogTitle;

    @TranslatableMessage
    public static String CommonNavigatorTab_name;

    @TranslatableMessage
    public static String ConfirmationDialogDeleteHook_dialogMessage;

    @TranslatableMessage
    public static String ConfirmationDialogDeleteHook_dialogTitle;

    @TranslatableMessage
    public static String ContextMenuFiller_closeSession;

    @TranslatableMessage
    public static String ContextMenuFiller_move;

    @TranslatableMessage
    public static String ContextMenuFiller_newRepresentation;

    @TranslatableMessage
    public static String ContextMenuFiller_saveSession;

    @TranslatableMessage
    public static String ContextMenuFiller_saveSessionError;

    @TranslatableMessage
    public static String ContextMenuFiller_showInHierarchy;

    @TranslatableMessage
    public static String CopyRepresentationAction_copyRepresentationDialog_title;

    @TranslatableMessage
    public static String CopyRepresentationAction_copyRepresentationsDialog_defaultNewName;

    @TranslatableMessage
    public static String CopyRepresentationAction_copyRepresentationsDialog_message;

    @TranslatableMessage
    public static String CopyRepresentationAction_copyRepresentationsDialog_title;

    @TranslatableMessage
    public static String CopyRepresentationAction_name;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_initialEObject;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_modelCreation;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_resourceAdditionError;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_resourceCreationError;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_resourceNotCreatedError;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_selectResourceMessage;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_windowTitle;

    @TranslatableMessage
    public static String CreateOrAddResourceWizard_wizardTitle;

    @TranslatableMessage
    public static String CreateOrAddResourceWizardPage_addResource_details;

    @TranslatableMessage
    public static String CreateOrAddResourceWizardPage_addResource_label;

    @TranslatableMessage
    public static String CreateOrAddResourceWizardPage_createResource_details;

    @TranslatableMessage
    public static String CreateOrAddResourceWizardPage_createResource_label;

    @TranslatableMessage
    public static String CreateOrAddResourceWizardPage_description;

    @TranslatableMessage
    public static String CreateRepresentationAction_creationTask;

    @TranslatableMessage
    public static String CreateRepresentationAction_openingTask;

    @TranslatableMessage
    public static String CreateRepresentationFromSessionAction_name;

    @TranslatableMessage
    public static String CreateRepresentationFromSessionAction_wizardTitle;

    @TranslatableMessage
    public static String CreateRepresentationWizard_title;

    @TranslatableMessage
    public static String CreateSessionResourceWizard_resourceCreationError;

    @TranslatableMessage
    public static String CreateSessionResourceWizard_sessionDataCreationError;

    @TranslatableMessage
    public static String CreateSessionResourceWizard_title;

    @TranslatableMessage
    public static String DefaultDialectEditorDialogFactory_message;

    @TranslatableMessage
    public static String DefaultDialectEditorDialogFactory_title;

    @TranslatableMessage
    public static String DeleteRepresentationAction_closeEditorsTask;

    @TranslatableMessage
    public static String DeleteRepresentationAction_deleteRepresentationTask;

    @TranslatableMessage
    public static String DeleteRepresentationAction_deleteRepresentationTask_plural;

    @TranslatableMessage
    public static String DeleteRepresentationAction_message;

    @TranslatableMessage
    public static String DeleteRepresentationAction_message_plural;

    @TranslatableMessage
    public static String DeleteRepresentationAction_name;

    @TranslatableMessage
    public static String DeleteRepresentationAction_title;

    @TranslatableMessage
    public static String DeleteRepresentationAction_title_plural;

    @TranslatableMessage
    public static String DesignerControlAction_controlTask;

    @TranslatableMessage
    public static String DesignerControlAction_saveDialogTitle;

    @TranslatableMessage
    public static String DesignerControlAction_savingTask;

    @TranslatableMessage
    public static String DesignerControlAction_uncontrolTask;

    @TranslatableMessage
    public static String DesignerInterpreterView_addDependencyButton;

    @TranslatableMessage
    public static String DesignerInterpreterView_addDependencyDialogMessage;

    @TranslatableMessage
    public static String DesignerInterpreterView_addDependencyDialogTitle;

    @TranslatableMessage
    public static String DesignerInterpreterView_copyToClipboardButton;

    @TranslatableMessage
    public static String DesignerInterpreterView_copyToClipboardResult;

    @TranslatableMessage
    public static String DesignerInterpreterView_evaluationResult;

    @TranslatableMessage
    public static String DesignerInterpreterView_invalidExpressionError;

    @TranslatableMessage
    public static String DesignerInterpreterView_requestsInterpreter;

    @TranslatableMessage
    public static String DesignerInterpreterView_setButton;

    @TranslatableMessage
    public static String DesignerInterpreterView_typeVariableName;

    @TranslatableMessage
    public static String DesignerInterpreterView_unsetButton;

    @TranslatableMessage
    public static String DesignerInterpreterView_variableName;

    @TranslatableMessage
    public static String DesignerInterpreterView_variablesSection;

    @TranslatableMessage
    public static String DTableLabelProvider_nbSelectedItems;

    @TranslatableMessage
    public static String DTableLabelProvider_selectedItemsList;

    @TranslatableMessage
    public static String EclipseUISessionFactoryDescriptor_extensionLoadingError;

    @TranslatableMessage
    public static String EditorNameAdapter_representationClosingError;

    @TranslatableMessage
    public static String ExportAction_defaultDiagramName;

    @TranslatableMessage
    public static String ExportAction_exportDiagramsAsImagesCancelled;

    @TranslatableMessage
    public static String ExportAction_exportDiagramsAsImagesTitle;

    @TranslatableMessage
    public static String ExportAction_exportError;

    @TranslatableMessage
    public static String ExportAction_exportImpossibleTitle;

    @TranslatableMessage
    public static String ExportAction_imagesTooLargeMessage;

    @TranslatableMessage
    public static String ExportAction_memAllocError;

    @TranslatableMessage
    public static String ExportOneRepresentationAsImageDialog_dialogTitle;

    @TranslatableMessage
    public static String ExportOneRepresentationAsImageDialog_exportToImage;

    @TranslatableMessage
    public static String ExportOneRepresentationAsImageDialog_invalidFilePath;

    @TranslatableMessage
    public static String ExportOneRepresentationAsImageDialog_invalidPath;

    @TranslatableMessage
    public static String ExportOneRepresentationAsImageDialog_toFile;

    @TranslatableMessage
    public static String ExportRepresentationsAction_label;

    @TranslatableMessage
    public static String ExportRepresentationsAction_noRepresentationsDialog_message;

    @TranslatableMessage
    public static String ExportRepresentationsAction_noRepresentationsDialog_title;

    @TranslatableMessage
    public static String ExportRepresentationsFromFileAction_errorDialog_title;

    @TranslatableMessage
    public static String ExportRepresentationsFromFileAction_exportTask;

    @TranslatableMessage
    public static String ExportRepresentationsFromFileAction_interruptedDialog_title;

    @TranslatableMessage
    public static String ExportRepresentationsFromFileAction_noRepresentationsDialog_message;

    @TranslatableMessage
    public static String ExportRepresentationsFromFileAction_noRepresentationsDialog_title;

    @TranslatableMessage
    public static String ExportSeveralRepresentationsAsImagesDialog_dialogMessage;

    @TranslatableMessage
    public static String ExportSeveralRepresentationsAsImagesDialog_dialogTitle;

    @TranslatableMessage
    public static String ExportSeveralRepresentationsAsImagesDialog_toDirectory;

    @TranslatableMessage
    public static String ExtractRepresentationAction_dialogTitle;

    @TranslatableMessage
    public static String ExtractRepresentationAction_label;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_airdCreationError;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_moveFailed;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_moveInterrupted;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_pageName;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_resourceCreationError;

    @TranslatableMessage
    public static String ExtractRepresentationsWizard_title;

    @TranslatableMessage
    public static String FilteredCommonTree_missingFilter;

    @TranslatableMessage
    public static String FilteredCommonTree_refreshFilterJob;

    @TranslatableMessage
    public static String GenericInitialObjectPage_containerLabel;

    @TranslatableMessage
    public static String GenericInitialObjectPage_encodingLabel;

    @TranslatableMessage
    public static String GenericModelCreationPage_fileExtensionError;

    @TranslatableMessage
    public static String HierarchyLabelProvider_elementWihtoutNameLabel;

    @TranslatableMessage
    public static String InvalidModelingProjectMarkerUpdaterJob_updateMarkers;

    @TranslatableMessage
    public static String LoadEMFResourceRunnableWithProgress_loadResourceTask;

    @TranslatableMessage
    public static String LogThroughActiveDialectEditorLogListener_permissionError;

    @TranslatableMessage
    public static String MarkerDeletionJob_name;

    @TranslatableMessage
    public static String MenuHelper_anonymousRepresentation;

    @TranslatableMessage
    public static String Messages_createRepresentationInputDialog_NamePrefix;

    @TranslatableMessage
    public static String Messages_createRepresentationInputDialog_NewRepresentationNameLabel;

    @TranslatableMessage
    public static String Messages_createRepresentationInputDialog_RepresentationDescriptionLabel;

    @TranslatableMessage
    public static String Messages_createRepresentationInputDialog_Title;

    @TranslatableMessage
    public static String Messages_createRepresentationInputDialog_DefaultRepresentationDescName;

    @TranslatableMessage
    public static String ModelingModelProvider_addAnotherRepresentationFile;

    @TranslatableMessage
    public static String ModelingModelProvider_addAnotherRepresentationFileSeveralProjects;

    @TranslatableMessage
    public static String ModelingModelProvider_mainRepresentationFileDeleted;

    @TranslatableMessage
    public static String ModelingModelProvider_mainRepresentationFilesOfSomeProjectsDeleted;

    @TranslatableMessage
    public static String ModelingModelProvider_satusUnsaveDataWillBeLostWithProjectNames;

    @TranslatableMessage
    public static String ModelingModelProvider_satusUnsavedDataWillBeLost;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_addingModelingNatureTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_convertToModelingProjectTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_createModelingProjectTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_createProjectTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_createRepresentationFileTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_openProjectTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_removingModelingNatureTask;

    @TranslatableMessage
    public static String ModelingProjectManagerImpl_semanticResourcesAdditionTask;

    @TranslatableMessage
    public static String ModelingProjectWizard_title;

    @TranslatableMessage
    public static String ModelingToggleNatureAction_errorDialogMessage;

    @TranslatableMessage
    public static String ModelingToggleNatureAction_errorDialogTitle;

    @TranslatableMessage
    public static String MoveRepresentationAction_text;

    @TranslatableMessage
    public static String NavigateToCommand_name;

    @TranslatableMessage
    public static String NewSessionWizard_fileCreationTask;

    @TranslatableMessage
    public static String NewSessionWizard_representationFileCreationError;

    @TranslatableMessage
    public static String NewSessionWizard_selectModel;

    @TranslatableMessage
    public static String NewSessionWizard_title;

    @TranslatableMessage
    public static String OpenCloseSessionActionProvider_openCloseAction_close;

    @TranslatableMessage
    public static String OpenCloseSessionActionProvider_openCloseAction_open;

    @TranslatableMessage
    public static String OpenCloseSessionActionProvider_openWithMenu;

    @TranslatableMessage
    public static String OpenRepresentationsAction_name;

    @TranslatableMessage
    public static String OpenRepresentationsAction_openRepresentationsTask;

    @TranslatableMessage
    public static String OpenRepresentationsAction_openRepresentationTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_errorInvalidInputList;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_invalidModelingProjectsError;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_label;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadingModelsTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadingProblem_defaultErrorDetail;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadingProblem_modelingProject;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadingProblem_representationFile;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadingRepresentationFileTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadReferencedModelsTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_loadRepresentationsTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_openingStartRepresentationTask;

    @TranslatableMessage
    public static String OpenRepresentationsFileJob_unexpectedException;

    @TranslatableMessage
    public static String OpenSessionOnExpandListener_expandJob;

    @TranslatableMessage
    public static String OpenViewpointSelectionAction_name;

    @TranslatableMessage
    public static String ProjectDependenciesItemImpl_text;

    @TranslatableMessage
    public static String RefreshLabelImageJob_name;

    @TranslatableMessage
    public static String RemoveRepresentationResourceAction_name;

    @TranslatableMessage
    public static String RemoveRepresentationResourceAction_saveDialogTitle;

    @TranslatableMessage
    public static String RemoveSemanticResourceAction_name;

    @TranslatableMessage
    public static String RenameRepresentationAction_name;

    @TranslatableMessage
    public static String RepresentationFilesRepairAction_repairError;

    @TranslatableMessage
    public static String RepresentationFilesRepairAction_repairFailed;

    @TranslatableMessage
    public static String RepresentationFilesRepairAction_repairInterrupted;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_askContinue;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_askSave;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_confirmationDialogTitle;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_launchImpossibleRepresentationsOpened;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_migrationCanceled;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_repairActionLabel;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_representationFileModified;

    @TranslatableMessage
    public static String RepresentationFilesRepairValidator_representationFilesModified;

    @TranslatableMessage
    public static String RepresentationSelectionWizardPage_errorReadonlyContainer;

    @TranslatableMessage
    public static String RepresentationSelectionWizardPage_message;

    @TranslatableMessage
    public static String RepresentationSelectionWizardPage_title;

    @TranslatableMessage
    public static String RepresentationsSelectionDialog_shellTitle;

    @TranslatableMessage
    public static String RepresentationsSelectionWizardPage_message;

    @TranslatableMessage
    public static String RepresentationsSelectionWizardPage_pageTitle;

    @TranslatableMessage
    public static String RepresentationsSelectionWizardPage_readOnlyContainerError;

    @TranslatableMessage
    public static String RepresentationsSelectionWizardPage_unknownCodeError;

    @TranslatableMessage
    public static String ResourcesFolderItemImpl_text;

    @TranslatableMessage
    public static String RestoreToLastSavePointListener_errorModifiedResourcesReloaded;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_browseButton;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_defaultFileName;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_description;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_existingFileGroup;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_existingFileGroup_button;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_folderExportGroup;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_folderExportGroup_button;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_folderSelectionDialogTitle;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_newFileGroup;

    @TranslatableMessage
    public static String SelectAnalysisFilePage_newFileGroup_button;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_browseButton;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_description;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_noMetamodelType;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_registryLabel;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_statusNoURI;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_statusUnknownURI;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_typeValues;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_uriLabel;

    @TranslatableMessage
    public static String SelectMetamodelWizardPage_workspaceDialogTitle;

    @TranslatableMessage
    public static String SelectRepresentationsWizard_title;

    @TranslatableMessage
    public static String SemanticElementSelectionWizardPage_message;

    @TranslatableMessage
    public static String SemanticElementSelectionWizardPage_title;

    @TranslatableMessage
    public static String SemanticResourceAdditionOperation_semanticResourceAdditionTask;

    @TranslatableMessage
    public static String SemanticResourceDialog_representationFilesCantBeAdded_message;

    @TranslatableMessage
    public static String SemanticResourceDialog_representationFilesCantBeAdded_status;

    @TranslatableMessage
    public static String SemanticResourceDialog_representationFilesCantBeAdded_title;

    @TranslatableMessage
    public static String SessionEditorInput_defaultEditorName;

    @TranslatableMessage
    public static String SessionFileCreationWizardPage_description;

    @TranslatableMessage
    public static String SessionFileCreationWizardPage_errorMessage;

    @TranslatableMessage
    public static String SessionFileCreationWizardPage_noSelectionFileName;

    @TranslatableMessage
    public static String SessionFileCreationWizardPage_title;

    @TranslatableMessage
    public static String SessionHelper_openStartupRepresentationsTask;

    @TranslatableMessage
    public static String SessionHelper_origin;

    @TranslatableMessage
    public static String SessionHelper_selectionDialogMessage;

    @TranslatableMessage
    public static String SessionHelper_selectionDialogMessageWithOrigin;

    @TranslatableMessage
    public static String SessionHelper_selectionDialogTitle;

    @TranslatableMessage
    public static String SessionHelper_startupRepresentationOpeningTask;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_description;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_emptyFile_details;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_emptyFile_label;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_initialization_details;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_initialization_label;

    @TranslatableMessage
    public static String SessionKindSelectionWizardPage_title;

    @TranslatableMessage
    public static String SessionLabelProvider_errorReadingModel;

    @TranslatableMessage
    public static String SessionResourceCreationWizardPage_noSelectionFileName;

    @TranslatableMessage
    public static String SessionResourceCreationWizardPage_wrongFileExtension;

    @TranslatableMessage
    public static String SessionSpecificEditorInput_transformationFailure;

    @TranslatableMessage
    public static String SiriusCellEditorProviderCollector_contributionInstantiationError;

    @TranslatableMessage
    public static String SiriusCommonContentProvider_asyncUpdateJob;

    @TranslatableMessage
    public static String SiriusCommonLabelProvider_eClassDisabled;

    @TranslatableMessage
    public static String SiriusControlHandler_controlTask;

    @TranslatableMessage
    public static String SiriusPreferencePage_autoRefresh;

    @TranslatableMessage
    public static String SiriusPreferencePage_defensiveEditValidation;

    @TranslatableMessage
    public static String SiriusPreferencePage_emptyAirdOnControl;

    @TranslatableMessage
    public static String SiriusPreferencePage_emptyAirdOnControl_help;

    @TranslatableMessage
    public static String SiriusPreferencePage_enableGrouping;

    @TranslatableMessage
    public static String SiriusPreferencePage_enableGrouping_help;

    @TranslatableMessage
    public static String SiriusPreferencePage_filesGroup;

    @TranslatableMessage
    public static String SiriusPreferencePage_groupingGroup;

    @TranslatableMessage
    public static String SiriusPreferencePage_groupingGroupSize;

    @TranslatableMessage
    public static String SiriusPreferencePage_groupingGroupSize_help;

    @TranslatableMessage
    public static String SiriusPreferencePage_groupingTheshold;

    @TranslatableMessage
    public static String SiriusPreferencePage_groupingTheshold_help;

    @TranslatableMessage
    public static String SiriusPreferencePage_profilerGroup;

    @TranslatableMessage
    public static String SiriusPreferencePage_profiling;

    @TranslatableMessage
    public static String SiriusPreferencePage_profiling_help;

    @TranslatableMessage
    public static String SiriusPreferencePage_refreshGroup;

    @TranslatableMessage
    public static String SiriusPreferencePage_refreshOnRepresentationOpening;

    @TranslatableMessage
    public static String SiriusUncontrolHandler_uncontrolRepresentationsMessage;

    @TranslatableMessage
    public static String SiriusUncontrolHandler_uncontrolRepresentationsTitle;

    @TranslatableMessage
    public static String SiriusUncontrolHandler_uncontrolTask;

    @TranslatableMessage
    public static String SmartDialogAnalysisSelector_message;

    @TranslatableMessage
    public static String SmartDialogAnalysisSelector_title;

    @TranslatableMessage
    public static String SpecificEditorInputTranformer_defaultFileName;

    @TranslatableMessage
    public static String SpecificEditorInputTranformer_newRepresentationName;

    @TranslatableMessage
    public static String SpecificEditorInputTranformer_transformationFailure;

    @TranslatableMessage
    public static String TraceabilityMarkerNavigationProvider_dialogMessage;

    @TranslatableMessage
    public static String TraceabilityMarkerNavigationProvider_dialogTitle;

    @TranslatableMessage
    public static String TraceabilityMarkerNavigationProvider_noSessionFoundError;

    @TranslatableMessage
    public static String TreeEditorDialogFactory_error;

    @TranslatableMessage
    public static String TypedVariableValueDialog_title;

    @TranslatableMessage
    public static String UserSession_openRepresentationTask;

    @TranslatableMessage
    public static String UserSession_representationNotFound;

    @TranslatableMessage
    public static String UserSession_viewpointSelectionFailed;

    @TranslatableMessage
    public static String ViewpointItemImpl_notFoundLabel;

    @TranslatableMessage
    public static String ViewpointSelection_applySelectionTask;

    @TranslatableMessage
    public static String ViewpointSelection_iconColumn;

    @TranslatableMessage
    public static String ViewpointSelection_loadingViewpointsTask;

    @TranslatableMessage
    public static String ViewpointSelection_missingDependencies_header;

    @TranslatableMessage
    public static String ViewpointSelection_missingDependencies_requirements;

    @TranslatableMessage
    public static String ViewpointSelection_viewpointColumn;

    @TranslatableMessage
    public static String ViewpointSelection_viewpointsMapNotReused;

    @TranslatableMessage
    public static String ViewpointSelection_wizardTitle;

    @TranslatableMessage
    public static String ViewpointSelectionCallback_deselection;

    @TranslatableMessage
    public static String ViewpointSelectionCallback_selection;

    @TranslatableMessage
    public static String ViewpointSelectionCallbackWithConfimation_viewpointUsedInOpenEditors_message;

    @TranslatableMessage
    public static String ViewpointSelectionCallbackWithConfimation_viewpointUsedInOpenEditors_title;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_description;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_dialogTitle;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_message;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_selectionMessage;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_text;

    @TranslatableMessage
    public static String ViewpointSelectionDialog_title;

    @TranslatableMessage
    public static String ViewpointsFolderItemImpl_representationsPerCategory_title;

    @TranslatableMessage
    public static String ViewpointsSelectionWizardPage_documentation_none;

    @TranslatableMessage
    public static String ViewpointsSelectionWizardPage_documentation_title;

    @TranslatableMessage
    public static String ViewpointsSelectionWizardPage_message;

    @TranslatableMessage
    public static String ViewpointsSelectionWizardPage_title;

    // CHECKSTYLE:ON

    private Messages() {
        // Prevents instanciation.
    }
}
