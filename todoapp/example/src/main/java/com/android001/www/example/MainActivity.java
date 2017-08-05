package com.android001.www.example;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android001.common.app.call.ActiveApp;
import com.android001.common.app.call.Caller;
import com.android001.common.app.call.UriCaller;
import com.android001.common.app.utils.ApkTool;
import com.android001.common.app.utils.Crc32Util;
import com.android001.common.hardware.sim.DeviceIdSelector;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.ndk.Hello;
import com.android001.service.alarmService.AlarmSetter;
import com.android001.service.alarmService.PendingAlarm;
import com.android001.service.commonService.CommonService;
import com.android001.ui.dialog.CommonAlertDialog;

import com.android001.common.app.call.example.QQRsevenCaller;
import com.android001.ui.dialog.CustomDialog;
import com.android001.www.example.notification.NotificationBuilder;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button mPrint, mDrawerNavigation;
    private final BroadcastReceiver mDevInfoReceiver = new DevInfoReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("启动完成");
        setContentView(R.layout.activity_main);
        mPrint = (Button) findViewById(R.id.print);
        mPrint.setOnClickListener(this);
        mDrawerNavigation = (Button) findViewById(R.id.drawerNavigation);
        mDrawerNavigation.setOnClickListener(this);
        //
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_TIME_CHANGED);
//        this.registerReceiver(new CalendarChangeReceiver(), filter);

        registerBroadcastReceiver();
    }


    @Override
    public void onClick(View v) {
//        DeviceIdSelector.getInstance().addDeviceID();
//        for(int i = 0 ;i <100; i ++) {
//        }
        DeviceIdSelector.addDeviceID();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("品牌："+ Build.BRAND+"\n");
        stringBuilder.append("型号："+ Build.MODEL+"\n");
        stringBuilder.append("IMEI&MEID："+ DeviceIdDAO.getInstance().getImEiAnyWay()+"\n");
        stringBuilder.append("当前时间："+ DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        stringBuilder.append("当前时间："+ System.currentTimeMillis());

        switch (v.getId()) {
            case R.id.print:
                Logger.e(stringBuilder.toString());
//                String launcherPackageName = getLauncherPackageName(this);
//                Log.e(TAG,"获取到当前正在运行的包名 = "+launcherPackageName);
//                skip2OtherApp();
//                showCustomDialog();
                logAppMessage();
                break;
            case R.id.drawerNavigation:
                showDialog("手机信息", stringBuilder.toString());
//                Intent intent = new Intent(this,CommonService.class);
//                startService(intent);

//                showCustomNotification();
//                Log.e(TAG,"java call native  : "+new Hello().helloJni());
                break;
        }
    }

    private void logAppMessage(){
        StringBuilder sbCounter = new StringBuilder("使用计数器中Crc32Util类获取到的app的crc32 和 md5分别是：");
        sbCounter.append(Crc32Util.crc(this,this.getPackageName())).append("  |  ")
                .append(Crc32Util.md5(this,this.getPackageName()));
        Log.e(TAG,sbCounter.toString());

        try {
            ApplicationInfo applicationInfo = this.getPackageManager().getApplicationInfo(
                    this.getPackageName(), PackageManager.GET_META_DATA);
            StringBuilder sbLauncher = new StringBuilder("使用launcher中的pingManager类获取到的app的crc32 和 md5分别是：");
            sbLauncher.append(ApkTool.getApkFileSFCrc32(applicationInfo.sourceDir)).append("  |  ")
                    .append(ApkTool.getFileMD5(applicationInfo.sourceDir));
            Log.e(TAG,sbLauncher.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static final int  NOTIFICATION_ID = 99;

    private void showCustomNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent  = new Intent(Intent.ACTION_VIEW);
        ComponentName cn = new ComponentName("com.UCMobile","com.UCMobile.main.UCMobile");
        intent.setComponent(cn);
        intent.setData(UriCaller.getIntentDataUri("http://www.ifeng.com"));
        Notification notification = NotificationBuilder.buildNotification("看新闻？","找凤凰",intent);
        manager.notify(NOTIFICATION_ID,notification);
    }

//    private void cancleNotification(){
//        manager.cancel(NOTIFICATION_ID);
//    }


    /**
     * show dialog
     */
    private CustomDialog customDialog;
    private void showCustomDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        customDialog = builder.cancelTouchout(false)
                .view(com.android001.ui.R.layout.layout_custom_dialog)
                .heightDimenRes(com.android001.ui.R.dimen.dialog_custom_height)
                .widthDimenRes(com.android001.ui.R.dimen.dialog_custom_width)
                .style(com.android001.ui.R.style.Dialog)
                .addViewOnClick(com.android001.ui.R.id.btn_left, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                })
                .addViewOnClick(com.android001.ui.R.id.btn_right, new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        skip2OtherApp();
                    }
                })
                .build();
        customDialog.show();
    }

    private void skip2OtherApp() {
        Log.e(TAG, "skip2OtherApp");
        final Caller caller = new QQRsevenCaller(ActiveApp.CommonActiveApp.QQRsevenApp.getActiveApp());
        if (caller.fitCall()) {
            android.app.AlertDialog alertDialog = CommonAlertDialog.getCommonAlertDialog(
                    MainActivity.this,
                    CommonAlertDialog.QQRsevenTitle,
                    CommonAlertDialog.QQRsevenMSG,
                    new CommonAlertDialog.PositiveListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            caller.call(MainActivity.this);
                        }
                    }
            );
            alertDialog.show();
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    /**
     * 获取正在运行桌面包名（注：存在多个桌面时且未指定默认桌面时，该方法返回Null,使用时需处理这个情况）
     */
    public String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            return null;
        }
        if (res.activityInfo.packageName.equals("android")) {
            // 有多个桌面程序存在，且未指定默认项时；
            return null;
        } else {
            return res.activityInfo.packageName;
        }
    }


    private void showDialog(String tile, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tile);
        builder.setMessage(msg);
        builder.create().show();
    }

    private void registerBroadcastReceiver() {
        IntentFilter meidIntentFilter = new IntentFilter();
        meidIntentFilter.addAction("intent_action_imei_meid");
        registerReceiver(mDevInfoReceiver, meidIntentFilter);
    }

    private void unregisterBroadcastReceiver() {
        if (mDevInfoReceiver != null) {
            unregisterReceiver(mDevInfoReceiver);
        }
    }

    private class DevInfoReceiver extends BroadcastReceiver {
        private static final String TAG = "DevInfoReceiver";

        private DevInfoReceiver() {
        }

        public void onReceive(Context paramContext, Intent paramIntent) {
            if (!paramIntent.getAction().equals("intent_action_imei_meid")) {
                return;
            }
            SharedPreferences sp = paramContext.getSharedPreferences("pref_devinfo", 3);
            String str = paramIntent.getStringExtra("extra_key_meid");
            if (str != null) {
                sp.edit().putString("extra_key_meid", str).commit();
            }
//            paramIntent = paramIntent.getExtras();
            Bundle extrasBundle = paramIntent.getExtras();
            if (extrasBundle == null) {
                return;
            }
            String[] imeis = extrasBundle.getStringArray("extra_key_imei");
            if (imeis == null) {
                return;
            }
            int i = 0;
            while (i < imeis.length) {
                if (imeis[i] != null) {
                    Log.e(TAG, "deviceId = " + imeis[i]);
                    sp.edit().putString("extra_key_imei" + (i + 1), imeis[i]).commit();
                }
                i += 1;
            }
        }
    }

}
