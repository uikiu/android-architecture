package com.android001.common.hardware.sim.meitu;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android001.common.CommonUtilMethods;
import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SDKTools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/16 上午11:31
 */

public class MeituDeviceIDRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "MeituDeviceIDRetriever";

    @Override
    public void addDeviceId() {


        try {
            Log.e(TAG,"开始获取美图手机的串号：imei&meid");
            int[] slotIds = getSlotIds(CommonUtilMethods.getTelephonyManager());
            //1. 通过PhoneFactory获取phone对象
            Class<?> meiTuPhoneFactoryClazz = Class.forName("com.android.internal.telephony.PhoneFactory");
            // 修改私有常量sMadeDefaults
            Field field = meiTuPhoneFactoryClazz.getDeclaredField("sMadeDefaults");
            field.setAccessible(true);
//            modifieresField.setInt(field,field.getModifiers() & ~Modifier.FINAL);
            field.set(Boolean.class,true);
            for (int i = 0; i < slotIds.length; i++) {
                // 获取getphone方法
                Method getPhoneMethod = meiTuPhoneFactoryClazz.getMethod("getPhone", int.class);//public static Phone getPhone(int phoneId)
                getPhoneMethod.setAccessible(true);
                Object phoneObject = getPhoneMethod.invoke(null, slotIds[i]);//此行出错InvocationTargetException
                //2. 获取phone的clazz类
                Class<?> phoneClazz = Class.forName("com.android.internal.telephony.Phone");
                //3. 获取meid
                Method getMeidMethod = phoneClazz.getDeclaredMethod("getMeid");
                getMeidMethod.setAccessible(true);
                String meid = (String) getMeidMethod.invoke(phoneObject);
                DeviceIdDAO.getInstance().addDeviceId(meid);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"获取手机串号出错："+e.getMessage());
        }


    }

    public int getPhoneCount() {
        TelephonyManager tm =
                CommonUtilMethods.getTelephonyManager();
        if (SDKTools.getSDKToolsInstance().after(23))
            return tm.getPhoneCount();
        else
            return 0;
//        return 0;
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
}
