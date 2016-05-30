package com.matthijs.philipshue.HueController.HueActions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class GetRequest {

    public static String executeGetRequest(URL url) {
        String result = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            // Lees het resultaat dat de Bridge retourneert.
            BufferedReader b = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = b.readLine();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
