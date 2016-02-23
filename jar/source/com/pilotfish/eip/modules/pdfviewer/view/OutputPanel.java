package com.pilotfish.eip.modules.pdfviewer.view;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;
import com.pilotfish.eip.modules.pdfviewer.model.Coordinate;
import com.pilotfish.eip.modules.pdfviewer.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;
import static com.pilotfish.eip.modules.pdfviewer.view.ActionCommands.*;
import static com.pilotfish.eip.modules.pdfviewer.model.CoordinateModel.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class OutputPanel extends AbstractView{

    private JPanel outputPanel;
    private JScrollPane scrollPane;
    private List<CoordinatePanel> pageCoordinatePanels = new ArrayList<>();
    private ButtonGroup pageButtonGroup;
    private JPanel coordinatesPanel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;

    private String description =
            "Bottom Left Corner = (0,0)";

    public OutputPanel(PDFViewerHelper helper) {
        super(helper);
        init();
    }

    private void init(){
        outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel("Pages & Coordinates");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(Fonts.HEADER_FONT);

        descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(Fonts.LABEL_FONT);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);

        //pageCount = (Integer) getHelper().getModelProperty(PAGE_COUNT_PROPERTY);
        //generatePageCoordinatePanels(pageCount);

        coordinatesPanel = new JPanel();
        coordinatesPanel.setLayout(new BoxLayout(coordinatesPanel, BoxLayout.Y_AXIS));
        //addCoordinatePanels(coordinatesPanel);

        JPanel titlePanel = new JPanel();
        //titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(descriptionLabel, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(coordinatesPanel);
        outputPanel.add(titlePanel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        outputPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
    }

    private void addCoordinatePanels(){
        coordinatesPanel.removeAll();
        for(JPanel panel : pageCoordinatePanels){
            coordinatesPanel.add(panel);
        }
        coordinatesPanel.revalidate();
        coordinatesPanel.repaint();
    }

    private void generatePageCoordinatePanels(int pageCount){
        pageCoordinatePanels.clear();
        pageButtonGroup = new ButtonGroup();
        for(int i = 0; i < pageCount; i++){
            CoordinatePanel coordinatePanel = new CoordinatePanel(i + 1);
            pageButtonGroup.add(coordinatePanel.getPageRadioButton());
            coordinatePanel.getPageRadioButton().addActionListener(helper.getActionListener(PDFViewer.OUTPUT_PANEL_CONTROLLER));
            if(i == 0){
                coordinatePanel.getPageRadioButton().setSelected(true);
            }

            coordinatePanel.getXCopyButton().addActionListener(helper.getActionListener(PDFViewer.OUTPUT_PANEL_CONTROLLER));
            coordinatePanel.getYCopyButton().addActionListener(helper.getActionListener(PDFViewer.OUTPUT_PANEL_CONTROLLER));
            coordinatePanel.getClearButton().addActionListener(helper.getActionListener(PDFViewer.OUTPUT_PANEL_CONTROLLER));

            pageCoordinatePanels.add(coordinatePanel);
        }
    }

    public JPanel getOutputPanel(){
        return outputPanel;
    }

    @Override
    public void updateView(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(PAGE_COUNT_PROPERTY)){
            int pageCount = (Integer) evt.getNewValue();
            generatePageCoordinatePanels(pageCount);
            addCoordinatePanels();
        }
        else if(evt.getPropertyName().equals(COORDINATE_PROPERTY)){
            IndexedPropertyChangeEvent iEvt = (IndexedPropertyChangeEvent) evt;
            int pageNumber = iEvt.getIndex();
            Coordinate coordinate = (Coordinate) iEvt.getNewValue();
            CoordinatePanel panel = pageCoordinatePanels.get(pageNumber);
            panel.setX(coordinate.getFormattedX());
            panel.setY(coordinate.getFormattedY());
        }
        else if(evt.getPropertyName().equals(REMOVE_COORDINATE_PROPERTY)){
            IndexedPropertyChangeEvent iEvt = (IndexedPropertyChangeEvent) evt;
            int pageNumber = iEvt.getIndex();
            CoordinatePanel panel = pageCoordinatePanels.get(pageNumber);
            panel.setX("");
            panel.setY("");
        }
    }

    public static class CoordinatePanel extends JPanel{

        private JRadioButton pageRadioButton;
        private JTextField xField;
        private JTextField yField;
        private int pageNumber;
        private JButton xCopyButton;
        private JButton yCopyButton;
        private JButton clearButton;

        public CoordinatePanel(int pageNumber){
            this.pageNumber = pageNumber;
            init();
        }

        public int getPageNumber(){
            return pageNumber;
        }

        public JRadioButton getPageRadioButton(){
            return pageRadioButton;
        }

        public JTextField getXField(){
            return xField;
        }

        public JTextField getYField(){
            return yField;
        }

        public void setX(String x){
            xField.setText(x);
        }

        public void setY(String y){
            yField.setText(y);
        }

        public JButton getXCopyButton(){
            return xCopyButton;
        }

        public JButton getYCopyButton(){
            return yCopyButton;
        }

        public JButton getClearButton(){
            return clearButton;
        }

        private void init(){
            pageRadioButton = new JRadioButton("Page " + pageNumber);
            pageRadioButton.setFont(Fonts.LABEL_FONT);
            pageRadioButton.setActionCommand(SELECT_PAGE_COMMAND);

            JLabel xLabel = new JLabel("X");
            xLabel.setFont(Fonts.LABEL_FONT);
            JLabel yLabel = new JLabel("Y");
            yLabel.setFont(Fonts.LABEL_FONT);

            JLabel inchesLabel1 = new JLabel("inches");
            inchesLabel1.setFont(Fonts.LABEL_FONT);
            JLabel inchesLabel2 = new JLabel("inches");
            inchesLabel2.setFont(Fonts.LABEL_FONT);

            xField = new JTextField(5);
            xField.setFont(Fonts.LABEL_FONT);
            xField.setEditable(false);
            xField.setMaximumSize(xField.getPreferredSize());

            yField = new JTextField(5);
            yField.setFont(Fonts.LABEL_FONT);
            yField.setEditable(false);
            yField.setMaximumSize(yField.getPreferredSize());

            xCopyButton = new JButton(getIcon("icons/copy-icon2.png"));
            xCopyButton.setActionCommand(COPY_X_COMMAND);
            xCopyButton.setToolTipText("Copy the X coordinate value to the clipboard");

            yCopyButton = new JButton(getIcon("icons/copy-icon2.png"));
            yCopyButton.setActionCommand(COPY_Y_COMMAND);
            yCopyButton.setToolTipText("Copy the Y coordinate value to the clipboard");

            clearButton = new JButton(getIcon("icons/clear.png"));
            clearButton.setActionCommand(CLEAR_COORDINATE_COMMAND);
            clearButton.setToolTipText("Clear the coordinate values for this page");

            JPanel xRowPanel = new JPanel();
            xRowPanel.setLayout(new BoxLayout(xRowPanel, BoxLayout.X_AXIS));
            xRowPanel.add(xLabel);
            xRowPanel.add(xField);
            xRowPanel.add(inchesLabel1);
            xRowPanel.add(xCopyButton);

            JPanel yRowPanel = new JPanel();
            yRowPanel.setLayout(new BoxLayout(yRowPanel, BoxLayout.X_AXIS));
            yRowPanel.add(yLabel);
            yRowPanel.add(yField);
            yRowPanel.add(inchesLabel2);
            yRowPanel.add(yCopyButton);

            JPanel coordinatePanel = new JPanel();
            coordinatePanel.setLayout(new BoxLayout(coordinatePanel, BoxLayout.Y_AXIS));
            coordinatePanel.add(xRowPanel);
            coordinatePanel.add(yRowPanel);
            coordinatePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 2));

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(pageRadioButton);
            add(coordinatePanel);
            add(clearButton);
            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(2,2,2,2),
                            BorderFactory.createEtchedBorder()
                    ));
        }

        private ImageIcon getIcon(String path){
            return new ImageIcon(getClass().getClassLoader().getResource(path));
        }

    }
}
