package com.matthijs.philipshue.Model;

import java.io.Serializable;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class State implements Serializable {
    public boolean on;
    public int bri;
    public int hue;
    public int sat;
    public double x; //Colorspace as a list of 2 floats with 3 decimals
    public double y; //Colorspace as a list of 2 floats with 3 decimals
    public int ct;
    public String alert = "";
    public String effect = "";
    public String colormode = "";
    public boolean reachable;

    /*
    NEEDS REFACTORING
     */
}
