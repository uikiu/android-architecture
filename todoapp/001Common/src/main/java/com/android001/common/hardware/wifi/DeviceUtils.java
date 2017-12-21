package com.android001.common.hardware.wifi;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * 参考：
 * @link https://yrom.net/blog/2015/10/28/android-m-getting-wifi-macaddress-1/
 * @link http://www.jianshu.com/p/a62ff95d8e17
 * shell命令
 */

public class DeviceUtils {


    public static String getWifiMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    public static final String SECURE_SETTINGS_BLUETOOTH_ADDRESS = "bluetooth_address";

    public static String getBluetoothMacAddress(Context mContext) {

        String address = "unKnow";

        try {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Log.d("deviceInfo", "device does not support bluetooth");
                return "device does not support bluetooth";
            }

            address = mBluetoothAdapter.getAddress();
            if (address.equals("02:00:00:00:00:00")) {

                try {

                    ContentResolver mContentResolver = mContext.getContentResolver();

                    address = Settings.Secure.getString(mContentResolver, SECURE_SETTINGS_BLUETOOTH_ADDRESS);

                } catch (Exception e) {

                }

            } else {

            }
        } catch (Exception e) {
        }

        return address;
    }
}
