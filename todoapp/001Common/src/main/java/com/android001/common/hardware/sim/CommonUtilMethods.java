package com.android001.common.hardware.sim;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android001.common.app.AppHolder;

/**
 * Created by xixionghui on 2017/3/6.
 */

public class CommonUtilMethods {

    private static TelephonyManager sTelephonyManager;

    public static TelephonyManager getTelephonyManager(Context paramContext)
    {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager)paramContext.getApplicationContext().getSystemService("phone");
        }
        return sTelephonyManager;
    }

    public static TelephonyManager getTelephonyManager()
    {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager) AppHolder.getContext().getApplicationContext().getSystemService("phone");
        }
        return sTelephonyManager;
    }




}
