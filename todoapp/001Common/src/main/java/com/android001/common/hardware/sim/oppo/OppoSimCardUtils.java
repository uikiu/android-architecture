package com.android001.common.hardware.sim.oppo;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by android001 on 2017/5/18.
 */

public class OppoSimCardUtils {

    //1. 获取ColorOSTelephonyManager对象
//    Object colorOSTelephonyManager = ColorOSTelephonyManager.getDefault(paramContext);

    public static Object getColorOSTelephonyManager(Context paramContext) {
        try {
            Class<?> objectClazz = Class.forName("android.telephony.ColorOSTelephonyManager");
            Method getDefaultMethod = objectClazz.getDeclaredMethod("getDefault", Context.class);
            getDefaultMethod.setAccessible(true);
            return getDefaultMethod.invoke(null, paramContext);
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isSoftSimCard(Context paramContext, int paramInt) {

        try {
            if (paramContext == null) {
                Log.e("OppoSimCardUtils", "isSoftSimCard false, context is null");
            }
//        while (paramInt != ColorOSTelephonyManager.getDefault(paramContext).colorGetSoftSimCardSlotId()) {
//            return false;
//        }

            Class<?> objectClazz = Class.forName("android.telephony.ColorOSTelephonyManager");
            Method getDefaultMethod = objectClazz.getDeclaredMethod("getDefault", Context.class);
            getDefaultMethod.setAccessible(true);
            Object colorOSTelephonyManager =  getDefaultMethod.invoke(null, paramContext);
            Method colorSoftSimCardSlotIdMethod = objectClazz.getDeclaredMethod("colorGetSoftSimCardSlotId");
            int slotId = (int)colorSoftSimCardSlotIdMethod.invoke(colorOSTelephonyManager);
            while (paramInt!=slotId) {
                return false;
            }

            Log.d("OppoSimCardUtils", "isSoftSimCard true, slot " + paramInt);
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }

    /**
     * 是否为cdma手机
     *
     * @param paramContext
     * @param paramInt
     * @return
     */
    public static boolean isCTCCCard(Context paramContext, int paramInt) {
        SubscriptionInfo localSubscriptionInfo = SubscriptionManager.from(paramContext).getActiveSubscriptionInfoForSimSlotIndex(paramInt);
        if (localSubscriptionInfo != null) {
            Log.d("OppoSimCardUtils", "isCTCorWhiteCard sir = " + localSubscriptionInfo);
            int i = localSubscriptionInfo.getIconTint();
            if (isSoftSimCard(paramContext, paramInt)) {
            }
            do {
                if (i == 1) {
                    return true;
                }
                return false;
            } while ((i == 2) || (i == 3) || (i == 9));
        }
        boolean bool3 = false;
        boolean bool1 = false;
        boolean bool2 = bool3;
        String iccCardType ;
        for (; ; ) {
            try {
//                iccCardType = ColorOSTelephonyManager.getDefault(paramContext).colorGetIccCardTypeGemini(paramInt);
                Class<?> objectClazz = Class.forName("android.telephony.ColorOSTelephonyManager");
                Method getDefaultMethod = objectClazz.getDeclaredMethod("getDefault", Context.class);
                getDefaultMethod.setAccessible(true);
                Object colorOSTelephonyManager =  getDefaultMethod.invoke(null, paramContext);
                Method colorSoftSimCardSlotIdMethod = objectClazz.getDeclaredMethod("colorGetIccCardTypeGemini",int.class);
                iccCardType = (String)colorSoftSimCardSlotIdMethod.invoke(colorOSTelephonyManager,paramInt);

                bool2 = bool3;
                if (!"CSIM".equals(iccCardType)) {
                    bool2 = bool3;
                    if (!"RUIM".equals(iccCardType)) {
                        bool2 = bool1;
                        Log.d("OppoSimCardUtils", "isCTCCCard: iccCardType = " + iccCardType + ", slotId = " + paramInt);
                        return bool1;
                    }
                }
            } catch (Throwable throwable) {
                return bool2;
            }
            bool1 = true;
        }
    }
}
