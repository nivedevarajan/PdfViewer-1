package com.pilotfish.eip.modules.pdfviewer;

import com.pilotfish.eip.modules.pdfviewer.controller.DisplayPanelController;
import com.pilotfish.eip.modules.pdfviewer.controller.MenuBarController;
import com.pilotfish.eip.modules.pdfviewer.controller.OutputPanelController;
import com.pilotfish.eip.modules.pdfviewer.model.AbstractModel;
import com.pilotfish.eip.modules.pdfviewer.model.CoordinateModel;
import com.pilotfish.eip.modules.pdfviewer.model.PDFModel;
import com.pilotfish.eip.modules.pdfviewer.util.ImageSizeConverter;
import com.pilotfish.eip.modules.pdfviewer.util.PDFReader;
import com.pilotfish.eip.modules.pdfviewer.view.AbstractView;
import com.pilotfish.eip.modules.pdfviewer.view.DisplayPanel;
import com.pilotfish.eip.modules.pdfviewer.util.PDFRetriever;
import com.pilotfish.eip.modules.pdfviewer.view.MenuBar;
import com.pilotfish.eip.modules.pdfviewer.view.OutputPanel;
import com.pilotfish.eip.modules.pdfviewer.view.ViewerFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;

/**
 * The main class of this application
 *
 * Created by craigmiller on 2/5/16.
 */
public class PDFViewer implements PropertyChangeListener{

    private Log log = LogFactory.getLog(PDFViewer.class);

    public static final String MENU_BAR_CONTROLLER = "MenuBarController";
    public static final String DISPLAY_PANEL_CONTROLLER = "DisplayPanelController";
    public static final String OUTPUT_PANEL_CONTROLLER = "OutputPanelController";

    private PDFViewerHelper helper;
    private List<AbstractModel> registeredModels;
    private List<AbstractView> registeredViews;
    private ViewerFrame viewerFrame;

    public PDFViewer(){
        this.helper = new PDFViewerHelper(this);
        registeredModels = new ArrayList<>();
        registeredViews = new ArrayList<>();
        init();
    }

    /**
     * Start the application.
     */
    public void startApplication(){
        log.info("Starting application");
        initView();
        while(true){
            File pdfFile = PDFRetriever.showChooserGetPDF();
            if(pdfFile != null){
                updateModelProperty(PDF_FILE_PROPERTY, pdfFile);
                break;
            }
            else{
                int result = JOptionPane.showConfirmDialog(null, "No file selected, do you want to try again?",
                        "No File", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(result != JOptionPane.YES_OPTION){
                    shutdown();
                }
            }
        }
    }

    /**
     * Shutdown the application. This method only gets called if
     * the initial setup process is cancelled.
     */
    private void shutdown(){
        JOptionPane.showMessageDialog(null, "Shutting down program",
                "Shutting Down", JOptionPane.WARNING_MESSAGE);
        log.info("PDFViewer configuration cancelled");
        System.exit(1);
    }

    /**
     * Load the application for the specified file
     * to be displayed.
     *
     * @param pdfFile the pdf file to display.
     */
    private void loadApplicationForFile(File pdfFile){
        try{
            int orientation = -1;
            while(true){
                orientation = PDFRetriever.showDialogGetOrientation();
                if(orientation > 0){
                    updateModelProperty(ORIENTATION_PROPERTY, orientation);
                    break;
                }
                else{
                    int result = JOptionPane.showConfirmDialog(null, "No orientation selected, do you want to try again?",
                            "No Orientation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(result != JOptionPane.YES_OPTION){
                        shutdown();
                    }
                }
            }

            Dimension displaySize = ImageSizeConverter.getDisplayDocumentSize(orientation);
            updateModelProperty(DISPLAY_SIZE_PROPERTY, displaySize);

            List<BufferedImage> pageImages = PDFReader.getPageImagesFromDocument(pdfFile);
            if(pageImages != null && pageImages.size() > 0){
                updateModelProperty(PAGE_IMAGES_PROPERTY, pageImages);
            }

            updateModelProperty(PAGE_DISPLAYED_PROPERTY, 0);
        }
        catch(PDFViewerException ex){
            log.error("Unable to load PDF page images. " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null, "Unable to load PDF: " + ex.getMessage() + "\nPlease try again from the File menu",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initialize models and controllers for the application.
     */
    private void init(){
        CoordinateModel coordinateModel = new CoordinateModel();
        coordinateModel.addPropertyChangeListener(this);
        registeredModels.add(coordinateModel);

        PDFModel pdfModel = new PDFModel();
        pdfModel.addPropertyChangeListener(this);
        registeredModels.add(pdfModel);

        helper.addController(MENU_BAR_CONTROLLER, new MenuBarController());
        helper.addController(DISPLAY_PANEL_CONTROLLER, new DisplayPanelController());
        helper.addController(OUTPUT_PANEL_CONTROLLER, new OutputPanelController());

    }

    /**
     * Initialize the views for the application.
     */
    private void initView(){
        viewerFrame = new ViewerFrame(helper);
        MenuBar menuBar = new MenuBar(helper);
        DisplayPanel displayPanel = new DisplayPanel(helper);
        OutputPanel outputPanel = new OutputPanel(helper);

        registeredViews.add(viewerFrame);
        registeredViews.add(menuBar);
        registeredViews.add(displayPanel);
        registeredViews.add(outputPanel);

        viewerFrame.setMenuBar(menuBar.getMenuBar());
        viewerFrame.setDisplayPanel(displayPanel.getDisplayPanel());
        viewerFrame.setOutputPanel(outputPanel.getOutputPanel());
    }

    /**
     * Make the view visible. It can also be used to refresh
     * the view's contents by re-assembling the frame.
     */
    private void showView(){
        log.info("Displaying main view");
        viewerFrame.assembleAndShowFrame();
    }

    /**
     * Convenience method to help update model properties. This
     * method is a part of a chain linked through the Helper.
     *
     * @param propName the name of the model property to update.
     * @param values the values to set to the model property.
     */
    public void updateModelProperty(String propName, Object... values){
        for(AbstractModel model : registeredModels){
            model.updateProperty(propName, values);
        }
    }

    /**
     * Get the Helper for this application.
     *
     * @return the Helper for this application.
     */
    public PDFViewerHelper getHelper(){
        return helper;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for(AbstractView view : registeredViews){
            view.updateView(evt);
        }
        if(evt.getPropertyName().equals(PDF_FILE_PROPERTY)){
            File pdfFile = (File) evt.getNewValue();
            loadApplicationForFile(pdfFile);
            showView();
        }
    }

    /**
     * Convenience method to get the value of a model property.
     *
     * @param propName the name of the model property to get.
     * @param params the parameters, if any, to retrieve the model property.
     * @return the value of the property.
     */
    public Object getModelProperty(String propName, Object...params){
        for(AbstractModel model : registeredModels){
            Object o = model.getProperty(propName, params);
            if(o != null){
                return o;
            }
        }
        return null;
    }
}
