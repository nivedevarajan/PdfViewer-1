package com.pilotfish.eip.modules.pdfviewer;

import com.pilotfish.eip.modules.pdfviewer.controller.AbstractController;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The Helper is a convenience class to link the parts of this application together.
 * It contains callback methods to the main controller (PDFViewer), which has
 * all views and models stored in collections. The Helper's main purpose
 * is to provide controllers to the views and allow controllers to
 * abstractly call back into the models.
 *
 * Created by craigmiller on 2/5/16.
 */
public class PDFViewerHelper {

    private Map<String,AbstractController> controllerMap;
    private PDFViewer mainController;

    public PDFViewerHelper(PDFViewer mainController){
        controllerMap = new HashMap<>();
        this.mainController = mainController;
    }

    public void addController(String key, AbstractController controller){
        controller.setHelper(this);
        controllerMap.put(key, controller);
    }

    public void removeController(String key){
        controllerMap.remove(key);
    }

    public ActionListener getActionListener(String key){
        AbstractController controller = controllerMap.get(key);
        if(controller == null || !(controller instanceof ActionListener)){
            throw new IllegalArgumentException("Requested controller is not of type ActionListener: " + key);
        }
        return (ActionListener) controller;
    }

    public MouseListener getMouseListener(String key){
        AbstractController controller = controllerMap.get(key);
        if(controller == null || !(controller instanceof MouseListener)){
            throw new IllegalArgumentException("Requested controller is not of type MouseListener: " + key);
        }
        return (MouseListener) controller;
    }

    public void updateModelProperty(String propName, Object... values){
        mainController.updateModelProperty(propName, values);
    }

    public Object getModelProperty(String propName, Object... params){
        return mainController.getModelProperty(propName, params);
    }

}
