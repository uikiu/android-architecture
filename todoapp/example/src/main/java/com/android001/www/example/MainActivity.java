package com.android001.www.example;

import android.content.Context;
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

    Button mPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.e("启动完成");
        mPrint = (Button) findViewById(R.id.print);
        mPrint.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print:
                Log.i("xixionghui","获取到的ip = "+IpUtils.getWIFILocalIpAdress(this));
                break;
        }
    }

   void prntHuaWeiImei(){
       try {
           Class<?> classFile = Class.forName("android.telephony.TelephonyManager");
//            Method method = classFile.getMethod("getDeviceId",String.class);//java.lang.NoSuchMethodException: getDeviceId [class java.lang.String]
//           for (int i = 0; i < 2; i++) {
//               Logger.e("xixionghui","获取到的第"+i+"IMEI号码： "+method.invoke(String.class,i));
//
//           }

            Method[] declaredMethods = classFile.getDeclaredMethods();
           for (int i = 0; i < declaredMethods.length; i++) {
               Method method = declaredMethods[i];
               Logger.e("xixionghui","获取到的方法名称："+method.getName());
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }
}
