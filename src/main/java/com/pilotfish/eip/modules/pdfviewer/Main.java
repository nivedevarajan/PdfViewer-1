package com.pilotfish.eip.modules.pdfviewer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.LogManager;

/**
 * Created by craigmiller on 2/5/16.
 */
public class Main {

    public static void main(String[] args){
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExcepions());
        configureLogging();
        addShutdownHook();

        LogFactory.getFactory();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //ViewUtil.initAlloyUI();
                PDFViewer pdfViewer = new PDFViewer();
                pdfViewer.startApplication();
                Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExcepions());
            }
        });
    }

    /**
     * Add a shutdown hook so the program logs its own termination.
     */
    private static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            private Log log = LogFactory.getLog(Main.class);

            @Override
            public void run(){
                log.info("Shutting down application");
            }
        });
    }

    /**
     * Configure logging. Ensure that the logging directory exists,
     * and load the logging configuration file from the classpath.
     */
    private static void configureLogging(){
        File loggingDir = new File(System.getProperty("user.home"), "PdfViewerLog");
        if(!loggingDir.exists()){
            if(!loggingDir.mkdir()){
                System.err.println("ERROR!! Unable to create logging directory");
            }
        }

        //This code is essential for commons-logging. Otherwise, it doesn't detect props on classpath.
        try{
            URL url = Main.class.getClassLoader().getResource("commons-logging.properties");
            if(url != null){
                LogManager.getLogManager().readConfiguration(url.openStream());
            }
        }
        catch(IOException ex){
            System.err.println("ERROR!! Unable to load logging configuration");
            ex.printStackTrace();
        }
    }

    /**
     * Uncaught exception handler to log all uncaught exceptions.
     */
    private static class UncaughtExcepions implements Thread.UncaughtExceptionHandler{

        private Log log = LogFactory.getLog(UncaughtExcepions.class);
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("Uncaught Exception: " + e.getMessage(), e);
        }
    }

}
