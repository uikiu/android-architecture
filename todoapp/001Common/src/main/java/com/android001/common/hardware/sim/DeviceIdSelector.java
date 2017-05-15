package com.android001.common.hardware.sim;

import com.android001.common.hardware.sim.common.CommonDeviceIDRetriever;
import com.android001.common.hardware.sim.huaweiContact.HWDeviceIDRetriever;
import com.android001.common.os.others.OSUtils;

import static com.android001.common.os.others.OSUtils.ROM_TYPE.FLYME;
import static com.android001.common.os.others.OSUtils.ROM_TYPE.MIUI;

/**
 * Created by xixionghui on 2017/5/15.
 */

public class DeviceIdSelector {

    private DeviceIdSelector() {
    }

    private static class DeviceIdSelectorHolder {
        private static final DeviceIdSelector INSTANCE = new DeviceIdSelector();
    }

    public DeviceIdSelector getInstance() {
        return DeviceIdSelectorHolder.INSTANCE;
    }

    public void addDeviceID() {
        OSUtils.ROM_TYPE romType = OSUtils.getROMName();
        switch (romType) {
            case UNKNOW://-----未知系统：启用默认
                CommonDeviceIDRetriever.getInstance().addDeviceId();
            case MIUI://小米
                break;
            case FLYME://魅族
                break;
            case EMUI://华为
                HWDeviceIDRetriever.getInstance().addDeviceId();
                break;
            case COLOR_OS://oppo
                break;
            case FUNTOUCH_UI://VIVO
                break;
            case EUI://乐视
                break;
            case COOLUI://酷派
                break;
            case AMIGO://金立
                break;
            case TOUCH_WIZ://三星
                break;
            default://-----未知系统：启用默认
                CommonDeviceIDRetriever.getInstance().addDeviceId();
                break;
        }
    }
}
