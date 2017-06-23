package com.android001.ui.notification;

import android.app.Notification;
import android.app.VoiceInteractor;
import android.support.v7.app.NotificationCompat;

import com.android001.ui.app.AppHolder;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/21 下午3:14
 */

public class NotificationBuilder {

    /**
     * 透传元素
     */
    public static class PenetrateElement {
        private String title;
        private String content;
        private String icon;//url---->InputStream--->Bitmap
        private int type;//open mode 打开方式：componentName组件打开(普通跳转)、Uri打开(打开详情页)、url(打开浏览器)

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public Notification buildNotification(PenetrateElement penetrateElement) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppHolder.getContext());//为了版本兼容 选择V7包下的notificationCompat构造
//        builder.setTicker("ticker");//可有可无
        builder.setContentTitle(penetrateElement.getTitle());
        builder.setContentText(penetrateElement.getContent());
//        builder.setSubText("第三行内容");
        builder.setContentInfo("通知的右侧 时间的下面 用来展示一些其他信息");
        builder.setNumber(2);//number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setAutoCancel(true);//默认可移除
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setLargeIcon()
        return builder.build();
    }


    public Notification buildNotification(String title,String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppHolder.getContext());//为了版本兼容 选择V7包下的notificationCompat构造
//        builder.setTicker("ticker");//可有可无
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);//默认可移除
        return builder.build();
    }
}
