package com.android001.common.hardware.sim.gione;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.UUID;
import java.util.regex.Pattern;

@SuppressLint({"DefaultLocale"})
public class SysInfoUtil {
    private static final boolean DEBUG = false;
    private static final String IIMEI_FILENAME = "iimei";
    private static final Pattern PATTERN;
    private static final String UID_FILENAME = "UID";
    private static String mDeviceID;
    private static final String TAG  = "SysInfoUtil";

    static {
        PATTERN = Pattern.compile("^0+$");
        mDeviceID = null;
    }

    private static void genDeviceID(Context context, File file) {
        try {
            String cPUSerial = getCPUSerial();//获取CPU序列号
            cPUSerial = !invalidDeviceId(context, cPUSerial) ? "C" + cPUSerial : null;
            if (cPUSerial == null) {
                cPUSerial = getHardwareSerialNumber();
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "S" + cPUSerial : null;
            }
            if (cPUSerial == null) {
                cPUSerial = getAndroidID(context);
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "A" + cPUSerial : null;
            }
            if (cPUSerial == null) {
                cPUSerial = ((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getSubscriberId();
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "I" + cPUSerial : null;
            }
            if (cPUSerial == null) {
                cPUSerial = getMacAddress(context);
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "M" + cPUSerial : null;
            }
            if (cPUSerial == null) {
                cPUSerial = "U" + genUUID();
            }
            mDeviceID = cPUSerial;
//            writeDeviceIdFile(context, mDeviceID, file);
        } catch (Exception e) {
        }
    }

    public static String  genDeviceID(Context context) {
        try {
            String cPUSerial = getCPUSerial();
            cPUSerial = !invalidDeviceId(context, cPUSerial) ? "C" + cPUSerial : null;
            Log.e(TAG,"getCPUSerial = "+getCPUSerial());

            if (cPUSerial == null) {
                cPUSerial = getHardwareSerialNumber();
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "S" + cPUSerial : null;
            }
            Log.e(TAG,"getHardwareSerialNumber = "+getHardwareSerialNumber());

            if (cPUSerial == null) {
                cPUSerial = getAndroidID(context);
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "A" + cPUSerial : null;
            }
            Log.e(TAG,"getAndroidID = "+getAndroidID(context));


            if (cPUSerial == null) {
                cPUSerial = ((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getSubscriberId();
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "I" + cPUSerial : null;
            }
            Log.e(TAG,"getSubscriberId = "+((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getSubscriberId());

            if (cPUSerial == null) {
                cPUSerial = getMacAddress(context);
                cPUSerial = !invalidDeviceId(context, cPUSerial) ? "M" + cPUSerial : null;
            }
            Log.e(TAG,"getMacAddress = "+getMacAddress(context));

            if (cPUSerial == null) {
                cPUSerial = "U" + genUUID();
            }
            Log.e(TAG,"genUUID = "+genUUID());

            mDeviceID = cPUSerial;
//            writeDeviceIdFile(context, mDeviceID, file);
        } catch (Exception e) {
        }
        return  mDeviceID;
    }

    private static String genUUID() {
        String str = null;
        try {
            str = UUID.randomUUID().toString().replaceAll("-", CardHotel.KEY_ROOM_COUNT).replace(":", CardHotel.KEY_ROOM_COUNT).toLowerCase();
        } catch (Exception e) {
        }
        return str;
    }

    private static String getAndroidID(Context context) {
        String str = null;
        if (VERSION.SDK_INT >= 8) {
            try {
                str = Secure.getString(context.getContentResolver(), "android_id");
                if (str != null) {
                    str = str.toLowerCase();
                }
            } catch (Exception e) {
            }
        }
        return str;
    }

    private static String getCPUSerial() {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cat /proc/cpuinfo").getInputStream()));
            for (int i = 1; i < 100; i++) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                readLine = readLine.toLowerCase();
                int indexOf = readLine.indexOf("serial");
                int indexOf2 = readLine.indexOf(":");
                if (indexOf > -1 && indexOf2 > 0) {
                    str = readLine.substring(indexOf2 + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
        }
        return str == null ? str : str.toLowerCase();
    }

    /*
    public static String getDeviceId(Context context) {
        String deviceId;
        synchronized (SysInfoUtil.class) {
            try {
                if (TextUtils.isEmpty(mDeviceID)) {
                    deviceId = ((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getDeviceId();
                    if (invalidDeviceId(context, deviceId)) {
                        File file = new File(context.getFilesDir(), UID_FILENAME);//路径：data/data/packageName/UID
                        if (file.exists()) {//UID文件存在
                            mDeviceID = readDeviceIdFile(context, file);
                            if (TextUtils.isEmpty(mDeviceID)) {
                                genDeviceID(context, file);
                                mDeviceID = readDeviceIdFile(context, file);
                            }
                        } else {//UID文件不存在
                            genDeviceID(context, file);
                            mDeviceID = readDeviceIdFile(context, file);
                        }
                    } else {
                        mDeviceID = deviceId;
                    }
                }
                deviceId = mDeviceID != null ? mDeviceID : CardHotel.KEY_ROOM_COUNT;
            } catch (Throwable th) {
                Class cls = SysInfoUtil.class;
            }
        }
        return deviceId;
    }
    */

    private static String getHardwareSerialNumber() {
        return VERSION.SDK_INT < 9 ? null : Build.SERIAL;
    }

    public static String getIMEI(Context context) {
        try {
            return ((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getDeviceId();
        } catch (Throwable th) {
            return null;
        }
    }

    private static String getMacAddress(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) getSystemService(context, "wifi")).getConnectionInfo();
            if (connectionInfo == null) {
                return null;
            }
            String macAddress = connectionInfo.getMacAddress();
            if (macAddress == null) {
                return macAddress;
            }
            try {
                return macAddress.replaceAll("-", CardHotel.KEY_ROOM_COUNT).replaceAll(":", CardHotel.KEY_ROOM_COUNT).toLowerCase();
            } catch (Exception e) {
                return macAddress;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public static String getPhoneNumber(Context context) {
        return ((TelephonyManager) getSystemService(context, JsonKey.KEY_PHONE)).getLine1Number();
    }

    public static int[] getScreenPixels(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static String getScreenPixelsString(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        return String.valueOf(displayMetrics.heightPixels) + "x" + String.valueOf(i);
    }

    public static Object getSystemService(Context context, String str) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        return context.getSystemService(str);
    }

    private static boolean invalidDeviceId(Context context, String str) {
        Throwable th;
        InputStream inputStream;
        if (TextUtils.isEmpty(str) || isAllZeroes(str)) {
            return true;
        }
        InputStream openLatestInputFile;
        try {
            openLatestInputFile = NovoFileUtil.openLatestInputFile(context, IIMEI_FILENAME);
            if (openLatestInputFile != null) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openLatestInputFile));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        try {
                            if (Pattern.compile(readLine).matcher(str).matches()) {
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    if (openLatestInputFile == null) {
                        return true;
                    }
                    try {
                        openLatestInputFile.close();
                        return true;
                    } catch (Exception e2) {
                        return true;
                    }
                } catch (Exception e3) {
                    inputStream = openLatestInputFile;
                } catch (Throwable th22) {
                    th = th22;
                }
            }
            if (openLatestInputFile != null) {
                try {
                    openLatestInputFile.close();
                    return DEBUG;
                } catch (Exception e4) {
                    return DEBUG;
                }
            }
        } catch (Exception e5) {
            inputStream = null;
            if (inputStream != null) {
                try {
                    inputStream.close();
                    return DEBUG;
                } catch (Exception e6) {
                    return DEBUG;
                }
            }
            return DEBUG;
        } catch (Throwable th3) {
            th = th3;
            openLatestInputFile = null;
            if (openLatestInputFile != null) {
                try {
                    openLatestInputFile.close();
                } catch (Exception e7) {
                }
            }
//            throw th;
        }
        return DEBUG;
    }

    private static boolean isAllZeroes(String str) {
        return PATTERN.matcher(str).find();
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) getSystemService(context, "connectivity")).getNetworkInfo(1);
        } catch (Exception e) {
            networkInfo = null;
        }
        return networkInfo == null ? DEBUG : networkInfo.isConnected();
    }

    /*
    private static String readDeviceIdFile(Context context, File file) {
        RandomAccessFile randomAccessFile = null;
        Throwable th;
        String str = null;
        try {
            randomAccessFile = new RandomAccessFile(file, UpdateConfig.UPDATE_CONTAINER_REMOVE_TYPE);
            try {
                byte[] bArr = new byte[((int) randomAccessFile.length())];
                randomAccessFile.readFully(bArr);
                str = dr.b(new String(bArr), context.getPackageName());
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
//            String str2 = str;
//            th = th3;
//            randomAccessFile = str2;

            if (randomAccessFile != null) {

                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

//            throw th;
        }
        return str;
    }

    private static void writeDeviceIdFile(Context context, String str, File file) {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (!TextUtils.isEmpty(str)) {
            FileOutputStream fileOutputStream2;
            try {
                fileOutputStream2 = new FileOutputStream(file, DEBUG);
                try {
                    fileOutputStream2.write(dr.a(str, context.getPackageName()).getBytes());
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e2) {
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e3) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (Exception e4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                fileOutputStream = null;
                if (fileOutputStream != null) {

                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream2 = null;
                if (fileOutputStream2 != null) {

                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
//                throw th;
            }
        }
    }
    */

    interface JsonKey {
        public static final String KEY_CITY = "city";
        public static final String KEY_DESC = "des";
        public static final String KEY_INTRO_FLAG = "intro_flag";
        public static final String KEY_MOBILE_TYPE = "mobile_type";
        public static final String KEY_NAME = "name";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_SERVICE = "service";
    }
}
