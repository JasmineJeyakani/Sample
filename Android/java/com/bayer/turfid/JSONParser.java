package com.bayer.turfid;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Jasmine Jeyakani on 9/8/2016.
 */
public class JSONParser {
    private static final String TAG = JSONParser.class.getSimpleName();
    static String json = "";

    public JSONParser() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
//        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            is.close();
            json = sb.toString();
        } catch (IOException e) {
            Log.d("Ex1", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d("Ex", e.getMessage());
            }
        }
        return json;
    }
}