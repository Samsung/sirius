/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.tools.api.figure;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.IFigure;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.diagram.BundledImage;
import org.eclipse.sirius.diagram.BundledImageShape;
import org.eclipse.sirius.diagram.internal.queries.BundledImageExtensionQuery;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.diagram.ui.tools.api.color.ColorManager;
import org.eclipse.sirius.viewpoint.RGBValues;
import org.eclipse.swt.graphics.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A {@link BundledImageFigure} is a Figure corresponding to an Image defined in
 * a plugin.
 *
 * @author cbrun
 */
public class BundledImageFigure extends SVGFigure {

    /**
     * The stroke tag in the SVG file.
     */
    private static final String SVG_STROKE = "stroke"; //$NON-NLS-1$

    /**
     * The stroke width tag in the SVG file.
     */
    private static final String SVG_STROKE_WIDTH = "stroke-width"; //$NON-NLS-1$

    /**
     * The fill tag in the SVG file.
     */
    private static final String SVG_FILL = "fill"; //$NON-NLS-1$

    /**
     * The stop-color tag in the SVG file.
     */
    private static final String SVG_STOP_COLOR = "stop-color"; //$NON-NLS-1$

    /**
     * The name of the style attribute in the SVG file.
     */
    private static final String SVG_STYLE_ATTRIBUTE_NAME = "style"; //$NON-NLS-1$

    /**
     * The id of the lighter stop color of the gradient in the SVG file.
     */
    private static final String SVG_STOP_LIGHTER_ID = "stop1"; //$NON-NLS-1$

    /**
     * The id of the main stop color of the gradient in the SVG file.
     */
    private static final String SVG_STOP_MAIN_ID = "stop2"; //$NON-NLS-1$

    /**
     * The id of the gradient element in the SVG file.
     */
    private static final String SVG_GRADIENT_ELEMENT_ID = "elementWithGradient"; //$NON-NLS-1$

    /**
     * The id of the border element in the SVG file.
     */
    private static final String SVG_BORDER_ID = "externalBorder"; //$NON-NLS-1$

    /**
     * The id of the shadow element in the SVG file.
     */
    private static final String SVG_SHADOW_ELEMENT_ID = "shadow"; //$NON-NLS-1$

    /**
     * The id of the border size attribute in a bundle image shape extension
     */
    private static final String BORDER_SIZE_ATTRIBUTE = "borderSizeAttribute"; //$NON-NLS-1$

    /**
     * The id of the border size identifier in a bundle image shape extension
     */
    private static final String BORDER_SIZE_IDENTIFIER = "borderSizeIdentifier"; //$NON-NLS-1$

    /**
     * The id of the border color attribute in a bundle image shape extension
     */
    private static final String BORDER_COLOR_ATTRIBUTE = "borderColorAttribute"; //$NON-NLS-1$

    /**
     * The id of the border color identifier in a bundle image shape extension
     */
    private static final String BORDER_COLOR_IDENTIFIER = "borderColorIdentifier"; //$NON-NLS-1$

    /**
     * The id of the color attribute in a bundle image shape extension
     */
    private static final String COLOR_ATTRIBUTE = "colorAttribute"; //$NON-NLS-1$

    /**
     * The id of the color identifier in a bundle image shape extension
     */
    private static final String COLOR_IDENTIFIER = "colorIdentifier"; //$NON-NLS-1$

    private BundledImageExtensionQuery bundledImageExtensionQuery;

    /**
     * The actual shapeName use to draw the SVG figure
     */
    private String shapeName;

    /**
     * The actual provided shape ID use to draw the SVG figure
     */
    private String shapeID;

    /**
     * The actual border color use to draw the SVG figure
     */
    private String mainBorderColor;

    /**
     * The actual border size use to draw the SVG figure
     */
    private int mainBorderSize;

    /**
     * The actual lighter border color use to draw the shadow of SVG figure
     */
    private String lighterBorderColor;

    /**
     * The actual lighter gradient color use to draw the SVG figure
     */
    private String lighterGradientColor;

    /**
     * The actual main gradient color use to draw the SVG figure
     */
    private String mainGradientColor;

    /**
     * Create the {@link BundledImageFigure} from a {@link BundledImage}
     * instance.
     *
     * @param bundle
     *            {@link BundledImage} specification.
     * @return new Figure.
     */
    public static IFigure createImageFigure(final BundledImage bundle) {
        final BundledImageFigure fig = new BundledImageFigure();
        fig.refreshFigure(bundle);
        return fig;
    }

