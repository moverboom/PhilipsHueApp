package com.matthijs.philipshue.HueController;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.matthijs.philipshue.HueController.HueActions.GetRequest;
import com.matthijs.philipshue.HueController.HueActions.PutRequest;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by matthijs on 28-5-16.
 */
public class HueController {
    /**
     * Base URL to the Bridge
     */
    private static final String BASE_URL = "http://192.168.178.24:8000/api/";

    /**
     * Username to get access to the Bridge
     */
    private static final String USERNAME = "newdeveloper";

    /**
     * sub-URL to access groups
     */
    private static final String GROUPS = "/groups/";

    /**
     * sub-URL to access lights
     */
    private static final String LIGHTS = "/lights/";

    /**
     * List which holds all groups
     */
    private ArrayList<Group> groupList = new ArrayList<>();

    /**
     * Reference to the adapter which holds model objects currently displayed
     * This adapter is notified when the model is updated
     */
    private BaseAdapter adapter;

    /**
     * Sets the adapter reference
     *
     * @param adapter BaseAdapter
     */
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Fills the provided list with all Groups registered on the Bridge
     *
     * @param list ArrayList<Group>
     */
    public void getGroups(ArrayList<Group> list) {
        groupList = list;
        new GetGroups().execute();
    }

    /**
     * Sets the new state for the group which was defined by the user
     *
     * @param group Group to update
     * @param newState State to set
     */
    public void controlGroup(Group group, State newState) {
        ControlGroup controlGroup = new ControlGroup(newState);
        controlGroup.execute(group.getId());
    }


    /*
    EVERYTHING BELOW THIS COMMENT NEEDS REFACTORING
    Comments added later
     */

    private class GetGroups extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... args) {
            String result = null;
            try {
                URL url = new URL(BASE_URL + USERNAME + GROUPS);
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
                //Create JSONObject from response
                Group group = buildGroupFromJson(new JSONObject(string));
                groupList.add(group);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private Group buildGroupFromJson(JSONObject jsonObject) {
            Group group = new Group();
            try {
                //Get key iterator
                Iterator it = jsonObject.keys();
                while (it.hasNext()) {
                    //Get next key -> group id
                    String key = (String) it.next();
                    //get group json object
                    JSONObject jsonGroup = jsonObject.getJSONObject(key);
                    group.setId(Integer.parseInt((String)jsonObject.names().get(0)));
                    group.setName(jsonGroup.getString("name"));
                    group.setState(buildStateFromJson(jsonGroup.getJSONObject("action")));
                }
            } catch (Exception e) {
                Log.d("PhlilipsHue", e.getMessage());
                e.printStackTrace();
            }
            return group;
        }

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

    private class ControlGroup extends AsyncTask<Integer, Void, String> {
        private State newState;

        public ControlGroup(State newState) {
            this.newState = newState;
        }

        @Override
        public String doInBackground(Integer... args) {
            String result = null;
            try {
                URL url = new URL(BASE_URL + USERNAME + GROUPS + args[0] + "/action");
                result = PutRequest.executePutRequest(url, createJson());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return result;
        }

        private JSONObject createJson() {
            JSONObject json = new JSONObject();
            try {
                json.put("on", newState.on);
                json.put("effect", newState.effect);
                JSONArray xyArray = new JSONArray();
                xyArray.put(newState.x);
                xyArray.put(newState.y);
                json.put("xy", xyArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}
