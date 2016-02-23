package com.pilotfish.eip.modules.pdfviewer.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by craigmiller on 2/5/16.
 */
public class PDFModel extends AbstractModel{

    public static final String PDF_FILE_PROPERTY = "PdfFile";
    public static final String PAGE_DISPLAYED_PROPERTY = "PageDisplayed";
    public static final String PAGE_IMAGES_PROPERTY = "PageImages";
    public static final String ORIENTATION_PROPERTY = "Orientation";
    public static final String DISPLAY_SIZE_PROPERTY = "DisplaySize";
    public static final String PAGE_COUNT_PROPERTY = "PageCount";

    private List<BufferedImage> pageImages;
    private File pdfFile;
    private int orientation;
    private Dimension displaySize;
    private int pageDisplayed;

    public void setPdfFile(File pdfFile){
        this.pdfFile = pdfFile;
        firePropertyChangeEvent(PDF_FILE_PROPERTY, null, pdfFile);
    }

    public File getPdfFile(){
        return pdfFile;
    }

    public void setOrientation(int orientation){
        this.orientation = orientation;
        firePropertyChangeEvent(ORIENTATION_PROPERTY, null, orientation);
    }

    public int getOrientation(){
        return orientation;
    }

    public void setPageImages(List<BufferedImage> pageImages){
        this.pageImages = pageImages;
        firePropertyChangeEvent(PAGE_COUNT_PROPERTY, null, getPageCount());
    }

    public List<BufferedImage> getPageImages(){
        return pageImages;
    }

    public int getPageCount(){
        return pageImages.size();
    }

    public void setPageDisplayed(int pageNumber){
        this.pageDisplayed = pageNumber;
        BufferedImage img = pageImages.get(pageNumber);
        fireIndexedPropertyChangeEvent(PAGE_DISPLAYED_PROPERTY, pageDisplayed, null, img);
    }

    public int getPageDisplayed(){
        return pageDisplayed;
    }

    public void setDisplaySize(Dimension displaySize){
        this.displaySize = displaySize;
        firePropertyChangeEvent(DISPLAY_SIZE_PROPERTY, null, displaySize);
    }

    public Dimension getDisplaySize(){
        return displaySize;
    }

    @Override
    public void updateProperty(String propName, Object... values) {
        if(propName.equals(PDF_FILE_PROPERTY) && values.length == 1){
            validatePropertyValues(propName, values);
            setPdfFile((File) values[0]);
        }
        else if(propName.equals(PAGE_DISPLAYED_PROPERTY) && values.length == 1){
            validatePropertyValues(propName, values);
            setPageDisplayed((Integer) values[0]);
        }
        else if(propName.equals(PAGE_IMAGES_PROPERTY) && values.length == 1){
            validatePropertyValues(propName, values);
            setPageImages((List<BufferedImage>) values[0]);
        }
        else if(propName.equals(ORIENTATION_PROPERTY) && values.length == 1){
            validatePropertyValues(propName, values);
            setOrientation((Integer) values[0]);
        }
        else if(propName.equals(DISPLAY_SIZE_PROPERTY) && values.length == 1){
            validatePropertyValues(propName, values);
            setDisplaySize((Dimension) values[0]);
        }
    }

    @Override
    public Object getProperty(String propName, Object... params){
        if(propName.equals(ORIENTATION_PROPERTY)){
            return getOrientation();
        }
        else if(propName.equals(DISPLAY_SIZE_PROPERTY)){
            return getDisplaySize();
        }
        else if(propName.equals(PAGE_DISPLAYED_PROPERTY)){
            return getPageDisplayed();
        }
        else if(propName.equals(PAGE_COUNT_PROPERTY)){
            return getPageCount();
        }

        return null;
    }

    private void validatePropertyValues(String propName, Object... values){
        if(propName.equals(PDF_FILE_PROPERTY)){
            if(!(values[0] instanceof File)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
        else if(propName.equals(PAGE_DISPLAYED_PROPERTY)){
            if(!(values[0] instanceof Integer)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
        else if(propName.equals(PAGE_IMAGES_PROPERTY)){
            if(!(values[0] instanceof List)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
        else if(propName.equals(ORIENTATION_PROPERTY)){
            if(!(values[0] instanceof Integer)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
        else if(propName.equals(DISPLAY_SIZE_PROPERTY)){
            if(!(values[0] instanceof Dimension)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
    }
}
