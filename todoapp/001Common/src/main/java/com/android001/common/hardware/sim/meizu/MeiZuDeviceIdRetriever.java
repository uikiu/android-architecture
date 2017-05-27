package com.android001.common.hardware.sim.meizu;

import android.util.Log;

import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.os.android.SystemPropertiesAccessor;

import java.util.Arrays;

/**
 * Created by android001 on 2017/5/26.
 */

public class MeiZuDeviceIdRetriever extends BaseDeviceIDRetriever {
    private static final String TAG = "MeiZuDeviceIdRetriever";

    @Override
    public void addDeviceId() {
        try {
            String imei = SystemPropertiesAccessor.get("ril.gsm.imei");
            String[] imeiArrays = imei.split(",");
            if (null != imeiArrays && imeiArrays.length > 0) {
                for (int i = 0; i < imeiArrays.length; i++) {
                    DeviceIdDAO.getInstance().addDeviceId(imeiArrays[i]);
                }
            }

            String meid = SystemPropertiesAccessor.get("ril.cdma.meid");
            DeviceIdDAO.getInstance().addDeviceId(meid);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
