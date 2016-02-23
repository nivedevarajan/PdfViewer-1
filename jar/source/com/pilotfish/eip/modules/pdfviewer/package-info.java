/**
 * eiPDFViewer Design White Paper
 *
 * This document is to explain all design decisions in the
 * eiPDFViewer, so future developers can maintain it properly.
 *
 * 1) MODEL VIEW CONTROLLER
 * This tool adheres to a strict model view controller pattern,
 * using the PDFViewerHelper to facilitate it.
 *
 * 1a) MAIN CONTROLLER
 * PDFViewer class is the main controller. It runs at application
 * start, and builds all models/views/etc. It handles high-level operations
 * like building the initial configuration for the program, and reloading
 * the program if the PDF file its rendering changes.
 *
 * It contains lists of all views and models used by the application.
 * It implements the PropertyChangeListener interface, so it can listen
 * for any changes to models, and then pass those changes along to the views.
 * It also provides callback methods that the helper uses to facilitate abstract
 * access to the models so that the views and controllers can modify/access model
 * properties.
 *
 * 1b) HELPER
 * PDFViewerHelper links the controllers and the views. It has callback
 * methods into the main controller class, which it provides to the
 * controllers. It also stores a map of all controllers. In views,
 * a controller is retrieved from the helper by a String key, which is
 * a constant in the PDFViewer class. This allows for greater abstraction
 * between views and controllers, as neither are totally aware of each other.
 *
 * 1c) SUB CONTROLLERS
 * The Controller package contains all the individual controllers for
 * the views. Each controller inherits from AbstractController, which
 * contains utility methods for calling back into the main controller
 * to update models and get values from models.
 *
 * Each individual controller also implements one of Java's listener'
 * interfaces, so that they can be added as a listener to the Swing
 * components in the views.
 *
 * 1d) MODELS
 * All models are JavaBean style bound property models. They all inherit from
 * AbstractModel, and implement two abstract methods: updateProperty(String,Object...)
 * and getProperty(String,Object...).
 *
 * updateProperty(String,Object...) is an abstract way to update the values of
 * the model. The main controller (PDFViewer) has a list of all models, and
 * when a change is passed along from one of the other controllers, it
 * called the update method on all models in the list. If the String property name
 * matches a property that model contains, it validates the values and then
 * updates the property value. Finally, it fires a PropertyChangeEvent, which is
 * received by PDFViewer.
 *
 * getProperty(String,Object...) is an abstract way to retrieve a property
 * value. It is also called by the main controller (PDFViewer), which searches
 * through all models to find one that returns a value. Each model implements
 * this method to return a value based on the String for the property name, if
 * it contains the requested value.
 *
 * 1e) VIEWS
 * All Views inherit from AbstractView. None of them extend Swing components
 * because of this. No Java listeners should be added directly to view classes.
 * Instead, each Swing component that needs to perform an action should have
 * the appropriate listener retrieved from the Helper, using one of its get...Listener(...)
 * convenience methods.
 *
 * AbstractView provides a method, updateView(PropertyChangeEvent), to allow
 * the main controller (PDFViewer) to pass property changes to the views. Each
 * view should check the property name to see if the property change applies to it,
 * and, if so, take the appropriate action. The updateView(PropertyChangeEvent)
 * method is called by PDFViewer on all views every time a property is changed.
 *
 * Lastly, the ViewerFrame instantiates and assembles its components separately.
 * This is because all views should be instantiated in the main controller (PDFViewer)
 * and then assembled in the ViewerFrame using its setter methods. This allows
 * the views to be added to PDFViewer's view list, which is used to abstractly
 * update them. Each time the assembleAndShowFrame() method of ViewerFrame is
 * called, all components are removed from the frame before it is reassembled
 * and repainted. This allows the components added after ViewerFrame was
 * instantiated to be properly assembled. It also allows the frame to be
 * easily rebuilt after loading a new PDF file.
 *
 * 2) UTILITIES
 * There are several utility classes that help with the operations of this
 * application. All can be found in the util package.
 *
 * 2a) IMAGE SIZE CONVERTER
 * This class uses the current screen resolution to provide both the size
 * the display panel should render the pdf image, and to generate an image
 * of the PDF page that is the correct size to not only fit the screen, but
 * maintain the abstract ratio of its source.
 *
 * 2b) PDF READER
 * This class uses PDFBox to read a PDF file and convert each page into
 * an image of its contents.
 *
 * 2c) PDF RETRIEVER
 * This class provides a file chooser to select and load the PDF file from
 * the disk. It also provides a dialog to get the orientation that the document
 * should have.
 *
 * 2d) FONTS
 * A universal fonts class for the UI.
 *
 * 2e) VIEW UTIL
 * An attempt to use the Alloy LookAndFeel, to match the LookAndFeel of the eiConsole.
 * This looks to not be possible because of the licensing issues.
 *
 * 2f) EXPORT WRITER
 * A class to handle operations exporting the values in the application to
 * a text file.
 *
 *
 * Created by craigmiller on 2/5/16.
 */
package com.pilotfish.eip.modules.pdfviewer;