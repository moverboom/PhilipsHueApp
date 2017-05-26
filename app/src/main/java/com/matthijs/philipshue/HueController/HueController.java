package com.matthijs.philipshue.HueController;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.BaseAdapter;
import com.matthijs.philipshue.HueController.HueActions.ControlGroup;
import com.matthijs.philipshue.HueController.HueActions.ControlLight;
import com.matthijs.philipshue.HueController.HueActions.GetGroups;
import com.matthijs.philipshue.HueController.HueActions.GetLights;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.Light;
import java.util.ArrayList;

/**
 * Handles all interaction with the Bridge
 *
 * Created by Matthijs Overboom on 28-5-16.
 */
public class HueController {
    /**
     * Base URL to the Bridge
     */
    private String baseURL = "http://10.0.0.3:8000/api/";

    /**
     * Username to get access to the Bridge
     */
    private String username = "newdeveloper/";

    /**
     * sub-URL to access groups
     */
    private final String GROUPS = "groups/";

    /**
     * sub-URL to access lights
     */
    private final String LIGHTS = "lights/";

    /**
     * Reference to the adapter which holds model objects currently displayed
     * This adapter is notified when the model is updated
     */
    private BaseAdapter adapter;

    /**
     * Disable default constuctor
     */
    private HueController(String baseURL, String username){
        this.baseURL = baseURL;
        this.username = username;
    }

    /**
     * Builder function
     *
     * @param baseURL String bridge URL. e.g. 192.168.178.2 or mybridge.com
     * @param username String bridge username
     * @return HueController
     */
    public static HueController create(String baseURL, String username) {
        return new HueController(baseURL, username);
    }

    /**
     * Builder function
     *
     * @param context Context to get shared preferences from
     * @return HueController
     */
    public static HueController create(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        String baseURL = SP.getString("bridge_url", "http://192.168.178.2/");
        baseURL = "http://" + cleanBaseUrl(baseURL) + "/api/"; //Who hacks IoT devices anyways /s
        String username = SP.getString("bridge_username", "newdeveloper/") + "/";
        return new HueController(baseURL, username);
    }

    /**
     * Check if baseURL contains schema
     *
     * @param baseURL String
     * @return boolean
     */
    private static String cleanBaseUrl(String baseURL) {
        return baseURL.replace("http://", "").replace("https://", "").replace("/", "");
    }

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
        if(adapter == null) {
            throw new IllegalStateException("BaseAdapter must be set on get methods. Use setAdapter(BaseAdapter)");
        }
        new GetGroups(list, adapter).execute(baseURL + username + GROUPS);
    }

    /**
     * Fills the provided list with all Lights registered on the Bridge
     *
     * @param list ArrayList<Light>
     */
    public void getLights(ArrayList<Light> list) {
        if(adapter == null) {
            throw new IllegalStateException("BaseAdapter must be set on get methods. Use setAdapter(BaseAdapter)");
        }
        new GetLights(list, adapter).execute(baseURL + username + LIGHTS);
    }

    /**
     * Sets the new state for the group
     *
     * @param group Group to update
     */
    public void controlGroup(Group group) {
        ControlGroup controlGroup = new ControlGroup(group);
        controlGroup.execute(baseURL + username + GROUPS + group.getId() + "/action");
    }

    /**
     * Sets the new state for the light
     *
     * @param light Light to update
     */
    public void controlLight(Light light) {
        ControlLight controlLight = new ControlLight(light);
        controlLight.execute(baseURL + username + LIGHTS + light.getId() + "/state");
    }
}
