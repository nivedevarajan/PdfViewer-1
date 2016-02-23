package com.pilotfish.eip.modules.pdfviewer.controller;

import com.pilotfish.eip.modules.pdfviewer.model.Coordinate;
import com.pilotfish.eip.modules.pdfviewer.util.ExportWriter;
import com.pilotfish.eip.modules.pdfviewer.util.PDFRetriever;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import static com.pilotfish.eip.modules.pdfviewer.view.ActionCommands.*;
import static com.pilotfish.eip.modules.pdfviewer.model.PDFModel.*;
import static com.pilotfish.eip.modules.pdfviewer.model.CoordinateModel.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class MenuBarController extends AbstractController implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getActionCommand().equals(LOAD_NEW_PDF_COMMAND)){
            loadNewPdf();
        }
        else if(evt.getActionCommand().equals(EXIT_COMMAND)){
            exit();
        }
        else if(evt.getActionCommand().equals(EXPORT_LIST_COMMAND)){
            exportList();
        }
    }

    private void exportList(){
        Map<Integer,Coordinate> pageCoordinateMap = (Map<Integer,Coordinate>) getHelper().getModelProperty(COORDINATES_BY_PAGE_PROPERTY);
        ExportWriter.exportAsList(pageCoordinateMap);
    }

    private void loadNewPdf(){
        File pdfFile = PDFRetriever.showChooserGetPDF();
        if(pdfFile != null){
            updateModelProperty(PDF_FILE_PROPERTY, pdfFile);
        }
    }

    private void exit(){
        System.exit(0);
    }
}
