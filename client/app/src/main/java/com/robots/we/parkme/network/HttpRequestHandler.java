package com.robots.we.parkme.network;

import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.beans.User;

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
    public static InputStream refresh(String carParkId) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("id=");
        param.append(carParkId);
        return downloadUrl(NetworkConfigurations.URL_REFRESH + param.toString());
    }

    /**
     * load user
     *
     * @param id
     * @return
     * @throws IOException
     */
    public static InputStream loadUser(String id) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("userId=");
        param.append(id);
        return downloadUrl(NetworkConfigurations.URL_LOAD_USER + param.toString());
    }

    /**
     * save user
     *
     * @param user
     * @return
     * @throws IOException
     */
    public static InputStream save(User user) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("registrationToken=");
        param.append(user.getRegistrationToken());
        param.append("&");
        param.append("vehicleNumber=");
        param.append(user.getVehichleNumber());
        param.append("&");
        param.append("mobileNumber=");
        param.append(user.getMobileNumber());
        param.append("&");
        param.append("name=");
        param.append(user.getName());
        param.append("&");
        param.append("id=");
        param.append(user.getUserId());

        return downloadUrl(NetworkConfigurations.URL_SAVE_USER + param.toString());
    }

    /**
     * @param slot
     * @param lat
     * @param log
     * @return
     * @throws IOException
     */
    public static InputStream allocate(Slot slot, String lat, String log) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("carParkId=");
        param.append(HomeActivity.CURRENT_SELECTED_CAR_PARK);
        param.append("&");
        param.append("latitude=");
        param.append(lat);
        param.append("&");
        param.append("longitude=");
        param.append(log);
        param.append("&");
        param.append("slotId=");
        param.append(slot.getId());
        param.append("&");
        param.append("userId=");
        param.append(AuthenticationHandler.USER.getUserId());

        return downloadUrl(NetworkConfigurations.URL_ALLOCATE + param.toString());
    }

    /**
     * @param slot
     * @return
     * @throws IOException
     */
    public static InputStream release(Slot slot) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("carParkId=");
        param.append(HomeActivity.CURRENT_SELECTED_CAR_PARK);
        param.append("&");
        param.append("slotId=");
        param.append(slot.getId());
        param.append("&");
        param.append("userId=");
        param.append(AuthenticationHandler.USER.getUserId());

        return downloadUrl(NetworkConfigurations.URL_RELEASE + param.toString());
    }

    /**
     * @param lat
     * @param log
     * @return
     * @throws IOException
     */
    public static InputStream find(String lat, String log) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("lat=");
        param.append(lat);
        param.append("&");
        param.append("log=");
        param.append(log);

        return downloadUrl(NetworkConfigurations.URL_FIND + param.toString());
    }

    /**
     * @param slot
     * @return
     * @throws IOException
     */
    public static InputStream notify(Slot slot) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("carParkId=");
        param.append(HomeActivity.CURRENT_SELECTED_CAR_PARK);
        param.append("&");
        param.append("slotId=");
        param.append(slot.getId());
        param.append("&");
        param.append("userId=");
        param.append(AuthenticationHandler.USER.getUserId());

        return downloadUrl(NetworkConfigurations.URL_NOTIFY + param.toString());
    }


    /**
     * @param slot
     * @return
     * @throws IOException
     */
    public static InputStream block(Slot slot) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("carParkId=");
        param.append(HomeActivity.CURRENT_SELECTED_CAR_PARK);
        param.append("&");
        param.append("slotId=");
        param.append(slot.getId());
        param.append("&");
        param.append("userId=");
        param.append(AuthenticationHandler.USER.getUserId());

        return downloadUrl(NetworkConfigurations.URL_BLOCK + param.toString());
    }

    /**
     * @param slotId
     * @return
     * @throws IOException
     */
    public static InputStream adminAllocate(String slotId, String mobile) throws IOException {
        StringBuilder param = new StringBuilder();
        param.append("carParkId=");
        param.append(HomeActivity.CURRENT_SELECTED_CAR_PARK);
        param.append("&");
        param.append("slotId=");
        param.append(slotId);
        param.append("&");
        param.append("mobile=");
        param.append(mobile);

        return downloadUrl(NetworkConfigurations.URL_ADMIN_ALLOCATE + param.toString());
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

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
