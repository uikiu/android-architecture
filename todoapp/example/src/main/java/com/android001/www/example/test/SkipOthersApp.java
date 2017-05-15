package com.android001.www.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xixionghui on 2017/5/3.
 */

public class SkipOthersApp {

    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 处理 搜索Intent跳转
     *
     * @param context
     */
    public void setSearchIntent(Context context) {
        int searchType = 0;//搜索 类型 1 搜狗搜索 2  百度搜索
        Intent intent = new Intent();
        if (isAppInstalled(context, "com.sogou.activity.src")) {
            intent.setAction(Intent.ACTION_MAIN);
            intent.setClassName("com.sogou.activity.src", "com.sogou.activity.src.SplashActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            LogUtil.d(TAG, "intent SOUGOU ");
            searchType = 1;
        } else if (!isAppInstalled(context, "com.sogou.activity.src") && isAppInstalled(context, "com.baidu.searchbox")) {
            intent.setAction(Intent.ACTION_MAIN);
            intent.setClassName("com.baidu.searchbox", "com.baidu.searchbox.SplashActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            LogUtil.d(TAG, "intent BAIDU ");
            searchType = 2;
        } else {
//            intent = new Intent(context, SearchActivity.class);
        }
        if (searchType == 1) {
//            PingManager.getInstance().reportUserAction4App(
//                    PingManager.KLAUNCHER_WIDGET_SOUGOU_SEARCH, context.getPackageName());
//            LogUtil.d(TAG, "PingManager SOUGOU ");
        } else if (searchType == 2) {
//            PingManager.getInstance().reportUserAction4App(
//                    PingManager.KLAUNCHER_WIDGET_BAIDU_SEARCH, context.getPackageName());
//            LogUtil.d(TAG, "PingManager BAIDU ");
        }
        context.startActivity(intent);
    }

}
