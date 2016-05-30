package com.matthijs.philipshue.HueController;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.matthijs.philipshue.HueController.HueActions.ControlGroup;
import com.matthijs.philipshue.HueController.HueActions.GetGroups;
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
 * Handles all interaction with the Bridge
 *
 * Created by Matthijs Overboom on 28-5-16.
 */
public class HueController {
    /**
     * Base URL to the Bridge
     */
    private static final String BASE_URL = "http://192.168.178.16:8000/api/";

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
        new GetGroups(list, adapter).execute(BASE_URL + USERNAME + GROUPS);
    }

    /**
     * Sets the new state for the group which was defined by the user
     *
     * @param group Group to update
     * @param newState State to set
     */
    public void controlGroup(Group group, State newState) {
        ControlGroup controlGroup = new ControlGroup(group, newState);
        controlGroup.execute(BASE_URL + USERNAME + GROUPS + group.getId() + "/action");
    }
}
