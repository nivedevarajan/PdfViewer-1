package com.pilotfish.eip.modules.pdfviewer.util;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by craigmiller on 2/5/16.
 */
public class PDFReader {

    private static Log log = LogFactory.getLog(PDFReader.class);

    public static List<BufferedImage> getPageImagesFromDocument(File pdfFile) throws PDFViewerException{
        log.debug("Retrieving page images from document");

        List<BufferedImage> pageImages = new ArrayList<>();
        PDDocument doc = null;
        try{

            doc = getDocument(pdfFile);
            int numPages = doc.getNumberOfPages();

            log.debug("Converting pages to images. # Pages: " + numPages);

            PDFRenderer renderer = new PDFRenderer(doc);
            for(int i = 0; i < numPages; i++){
                pageImages.add(renderer.renderImage(i, 1, ImageType.RGB));
            }
        }
        catch(IOException ex){
            throw new PDFViewerException("Unable to create page images from Document", ex);
        }
        finally{
            if(doc != null){
                try{
                    doc.close();
                }
                catch(IOException ex){
                    log.error("Unable to close PDF document. " + ex.getMessage(), ex);
                }
            }
        }

        return pageImages;
    }

    private static PDDocument getDocument(File pdfFile) throws PDFViewerException{
        log.trace("Loading document from file. File: " + pdfFile.toString());

        PDDocument doc = null;
        try{
            doc = PDDocument.load(pdfFile);
        }
        catch(IOException ex){
            throw new PDFViewerException("Unable to load PDF Document", ex);
        }
        return doc;
    }

}
