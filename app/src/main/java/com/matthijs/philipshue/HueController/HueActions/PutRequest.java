package com.matthijs.philipshue.HueController.HueActions;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class PutRequest {
    public static String executePutRequest(URL url, JSONObject data) {
        String result = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            // Write JSON data to request body
            DataOutputStream d = new DataOutputStream(conn.getOutputStream());
            d.writeBytes(data.toString());

            // Read result from Bridge
            BufferedReader b = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = b.readLine();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
