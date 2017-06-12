package com.android001.service.alarmService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/12 上午10:43
 */

public class PendingAlarm {

    private int mType;
    private long mTriggerAtMillis;
    private String mTag;
    private PendingIntent mPendingIntent;//必选的---抽取





    private PendingAlarm(int mType, long mTriggerAtMillis, String mTag, PendingIntent mPendingIntent) {
        this.mType = mType;
        this.mTriggerAtMillis = mTriggerAtMillis;
        this.mTag = mTag;
        this.mPendingIntent = mPendingIntent;
    }


    public int getType() {
        return mType;
    }

    public long getTriggerAtMillis() {
        return mTriggerAtMillis;
    }

    public String getTag() {
        return mTag;
    }

    public PendingIntent getPendingIntent() {
        return mPendingIntent;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTriggerAtMillis(long mTriggerAtMillis) {
        this.mTriggerAtMillis = mTriggerAtMillis;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public void setPendingIntent(PendingIntent mPendingIntent) {
        this.mPendingIntent = mPendingIntent;
    }

    public static PendingIntent getActivityPendingIntent (String packageName,String activityName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ComponentName cn = new ComponentName(packageName,activityName);
        intent.setComponent(cn);
        PendingIntent pi = PendingIntent.getActivity(com.android001.service.app.AppHolder.getContext(),0,intent,0);
        return pi;
    }




    public static class PendingAlarmBuilder {

        private int mType;
        private long mTriggerAtMillis;
        private String mTag;
        private PendingIntent mPendingIntent;

        public PendingAlarmBuilder(PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
        }

        public PendingAlarmBuilder setType(int type) throws Exception {
            if (0 <= type && type <= 3) {
                this.mType = type;
            } else {
                this.mType = AlarmManager.RTC_WAKEUP;//DEFAULT VALUE
            }
            return this;
        }

        public PendingAlarmBuilder setTriggerAtMillis(long triggerAtMillis) {
            if (triggerAtMillis < System.currentTimeMillis())
                triggerAtMillis = System.currentTimeMillis();
            this.mTriggerAtMillis = triggerAtMillis;
            return this;
        }

        public PendingAlarmBuilder setTag(String tag) {
            if (TextUtils.isEmpty(tag))
                tag = "default";
            this.mTag = tag;
            return this;
        }

        public PendingAlarmBuilder setPendingIntent(PendingIntent pendingIntent) throws NullPointerException {
            if (null == pendingIntent)
                throw new NullPointerException("pendingIntent can not be null");
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public PendingAlarm build() {


//            Log.e("android001",
//                    "闹钟类型type = "+ mType+"\n"+
//                            "触发时间triggerAtMillis = "+ mTriggerAtMillis+"\n"+
//                            "标签 tag = "+ mTag+"\n"+
//                            "将要执行任务pendingIntent = "+ mPendingIntent.toString()+"\n"
//            );

            PendingAlarm pendingAlarm = new PendingAlarm(mType, mTriggerAtMillis, mTag, mPendingIntent);
            return pendingAlarm;
        }
    }


}
