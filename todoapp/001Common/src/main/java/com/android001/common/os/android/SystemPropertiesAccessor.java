package com.android001.common.os.android;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by www.android001.com
 * 提供对android.os.SystemProperties访问
 */

public class SystemPropertiesAccessor {
    private static final String TAG = "SystemPropertiesAccess";



    public static String get(String key) {
        String getResult;
        try {
            final Class<?> systemPropertiesClazz = Class.forName("android.os.SystemProperties");
            final Method getMethod = systemPropertiesClazz.getMethod("get",String.class);
            getMethod.setAccessible(true);
            getResult = (String) getMethod.invoke(null,key);
        } catch (Throwable e) {
            e.printStackTrace();
            getResult = null;
        }
        return getResult;
    }

    /**
     * Gets system properties set by <code>adb shell setprop <em>key</em> <em>value</em></code>
     *
     * @param key the property key.
     * @param defaultValue the value to return if the property is undefined or empty (this parameter
     *            may be {@code null}).
     * @return the system property value or the default value.
     */
    public static String get(String key, String defaultValue) {
        try {
            final Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            final Method get = systemProperties.getMethod("get", String.class, String.class);
            return (String) get.invoke(null, key, defaultValue);
        } catch (Throwable e) {
            // This should never happen
            Log.e(TAG, "Exception while getting system property: ", e);
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            final Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            final Method get = systemProperties.getMethod("getBoolean", String.class, boolean.class);
            return (boolean) get.invoke(null, key, defaultValue);
        } catch (Throwable e) {
            // This should never happen
            Log.e(TAG, "Exception while getting system property: ", e);
            return defaultValue;
        }
    }
}
