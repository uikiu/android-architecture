package com.android001.common.hardware.sim;

import android.annotation.TargetApi;
import android.content.Context;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android001.common.app.AppHolder;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;

/**
 * Created by xixionghui on 2017/3/6.
 */

public class SimFactoryManager {
    private static boolean sIsDualSim = true;
    private static Context mContext;

    static {
        mContext = AppHolder.getContext();
    }

    @TargetApi(23)
    public static String getDeviceId2(int paramInt) {
        if (sIsDualSim) {
            try {

                String str = CommonUtilMethods.getTelephonyManager(mContext).getDeviceId(paramInt);
                return str;
            } catch (Exception localException) {
                Log.w("CSP_SimFactoryManager", "Unsupported exception thrown in getDeviceId method hence returning null");
                return null;
            }
        }
        return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    @TargetApi(23)
    public static String getDeviceId(int paramInt) {
        if (sIsDualSim) {
            try {
                int j = getSubscriptionIdBasedOnSlot(paramInt);
                int i = j;
                if (j == -1) {
                    i = paramInt;
                }
                String str = CommonUtilMethods.getTelephonyManager(mContext).getDeviceId(i);
                return str;
            } catch (Exception localException) {
                Log.w("CSP_SimFactoryManager", "Unsupported exception thrown in getDeviceId method hence returning null");
                return null;
            }
        }
        return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }


    public static int getSubscriptionIdBasedOnSlot(int paramInt) {
        try {
            Class<?> classFile = Class.forName("android.telephony.SubscriptionManager");
            Method method = classFile.getMethod("getSubId",new Class[]{int.class});

            Integer[] sloatId = new Integer[2];
            method.invoke(sloatId,paramInt);
            for (int i = 0; i < sloatId.length; i++) {
                Log.i("xixionghui","获取到的sloatId = "+sloatId[i]);
                if (sloatId[i]!=null)
                    return sloatId[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
