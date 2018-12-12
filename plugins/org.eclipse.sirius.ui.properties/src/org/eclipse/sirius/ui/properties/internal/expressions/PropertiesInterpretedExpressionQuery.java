/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.properties.internal.expressions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.eef.common.api.utils.Util;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.business.api.dialect.description.AbstractInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.business.api.dialect.description.MultiLanguagesValidator;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.internal.dialect.description.ToolInterpretedExpressionTargetSwitch;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterContext;
import org.eclipse.sirius.common.tools.api.interpreter.TypeName;
import org.eclipse.sirius.common.tools.api.interpreter.ValidationResult;
import org.eclipse.sirius.common.tools.api.interpreter.VariableType;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.properties.DynamicMappingFor;
import org.eclipse.sirius.properties.GroupDescription;
import org.eclipse.sirius.properties.PageDescription;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.tools.api.interpreter.context.SiriusInterpreterContextFactory;
import org.eclipse.sirius.ui.properties.internal.SiriusInputDescriptor;
import org.eclipse.sirius.ui.properties.internal.SiriusToolServices;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Extension;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.tool.InitialOperation;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * An {@code IInterpretedExpressionQuery} for expressions occuring inside
 * properties view descriptions.
 *
 * @author pcdavid
 */
public final class PropertiesInterpretedExpressionQuery extends AbstractInterpretedExpressionQuery implements IInterpretedExpressionQuery {
    private Collection<EPackage> packagesToImport;

    /**
     * Constructor.
     *
     * @param target
     *            the VSM element on which the expression appears.
     * @param expressionAttribute
     *            the attribute of the VSM element which defines the expression
     *            (assumed to be an InterpredExpression).
     */
    public PropertiesInterpretedExpressionQuery(EObject target, EStructuralFeature expressionAttribute) {
        super(target, expressionAttribute);
    }

    @Override
    protected void initializeTargetSwitch() {
        this.targetSwitch = new PropertiesExpressionsGlobalTargetSwitch(feature);
    }

    @Override
    public Collection<EPackage> getPackagesToImport() {
        /*
         * We can't rely on the default implementation here, as it assumes we
         * are inside a RepresentationDescription, which is not the case for
         * properties definitions.
         */
        if (packagesToImport == null) {
            packagesToImport = PropertiesInterpretedExpressionQuery.getEPackagesInScope(target);
        }
        return packagesToImport;
    }

    @Override
    public Collection<String> getDependencies() {
        /*
         * We can't rely on the default implementation here, as it assumes we
         * are inside a Viewpoint, which is not the case for properties
         * definitions.
         */
        if (dependencies == null) {
            Collection<String> result = Lists.newArrayList(VSMNavigation.getJavaExtensionsInVSM(target));
            // Make sure the implicitly registered SiriusToolServices class is
            // also visible.
            result.add(SiriusToolServices.class.getName());
            dependencies = result;
        }
        return dependencies;
    }

    private static Collection<EPackage> getEPackagesInScope(EObject target) {
        Collection<EPackage> result = Sets.newLinkedHashSet();

        boolean needsGlobalPackages = false;
        for (RepresentationDescription desc : VSMNavigation.getRepresentationDescriptionsInVSM(target)) {
            EList<EPackage> configured = desc.getMetamodel();
            result.addAll(configured);
            if (configured.isEmpty()) {
                /*
                 * If at least one of the possible source representations has no
                 * explicitly configured metamodel, we must include the globally
                 * registered packages.
                 */
                needsGlobalPackages = true;
            }
        }

        if (needsGlobalPackages) {
            result.addAll(PropertiesInterpretedExpressionQuery.getAllRegisteredEPackages(EPackage.Registry.INSTANCE));
        }

        // Also add metamodels explicitly added to the ViewExtensionDescription,
        // if any.
        Option<EObject> viewDescriptionOpt = new EObjectQuery(target).getFirstAncestorOfType(PropertiesPackage.Literals.VIEW_EXTENSION_DESCRIPTION);
        if (viewDescriptionOpt.some()) {
            ViewExtensionDescription ved = (ViewExtensionDescription) viewDescriptionOpt.get();
            result.addAll(ved.getMetamodels());
        }

        // In all cases, we make sure the core Sirius metamodels are present...
        result.add(EcorePackage.eINSTANCE);
        result.add(ViewpointPackage.eINSTANCE);
        result.add(DescriptionPackage.eINSTANCE);
        result.add(ToolPackage.eINSTANCE);
        result.add(ValidationPackage.eINSTANCE);
        // ... and the properties view one too.
        result.add(PropertiesPackage.eINSTANCE);

        return result;
    }

    private static Collection<EPackage> getAllRegisteredEPackages(Registry source) {
        Collection<EPackage> result = Sets.newLinkedHashSet();
        for (String nsURI : Sets.newLinkedHashSet(source.keySet())) {
            try {
                result.add(source.getEPackage(nsURI));
                // CHECKSTYLE:OFF
            } catch (Throwable e) {
                /*
                 * anything might happen here depending on the other Eclipse
                 * tools, and we've seen many time tools breaking all the
                 * others.
                 */
                // CHECKSTYLE:ON
            }
        }
        return result;
    }

