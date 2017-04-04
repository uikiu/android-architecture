package com.android001.www.example;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android001.common.hardware.IMTools;
import com.android001.common.hardware.sim.SimFactoryManager;
import com.android001.common.hardware.wifi.IpUtils;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mPrint,mDrawerNavigation;


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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        this.registerReceiver(new CalendarChangeReceiver(),filter);
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print:
                Log.i("xixionghui","获取到的ip = "+IpUtils.getWIFILocalIpAdress(this));
                break;
            case R.id.drawerNavigation:
                startActivity(new Intent(this,DrawerActivity.class));
                break;
        }
    }


}
