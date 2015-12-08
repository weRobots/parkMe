package com.robots.we.parkme.network;

/**
 * This class contains configuration keys for network usage.
 * <p/>
 * Created by supun.hettigoda on 11/28/2015.
 */
public class NetworkConfigurations {
    private static String HOST_WIFY = "http://172.20.62.207:6060/";
    private static String HOST_EMULATOR = "http://10.0.2.2:6060/";
    private static String REFRESH_PATH = "parkme/refresh/dorefresh?";
    private static String LOAD_USER_PATH = "parkme/userService/readUser?";
    private static String SAVE_USER_PATH = "parkme/userService/saveUser?";
    private static String ALLOCATE_PATH = "parkme/carParkService/parkCare?";
    private static String RELEASE_PATH = "parkme/carParkService/releaseCar?";
    private static String NOTIFY_PATH = "parkme/carParkService/sendUpdateToBlockingSlots?";
    private static String BLOCK_PATH = "parkme/carParkService/block?";
    private static String FIND_PATH = "parkme/carParkService/find?";

    // refresh
    public static String URL_REFRESH = HOST_WIFY + REFRESH_PATH;

    // load user
    public static String URL_LOAD_USER = HOST_WIFY + LOAD_USER_PATH;

    // save user
    public static String URL_SAVE_USER = HOST_WIFY + SAVE_USER_PATH;

    // allocate
    public static String URL_ALLOCATE = HOST_WIFY + ALLOCATE_PATH;

    // release
    public static String URL_RELEASE = HOST_WIFY + RELEASE_PATH;

    // notify
    public static String URL_NOTIFY = HOST_WIFY + NOTIFY_PATH;

    // block
    public static String URL_BLOCK = HOST_WIFY + BLOCK_PATH;

    // find
    public static String URL_FIND = HOST_WIFY + FIND_PATH;
}
