package com.pilotfish.eip.modules.pdfviewer.controller;

import com.pilotfish.eip.modules.pdfviewer.model.Coordinate;
import com.pilotfish.eip.modules.pdfviewer.util.ImageSizeConverter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;
import static com.pilotfish.eip.modules.pdfviewer.model.CoordinateModel.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class DisplayPanelController extends AbstractController implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent evt) {
        //Get raw pixel coordinates
        int x = evt.getX();
        int y = evt.getY();

        //Get the display size from the model
        Dimension displaySize = (Dimension) getHelper().getModelProperty(DISPLAY_SIZE_PROPERTY);

        //Change it so that 0 height is the bottom left corner
        y = displaySize.height - y;

        double xPercent = (double) x / displaySize.width;
        double yPercent = (double) y / displaySize.height;

        int orientation = (Integer) getHelper().getModelProperty(ORIENTATION_PROPERTY);

        double xInches = 0.0;
        double yInches = 0.0;
        if(orientation == ImageSizeConverter.PORTRAIT){
            xInches = 8.5 * xPercent;
            yInches = 11 * yPercent;
        }
        else{
            xInches = 11 * xPercent;
            yInches = 8.5 * yPercent;
        }

        Coordinate coordinate = new Coordinate(xInches, yInches);
        int pageDisplayed = (Integer) getHelper().getModelProperty(PAGE_DISPLAYED_PROPERTY);
        getHelper().updateModelProperty(COORDINATE_PROPERTY, pageDisplayed, coordinate);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }
}
