package com.matthijs.philipshue.HueController.HueActions;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 31-5-16.
 */
public class GetGroups extends AsyncTask<String, Void, String> {
    /**
     * ArrayList which holds all Groups
     */
    private ArrayList<Group> groupArrayList;

    /**
     * Reference to the adapter displaying the Groups to notify
     */
    private BaseAdapter adapter;

    /**
     * Constructor
     *
     * @param groupArrayList ArrayList
     * @param adapter BaseAdapter
     */
    public GetGroups(ArrayList<Group> groupArrayList, BaseAdapter adapter) {
        this.groupArrayList = groupArrayList;
        this.adapter = adapter;
    }

    /*
    NEEDS REFACTORING
     */

    @Override
    protected String doInBackground(String... args) {
        String result = null;
        try {
            URL url = new URL(args[0]);
            result = GetRequest.executeGetRequest(url);
            Log.d("PhilipsHue", result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String string) {
        Log.d("PhilipsHue", "Result: " + string);
        try {
            //Create JSONObject from response\
            Log.d("PhilipsHue", "Names: " + new JSONObject(string).names().toString());
            buildGroupsFromJson(new JSONObject(string));
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses supplied JSONObject and extract all Groups and their States from it
     *
     * @param jsonObject JSONObject containing Groups
     */
    private void buildGroupsFromJson(JSONObject jsonObject) {
        try {
            for(int i = 0; i < jsonObject.names().length(); i++) {
                Log.d("PhilipsHue", jsonObject.getJSONObject((String)jsonObject.names().get(i)).toString());
                JSONObject groupJson = jsonObject.getJSONObject((String)jsonObject.names().get(i));
                Group group = new Group();
                group.setId(jsonObject.names().getInt(i));
                group.setName(groupJson.getString("name"));
                group.setState(buildStateFromJson(groupJson.getJSONObject("action")));
                groupArrayList.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a Group's State using the given JSONObject
     *
     * @param jsonObject JSONObject holding the State
     * @return State
     */
    private State buildStateFromJson(JSONObject jsonObject) {
        State state = new State();
        try {
            state.on = jsonObject.getBoolean("on");
            state.effect = jsonObject.getString("effect");
            state.x = (double) jsonObject.getJSONArray("xy").get(0);
            state.y = (double) jsonObject.getJSONArray("xy").get(1);
        } catch (Exception e) {
            Log.d("PhilipsHue", e.getMessage());
            e.printStackTrace();
        }
        return state;
    }
}
