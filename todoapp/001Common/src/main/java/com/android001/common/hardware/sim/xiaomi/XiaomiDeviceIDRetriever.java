package com.android001.common.hardware.sim.xiaomi;

import android.util.Log;

import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* class design：
* @author android001
* created at 2017/6/2 下午2:57
*/

public class XiaomiDeviceIDRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "XiaomiDeviceIDRetriever";
    @Override
    public void addDeviceId()
    {

        try {
            //获取miui.telephony.TelephonyManager的clazz
            Class<?> miuiTelephonyManagerClazz = Class.forName("miui.telephony.TelephonyManager");
            //获取TelephonyManager对象
            Method getDefaultMethod = miuiTelephonyManagerClazz.getDeclaredMethod("getDefault");
            Object miuiTelephonyManagerObject = getDefaultMethod.invoke(null);
            //执行getImeiList方法
            Method getImeiListMethod = miuiTelephonyManagerClazz.getDeclaredMethod("getImeiList");
            List<String> imeiList = (List<String>)getImeiListMethod.invoke(miuiTelephonyManagerObject);
            DeviceIdDAO.getInstance().addDeviceIDs(imeiList);
            //执行getMeidList方法
            Method getMeidListMethod = miuiTelephonyManagerClazz.getDeclaredMethod("getMeidList");
            List<String> meidList = (List<String>)getMeidListMethod.invoke(miuiTelephonyManagerObject);
            DeviceIdDAO.getInstance().addDeviceIDs(meidList);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
