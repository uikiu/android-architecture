package com.android001.common.hardware.sim.leEco;

import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android001.common.CommonUtilMethods;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SDKTools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by android001 on 2017/5/26.
 */

public class LeEcoDeviceIdRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "LeEcoDeviceIdRetriever";
    @Override
    public void addDeviceId() {

        try {
            //1. 获取TelephonyManager
            TelephonyManager telephonyManager = CommonUtilMethods.getTelephonyManager();

            //2. 获取meid
            addMeidOrImei(telephonyManager,"getMeid");
            //3. 获取imei
            addMeidOrImei(telephonyManager,"getImei");
            //3. 获取deviceId
            addMeidOrImei(telephonyManager,"getDeviceId");
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private static int[] getSlotIds(TelephonyManager telephonyManager) {
        int[] iArr = null;
        int phoneCount = 3;
        try {
            if (Build.VERSION.SDK_INT >= 23)
                phoneCount = telephonyManager.getPhoneCount();

            iArr = new int[phoneCount];
            for (int i = 0; i < phoneCount; i++) {
                iArr[i] = i;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (null == iArr)
            iArr = new int[]{0, 1, 2};

        return iArr;

    }


    //public String getMeid()
    //public String getMeid(int slotIndex)
    public static void addMeidOrImei(TelephonyManager telephonyManager,String methodName)
    {
        try {
            Class<?> telephonyManagerClazz = Class.forName("android.telephony.TelephonyManager");

            //public String getMeid()
            Method getMeidMethod1 = telephonyManagerClazz.getDeclaredMethod(methodName);
            if (null!=getMeidMethod1)
            {
                getMeidMethod1.setAccessible(true);
                String deviceIdDefault = (String) getMeidMethod1.invoke(telephonyManager);
                DeviceIdDAO.getInstance().addDeviceId(deviceIdDefault);
            }
            //public String getMeid(int slotIndex)
            Method getMeidMethod2 = telephonyManagerClazz.getDeclaredMethod(methodName,int.class);
            if (null==getMeidMethod2) return;
            getMeidMethod2.setAccessible(true);
            int[] slotIds = getSlotIds(telephonyManager);
            for (int i = 0; i < slotIds.length; i++) {
                String deviceId = (String) getMeidMethod2.invoke(telephonyManager,slotIds[i]);
                DeviceIdDAO.getInstance().addDeviceId(deviceId);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
