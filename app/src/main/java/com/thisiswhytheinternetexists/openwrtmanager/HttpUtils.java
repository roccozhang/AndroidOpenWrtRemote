package com.thisiswhytheinternetexists.openwrtmanager;

/**
 * Created by flix on 28/06/16.
 */
import android.support.v4.util.Pair;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HttpUtils {

    public static int getStatusCode(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int v = conn.getResponseCode();
            return v;
        } catch (IOException e) {
            Log.e("OpenWRT Manager", e.getMessage());
            return -1;
        }
    }

    public static String getContents(String url, String token) {
        String contents ="";

        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("Cookie", token);

            InputStream in = conn.getInputStream();
            contents = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.v("OpenWRT Manager", "MALFORMED URL EXCEPTION");
        } catch (IOException e) {
            Log.e("OpenWRT Manager", e.getMessage());
        }
        return contents;
    }

    private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}