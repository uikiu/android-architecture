package com.android001.www.example.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.NotificationCompat;

import com.android001.common.resource.res.image.ImageUtils;
import com.android001.ui.app.AppHolder;
import com.android001.www.example.R;

/**
* class design：
* @author android001
* created at 2017/6/21 下午4:24
*/

public class NotificationBuilder {

    public static Notification buildNotification(String title, String content, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppHolder.getContext());//为了版本兼容 选择V7包下的notificationCompat构造
//        builder.setTicker("ticker");//可有可无
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);//默认可移除
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(ImageUtils.resouce2Bitmap(R.mipmap.ic_launcher));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                com.android001.common.app.AppHolder.getContext(),
                1,
                intent,
                0
        );
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }
}
