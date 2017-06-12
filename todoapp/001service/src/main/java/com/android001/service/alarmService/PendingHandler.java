package com.android001.service.alarmService;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android001.service.app.AppHolder;

/**
 * class design：
 *
 * @author android001
 * created at 2017/6/9 下午3:59
 * 对将要发生的事情的封装
 * 包含：任务(报警)类型type、要执行的任务mCallabck、执行时间mTriggerAtMillis、任务的tag
 * int type,long triggerAtMillis,String tag,AlarmManager.OnAlarmListener listener, Handler handler
 */
public class PendingHandler {
    private final int mType;
    private final long mTriggerAtMillis;
    private final String mTag;
    private final Runnable mCallback;//在这里是AlarmListenerRunnable
    private final Handler mHandler;


    /**
     * @param type AlarmManager的type
     * @param triggerAtMillis 第一次触发时间
     * @param tag 标签
     * @param callback 将要执行的行为----替代PendingIntent
     */
    public PendingHandler(int type, long triggerAtMillis, String tag, Runnable callback, Handler handler) {
        mType = type;//type可能就没用了
        mTriggerAtMillis = triggerAtMillis;
        mTag = tag;
        mCallback = callback;
        mHandler = handler;
    }

    public PendingHandler(long triggerAtMillis, String tag, Runnable callback, Handler handler) {
        mTag = tag;
        mCallback = callback;
        mHandler = handler;
        mTriggerAtMillis = triggerAtMillis;
        mType = 0;//默认类型
    }



    public void dispatch() {
        if (mCallback != null) {
            Log.e("android001","任务非空");
//            mHandler.postAtTime(mCallback,mTriggerAtMillis+2*1000);
//            mHandler.post(mCallback);
            Log.e("android001","执行时间："+mTriggerAtMillis);
            mHandler.postAtTime(mCallback,mTriggerAtMillis);
//            mCallback.run();
        }else {
            Log.e("android001","任务空的");
        }
    }

    public Runnable getCallback() {
        return mCallback;
    }

    public String getTag() {
        return mTag;
    }

    public long getTriggerTimeMillis() {
        return mTriggerAtMillis;
    }


    //------------------------------------------------------------------------

    /**
     * 对报警要执行的任务的封装-----替代旧的PendingIntent
     * class design：
     * Runnable
     * @author android001
     * created at 2017/6/9 上午10:33
     * 未来要处理的任务的封装。
     * mlistener 为要处理的任务接口
     * handler 为要在那个线程中处理
     */
    public abstract class AlarmListenerRunnable implements Runnable {
        private final Handler mHandler;

        public AlarmListenerRunnable( Handler handler) {
            mHandler = handler;
        }

        @Override
        public void run() {
            if (mHandler != null) {
                mHandler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2017/6/11 要执行的任务 
                    }
                },mTriggerAtMillis);
            } else {
                // TODO: 2017/6/11 handler 为空不能执行，或者尝试执行默认 
            }
        }


        public abstract void doAction();
    }


}
