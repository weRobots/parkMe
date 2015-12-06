package com.robots.we.parkme.network;

/**
 * This class contains configuration keys for network usage.
 * <p/>
 * Created by supun.hettigoda on 11/28/2015.
 */
public class NetworkConfigurations {
    private static String HOST_WIFY = "http://172.20.62.207:6060/";
    private static String HOST_EMULATOR = "http://10.0.2.2:6060/";
    private static String REFRESH_PATH = "parkme/refresh/dorefresh?id=01";
    private static String LOAD_USER_PATH = "parkme/userService/readUser?userId=";

    // refresh
    public static String URL_REFRESH = HOST_WIFY + REFRESH_PATH;

    // load user
    public static String URL_LOAD_USER = HOST_EMULATOR + LOAD_USER_PATH;
}