    @Override
    public Map<String, VariableType> getAvailableVariables() {
        if (availableVariables == null) {
            availableVariables = Maps.newLinkedHashMap();
        }
        boolean isPropertiesElement = target.eClass().getEPackage() == PropertiesPackage.eINSTANCE;
        if (isPropertiesElement) {
            // "input" is always available.
            availableVariables.put(EEFExpressionUtils.INPUT, VariableType.fromString(SiriusInputDescriptor.class.getName()));

            if (feature == PropertiesPackage.Literals.SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION) {
                VariableType candidatesExpressionType = getResultType(target, PropertiesPackage.Literals.SELECT_DESCRIPTION__CANDIDATES_EXPRESSION);
                availableVariables.put(EEFExpressionUtils.EEFSelect.CANDIDATE, candidatesExpressionType);
            } else if (feature == PropertiesPackage.Literals.RADIO_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION) {
                VariableType candidatesExpressionType = getResultType(target, PropertiesPackage.Literals.RADIO_DESCRIPTION__CANDIDATES_EXPRESSION);
                availableVariables.put(EEFExpressionUtils.EEFSelect.CANDIDATE, candidatesExpressionType);
            } else if (feature == PropertiesPackage.Literals.LABEL_DESCRIPTION__DISPLAY_EXPRESSION) {
                VariableType candidatesExpressionType = getResultType(target, PropertiesPackage.Literals.LABEL_DESCRIPTION__VALUE_EXPRESSION);
                availableVariables.put(EEFExpressionUtils.EEFReference.VALUE, candidatesExpressionType);
            } else if (feature == PropertiesPackage.Literals.LIST_DESCRIPTION__DISPLAY_EXPRESSION) {
                VariableType candidatesExpressionType = getResultType(target, PropertiesPackage.Literals.LIST_DESCRIPTION__VALUE_EXPRESSION);
                availableVariables.put(EEFExpressionUtils.EEFList.VALUE, candidatesExpressionType);
            } else if (feature == PropertiesPackage.Literals.HYPERLINK_DESCRIPTION__DISPLAY_EXPRESSION) {
                VariableType candidatesExpressionType = getResultType(target, PropertiesPackage.Literals.HYPERLINK_DESCRIPTION__VALUE_EXPRESSION);
                availableVariables.put(EEFExpressionUtils.EEFReference.VALUE, candidatesExpressionType);
            } else if (feature == PropertiesPackage.Literals.DYNAMIC_MAPPING_IF__PREDICATE_EXPRESSION) {
                if (target.eContainer() instanceof DynamicMappingFor) {
                    DynamicMappingFor forDefinition = (DynamicMappingFor) target.eContainer();
                    String iteratorName = forDefinition.getIterator();
                    if (!Util.isBlank(iteratorName)) {
                        VariableType iteratorType = getResultType(forDefinition, PropertiesPackage.Literals.DYNAMIC_MAPPING_FOR__ITERABLE_EXPRESSION);
                        availableVariables.put(iteratorName, iteratorType);
                    }
                }
            }
            return availableVariables;
        } else {
            return super.getAvailableVariables();
        }
    }

    @Override
    protected Option<EObject> getToolContext() {
        Option<EObject> result = super.getToolContext();
        if (!result.some()) {
            if (target instanceof PageDescription || target instanceof GroupDescription) {
                result = Options.newSome(target);
            } else {
                result = new EObjectQuery(target).getFirstAncestorOfType(ToolPackage.Literals.INITIAL_OPERATION);
            }
        }
        return result;
    }

