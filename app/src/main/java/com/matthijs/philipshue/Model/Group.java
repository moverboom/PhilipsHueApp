package com.matthijs.philipshue.Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by matthijs on 28-5-16.
 */
public class Group implements Serializable {
    private int id;
    private String name;
    private State state;
    private HashMap<Integer, Light> lightHashMap;

    public Group() {
        lightHashMap = new HashMap<Integer, Light>();
    }

    public Light getLight() {
        return null;
    }

    public void addLight(Light light) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(State state) {
        this.state = state;
    }

}
