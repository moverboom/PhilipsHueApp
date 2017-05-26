package com.matthijs.philipshue.HueController.HueActions;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.matthijs.philipshue.Model.Light;
import com.matthijs.philipshue.Model.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 25-5-17.
 */

public class GetLights extends AsyncTask<String, Void, String> {
    /**
     * ArrayList which holds all Lights
     */
    private ArrayList<Light> lightArrayList;

    /**
     * Reference to the adapter displaying the Lights to notify
     */
    private BaseAdapter adapter;

    /**
     * Constructor
     *
     * @param lightArrayList ArrayList
     * @param adapter        BaseAdapter
     */
    public GetLights(ArrayList<Light> lightArrayList, BaseAdapter adapter) {
        this.lightArrayList = lightArrayList;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... args) {
        String result = null;
        try {
            URL url = new URL(args[0]);
            result = GetRequest.executeGetRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String string) {
        try {
            buildLightsFromJson(new JSONObject(string));
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses supplied JSONObject and extract all Lights and their States from it
     *
     * @param jsonObject JSONObject containing Lights
     */
    private void buildLightsFromJson(JSONObject jsonObject) {
        try {
            for (int i = 0; i < jsonObject.names().length(); i++) {
                JSONObject lightAsJson = jsonObject.getJSONObject((String) jsonObject.names().get(i));
                Light light = new Light();
                light.setId(jsonObject.names().getInt(i));
                light.setName(lightAsJson.getString("name"));
                light.setState(buildStateFromJson(lightAsJson.getJSONObject("state")));
                lightArrayList.add(light);
            }
        } catch (Exception e) {
            Log.d("PhilipsHue", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Builds a Light's State using the given JSONObject
     *
     * @param jsonObject JSONObject holding the State
     * @return State
     */
    private State buildStateFromJson(JSONObject jsonObject) {
        State state = new State();
        try {
            state.on = jsonObject.getBoolean("on");
            state.effect = jsonObject.getString("effect");
            state.x = jsonObject.getJSONArray("xy").getDouble(0);
            state.y = jsonObject.getJSONArray("xy").getDouble(1);
        } catch (Exception e) {
            Log.d("PhilipsHue", e.getMessage());
            e.printStackTrace();
        }
        return state;
    }
}
