package com.pilotfish.eip.modules.pdfviewer.view;

import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Created by craigmiller on 2/5/16.
 */
public class ViewerFrame extends AbstractView {

    private JFrame frame;

    private JPanel displayPanel;
    private JPanel outputPanel;
    private JMenuBar menuBar;

    public ViewerFrame(PDFViewerHelper helper) {
        super(helper);
        initComponents();
    }

    private void initComponents(){
        frame = new JFrame("PilotFish eiPDFViewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Temporary placeholder panels for components
        displayPanel = new JPanel();
        outputPanel = new JPanel();
        menuBar = new JMenuBar();

        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);
    }

    public void assembleAndShowFrame(){
        frame.getContentPane().removeAll();

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(outputPanel, BorderLayout.WEST);
        frame.getContentPane().add(displayPanel, BorderLayout.CENTER);

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame(){
        return frame;
    }

    public void setDisplayPanel(JPanel panel){
        this.displayPanel = panel;
    }

    public void setOutputPanel(JPanel panel){
        this.outputPanel = panel;
    }

    public void setMenuBar(JMenuBar menuBar){
        this.menuBar = menuBar;
    }

    @Override
    public void updateView(PropertyChangeEvent evt) {

    }
}
