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
    private static final String BASE_URL = "http://192.168.178.16:8000/api/";
    private static final String USERNAME = "newdeveloper";
    private static final String GROUPS = "/groups/";
    private static final String LIGHTS = "/lights/";
    private ArrayList<Group> groupList = new ArrayList<>();
    private BaseAdapter adapter;


    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public void getGroups(ArrayList<Group> list) {
        groupList = list;
        new GetGroups().execute();
    }

    public void controlGroup(Group group, State newState) {
        ControlGroup controlGroup = new ControlGroup(newState);
        controlGroup.execute(group.getId());
    }

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
                JSONObject jsonResult = new JSONObject(string);
                //Get key iterator
                Iterator it = jsonResult.keys();
                while(it.hasNext()) {
                    //Get next key -> group id
                    String key = (String)it.next();
                    //get group json object
                    JSONObject jsonGroup = jsonResult.getJSONObject(key);
                    Group group = new Group();
                    group.setName(jsonGroup.getString("name"));

                    State state = new State();
                    state.on = jsonGroup.getJSONObject("action").getBoolean("on");
                    state.x = (double)jsonGroup.getJSONObject("action").getJSONArray("xy").get(0);
                    state.y = (double)jsonGroup.getJSONObject("action").getJSONArray("xy").get(1);
                    group.setState(state);

                    groupList.add(group);
                    Log.d("PhilipsHue", jsonGroup.getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
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
