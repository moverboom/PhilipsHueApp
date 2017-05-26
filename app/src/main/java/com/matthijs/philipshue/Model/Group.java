package com.matthijs.philipshue.Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class Group implements Serializable {
    /**
     * Group id as defined by Bridge
     */
    protected int id;

    /**
     * Group name
     */
    protected String name;

    /**
     * Group state as State.
     * This holds all settings like hue, bri, effect etc.
     */
    protected State state;


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