    @Override
    public void setBorder(Border border) {
        if (BundledImageShape.getByName(shapeName) != null) {
            super.setBorder(null);
        } else {
            super.setBorder(border);
        }
    }

    private boolean updateShape(BundledImage bundledImage) {
        boolean updated = false;
        if (bundledImage != null && bundledImage.getShape() != null) {
            String newShapeName = bundledImage.getShape().getName();
            if (!StringUtil.isEmpty(newShapeName) && (!newShapeName.equals(getShapeName()) || bundledImage.getProvidedShapeID() != null && !bundledImage.getProvidedShapeID().equals(getShapeID()))) {
                if (newShapeName.equals(BundledImageShape.PROVIDED_SHAPE_LITERAL.getName())) {
                    String providedShapeID = bundledImage.getProvidedShapeID();
                    IConfigurationElement extension = getBundledImageExtensionQuery().getExtensionDefiningProvidedShapeID(providedShapeID);
                    if (extension != null) {
                        final String path = bundledImageExtensionQuery.getImagePath(extension);
                        String imageFileURI = "platform:/plugin" + path; //$NON-NLS-1$
                        this.setURI(imageFileURI, false);
                        this.setShapeName(BundledImageShape.PROVIDED_SHAPE_LITERAL.getName());
                        this.setShapeID(providedShapeID);
                    }
                } else {
                    this.setURI(getImageFileURI(newShapeName), false);
                    this.setShapeName(newShapeName);
                }
                updated = true;
            }
        }
        return updated;
    }

    /**
     * @param bundledImage
     * @param force
     *            If the color must be force to refresh (in case of shape update
     *            for example)
     */
    private boolean updateColors(BundledImage bundledImage, boolean force) {
        boolean updated = updateColorFields(bundledImage);
        updated = updateDocumentColors(force || updated, bundledImage);
        return updated;
    }

    /**
     * @param bundledImage
     * @param force
     *            If the border size must be force to refresh (in case of shape
     *            update for sample)
     */
    private boolean updateBorderSize(BundledImage bundledImage, boolean force) {
        boolean updated = updateBorderSizeFields(bundledImage);
        updated = updateDocumentBorderSize(force || updated, bundledImage);
        return updated;
    }

    private boolean updateColorFields(BundledImage bundledImage) {
        // Compute colors
        RGBValues color = bundledImage.getColor();
        Color newLighterColor = ColorManager.getDefault().getLighterColor(color);

        RGBValues borderColor = bundledImage.getBorderColor();
        Color newBorderLighterColor = ColorManager.getDefault().getLighterColor(borderColor);

        // Get Hexa values
        String hexaColor = BundledImageFigure.getRGBValuesColorToHexa(color);
        String hexaLighterColor = getColorToHexa(newLighterColor);
        String hexaBorderColor = BundledImageFigure.getRGBValuesColorToHexa(borderColor);
        String hexaLighterBorderColor = getColorToHexa(newBorderLighterColor);

        boolean updated = false;

        if (hexaColor != null && (!hexaColor.equals(this.getMainGradientColor()))) {
            this.setMainGradientColor(hexaColor);
            updated = true;
        }

        if (hexaLighterColor != null && (!hexaLighterColor.equals(this.getLighterGradientColor()))) {
            this.setLighterGradientColor(hexaLighterColor);
            updated = true;
        }

        if (hexaBorderColor != null && (!hexaBorderColor.equals(this.getMainBorderColor()))) {
            this.setMainBorderColor(hexaBorderColor);
            updated = true;
        }

        if (hexaLighterBorderColor != null && (!hexaLighterBorderColor.equals(this.getLighterBorderColor()))) {
            this.setLighterBorderColor(hexaLighterBorderColor);
            updated = true;
        }

        return updated;
    }

    private boolean updateBorderSizeFields(BundledImage bundledImage) {
        // Compute border size
        int borderSize = bundledImage.getBorderSize();
        boolean updated = false;

        if (borderSize != this.getMainBorderSize()) {
            this.setMainBorderSize(borderSize);
            updated = true;
        }

        return updated;
    }

