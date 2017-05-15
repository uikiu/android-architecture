package com.android001.common.hardware.sim.common;

import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by xixionghui on 2017/5/11.
 */

public class DeviceIdManager {


    private static final String SPLIT = ",";
    private final HashSet<String> IM_EI_SET = new HashSet<>();
    private static final String DEFAULT_DEVICE_ID = "100000000000000";

    private DeviceIdManager() {
    }

    static class DeviceIdManagerHolder {
        private static final DeviceIdManager INSTANCE = new DeviceIdManager();
    }

    public static DeviceIdManager getInstance() {
        return DeviceIdManagerHolder.INSTANCE;
    }

    private boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        if (input.equals("null")) {
            return true;
        }
        if (input.replaceAll(" ", "").equals("{}")) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    private boolean isValidDeviceId(String deviceId) {
        if (isEmpty(deviceId))
            return false;
        Pattern pattern = Pattern.compile("^[0-9A-Fa-f]{13,18}+$");
        if ((!pattern.matcher(deviceId).matches())
                || (deviceId.indexOf("000000000") != -1) || (deviceId.indexOf("111111111") != -1)
                || (deviceId.indexOf("222222222") != -1) || (deviceId.indexOf("333333333") != -1)
                || (deviceId.indexOf("444444444") != -1) || (deviceId.indexOf("555555555") != -1)
                || (deviceId.indexOf("666666666") != -1) || (deviceId.indexOf("777777777") != -1)
                || (deviceId.indexOf("888888888") != -1) || (deviceId.indexOf("999999999") != -1))
            return false;
        return true;
    }


    public void addDeviceId(String deviceId) {
        if (isValidDeviceId(deviceId)) {
            deviceId.toUpperCase(Locale.getDefault());
            IM_EI_SET.add(deviceId);
        }
    }

    private String translateSetToString(HashSet<String> set) {
        if (set == null || set.size() <= 0) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (String deviceId : set) {
            if (isValidDeviceId(deviceId)) {
                buffer.append(deviceId).append(SPLIT);
            }
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }

        return buffer.toString();

    }

    private boolean isCompleteCheckImEi() {
        return IM_EI_SET.size() > 0;
    }

    public String getImEiAnyWay() {

        if (!isCompleteCheckImEi()) {
            return DEFAULT_DEVICE_ID;
        }
        return translateSetToString(IM_EI_SET);
    }
}
