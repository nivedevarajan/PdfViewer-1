package com.pilotfish.eip.modules.pdfviewer.view;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;

import java.beans.PropertyChangeEvent;

/**
 * Created by craigmiller on 2/5/16.
 */
public abstract class AbstractView {

    protected PDFViewerHelper helper;

    protected AbstractView(PDFViewerHelper helper){
        this.helper = helper;
    }

    public PDFViewerHelper getHelper(){
        return helper;
    }

    public abstract void updateView(PropertyChangeEvent evt);

}
