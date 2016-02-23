package com.pilotfish.eip.modules.pdfviewer.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by craigmiller on 2/5/16.
 */
public class ImageSizeConverter {

    public static final int PORTRAIT = 101;
    public static final int LANDSCAPE = 102;

    //Convert height to width ratio
    private static final double STANDARD_PORTRAIT_RATIO = 11 / 8.5;

    //Convert height to width ratio
    private static final double STANDARD_LANDSCAPE_RATIO = 8.5 / 11;

    public static Dimension getDisplayDocumentSize(int orientation){
        Dimension displayDocSize = null;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        switch(orientation){
            case PORTRAIT:
                displayDocSize = new Dimension();
                displayDocSize.height = new Double(screenSize.height * 0.8).intValue();
                displayDocSize.width = new Double(displayDocSize.height / STANDARD_PORTRAIT_RATIO).intValue();
                break;
            case LANDSCAPE:
                displayDocSize = new Dimension();
                displayDocSize.height = new Double(screenSize.height * 0.8).intValue();
                displayDocSize.width = new Double(displayDocSize.height / STANDARD_LANDSCAPE_RATIO).intValue();
                break;
        }

        return displayDocSize;
    }

    public static BufferedImage convertImageToDisplaySize(BufferedImage srcImage,
                                                          Dimension displaySize){
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
