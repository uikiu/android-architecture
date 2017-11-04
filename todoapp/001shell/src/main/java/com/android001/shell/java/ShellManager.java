package com.android001.shell.java;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
* class design：
* @author android001
* created at 2017/8/29 上午11:02
*/

public class ShellManager {
    public static void reBoot(){
        execShell("pm list packages -3");
    }

    public static void execShell(String cmd){
        Log.e("android001","要执行的命令："+cmd);
        try{
            //权限设置
            Process p = Runtime.getRuntime().exec("sh");  //su为root用户,sh普通用户
            //获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            //提交命令
            dataOutputStream.flush();
            //关闭流操作
            dataOutputStream.close();
            outputStream.close();
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }
}
