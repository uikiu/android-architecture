package com.android001.common.hardware.sim;

import android.content.Context;
import android.telephony.TelephonyManager;

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
}
