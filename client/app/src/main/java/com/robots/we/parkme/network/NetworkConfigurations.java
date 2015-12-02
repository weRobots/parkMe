package com.robots.we.parkme.network;

/**
 * This class contains configuration keys for network usage.
 * <p/>
 * Created by supun.hettigoda on 11/28/2015.
 */
public class NetworkConfigurations {
    private static String HOST_WIFY = "http://172.20.61.22:6060/";
    private static String HOST_EMULATOR = "http://10.0.0.2:6060/";
    private static String REFRESH_PATH = "parkme/refresh/dorefresh?id=01";
    public static String URL_REFRESH = HOST_WIFY + REFRESH_PATH;
}
