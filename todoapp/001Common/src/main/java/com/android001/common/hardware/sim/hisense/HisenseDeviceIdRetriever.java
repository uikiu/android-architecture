package com.android001.common.hardware.sim.hisense;

import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SDKTools;
import com.android001.common.os.android.SystemPropertiesAccessor;

import android.os.Build;
import android.provider.Settings.Global;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

/**
* class design：
* @author android001
* created at 2017/6/2 上午9:58
*/

public class HisenseDeviceIdRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "HisenseRetriever";
    @Override
    public void addDeviceId() {

        try {
            if (SDKTools.getSDKToolsInstance().equalOrGrater(17)) {
                String hisenseDeviceId = Global.getString(AppHolder.getContext().getContentResolver(), "cdma_meid_with_no_card");
//                Log.e(TAG,"获取到的海信设备ID = "+hisenseDeviceId);
                DeviceIdDAO.getInstance().addDeviceId(hisenseDeviceId);

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void printField ()
    {

        try {
            Class<?> globalClazz = Class.forName("android.provider.Settings$Global");
            Field[] fields= globalClazz.getDeclaredFields();
            if (null!=fields&&fields.length!=0)
            {
                for (int i = 0 ; i < fields.length ; i++)
                {
                    Field file = fields[i];
                    file.setAccessible(true);
                    Log.e(TAG,file.getName()+"  =  "+fields[i].get(file.getName()));
                }
            } else
            {
                Log.e(TAG,"获取到的字段为空");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
