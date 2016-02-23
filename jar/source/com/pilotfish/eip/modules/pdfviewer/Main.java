package com.pilotfish.eip.modules.pdfviewer;

import com.pilotfish.eip.modules.pdfviewer.util.ViewUtil;

import javax.swing.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //ViewUtil.initAlloyUI();
                PDFViewer pdfViewer = new PDFViewer();
                pdfViewer.startApplication();
            }
        });
    }

}
