package com.android001.common.hardware.sim.vivo.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android001.common.BuildConfig;
import com.android001.common.app.AppHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by android001 on 2017/5/16.
 */

public class VivoTelephonyUtils {

    private static final String TAG = "VivoTelephonyUtils";

    //com.android.internal.telephony.VivoTelephonyApiParams
    //Object localObject = new VivoTelephonyApiParams("API_TAG_getImei");
    //((VivoTelephonyApiParams)localObject).getAsString("imei");

    public static String getImeiOfCdmaPhone() {
        try {
            Class<?> vivoTelephonyApiParamsClazz = Class.forName("com.android.internal.telephony.VivoTelephonyApiParams");
            Constructor vivoTelephonyApiParamsConstructor = vivoTelephonyApiParamsClazz.getConstructor(String.class);
            vivoTelephonyApiParamsConstructor.setAccessible(true);
            Object vivoTelephonyApiParams = vivoTelephonyApiParamsConstructor.newInstance("API_TAG_getImei");

            //--

            Method getAsStringMethod = vivoTelephonyApiParamsClazz.getMethod("getAsString", String.class);
            return (String) getAsStringMethod.invoke(vivoTelephonyApiParams, "imei");
        } catch (Exception e) {
            Log.e(TAG,"在执行getImeiOfCdmaPhone时出错："+e.getMessage());
            return "";
        }
    }

    public static String getMeidOfCdmaPhone() {
        try {
            Class<?> vivoTelephonyApiParamsClazz = Class.forName("com.android.internal.telephony.VivoTelephonyApiParams");
            Constructor vivoTelephonyApiParamsConstructor = vivoTelephonyApiParamsClazz.getDeclaredConstructor(String.class);
            vivoTelephonyApiParamsConstructor.setAccessible(true);
            Object vivoTelephonyApiParams = vivoTelephonyApiParamsConstructor.newInstance("API_TAG_getMeid");

            //-----

            Method getAsStringMethod = vivoTelephonyApiParamsClazz.getDeclaredMethod("getAsString", String.class);
            getAsStringMethod.setAccessible(true);
            String meid1 =  (String) getAsStringMethod.invoke(vivoTelephonyApiParams, "meid");
            return meid1;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)Log.e(TAG,"在执行getMeidOfCdmaPhone时出错："+e.getMessage());
            return "";
        }
    }

    public static Object getVivoTelephonyApiParamsObject() {
        try {
            Class<?> vivoTelephonyApiParamsClazz = Class.forName("com.android.internal.telephony.VivoTelephonyApiParams");
            Constructor vivoTelephonyApiParamsConstructor = vivoTelephonyApiParamsClazz.getConstructor(String.class);
            vivoTelephonyApiParamsConstructor.setAccessible(true);
            return vivoTelephonyApiParamsConstructor.newInstance("API_TAG_getImei");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     public static String getImei(int paramInt)
     {
     return mFtTelephony.getImei(paramInt);
     }
     */

    public static String getImei(int paramInt) {
        try {
            Class<?> ftTelephonyAdapterClazz = Class.forName("android.telephony.FtTelephonyAdapter");
            Method getFtTelephonyMethod = ftTelephonyAdapterClazz.getMethod("getFtTelephony", Context.class);
            getFtTelephonyMethod.setAccessible(true);
            Object ftTelephonyAdapterObject = getFtTelephonyMethod.invoke(null, AppHolder.getContext());
            //---
            Class<?> ftTelephonyClazz = Class.forName("android.telephony.FtTelephony");
            Method getImeiMethod = ftTelephonyClazz.getMethod("getImei",int.class);
            getImeiMethod.setAccessible(true);
            return (String)getImeiMethod.invoke(ftTelephonyAdapterObject,paramInt);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)Log.e(TAG,"在执行getImei时出错："+e.getMessage());
            return "";
        }
    }

    public static String getMeid() {
        try {
            Class<?> ftTelephonyAdapterClazz = Class.forName("android.telephony.FtTelephonyAdapter");
            Method getFtTelephonyMethod = ftTelephonyAdapterClazz.getMethod("getFtTelephony", Context.class);
            getFtTelephonyMethod.setAccessible(true);
            Object ftTelephonyAdapterObject = getFtTelephonyMethod.invoke(null, AppHolder.getContext());
            //---
            Class<?> ftTelephonyClazz = Class.forName("android.telephony.FtTelephony");
            Method getImeiMethod = ftTelephonyClazz.getMethod("getMeid");
            getImeiMethod.setAccessible(true);
            return (String)getImeiMethod.invoke(ftTelephonyAdapterObject);
        } catch (Exception e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG)Log.e(TAG,"在执行getImei时出错："+e.getMessage());
            return "";
        }
    }


}
