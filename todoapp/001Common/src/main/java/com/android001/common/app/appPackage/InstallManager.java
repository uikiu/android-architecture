package com.android001.common.app.appPackage;

import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android001.common.CommonUtilMethods;

import java.io.IOException;

/**
* class design：
* @author android001
* created at 2017/8/31 下午4:45
*/

public class InstallManager
{
    public static final String TAG = InstallManager.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static PackageInstaller.SessionParams generateInstallSession()
    {
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        return params;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void install() throws IOException {
        //获取installer
        PackageInstaller installer = CommonUtilMethods.getPackageManager().getPackageInstaller();
        //获取session
        int sessionID = installer.createSession(generateInstallSession());
        PackageInstaller.Session session = installer.openSession(sessionID);
        //读取要安装的apk
//        session.openWrite()
        //注册回到
        installer.registerSessionCallback(new PackageInstaller.SessionCallback() {
            @Override
            public void onCreated(int sessionId) {
                Log.e(TAG,"=====onCreate=====");
            }

            @Override
            public void onBadgingChanged(int sessionId) {
                Log.e(TAG,"=====onBadgingChanged=====");
            }

            @Override
            public void onActiveChanged(int sessionId, boolean active) {
                Log.e(TAG,"=====onActiveChanged=====");
            }

            @Override
            public void onProgressChanged(int sessionId, float progress) {
                Log.e(TAG,"=====onProgressChanged=====");
            }

            @Override
            public void onFinished(int sessionId, boolean success) {
                Log.e(TAG,"=====onFinished=====: success = "+success);
            }
        });

    }
}
