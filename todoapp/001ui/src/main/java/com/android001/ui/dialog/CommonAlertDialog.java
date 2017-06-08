package com.android001.ui.dialog;



import android.app.Activity;
import android.content.DialogInterface;

/**
* class design：
* @author android001
* created at 2017/6/8 上午9:37
*/

public class CommonAlertDialog {
    public static final String CommonNegative = "不执行";//消极
    public static final String CommonNeutral = "好的";//中立
    public static final String CommonPositive = "执行";//积极

    public static final String QQRsevenMSG = "检测到您是新机，是否需要同步通讯录？";
    public static final String QQRsevenTitle = "同步提示";

    public static class NegativeListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public static interface PositiveListener extends DialogInterface.OnClickListener {

    }



    public static android.support.v7.app.AlertDialog getCommonAlertDialogCompat(Activity activity, String title,String msg){//这些参数可以通过设定bundle通过intent传输
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        return builder.create();
    }

    public static android.app.AlertDialog getCommonAlertDialog(Activity activity, String title, String msg,PositiveListener listener){//这些参数可以通过设定bundle通过intent传输
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton(CommonNegative,new NegativeListener());
        builder.setPositiveButton(CommonPositive,listener);
        return builder.create();
    }
}


