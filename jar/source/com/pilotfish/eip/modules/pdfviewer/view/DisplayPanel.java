package com.pilotfish.eip.modules.pdfviewer.view;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;
import com.pilotfish.eip.modules.pdfviewer.util.Fonts;
import com.pilotfish.eip.modules.pdfviewer.util.ImageSizeConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;

import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class DisplayPanel extends AbstractView {

    private JPanel displayPanel;
    private JLabel displayLabel;
    private JLabel pageNumberLabel;
    private int orientation;
    private Dimension displayDocumentSize;

    public DisplayPanel(PDFViewerHelper helper) {
        super(helper);
        init();
    }

    private void init(){
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        pageNumberLabel = new JLabel();
        pageNumberLabel.setHorizontalAlignment(JLabel.CENTER);
        pageNumberLabel.setFont(Fonts.HEADER_FONT);

        displayLabel = new JLabel();
        displayLabel.addMouseListener(helper.getMouseListener(PDFViewer.DISPLAY_PANEL_CONTROLLER));

        displayPanel.add(displayLabel, BorderLayout.CENTER);
        displayPanel.add(pageNumberLabel, BorderLayout.NORTH);
    }

    public JPanel getDisplayPanel(){
        return displayPanel;
    }

    @Override
    public void updateView(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ORIENTATION_PROPERTY)){
            this.orientation = (Integer) evt.getNewValue();
        }
        else if(evt.getPropertyName().equals(PAGE_DISPLAYED_PROPERTY)){
            IndexedPropertyChangeEvent iEvt = (IndexedPropertyChangeEvent) evt;
            int pageDisplayed = iEvt.getIndex() + 1;
            pageNumberLabel.setText("Page " + pageDisplayed + " Preview");

            BufferedImage srcPageToDisplay = (BufferedImage) iEvt.getNewValue();
            BufferedImage pageToDisplay = ImageSizeConverter.convertImageToDisplaySize(srcPageToDisplay, displayDocumentSize);
            ImageIcon pageImage = new ImageIcon(pageToDisplay);
            displayLabel.setIcon(pageImage);
        }
        else if(evt.getPropertyName().equals(DISPLAY_SIZE_PROPERTY)){
            displayDocumentSize = (Dimension) evt.getNewValue();
            displayDocumentSize = ImageSizeConverter.getDisplayDocumentSize(orientation);
            displayPanel.setSize(displayDocumentSize);
        }
    }
}
