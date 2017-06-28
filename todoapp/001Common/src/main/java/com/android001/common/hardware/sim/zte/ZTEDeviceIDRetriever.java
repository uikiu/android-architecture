package com.android001.common.hardware.sim.zte;

import android.print.PageRange;
import android.util.Log;

import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SystemPropertiesAccessor;
import com.orhanobut.logger.Logger;

/**
* class design：
* @author android001
* created at 2017/6/28 上午11:53
*/

public class ZTEDeviceIDRetriever extends BaseDeviceIDRetriever {
//    private static final String TAG = ZTEDeviceIDRetriever.class.getSimpleName();
    @Override
    public void addDeviceId() {

        try {
            String imei = SystemPropertiesAccessor.get("gsm.build.imei");
            String imei2 = SystemPropertiesAccessor.get("gsm.build.imei2");
            String meid =  SystemPropertiesAccessor.get("gsm.build.meid");

            DeviceIdDAO.getInstance().addDeviceId(imei);
            DeviceIdDAO.getInstance().addDeviceId(imei2);
            DeviceIdDAO.getInstance().addDeviceId(meid);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
