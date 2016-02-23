package com.pilotfish.eip.modules.pdfviewer.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by craigmiller on 2/5/16.
 */
public class ImageSizeConverter {

    private static Log log = LogFactory.getLog(ImageSizeConverter.class);

    public static final int PORTRAIT = 101;
    public static final int LANDSCAPE = 102;

    //Convert height to width ratio
    private static final double STANDARD_PORTRAIT_RATIO = 11 / 8.5;

    //Convert height to width ratio
    private static final double STANDARD_LANDSCAPE_RATIO = 8.5 / 11;

    public static Dimension getDisplayDocumentSize(int orientation){
        Dimension displayDocSize = new Dimension();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        String orientationString = orientation == PORTRAIT ? "Portrait" : "Landscape";
        log.debug("Calculating display size. Screen Height: " + screenSize.height +
                " Screen Width: " + screenSize.width + " Orientation: " + orientationString);

        switch(orientation){
            case PORTRAIT:
                displayDocSize.height = new Double(screenSize.height * 0.8).intValue();
                displayDocSize.width = new Double(displayDocSize.height / STANDARD_PORTRAIT_RATIO).intValue();
                break;
            case LANDSCAPE:
                displayDocSize.height = new Double(screenSize.height * 0.8).intValue();
                displayDocSize.width = new Double(displayDocSize.height / STANDARD_LANDSCAPE_RATIO).intValue();
                break;
        }

        log.debug("Calculated Display Doc Size: Height: " + displayDocSize.height + " Width: " + displayDocSize.width);

        return displayDocSize;
    }

    public static BufferedImage convertImageToDisplaySize(BufferedImage srcImage,
                                                          Dimension displaySize){
        log.debug("Converting image to display size.");

        BufferedImage resultImage = new BufferedImage(displaySize.width, displaySize.height, srcImage.getType());
        Graphics2D g = resultImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(srcImage, 0, 0, displaySize.width, displaySize.height, 0, 0,
                srcImage.getWidth(), srcImage.getHeight(), null);
        g.dispose();

        return resultImage;
    }

}