    private boolean updateDocumentColors(boolean needsUpdate, BundledImage bundledImage) {
        boolean updated = false;
        if (needsUpdate) {
            setURI(getURI(), false);
            Document document = this.getDocument();
            if (document != null && needsUpdate) {
                /* Update the primary color (if exists). */
                Element gradientStep1 = findElementInDocument(bundledImage, document, BundledImageFigure.COLOR_IDENTIFIER, BundledImageFigure.SVG_STOP_LIGHTER_ID);
                if (gradientStep1 != null) {
                    String gradientStep1Style = getAttributeValue(gradientStep1, bundledImage, BundledImageFigure.COLOR_ATTRIBUTE, BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME);
                    gradientStep1.setAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME,
                            BundledImageFigure.getNewStyle(gradientStep1Style, BundledImageFigure.SVG_STOP_COLOR, getLighterGradientColor()));
                    updated = true;
                }

                /* Update the secondary gradient color (if exists). */
                Element gradientStep2 = document.getElementById(BundledImageFigure.SVG_STOP_MAIN_ID);
                if (gradientStep2 != null) {
                    String gradientStep2Style = gradientStep2.getAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME);
                    gradientStep2.setAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME,
                            BundledImageFigure.getNewStyle(gradientStep2Style, BundledImageFigure.SVG_STOP_COLOR, getMainGradientColor()));
                    updated = true;
                }

