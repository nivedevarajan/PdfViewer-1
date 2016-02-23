package com.pilotfish.eip.modules.pdfviewer.model;

import java.text.DecimalFormat;

/**
 * Created by craigmiller on 2/5/16.
 */
public class Coordinate{

    private double x;
    private double y;

    private DecimalFormat decimalFormat = new DecimalFormat("0.####");

    public Coordinate(){}

    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getRawX(){
        return x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getRawY(){
        return y;
    }

    public String getFormattedX(){
        return decimalFormat.format(x);
    }

    public String getFormattedY(){
        return decimalFormat.format(y);
    }

    @Override
    public String toString(){
        return "(" + getFormattedX() + ", " + getFormattedY() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.x, x) != 0) return false;
        return Double.compare(that.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
