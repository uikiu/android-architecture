package com.android001.www.example.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android001.ui.app.AppHolder;
import com.android001.www.example.MainActivity;
import com.android001.www.example.R;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/8/8 上午11:57
 */

public class SpannableStringSample {

    public static SpannableStringBuilder getSpannableStep() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        //1.
        ssb.append("第一步：请将客户手机连接到以下\n");
        //2.wifi名称
        ssb.append("WiFi:");
        String wifiApName = "143001-" + Build.MODEL;
        Log.e("android001", "要显示的wifi名称：" + wifiApName + "  ,  wifi名称的长度 = " + wifiApName.length());
        ssb.append(wifiApName);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FC6C33"));
        ssb.setSpan
                (
                        colorSpan, 21, 21 + wifiApName.length()
                        , Spannable.SPAN_INCLUSIVE_EXCLUSIVE

                );
        ssb.append("(可同时连接4台手机安装软件)\n\n");
        //3. 第二步
        ssb.append("第2步：连接成功后，客户手机打开浏览器扫描二维码，安装并打开软件后，即可免流量装机！\n");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), ssb.length() - 7, ssb.length() - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(36);//字体大小
        ssb.setSpan(absoluteSizeSpan, 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return ssb;
    }

    public static SpannableStringBuilder getSpannableNoteQRCode() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        //1.
        ssb.append("注：若客户手机无法扫描二维码，请链接成功后在客户手机浏览器地址树洞输入：\n");
        //2.wifi地址
        String wifiHost = "192.168.43.1:8888";
        ssb.append(wifiHost);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FC6C33"));
        ssb.setSpan
                (
                        colorSpan,ssb.length()-wifiHost.length(), ssb.length()
                        , Spannable.SPAN_INCLUSIVE_EXCLUSIVE

                );
        ssb.append("(注意英文冒号)，免流量下载dlhtInstallClient.apk开始安装!\n");
        return ssb;
    }

    public static SpannableStringBuilder getSpannableNoteInstallMode() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        //1.
        ssb.append("注：本机已开启装机模式，安装过程中无法连接无线网络，安装完成后，");
        //2.退出装机模式
        String exitInstall = "点此退出装机模式，回传数据！";
        ssb.append(exitInstall);
        //变色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FC6C33"));
        ssb.setSpan
                (
                        colorSpan,ssb.length()-exitInstall.length(), ssb.length()
                        , Spannable.SPAN_INCLUSIVE_EXCLUSIVE

                );
        //下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ssb.setSpan(underlineSpan,
                ssb.length()-exitInstall.length(),
                ssb.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        //点击效果
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AppHolder.getContext(), "请不要点我", Toast.LENGTH_SHORT).show();
            }
        };
        ssb.setSpan(clickableSpan, ssb.length()-exitInstall.length(),
                ssb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }

    public static Bitmap drawable2Bitmap(int drawableId) {
        Drawable db = AppHolder.getContext().getResources().getDrawable(drawableId);
        BitmapDrawable drawable = (BitmapDrawable) db;

        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }
}