    @Override
    protected void addVariablesFromToolContext(EObject toolContext) {
        super.addVariablesFromToolContext(toolContext);
        availableVariables.put(EEFExpressionUtils.INPUT, VariableType.fromString(SiriusInputDescriptor.class.getName()));
        if (toolContext instanceof InitialOperation) {
            EReference callbackFeature = toolContext.eContainmentFeature();
            VariableType stringType = VariableType.fromString("java.lang.String"); //$NON-NLS-1$
            VariableType booleanType = VariableType.fromString("java.lang.Boolean"); //$NON-NLS-1$
            VariableType unkownType = VariableType.fromString(TypeName.EOBJECT_TYPENAME.getCompleteName());
            if (callbackFeature == PropertiesPackage.Literals.TEXT_DESCRIPTION__INITIAL_OPERATION) {
                availableVariables.put(EEFExpressionUtils.EEFText.NEW_VALUE, stringType);
            } else if (callbackFeature == PropertiesPackage.Literals.CHECKBOX_DESCRIPTION__INITIAL_OPERATION) {
                availableVariables.put(EEFExpressionUtils.EEFCheckbox.NEW_VALUE, booleanType);
            } else if (callbackFeature == PropertiesPackage.Literals.HYPERLINK_DESCRIPTION__INITIAL_OPERATION) {
                Option<Collection<String>> domainClass = VSMNavigation.getDomainClassFromContainingGroup(toolContext);
                if (domainClass.some()) {
                    availableVariables.put(EEFExpressionUtils.EEFHyperlink.SELECTION, VariableType.fromStrings(domainClass.get()));
                } else {
                    availableVariables.put(EEFExpressionUtils.EEFHyperlink.SELECTION, unkownType);
                }
            } else if (callbackFeature == PropertiesPackage.Literals.RADIO_DESCRIPTION__INITIAL_OPERATION) {
                availableVariables.put(EEFExpressionUtils.EEFText.NEW_VALUE, stringType);
            } else if (callbackFeature == PropertiesPackage.Literals.WIDGET_ACTION__INITIAL_OPERATION) {
                Option<Collection<String>> domainClass = VSMNavigation.getDomainClassFromContainingGroup(toolContext);
                if (!domainClass.some()) {
                    availableVariables.put(EEFExpressionUtils.EEFHyperlink.SELECTION, VariableType.fromStrings(domainClass.get()));
                } else {
                    availableVariables.put(EEFExpressionUtils.EEFHyperlink.SELECTION, unkownType);
                }
            } else if (callbackFeature == PropertiesPackage.Literals.SELECT_DESCRIPTION__INITIAL_OPERATION) {
                availableVariables.put(EEFExpressionUtils.EEFText.NEW_VALUE, stringType);
            }
        }
    }

    private VariableType getResultType(EObject owner, EAttribute attr) {
        IInterpreterContext context = SiriusInterpreterContextFactory.createInterpreterContext(owner, attr);
        ValidationResult res = MultiLanguagesValidator.getInstance().validateExpression(context, (String) owner.eGet(attr));
        return res.getReturnTypes();
    }

    /**
     * The switch used to compute domainClasses for expressions used in
     * properties definitions.
     *
     * @author pcdavid
     */
    private static class PropertiesExpressionsGlobalTargetSwitch implements IInterpretedExpressionTargetSwitch {
        /**
         * The switch for properties-specific expressions.
         */
        private final DomainClassSwitch propertiesSwitch;

        /**
         * The switch we delegate to for Model Operations.
         */
        private final ToolInterpretedExpressionTargetSwitch delegateSwitch;

        /**
         * By default ToolInterpretedExpressionTargetSwitch assumes operations
         * will appear inside representations or mappings, so we override
         * getFirstContextChangingContainer() to locate the parent
         * GroupDescription instead.
         */
        private static class CustomToolInterpretedExpressionTargetSwitch extends ToolInterpretedExpressionTargetSwitch {
            public CustomToolInterpretedExpressionTargetSwitch(EStructuralFeature feature, IInterpretedExpressionTargetSwitch defaultSwitch) {
                super(feature, defaultSwitch);
            }

            @Override
            protected EObject getFirstContextChangingContainer(EObject element) {
                EObject defaultResult = super.getFirstContextChangingContainer(element);
                if (defaultResult instanceof Extension) {
                    /*
                     * The generic algorithm in the super-class does not know
                     * anything about the properties metamodel but will stop at
                     * the top-level ViewExtensionDescription as it is an
                     * Extension.
                     */
                    return VSMNavigation.findClosestGroupDescription(element);
                } else {
                    return defaultResult;
                }
            }
        }

        public PropertiesExpressionsGlobalTargetSwitch(EStructuralFeature feature) {
            this.propertiesSwitch = new DomainClassSwitch(feature);
            this.delegateSwitch = new CustomToolInterpretedExpressionTargetSwitch(feature, this);
        }

        @Override
        public Option<Collection<String>> doSwitch(EObject target, boolean considerFeature) {
            Collection<String> targetTypes = Sets.newLinkedHashSet();
            Option<Collection<String>> expressionTarget = Options.newSome(targetTypes);
            if (target != null) {
                if (expressionTarget.some() && expressionTarget.get().isEmpty()) {
                    propertiesSwitch.setConsiderFeature(considerFeature);
                    expressionTarget = propertiesSwitch.doSwitch(target);
                }
                if (expressionTarget.some() && expressionTarget.get().isEmpty()) {
                    delegateSwitch.setConsiderFeature(considerFeature);
                    expressionTarget = delegateSwitch.doSwitch(target);
                }
            }
            return expressionTarget;
        }

        @Override
        public EObject getFirstRelevantContainer(EObject obj) {
            if (obj != null) {
                EObject container = obj.eContainer();
                while (container != null && !isRelevant(container)) {
                    container = container.eContainer();
                }
                return container;
            } else {
                return null;
            }
        }

        /**
         * In this context, relevant containers are top-level pages and groups
         * only. The root ViewExtensionDescriptions do not define anything
         * relevant for domain class computation.
         */
        private boolean isRelevant(EObject container) {
            return container instanceof PageDescription || container instanceof GroupDescription;
        }
    }
}
