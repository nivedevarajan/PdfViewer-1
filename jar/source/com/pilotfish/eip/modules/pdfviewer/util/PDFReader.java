package com.pilotfish.eip.modules.pdfviewer.util;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by craigmiller on 2/5/16.
 */
public class PDFReader {

    public static List<BufferedImage> getPageImagesFromDocument(File pdfFile) throws PDFViewerException{
        List<BufferedImage> pageImages = new ArrayList<>();
        PDDocument doc = null;
        try{
            doc = getDocument(pdfFile);
            List<PDPage> docPages = doc.getDocumentCatalog().getAllPages();
            for(PDPage page : docPages){
                pageImages.add(page.convertToImage());
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
                    ex.printStackTrace();
                }
            }
        }

        return pageImages;
    }

    private static PDDocument getDocument(File pdfFile) throws PDFViewerException{
        FileInputStream iStream = null;
        PDDocument doc = null;
        try{
            iStream = new FileInputStream(pdfFile);
            doc = PDDocument.load(iStream);
        }
        catch(IOException ex){
            throw new PDFViewerException("Unable to load PDF Document", ex);
        }
        finally{
            if(iStream != null){
                try{
                    iStream.close();
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        return doc;
    }

}
