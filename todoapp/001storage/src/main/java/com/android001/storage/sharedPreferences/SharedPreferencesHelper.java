package com.android001.storage.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;


import com.android001.storage.AppHolder;

import java.util.Map;


public class SharedPreferencesHelper {

    public static final String FILE_NAME_ADB = "adb_input";

    //
    public static SharedPreferences getSharePreference(String fileName) {
        return AppHolder.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static synchronized void updatePreferenceData(String fileName, String key, String data) throws Exception {
        SharedPreferences sharedPreferences = getSharePreference(fileName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static synchronized void updatePreferenceData(String fileName, Map<String, String> stringMap) {

        try {
            for (Map.Entry<String, String> entry :
                    stringMap.entrySet()) {
                updatePreferenceData(fileName, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static synchronized Map retrievePreferenceData(String fileName) {
        Map<String, String> mapData = null;
        try {
            SharedPreferences sharedPreferences = getSharePreference(fileName);
            mapData = (Map<String, String>) sharedPreferences.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapData;
    }


}
