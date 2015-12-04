package com.robots.we.parkme.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by supun.hettigoda on 11/28/2015.
 */
public class HttpRequestHandler {

    /**
     * load the car park stream
     *
     * @return
     * @throws IOException
     */
    public static InputStream refresh() throws IOException {
        return downloadUrl(NetworkConfigurations.URL_REFRESH);
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
