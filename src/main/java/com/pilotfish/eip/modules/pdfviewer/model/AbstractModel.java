package com.pilotfish.eip.modules.pdfviewer.model;

import com.pilotfish.eip.modules.pdfviewer.PDFViewerException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by craigmiller on 2/5/16.
 */
public abstract class AbstractModel {

    private final PropertyChangeSupport propSupport = new PropertyChangeSupport(this);

    protected AbstractModel(){}

    protected void firePropertyChangeEvent(String propName, Object oldValue, Object newValue){
        propSupport.firePropertyChange(propName, oldValue, newValue);
    }

    protected void fireIndexedPropertyChangeEvent(String propName, int index, Object oldValue, Object newValue){
        propSupport.fireIndexedPropertyChange(propName, index, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        propSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        propSupport.removePropertyChangeListener(listener);
    }

    public abstract void updateProperty(String propName, Object...values);

    public abstract Object getProperty(String propertyName, Object...params);

}
