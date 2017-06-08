package com.android001.common.app.call;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android001.common.app.AppHolder;
import com.android001.common.app.utils.AppTool;

/**
* class design：
* @author android001
* created at 2017/6/7 下午3:45
*/
public abstract class BaseCaller implements Caller{

    private final static String NONE_ACTIVITY= "none";

    private final static String SPOT = ".";

    protected final ActiveApp activeApp;

    private Intent intent;

    public BaseCaller(ActiveApp activeApp){
        this.activeApp = activeApp;
    }

    @Override
    public boolean fitCall() {
        //此app是否允许调起
        if(!activeApp.isEnable()){
            return false;
        }
        //是否已经安装了此apk
        final String pkgName = activeApp.getPackageName();
        if(!AppTool.isAppInstalled(AppHolder.getContext(),pkgName)){
            return false;
        }
        //activity是否为空
        final String activity = activeApp.getActivityName();
        PackageManager pm = AppHolder.getContext().getPackageManager();
        if(TextUtils.isEmpty(activity)||NONE_ACTIVITY.equals(activity)){//activity为空---尝试使用仅通过packageName启动app
            intent = pm.getLaunchIntentForPackage(pkgName);
            return intent != null;
        }else {
            String fullClassName = activity.startsWith(SPOT)?pkgName+activity:activity;//补全：完全限定名
            intent = new Intent();
            intent.setClassName(pkgName,fullClassName);//设定Intent的packageName和className
            boolean exist = intent.resolveActivity(pm)!=null;
            if(exist){
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            return exist;
        }

    }

    public abstract void call(Context context, Intent intent);
    @Override
    public void call(Context context) {
        if(intent==null){
            final String pkgName = activeApp.getPackageName();
            final String activity = activeApp.getActivityName();
            PackageManager pm = AppHolder.getContext().getPackageManager();
            if(TextUtils.isEmpty(activity)||NONE_ACTIVITY.equals(activity)){
                intent = pm.getLaunchIntentForPackage(pkgName);
            }else {
                String fullClassName = activity.startsWith(SPOT)?pkgName+activity:activity;
                intent = new Intent();
                intent.setClassName(pkgName,fullClassName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }

        call(context,intent);
    }
}
