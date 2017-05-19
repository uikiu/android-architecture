package com.android001.www.example;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android001.common.hardware.sim.DeviceIdSelector;
import com.android001.common.hardware.sim.common.CommonDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;
import com.android001.common.hardware.sim.huaweiContact.HWDeviceIDRetriever;
import com.orhanobut.logger.Logger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button mPrint, mDrawerNavigation;


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
    }


    @Override
    public void onClick(View v) {
        DeviceIdSelector.getInstance().addDeviceID();
        switch (v.getId()) {
            case R.id.print:
                Logger.e(DeviceIdDAO.getInstance().getImEiAnyWay());
                break;
            case R.id.drawerNavigation:
                showDialog("IMEI&&MEID", DeviceIdDAO.getInstance().getImEiAnyWay());
                break;
        }
    }


    private void showDialog(String tile,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tile);
        builder.setMessage(msg);
        builder.create().show();
    }

}
