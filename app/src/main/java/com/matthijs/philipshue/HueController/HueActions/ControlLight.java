package com.matthijs.philipshue.HueController.HueActions;

import android.os.AsyncTask;

import com.matthijs.philipshue.Model.Light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matthijs Overboom on 25-5-17.
 */

public class ControlLight extends AsyncTask<String, Void, String> {
    /**
     * Light to set
     */
    private Light light;

    /**
     * Constructor
     *
     * @param light Light to set
     */
    public ControlLight(Light light) {
        this.light = light;
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
     * Sets Light's on attribute
     * Needs implementation of a check whether it is needed.
     * The PhilipsHue API specifies that the on attributes must only be
     * provided when needed.
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setOnOffIfNeeded(JSONObject jsonObject) throws JSONException {
        jsonObject.put("on", light.getState().on);
    }

    /**
     * Sets the Light's color using XY values
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setXY(JSONObject jsonObject) throws JSONException {
        JSONArray xyArray = new JSONArray();
        xyArray.put(light.getState().x);
        xyArray.put(light.getState().y);
        jsonObject.put("xy", xyArray);
    }

    /**
     * Sets the Light's effect.
     * Can only be colorloop as defined in the PhilipsHue API
     *
     * @param jsonObject JSONObject to set
     * @throws JSONException
     */
    private void setEffect(JSONObject jsonObject) throws JSONException {
        jsonObject.put("effect", light.getState().effect);
    }
}