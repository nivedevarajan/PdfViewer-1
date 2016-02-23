package com.pilotfish.eip.modules.pdfviewer.controller;

import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;

/**
 * Created by craigmiller on 2/5/16.
 */
public abstract class AbstractController {

    protected PDFViewerHelper helper;

    protected AbstractController(){

    }

    public void setHelper(PDFViewerHelper helper){
        this.helper = helper;
    }

    public PDFViewerHelper getHelper(){
        return helper;
    }

    protected void updateModelProperty(String propName, Object... values){
        helper.updateModelProperty(propName, values);
    }

}
