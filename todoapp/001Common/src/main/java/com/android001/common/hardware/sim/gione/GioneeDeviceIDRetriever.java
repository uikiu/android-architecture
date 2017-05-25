package com.android001.common.hardware.sim.gione;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android001.common.CommonUtilMethods;
import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SDKTools;
import com.android001.common.os.android.SystemPropertiesAccessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xixionghui on 2017/5/23.
 */

public class GioneeDeviceIDRetriever extends BaseDeviceIDRetriever
{
    private static final String TAG = "GioneeDeviceIDRetriever";
    @Override
    public void addDeviceId()
    {
        cycleGetDeviceId();
        gnGetDeviceId();
    }

    private void test() {
        gnGetDeviceId();
        //com.android.internal.telephony.cdma.CDMALTEPhone
        //com.android.internal.telephony.cdma.CDMAPhone:public String getDeviceId()

//        try {
//            Class<?> cdmaPhoneClazz = Class.forName("com.android.internal.telephony.cdma.CDMAPhone");
//            Method getDeviceIdMethod = cdmaPhoneClazz.getDeclaredMethod("getDeviceId");
//            String meid = (String) getDeviceIdMethod.invoke(cdmaPhoneClazz.newInstance());
//            Log.e(TAG,"MEID = "+meid);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }

    }



    private void cycleGetDeviceId()
    {

        try {
            TelephonyManager telephonyManager = CommonUtilMethods.getTelephonyManager();

            int phoneCount;
            if (Build.VERSION.SDK_INT >= 23) {
                phoneCount = telephonyManager.getPhoneCount();
                int j = 0;
                while (j < phoneCount) {
                    String deviceId = telephonyManager.getDeviceId(j);//已经不被推荐使用了，另外是从sdk23开始使用的
                    DeviceIdDAO.getInstance().addDeviceId(deviceId);
                    j += 1;
                }
            } else {

            }


        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
     * mediatek://联发科技（公司名)
     * ExtensionManager.getInstance().getDialPadExtension().getSingleIMEI(paramString);
     */
    private void  addMediatekDeviceId() throws Exception
    {

            //1. 获取MPlugin的Clazz对象
            Class<?> mPluginClazz = Class.forName("com.mediatek.common.MPlugin");
            Method createInstanceMethod =  mPluginClazz.getMethod("createInstance",String.class, Context.class);
            createInstanceMethod.setAccessible(true);
            //2. 获取IDialPadExtension对象：mDialPadExtension = ((IDialPadExtension)MPlugin.createInstance(IDialPadExtension.class.getName(), sContext))
            Object idialPadExtensionObject =  createInstanceMethod.invoke(null,"IDialPadExtension",AppHolder.getContext());
            Log.e(TAG,"idialPadExtensionObject = "+ idialPadExtensionObject);

    }

    //---

    private void gnGetDeviceId() {
        String imei1 = SystemPropertiesAccessor.get("persist.radio.imei");
        String imei2 = SystemPropertiesAccessor.get("persist.sys.gn.imei");

        String meid1 = SystemPropertiesAccessor.get("persist.radio.meid");
        String meid2 = SystemPropertiesAccessor.get("persist.sys.gn.meid");

        Log.e(
              TAG,
              "IMEI1 = "+ imei1+"\n" +
              "IMEI2 = "+ imei2+"\n" +
              "MEID1 = "+ meid1+"\n" +
              "MEID2 = "+ meid2+"\n"
              );
        DeviceIdDAO.getInstance().addDeviceId(imei1);
        DeviceIdDAO.getInstance().addDeviceId(imei2);
        DeviceIdDAO.getInstance().addDeviceId(meid1);
        DeviceIdDAO.getInstance().addDeviceId(meid2);


    }
}
