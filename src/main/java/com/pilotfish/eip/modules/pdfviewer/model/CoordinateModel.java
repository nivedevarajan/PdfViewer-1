package com.pilotfish.eip.modules.pdfviewer.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by craigmiller on 2/5/16.
 */
public class CoordinateModel extends AbstractModel {

    private Map<Integer,Coordinate> coordinateMap;

    private Log log = LogFactory.getLog(CoordinateModel.class);

    public static final String COORDINATE_PROPERTY = "Coordinate";
    public static final String REMOVE_COORDINATE_PROPERTY = "RemoveCoordinate";
    public static final String COORDINATES_BY_PAGE_PROPERTY = "CoordinatesByPage";


    public CoordinateModel(){
        coordinateMap = new TreeMap<>();
    }

    public void setCoordinate(Integer pageNumber, Coordinate coordinate){
        log.trace("Setting coordinate. Page: " + pageNumber + " Coordinate: (" +
                coordinate.getFormattedX() + "," + coordinate.getFormattedY() + ")");

        coordinateMap.put(pageNumber, coordinate);
        fireIndexedPropertyChangeEvent(COORDINATE_PROPERTY, pageNumber, null, coordinate);
    }

    public void removeCoordinate(Integer pageNumber){
        log.trace("Removing coordinate. Page: " + pageNumber);

        coordinateMap.remove(pageNumber);
        fireIndexedPropertyChangeEvent(REMOVE_COORDINATE_PROPERTY, pageNumber, null, pageNumber);
    }

    public Coordinate getCoordinate(Integer pageNumber){
        return coordinateMap.get(pageNumber);
    }

    public Map<Integer,Coordinate> getCoordinatesByPage(){
        return coordinateMap;
    }

    @Override
    public void updateProperty(String propName, Object... values){
        if(propName.equals(COORDINATE_PROPERTY) && values.length == 2){
            validatePropertyValues(propName, values);
            setCoordinate((Integer) values[0], (Coordinate) values[1]);
        }
        else if(propName.equals(REMOVE_COORDINATE_PROPERTY) && values.length == 1) {
            validatePropertyValues(propName, values);
            removeCoordinate((Integer) values[0]);
        }
    }

    @Override
    public Object getProperty(String propName, Object... params){
        if(propName.equals(COORDINATE_PROPERTY)){
            validateParamValues(propName, params);
            return getCoordinate((Integer) params[0]);
        }
        else if(propName.equals(COORDINATES_BY_PAGE_PROPERTY)){
            return getCoordinatesByPage();
        }
        return null;
    }

    private void validateParamValues(String propName, Object... values){
        if(propName.equals(COORDINATE_PROPERTY)){
            if(!(values[0] instanceof Integer) && !(values[1] instanceof Coordinate)){
                throw new IllegalArgumentException("Parameter values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
    }

    private void validatePropertyValues(String propName, Object... values){
        if(propName.equals(COORDINATE_PROPERTY)){
            if(!(values[0] instanceof Integer) && !(values[1] instanceof Coordinate)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName() + ", Values[1]: " + values[1].getClass().getName());
            }
        }
        else if(propName.equals(REMOVE_COORDINATE_PROPERTY)){
            if(!(values[0] instanceof Integer)){
                throw new IllegalArgumentException("Property values of invalid types: PropName: " + propName + ", Values[0]: " +
                        values[0].getClass().getName());
            }
        }
    }
}
