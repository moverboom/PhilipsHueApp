package com.matthijs.philipshue.HueController.HueActions;

import android.os.AsyncTask;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.State;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matthijs Overboom on 31-5-16.
 */
public class ControlGroup extends AsyncTask<String, Void, String> {
    /**
     * New State defined by user via UI
     */
    private State newState;

    /**
     * Group to set
     */
    private Group group;

    /**
     * Constructor
     *
     * @param group Group to set
     * @param newState State to set
     */
    public ControlGroup(Group group, State newState) {
        this.newState = newState;
        this.group = group;
    }

    @Override
    public String doInBackground(String... args) {
        String result = null;
        try {
            URL url = new URL(args[0]);
            result = PutRequest.executePutRequest(url, createJson());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Creates the json to send to the Bridge
     *
     * @return JSONObject
     */
    private JSONObject createJson() {
        JSONObject json = new JSONObject();
        try {
            setOnOffIfNeeded(json);
            setXY(json);
            setEffect(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Sets Group's on attribute
     * Needs implementation of a check wheter if it is needed.
     * The PhilipsHue API specifies that the on attributes must only be
     * provided when needed.
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setOnOffIfNeeded(JSONObject jsonObject) throws JSONException {
        jsonObject.put("on", newState.on);
    }

    /**
     * Sets the Group's color using XY values
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setXY(JSONObject jsonObject) throws JSONException {
        JSONArray xyArray = new JSONArray();
        xyArray.put(newState.x);
        xyArray.put(newState.y);
        jsonObject.put("xy", xyArray);
    }

    /**
     * Sets the Group's effect.
     * Can only be colorloop as defined in the PhilipsHue API
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setEffect(JSONObject jsonObject) throws JSONException {
        jsonObject.put("effect", newState.effect);
    }
}
