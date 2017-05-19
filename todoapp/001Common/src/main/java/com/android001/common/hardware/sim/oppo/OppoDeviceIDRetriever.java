package com.android001.common.hardware.sim.oppo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android001.common.BuildConfig;
import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xixionghui on 2017/5/18.
 */

public class OppoDeviceIDRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "OppoDeviceIDRetriever";


    @Override
    public void addDeviceId() {
        addIMEIAndMeid();
    }

    private void addIMEIAndMeid() {
        addMeid();
        addIMEI();
    }

    private void addMeid() {
        Log.e(TAG,"start add oppo meid");
        try {
            Class<?> objectClazz = Class.forName("android.telephony.ColorOSTelephonyManager");
            Method getDefaultMethod = objectClazz.getDeclaredMethod("getDefault", Context.class);
            getDefaultMethod.setAccessible(true);
            Object colorOSTelephonyManager = getDefaultMethod.invoke(null, AppHolder.getContext());

            Method colorGetMeidMethod = objectClazz.getDeclaredMethod("colorGetMeid", int.class);
            String meid1 = (String) colorGetMeidMethod.invoke(colorOSTelephonyManager, 0);
            String meid2 = (String) colorGetMeidMethod.invoke(colorOSTelephonyManager, 1);
//            Log.e(TAG,"获取到的oppo的meid = "+meid1+" , "+meid2);
//            String meid = meid1;
//            if (!TextUtils.isEmpty(meid2)) {
//                if (meid2.startsWith("A")) {
//                    meid = meid2;
//                }
//            }

            DeviceIdDAO.getInstance().addDeviceId(meid1);
            DeviceIdDAO.getInstance().addDeviceId(meid2);
        } catch (Throwable e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG)
                Log.e(TAG,"oppo add meid error："+e.getMessage());
        }
    }

    private void addIMEI() {
        Log.e(TAG,"start add oppo meid");
        try {
            Class<?> objectClazz = Class.forName("android.telephony.ColorOSTelephonyManager");
            Method getDefaultMethod = objectClazz.getDeclaredMethod("getDefault", Context.class);
            getDefaultMethod.setAccessible(true);
            Object colorOSTelephonyManager = getDefaultMethod.invoke(null, AppHolder.getContext());

            Method colorGetMeidMethod = objectClazz.getDeclaredMethod("colorGetImei", int.class);
            String imei1 = (String) colorGetMeidMethod.invoke(colorOSTelephonyManager, 0);
            String imei2 = (String) colorGetMeidMethod.invoke(colorOSTelephonyManager, 1);

            DeviceIdDAO.getInstance().addDeviceId(imei1);
            DeviceIdDAO.getInstance().addDeviceId(imei2);
            Log.e(TAG,"获取到的oppo的imei = "+imei1+" , "+imei2);
        } catch (Throwable e) {
            e.printStackTrace();
//            if (BuildConfig.DEBUG)
                Log.e(TAG,"oppo add imei error："+e.getMessage());
        }
    }

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
}