                /* Update the shadow border (if exists). */
                Element shadow = document.getElementById(BundledImageFigure.SVG_SHADOW_ELEMENT_ID);
                if (shadow != null) {
                    String shadowStyle = shadow.getAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME);
                    shadow.setAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME, BundledImageFigure.getNewStyle(shadowStyle, BundledImageFigure.SVG_FILL, getLighterBorderColor()));
                    updated = true;
                }

                /* Update the border color (if exists). */
                Element elementWithGradient = findElementInDocument(bundledImage, document, BundledImageFigure.BORDER_COLOR_IDENTIFIER,
                        BundledImageFigure.SVG_GRADIENT_ELEMENT_ID);
                if (elementWithGradient != null) {
                    String elementWithGradientStyle = getAttributeValue(elementWithGradient, bundledImage, BundledImageFigure.BORDER_COLOR_ATTRIBUTE,
                            BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME);
                    elementWithGradient.setAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME, getNewStyle(elementWithGradientStyle, BundledImageFigure.SVG_STROKE, getMainBorderColor()));
                    updated = true;
                }
            }
        }
        return updated;
    }

    private boolean updateDocumentBorderSize(boolean needsUpdate, BundledImage bundledImage) {
        boolean updated = false;
        if (needsUpdate) {
            Document document = this.getDocument();
            if (document != null && needsUpdate) {
                /* Update the border size (if exists). */
                Element elementWithGradient = findElementInDocument(bundledImage, document, BundledImageFigure.BORDER_SIZE_IDENTIFIER, BundledImageFigure.SVG_BORDER_ID);
                if (elementWithGradient != null) {
                    String elementWithGradientStyle = getAttributeValue(elementWithGradient, bundledImage, BundledImageFigure.BORDER_SIZE_ATTRIBUTE,
                            BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME);
                    elementWithGradient.setAttribute(BundledImageFigure.SVG_STYLE_ATTRIBUTE_NAME,
                            getNewStyle(elementWithGradientStyle, BundledImageFigure.SVG_STROKE_WIDTH, Integer.toString(getMainBorderSize())));
                    updated = true;
                }
            }
        }
        return updated;
    }

    private Element findElementInDocument(BundledImage bundledImage, Document document, String elementId, String defaultId) {
        Element element = null;
        String findParameterInExtension = getBundledImageExtensionQuery().findParameterInExtension(
                getBundledImageExtensionQuery().getExtensionDefiningProvidedShapeID(bundledImage.getProvidedShapeID()), elementId);
        if (findParameterInExtension != null) {
            element = document.getElementById(findParameterInExtension);
        } else {
            element = document.getElementById(defaultId);
        }
        return element;
    }

    private String getAttributeValue(Element documentElement, BundledImage bundledImage, String attributeId, String defaultId) {
        if (BundledImageShape.PROVIDED_SHAPE_LITERAL.equals(bundledImage.getShape())) {
            return documentElement.getAttribute(getBundledImageExtensionQuery().findParameterInExtension(
                    getBundledImageExtensionQuery().getExtensionDefiningProvidedShapeID(bundledImage.getProvidedShapeID()), attributeId));
        }
        return documentElement.getAttribute(defaultId);
    }

    private BundledImageExtensionQuery getBundledImageExtensionQuery() {
        if (this.bundledImageExtensionQuery == null) {
            this.bundledImageExtensionQuery = new BundledImageExtensionQuery();
        }
        return this.bundledImageExtensionQuery;
    }

    private static String getImageFileURI(String shapeName) {
        return MessageFormat.format("platform:/plugin/{0}/images/{1}.svg", DiagramUIPlugin.getPlugin().getSymbolicName(), shapeName); //$NON-NLS-1$
    }

    /**
     * @param color
     *            The color to transform in hexa value
     * @return The hexa representation of the color.
     */
    private static String getRGBValuesColorToHexa(final RGBValues color) {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()); //$NON-NLS-1$
    }

    /**
     * @param color
     *            The color to transform in hexa value
     * @return The hexa representation of the color.
     */
    private static String getColorToHexa(Color color) {
        return String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()); //$NON-NLS-1$
    }

    private static String getNewStyle(String actualStyle, String colorAttribute, String newColor) {
        int indexOfColorAttribute = actualStyle.indexOf(colorAttribute);
        String newStyle;
        if (BundledImageFigure.SVG_STROKE_WIDTH.equals(colorAttribute)) {
            newStyle = actualStyle.substring(0, indexOfColorAttribute + colorAttribute.length() + 1);
        } else {
            // Colors have an extra '#' as prefix
            newStyle = actualStyle.substring(0, indexOfColorAttribute + colorAttribute.length() + 2);
        }
        newStyle = newStyle.concat(newColor);
        newStyle = newStyle.concat(actualStyle.substring(actualStyle.indexOf(";", indexOfColorAttribute), actualStyle.length())); //$NON-NLS-1$
        return newStyle;
    }

    /**
     * refresh the figure.
     *
     * @param bundledImage
     *            the image associated to the figure
     */
    public void refreshFigure(final BundledImage bundledImage) {
        if (bundledImage != null) {
            boolean updated = this.updateShape(bundledImage);
            updated = this.updateColors(bundledImage, updated) || updated;
            updated = this.updateBorderSize(bundledImage, updated) || updated;
            if (updated) {
                this.contentChanged();
            }
        } else {
            this.setURI(null);
        }
    }

    protected String getShapeName() {
        return shapeName;
    }

    protected void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    protected String getShapeID() {
        return shapeID;
    }

    protected void setShapeID(String shapeID) {
        this.shapeID = shapeID;
    }

    protected String getMainBorderColor() {
        return mainBorderColor;
    }

    protected void setMainBorderColor(String mainBorderColor) {
        this.mainBorderColor = mainBorderColor;
    }

    protected int getMainBorderSize() {
        return mainBorderSize;
    }

    protected void setMainBorderSize(int mainBorderSize) {
        this.mainBorderSize = mainBorderSize;
    }

    protected String getLighterBorderColor() {
        return lighterBorderColor;
    }

    protected void setLighterBorderColor(String lighterBorderColor) {
        this.lighterBorderColor = lighterBorderColor;
    }

    protected String getLighterGradientColor() {
        return lighterGradientColor;
    }

    protected void setLighterGradientColor(String lighterGradientColor) {
        this.lighterGradientColor = lighterGradientColor;
    }

    protected String getMainGradientColor() {
        return mainGradientColor;
    }

    protected void setMainGradientColor(String mainGradientColor) {
        this.mainGradientColor = mainGradientColor;
    }

    /**
     * Compute a key for this BundleImageFigure. This key is used to store in
     * cache the corresponding {@link org.eclipse.swt.graphics.Image}.
     *
     * {@inheritDoc}
     *
     * @return The key corresponding to this BundleImageFigure.
     */
    @Override
    protected String getDocumentKey() {
        StringBuffer result = new StringBuffer();
        result.append(super.getDocumentKey());
        result.append(SVGFigure.SEPARATOR);
        result.append(shapeName);
        result.append(SVGFigure.SEPARATOR);
        result.append(mainBorderColor);
        result.append(SVGFigure.SEPARATOR);
        result.append(mainGradientColor);
        result.append(SEPARATOR);
        result.append(mainBorderSize);
        return result.toString();
    }
}
