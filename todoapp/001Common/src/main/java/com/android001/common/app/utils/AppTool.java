package com.android001.common.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
* class design：
* @author android001
* created at 2017/6/7 下午12:29
*/
public class AppTool {

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo pkg = packageManager.getPackageInfo(packageName, 0);
            return pkg != null;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static PackageInfo getPackageInfo(Context context, String pkgName) {
        try {
            return context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static PackageInfo getApkPackageInfo(Context context, String filePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(filePath, 0);
        if (pi != null) {
            pi.applicationInfo.sourceDir = filePath;
            pi.applicationInfo.publicSourceDir = filePath;
        }
        return pi;
    }

    public static void installApp(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static ApplicationInfo getBaseInfo(Context context, String pkg){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getApplicationInfo(pkg, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public static String crc(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, 0);
            return getApkFileSFCrc32(packageInfo.applicationInfo.sourceDir);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "null";
    }

    public static String getApkFileSFCrc32(String ApkFilePath) {
        long crc = 0xffffffff;

        try {
            File f = new File(ApkFilePath);
            ZipFile z = new ZipFile(f);
            Enumeration<? extends ZipEntry> zList = z.entries();
            ZipEntry ze;
            while (zList.hasMoreElements()) {
                ze = zList.nextElement();
                if (ze.isDirectory()
                        || !ze.getName().contains("META-INF")
                        || !ze.getName().contains(".SF")) {
                } else {
                    crc = ze.getCrc();
                    break;
                }
            }
            z.close();
        } catch (Exception ignored) {
        }
        return Long.toHexString(crc);
    }
}
