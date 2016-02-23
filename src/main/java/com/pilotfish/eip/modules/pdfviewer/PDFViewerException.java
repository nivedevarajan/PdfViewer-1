package com.pilotfish.eip.modules.pdfviewer;

/**
 * Created by craigmiller on 2/5/16.
 */
public class PDFViewerException extends Exception {

    public PDFViewerException() {
    }

    public PDFViewerException(String message) {
        super(message);
    }

    public PDFViewerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDFViewerException(Throwable cause) {
        super(cause);
    }
}
