package com.android001.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.android001.common.app.AppHolder;

/**
 * Created by xixionghui on 2017/3/6.
 */

public class CommonUtilMethods {

    private static TelephonyManager sTelephonyManager;
    private static PackageManager mPackageManager;

    public static TelephonyManager getTelephonyManager(Context paramContext) {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager) paramContext.getApplicationContext().getSystemService("phone");
        }
        return sTelephonyManager;
    }

    public static TelephonyManager getTelephonyManager() {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager) AppHolder.getContext().getApplicationContext().getSystemService("phone");
        }
        return sTelephonyManager;
    }

    public static PackageManager getPackageManager() {
        if (mPackageManager == null) {
            mPackageManager = AppHolder.getContext().getApplicationContext().getPackageManager();
        }
            return mPackageManager;
    }


}
