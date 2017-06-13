package com.android001.common.app.call;

import android.content.ComponentName;

/**
 * Created by Bison.Wensent on 16/7/18.
 */
public class ActiveApp {
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String appName;//app名称

    private String packageName;//包名

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private String activityName;//类名

    private int priority;//优先级

    private String id;//apk包id

    private boolean enable = false;//是否允许调起

    public ActiveApp clone() {
        ActiveApp app = new ActiveApp();
        app.id = this.id;
        app.enable = this.enable;
        app.appName = this.appName;
        app.packageName = this.packageName;
        app.activityName = this.activityName;
        app.priority = this.priority;
        return app;
    }

    @Override
    public String toString() {
        return "ActiveApp{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", activityName='" + activityName + '\'' +
                ", priority=" + priority +
                ", id='" + id + '\'' +
                ", enable=" + enable +
                '}';
    }

    //将此方法枚举化
    public static ActiveApp QQRsevenApp() {
        ActiveApp activeApp = new ActiveApp();
        activeApp.setEnable(true);
        activeApp.setPackageName("com.tencent.qqpim");
        activeApp.setActivityName("com.tencent.qqpim.ui.QQPimAndroid");
        return activeApp;
    }

    public enum CommonActiveApp {
        //实例对象
        QQRsevenApp("com.tencent.qqpim", "com.tencent.qqpim.ui.QQPimAndroid", true);

        //实例构造
        private ActiveApp mActiveApp = new ActiveApp();

        private CommonActiveApp(String packageName, String activityName, boolean enable) {
            mActiveApp.setPackageName(packageName);
            mActiveApp.setActivityName(activityName);
            mActiveApp.setEnable(enable);
        }

        public ActiveApp getActiveApp() {
            return mActiveApp;
        }
    }
}
