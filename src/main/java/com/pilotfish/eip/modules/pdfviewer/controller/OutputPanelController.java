package com.pilotfish.eip.modules.pdfviewer.controller;

import com.pilotfish.eip.modules.pdfviewer.model.Coordinate;
import com.pilotfish.eip.modules.pdfviewer.view.OutputPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.pilotfish.eip.modules.pdfviewer.view.ActionCommands.*;
import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;
import static com.pilotfish.eip.modules.pdfviewer.model.CoordinateModel.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class OutputPanelController extends AbstractController implements ActionListener {

    private Log log = LogFactory.getLog(OutputPanelController.class);

    private static final int X = 201;
    private static final int Y = 202;

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getActionCommand().equals(SELECT_PAGE_COMMAND)){
            selectPage((Component) evt.getSource());
        }
        else if(evt.getActionCommand().equals(COPY_X_COMMAND)){
            copy((Component) evt.getSource(), X);
        }
        else if(evt.getActionCommand().equals(COPY_Y_COMMAND)){
            copy((Component) evt.getSource(), Y);
        }
        else if(evt.getActionCommand().equals(CLEAR_COORDINATE_COMMAND)){
            clear((Component) evt.getSource());
        }
    }

    private void clear(Component source){
        OutputPanel.CoordinatePanel panel = getCoordinatePanel(source);
        if(panel != null){
            int pageNumber = panel.getPageNumber() - 1;

            log.info("Clearing coordinates for page. Page: " + pageNumber);

            getHelper().updateModelProperty(REMOVE_COORDINATE_PROPERTY, pageNumber);
        }
        else{
            log.error("Couldn't find CoordinatePanel to clear");
        }
    }

    private void copy(Component source, int coordinateType){
        OutputPanel.CoordinatePanel panel = getCoordinatePanel(source);
        if(panel != null){
            int pageNumber = panel.getPageNumber() - 1;



            Coordinate coordinate = (Coordinate) getHelper().getModelProperty(COORDINATE_PROPERTY, pageNumber);
            if(coordinate != null){
                String textToCopy = "";
                switch(coordinateType){
                    case X:
                        textToCopy = coordinate.getFormattedX();
                        break;
                    case Y:
                        textToCopy = coordinate.getFormattedY();
                        break;
                }

                String typeString = coordinateType == X ? "X" : "Y";
                log.info("Copying coordinate to clipboard. Page: " + pageNumber + " " + typeString + ": " + textToCopy);

                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(textToCopy), null);
            }
        }
        else{
            log.error("Couldn't find CoordinatePanel to copy from");
        }
    }

    private void selectPage(Component source){
        OutputPanel.CoordinatePanel panel = getCoordinatePanel(source);
        if(panel != null){
            int pageNumber = panel.getPageNumber() - 1;

            log.info("Changing displayed page. New Page: " + pageNumber);

            getHelper().updateModelProperty(PAGE_DISPLAYED_PROPERTY, pageNumber);
        }
        else{
            log.error("Couldn't find CoordinatePanel to display");
        }

    }

    private OutputPanel.CoordinatePanel getCoordinatePanel(Component source){
        Container c = source.getParent();
        if(c == null){
            return null;
        }
        else if(c instanceof OutputPanel.CoordinatePanel){
            return (OutputPanel.CoordinatePanel) c;
        }
        else{
            return getCoordinatePanel(c);
        }
    }
}
