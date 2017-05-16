package com.android001.common.hardware.sim.vivo.utils;

/**
 * Created by android001 on 2017/5/16.
 */


import android.content.Context;

import com.android001.common.app.AppHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VivoSimCardUtils {


    //import android.telephony.FtTelephonyAdapter;
    //private static FtTelephony mFtTelephony = FtTelephonyAdapter.getFtTelephony(DialerApplication.getInstance().getApplicationContext());

    public static boolean isMultiSimCard() {

        try {
            Class<?> ftTelephonyAdapterClazz = Class.forName("android.telephony.FtTelephonyAdapter");
            Method getFtTelephonyMethod = ftTelephonyAdapterClazz.getMethod("getFtTelephony", Context.class);
            getFtTelephonyMethod.setAccessible(true);
            Object ftTelephonyAdapterObject = getFtTelephonyMethod.invoke(null, AppHolder.getContext());
            //---
            Class<?> ftTelephonyClazz = Class.forName("android.telephony.FtTelephony");
            Method isMutiCardMethod = ftTelephonyClazz.getMethod("isMultiSimCard");
            isMutiCardMethod.setAccessible(true);
            return (boolean)isMutiCardMethod.invoke(ftTelephonyAdapterObject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


}
