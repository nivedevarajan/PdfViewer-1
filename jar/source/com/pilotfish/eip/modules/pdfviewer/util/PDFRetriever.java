package com.pilotfish.eip.modules.pdfviewer.util;

import com.pilotfish.eip.modules.pdfviewer.util.ImageSizeConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Created by craigmiller on 2/5/16.
 */
public class PDFRetriever {

    public static File showChooserGetPDF(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        chooser.setMultiSelectionEnabled(false);

        int result = chooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File pdfFile = chooser.getSelectedFile();
            if(pdfFile != null){
                return pdfFile;
            }
        }
        return null;
    }

    public static int showDialogGetOrientation(){
        int result = JOptionPane.showOptionDialog(null, "Set Page Orientation",
                "Page Orientation", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Portrait", "Landscape"}, null);
        if(result == JOptionPane.YES_OPTION){
            return ImageSizeConverter.PORTRAIT;
        }
        else if(result == JOptionPane.NO_OPTION){
            return ImageSizeConverter.LANDSCAPE;
        }
        return 0;
    }

}
