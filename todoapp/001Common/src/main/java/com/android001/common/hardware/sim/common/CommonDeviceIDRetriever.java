package com.android001.common.hardware.sim.common;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import com.android001.common.CommonUtilMethods;
import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;

import java.lang.reflect.Method;

/**
 * 通用获取deviceId方式
 */

public class CommonDeviceIDRetriever extends BaseDeviceIDRetriever {

    private CommonDeviceIDRetriever() {
        init();
    }

    static class CommonDeviceIDManagerHolder {
        private static final CommonDeviceIDRetriever INSTANCE = new CommonDeviceIDRetriever();
    }

    public static CommonDeviceIDRetriever getInstance() {
        return CommonDeviceIDManagerHolder.INSTANCE;
    }

    //---


    @Override
    public void addDeviceId() {
        try {
            //1. 获取TelephonyManager
            TelephonyManager telephonyManager = CommonUtilMethods.getTelephonyManager();
            DeviceIdDAO.getInstance().addDeviceId(telephonyManager.getDeviceId());
            //2. 获取slotIds
            int[] slotIds = getSlotIds(telephonyManager);
            //3. deviceId
            addMeidOrImei(telephonyManager,"getMeid",slotIds);
            addMeidOrImei(telephonyManager,"getImei",slotIds);
            addMeidOrImei(telephonyManager,"getDeviceId",slotIds);
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
    public static void addMeidOrImei(TelephonyManager telephonyManager,String methodName ,int[] slotIds)
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
            for (int i = 0; i < slotIds.length; i++) {
                String deviceId = (String) getMeidMethod2.invoke(telephonyManager,slotIds[i]);
                DeviceIdDAO.getInstance().addDeviceId(deviceId);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


}
