package com.matthijs.philipshue.Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by matthijs on 28-5-16.
 */
public class Group implements Serializable {
    /**
     * Group id as defined by Bridge
     */
    private int id;

    /**
     * Group name
     */
    private String name;

    /**
     * Group state as State.
     * This holds all settings like hue, bri, effect etc.
     */
    private State state;

    /**
     * HashMap which contains all Lights in the Group
     */
    private HashMap<Integer, Light> lightHashMap;

    /**
     * Constructor
     */
    public Group() {
        lightHashMap = new HashMap<Integer, Light>();
    }

    /**
     * Returns Light for given id
     * Doesn't do anything yet
     * @return
     */
    public Light getLight() {
        return null;
    }

    /**
     * Returns the Group's id
     * @return id as int
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Group's id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Group's name
     * @return name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Group's name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Group's state
     * @return state as State
     */
    public State getState() {
        return state;
    }

    /**
     * Set the Group's state
     * @param state State
     */
    public void setState(State state) {
        this.state = state;
    }

}
