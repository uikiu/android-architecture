package com.android001.www.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android001.common.hardware.sim.DeviceIdSelector;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.orhanobut.logger.Logger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button mPrint, mDrawerNavigation;
    private final BroadcastReceiver mDevInfoReceiver = new DevInfoReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.e("启动完成");
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
        switch (v.getId()) {
            case R.id.print:
                Logger.e(DeviceIdDAO.getInstance().getImEiAnyWay());
                String launcherPackageName = getLauncherPackageName(this);
                Log.e(TAG,"获取到当前正在运行的包名 = "+launcherPackageName);
                break;
            case R.id.drawerNavigation:
                showDialog("IMEI&&MEID", DeviceIdDAO.getInstance().getImEiAnyWay());
                break;
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


    private void showDialog(String tile,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tile);
        builder.setMessage(msg);
        builder.create().show();
    }

    private void registerBroadcastReceiver()
    {
        IntentFilter meidIntentFilter = new IntentFilter();
        meidIntentFilter.addAction("intent_action_imei_meid");
        registerReceiver(mDevInfoReceiver, meidIntentFilter);
    }

    private void unregisterBroadcastReceiver()
    {
        if (mDevInfoReceiver != null) {
            unregisterReceiver(mDevInfoReceiver);
        }
    }

    private class DevInfoReceiver extends BroadcastReceiver
    {
        private static final String TAG = "DevInfoReceiver";

        private DevInfoReceiver() {}

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            if (!paramIntent.getAction().equals("intent_action_imei_meid"))
            {
                return;
            }
            SharedPreferences sp = paramContext.getSharedPreferences("pref_devinfo", 3);
            String str = paramIntent.getStringExtra("extra_key_meid");
            if (str != null)
            {
                sp.edit().putString("extra_key_meid", str).commit();
            }
//            paramIntent = paramIntent.getExtras();
            Bundle extrasBundle = paramIntent.getExtras();
            if (extrasBundle == null)
            {
                return;
            }
            String[] imeis = extrasBundle.getStringArray("extra_key_imei");
            if (imeis == null)
            {
                return;
            }
            int i = 0;
            while (i < imeis.length)
            {
                if (imeis[i] != null)
                {
                    Log.e(TAG,"deviceId = "+imeis[i]);
                    sp.edit().putString("extra_key_imei" + (i + 1), imeis[i]).commit();
                }
                i += 1;
            }
        }
    }

}
