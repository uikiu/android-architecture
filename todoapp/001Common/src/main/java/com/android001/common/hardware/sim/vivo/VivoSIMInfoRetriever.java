package com.android001.common.hardware.sim.vivo;

import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.hardware.sim.vivo.utils.VivoDialerUtils;
import com.android001.common.hardware.sim.vivo.utils.VivoSimCardUtils;
import com.android001.common.hardware.sim.vivo.utils.VivoTelephonyUtils;

/**
 * Created by xixionghui on 2017/5/16.
 */

public class VivoSIMInfoRetriever extends BaseDeviceIDRetriever {


    private boolean isCDMADevices() {
        return VivoDialerUtils.isCTCC();
    }

    private boolean isUnicomDevices() {
        return VivoDialerUtils.isUnicom();
    }

    private boolean isFullNetDevices() {
        return VivoDialerUtils.isFULL();
    }

    private boolean isMutiCard() {
        return VivoSimCardUtils.isMultiSimCard();
    }


    @Override
    public void addDeviceId() {

        try {
            String imeiOfCDMADevice = VivoTelephonyUtils.getImeiOfCdmaPhone();
            String meidOfCDMADevice = VivoTelephonyUtils.getMeidOfCdmaPhone();
            String imei1 = VivoTelephonyUtils.getImei(0);
            String imei2 = VivoTelephonyUtils.getImei(1);
            String meid = VivoTelephonyUtils.getMeid();

            DeviceIdDAO.getInstance().addDeviceId(imeiOfCDMADevice);
            DeviceIdDAO.getInstance().addDeviceId(meidOfCDMADevice);
            DeviceIdDAO.getInstance().addDeviceId(imei1);
            DeviceIdDAO.getInstance().addDeviceId(imei2);
            DeviceIdDAO.getInstance().addDeviceId(meid);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }


}
