package com.android001.common.hardware.sim.gione;

//import ap;

public class UpdateConfig {
    public static boolean DEBUG = false;
    public static final String KEY_UPDATE_PREFER = "key_update_seciton";
    public static final String SP_KEY_LOCAL_TIMESTAMP = "local_current_time";
    public static final String SP_KEY_TOTAL_RECEIVED_BYTES = "receive_bytes";
    public static final String TIMESTAMP_EXT = ".t";
    public static final String UPDATE_CONTAINER_APK_TYPE = "a";
    public static final String UPDATE_CONTAINER_DOWNLOAD_TYPE = "d";
    public static final String UPDATE_CONTAINER_REMOVE_TYPE = "r";
    public static final String UPDATE_CONTAINER_UPLOAD_TYPE = "u";
    public static final long UPDATE_INTERVAL_TIME = 28800000;
    public static final String UPDATE_JSON_FILE_NAME = "u.json";
    public static final String UPDATE_MD5_FILE_NAME = "u.md5";
    public static final String UPDATE_WIFI_JSON_FILE_NAME = "w.json";
    public static final String UPDATE_ZIP_FILE_NAME = "u.zip";
    public static String sdk_version;
//    public static ap sysContext;

    static {
//        sysContext = null;
        DEBUG = false;
        sdk_version = null;
    }
}
