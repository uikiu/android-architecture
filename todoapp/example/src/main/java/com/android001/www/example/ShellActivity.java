package com.android001.www.example;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android001.common.CommonUtilMethods;
import com.android001.common.app.utils.Crc32Util;
import com.android001.common.shell.ShellUtils;
import com.android001.shell.java.ShellManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.CRC32;

public class ShellActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnShell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        btnShell = (Button) findViewById(R.id.shell);
        btnShell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shell:
//                ShellUtils.CommandResult commandResult = ShellUtils.execShellCommand("chmod +x /data/local/tmp/eClick",false,true);
                ShellUtils.CommandResult commandResult = ShellUtils.execShellCommand("chmod +x "+ Environment.getExternalStorageDirectory().getPath()+"/eClick",false,true);
                Log.e("android001","添加执行权限："+commandResult.toString());
                break;
            default:
                break;
        }
    }

    private List<PackageInfo> getAdbPackageInfoList() {
        List<PackageInfo> pmPackageInfoList = new ArrayList<>();
        try {
            ShellUtils.CommandResult commandResult = ShellUtils.execShellCommand("pm list packages", false, true);
            if (commandResult.result == 0) {
                String allPackageName = commandResult.successMsg;
                String replacePackage = allPackageName.replace("package:", ",");
                replacePackage = replacePackage.substring(1, replacePackage.length());
                String[] packageNames = replacePackage.split(",");
                for (int i = 0; i < packageNames.length; i++) {
                    String packageName = packageNames[i];

                    try {
                        PackageInfo packageInfo = CommonUtilMethods.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                        if (null != packageInfo) {
                            pmPackageInfoList.add(packageInfo);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pmPackageInfoList;
    }


}
