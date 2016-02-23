package com.pilotfish.eip.modules.pdfviewer.util;

import com.pilotfish.eip.modules.pdfviewer.model.Coordinate;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by craigmiller on 2/5/16.
 */
public class ExportWriter {

    private static Log log = LogFactory.getLog(ExportWriter.class);

    public static void exportAsList(Map<Integer,Coordinate> pageCoordinateMap){
        log.info("Exporting data as list");

        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            if(!FilenameUtils.isExtension(file.getName(), "txt")){
                String filePath = FilenameUtils.removeExtension(file.toString());
                String fileName = FilenameUtils.getName(filePath);
                filePath = filePath + FilenameUtils.EXTENSION_SEPARATOR_STR + "txt";
                file = new File(filePath);
                writeListToFile(file, fileName, pageCoordinateMap);
            }
        }
    }

    private static void writeListToFile(File file, String fileName, Map<Integer,Coordinate> pageCoordinateMap){
        FileWriter writer = null;
        try{
            writer = new FileWriter(file);

            writer.write(fileName + "\n\n");

            Set<Integer> pages = pageCoordinateMap.keySet();
            for(Integer i : pages){
                writer.write("Page " + (i + 1) + ": ");
                Coordinate coordinate = pageCoordinateMap.get(i);
                writer.write(coordinate.toString() + "\n");
            }
        }
        catch(IOException ex){
            log.error("Unable to write list to file. " + ex.getMessage(), ex);
        }
        finally{
            if(writer != null){
                try{
                    writer.close();
                }
                catch(IOException ex){
                    log.error("Unable to close writer. " + ex.getMessage(), ex);
                }
            }
        }
    }

}
